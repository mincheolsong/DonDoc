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
        bankCodeRepository.save(new BankCode(39L, "경남은행"));
        // 광주은행
        bankCodeRepository.save(new BankCode(34L, "광주은행"));
        // 지역농축협
        bankCodeRepository.save(new BankCode(12L, "지역농축협"));
        // 부산은행
        bankCodeRepository.save(new BankCode(32L, "부산은행"));
        // 새마을금고
        bankCodeRepository.save(new BankCode(45L, "새마을금고"));
        // 산림조합
        bankCodeRepository.save(new BankCode(64L, "산림조합"));
        // 신한은행
        bankCodeRepository.save(new BankCode(88L, "신한은행"));
        // 신협
        bankCodeRepository.save(new BankCode(48L, "신협"));
        // 씨티은행
        bankCodeRepository.save(new BankCode(27L, "씨티은행"));
        // 우리은행
        bankCodeRepository.save(new BankCode(20L, "우리은행"));
        // 우체국예금보험
        bankCodeRepository.save(new BankCode(71L, "우체국예금보험"));
        // 저축은행중앙회
        bankCodeRepository.save(new BankCode(50L, "저축은행중앙회"));
        // 전북은행
        bankCodeRepository.save(new BankCode(37L, "전북은행"));
        // 제주은행
        bankCodeRepository.save(new BankCode(35L, "제주은행"));
        // 카카오뱅크
        bankCodeRepository.save(new BankCode(90L, "카카오뱅크"));
        // 케이뱅크
        bankCodeRepository.save(new BankCode(89L, "케이뱅크"));
        // 토스뱅크
        bankCodeRepository.save(new BankCode(92L, "토스뱅크"));
        // 하나은행
        bankCodeRepository.save(new BankCode(81L, "하나은행"));
        // IBK기업은행
        bankCodeRepository.save(new BankCode(3L, "IBK기업은행"));
        // KB국민은행
        bankCodeRepository.save(new BankCode(4L, "KB국민은행"));
        // DGB대구은행
        bankCodeRepository.save(new BankCode(31L, "DGB대구은행"));
        // KDB산업은행
        bankCodeRepository.save(new BankCode(2L, "KDB산업은행"));
        // NH농협은행
        bankCodeRepository.save(new BankCode(11L, "NH농협은행"));
        // SC제일은행
        bankCodeRepository.save(new BankCode(23L, "SC제일은행"));
        // Sh수협은행
        bankCodeRepository.save(new BankCode(7L, "Sh수협은행"));
    }
}