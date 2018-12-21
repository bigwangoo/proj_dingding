package com.wangyd.dingding;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.imageloader.glide.GlideImageLoaderProvider;
import com.tianxiabuyi.txutils.network.intercept.LoggerInterceptor;
import com.tianxiabuyi.txutils.util.AppUtils;
import com.wangyd.dingding.api.intercept.MyCookiesAddInterceptor;
import com.wangyd.dingding.api.intercept.MyCookiesSaveInterceptor;
import com.wangyd.dingding.api.intercept.MyInterceptor;
import com.wangyd.dingding.api.intercept.MyMockInterceptor;

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

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        // 防止多进程重复初始化
        if (AppUtils.isMainProcess(this)) {
            initTxUtils();
            initBugly();
            initThird();
        }
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
                .colorPrimary(R.color.colorPrimary)
                .imageLoader(new GlideImageLoaderProvider())
                .okHttpBuilder(okHttpBuilder)
                .isCacheOn(true)
                .build();
        TxUtils.getInstance().init(configuration);
    }

    private void initBugly() {
        // 初始化Bugly
        Context context = getApplicationContext();
        String packageName = context.getPackageName();
        String processName = AppUtils.getProcessName();
        // 上报策略
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        // 上报进程
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化延迟20s
        strategy.setAppReportDelay(20000);
        CrashReport.initCrashReport(context, Constant.BUGLY_APP_ID, BuildConfig.DEBUG, strategy);
        // 开发设备
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);
    }

    private void initThird() {
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

    public int getActivityCount() {
        return activityCount;
    }

    public static Application getApplication() {
        return application;
    }
}
