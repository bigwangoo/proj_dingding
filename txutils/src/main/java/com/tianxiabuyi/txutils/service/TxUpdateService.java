package com.tianxiabuyi.txutils.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.tianxiabuyi.txutils.R;

/**
 * @author wangyd
 * @date 2018/5/25
 * @description 后台下载 todo
 */
public class TxUpdateService extends IntentService {

    public static final String ACTION_UPDATE = "com.tianxiabuyi.txutils.action.UPDATE";

    public static final String EXTRA_URL = "extra_url";

    private final int NOTIFY_ID = 10006024;

    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    public static void startActionUpdate(Context context, String url) {
        Intent intent = new Intent(context, TxUpdateService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_URL, url);
        context.startService(intent);
    }

    public TxUpdateService() {
        super("TxUpdateService");
    }

    public TxUpdateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                String url = intent.getStringExtra(EXTRA_URL);
                handleActionUpdate(url);
            }
        }
    }

    /**
     * 处理下载
     *
     * @param url
     */
    private void handleActionUpdate(String url) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this, "txby");
        builder.setTicker("update_downloading")
                .setContentTitle("update_downloading")
                .setContentText("update_progress 0%")
                .setSmallIcon(R.drawable.tx_loading)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setProgress(100, 0, false);
        manager.notify(NOTIFY_ID, builder.build());

        // 更新进度
        double progress = 0;
        int p = (int) (progress * 100);
        builder.setContentText("update_progress" + p + "%");
        builder.setProgress(100, p, false);
        manager.notify(NOTIFY_ID, builder.build());

        // 取消通知
        manager.cancel(NOTIFY_ID);
    }

}
