package com.tianxiabuyi.txutils.network;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * @author xjh1994
 * @date 2016/8/29
 */
public class TxCallAdapter<R> implements CallAdapter<R, TxCall<?>> {

    private final Type responseType;

    public TxCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public TxCall<?> adapt(Call<R> call) {
        return new TxCall<>(call);
    }
}