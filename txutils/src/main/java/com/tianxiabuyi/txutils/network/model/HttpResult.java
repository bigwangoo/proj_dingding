package com.tianxiabuyi.txutils.network.model;

import java.io.Serializable;

/**
 * @author xjh1994
 * @date 2016/7/27
 */
public class HttpResult<T> implements Serializable {

    /**
     * 返回成功
     */
    public static final int SUCCESS = 0;
    /**
     * token过期
     */
    public static final int TOKEN_ERR_O = 20002;

    private String msg;
    private int status = -1;

    private String errmsg;
    private int errcode = -1;
    private T data;

    public HttpResult() {

    }

    public HttpResult(String result) {

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return errcode == SUCCESS || status == SUCCESS;
    }

    public boolean isTokenExpired() {
        return errcode == TOKEN_ERR_O;
    }

}
