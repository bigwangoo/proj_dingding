package com.tianxiabuyi.txutils.util;

import android.text.TextUtils;

import com.tianxiabuyi.txutils.BuildConfig;

/**
 * 生成加密key
 *
 * @author xjh1994
 * @date 2016/8/17
 */
public class KeyUtils {

    static {
        System.loadLibrary("KeyUtils");
    }

    public static native String something();

    public static String getKey() {
        return "&&";
    }

    public static String getString() {
        String string = "么久乁乌乔么";
        char[] array = string.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] ^ 20000);
        }
        return new String(array);
    }

    public static String getWholeKey() {
        return TextUtils.concat(BuildConfig.appKey,
                KeyUtils.something(),
                KeyUtils.getKey(),
                BuildConfig.appKey,
                KeyUtils.getString()).toString();
    }
}
