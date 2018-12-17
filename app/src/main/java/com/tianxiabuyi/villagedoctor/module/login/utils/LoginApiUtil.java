package com.tianxiabuyi.villagedoctor.module.login.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.tianxiabuyi.txutils.network.util.TxLog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * @author wangyd
 * @date 2018/5/10
 * @description 第三方授权
 */
public class LoginApiUtil implements Handler.Callback {
    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    private Handler handler;
    private Context context;
    private String platformName;
    private OnAuthorizeListener listener;
    private ProgressDialog pd;

    public LoginApiUtil() {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    public void login(final Context context, String platformName, OnAuthorizeListener listener) {
        if (context == null || platformName == null) {
            return;
        }

        this.context = context.getApplicationContext();
        this.platformName = platformName;
        this.listener = listener;

        Platform plat = ShareSDK.getPlatform(platformName);
        if (plat == null) {
            listener.authorizeFail();
            return;
        }

        if (plat.isAuthValid()) {
            listener.authorizeSuccess(plat);
            return;
        }
        // 进度条
        pd = ProgressDialog.show(context, null, "加载中...", false, true);
        //使用SSO授权，通过客户单授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_COMPLETE;
                    msg.arg1 = action;
                    msg.obj = new Object[]{plat.getName(), res};
                    handler.sendMessage(msg);
                }
                TxLog.d("onComplete");
            }

            @Override
            public void onError(Platform plat, int action, Throwable t) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_ERROR;
                    msg.arg1 = action;
                    msg.obj = t.getMessage();
                    handler.sendMessage(msg);
                }
                TxLog.d("onError" + t.getMessage());
            }

            @Override
            public void onCancel(Platform plat, int action) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_CANCEL;
                    msg.arg2 = action;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }
                TxLog.d("onCancel");
            }
        });
        // plat.authorize();
        plat.showUser(null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        pd.dismiss();
        switch (msg.what) {
            // 成功
            case MSG_AUTH_COMPLETE: {
                Object[] obj = (Object[]) msg.obj;
                Platform platform = ShareSDK.getPlatform((String) obj[0]);
                listener.authorizeSuccess(platform);
            }
            break;

            // 失败
            case MSG_AUTH_ERROR: {
                Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
                Platform platform = ShareSDK.getPlatform(platformName);
                platform.removeAccount(true);
                listener.authorizeFail();
            }
            break;

            // 取消
            case MSG_AUTH_CANCEL: {
                Toast.makeText(context, "取消授权", Toast.LENGTH_SHORT).show();
                listener.authorizeFail();
            }
            break;

            default:
                listener.authorizeFail();
                break;
        }
        return false;
    }

    public interface OnAuthorizeListener {
        /**
         * 授权成功
         */
        void authorizeSuccess(Platform platform);

        /**
         * 授权失败
         */
        void authorizeFail();
    }
}

