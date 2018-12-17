package com.tianxiabuyi.txutils_ui.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by xjh1994 on 16/11/23.
 * 设置布局
 */

public class SettingLayout extends LinearLayout {

    public SettingLayout(Context context) {
        this(context, null);
    }

    public SettingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }
}
