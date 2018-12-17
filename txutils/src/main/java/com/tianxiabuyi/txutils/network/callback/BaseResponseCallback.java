package com.tianxiabuyi.txutils.network.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ParseException;
import android.support.annotation.Nullable;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.callback.inter.TxCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.util.NetUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author xjh1994
 * @date 2016/8/18
 * @description 网络请求回调基类
 */
public abstract class BaseResponseCallback<T> implements TxCallback<T> {

    protected Context mContext;
    protected ProgressDialog mDialog;

    protected BaseResponseCallback() {
    }

    protected BaseResponseCallback(Context context) {
        if (context != null) {
            mContext = context;
            mDialog = new ProgressDialog(context);
        }
    }

    protected BaseResponseCallback(ProgressDialog dialog) {
        mDialog = dialog;
    }

    @Override
    public void onStart(final Call<T> call) {
        if (mDialog != null) {
            initDialog(call);
            mDialog.show();
        }
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response == null) {
            onFailed(new TxException(TxUtils.getInstance().getContext().getString(R.string.tx_error_reading_cache)));
            return;
        }

        if (response.isSuccessful()) {
            T result = response.body();
            if (result instanceof HttpResult) {
                // 继承 HttpResult
                HttpResult httpResult = (HttpResult) result;
                if (httpResult.isSuccess()) {
                    onSuccess(result);
                } else {
                    onFailed(new TxException(httpResult));
                }
            } else {
                // 老接口、其他自定义数据
                onSuccess(result);
            }
        } else {
            onFailed(new TxException(response.code(), response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (call.isCanceled()) {
            // 取消 do nothing
            return;
        }

        String message = t.getMessage();
        if (!NetUtils.isConnected(TxUtils.getInstance().getContext())) {
            // 网络问题
            message = TxUtils.getInstance().getContext().getString(R.string.tx_please_check_network);
        } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
            // Connect
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_server_conn_err);
        } else if (t instanceof SocketTimeoutException) {
            // 超时
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_server_conn_timeout);
        } else if (t instanceof IllegalStateException) {
            //
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_illegal_state);
        } else if (t instanceof HttpException) {
            // HTTP
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_http_error);
        } else if (t instanceof JsonIOException || t instanceof JsonParseException
                || t instanceof ParseException || t instanceof JSONException) {
            // 数据解析异常
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_parse_exception);
        } else {
            // 其他
            message = TxUtils.getInstance().getContext().getString(R.string.tx_retry_error_def);
        }
        onFailed(new TxException(message));
    }

    @Override
    public void onFinish() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Nullable
    public Context getContext() {
        return mContext;
    }

    private void initDialog(final Call<T> call) {
        mDialog.setMessage(TxUtils.getInstance().getContext().getString(R.string.tx_loading));
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                call.cancel();
            }
        });
    }
}
