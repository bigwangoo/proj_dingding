package com.tianxiabuyi.txutils.network.model;

import java.io.Serializable;

/**
 * @author xjh1994
 * @date 2016/8/18
 * @description 用户信息基类
 */
public class TxUser implements Serializable {

    /**
     * uid : 233192
     * user_name : wang
     */

    private int uid;
    private String user_name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
