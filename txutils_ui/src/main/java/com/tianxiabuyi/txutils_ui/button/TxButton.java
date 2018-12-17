package com.tianxiabuyi.txutils_ui.button;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author xjh1994
 * @date 16/11/22
 */
public class TxButton extends android.support.v7.widget.AppCompatButton {

    public TxButton(Context context) {
        this(context, null);
    }

    public TxButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TxButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }
}
