package com.bank.backend.service;

import com.bank.backend.common.exception.NotFoundException;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.dto.*;
import com.bank.backend.entity.*;
import com.bank.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final MemoRepository memoRepository;


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
    public OwnerDto.Response certification(OwnerDto.Request request) throws Exception{
        // 식별번호 해싱
        String identification = EncryptionUtils.encryption(request.getIdentificationNumber(), SALT);

        // 식별 번호 조회
        Optional<Owner> owner = ownerRepository.findByIdentificationNumber(identification);

        // 존재하는 경우
        if(owner.isPresent()){
            return OwnerDto.Response.builder()
                    .owner(owner.get())
                    .isPresent(false)
                .build();
        }

        // 존재하지 않음
        return OwnerDto.Response.builder()
                .owner(Owner.builder()
                        .ownerName(request.getOwnerName())
                        .identificationNumber(identification)
                        .build()
                )
                .isPresent(true)
                .build();
    }

    @Override
    public OwnerDto.Response certification(AccountDto.Request request) throws Exception{
        // 식별번호 해싱
        String identification = EncryptionUtils.encryption(request.getIdentificationNumber(), SALT);

        // 식별 번호 조회
        Optional<Owner> owner = ownerRepository.findByIdentificationNumber(identification);

        // 존재하는 경우
        if(owner.isPresent()){
            return OwnerDto.
                    Response.builder()
                    .owner(owner.get())
                    .isPresent(false)
                    .build();
        }

        // 존재하지 않음
        return OwnerDto.Response.builder()
                .isPresent(true)
                .build();
    }
    // 예금주 생성
    @Override
    @Transactional // read-only => create 가능하도록
    public OwnerDto.Response createOwner(OwnerDto.Response response) {
        // 예금주 생성
        ownerRepository.save(response.getOwner());
        return response;
    }

    // 계좌 개설
    @Override
    @Transactional
    public AccountDto.Response createAccount(Owner owner, AccountDto.Request request) throws Exception{
        // 계좌 개수 확인
        boolean creatable = countAccount(owner.getIdentificationNumber());

        // 개수의 초과
        if(!creatable){
            return AccountDto.Response.builder()
                    .msg("생성가능한 계좌의 수가 초과했습니다.")
                    .success(false)
                    .build();
        }

        // 은행 코드 탐색
        BankCode bankCode = bankCodeRepository.findById(request.getBankCode())
                .orElseThrow(() -> new NoSuchElementException("유요하지 않은 은행코드입니다."));

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
                .bankCode(bankCode) // 은행 코드
                .password(request.getPassword())// 계좌 비밀번호
                .owner(owner) // 예금주
                .balance(1_000_000) // 잔액
                .salt(tempSalt) // 암호화 키
                .build();

        // 계좌 저장
        accountRepository.save(account);

        // 완료 반환
        return AccountDto.Response.builder()
                .msg("계좌 생성이 완료되었습니다.")
                .bankName(bankCode.getBankName())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwner().getOwnerName())
                .success(true)
                .build();
    }

    // 예금주의 계좌 개수 파악
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

        // 생성 가능
        return true;
    }

    /** 계좌 거래 내역 조회 */
    @Override
    public List<HistoryDto.Response> getHistoryList(HistoryDto.Request req) throws Exception {

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

        List<HistoryDto.Response> ret = new ArrayList<>();

        for(int i=0; i<history.size(); i++){
            Optional<Memo> memo = memoRepository.findByHistoryId(history.get(i)); // 거래 내역의 메모 조회

            if(memo.isEmpty()){ // 해당 거래 내역에 대한 메모가 없을 때
                ret.add(HistoryDto.Response.toDTO(history.get(i)));
            } else { // 작성 된 메모가 있을 때
                ret.add(HistoryDto.Response.toDTO(history.get(i), memo.get()));
            }
        }

        return ret;
    }

    /** 상세 거래 내역 조회 */
    @Override
    public HistoryDto.Response getDetailHistory(HistoryDto.Request req) throws Exception {

        String identificationNumber = EncryptionUtils.encryption(req.getIdentificationNumber(), SALT);

        // 해당 예금주 탐색
        Owner owner = ownerRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(()-> new Exception("잘못된 식별번호 입니다."));

        // 해당 예금주의 계좌 탐색
        Account account = accountRepository.findByOwnerAndAccountNumber(owner, req.getAccountNumber())
                .orElseThrow(()-> new Exception("회원님의 계좌 정보와 일치하지 않습니다."));

        // 상세 거래 내역 조회
        History detailHistory = historyRepository.findByAccountAndId(account, req.getHistoryId())
                .orElseThrow(()-> new Exception("거래 내역 정보가 없습니다."));

        // 거래 내역의 메모 조회
        Optional<Memo> memo = memoRepository.findByHistoryId(detailHistory);

        // 해당 거래 내역에 대한 메모가 없을 때
        if(memo.isEmpty()){
            return HistoryDto.Response.toDTO(detailHistory);
        }

        // 작성 된 메모가 있을 때
        return HistoryDto.Response.toDTO(detailHistory, memo.get());
    }

    /** 거래 상세 내역 - 메모 작성 */
    @Override
    @Transactional
    public MemoDto.Response writeMemo(MemoDto.Request req) throws Exception {

        String identificationNumber = EncryptionUtils.encryption(req.getIdentificationNumber(), SALT);

        // 해당 예금주 탐색
        Owner owner = ownerRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(()-> new Exception("잘못된 식별번호 입니다."));

        // 해당 예금주의 계좌 탐색
        Account account = accountRepository.findByOwnerAndAccountNumber(owner, req.getAccountNumber())
                .orElseThrow(()-> new Exception("회원님의 계좌 정보와 일치하지 않습니다."));

        // 상세 거래 내역 조회
        History history = historyRepository.findById(req.getHistoryId())
                .orElseThrow(()-> new Exception("거래 내역 정보가 없습니다."));

        Optional<Memo> memo = memoRepository.findByHistoryId(history);

        if(memo.isEmpty()){ // 해당 거래 내역에 대한 메모가 없을 때
            // DB에 메모 저장
            Memo ret = memoRepository.save(
                    MemoDto.Request.saveMemoDto(history, req.getContent())
            );
            
            return MemoDto.Response.toDTO(ret);
        } else { // 작성 된 메모가 있을 때
            memo.get().setContent(req.getContent());
        }

        return MemoDto.Response.toDTO(memo.get());
    }

    // 계좌 실명 조회
    @Override
    public AccountCertificationDto.Response getAccount(AccountCertificationDto.Request request) {
        // 계좌 조회
        // 영서 is good... 영서의 JPA로 해결 했습다.
        Account account = accountRepository.findByBankCode_IdAndAccountNumber(request.getBankCode(), request.getAccountNumber())
                .orElseThrow(() -> new NoSuchElementException("계좌 정보가 존재하지 않습니다."));

        // 조회 완료
        if(request.getBankCode() != account.getBankCode().getId()){

            // 조회 실패
            return AccountCertificationDto.Response.builder()
                    .msg("계좌 정보가 일치하지 않습니다.")
                    .success(false)
                    .build();
        }


        return AccountCertificationDto.Response.builder()
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwner().getOwnerName())
                .bankName(account.getBankCode().getBankName())
                .msg("계좌 조회에 성공했습니다.")
                .success(true)
                .build();
    }

    // 계좌 이체
    @Override
    @Transactional
    public TransferDto.Response transfer(TransferDto.Request request) throws Exception{
        // 송금인 조회(사실은 필요 없을 수도), 혹시 모른 경우 대비해서 실행(계좌는 있는데 예금주가 존재하지 않는 경우) => DB 데이터 손실
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
        Account toAccount = accountRepository.findByBankCode_IdAndAccountNumber(request.getToCode(), request.getToAccount())
                .orElseThrow(() -> new NoSuchElementException("상대방 계좌 정보가 존재하지 않습니다."));

        // 비밀번호 길이 오류
        if(request.getPassword().toString().length() != 4){
            return TransferDto.Response.builder()
                    .msg("비밀번호의 길이가 맞지 않습니다.")
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

                // 계좌 정지
                return TransferDto.Response.builder()
                        .msg("비밀번호 5회 실패로 계좌가 정저됩니다.")
                        .success(false)
                        .build();
            }

            // 비밀번호 오류
            return TransferDto.Response.builder()
                    .msg("비밀번호가 일치하지 않습니다.")
                    .success(false)
                    .build();
        }

        // 거래금액
        Integer transferAmount = request.getTransferAmount();

        // 송금 후 잔액
        Integer sendBalance = sendAccount.getBalance() - transferAmount;

        // 이체 후 잔액
        Integer receiveBalance = toAccount.getBalance() + transferAmount;

        // 잔액 부족
        if(sendBalance < 0){
            return TransferDto.Response.builder()
                    .msg("잔액이 부족합니다.")
                    .success(false)
                    .build();
        }

        if(request.getSign() == ""){
            request.setSign(sendAccount.getOwner().getOwnerName());
        }

        if(request.getToSign() == ""){
            request.setToSign(toAccount.getOwner().getOwnerName());
        }

        // 보내는 사람의 기록
        History send = History.builder()
                .account(sendAccount) // 보내는 사람 계좌 ID
                .toAccount(toAccount.getAccountNumber()) // 받는 사람 계좌 번호
                .toCode(toAccount.getBankCode()) // 받는 사람 계좌 은행 코드
                .type(1) // 1 : 송금
                .transferAmount(transferAmount) // 거래금액
                .afterBalance(sendBalance) // 송금 후 잔액
                .sign(request.getSign()) // 보내는 사람이 보는 메모
                .toSign(request.getToSign()) // 받는 사람이 보는 메모
                .build();

        // 받는 사람의 기록
        History receive = History.builder()
                .account(toAccount) // 받는 사람 계좌 ID
                .toAccount(sendAccount.getAccountNumber()) // 보내는 사람 계좌
                .toCode(sendAccount.getBankCode()) // 보내는 사람 계좌 은행 코드
                .type(2) // 2 : 입금
                .transferAmount(transferAmount) // 거래금액
                .afterBalance(toAccount.getBalance() + transferAmount) // 입금 후 잔액
                .sign(request.getToSign()) // 받는 사람이 보는 메모
                .toSign(request.getSign()) // 보내는 사람이 보는 메모
                .build();

        // 계좌 잔액 변경
        sendAccount.setBalance(sendBalance);
        toAccount.setBalance(receiveBalance);

        // 기록 저장
        historyRepository.save(send);
        historyRepository.save(receive);

        // 비밀번호 오류 횟수 초기화
        sendAccount.setWrongCount(0);

        // 이체 성공
        return TransferDto.Response.builder()
                .msg("이체가 정상적으로 수행되었습니다.")
                .sendOwner(sendAccount.getOwner().getOwnerName())
                .toOwner(toAccount.getOwner().getOwnerName())
                .success(true)
                .build();
    }

    @Override
    @Transactional
    public PasswordDto.Response resetPassword(PasswordDto.Request request)  throws Exception{
        // 식별번호 암호화
        String identification = EncryptionUtils.encryption(request.getIdentificationNumber(), SALT);

        // 식별번호 조회
        Owner owner = ownerRepository.findByIdentificationNumber(identification)
                .orElseThrow(() -> new NoSuchElementException("예금주가 존재하지 않습니다."));

        // 예금주와 계좌번호로 계좌 1개 조회
        Account account = accountRepository.findByOwnerAndAccountNumber(owner, request.getAccountNumber())
                .orElseThrow(() -> new NoSuchElementException("계좌 정보가 존재하지 않습니다."));

        // 새로운 암호화 키
        String salt = EncryptionUtils.makeSalt();

        // 새로운 키를 이용한 암호화
        String password = EncryptionUtils.encryption(request.getNewPassword(), salt);

        // 변경
        account.setPassword(password);
        account.setSalt(salt);

        // 상태 여부에 상관 없이 활성화
        account.setStatus(true);

        // 비밀번호 오류 횟수 초기화
        account.setWrongCount(0);

        // 비밀번호 재설정 완료
        return PasswordDto.Response.builder()
                .msg("비밀번호가 재설정 되었습니다.")
                .success(true)
                .build();
    }
}
