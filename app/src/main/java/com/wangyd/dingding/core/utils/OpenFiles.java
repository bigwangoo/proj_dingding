package com.wangyd.dingding.core.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.villagedoctor.BuildConfig;

import java.io.File;

/**
 * @author:jjj
 * @data:2018/7/3
 * @description:
 */

public class OpenFiles {

    public static String AUTHORITY = BuildConfig.APPLICATION_ID + ".FileProvider";

    public static void openFileByPath(Context context, String path) {
        if (context == null || path == null) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setAction(Intent.ACTION_VIEW);

        //文件的类型
        String type = "";
        for (int i = 0; i < MATCH_ARRAY.length; i++) {
            //判断文件的格式
            if (path.toString().contains(MATCH_ARRAY[i][0].toString())) {
                type = MATCH_ARRAY[i][1];
                break;
            }
        }
        File file = new File(path);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, AUTHORITY, file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        try {
            //设置intent的data和Type属性
            intent.setDataAndType(uri, type);
            //跳转
            context.startActivity(intent);
//            Intent.createChooser(intent, "请选择对应的软件打开该附件！");
        } catch (Exception e) { //当系统没有携带文件打开软件，提示
            ToastUtils.show("无法打开该格式文件!");
            e.printStackTrace();
        }
    }

    //建立一个文件类型与文件后缀名的匹配表
    public static final String[][] MATCH_ARRAY = {
            //{后缀名，    文件类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".file_jpg", "image/*"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".file_ppt", "application/vnd.ms-powerpoint"},
            {".prop", "text/plain"},
            {".rar", "application/x-rar-compressed"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".file_zip", "application/file_zip"},
            {"", "*/*"}
    };
}
