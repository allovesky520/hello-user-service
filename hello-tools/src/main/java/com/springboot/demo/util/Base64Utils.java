package com.springboot.demo.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Base64Utils {
    private static final Logger log = LoggerFactory.getLogger(Base64Utils.class);

    public Base64Utils() {
    }

    public static byte[] base64toByteArr(String imgStr) {
        if (imgStr == null) {
            return new byte[0];
        } else {
            try {
                byte[] byteArr = Base64.decodeBase64(imgStr);

                for(int i = 0; i < byteArr.length; ++i) {
                    if (byteArr[i] < 0) {
                        byteArr[i] = (byte)(byteArr[i] + 256);
                    }
                }

                return byteArr;
            } catch (Exception var3) {
                log.error("base64字符串转化成byte数组出错:" + var3.getLocalizedMessage());
                return null;
            }
        }
    }

    public static void base64ToFile(String base64Str, String newImgPath, String newImgName, String suffix) {
    }

    public static String encode(String source) {
        return new String(Base64.encodeBase64(source.getBytes()));
    }

    public static String encode(byte[] source) {
        return new String(Base64.encodeBase64(source));
    }

    public static String decode(String source) {
        return new String(Base64.decodeBase64(source.getBytes()));
    }

    public static String decode(byte[] source) {
        return new String(Base64.decodeBase64(source));
    }
}
