package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.EncryptionUtils;
import com.dondoc.backend.moim.dto.MoimDetailDto;
import com.dondoc.backend.moim.dto.MoimListDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import com.dondoc.backend.moim.repository.MoimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoimServiceImpl implements MoimService{

    private final MoimMemberService moimMemberService;
    private final MoimRepository moimRepository;
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
    public List<Moim> getMoimList(String userId) {
        List<Moim> result = moimRepository.getMoimList(userId);

        return result;
    }


    @Override
    public MoimDetailDto.Response getMoimDetail(String userId, Long moimId) throws Exception {

        try {
            MoimMember moimMember = moimMemberService.findMoimMember(userId, moimId); // userType 검사 (Exception 던질 수 있음)
            int type = moimMember.getUserType(); // userType
            int status = moimMember.getStatus(); // 초대 승인 여부

            /**
             * Fetch Join 수행
             * MoimMember (컬렉션 fetch join)
             * MoimMember의 Account
             * MoimMember의 User
             */
            List<Moim> moims = this.findMoimWithMember(moimId);

            if(moims.size()!=1) throw new RuntimeException("/detail/{moimId} 실패"); // Moim이 하나가 아니면 에러

            Moim moim = moims.get(0);

            /** 관리자인 경우 && 초대 승인 **/
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

            }else if(type==1 && status==1){ /**일반 사용자의 경우 && 초대 승인**/
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
    @Override
    public Moim findById(Long id) throws Exception{
        Moim moim = moimRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당하는 모임이 존재하지 않습니다"));
        return moim;
    }
}
