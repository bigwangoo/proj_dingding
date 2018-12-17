package com.tianxiabuyi.txutils.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.tianxiabuyi.txutils.TxImageLoader;
import com.tianxiabuyi.txutils.imageloader.BaseImageLoaderProvider;
import com.tianxiabuyi.txutils.imageloader.CommonImageLoader;
import com.tianxiabuyi.txutils.util.NetUtils;

/**
 * Created by xjh1994 on 2016/9/1.
 */

public class GlideImageLoaderProvider extends BaseImageLoaderProvider {

    @Override
    public void loadImage(Context context, CommonImageLoader img) {
        if (img.getStrategy() == TxImageLoader.LOAD_STRATEGY_ONLY_WIFI && !NetUtils.isWifi(context)) {
            //只在WiFi下加载
            img.getImgView().setImageResource(img.getPlaceHolder());
            return;
        }

        DrawableRequestBuilder requestBuilder = Glide.with(context).load(img.getUrl()).placeholder(img.getPlaceHolder());
        if (img.isCircle()) {
            requestBuilder.bitmapTransform(new CropCircleTransformation(context));
        }
        if (img.isRound()) {
            requestBuilder.bitmapTransform(new RoundedCornersTransformation(context, img.getRoundRadius(), 0));
        }
        requestBuilder.into(img.getImgView());
    }
}
