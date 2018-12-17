package com.tianxiabuyi.txutils.util;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by xjh1994 on 16/11/11.
 */
public class IntentUtils {

    /**
     * 将文本内容放到系统剪贴板里。
     *
     * @param context
     * @param text
     */
    public static void clip(Context context, String text) {
        android.content.ClipboardManager c = (android.content.ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        if (c != null) {
            c.setPrimaryClip(ClipData.newPlainText(text, text));
        }
    }

    /**
     * 打开拨号界面，同时传递电话号码
     *
     * @param context
     * @param phone
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 打开短信界面，同时传递电话号码
     *
     * @param context
     * @param phone
     */
    public static void sms(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:+86 " + phone));
        intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }

    /**
     * 打开网页
     *
     * @param context
     * @param url
     */
    public static void startWebsite(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 打开文件
     *
     * @param context
     * @param file
     * @throws Exception
     */
    public static void openFile(Context context, File file) throws Exception {
        //这里最好try一下，有可能会报错。
        //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            //获取文件file的MIME类型
            String type = FileUtils.getMIMEType(file);
            //设置intent的data和Type属性。
            intent.setDataAndType(Uri.fromFile(file), type);
            //跳转
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("apps not installed");
        }
    }
}
