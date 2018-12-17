package com.wangyd.dingding.api.intercept;

import android.text.TextUtils;

import com.tianxiabuyi.txutils.util.CookiesUtils;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangyd
 * @date 2018/6/13
 * @description Cookies持久化
 */
public class MyCookiesAddInterceptor implements Interceptor {

    public MyCookiesAddInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        // 添加Cookies
        Set<String> cookies = CookiesUtils.getInstance().getCookie(request.url().toString(), request.url().host());
        if (cookies != null) {
            for (String cookie : cookies) {
                if (!TextUtils.isEmpty(cookie)) {
                    builder.addHeader("Cookie", cookie);
                }
            }
        }
        return chain.proceed(builder.build());
    }

}
