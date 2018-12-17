package com.tianxiabuyi.txutils;

import android.content.Context;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.imageloader.BaseImageLoaderProvider;

import okhttp3.OkHttpClient;

/**
 * @author xjh1994
 * @date 2016/8/17
 * @description 全局配置
 */
public class TxConfiguration {

    public static final boolean DEBUG = true;
    public static final boolean RELEASE = false;

    private final boolean mode;
    private final Context context;
    private final String baseUrl;                               //api地址 （必传）
    private final String appType;                               //APP类型 （必传）
    private final String hospitalId;                            //医院id  （必传）
    private final String token;                                 //token
    private final String tokenRefreshUrl;                       //token刷新地址
    private final String theme;                                 //主题
    private final int colorPrimary;                             //主题色
    private final BaseImageLoaderProvider imageLoaderProvider;  //图片加载
    private final Class loginClass;                             //登录界面
    private final OkHttpClient.Builder okHttpBuilder;           //自定义okHttpBuilder
    private final boolean isCacheOn;                            //开启缓存              默认关闭
    private final boolean isCacheFromHeader;                    //Header方式缓存        默认开启
    private final boolean isCacheNetworkOff;                    //无网络是否使用缓存    默认关闭
    private final int cacheTime;                                //缓存时间，单位是秒

    public TxConfiguration(final Builder builder) {
        this.mode = builder.mode;
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
        this.appType = builder.appType;
        this.hospitalId = builder.hospitalId;
        this.token = builder.token;
        this.tokenRefreshUrl = builder.tokenRefreshUrl;
        this.theme = builder.theme;
        this.colorPrimary = builder.colorPrimary;
        this.imageLoaderProvider = builder.imageLoaderProvider;
        this.okHttpBuilder = builder.okHttpBuilder;
        this.isCacheOn = builder.isCacheOn;
        this.isCacheFromHeader = builder.isCacheFromHeader;
        this.cacheTime = builder.cacheTime;
        this.isCacheNetworkOff = builder.isCacheNetworkOff;
        this.loginClass = builder.loginClass;
    }

    public boolean isDebug() {
        return mode;
    }

    public Context getContext() {
        return context;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAppType() {
        return appType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public String getToken() {
        return token;
    }

    public String getTokenRefreshUrl() {
        return tokenRefreshUrl;
    }

    public String getTheme() {
        return theme;
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public BaseImageLoaderProvider getImageLoaderProvider() {
        return imageLoaderProvider;
    }

    public OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpBuilder;
    }

    public boolean isCacheOn() {
        return isCacheOn;
    }

    public boolean isCacheFromHeader() {
        return isCacheFromHeader;
    }

    public boolean isCacheNetworkOff() {
        return isCacheNetworkOff;
    }

    public int getCacheTime() {
        return cacheTime;
    }

    public Class getLoginClass() {
        return loginClass;
    }

    /**
     * builder
     */
    public static class Builder {
        private boolean mode = RELEASE;

        private Context context;
        private String baseUrl;
        private String appType;
        private String hospitalId;
        private String token;
        private String tokenRefreshUrl;
        private String theme;
        private int colorPrimary = -1;
        private BaseImageLoaderProvider imageLoaderProvider;
        private Class loginClass;
        private OkHttpClient.Builder okHttpBuilder;
        private boolean isCacheOn = false;
        private boolean isCacheFromHeader = true;
        private boolean isCacheNetworkOff = false;
        private int cacheTime = 0;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder mode(boolean mode) {
            this.mode = mode;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder appType(String appType) {
            this.appType = appType;
            return this;
        }

        public Builder hospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder tokenRefreshUrl(String tokenRefreshUrl) {
            this.tokenRefreshUrl = tokenRefreshUrl;
            return this;
        }

        public Builder theme(String theme) {
            this.theme = theme;
            return this;
        }

        public Builder colorPrimary(int colorPrimary) {
            this.colorPrimary = colorPrimary;
            return this;
        }

        public Builder imageLoader(BaseImageLoaderProvider imageLoaderProvider) {
            this.imageLoaderProvider = imageLoaderProvider;
            return this;
        }

        public Builder loginClass(Class loginClass) {
            this.loginClass = loginClass;
            return this;
        }

        public Builder okHttpBuilder(OkHttpClient.Builder okHttpBuilder) {
            this.okHttpBuilder = okHttpBuilder;
            return this;
        }

        public Builder isCacheOn(boolean isCacheOn) {
            this.isCacheOn = isCacheOn;
            return this;
        }

        public Builder isCacheFromHeader(boolean isCacheFromHeader) {
            this.isCacheFromHeader = isCacheFromHeader;
            return this;
        }

        public Builder isCacheNetworkOff(boolean isCacheNetworkOff) {
            this.isCacheNetworkOff = isCacheNetworkOff;
            return this;
        }

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public TxConfiguration build() {
            initEmptyFieldsWithDefaultValues();
            return new TxConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = TxConstants.BASE_URL;
            }

            if (TextUtils.isEmpty(tokenRefreshUrl)) {
                tokenRefreshUrl = TxConstants.TOKEN_REFRESH_URL;
            }

            if (colorPrimary == -1) {
                throw new NullPointerException("colorPrimary cannot be null. " +
                        "Did you forget to set colorPrimary in TxConfiguration?");
            }
        }
    }

}
