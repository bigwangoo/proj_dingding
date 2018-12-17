package com.tianxiabuyi.txutils.network.callback;

import android.content.Context;

import com.tianxiabuyi.txutils.network.exception.TxException;

/**
 * @author xjh1994
 * @date 2016/8/30
 * @description 软件更新会滴
 */
public abstract class UpdateCallback<T> {

    private Context mContext;

    public UpdateCallback() {

    }

    public UpdateCallback(Context context) {
        mContext = context;
    }

    public abstract void onSuccess(T result);

    public abstract void onError(TxException e);

    public Context getContext() {
        return mContext;
    }
}
