package com.dondoc.backend.user.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.user.dto.account.*;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.repository.AccountRepository;
import com.dondoc.backend.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private WebClient webClient = WebClient.create("http://j9d108.p.ssafy.io:9090"); // 은행 서버

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        List<Account> byAccountNumber = accountRepository.findByAccountNumber(accountNumber);
        if(byAccountNumber.size()!=1){
            throw new NotFoundException("계좌를 찾을 수 없습니다.");
        }
        return byAccountNumber.get(0);
    }

    @Override
    public Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("계좌를 찾을 수 없습니다."));
    }

    @Override
    public AccountListDto.BankResponse loadBankList(Long userId) {
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

        if (!response.get("success").toString().equals("true")) {
            return AccountListDto.BankResponse.builder()
                    .success(false)
                    .msg("계좌 목록 불러올 수 없습니다.")
                    .build();
        }


        return AccountListDto.BankResponse.builder()
                .msg("계좌 목록을 성공적으로 불러왔습니다.")
                .success(true)
                .accountList((List<Account>) response.get("response"))
                .build();
    }

    // 계좌 목록 불러오기
    @Override
    public AccountListDto.Response loadList(Long userId) {
        List<Account> list = accountRepository.findByUserId(userId);

        if (list.isEmpty()) {
            throw new NotFoundException("등록된 계좌가 없습니다.");
        }

        List<AccountDetailDto.AccountDetail> result = new ArrayList<>();

        for(Account account : list){
            AccountDetailDto.Response response = accountDetail(account.getAccountId());

            result.add(response.getAccountDetail());
        }

        return AccountListDto.Response.builder()
                .msg("계좌 목록을 성공적으로 불러왔습니다.")
                .success(true)
                .accountList(result)
                .build();
    }

    // 사용할 계좌 저장
    @Override
    @Transactional
    public AccountListDto.Response saveList(Long userId, List<AccountListDto.Request> accountList) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        for (AccountListDto.Request compare : accountList) {
            // 해당 계좌 ID가 맞는지 확인
            Map response = webClient.get()
                    .uri("/bank/account/detail/" + compare.getAccountId())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            try {
                Map<String, String> res = (Map<String, String>) response.get("response");
                String account = res.get("accountNumber");

                if (!account.equals(compare.getAccountNumber())) {
                    throw new NoSuchElementException("계좌 정보가 잘못 되었습니다.");
                }

            } catch (NotFoundException e) {
                throw new NoSuchElementException("계좌 정보가 잘못 되었습니다.");
            }
        }


        // 삭제할 목록 판별
        List<Account> existAccount = accountRepository.findByUserId(userId);

        // 존재하는 계좌 목록
        for (Account account : existAccount) {
            boolean status = false;
            Long accountId = account.getAccountId();

            for (int i = 0; i < accountList.size(); i++) {
                if (accountId == accountList.get(i).getAccountId()) {
                    status = true;
                    break;
                }
            }

            if (!status) {
                accountRepository.deleteByAccountId(accountId);
            }
        }

        // 요청 계좌 만큼 처리
        for (AccountListDto.Request request : accountList) {
            // 있는 경우 패스
            if (accountRepository.findByAccountId(request.getAccountId()).isPresent()) {
                continue;
            }

            // Account 객체 생성
            Account account = Account.builder()
                    .bankName(request.getBankName())
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
    public HistoryDto.Response searchHistory(Long userId, String accountNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("identificationNumber", user.getPhoneNumber());
        requestMap.put("accountNumber", accountNumber);

        Map response = webClient.post()
                .uri("/bank/history")
                .bodyValue(requestMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return HistoryDto.Response.builder()
                .historyList(response)
                .build();
    }

    @Override
    public AccountDto.Response setAccount(Long userId, Long accountId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NotFoundException("계좌 정보를 찾을 수 없습니다."));

        if (account.getUser().equals(user)) {
            user.setMainAccount(accountId);
        }else{
            throw new NoSuchElementException("유저가 일치하지 않습니다.");
        }


        userRepository.save(user);

        return AccountDto.Response.builder()
                .success(true)
                .msg("대표계좌를 설정하였습니다.")
                .build();
    }

    @Override
    public AccountDetailDto.Response accountDetail(Long accountId) {

        Map response = webClient.get()
                .uri("/bank/account/detail/" + accountId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(!(boolean)response.get("success")){
            throw new NotFoundException("계좌 정보를 찾을 수 없습니다.");
        }

        Map<String, Object> map = (Map<String, Object>)response.get("response");


        Map account = (Map<String, String>) response.get("response");

        AccountDetailDto.AccountDetail accountDetail = AccountDetailDto.AccountDetail.builder()
                .accountId(Long.parseLong(((Integer) map.get("accountId")).toString()))
                .accountName((String)account.get("accountName"))
                .accountNumber((String)account.get("accountNumber"))
                .balance((Integer)account.get("balance"))
                .bankCode(Long.parseLong(((Integer) map.get("bankCode")).toString()))
                .bankName((String)account.get("bankName"))
                .build();

        return AccountDetailDto.Response.builder()
                .success(true)
                .msg("계좌 상세 조회에 성공했습니다.")
                .accountDetail(accountDetail)
                .build();
    }

    @Override
    public HistoryDetailDto.Response historyDetail(Long userId, HistoryDetailDto.Request historyDto) {
        // 유저 탐색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));



        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("identificationNumber", user.getPhoneNumber());
        requestMap.put("accountNumber", historyDto.getAccountNumber());
        requestMap.put("historyId", historyDto.getHistoryId());

        // 요청 전송
        Map response = (Map<String, String>)(webClient.post()
                .uri("/bank/detail_history")
                .bodyValue(requestMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block()
                .get("response"));

        Map<String, String> result = (Map<String, String>)response.get("historyId");

        HistoryDetailDto.HistoryDetail historyDetail = HistoryDetailDto.HistoryDetail.builder()
                .id(Long.parseLong(String.valueOf(result.get("id"))))
                .toAccount(result.get("toAcocunt"))
                .toCode(Long.parseLong(String.valueOf(((Map<String, String>)response.get("toCode")).get("bankCodeId"))))
                .toBankName(((Map<String, String>)response.get("toCode")).get("bankName"))
                .type(Integer.parseInt(String.valueOf(result.get("type"))))
                .transferAmount(Integer.parseInt(String.valueOf(result.get("transferAmount"))))
                .afterBalance(Integer.parseInt(String.valueOf(result.get("afterBalance"))))
                .sign(result.get("sign"))
                .toSign(result.get("toSign"))
                .createdAt(LocalDateTime.parse(result.get("createdAt")))
                .build();

        return HistoryDetailDto.Response.builder()
                .success(true)
                .msg("성공적으로 상세 거래내역을 불러왔습니다.")
                .historyDetail(historyDetail)
                .build();
    }

    // 거래내역 메모 작성
    @Override
    public HistoryMemoDto.Response historyMemo(Long userId, HistoryMemoDto.Request memo) {
        // 유저 탐색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("identificationNumber", user.getPhoneNumber());
        requestMap.put("content", memo.getContent());
        requestMap.put("historyId", memo.getHistoryId());
        requestMap.put("accountNumber", memo.getAccountNumber());

        Map response = webClient.post()
                .uri("/bank/detail_history/memo")
                .bodyValue(requestMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(!(boolean)response.get("success")){
            throw new NoSuchElementException("메모 작성을 실패했습니다.");
        }

        Map<String, String> result = (Map<String, String>)response.get("response");

        return HistoryMemoDto.Response.builder()
                .msg("메모 작성을 완료하였습니다.")
                .success(true)
                .result(result)
                .build();
    }

    @Override
    public TransferDto.Response transferAmount(Long userId, TransferDto.Request request) {
        // 유저 탐색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("identificationNumber", user.getPhoneNumber());
        requestMap.put("accountId", request.getAccountId());
        requestMap.put("password", request.getPassword());
        requestMap.put("sign", request.getSign());
        requestMap.put("toAccount", request.getToAccount());
        requestMap.put("toCode", request.getToCode());
        requestMap.put("toSign", request.getToSign());
        requestMap.put("transferAmount", request.getTransferAmount());

        Map response = webClient.post()
                .uri("/bank/account/transfer")
                .bodyValue(requestMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if(!(boolean)response.get("success")){
            throw new NoSuchElementException("송금에 실패했습니다.");
        }

        return TransferDto.Response.builder()
                .msg((String)response.get("response"))
                .success(true)
                .build();
    }

    @Override
    public Account findByAccountId(Long accountId){
        Optional<Account> byAccountId = accountRepository.findByAccountId(accountId);
        if(byAccountId.isPresent()){
            return byAccountId.get();
        }
        return null;

    }

    @Override
    public AccountCertificationDto.Response certificationAccount(String accountNumber, Long bankCode) {
        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("accountNumber", accountNumber);
        requestMap.put("bankCode", bankCode);

        Map response = (Map<String, String>)webClient.post()
                .uri("/bank/account/certification")
                .bodyValue(requestMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block()
                .get("response");

        if(response == null){
            throw new NotFoundException("계좌를 찾을 수 없습니다.");
        }

        return AccountCertificationDto.Response.builder()
                .msg("계좌 조회에 성공했습니다.")
                .success(true)
                .response(response)
                .build();
    }
}
