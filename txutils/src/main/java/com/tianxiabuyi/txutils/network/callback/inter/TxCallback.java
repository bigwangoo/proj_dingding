package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author xjh1994
 * @date 2016/8/29
 */
public interface TxCallback<T> {

    void onStart(Call<T> call);

    void onResponse(Call<T> call, Response<T> response);

    void onFailure(Call<T> call, Throwable t);

    void onFailed(TxException e);

    void onSuccess(T result);

    void onError(TxException e);

    void onTokenExpired();

    void onFinish();
}
