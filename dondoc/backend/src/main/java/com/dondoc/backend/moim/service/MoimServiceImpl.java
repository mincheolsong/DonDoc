package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.EncryptionUtils;
import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.moim.dto.*;
import com.dondoc.backend.moim.entity.*;
import com.dondoc.backend.moim.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoimServiceImpl implements MoimService{

    private final MoimMemberService moimMemberService;
    private final MoimRepository moimRepository;
    private final MoimMemberRepository moimMemberRepository;
    private final WithdrawRequestRepository withdrawRequestRepository;
    private final CategoryRepository categoryRepository;
    private final MissionRepository missionRepository;
    private final PasswordEncoder passwordEncoder;

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
    public Map<String,Object> createAccountAPI(String moimName, int bankCode, String identificationNumber, String password) {
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
            Map<String,Object> result = new HashMap<>();
            result.put("accountId",tmp.get("accountId"));
            result.put("accountNumber",tmp.get("accountNumber"));

            return result;
        }

        log.debug("creaetMoimAPI log = {} ", response.toString());
        return null;
    }

    @Transactional
    @Override
    public Moim createMoim(String identificationNumber, String moimName, String introduce, Long moimAccountId, String moimAccountNumber, int limited, int moimType, int managerSize) {
        if(moimType<1 || moimType>3){
            throw new RuntimeException("모임 타입을 잘못 지정했습니다");
        }

        if(moimType==1 && managerSize!=0){
            throw new RuntimeException("모임 종류에 맞지않는 매니저를 초대했습니다.");
        }
        if(moimType==2 && managerSize!=1){
            throw new RuntimeException("모임 종류에 맞지않는 매니저를 초대했습니다.");
        }

        Moim moim = new Moim(identificationNumber,moimName,introduce,moimAccountId,moimAccountNumber,limited,moimType);
        moimRepository.save(moim);

        return moim;
    }

    @Override
    public boolean checkIdenNumDuplicate(String identificationNumber) {
        List<Moim> byIdentificationNumber = moimRepository.findByIdentificationNumber(identificationNumber);
        if(byIdentificationNumber.size()!=0)
            return true;

        return false;
    }

    @Override
    public String makeIdentificationNumber() throws Exception {
        String result = EncryptionUtils.makeIdentificationNumber();

        // 중복체크
        if (checkIdenNumDuplicate(result)){
            throw new Exception("인증번호 생성 중 오류 발생.");
        }
        return result;
    }
    
    @Override
    public List<Moim> getMoimList(Long userId) {
        List<Moim> result = moimRepository.getMoimList(userId);

        return result;
    }


    @Override
    public MoimDetailDto.Response getMoimDetail(Long userId, Long moimId) throws Exception {

        try {
            MoimMember moimMember = moimMemberService.findMoimMember(userId, moimId); // userType 검사 (Exception 던질 수 있음)
            int type = moimMember.getUserType(); // userType
            int status = moimMember.getStatus(); // 초대 승인 여부

            /**
             * MoimMember (컬렉션 fetch join)
             * MoimMember의 Account (fetch join)
             * MoimMember의 User (fetch join)
             */
            List<Moim> moims = this.findMoimWithMember(moimId);

            if(moims.size()!=1) throw new RuntimeException("moimId : " + moimId + "모임 상세조회 실패"); // Moim이 하나가 아니면 에러

            Moim moim = moims.get(0);

            /** 관리자인 경우 && 초대 승인된 상태 **/
            if(type==0 && status==1) {

                int balance = this.searchBalance(moim.getIdentificationNumber()); // (은행 API) 현재 모임계좌의 잔액조회
                List<MoimDetailDto.WithDrawRequestDto> withDrawRequestDtos = new ArrayList<>();

                List<MoimMember> moimMemberList = moim.getMoimMemberList();
                for (MoimMember member : moimMemberList) {
                    // 지연로딩 초기화 수행
                    List<WithdrawRequest> withdrawRequests = member.getWithdrawRequests();
                    for (WithdrawRequest withdrawRequest : withdrawRequests) {
                        /**
                         * - 0 : 수락 대기 중
                         * - 1 : 요청 수락
                         * - 2 : 요청 거절
                         */
                        if(withdrawRequest.getStatus()==0){ // 수락 대기중인 요청이면
                            withDrawRequestDtos.add(new MoimDetailDto.WithDrawRequestDto(withdrawRequest,member));
                        }
                    }
                }

                MoimDetailDto.ManagerResponse result = new MoimDetailDto.ManagerResponse(moim,balance);
                result.setRequest(withDrawRequestDtos);

                return result;

            }else if(type==1 && status==1){ /**일반 사용자의 경우 && 초대 승인된 상태**/
                MoimDetailDto.MemberResponse result = new MoimDetailDto.MemberResponse(moim);
                return result;
            }

            throw new RuntimeException("모임상세조회 실패");

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Moim> findMoimWithMember(Long moimId) {
        List<Moim> moims = moimRepository.findWithMember(moimId);
        return moims;
    }

    @Override
    public int searchBalance(String identificationNumber) {
        Map<String, Object> bodyMap = new HashMap<>();
        List<String> identificationNumbers = new ArrayList<>();
        identificationNumbers.add(identificationNumber);

        bodyMap.put("identificationNumber", identificationNumbers);

        Map response = webClient.post()
                .uri("/bank/account/list")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){ // 계좌목록 조회 성공 시
            List<Map<String,Object>> response_data = (List<Map<String,Object>>) response.get("response");
            // 모임계좌의 당 식별번호는 하나로 유한함
            Map<String, Object> tmp = response_data.get(0);
            String result = tmp.get("balance").toString();

            return Integer.parseInt(result);
        }

        log.debug("searchBalance log = {} ", response.toString());
        return 0;

    }                

    /** 관리자에게 출금 요청 */
    @Override
    @Transactional
    public WithdrawRequestDto.Response withdrawReq(Long userId, WithdrawRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(()-> new NotFoundException("카테고리 정보가 존재하지 않습니다."));

        // 요청 금액 -> 가능한지 판단
        Map response = webClient.get()
                .uri("/bank/account/detail/"+member.getMoim().getMoimAccountId())
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
    public MissionRequestDto.Response missionReq(Long userId, MissionRequestDto.Request req) throws Exception {

        // 신청인
        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
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
                .uri("/bank/account/detail/"+member.getMoim().getMoimAccountId())
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
    public AllRequestDto.Response getRequestList(Long userId, AllRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
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
    public DetailRequestDto.Response getRequestDetail(Long userId, DetailRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        if(req.getRequestType() == 0) { // 출금 요청 조회
            WithdrawRequest withdrawRequest;

            if(member.getUserType()==0){ // 관리자일 때 해당 요청 조회 가능
                withdrawRequest = withdrawRequestRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                        .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));
            } else { // 일반 사용자일 때 자신의 요청에만 조회 가능
                withdrawRequest = withdrawRequestRepository.findByMoimMemberAndMoimMember_MoimAndId(member, member.getMoim(), req.getRequestId())
                        .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));
            }

            return DetailRequestDto.Response.toDTO_WithdrawReq(
                    WithdrawRequestDto.Response.toDTO(withdrawRequest)
            );

        } else if(req.getRequestType() == 1) { // 미션 요청 조회
            Mission mission;

            if(member.getUserType()==0) { // 관리자일 때 해당 요청 조회 가능
                mission = missionRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                        .orElseThrow(() -> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));
            } else { // 일반 사용자일 때 자신의 요청에만 조회 가능
                mission = missionRepository.findByMoimMemberAndMoimMember_MoimAndId(member, member.getMoim(), req.getRequestId())
                        .orElseThrow(() -> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));
            }

            return DetailRequestDto.Response.toDTO_Mission(
                    MissionRequestDto.Response.toDTO(mission)
            );
        } else {
            throw new IllegalArgumentException("조회 할 요청타입을 다시 확인해 주세요.");
        }
    }

    /** 요청 취소하기 */
    @Override
    @Transactional
    public CancelRequestDto.Response cancelReq(Long userId, CancelRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        if(req.getRequestType() == 0) { // 출금 요청 조회

            WithdrawRequest withdrawRequest = withdrawRequestRepository.findByMoimMemberAndMoimMember_MoimAndId(member, member.getMoim(), req.getRequestId())
                    .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

            if(withdrawRequest.getStatus()!=0) throw new IllegalArgumentException("승인 대기 중인 요청이 아닙니다.");

            withdrawRequestRepository.deleteById(withdrawRequest.getId());

            return CancelRequestDto.Response.toDTO(
                    "출금 요청을 취소하셨습니다.",
                    withdrawRequest.getMoimMember().getMoim().getMoimName(),
                    withdrawRequest.getTitle(),
                    withdrawRequest.getContent(),
                    withdrawRequest.getAmount()
            );

        } else if(req.getRequestType() == 1) { // 미션 요청 조회

            Mission mission = missionRepository.findByMoimMemberAndMoimMember_MoimAndId(member, member.getMoim(), req.getRequestId())
                    .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

            if(mission.getStatus()!=0) throw new IllegalArgumentException("승인 대기 중인 요청이 아닙니다.");

            missionRepository.deleteById(mission.getId());

            return CancelRequestDto.Response.toDTO(
                    "미션 요청을 취소하셨습니다.",
                    mission.getMoimMember().getMoim().getMoimName(),
                    mission.getTitle(),
                    mission.getContent(),
                    mission.getAmount()
            );
        } else {
            throw new IllegalArgumentException("조회 할 요청타입을 다시 확인해 주세요.");
        }
    }

    @Override
    public Moim findById(Long id) throws Exception{
        Moim moim = moimRepository.findById(id).orElseThrow(() -> new NotFoundException("해당하는 모임이 존재하지 않습니다"));
        return moim;
    }

    @Override
    public List<Object> getHistoryList(String identificationNumber, String accountNumber) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("identificationNumber", identificationNumber);
        bodyMap.put("accountNumber", accountNumber);

        Map response = webClient.post()
                .uri("/bank/history")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){
            List<Object> list = (List<Object>)response.get("response");
            return list;
        }else{
            return null;
        }

    }

    @Override
    public Object getHistoryDetail(String identificationNumber, String accountNumber, Long historyId) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("identificationNumber", identificationNumber);
        bodyMap.put("accountNumber", accountNumber);
        bodyMap.put("historyId",historyId);

        Map response = webClient.post()
                .uri("/bank/detail_history")
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){
            Object result = response.get("response");
            return result;
        }else{
            return null;
        }

    }



    /** 출금 요청 승인 */
    @Override
    @Transactional
    public AllowRequestDto.Response allowRequest(Long userId, AllowRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 일반 이용자가 승인하려는 경우
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

            return AllowRequestDto.Response.toDTO_WithdrawReq(
                "계좌 이체가 성공적으로 이루어졌습니다.",
                    WithdrawRequestDto.Response.toDTO(withdrawRequest)
            );

        } else { // 계좌이체 실패
            throw new Exception("계좌 이체가 정상적으로 이루어지지 않았습니다.");
        }
    }

    /** 출금 요청 거절 */
    @Override
    @Transactional
    public String rejectRequest(Long userId, RejectRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 일반 이용자가 거절하려는 경우
        if(member.getUserType()!=1)
            throw new IllegalArgumentException("관리자만 거절할 수 있습니다.");

        WithdrawRequest withdrawRequest = withdrawRequestRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(withdrawRequest.getStatus()!=0){
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 출금요청에서 status 변경
        withdrawRequest.setStatus(2);

        return "출금요청이 거절 되었습니다.";

    }

    /** 미션 요청 승인 */
    @Override
    @Transactional
    public AllowRequestDto.Response allowMissionRequest(Long userId, AllowRequestDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 관리자 비밀번호 확인
        if(!passwordEncoder.matches(req.getPassword() + member.getUser().getSalt() , member.getUser().getPassword())){
            throw new NoSuchElementException("비밀번호가 일치하지 않습니다.");
        }

        // 일반 이용자가 승인하려는 경우
        if(member.getUserType()!=1)
            throw new IllegalArgumentException("관리자만 승인할 수 있습니다.");

        Mission mission = missionRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(mission.getStatus()!=0){
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 미션 요청한 회원
        MoimMember moimMember = mission.getMoimMember();

        // 모임 유형에 따라 다른 로직
        if(member.getMoim().getMoimType() == 2) { // 두명 관리자

            // 출금 요청한 사람이 관리자이면 ?
            // 다른 관리자의 승인 필요
            if(member.getUser().getId() == moimMember.getUser().getId()) {
                throw new IllegalArgumentException("본인의 미션 요청에 승인할 수 없습니다.");
            }
        }

        // 현재 상황에서 승인할 수 있는 요청인지 -> limited 확인
        Map response = webClient.get()
                .uri("/bank/account/detail/"+member.getMoim().getMoimAccountId())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(response.get("success").toString()=="true"){ // 계좌 조회 성공
            Map<String,String> res = (Map<String, String>) response.get("response");

            // 현재 모임 잔액 - 제한된 금액
            int possibleAmount = Integer.parseInt(String.valueOf(res.get("balance"))) - member.getMoim().getLimited();

            if(possibleAmount < mission.getAmount()){
                throw new IllegalArgumentException("승인 가능한 금액을 초과하였습니다.");
            } else { // 승인 가능한 금액일 때

                // 제한금액 갱신
                moimMember.getMoim().setLimited(member.getMoim().getLimited()+mission.getAmount());

                // status 바꾸기
                mission.setStatus(1);

                return AllowRequestDto.Response.toDTO_Mission(
                        "미션 승인이 성공적으로 이루어졌습니다.",
                        MissionRequestDto.Response.toDTO(mission)
                );
            }
        } else { // 계좌 조회 실패
            throw new NotFoundException("계좌 조회에 실패하였습니다.");
        }

    }

    /** 미션 요청 거절 */
    @Override
    @Transactional
    public String rejectMissionRequest(Long userId, RejectRequestDto.Request req) throws Exception {
        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 일반 이용자가 거절하려는 경우
        if(member.getUserType()!=1)
            throw new IllegalArgumentException("관리자만 거절할 수 있습니다.");

        Mission mission = missionRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(mission.getStatus()!=0){
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 미션요청에서 status 변경
        mission.setStatus(2);

        return "미션요청이 거절 되었습니다.";
    }

    /** 미션 성공 */
    @Override
    @Transactional
    public SuccessOrNotMissionDto.Response successMission(Long userId, SuccessOrNotMissionDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 일반 이용자가 승인하려는 경우
        if(member.getUserType()!=1)
            throw new IllegalArgumentException("관리자만 승인할 수 있습니다.");

        Mission mission = missionRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(mission.getStatus()==0){
            throw new IllegalArgumentException("승인 대기 중인 미션입니다.");
        } else if(mission.getStatus()!=1) {
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 미션 요청한 회원
        MoimMember moimMember = mission.getMoimMember();

        // 모임 유형에 따라 다른 로직
        if(member.getMoim().getMoimType() == 2) { // 두명 관리자

            // 해당 미션을 한 사람이 관리자이면 ?
            // 다른 관리자의 승인 필요
            if(member.getUser().getId() == moimMember.getUser().getId()) {
                throw new IllegalArgumentException("본인의 미션에 승인할 수 없습니다.");
            }
        }

        // 계좌이체
        // 내가 등록해놓은 계좌로 이체 => Bank 계좌이체 API 사용
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("identificationNumber", moimMember.getMoim().getIdentificationNumber());
        bodyMap.put("accountId", moimMember.getMoim().getMoimAccountId());
        bodyMap.put("toCode", moimMember.getAccount().getBankCode());
        bodyMap.put("toAccount", moimMember.getAccount().getAccountNumber());
        bodyMap.put("transferAmount", mission.getAmount());
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

            // 미션 status 변경
            mission.setStatus(4);

            // 모임 limited 변경
            member.getMoim().setLimited(member.getMoim().getLimited()-mission.getAmount());

            return SuccessOrNotMissionDto.Response.toDTO(
                    "미션 성공하셨습니다.",
                    mission.getMoimMember().getMoim().getMoimName(),
                    mission.getTitle(),
                    mission.getContent(),
                    mission.getAmount(),
                    mission.getEndDate()
            );

        } else { // 계좌이체 실패
            throw new Exception("계좌 이체가 정상적으로 이루어지지 않았습니다.");
        }
    }

    /** 미션 실패 */
    @Override
    @Transactional
    public SuccessOrNotMissionDto.Response failMission(Long userId, SuccessOrNotMissionDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        // 일반 이용자가 거절하려는 경우
        if(member.getUserType()!=1)
            throw new IllegalArgumentException("관리자만 거절할 수 있습니다.");

        Mission mission = missionRepository.findByMoimMember_MoimAndId(member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(mission.getStatus()==0){
            throw new IllegalArgumentException("승인 대기 중인 미션입니다.");
        } else if(mission.getStatus()!=1) {
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 미션요청에서 status 변경
        mission.setStatus(3);

        return SuccessOrNotMissionDto.Response.toDTO(
                "미션 실패하셨습니다.",
                mission.getMoimMember().getMoim().getMoimName(),
                mission.getTitle(),
                mission.getContent(),
                mission.getAmount(),
                mission.getEndDate()
        );
    }

    /** 미션 포기 */
    @Override
    @Transactional
    public SuccessOrNotMissionDto.Response quitMission(Long userId, SuccessOrNotMissionDto.Request req) throws Exception {

        MoimMember member = moimMemberRepository.findByUser_IdAndMoim_Id(userId, req.getMoimId())
                .orElseThrow(()-> new NotFoundException("모임 회원의 정보가 존재하지 않습니다."));

        log.info("member : {}", member.getUser().getName());

        Mission mission = missionRepository.findByMoimMemberAndMoimMember_MoimAndId(member, member.getMoim(), req.getRequestId())
                .orElseThrow(()-> new IllegalArgumentException("요청 정보가 없습니다. 정보를 다시 확인해 주세요."));

        if(mission.getStatus()==0){
            throw new IllegalArgumentException("승인 대기 중인 미션입니다.");
        } else if(mission.getStatus()!=1) {
            throw new IllegalArgumentException("승인 혹은 거절 된 요청입니다.");
        }

        // 미션요청에서 status 변경
        mission.setStatus(3);

        return SuccessOrNotMissionDto.Response.toDTO(
                "미션 포기하셨습니다.",
                mission.getMoimMember().getMoim().getMoimName(),
                mission.getTitle(),
                mission.getContent(),
                mission.getAmount(),
                mission.getEndDate()
        );
    }



    /** 나의 미션 조회 */
    @Override
    public List<MissionInfoDto.Response> getMyMission(Long userId) throws Exception {

        MoimMember member = moimMemberRepository.findTop1ByUser_Id(userId)
                .orElseThrow(()-> new NotFoundException("미션 정보가 없습니다."));

        log.info("member : {}", member.getUser().getName());

        List<Mission> missionList = missionRepository.findByMoimMemberAndStatus(member, 1);


        log.info("missionList : {}", missionList.size());

        List<MissionInfoDto.Response> resultMissionList = missionList.stream()
                .map(entity -> MissionInfoDto.Response.toDTO(entity.getId(), entity.getMoimMember().getMoim().getMoimName(), entity.getTitle(), entity.getContent(), entity.getAmount(), entity.getEndDate()))
                .collect(Collectors.toList());

        return resultMissionList;
    }

}
