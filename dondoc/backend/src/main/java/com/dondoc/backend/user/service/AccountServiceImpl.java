package com.dondoc.backend.user.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.user.dto.account.AccountListDto;
import com.dondoc.backend.user.dto.account.HistoryDto;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.repository.AccountRepository;
import com.dondoc.backend.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private WebClient webClient = WebClient.create("http://j9d108.p.ssafy.io:9090"); // 은행 서버
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("계좌를 찾을 수 없습니다."));
    }

    @Override
    public AccountListDto.Response loadBankList(Long userId) {
        // 식별번호(핸드폰번호) 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다,"));

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("identificationNumber", List.of(user.getPhoneNumber()));


        Map response = webClient.post()
                .uri("/bank/account/list")
                .bodyValue(requestMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        log.info(response.get("response").toString());

        if(!response.get("success").toString().equals("true")){
            return AccountListDto.Response.builder()
                    .success(false)
                    .msg("계좌 목록 불러올 수 없습니다.")
                    .build();
        }

        return AccountListDto.Response.builder()
                .msg("계좌 목록을 성공적으로 불러왔습니다.")
                .success(true)
                .accountList((List<Account>) response.get("response"))
                .build();
    }

    @Override
    public AccountListDto.Response loadList(Long userId) {
        List<Account> list = accountRepository.findByUserId(userId);

        if(list.isEmpty()){
            throw new NotFoundException("등록된 계좌가 없습니다.");
        }

        for(Account account : list){
            log.info(account.getAccountId().toString());
        }

        return AccountListDto.Response.builder()
                .msg("계좌 목록을 성공적으로 불러왔습니다.")
                .success(true)
                .accountList(list)
                .build();
    }

    @Override
    @Transactional
    public AccountListDto.Response saveList(Long userId, List<AccountListDto.Request> accountList) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        // 삭제할 목록 판별
        List<Account> existAccount = accountRepository.findByUserId(userId);

        // 존재하는 계좌 목록
        for(Account account : existAccount){
            boolean status = false;
            Long accountId = account.getAccountId();

            for(int i = 0; i < accountList.size(); i++){
                if(accountId == accountList.get(i).getAccountId()){
                    status = true;
                    break;
                }
            }

            if(!status){
                log.info(accountId.toString());
                accountRepository.deleteByAccountId(accountId);
            }
        }

        // 요청 계좌 만큼 처리
        for(AccountListDto.Request request : accountList){
            // 있는 경우 패스
            if(accountRepository.findByAccountId(request.getAccountId()).isPresent()){
                continue;
            }

            // Account 객체 생성
            Account account = Account.builder()
                    .bankCode(request.getBankCode())
                    .accountNumber(request.getAccountNumber())
                    .accountId(request.getAccountId())
                    .user(user)
                    .build();

            // 없는 경우 새로 추가
            accountRepository.save(account);
        }

        return AccountListDto.Response.builder()
                .success(true)
                .msg("계좌 등록이 완료되었습니다.")
                .build();
    }

    @Override
    public AccountListDto.Response searchHistory(Long userId, HistoryDto historyDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("identificationNumber", List.of(user.getPhoneNumber()));

        Map response = webClient.post()
                .uri("/bank/account/list")
                .bodyValue(requestMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return null;
    }
}
