package com.wangyd.dingding.api.model;

/**
 * @author wangyd
 * @date 2018/6/13
 * @description description
 */
public class MyHttpResult<T> {

    public static final int SUCCESS = 0;        //返回成功
    public static final int NOT_LOGIN = 106;    //没有登录

    private String msg;
    private int status = -1;
    private T data;

    public MyHttpResult() {

    }

    public MyHttpResult(String result) {

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return status == SUCCESS;
    }

    public boolean isLogout() {
        return status == NOT_LOGIN;
    }
}
