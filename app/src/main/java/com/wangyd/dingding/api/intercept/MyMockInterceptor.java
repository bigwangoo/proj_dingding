package com.wangyd.dingding.api.intercept;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.db.util.IOUtil;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.wangyd.dingding.core.utils.UserSpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author wangyd
 * @date 2018/5/22
 * @description 模拟数据拦截器
 */
public class MyMockInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //TxLog.e("intercept: Thread=" + Thread.currentThread().getName());
        Response response = null;
        Request request = chain.request();

        // 离线模式
        boolean offlineMode = true;
        if (offlineMode) {
            response = interceptRequestWhenDebug(request);
        }

        // 默认不处理
        if (response == null) {
            response = chain.proceed(request);
        }
        return response;
    }

    /**
     * 拦截指定地址
     */
    private Response interceptRequestWhenDebug(Request request) {
        String path = request.url().uri().getPath();
        TxLog.e("intercept: path=" + path);

        String result;
        switch (getExactlyPath(path)) {


            // 获取模拟数据
            case "api/likeThis":
                result = getMockResponse("mock/getContactList.json");
                break;
            default:
                result = getDefaultResponse();
//                result = null;
                break;
        }
//        if (result == null) {
//            return null;
//        }

        if (TextUtils.isEmpty(result)) {
            return new Response.Builder().code(500).protocol(Protocol.HTTP_1_0).request(request)
                    .addHeader("Content-Type", "application/json")
                    .message("")
                    .body(ResponseBody.create(MediaType.parse("application/json"), result))
                    .build();
        } else {
            return new Response.Builder().code(200).protocol(Protocol.HTTP_1_0).request(request)
                    .addHeader("Content-Type", "application/json")
                    .message("")
                    .body(ResponseBody.create(MediaType.parse("application/json"), result))
                    .build();
        }
    }

    /**
     * 默认请求响应
     */
    private String getDefaultResponse() {
        MyHttpResult result = new MyHttpResult();
        result.setMsg("离线模式，无法访问网络");
        result.setStatus(40000);
        return GsonUtils.toJson(result);
    }

    /**
     * 从本地Json文件 获取请求响应
     */
    private String getMockResponse(String jsonPath) {
        try {
            Context context = TxUtils.getInstance().getContext();
            InputStream inputStream = context.getAssets().open(jsonPath, AssetManager.ACCESS_BUFFER);
            return IOUtil.readStr(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从本地数据库 获取请求响应
     */
    private <T> String getCacheResponse(T data) {
        MyHttpResult<T> result = new MyHttpResult<>();
        result.setStatus(MyHttpResult.SUCCESS);
        result.setMsg("success");
        result.setData(data);
        return GsonUtils.toJson(result);
    }

    /**
     * 读取请求中参数
     */
    @NonNull
    private Map<String, String> getParameterMap(Request request) {
        HttpUrl url = request.url();
        Map<String, String> params = new HashMap<>(16);
        Set<String> parameterNames = url.queryParameterNames();
        if (parameterNames.size() > 0) {
            for (String name : parameterNames) {
                String value = url.queryParameter(name);
                params.put(name, value);
            }
        }
        return params;
    }

    /**
     * 获取匹配路径
     */
    private String getExactlyPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("/")) {
                return path.substring(1, path.length());
            }
        }
        return path;
    }
}
