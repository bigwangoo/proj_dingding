package com.tianxiabuyi.txutils;

import android.content.Context;

import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.service.TxFeedbackService;
import com.tianxiabuyi.txutils.util.AppUtils;

import okhttp3.OkHttpClient;

/**
 * 意见反馈
 *
 * @author xjh1994
 * @date 16/11/15
 */
public class TxFeedbackManager {

    /**
     * 发送意见反馈
     *
     * @param context
     * @param suggestion
     * @param grade
     * @param uid
     * @param callback
     */
    public static void sendFeedback(Context context,
                                    String suggestion,
                                    float grade,
                                    String uid,
                                    ResponseCallback<HttpResult> callback) {
        String version = AppUtils.getVersionName(context);
        int versionCode = AppUtils.getVersionCode(context);

        // 需要使用默认配置
        OkHttpClient httpClient = TxUtils.getInstance().getDefaultOkHttp();
        TxFeedbackService service = TxServiceManager.createService(TxFeedbackService.class, httpClient);
        service.sendFeedback(2, version, versionCode, suggestion, grade, uid)
                .enqueue(callback);
    }

    /**
     * 无用户id
     *
     * @param context
     * @param suggestion
     * @param grade
     * @param callback
     */
    public static void sendFeedback(Context context,
                                    String suggestion,
                                    float grade,
                                    ResponseCallback<HttpResult> callback) {
        sendFeedback(context, suggestion, grade, "0", callback);
    }

    /**
     * 无评分
     *
     * @param context
     * @param suggestion
     * @param callback
     */
    public static void sendFeedback(Context context,
                                    String suggestion,
                                    ResponseCallback<HttpResult> callback) {
        sendFeedback(context, suggestion, 5.0f, "0", callback);
    }
}
