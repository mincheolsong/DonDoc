package com.bank.backend.service;

import com.bank.backend.common.exception.NotFoundException;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.dto.*;
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
    public OwnerCertificationDto.Response certification(OwnerDto.Request request) throws Exception{
        // 식별번호 해싱
        String identification = EncryptionUtils.encryption(request.getIdentificationNumber(), SALT);

        // 식별 번호 조회
        Optional<Owner> owner = ownerRepository.findByIdentificationNumber(identification);

        // 존재하는 경우
        if(owner.isPresent()){
            return OwnerCertificationDto.
                    Response.builder()
                    .owner(owner.get())
                    .isPresent(false)
                .build();
        }

        // 존재하지 않음
        return OwnerCertificationDto.Response.builder()
                .owner(Owner.builder()
                        .ownerName(request.getOwnerName())
                        .identificationNumber(identification)
                        .build()
                )
                .isPresent(true)
                .build();
    }

    @Override
    public OwnerCertificationDto.Response certification(AccountDto.Request request) throws Exception{
        // 식별번호 해싱
        String identification = EncryptionUtils.encryption(request.getIdentificationNumber(), SALT);

        // 식별 번호 조회
        Optional<Owner> owner = ownerRepository.findByIdentificationNumber(identification);

        // 존재하는 경우
        if(owner.isPresent()){
            return OwnerCertificationDto.
                    Response.builder()
                    .owner(owner.get())
                    .isPresent(false)
                    .build();
        }

        // 존재하지 않음
        return OwnerCertificationDto.Response.builder()
                .isPresent(true)
                .build();
    }
    // 예금주 생성
    @Override
    @Transactional // read-only => create 가능하도록
    public void createOwner(OwnerCertificationDto.Response response) {
        ownerRepository.save(response.getOwner());
    }

    // 계좌 생성
    @Override
    @Transactional
    public AccountDto.Response createAccount(Owner owner, AccountDto.Request request) throws Exception{

        boolean creatable = countAccount(owner.getIdentificationNumber());

        // present
        if(!creatable){
            return AccountDto.Response.builder()
                    .msg("생성가능한 계좌의 수가 초과했습니다.")
                    .success(false)
                    .build();
        }

        // 은행 코드 탐색
        Optional<BankCode> bankCode = bankCodeRepository.findById(Long.parseLong(request.getBankCode()));

        // 계좌번호 생성
        String accountNumber = EncryptionUtils.makeAccountNumber();

        // 계좌에 대한 salt 생성
        String tempSalt = EncryptionUtils.makeSalt();

        // 비밀번호 암호화
        request.setPassword(EncryptionUtils.encryption(request.getPassword(), tempSalt));

        // 계좌 객체 생성
        Account account = Account.builder()
                .accountNumber(accountNumber) // 계좌번호
                .accountName(request.getAccountName()) // 계좌 이름
                .bankCode(bankCode.get()) // 은행 코드
                .password(request.getPassword())// 계좌 비밀번호
                .owner(owner) // 예금주
                .balance(1_000_000) // 잔액
                .salt(tempSalt) // 암호화 키
                .build();

        // 계좌 저장
        accountRepository.save(account);
        return AccountDto.Response.builder()
                .msg("계좌 생성이 완료되었습니다.")
                .success(true)
                .build();
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
    public AccountCertificationDto.Response getAccount(AccountCertificationDto.Request request) {
        // 계좌 조회
        Account account = accountRepository.findByBankCode_BankCodeIdAndAccountNumber(request.getBankCode(), request.getAccountNumber())
                .orElseThrow(() -> new NoSuchElementException("계좌 정보가 존재하지 않습니다."));

        // 은행정보 일치하는지 확인 (사실... 쿼리문 bankCode가 적용이 안되길래 일단 계좌로 불러오고 검증하는 방식으로 구현)
        if(request.getBankCode() != account.getBankCode().getBankCodeId()){
            return AccountCertificationDto.Response.builder()
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwner().getOwnerName())
                .bankName(account.getBankCode().getBankName())
                .msg("계좌 조회에 성공했습니다.")
                .success(true)
                .build();
    }


        return AccountCertificationDto.Response.builder()
                .msg("계좌 정보가 일치하지 않습니다.")
                .success(false)
                .build();
    }

    @Override
    @Transactional
    public TransferDto.Response transfer(TransferDto.Request request) throws Exception{

        // 송금인 조회 =>
        Owner owner = ownerRepository.findByIdentificationNumber(EncryptionUtils.encryption(request.getIdentificationNumber(), SALT))
                .orElseThrow(() -> new NoSuchElementException("예금주가 존재하지 않습니다."));

        // 송금인 계좌 ID를 받아옴
        Account sendAccount = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NoSuchElementException("계좌 정보가 존재하지 않습니다."));

        if(!sendAccount.isStatus()){
            return TransferDto.Response.builder()
                    .msg("정지된 계좌입니다.")
                    .success(false)
                    .build();
        }

        // 상대방 계좌 조회
        Account toAccount = accountRepository.findByBankCode_BankCodeIdAndAccountNumber(request.getToCode(), request.getToAccount())
                .orElseThrow(() -> new NoSuchElementException("계좌 정보가 존재하지 않습니다."));

        if(request.getPassword().toString().length() != 4){
            return TransferDto.Response.builder()
                    .msg("비밀번호의 길이가 맞지 않습니다..")
                    .success(false)
                    .build();
        }
        // 해시 생성
        String password = EncryptionUtils.encryption(request.getPassword(), sendAccount.getSalt());

        // 비밀번호 조회
        if(!password.equals(sendAccount.getPassword())){
            // 틀린 횟수 추가
            sendAccount.setWrongCount(sendAccount.getWrongCount() + 1);

            // 계좌 상태 확인
            if(sendAccount.getWrongCount() >= 5){
                sendAccount.setStatus(false);

                return TransferDto.Response.builder()
                        .msg("비밀번호 5회 실패로 계좌가 정저됩니다.")
                        .success(false)
                        .build();
            }

            return TransferDto.Response.builder()
                    .msg("비밀번호가 일치하지 않습니다.")
                    .success(false)
                    .build();
        }

        // 거래금액
        Integer transferAmount = request.getTransferAmount();

        // 송금 잔액
        Integer sendBalance = sendAccount.getBalance() - transferAmount;
        Integer receiveBalance = toAccount.getBalance() + transferAmount;

        // 잔액 부족
        if(sendBalance < 0){
            return TransferDto.Response.builder()
                    .msg("잔액이 부족합니다.")
                    .success(false)
                    .build();
        }

        History send = History.builder()
                .account(sendAccount) // 보내는 사람 계좌 ID
                .toAccount(toAccount.getAccountNumber()) // 받는 사람 계좌 번호
                .toCode(toAccount.getBankCode()) // 받는 사람 계좌 은행 코드
                .type(1) // 1 : 송금
                .transferAmount(transferAmount) // 거래금액
                .afterBalance(sendBalance) // 송금 후 잔액
                .memo(request.getMemo()) // 보내는 사람이 보는 메모
                .toMemo(request.getToMemo()) // 받는 사람이 보는 메모
                .build();

        History receive = History.builder()
                .account(toAccount) // 받는 사람 계좌 ID
                .toAccount(sendAccount.getAccountNumber()) // 보내는 사람 계좌
                .toCode(sendAccount.getBankCode()) // 보내는 사람 계좌 은행 코드
                .type(2) // 2 : 입금
                .transferAmount(transferAmount) // 거래금액
                .afterBalance(toAccount.getBalance() + transferAmount) // 입금 후 잔액
                .memo(request.getToMemo()) // 받는 사람이 보는 메모
                .toMemo(request.getMemo()) // 보내는 사람이 보는 메모
                .build();

        // 계좌 잔액 변경
        sendAccount.setBalance(sendBalance);
        toAccount.setBalance(receiveBalance);

        // 수행
        historyRepository.save(send);
        historyRepository.save(receive);

        // 비밀번호 오류 횟수 초기화
        sendAccount.setWrongCount(0);

        return TransferDto.Response.builder()
                .msg("이체가 정상적으로 수행되었습니다.")
                .success(true)
                .build();
    }

    public boolean checkPassword(String password, String comparePassword){
        if(!password.equals(comparePassword)){
            return false;
        }
        return true;
    }
}
