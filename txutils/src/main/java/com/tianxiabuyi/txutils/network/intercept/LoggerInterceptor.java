package com.tianxiabuyi.txutils.network.intercept;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.tianxiabuyi.txutils.network.util.TxLog;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author wangyd
 * @date 2018/4/2
 * @description 网络请求 日志打印拦截器
 */
public class LoggerInterceptor implements Interceptor {
    private static final String TAG = "TxUtils";

    private String tag;
    private boolean showLog;

    public LoggerInterceptor() {
        this(TAG);
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public LoggerInterceptor(String tag, boolean showLog) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.tag = tag;
        this.showLog = showLog;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (showLog) {
            logForRequest(request);
        }
        Response response = chain.proceed(request);
        if (showLog) {
            return logForResponse(response);
        }
        return response;
    }

    /**
     * 打印请求
     */
    private void logForRequest(Request request) {
        try {
            Log.e(tag, "========request'log=======");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "mUrl : " + request.url().toString());
            Log.e(tag, "tag : " + request.tag());
            // header
            Headers headers = request.headers();
            if (headers != null && headers.size() > 0) {
                for (int i = 0, size = headers.size(); i < size; i++) {
                    Log.e(tag, "headers : " + headers.name(i) + ": " + headers.value(i));
                }
            }
            // body
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.e(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(tag, "========request'log=======end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印响应
     */
    private Response logForResponse(Response response) throws IOException {
        Log.e(tag, "========response'log=======");
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        Log.e(tag, "mUrl : " + clone.request().url());
        Log.e(tag, "code : " + clone.code());
        Log.e(tag, "protocol : " + clone.protocol());
        if (!TextUtils.isEmpty(clone.message())) {
            Log.e(tag, "message : " + clone.message());
        }

        if (showLog) {
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();

                        if (!clone.isSuccessful()) {
                            TxLog.e(tag, "responseBody's content : " + resp);
                        } else {
                            if (resp.contains("errcode")) {
                                TxLog.e(tag, "responseBody's content : " + resp);
                            } else {
                                TxLog.e(tag, "responseBody's content : " + resp);
                            }
                        }
                        body = ResponseBody.create(mediaType, resp);
                        Log.e(tag, "========response'log=======end");

                        return response.newBuilder().body(body).build();
                    } else {
                        Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        }

        Log.e(tag, "========response'log=======end");

        return response;
    }

    /**
     * body是否是文本
     */
    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求body
     */
    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

}
