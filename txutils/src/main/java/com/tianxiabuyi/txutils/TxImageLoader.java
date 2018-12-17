package com.tianxiabuyi.txutils;

import android.content.Context;
import android.widget.ImageView;

import com.tianxiabuyi.txutils.imageloader.BaseImageLoaderProvider;
import com.tianxiabuyi.txutils.imageloader.CommonImageLoader;
import com.tianxiabuyi.txutils.imageloader.universal.UniversalImageLoaderProvider;


/**
 * TxImageLoader
 *
 * @author xjh1994
 * @date 2016/5/30
 * @description 统一图片加载入口
 */
public class TxImageLoader {
    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    public static final int ROUND_CORNER_RADIUS = 20;

    private volatile static TxImageLoader mInstance;
    private BaseImageLoaderProvider mProvider;

    private TxImageLoader() {
        mProvider = new UniversalImageLoaderProvider();
    }

    //single instance
    public static TxImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (TxImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new TxImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public synchronized void init(BaseImageLoaderProvider imageLoaderProvider) {
        this.mProvider = imageLoaderProvider;
    }

    public void loadImage(Context context, CommonImageLoader img) {
        mProvider.loadImage(context, img);
    }

    public void loadImage(Context context, CommonImageLoader img, BaseImageLoaderProvider provider) {
        provider.loadImage(context, img);
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadImage(Context context, String url, ImageView imageView, BaseImageLoaderProvider provider) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .build();
        provider.loadImage(context, imageLoader);
    }

    public void loadImageCircle(Context context, String url, ImageView imageView) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .asCircle()
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadImageCircle(Context context, String url, ImageView imageView, int placeHolder) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .placeHolder(placeHolder)
                .imgView(imageView)
                .asCircle()
                .build();
        mProvider.loadImage(context, imageLoader);
    }
}
