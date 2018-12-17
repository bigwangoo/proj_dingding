package com.tianxiabuyi.txutils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.activity.TxUpdateActivity;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxUpdateResult;
import com.tianxiabuyi.txutils.network.service.TxUpdateService;
import com.tianxiabuyi.txutils.util.AppUtils;
import com.tianxiabuyi.txutils.util.NetUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;

import okhttp3.OkHttpClient;

/**
 * @author xjh1994
 * @date 2016/8/31
 * @description 软件更新管理
 */
public class TxUpdateManager {

    private static boolean isOnlyWifi = false;

    /**
     * 软件更新
     */
    public static void update(final Activity activity) {
        TxUpdateManager.update(activity, false);
    }

    /**
     * 软件更新
     *
     * @param toast 是否有提示信息
     */
    public static void update(final Activity activity, final boolean toast) {
        //非WiFi下不更新
        if (isOnlyWifi && !NetUtils.isWifi(activity)) {
            return;
        }

        // 需要使用默认配置
        OkHttpClient httpClient = TxUtils.getInstance().getDefaultOkHttp();
        TxUpdateService service = TxServiceManager.createService(TxUpdateService.class, httpClient);

        // 软件更新
        service.update().enqueue(new ResponseCallback<TxUpdateResult>(false) {
            @Override
            public void onSuccess(TxUpdateResult result) {
                TxUpdateResult.AppBean updateResult = result.getApp();
                compareVersion(activity, updateResult, toast);
            }

            @Override
            public void onError(TxException e) {
                if (toast) {
                    String msg = e.getMessage();
                    if (e.getResultCode() == 30041) {
                        msg = "已是最新版本";
                    }
                    ToastUtils.show(activity, msg);
                }
            }
        });
    }

    /**
     * 软件更新,自定义更新结果
     */
    public static void checkUpdate(final Context context, final TxUpdateListener listener) {
        // 需要使用默认配置
        OkHttpClient httpClient = TxUtils.getInstance().getDefaultOkHttp();
        TxUpdateService service = TxServiceManager.createService(TxUpdateService.class, httpClient);

        // 软件更新
        service.update().enqueue(new ResponseCallback<TxUpdateResult>() {
            @Override
            public void onSuccess(TxUpdateResult result) {
                boolean hasUpdate = false;
                TxUpdateResult.AppBean updateResult = result.getApp();
                if (updateResult != null) {
                    if (AppUtils.getVersionCode(context) < updateResult.getVersion_code()) {
                        hasUpdate = true;
                    }
                }
                listener.onUpdate(hasUpdate, result.getApp());
            }

            @Override
            public void onError(TxException e) {
                listener.onError(e);
            }
        });
    }

    /**
     * 比较当前版本
     */
    private static void compareVersion(final Activity activity, final TxUpdateResult.AppBean info, final boolean toast) {
        if (info != null) {
            if (AppUtils.getVersionCode(activity) < info.getVersion_code()) {
                new AlertDialog.Builder(activity)
                        .setTitle("检测到新版本 " + info.getVersion())
                        .setMessage(TextUtils.concat("更新内容：\n", info.getDesc()))
                        .setNegativeButton(R.string.tx_cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.tx_update, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                // 确认更新跳转页面
                                TxUpdateActivity.update(activity, info);
                            }
                        })
                        .create().show();
            } else {
                if (toast) {
                    ToastUtils.show(activity, activity.getString(R.string.tx_already_newest_version));
                }
            }
        }
    }

    /**
     * 设置只在WiFi下更新
     *
     * @param isOnlyWifi wifi
     */
    public static void setUpdateOnlyWifi(boolean isOnlyWifi) {
        TxUpdateManager.isOnlyWifi = isOnlyWifi;
    }

    /**
     * 自定义回调
     */
    public interface TxUpdateListener {
        void onUpdate(boolean hasUpdate, TxUpdateResult.AppBean updateResult);

        void onError(TxException e);
    }
}
