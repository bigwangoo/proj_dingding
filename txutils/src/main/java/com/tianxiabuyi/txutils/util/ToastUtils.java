package com.tianxiabuyi.txutils.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.tianxiabuyi.txutils.TxUtils;

/**
 * @author xjh1994
 * @date 2016/7/15
 * @description 吐司工具类 TODO 修改添加可覆盖的toast
 */
public class ToastUtils {

    public static boolean isShow = true;

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 不需要有Context
     */
    public static void show(@StringRes int message) {
        show(TxUtils.getInstance().getContext(), message);
    }

    public static void show(CharSequence message) {
        show(TxUtils.getInstance().getContext(), message);
    }

    public static void showLong(@StringRes int message) {
        showLong(TxUtils.getInstance().getContext(), message);
    }

    public static void showLong(CharSequence message) {
        showLong(TxUtils.getInstance().getContext(), message);
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShort(Context context, @StringRes int message) {
        if (isShow) {
            Toast.makeText(context, context.getString(message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void showLong(Context context, @StringRes int message) {
        if (isShow) {
            Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG).show();
        }
    }

    public static void show(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void show(Context context, @StringRes int message) {
        if (isShow) {
            Toast.makeText(context, context.getString(message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            Toast.makeText(context, message, duration).show();
        }
    }

    public static void show(Context context, @StringRes int message, int duration) {
        if (isShow) {
            Toast.makeText(context, context.getString(message), duration).show();
        }
    }

}
