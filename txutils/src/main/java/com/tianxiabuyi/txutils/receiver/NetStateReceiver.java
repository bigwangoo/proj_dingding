package com.tianxiabuyi.txutils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.NetUtils;

/**
 * 网络状态监听
 *
 * @author xjh1994
 * @date 2016/9/1
 */
public class NetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (ConnectivityManager.CONNECTIVITY_ACTION.equalsIgnoreCase(action)) {
            if (NetUtils.isConnected(context)) {
                onConnected();
                //Wifi情况下
                if (NetUtils.isWifi(context)) {
                    TxLog.e("使用WiFi");
                    onWifi();
                }
                //数据流量的情况下
                else {
                    TxLog.e("使用数据流量");
                    onData();
                }
            } else {
                TxLog.e("网络连接断开");
                onDisconnected();
            }
        }
    }

    protected void onConnected() {

    }

    protected void onDisconnected() {

    }

    protected void onData() {

    }

    protected void onWifi() {

    }
}
