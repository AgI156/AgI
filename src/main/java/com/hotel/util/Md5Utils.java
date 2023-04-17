package com.hotel.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Utils {
    public static String encrypt(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);

        if (md5Str.length() < 32) {
            int cnt = 32 - md5Str.length();
            for (int i = 0; i < cnt; i++) {
                md5Str = "0" + md5Str;
            }
        }

        return md5Str;
    }
}
