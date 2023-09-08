package com.bank.backend.service;

import com.bank.backend.dto.HistoryDto;
import com.bank.backend.entity.History;

import java.util.List;
import java.util.Map;

public interface BankService {

    public boolean certification(String identificationNumber) throws Exception;

    public void createOwner(Map<String, String> info);

    public void createAccount(Map<String, String> info);

    public boolean countAccount(String identification);

    /** 계좌 거래 내역 조회 */
    List<History> getHistoryList(HistoryDto.Request req) throws Exception;

    /** 상세 거래 내역 조회 */
    History getDetailHistory(HistoryDto.Request req) throws Exception;

}
