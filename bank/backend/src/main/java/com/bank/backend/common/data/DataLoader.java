package com.bank.backend.common.data;

import com.bank.backend.entity.BankCode;
import com.bank.backend.repository.BankCodeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BankCodeRepository bankCodeRepository;

    public DataLoader(BankCodeRepository bankCodeRepository) {
        this.bankCodeRepository = bankCodeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // 경남은행
        bankCodeRepository.save(new BankCode(39, "경남은행"));
        // 광주은행
        bankCodeRepository.save(new BankCode(34, "광주은행"));
        // 지역농축협
        bankCodeRepository.save(new BankCode(12, "지역농축협"));
        // 부산은행
        bankCodeRepository.save(new BankCode(32, "부산은행"));
        // 새마을금고
        bankCodeRepository.save(new BankCode(45, "새마을금고"));
        // 산림조합
        bankCodeRepository.save(new BankCode(64, "산림조합"));
        // 신한은행
        bankCodeRepository.save(new BankCode(88, "신한은행"));
        // 신협
        bankCodeRepository.save(new BankCode(48, "신협"));
        // 씨티은행
        bankCodeRepository.save(new BankCode(27, "씨티은행"));
        // 우리은행
        bankCodeRepository.save(new BankCode(20, "우리은행"));
        // 우체국예금보험
        bankCodeRepository.save(new BankCode(71, "우체국예금보험"));
        // 저축은행중앙회
        bankCodeRepository.save(new BankCode(50, "저축은행중앙회"));
        // 전북은행
        bankCodeRepository.save(new BankCode(50, "전북은행"));
        // 제주은행
        bankCodeRepository.save(new BankCode(35, "제주은행"));
        // 카카오뱅크
        bankCodeRepository.save(new BankCode(90, "카카오뱅크"));
        // 케이뱅크
        bankCodeRepository.save(new BankCode(89, "케이뱅크"));
        // 토스뱅크
        bankCodeRepository.save(new BankCode(92, "토스뱅크"));
        // 하나은행
        bankCodeRepository.save(new BankCode(81, "하나은행"));
        // IBK기업은행
        bankCodeRepository.save(new BankCode(003, "IBK기업은행"));
        // KB국민은행
        bankCodeRepository.save(new BankCode(004, "KB국민은행"));
        // DGB대구은행
        bankCodeRepository.save(new BankCode(31, "DGB대구은행"));
        // KDB산업은행
        bankCodeRepository.save(new BankCode(2, "KDB산업은행"));
        // NH농협은행
        bankCodeRepository.save(new BankCode(11, "NH농협은행"));
        // SC제일은행
        bankCodeRepository.save(new BankCode(23, "SC제일은행"));
        // Sh수협은행
        bankCodeRepository.save(new BankCode(7, "Sh수협은행"));
    }
}