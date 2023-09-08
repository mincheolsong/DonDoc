package com.bank.backend.service;


import java.util.Map;

public interface BankService {

    public boolean certification(String identificationNumber) throws Exception;

    public void createOwner(Map<String, String> info);

    public void createAccount(Map<String, String> info);

    public boolean countAccount(String identification);
}
