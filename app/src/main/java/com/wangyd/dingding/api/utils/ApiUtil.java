package com.wangyd.dingding.api.utils;

import android.text.TextUtils;
import android.util.Log;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.util.MD5EncryptUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author wangyd
 * @date 2018/11/27
 * @description description
 */
public class ApiUtil {

    private static final String TAG = ApiUtil.class.getSimpleName();
    private static final String KEY_SIGN = "signature";

    /**
     * 移除 县城、签名字段
     */
    public static void removeSignature(Map<String, String> params) {
        if (params != null) {
            params.remove("county");
            params.remove("signature");
        }
    }

    /**
     * 参数排序MD5签名
     */
    public static String reverseValue(Map<String, String> params) {
        Map<String, String> data = new HashMap<>(16);

        // 参数过滤
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // key不为空 value不为空，key不等于KEY_PARAMS
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value) && !KEY_SIGN.equals(key)) {
                data.put(key, value);
            }
        }

        // 按key排序
        Map<String, String> resultMap = sortMapByKey(data);

        // 拼接value
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entries = resultMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            sb.append(entry.getValue());
        }

        // 打印日志
        if (TxUtils.getInstance().getConfiguration().isDebug()) {
            for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                Log.e(TAG, "valueReverse: " + entry.getKey() + " " + entry.getValue());
            }
            Log.e(TAG, "result: " + sb.toString());
        }

        // MD5加密2次
        return MD5EncryptUtils.encryptMD5(MD5EncryptUtils.encryptMD5(sb.toString()));
    }

    /**
     * Map按key反向排序
     */
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return new HashMap<>(0);
        }

        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * Map key比较
     */
    private static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str2.compareToIgnoreCase(str1);
        }
    }
}
