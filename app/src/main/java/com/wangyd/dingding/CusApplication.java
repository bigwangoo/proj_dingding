package com.wangyd.dingding;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.mob.MobSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;
import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.imageloader.glide.GlideImageLoaderProvider;
import com.tianxiabuyi.txutils.network.intercept.LoggerInterceptor;
import com.tianxiabuyi.txutils.util.AppUtils;
import com.wangyd.dingding.Constant;
import com.wangyd.dingding.api.intercept.MyCookiesAddInterceptor;
import com.wangyd.dingding.api.intercept.MyCookiesSaveInterceptor;
import com.wangyd.dingding.api.intercept.MyInterceptor;
import com.wangyd.dingding.api.intercept.MyMockInterceptor;
import com.tianxiabuyi.villagedoctor.module.login.activity.LoginActivity;
import com.umeng.commonsdk.UMConfigure;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 初始化
 */
public class CusApplication extends MultiDexApplication {
    private static final String TAG = "xxt";
    private static Application application;
    private int activityCount;

    public static Application getApplication() {
        return application;
    }

    public int getActivityCount() {
        return activityCount;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 安装tinker
        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 防止多进程重复初始化
        if (isMainProcess()) {
            application = this;

            //initDev();
            initTxUtils();
            initWeex();
            initOther();
        }

        // 分享
        MobSDK.init(this);
        // 友盟
        UMConfigure.init(this, Constant.UMENG_APP_KEY, null, UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    private void initDev() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initTxUtils() {
        // 自定义okHttpBuilder
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .addInterceptor(new MyCookiesSaveInterceptor())
                .addInterceptor(new MyCookiesAddInterceptor())
                .addInterceptor(new MyMockInterceptor())
                .addInterceptor(new MyInterceptor())
                .addInterceptor(new LoggerInterceptor(TAG, BuildConfig.DEBUG))
                .connectTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS);

        TxConfiguration configuration = new TxConfiguration.Builder(this)
                .mode(BuildConfig.DEBUG)
                .baseUrl(Constant.getBaseUrl())
                .appType(Constant.APP_TYPE)
                .hospitalId(Constant.HOSPITAL)
                .theme(Constant.APP_THEME)
                .colorPrimary(R.color.colorPrimary)
                .loginClass(LoginActivity.class)
                .imageLoader(new GlideImageLoaderProvider())
                .okHttpBuilder(okHttpBuilder)
                .isCacheOn(true)
                .build();
        TxUtils.getInstance().init(configuration);
    }

    private void initWeex() {
//        WxUtils.getInstance().initWeex(this);
    }

    private void initOther() {
        // bugly
        Bugly.init(getApplicationContext(), Constant.BUGLY_APP_ID, BuildConfig.DEBUG);
        // X5
        QbSdk.initX5Environment(getApplicationContext(), null);

        // 注册生命周期回调
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // WXSDKManager.getInstance().notifyTrimMemory();
                //  WXSDKManager.getInstance().notifySerializeCodeCache();
            }
        });
    }

    /**
     * 判断进程名，保证只有主进程运行
     */
    public boolean isMainProcess() {
        String processName = AppUtils.getProcessName();
        Log.e(TAG, "process: " + processName);
        return processName != null && processName.equals(getPackageName());
    }

}
