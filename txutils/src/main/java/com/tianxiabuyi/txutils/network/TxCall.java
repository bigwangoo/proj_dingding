package com.tianxiabuyi.txutils.network;

import android.support.annotation.NonNull;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.callback.inter.TxCallback;
import com.tianxiabuyi.txutils.network.util.Platform;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author xjh1994
 * @date 2016/8/29
 */
public class TxCall<T> {

    private final Call<T> call;

    public TxCall(Call<T> call) {
        this.call = call;
    }

    /**
     * 异步请求
     */
    public void enqueue(final TxCallback<T> callback) {
        // 线程切换
        final Platform platform = TxUtils.getInstance().getPlatform();
        platform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onStart(call);
            }
        });

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull final Call<T> call, @NonNull final Response<T> response) {
                platform.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(call, response);
                        callback.onFinish();
                    }
                });
            }

            @Override
            public void onFailure(@NonNull final Call<T> call, @NonNull final Throwable t) {
                platform.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(call, t);
                        callback.onFinish();
                    }
                });
            }
        });
    }

    /**
     * 同步请求
     */
    public T execute() throws IOException {
        return call.execute().body();
    }

    /**
     * 取消请求
     */
    public void cancel() {
        call.cancel();
    }

    /**
     * 是否取消
     */
    public boolean isCanceled() {
        return call.isCanceled();
    }
}
