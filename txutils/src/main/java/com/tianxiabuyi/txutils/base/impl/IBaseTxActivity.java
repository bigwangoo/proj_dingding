package com.tianxiabuyi.txutils.base.impl;

import android.content.Intent;

import com.tianxiabuyi.txutils.network.exception.TxException;

/**
 * @author xjh1994
 * @date 2016/8/24
 */
public interface IBaseTxActivity {

    int getViewByXml();

    void getIntentData(Intent intent);

    void initView();

    void initData();

    void startAnimActivity(Class<?> cla);

    void startAnimActivity(Intent intent);

    void toast(String msg);

    void toast(int msgId);

    void toast(TxException exception);

    void toastLong(String msg);

    void toastLong(int msgId);

}
