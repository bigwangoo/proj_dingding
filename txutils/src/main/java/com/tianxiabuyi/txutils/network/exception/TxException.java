package com.tianxiabuyi.txutils.network.exception;

import android.text.TextUtils;

import com.tianxiabuyi.txutils.network.model.HttpResult;

/**
 * @author xjh1994
 * @date 2016/8/18
 * @description 通用异常
 */
public class TxException extends Exception {

    //缺少参数
    public static final int ERROR_MISS_PARAM = 20001;
    // token过期
    public static final int TOKEN_ERR_O = 20002;
    // token未认证
    public static final int TOKEN_ERR_T = 20004;

    // 账号不存在
    public static final int ERROR_USER_NO_FOUND = 30021;
    // 用户名或密码错误
    public static final int ERROR_USERNAME_PASS = 30022;
    // 修改密码时，旧密码错误
    public static final int ERROR_CHANGE_PASS = 30023;
    // 用户已存在
    public static final int ERROR_USER_EXIST = 30029;

    // 环信用户已存在
    public static final int EASE_USER_EXSIT = 400;

    private int resultCode;
    private String detailMessage = "";

    public TxException(String detailMessage) {
        super(detailMessage);
        this.detailMessage = detailMessage;
    }

    /**
     * 错误码
     */
    public TxException(int resultCode) {
        this(getTxExceptionMessage(resultCode, ""));
        this.resultCode = resultCode;
    }

    /**
     * 错误码、错误信息
     */
    public TxException(int resultCode, String detailMessage) {
        this(getTxExceptionMessage(resultCode, detailMessage));
        this.resultCode = resultCode;
        this.detailMessage = detailMessage;
    }

    /**
     * HttpResult
     */
    public TxException(HttpResult result) {
        this(getTxExceptionMessage(result.getErrcode(), result.getErrmsg()));
        this.resultCode = result.getErrcode();
    }


    public int getResultCode() {
        return resultCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    /**
     * 显示给用户前，对服务器传递过来的错误码进行转换
     */
    public static String getTxExceptionMessage(int code, String msg) {
        String message;
        switch (code) {
            case ERROR_MISS_PARAM:
                message = "请求缺少参数";
                break;
            case TOKEN_ERR_O:
                message = "登录已过期，请重新登录";
                break;
            case TOKEN_ERR_T:
                message = "登录已过期，请重新登录";
                break;
            case ERROR_USERNAME_PASS:
                message = "用户名或密码错误";
                break;
            case ERROR_CHANGE_PASS:
                message = "旧密码错误";
                break;
            case ERROR_USER_EXIST:
                message = "用户名已存在";
                break;
            case EASE_USER_EXSIT:
                message = "环信用户已存在";
                break;
            default:
                message = TextUtils.isEmpty(msg) ? "未知错误" : msg;
        }
        return message;
    }
}
