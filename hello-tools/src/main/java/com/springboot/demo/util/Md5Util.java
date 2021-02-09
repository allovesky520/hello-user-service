package com.springboot.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public Md5Util() {
    }

    public static final String encryptMD5(String source) {
        if (source == null) {
            source = "";
        }

        String result = "";

        try {
            result = encrypt(source, "MD5");
            return result;
        } catch (NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static final String encryptSHA(String source) {
        if (source == null) {
            source = "";
        }

        String result = "";

        try {
            result = encrypt(source, "SHA");
            return result;
        } catch (NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3);
        }
    }

    private static final String encrypt(String source, String algorithm) throws NoSuchAlgorithmException {
        byte[] resByteArray = encrypt(source.getBytes(), algorithm);
        return toHexString(resByteArray);
    }

    private static final byte[] encrypt(byte[] source, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.reset();
        md.update(source);
        return md.digest();
    }

    private static final String toHexString(byte[] res) {
        StringBuilder sb = new StringBuilder(res.length << 1);

        for(int i = 0; i < res.length; ++i) {
            String digit = Integer.toHexString(255 & res[i]);
            if (digit.length() == 1) {
                digit = '0' + digit;
            }

            sb.append(digit);
        }

        return sb.toString().toUpperCase();
    }
}
