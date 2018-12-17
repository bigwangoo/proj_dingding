package com.tianxiabuyi.txutils.network.util;

import android.text.TextUtils;
import android.util.Log;

import com.tianxiabuyi.txutils.TxUtils;

/**
 * @author wangyd
 * @date 2018/8/3
 * @description 简单日志工具 tag格式: customTagPrefix:className.methodName(L:lineNumber)
 */
public class TxLog {

    public static String customTagPrefix = "TxLog";

    private TxLog() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void v(String content) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content, tr);
    }

    public static void d(String content) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content, tr);
    }

    public static void i(String content) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content, tr);
    }

    public static void w(String content) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.w(tag, tr);
    }

    public static void e(String content) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.e(tag, content, tr);
    }

    public static void wtf(String content) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!TxUtils.getInstance().getConfiguration().isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, tr);
    }

    /**
     * 规定每段显示的长度
     */
    private static int maxLogLength = 2000;

    /**
     * 打印较大日志的工具类
     */
    public static void e(String tag, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = maxLogLength;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(tag + i, msg.substring(start, end));
                start = end;
                end = end + maxLogLength;
            } else {
                Log.e(tag, msg.substring(start, strLength));
                break;
            }
        }
    }
}
