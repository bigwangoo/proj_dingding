package com.tianxiabuyi.txutils.app;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author wangyd
 * @date 2018/8/6
 * @description Handler内存问题
 */
public abstract class TxWeakHandler<T> extends Handler {

    public WeakReference<T> reference;

    /**
     * 创建主线程Handler使用的构造器
     */
    public TxWeakHandler(T reference) {
        this.reference = new WeakReference<>(reference);
    }

    /**
     * 创建子线程Handler使用的构造器
     */
    public TxWeakHandler(Looper looper, T reference) {
        super(looper);
        this.reference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = reference.get();
        if (t == null) {
            return;
        }
        handleMessage(t, msg);
    }

    public abstract void handleMessage(T t, Message msg);

}