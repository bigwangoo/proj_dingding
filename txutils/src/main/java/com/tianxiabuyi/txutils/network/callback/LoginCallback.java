package com.tianxiabuyi.txutils.network.callback;

import android.content.Context;

import com.tianxiabuyi.txutils.network.exception.TxException;

/**
 * @author Administrator
 * @date 2016/8/18
 * @description 登录回调
 */
public abstract class LoginCallback<T> {

    private Context mContext;

    public LoginCallback() {
    }

    public LoginCallback(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void onSuccess(T user);

    public abstract void onError(TxException e);
}
