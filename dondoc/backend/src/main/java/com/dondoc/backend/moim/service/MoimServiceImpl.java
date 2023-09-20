package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.moim.dto.*;
import com.dondoc.backend.moim.entity.*;
import com.dondoc.backend.moim.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoimServiceImpl implements MoimService{

    private final MoimRepository moimRepository;
    private final MoimMemberRepository moimMemberRepository;
    private final WithdrawRequestRepository withdrawRequestRepository;
    private final CategoryRepository categoryRepository;
    private final MissionRepository missionRepository;

    private WebClient webClient = WebClient.create("http://localhost:9090"); // 은행 서버

    @Override
    public boolean createOnwerAPI(String identificationNumber, String moimName) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("identificationNumber", identificationNumber);
        bodyMap.put("ownerName", moimName);

        Map response = webClient.post()
                .uri("/bank/owner/create")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){ // 예금주 등록에 성공했으면
            return true;
        }

        log.debug("creaetOnwerAPI log = {} ", response.toString());
        return false;
    }

    @Override
    public String createAccountAPI(String moimName, int bankCode, String identificationNumber, String password) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("accountName", moimName);
        bodyMap.put("bankCode", bankCode);
        bodyMap.put("identificationNumber", identificationNumber);
        bodyMap.put("password", password);

        Map response = webClient.post()
                .uri("/bank/account/create")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){ // 계좌생성 성공했으면
            Map<String,String> tmp = (Map<String, String>) response.get("response");
            System.out.println(tmp);
            System.out.println(tmp.get("accountNumber"));
            return tmp.get("accountNumber");
        }

        log.debug("creaetMoimAPI log = {} ", response.toString());
        return null;
    }

    @Transactional
    @Override
    public Moim createMoim(String identificationNumber, String moimName, String introduce, String moimAccount, int limited, int moimType) {

        Moim moim = new Moim(identificationNumber,moimName,introduce,moimAccount,limited,moimType);
        moimRepository.save(moim);

        return moim;
    }

    /** 관리자에게 출금 요청 */
    @Override
    @Transactional
    public WithdrawRequestDto.Response withdrawReq(WithdrawRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(req.getUserId(), req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(()-> new NotFoundException("카테고리 정보가 존재하지 않습니다."));

        // 요청 금액 -> 가능한지 판단
        Map response = webClient.get()
                .uri("/bank/account/detail/"+member.getAccount().getAccountId())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){ // 계좌 조회 성공
            Map<String,String> res = (Map<String, String>) response.get("response");
            
            // 현재 모임 잔액 - 제한된 금액
            int possibleAmount = Integer.parseInt(String.valueOf(res.get("balance"))) - member.getMoim().getLimited();

            if(possibleAmount < req.getAmount()){ // 요청 가능한 금액보다 많이 요청했을 때
                throw new IllegalArgumentException("요청 가능한 금액을 초과하였습니다.");
            } else { // 요청 가능한 금액일 때
                WithdrawRequest withdrawRequest = withdrawRequestRepository.save(
                        req.saveWithdrawRequestDto(member, req.getTitle(), req.getContent(),
                                category, req.getAmount(), 0)
                );

                return WithdrawRequestDto.Response.toDTO(withdrawRequest);
            }
        } else { // 계좌 조회 실패
            throw new NotFoundException("계좌 조회에 실패하였습니다.");
        }
    }

    /** 관리자에게 미션 요청 */
    @Override
    @Transactional
    public MissionRequestDto.Response missionReq(MissionRequestDto.Request req) throws Exception {

        // 신청인
        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(req.getUserId(), req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 미션 할 사람
        MoimMember missionMember = moimMemberRepository.findByUser_IdAndMoim_Id(req.getMissionMemberId(), req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        // 일반 이용자가 다른 사람에게 미션을 부여했을 때
        if(member.getUserType()==2 && (missionMember.getUser().getPhoneNumber() != member.getUser().getPhoneNumber())){
            throw new IllegalArgumentException("관리자만 다른 사용자에게 미션을 부여할 수 있습니다.");
        }

        log.info("missionMember : {}", missionMember.getUser().getName());

        // 종료날짜가 오늘이거나 이전의 날짜를 입력했을 때
        LocalDate now = LocalDate.now();
        if(req.getEndDate().isBefore(now) || req.getEndDate().isEqual(now)){
            throw new IllegalArgumentException("종료 일자를 다시 확인해주세요.");
        }

        // 요청 금액 -> 가능한지 판단
        Map response = webClient.get()
                .uri("/bank/account/detail/"+member.getAccount().getAccountId())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){ // 계좌 조회 성공
            Map<String,String> res = (Map<String, String>) response.get("response");

            // 현재 모임 잔액 - 제한된 금액
            int possibleAmount = Integer.parseInt(String.valueOf(res.get("balance"))) - member.getMoim().getLimited();

            if(possibleAmount < req.getAmount()){ // 요청 가능한 금액보다 많이 요청했을 때
                throw new IllegalArgumentException("요청 가능한 금액을 초과하였습니다.");
            } else { // 요청 가능한 금액일 때

                Mission missionRequest;

                // 신청인이 관리자일 때 -> 바로 미션 승인 상태로
                if(member.getUserType() == 1) {
                    missionRequest = missionRepository.save(
                            req.saveMissionRequestDto(missionMember, req.getTitle(), req.getContent(), req.getAmount(), 1, LocalDateTime.now(), req.getEndDate())
                    );
                } else {
                    missionRequest = missionRepository.save(
                            req.saveMissionRequestDto(missionMember, req.getTitle(), req.getContent(), req.getAmount(), 0, req.getEndDate())
                    );
                }

                return MissionRequestDto.Response.toDTO(missionRequest);
            }
        } else { // 계좌 조회 실패
            throw new NotFoundException("계좌 조회에 실패하였습니다.");
        }

    }

    /** 요청 관리/목록 - 전체 리스트 조회 */
    @Override
    public AllRequestDto.Response getRequestList(AllRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(req.getUserId(), req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        List<WithdrawRequest> withdrawRequestList;
        List<Mission> missionList;

        if(member.getUserType()==1) { // 요청자가 모임의 관리자이면

            // 특정 조회하고자 하는 회원을 입력 했을 때 - 회원별 필터링
            if(req.getFindUserId()!=null){
                member = moimMemberRepository.findByUser_IdAndMoim_Id(req.getFindUserId(), req.getMoimId())
                        .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

                // 해당 모임에서 출금 요청 -> status 0인거만 조회 (출금 승인이 이루어지지 않은 것)
                // 정렬 -> 1. CreatedAt 내림차순
                withdrawRequestList = withdrawRequestRepository.findByMoimMemberAndMoimMember_MoimAndStatusOrderByCreatedAtDesc(member, member.getMoim(),0);

                // 해당 모임에서의 미션 요청 -> status가 -1 아닌거 조회 (미션 거절이 되지 않은 것)
                // 정렬 -> 1. status 오름차순, 2. CreatedAt 내림차순
                missionList = missionRepository.findByMoimMemberAndMoimMember_MoimAndStatusNotOrderByStatusAscCreatedAtDesc(member, member.getMoim(), -1);

            } else { // 전체 조회
                withdrawRequestList = withdrawRequestRepository.findByMoimMember_MoimAndStatusOrderByCreatedAtDesc(member.getMoim(), 0);
                missionList = missionRepository.findByMoimMember_MoimAndStatusNotOrderByStatusAscCreatedAtDesc(member.getMoim(), -1);
            }


        } else { // 요청자가 일반 회원이면

            // 해당 모임에서 출금 요청 모두 조회
            // 정렬 -> 1. status 오름차순, 2. CreatedAt 내림차순
            withdrawRequestList = withdrawRequestRepository.findByMoimMemberAndMoimMember_MoimOrderByStatusAscCreatedAtDesc(member, member.getMoim());

            // 해당 모임에서의 미션 요청 모두 조회
            // 정렬 -> 1. status 오름차순, 2. CreatedAt 내림차순
            missionList = missionRepository.findByMoimMemberAndMoimMember_MoimOrderByStatusAscCreatedAtDesc(member, member.getMoim());

        }

        List<WithdrawRequestDto.Response> resultWithdrawRequest = withdrawRequestList.stream()
                .map(entity -> WithdrawRequestDto.Response.toDTO(entity))
                .collect(Collectors.toList());

        log.info("withdrawRequestList Size : {}", withdrawRequestList.size());

        List<MissionRequestDto.Response> resultMission = missionList.stream()
                .map(entity -> MissionRequestDto.Response.toDTO(entity))
                .collect(Collectors.toList());

        log.info("missionList Size : {}", missionList.size());

        return AllRequestDto.Response.toDTO(resultWithdrawRequest, resultMission);

    }

    /** 요청 상세조회 */
    @Override
    public DetailRequestDto.Response getRequestDetail(DetailRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(req.getUserId(), req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        if(req.getRequestType() == 0) { // 출금 요청 조회

            WithdrawRequest withdrawRequest = withdrawRequestRepository.findByMoimMemberAndMoimMember_MoimAndId(member, member.getMoim(), req.getRequestId())
                    .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

            return DetailRequestDto.Response.toDTO_WithdrawReq(
                    WithdrawRequestDto.Response.toDTO(withdrawRequest)
            );

        } else if(req.getRequestType() == 1) { // 미션 요청 조회

            Mission mission = missionRepository.findByMoimMemberAndMoimMember_MoimAndId(member, member.getMoim(), req.getRequestId())
                    .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

            return DetailRequestDto.Response.toDTO_Mission(
                    MissionRequestDto.Response.toDTO(mission)
            );
        } else {
            throw new IllegalArgumentException("조회 할 요청타입을 다시 확인해 주세요.");
        }
    }

    /** 출금 요청 승인 */
    @Override
    @Transactional
    public AllowRequestDto.Response allowRequest(AllowRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(req.getUserId(), req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 일반 이용자가 출금요청을 한 경우
        if(member.getUserType()!=1)
            throw new IllegalArgumentException("관리자만 승인할 수 있습니다.");

        WithdrawRequest withdrawRequest = withdrawRequestRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(withdrawRequest.getStatus()!=0){
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 출금 요청한 회원
        MoimMember moimMember = withdrawRequest.getMoimMember();

        // 모임 유형에 따라 다른 로직
        if(member.getMoim().getMoimType() == 2) { // 두명 관리자

            // 출금 요청한 사람이 관리자이면 ?
            // 다른 관리자의 승인 필요
            if(member.getUser().getId() == moimMember.getUser().getId()) {
                throw new IllegalArgumentException("본인의 출금 요청에 승인할 수 없습니다.");
            }
        }

        log.info("moimMemberAccountNumber : {}", moimMember.getAccount().getAccountNumber());
        log.info("moimAccountNumber : {}", moimMember.getMoim().getMoimAccountNumber());

        // 내가 등록해놓은 계좌로 이체 => Bank 계좌이체 API 사용
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("identificationNumber", moimMember.getMoim().getIdentificationNumber());
        bodyMap.put("accountId", moimMember.getMoim().getMoimAccountId());
        bodyMap.put("toCode", moimMember.getAccount().getBankCode());
        bodyMap.put("toAccount", moimMember.getAccount().getAccountNumber());
        bodyMap.put("transferAmount", withdrawRequest.getAmount());
        bodyMap.put("password", req.getPassword());
        bodyMap.put("sign", "");
        bodyMap.put("toSign", "");

        Map response = webClient.post()
                .uri("/bank/account/transfer")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true") { // 계좌이체 성공
            // 출금요청에서 status 변경
            withdrawRequest.setStatus(1);
            //withdrawRequestRepository.deleteById(withdrawRequest.getId());

            return AllowRequestDto.Response.toDTO(
                "계좌 이체가 성공적으로 이루어졌습니다.",
                    moimMember.getAccount().getAccountNumber(),
                    withdrawRequest.getAmount()
            );

        } else { // 계좌이체 실패
            throw new Exception("계좌 이체가 정상적으로 이루어지지 않았습니다.");
        }
    }

    /** 출금 요청 거절 */
    @Override
    @Transactional
    public String rejectRequest(RejectRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(req.getUserId(), req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 일반 이용자가 출금요청을 한 경우
        if(member.getUserType()!=1)
            throw new IllegalArgumentException("관리자만 거절할 수 있습니다.");

        WithdrawRequest withdrawRequest = withdrawRequestRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(withdrawRequest.getStatus()!=0){
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 출금요청에서 status 변경
        withdrawRequest.setStatus(2);

        return "출금요청이 취소 되었습니다.";

    }


}
