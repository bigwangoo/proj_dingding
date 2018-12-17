package com.tianxiabuyi.txutils.network.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ToastUtils;

/**
 * @author xjh1994
 * @date 2016/8/18
 */
public abstract class ResponseCallback<T> extends BaseResponseCallback<T> {

    protected boolean mIsShowToast = true;

    public ResponseCallback() {
    }

    public ResponseCallback(boolean isShowToast) {
        mIsShowToast = isShowToast;
    }

    public ResponseCallback(Context context) {
        super(context);
    }

    public ResponseCallback(Context context, boolean isShowToast) {
        super(context);
        mIsShowToast = isShowToast;
    }

    public ResponseCallback(ProgressDialog dialog) {
        super(dialog);
    }

    public ResponseCallback(ProgressDialog dialog, boolean isShowToast) {
        super(dialog);
        mIsShowToast = isShowToast;
    }

    public void setIsShowToast(boolean isShowToast) {
        this.mIsShowToast = isShowToast;
    }

    @Override
    public void onFailed(TxException e) {
        String message = e.getDetailMessage();
        if (e.getResultCode() == 500 || e.getResultCode() == 504) {
            // 读取失败
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_error_def);
            onError(new TxException(message));

        } else if (e.getResultCode() == TxException.TOKEN_ERR_T) {
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
}
