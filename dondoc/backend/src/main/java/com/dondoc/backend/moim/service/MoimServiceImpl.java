package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.moim.dto.MissionRequestDto;
import com.dondoc.backend.moim.dto.WithdrawRequestDto;
import com.dondoc.backend.moim.entity.*;
import com.dondoc.backend.moim.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
}
