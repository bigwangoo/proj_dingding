package com.tianxiabuyi.txutils.network.intercept;

import android.content.Context;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.util.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangyd
 * @date 2018/8/24
 * @description 网络请求 缓存拦截器
 */
public class TxCacheInterceptor implements Interceptor {

    private Context context;
    private TxConfiguration configuration;

    public TxCacheInterceptor(TxConfiguration configuration) {
        this.context = configuration.getContext();
        this.configuration = configuration;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // 有网络时
        if (NetUtils.isConnected(context)) {
            Response.Builder builder = chain.proceed(request).newBuilder();
            if (configuration.isCacheFromHeader()) {
                //读接口上的 @Headers 里的配置
                String s = request.cacheControl().toString();
                builder.header("Cache-Control", "public, max-age=" + (TextUtils.isEmpty(s) ? "0" : s));
            } else {
                //读缓存时间配置
                builder.header("Cache-Control", "public, max-age=" + configuration.getCacheTime());
            }
            return builder.removeHeader("Pragma").build();
        }
        // 无网络时，设置超时为4周
        else {
            if (configuration.isCacheNetworkOff()) {
                int maxStale = 60 * 60 * 24 * 28;
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                return chain.proceed(request).newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma").build();
            } else {
                return chain.proceed(request);
            }
        }
    }
}
