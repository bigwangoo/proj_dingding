package com.tianxiabuyi.txutils.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

    private static final String ALGORITHM = "DESede";

    private static String defaultKey = KeyUtils.getWholeKey();

    public static String encryptStr(String src) {
        return encryptStr(src, defaultKey);
    }

    public static String decryptStr(String src) {
        if (TextUtils.isEmpty(src)) {
            return "";
        }
        return decryptStr(src, defaultKey);
    }

    public static String encryptStr(String src, String key) {
        return new String(b64Encode(encryptMode(purifyKey(key).getBytes(), src.getBytes())));
    }

    public static String decryptStr(String src, String key) {
        try {
            return new String(decryptMode(purifyKey(key).getBytes(), b64Decode(src.getBytes())));
        } catch (NullPointerException e) {
            return src;
        }
    }

    /**
     * 使用 Base64加密
     */
    public static byte[] b64Encode(byte[] b) {
        Base64 base64 = new Base64();
        b = base64.encode(b);
        return b;
    }

    /**
     * 使用 Base64解密
     */
    public static byte[] b64Decode(byte[] b) {
        Base64 base64 = new Base64();
        b = base64.decode(b);
        return b;
    }

    /**
     *
     */
    @SuppressLint("TrulyRandom")
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
            // 解密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param keybyte
     * @param src
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
            // 解密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成十六进制字符串
     *
     * @param b byte[]
     * @return String
     */
    @SuppressLint("DefaultLocale")
    public String byte2hex(byte[] b) {
        String stmp = "";
        StringBuilder hs = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs.append("0").append(stmp);
            } else {
                hs = hs.append(stmp);
            }
            if (n < b.length - 1) {
                hs = hs.append(":");
            }
        }
        return hs.toString().toUpperCase();
    }

    /**
     *
     */
    public static String purifyKey(String key) {
        // "1234567890abcdefghijklmn";
        String keyAddon = defaultKey;
        key = key + "";
        if (key.length() > 24) {
            key = key.substring(0, 24);
        }
        if (key.length() < 24) {
            key = keyAddon.substring(0, (24 - key.length())) + key;
        }
        return key;
    }

}
