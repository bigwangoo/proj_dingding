package com.tianxiabuyi.txutils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.network.TxCallAdapterFactory;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xjh1994
 * @date 2016/8/29
 * @description Service管理
 */
@SuppressWarnings("unchecked")
public class TxServiceManager {

    private static HashMap<String, Object> mServiceMap = new HashMap<>();

    /**
     * 创建 Retrofit Service
     */
    public static <T> T createService(Class<T> t) {
        return createService(t, "");
    }

    /**
     * 创建 Retrofit Service, 自定义接口地址url
     */
    public static <T> T createService(Class<T> t, String url) {
        T service = (T) mServiceMap.get(t.getName());
        if (service == null) {
            OkHttpClient okHttpClient = TxUtils.getInstance().getOkHttpClient();
            Retrofit retrofit = getRetrofit(okHttpClient, url);
            service = retrofit.create(t);
            mServiceMap.put(t.getName(), service);
        }
        return service;
    }

    /**
     * 创建 Retrofit Service, 自定义 OkHttpClient
     */
    public static <T> T createService(Class<T> t, OkHttpClient client) {
        return createService(t, client, "");
    }

    /**
     * 创建 Retrofit Service, 自定义 OkHttpClient、接口地址url
     */
    public static <T> T createService(Class<T> t, OkHttpClient client, String url) {
        if (client == null) {
            throw new NullPointerException("client cannot be null");
        }

        T service = (T) mServiceMap.get(t.getName());
        if (service == null) {
            Retrofit retrofit = getRetrofit(client, url);
            service = retrofit.create(t);
            mServiceMap.put(t.getName(), service);
        }
        return service;
    }

    /**
     * 取消请求, 在 onDestroy()时调用
     *
     * @param tag Object
     */
    public static void cancelTag(@NonNull Object tag) {
        OkHttpClient mOkHttpClient = TxUtils.getInstance().getOkHttpClient();

        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * @param okHttpClient
     * @param url
     * @return
     */
    public static Retrofit getRetrofit(OkHttpClient okHttpClient, String url) {
        return new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(url) ? TxUtils.getInstance().getConfiguration().getBaseUrl() : url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(TxCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
