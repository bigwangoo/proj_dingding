package com.wangyd.dingding.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tianxiabuyi.villagedoctor.R;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author wangyd
 * @date 2018/6/22
 * @description 分享工具
 */
public class ShareUtils {
    private static final String TITLE = "县乡通";
    private static final String TITLE_URL = "http://hosappqq.tianxiabuyi.com/xxyth";
    private static final String TEXT = "我在使用县乡通APP，点击下载吧";
    private static final String TEXT_URL = "http://hosappqq.tianxiabuyi.com/xxyth";

    /**
     * 默认分享APP
     *
     * @param context context
     */
    public static void showShare(Context context) {
        showShare(context, TITLE, TITLE_URL, TEXT, TEXT_URL);
    }

    /**
     * @param context  context
     * @param title    标题
     * @param titleUrl 标题链接
     * @param text     分享文本
     * @param url      url仅在微信
     */
    public static void showShare(Context context, String title, String titleUrl, String text, String url) {
        showShare(context, title, titleUrl, text, url, "", "", "");
    }

    /**
     * @param context  context
     * @param title    标题
     * @param titleUrl 标题链接
     * @param text     分享文本
     * @param url      url仅在微信
     * @param comment  评论
     * @param siteName 网站名称
     * @param siteUrl  网站地址
     */
    public static void showShare(Context context,
                                 String title, String titleUrl,
                                 String text, String url, String comment, String siteName, String siteUrl) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.logo, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数，确保SDcard下面存在此张图片
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        oks.setImageData(bitmap);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(comment);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(siteName);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(siteUrl);

        // 启动分享GUI
        oks.show(context);
    }
}
