package com.tianxiabuyi.txutils.base.impl;

import android.content.Intent;
import android.os.Bundle;

import com.tianxiabuyi.txutils.network.exception.TxException;

/**
 * Created by xjh1994 on 2016/8/24.
 */
public interface IBaseTxFragment {

    int getLayoutByXml();

    void initArguments(Bundle data);

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
