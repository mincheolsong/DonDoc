package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.repository.MoimRepository;
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
}
