package com.wangyd.dingding.api.intercept;

import com.tianxiabuyi.txutils.util.CookiesUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wangyd
 * @date 2018/6/13
 * @description Cookies持久化
 */
public class MyCookiesSaveInterceptor implements Interceptor {

    public MyCookiesSaveInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        List<String> cookies = response.headers("Set-Cookie");
        // 保存cookies
        CookiesUtils.getInstance().saveCookie(request.url().toString(), request.url().host(), cookies);

        return response;
    }

}
