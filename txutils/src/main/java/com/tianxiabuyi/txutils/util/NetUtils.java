package com.tianxiabuyi.txutils.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 网络相关的工具类
 *
 * @author xjh1994
 * @date 2016/7/15
 */
public class NetUtils {

    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null) {
                    return networkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (networkInfo != null) {
                    return networkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 3G网络
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (networkInfo != null) {
                    return networkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断当前何种网络
     */
    public static int getNetWorkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifi == State.CONNECTED) {
            return ConnectivityManager.TYPE_WIFI;
        }
        if (mobile == State.CONNECTED) {
            return ConnectivityManager.TYPE_MOBILE;
        }
        return -1;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Context context) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }

}
