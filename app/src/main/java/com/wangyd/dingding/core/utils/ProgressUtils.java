package com.wangyd.dingding.core.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * @author:jjj
 * @data:2018/9/11
 * @description:
 */
public class ProgressUtils {
    private static ProgressDialog mProgressDialog;

    private static void initProgressDialog(Activity activity) {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage("请稍后...");
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public static void showProgressDialog(Activity activity) {
        if (mProgressDialog == null) {
            initProgressDialog(activity);
        }
        mProgressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
