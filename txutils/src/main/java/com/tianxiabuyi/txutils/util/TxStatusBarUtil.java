package com.tianxiabuyi.txutils.util;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 适配4.4以上版本 MIU_IV、Fly_me和 6.0以上版本其他Android
 *
 * @author wangyd
 * @date 2018/5/9
 * @description 状态栏工具
 */
public class TxStatusBarUtil {

    /**
     * 状态栏亮色模式, 设置状态栏黑色文字、图标
     */
    public static void setStatusBarLightMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (setMIUIStatusBarLightMode(activity, true)) {
                Log.e("status: ", "MIUI");
            } else if (setFlymeStatusBarLightMode(activity.getWindow(), true)) {
                Log.e("status: ", "Flyme");
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                Log.e("status: ", "android");
            }
        }
    }

    /**
     * 状态栏暗色模式, 清除MI_UI、fly_me或6.0以上版本状态栏黑色文字、图标
     */
    public static void setStatusBarDarkMode(Activity activity) {
        if (setMIUIStatusBarLightMode(activity, false)) {
            Log.e("status: ", "MI_UI");
        } else if (setFlymeStatusBarLightMode(activity.getWindow(), false)) {
            Log.e("status: ", "Fly_me");
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            Log.e("status: ", "android");
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，可以用来判断是否为Fly me用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setFlymeStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    /**
     * 需要 MIU IV6以上
     *
     * @param activity Activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setMIUIStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;

                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return result;
    }
}
