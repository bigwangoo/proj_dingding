package com.tianxiabuyi.txutils.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * @author wangyd
 * @description Json 转换工具
 */
public class GsonUtils {
    private static Gson gson = new Gson();

    /**
     * 对象转json
     */
    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static String toJson(JsonElement jsonElement) {
        return gson.toJson(jsonElement);
    }

    /**
     * json字符串转对象或容器
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        try {
            return gson.fromJson(json, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象或容器
     */
    public static <T> T fromJson(String json, Class<T> c) {
        try {
            return gson.fromJson(json, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(JsonElement json, TypeToken<T> token) {
        try {
            return gson.fromJson(json, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(JsonElement json, Class<T> c) {
        try {
            return gson.fromJson(json, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}