package com.bank.backend.common.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class EncryptionUtils {

    private static final int SALT_SIZE = 32;
    public static String encryption(String str, String salt) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        for(int i = 0; i < 100; i++){
            String temp = str + salt;
            md.update(temp.getBytes());
            str = byteToString(md.digest());
        }

        // 해싱 처리 알고리즘
        return str;
    }

    public static String byteToString(byte[] temp){
        StringBuilder sb = new StringBuilder();
        for(byte a : temp){
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }

    public static String makeSalt() {
        SecureRandom random = new SecureRandom();

        byte[] temp = new byte[SALT_SIZE];
        random.nextBytes(temp);

        return byteToString(temp);
    }
}
