package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.moim.dto.WithdrawRequestDto;
import com.dondoc.backend.moim.entity.Category;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import com.dondoc.backend.moim.repository.CategoryRepository;
import com.dondoc.backend.moim.repository.MoimMemberRepository;
import com.dondoc.backend.moim.repository.MoimRepository;
import com.dondoc.backend.moim.repository.WithdrawRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

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

    /** 관리자에게 돈 요청 */
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
}
