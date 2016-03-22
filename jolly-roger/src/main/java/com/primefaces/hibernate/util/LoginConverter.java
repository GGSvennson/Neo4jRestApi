package com.primefaces.hibernate.util;

import java.io.UnsupportedEncodingException;
import java.security.*;

public class LoginConverter {
    
    public LoginConverter() {
    }
    
    public static String hash256(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes("UTF-8"));
        return bytesToHex(md.digest());
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
