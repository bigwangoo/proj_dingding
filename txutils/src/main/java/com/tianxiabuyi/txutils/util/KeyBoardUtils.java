package com.tianxiabuyi.txutils.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 打开或关闭软键盘
 */
public class KeyBoardUtils {

    /**
     * 打卡软键盘
     *
     * @param view     输入框
     * @param mContext 上下文
     */
    public static void openKeybord(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param view     输入框
     * @param mContext 上下文
     */
    public static void closeKeybord(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 是否打开
     *
     * @param view
     * @param mContext
     * @return
     */
    public static boolean isShow(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.isActive();
    }

}
