package com.wangyd.dingding.api.intercept;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.intercept.TxInterceptor;
import com.tianxiabuyi.txutils.util.MD5EncryptUtils;
import com.wangyd.dingding.core.utils.UserSpUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangyd
 * @date 2018/6/12
 * @description 网络请求拦截器，添加公共参数、加密
 */
public class MyInterceptor implements Interceptor {
    private static final String TAG = TxInterceptor.class.getSimpleName();

    private static final String KEY_COUNTY = "county";
    private static final String KEY_SIGN = "signature";

    public MyInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl httpUrl = originalRequest.url();
        HttpUrl.Builder builder = httpUrl.newBuilder();

        // 获取请求参数
        Map<String, String> params = new HashMap<>(16);
        Set<String> parameterNames = httpUrl.queryParameterNames();
        if (parameterNames.size() > 0) {
            for (String name : parameterNames) {
                params.put(name, httpUrl.queryParameter(name));
            }
        }

        // 添加country字段
        String countyId = UserSpUtils.getInstance().getCountyId();
        if (!TextUtils.isEmpty(countyId)) {
            params.put(KEY_COUNTY, countyId);
            builder.addQueryParameter(KEY_COUNTY, countyId);
        }

        // 添加signature字段
        if (params.size() > 0) {
            builder.addQueryParameter(KEY_SIGN, valueReverse(params));
        }

        HttpUrl newHttpUrl = builder.build();
        Request newRequest = originalRequest.newBuilder().url(newHttpUrl).build();
        return chain.proceed(newRequest);
    }

    /**
     * 参数排序后 MD5签名
     */
    private static String valueReverse(Map<String, String> params) {
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


