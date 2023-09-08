package com.bank.backend.service;

import com.bank.backend.common.exception.NotFoundException;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.dto.*;
import com.bank.backend.entity.Account;
import com.bank.backend.entity.BankCode;
import com.bank.backend.entity.Account;
import com.bank.backend.entity.BankCode;
import com.bank.backend.entity.History;
import com.bank.backend.entity.Owner;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.BankCodeRepository;
import com.bank.backend.repository.HistoryRepository;
import com.bank.backend.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankServiceImpl implements BankService {

    @Value("${owner.salt}")
    private String SALT;

    private final AccountRepository accountRepository;
    private final BankCodeRepository bankCodeRepository;
    private final HistoryRepository historyRepository;
    private final OwnerRepository ownerRepository;


    @Override
    public int findAccountList(List<AccountListDto.Response> result, String number) throws Exception{
        // number 해싱 후 조회
        String hashed_number = EncryptionUtils.encryption(number,SALT);

        Owner owner = ownerRepository.findOwnerWithAccount(hashed_number)
                .orElseThrow(()->new NotFoundException(number + " 에 해당하는 계좌정보가 존재하지 않습니다"));

        int cnt = 0;

        for (Account account : owner.getAccountList()) {
            AccountListDto.Response tmp = AccountListDto.Response.toDTO(account);
            result.add(tmp);
            cnt+=1;
        }

        return cnt; // 조회한 계좌목록 갯수 리턴

    }
    @Override
    public AccountDetailDto.Response findByAccountId(Long accountId) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(accountId + "를 accountId로 가지는 계좌가 존재하지 않습니다"));

        return AccountDetailDto.Response.toDTO(account);
    }

    // 식별번호의 존재 여부만 파악하는 메서드
    @Override
    public boolean certification(String identificationNumber) {
        // 식별 번호 조회
        Optional<Owner> owner = ownerRepository.findByIdentificationNumber(identificationNumber);

        // 존재하는 경우
        if(owner.isPresent()){return false;}

        // 존재하지 않음
        return true;
    }

    // 예금주 생성
    @Override
    @Transactional // read-only => create 가능하도록
    public void createOwner(Map<String, String> info) {
        Owner owner = Owner.builder()
                .ownerName(info.get("ownerName"))
                .identificationNumber(info.get("identificationNumber"))
                .build();
        ownerRepository.save(owner);
    }

    // 계좌 생성
    @Override
    @Transactional
    public void createAccount(Map<String, String> info) {
        // 은행 코드 탐색
        Optional<BankCode> bankCode = bankCodeRepository.findById(Long.parseLong(info.get("bankCode")));

        // 예금주 탐색
        Optional<Owner> owner = ownerRepository.findByIdentificationNumber(info.get("identificationNumber"));

        // 계좌번호 생성
        String accountNumber = EncryptionUtils.makeAccountNumber();

        // 계좌 객체 생성
        Account account = Account.builder()
                .accountNumber(accountNumber) // 계좌번호
                .accountName(info.get("accountName")) // 계좌 이름
                .bankCode(bankCode.get()) // 은행 코드
                .password(info.get("password"))// 계좌 비밀번호
                .owner(owner.get()) // 예금주
                .balance(1_000_000) // 잔액
                .salt(info.get("salt")) // 암호화 키
                .build();

        // 계좌 저장
        accountRepository.save(account);
    }

    @Override
    public boolean countAccount(String identification) {
        // 예금주 조회
        Owner owner = ownerRepository.findByIdentificationNumber(identification)
                .orElseThrow(() -> new NoSuchElementException("예금주가 존재하지 않습니다."));
        // 계좌 목록 조회
        List<Account> accountList = accountRepository.findAllByOwner(owner);

        // 계좌 개수 제한
        if(accountList.size() >= 5){
            return false;
        }

        return true;
    }

    /** 계좌 거래 내역 조회 */
    @Override
    public List<History> getHistoryList(HistoryDto.Request req) throws Exception {

        log.info("Req - {}", req.toString());

        String identificationNumber = EncryptionUtils.encryption(req.getIdentificationNumber(), SALT);

        // 해당 예금주 탐색
        Owner owner = ownerRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(()-> new Exception("잘못된 식별번호 입니다."));

        // 해당 예금주의 계좌 탐색
        Account account = accountRepository.findByOwnerAndAccountNumber(owner, req.getAccountNumber())
                .orElseThrow(()-> new Exception("회원님의 계좌 정보와 일치하지 않습니다."));

        // 계좌별 거래 내역 조회
        List<History> history = historyRepository.findByAccount(account);

        return history;
    }

    /** 상세 거래 내역 조회 */
    @Override
    public History getDetailHistory(HistoryDto.Request req) throws Exception {

        String identificationNumber = EncryptionUtils.encryption(req.getIdentificationNumber(), SALT);

        // 해당 예금주 탐색
        Owner owner = ownerRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(()-> new Exception("잘못된 식별번호 입니다."));

        // 해당 예금주의 계좌 탐색
        Account account = accountRepository.findByOwnerAndAccountNumber(owner, req.getAccountNumber())
                .orElseThrow(()-> new Exception("회원님의 계좌 정보와 일치하지 않습니다."));

        // 상세 거래 내역 조회
        History detailHistory = historyRepository.findByAccountAndHistoryId(account, req.getHistoryId())
                .orElseThrow(()-> new Exception("거래 내역 정보가 없습니다."));

        return detailHistory;
    }
    @Override
    public AccountDto getAccount(Map<String, String> info) {
        Account account = accountRepository.findByAccount(info.get("accountNumber"))
                .orElseThrow(() -> new NoSuchElementException("계좌번호가 존재하지 않습니다."));

        // 은행정보 일치하는지 확인 (사실... 쿼리문 bankCode가 적용이 안되길래 일단 계좌로 불러오고 검증하는 방식으로 구현)
        if(!info.get("bankCode").equals(account.getBankCode().getBankCodeId().toString())){
            return AccountDto.builder()
                    .msg("계좌 정보가 일치하지 않습니다.")
                    .success(false)
                    .build();
        }

        AccountDto accountDto = AccountDto.builder()
                .account(account.getAccountNumber())
                .ownerName(account.getOwner().getOwnerName())
                .bankCode(account.getBankCode().getBankCodeId())
                .success(true)
                .msg("계좌 조회에 성공했습니다.")
                .build();
        return accountDto;
    }

    @Override
    @Transactional
    public TransferDto transfer(Map<String, String> info) {
        // 송금인 조회 =>
        Owner owner = ownerRepository.findByIdentificationNumber(info.get("identificationNumber"))
                .orElseThrow(() -> new NoSuchElementException("예금주가 존재하지 않습니다."));



        // 송금인 계좌 ID를 받아옴
        Account sendAccount = accountRepository.findById(Long.parseLong(info.get("accountId")))
                .orElseThrow(() -> new NoSuchElementException("계좌 정보가 존재하지 않습니다."));

        // 상대방 계좌 조회
        Account toAccount = accountRepository.findByAccount(info.get("toAccount"))
                .orElseThrow(() -> new NoSuchElementException("계좌 정보가 존재하지 않습니다."));

        // 은행 코드 조회
        BankCode toCode = bankCodeRepository.findById(Long.parseLong(info.get("toCode")))
                .orElseThrow(() -> new NoSuchElementException("은행이 존재하지 않습니다."));

        // 거래금액
        Integer transferAmount = Integer.parseInt(info.get("transferAmount"));

        // 송금 잔액
        Integer sendBalance = sendAccount.getBalance() - transferAmount;
        Integer receiveBalance = toAccount.getBalance() + transferAmount;

        // 잔액 부족
        if(sendBalance < 0){
            return TransferDto.builder()
                    .msg("잔액이 부족합니다.")
                    .success(false)
                    .build();
        }

        History send = History.builder()
                .account(sendAccount) // 보내는 사람 계좌 ID
                .toAccount(toAccount.getAccountNumber()) // 받는 사람 계좌 번호
                .toCode(toCode) // 받는 사람 계좌 은행 코드
                .type(1) // 1 : 송금
                .transferAmount(transferAmount) // 거래금액
                .afterBalance(sendBalance) // 송금 후 잔액
                .memo(info.get("memo")) // 보내는 사람이 보는 메모
                .toMemo(info.get("toMemo")) // 받는 사람이 보는 메모
                .build();

        History receive = History.builder()
                .account(toAccount) // 받는 사람 계좌 ID
                .toAccount(sendAccount.getAccountNumber()) // 보내는 사람 계좌
                .toCode(sendAccount.getBankCode()) // 보내는 사람 계좌 은행 코드
                .type(2) // 2 : 입금
                .transferAmount(transferAmount) // 거래금액
                .afterBalance(toAccount.getBalance() + transferAmount) // 입금 후 잔액
                .memo(info.get("toMemo")) // 받는 사람이 보는 메모
                .toMemo(info.get("memo")) // 보내는 사람이 보는 메모
                .build();

        // 계좌 잔액 변경
        sendAccount.setBalance(sendBalance);
        toAccount.setBalance(receiveBalance);

        // 수행
        historyRepository.save(send);
        historyRepository.save(receive);

        return TransferDto.builder()
                .msg("이체가 정상적으로 수행되었습니다.")
                .success(true)
                .build();
    }
}
