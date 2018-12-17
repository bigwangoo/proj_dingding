package com.tianxiabuyi.txutils_ui.button;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tianxiabuyi.txutils_ui.R;

/**
 * @author xjh1994
 * @date 16/11/22
 */
public class TxRectangleButton extends android.support.v7.widget.AppCompatButton {

    public TxRectangleButton(Context context) {
        this(context, null);
    }

    public TxRectangleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TxRectangleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setBg(R.drawable.tx_bg_btn_rectangle_normal);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setBg(R.drawable.tx_bg_btn_rectangle_pressed);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            setBg(R.drawable.tx_bg_btn_rectangle_normal);
        }
        return super.onTouchEvent(event);
    }

    private void setBg(int drawableResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(getResources().getDrawable(drawableResId));
        } else {
            setBackgroundDrawable(getResources().getDrawable(drawableResId));
        }
    }
}
