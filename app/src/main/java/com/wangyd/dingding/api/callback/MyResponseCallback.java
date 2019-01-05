package com.wangyd.dingding.api.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.callback.BaseResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.wangyd.dingding.R;
import com.wangyd.dingding.api.exception.MyException;
import com.wangyd.dingding.api.model.MyHttpResult;

/**
 * @author wangyd
 * @date 2018/6/13
 * @description 网路请求回调，根据后台自定义
 */
public abstract class MyResponseCallback<T> extends BaseResponseCallback<T> {

    private boolean mIsShowToast = true;

    public MyResponseCallback() {
    }

    public MyResponseCallback(Context context) {
        super(context);
    }

    public MyResponseCallback(boolean isShowToast) {
        mIsShowToast = isShowToast;
    }

    public MyResponseCallback(Context context, boolean isShowToast) {
        super(context);
        mIsShowToast = isShowToast;
    }

    public MyResponseCallback(ProgressDialog dialog) {
        super(dialog);
    }

    public MyResponseCallback(ProgressDialog dialog, boolean isShowToast) {
        super(dialog);
        mIsShowToast = isShowToast;
    }

    public void setIsShowToast(boolean isShowToast) {
        this.mIsShowToast = isShowToast;
    }

    @Override
    public void onSuccess(T result) {
        if (result instanceof MyHttpResult) {
            // 继承 MyHttpResult
            MyHttpResult httpResult = (MyHttpResult) result;
            if (httpResult.isSuccess()) {
                onSuccessResult(result);
            } else {
                onFailed(new MyException(httpResult));
            }
        } else {
            // 老接口、其他自定义数据
            onSuccessResult(result);
        }
    }

    @Override
    public void onFailed(TxException e) {
        String message = e.getDetailMessage();

        if (e.getResultCode() == 500 || e.getResultCode() == 504) {
            // 读取失败
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_error_def);
            onError(new MyException(message));
        } else if (e.getResultCode() == MyHttpResult.NOT_LOGIN) {
            // Token未认证，需重新登录
            onTokenExpired();
        } else {
            // 其它
            onError(e);
        }

        // 统一弹出Toast提示
        if (mIsShowToast) {
            ToastUtils.show(message);
        }
    }

    @Override
    public void onTokenExpired() {
        Class loginClass = TxUtils.getInstance().getConfiguration().getLoginClass();
        if (loginClass != null) {
            Context context = TxUtils.getInstance().getContext();
            Intent intent = new Intent(context, loginClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(TxConstants.EXTRA_TOKEN_EXPIRES, true);
            context.startActivity(intent);
        }
    }

    public abstract void onSuccessResult(T result);
}
