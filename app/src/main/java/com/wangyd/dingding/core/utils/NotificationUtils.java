package com.wangyd.dingding.core.utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

/**
 * @author wangyd
 * @date 2018/7/20
 * @description 通知栏工具
 */
public class NotificationUtils {

    public static final String NOTICE_CHANNEL_DOWNLOAD = "download";

    /**
     * 创建下载通知渠道
     */
    public static void createDownloadChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "下载通知";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(context, NOTICE_CHANNEL_DOWNLOAD, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context,
                                                 String channelId,
                                                 String channelName,
                                                 int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.enableLights(true);
        channel.enableVibration(true);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }

}
