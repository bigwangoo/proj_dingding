package com.tianxiabuyi.txutils.util;

import android.text.TextUtils;

import java.security.MessageDigest;

public class MD5EncryptUtils {

    public static String encryptMD5(String plainText) {
        String md5 = "";

        if (TextUtils.isEmpty(plainText)) {
            return md5;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] bytes = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte b : bytes) {
                i = b;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            md5 = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return md5;
    }

}
