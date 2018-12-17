package com.wangyd.dingding.api.exception;

import android.text.TextUtils;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.wangyd.dingding.api.model.MyHttpResult;

/**
 * 继承父类，根据新后台重写
 *
 * @author wangyd
 * @date 2018/6/13
 * @description 封装异常信息
 */
public class MyException extends TxException {

    private static final int ERROR_PARAM_ERROR = 400;
    private static final int ERROR_SERVER_ERROR = 504;

    private int resultCode;
    private String detailMessage = "";

    public MyException(String detailMessage) {
        super(detailMessage);
        this.detailMessage = detailMessage;
    }

    /**
     * 错误码
     */
    public MyException(int resultCode) {
        this(getTxExceptionMessage(resultCode, ""));
        this.resultCode = resultCode;
    }

    /**
     * 错误码、错误信息
     */
    public MyException(int resultCode, String detailMessage) {
        this(getTxExceptionMessage(resultCode, detailMessage));
        this.resultCode = resultCode;
        this.detailMessage = detailMessage;
    }

    /**
     * httpResult
     */
    public MyException(MyHttpResult result) {
        this(getTxExceptionMessage(result.getStatus(), result.getMsg()));
        this.resultCode = result.getStatus();
    }


    @Override
    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String getDetailMessage() {
        return detailMessage;
    }

    /**
     * 显示给用户前，对服务器传递过来的错误码进行转换
     */
    public static String getTxExceptionMessage(int code, String msg) {
        String message;
        switch (code) {
            case ERROR_PARAM_ERROR:
                message = "请求参数错误";
                break;
            case ERROR_SERVER_ERROR:
                message = "服务器在打盹，请稍后再试";
                break;
            default:
                message = TextUtils.isEmpty(msg) ? "未知错误" : msg;
        }
        return message;
    }
}
