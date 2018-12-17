package com.tianxiabuyi.txutils_ui.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.tianxiabuyi.txutils_ui.R;

/**
 * @author xjh1994
 * @date 17/3/28
 * @description the editText can clear content
 */
public class CleanableEditText extends android.support.v7.widget.AppCompatEditText {
    // default clearImage size
    private final int DEFAULT_SIZE = 15;
    private Drawable mRightDrawable;
    private int clearImageSize;
    private boolean isHasFocus;

    public CleanableEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditText);
        clearImageSize = array.getDimensionPixelSize(R.styleable.CleanableEditText_clear_size, dip2px(context, DEFAULT_SIZE));
        array.recycle();

        //getCompoundDrawables: Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawables = this.getCompoundDrawables();
        //android:drawableRight
        mRightDrawable = drawables[2];
        if (mRightDrawable == null) {
            mRightDrawable = context.getResources().getDrawable(R.drawable.tx_clear);
        }
        mRightDrawable.setBounds(0, 0, clearImageSize, clearImageSize);

        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        this.addTextChangedListener(new TextWatcherImpl());

        setClearDrawableVisible(false);
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @NonNull
    @Override
    public Editable getText() {
        Editable text = super.getText();
        if (text == null) {
            return new SpannableStringBuilder("");
        }
        return text;
    }

    @NonNull
    public Editable getTextNonNull() {
        Editable text = super.getText();
        if (text == null) {
            return new SpannableStringBuilder("");
        }
        return text;
    }

    public void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                rightDrawable, getCompoundDrawables()[3]);
    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }

    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible = getText().toString().length() >= 1;
            if (hasFocus()) {
                setClearDrawableVisible(isVisible);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    }
}
