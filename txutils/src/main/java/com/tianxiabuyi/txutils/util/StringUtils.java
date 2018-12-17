package com.tianxiabuyi.txutils.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangyd
 * @date 2018/8/30
 * @description 字符串工具
 */
public class StringUtils {

    /**
     * 去除 value = null
     */
    public static HashMap<String, String> removeMapNull(Map<String, String> map) {
        if (map == null) {
            return new HashMap<>(0);
        }

        HashMap<String, String> data = new LinkedHashMap<>(16);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            Object value = entry.getValue();
            if (value != null) {
                data.put(entry.getKey(), entry.getValue());
            }
        }
        return data;
    }

    /**
     * 去除 value = null 与 value = ""
     */
    public static HashMap<String, String> removeMapNullEmpty(Map<String, String> map) {
        if (map == null) {
            return new HashMap<>(0);
        }

        HashMap<String, String> data = new LinkedHashMap<>(16);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            Object value = entry.getValue();
            if (value != null && !"".equals(value)) {
                data.put(entry.getKey(), entry.getValue());
            }
        }
        return data;
    }

    /**
     * 数组 转 String
     *
     * @param charSequences 数组
     * @param separator     分隔符号
     * @return String
     */
    public static String array2String(CharSequence[] charSequences, String separator) {
        if (charSequences == null || charSequences.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charSequences.length; i++) {
            if (i == charSequences.length - 1) {
                sb.append(charSequences[i]);
            } else {
                sb.append(charSequences[i]).append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 数组 转 String
     *
     * @param integers  数组
     * @param separator 分隔符号
     * @return String
     */
    public static String array2String(Integer[] integers, String separator) {
        if (integers == null || integers.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < integers.length; i++) {
            if (i == integers.length - 1) {
                sb.append(integers[i]);
            } else {
                sb.append(integers[i]).append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 集合 转 String
     *
     * @param list      集合
     * @param separator 分隔符号
     * @return String
     */
    public static String list2String(List list, String separator) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i)).append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 不显示null  null=""
     */
    public static String emptyIfNull(@Nullable String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    /**
     * 不显示null  null=" "
     */
    public static String spaceIfNull(@Nullable String str) {
        return TextUtils.isEmpty(str) ? " " : str;
    }

    /**
     * safe toString
     */
    public static String obj2String(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * parse int
     */
    public static int parseInteger(String numStr, int def) {
        try {
            if (numStr != null) {
                return Integer.parseInt(numStr);
            }
        } catch (Exception ignored) {
        }

        return def;
    }

    /**
     * 是否是GIF
     *
     * @param filename 文件名
     * @return boolean
     */
    public static boolean isGif(String filename) {
        return filename.toLowerCase().endsWith("gif");
    }
}
