package com.tianxiabuyi.txutils_ui.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.tianxiabuyi.txutils_ui.R;


/**
 * @author txby
 * @description CircleButton
 */
public class TxCircleButton extends android.support.v7.widget.AppCompatTextView {
    // default white color
    private final int defaultColor = Color.rgb(255, 255, 255);
    // default 5 dp radius
    private final int defaultRadius = 5;

    private int drawColor = defaultColor;
    private int radius = defaultRadius;

    public TxCircleButton(Context context) {
        super(context);
        initParams(context, null);
    }

    public TxCircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs);
    }

    public TxCircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TxCircleButton);
        radius = array.getDimensionPixelSize(R.styleable.TxCircleButton_radius, dip2px(context, defaultRadius));
        drawColor = array.getColor(R.styleable.TxCircleButton_back_color, defaultColor);
        array.recycle();
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(drawColor);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, this.getWidth(), this.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        super.onDraw(canvas);
    }
}
