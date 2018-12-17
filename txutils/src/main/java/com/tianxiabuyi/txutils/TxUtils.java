package com.tianxiabuyi.txutils;

import android.app.Application;
import android.content.Context;

import com.tianxiabuyi.txutils.db.x;
import com.tianxiabuyi.txutils.imageloader.universal.UniversalImageLoaderTool;
import com.tianxiabuyi.txutils.network.intercept.TxCacheInterceptor;
import com.tianxiabuyi.txutils.network.intercept.TxInterceptor;
import com.tianxiabuyi.txutils.network.intercept.TxLoggerInterceptor;
import com.tianxiabuyi.txutils.network.util.Platform;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import static com.tianxiabuyi.txutils.config.TxConstants.FILE_TIMEOUT;
import static com.tianxiabuyi.txutils.config.TxConstants.TIMEOUT;

/**
 * @author xjh1994
 * @date 2016/7/15
 */
public class TxUtils {

    public static final String TAG = TxUtils.class.getSimpleName();

    private volatile static TxUtils mInstance;

    private TxConfiguration mConfiguration;

    private Platform mPlatform;

    private OkHttpClient mOkHttpClient;

    public TxUtils() {
        mPlatform = Platform.get();
    }

    public static TxUtils getInstance() {
        if (mInstance == null) {
            synchronized (TxUtils.class) {
                if (mInstance == null) {
                    mInstance = new TxUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化配置
     */
    public synchronized void init(TxConfiguration configuration) {
        this.mConfiguration = configuration;

        //网络框架
        OkHttpClient.Builder okHttpBuilder = configuration.getOkHttpBuilder();
        if (okHttpBuilder == null) {
            okHttpBuilder = getDefaultOkHttpBuilder(configuration);
        }
        //添加缓存 需要读写权限
        if (configuration.isCacheOn()) {
            File cacheFile = FileUtils.getExternalCacheDir(FileUtils.CACHE);
            if (cacheFile == null) {
                return;
            }
            TxLog.e(cacheFile.getAbsolutePath());
            //缓存大小50M
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            TxCacheInterceptor cacheInterceptor = new TxCacheInterceptor(mConfiguration);
            okHttpBuilder.addNetworkInterceptor(cacheInterceptor);
            okHttpBuilder.addInterceptor(cacheInterceptor);
            okHttpBuilder.cache(cache);
            okHttpBuilder.retryOnConnectionFailure(true);
        }
        this.mOkHttpClient = okHttpBuilder.build();

        //数据库（xUtils db）
        x.Ext.init((Application) configuration.getContext());
        x.Ext.setDebug(configuration.isDebug());

        //ImageLoader
        if (configuration.getImageLoaderProvider() == null) {
            UniversalImageLoaderTool.initImageLoader(configuration.getContext());
        } else {
            TxImageLoader.getInstance().init(configuration.getImageLoaderProvider());
        }

        //OkHttpUtils
        OkHttpUtils.initClient(new OkHttpClient.Builder()
                .connectTimeout(FILE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(FILE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor(configuration.getContext().getString(R.string.tx_app_name)))
                .build());
    }

    public boolean isDebug() {
        return mConfiguration.isDebug();
    }

    public Context getContext() {
        return mConfiguration.getContext();
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public TxConfiguration getConfiguration() {
        return mConfiguration;
    }

    /**
     * 特定接口需要使用默认配置
     */
    public OkHttpClient getDefaultOkHttp() {
        return TxUtils.getInstance().getDefaultOkHttpBuilder(mConfiguration).build();
    }

    /**
     * 特定接口需要使用默认配置
     */
    public OkHttpClient.Builder getDefaultOkHttpBuilder(TxConfiguration configuration) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        String tag = configuration.getContext().getString(R.string.tx_app_name);
        builder.addInterceptor(new TxInterceptor(configuration))
                .addInterceptor(new TxLoggerInterceptor(tag, configuration.isDebug()))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS);
        return builder;
    }
}
