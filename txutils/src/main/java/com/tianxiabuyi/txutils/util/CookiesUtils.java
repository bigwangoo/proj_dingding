package com.tianxiabuyi.txutils.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 持久化Cookies
 *
 * @author wangyd
 * @date 2018/8/23
 */
public class CookiesUtils {
    private static final String FILE_NAME = "tx_cookies";

    private static SharedPreferences sp;
    private static CookiesUtils instance;

    private CookiesUtils() {
        sp = TxUtils.getInstance().getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static CookiesUtils getInstance() {
        if (instance == null) {
            synchronized (CookiesUtils.class) {
                if (instance == null) {
                    instance = new CookiesUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 保存 Cookies
     */
    public void saveCookie(String url, String domain, List<String> cookies) {
        if (cookies == null || cookies.isEmpty()) {
            return;
        }

        // 判重
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] split = cookie.split(";");
            for (String s : split) {
                if (set.contains(s)) {
                    continue;
                }
                set.add(s);
            }
        }

        SharedPreferences.Editor editor = sp.edit();
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null.");
        } else {
            editor.putStringSet(url, set);
        }
        if (!TextUtils.isEmpty(domain)) {
            editor.putStringSet(domain, set);
        }
        editor.apply();
    }

    /**
     * 获取 Cookies
     */
    public Set<String> getCookie(String url, String domain) {
        try {
            if (!TextUtils.isEmpty(url) && sp.contains(url)) {
                return sp.getStringSet(url, null);
            }
            if (!TextUtils.isEmpty(domain) && sp.contains(domain)) {
                return sp.getStringSet(domain, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清除 Cookies
     */
    public void clear() {
        sp.edit().clear().apply();
    }
}
