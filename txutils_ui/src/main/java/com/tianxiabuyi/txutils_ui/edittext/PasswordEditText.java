package com.tianxiabuyi.txutils_ui.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

/**
 * @author xjh1994
 * @date 17/3/28
 */
public class PasswordEditText extends android.support.v7.widget.AppCompatEditText {

    private static final int DEFAULT_SIZE = 15;

    private Drawable mEyeIconDrawable;
    private int imageSize;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);

        mEyeIconDrawable = ta.getDrawable(R.styleable.PasswordEditText_vpeIcon);
        imageSize = ta.getDimensionPixelSize(R.styleable.PasswordEditText_vpeIconSize, DisplayUtils.dip2px(context, DEFAULT_SIZE));

        Drawable[] drawables = this.getCompoundDrawables();

        mEyeIconDrawable = drawables[2];
        if (mEyeIconDrawable == null) {
            mEyeIconDrawable = getResources().getDrawable(R.drawable.ic_eye_close);
        }
        mEyeIconDrawable.setBounds(0, 0, imageSize, imageSize);

        ta.recycle();

        setClearDrawableVisible(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isShow = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isShow) {
                    setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    setClearDrawableVisible(true);
                    setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                return true;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Nullable
    @Override
    public Editable getText() {
        Editable text = super.getText();
        if (text == null) {
            return new SpannableStringBuilder("");
        }
        return text;
    }

    protected void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mEyeIconDrawable;
        } else {
            rightDrawable = getResources().getDrawable(R.drawable.ic_eye_open);
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                rightDrawable, getCompoundDrawables()[3]);
    }
}
