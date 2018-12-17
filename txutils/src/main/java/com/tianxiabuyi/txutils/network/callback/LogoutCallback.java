package com.tianxiabuyi.txutils.network.callback;

import android.content.Context;

/**
 * @author xjh1994
 * @date 2016/9/4
 * @description 登出回调
 */
public abstract class LogoutCallback {

    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public LogoutCallback() {

    }

    public LogoutCallback(Context context) {
        mContext = context;
    }

    public abstract void onSuccess();
}
