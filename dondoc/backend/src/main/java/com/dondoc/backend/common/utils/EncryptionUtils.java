package com.dondoc.backend.common.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
public class EncryptionUtils {

    private static final int SALT_SIZE = 16;
    public String encryption(String str, String salt) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        for(int i = 0; i < 100; i++){
            String temp = str + salt;
            md.update(temp.getBytes());
            str = byteToString(md.digest());
        }

        // 해싱 처리 알고리즘
        return str;
    }

    public String byteToString(byte[] temp){
        StringBuilder sb = new StringBuilder();
        for(byte a : temp){
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }

    public String makeSalt() {
        SecureRandom random = new SecureRandom();

        byte[] temp = new byte[SALT_SIZE];
        random.nextBytes(temp);

        return byteToString(temp);
    }

    public String makeAccountNumber() {
        SecureRandom random = new SecureRandom();

        long randomNumber = random.nextLong();
        String accountNumber = String.valueOf(randomNumber).substring(2,15);
        return accountNumber;
    }

    public static String makeIdentificationNumber(){
        SecureRandom random = new SecureRandom();

        long randomNumber = random.nextLong();
        String accountNumber = String.valueOf(randomNumber).substring(2,13);
        return accountNumber;
    }

    public String certificationNumber(){
        SecureRandom random = new SecureRandom();

        long randomNumber = random.nextLong();

        String certificationNumber = String.valueOf(randomNumber).substring(1,5);
        return certificationNumber;
    }
}
