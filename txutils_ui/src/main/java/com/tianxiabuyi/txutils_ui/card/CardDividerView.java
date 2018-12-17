//package com.tianxiabuyi.txutils_ui.card;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.DashPathEffect;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.PathEffect;
//import android.graphics.RectF;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.tianxiabuyi.txutils_ui.R;
//import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;
//
///**
// * @author wangyd
// * @date 2018/6/25
// * @description description
// */
//public class CardDividerView extends View {
//
//    private Paint mPaint;
//    private Paint mDashPaint;
//    private int mItemHeight;                // item 高度
//    private int mRadius;                    // 圆点半径
//    private int mCircleLeftPadding;         // 圆点左边padding
//    private int mCircleRightPadding;        // 圆点右边边padding
//    private int mCircleColor;               // 圆点颜色
//    private boolean isShowDivider;          // 底部分割
//    private Path mPath;                     // 虚线
//    private float mDashPaintWidth;          // 虚线高度
//    private float mDashWidth;               // 虚线宽度
//    private float mDashSpaceWidth;          // 虚线间隔宽度
//
//    public CardDividerView(Context context) {
//        this(context, null);
//    }
//
//    public CardDividerView(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CardDividerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CardDividerView);
//        mItemHeight = a.getDimensionPixelSize(R.styleable.CardDividerView_cdvItemHeight, DisplayUtils.dp2px(getContext(), 40));
//        mRadius = a.getDimensionPixelSize(R.styleable.CardDividerView_cdvCircleRadius, DisplayUtils.dp2px(getContext(), 6));
//        mCircleLeftPadding = a.getDimensionPixelSize(R.styleable.CardDividerView_cdvCirclePaddingLeft, DisplayUtils.dp2px(getContext(), 10));
//        mCircleRightPadding = a.getDimensionPixelSize(R.styleable.CardDividerView_cdvCirclePaddingRight, DisplayUtils.dp2px(getContext(), 10));
//        mCircleColor = a.getColor(R.styleable.CardDividerView_cdvCircleColor, 0xFFF1F0EE);
//        isShowDivider = a.getBoolean(R.styleable.CardDividerView_cdvShowDivider, true);
//        if (isShowDivider) {
//            mDashPaintWidth = a.getFloat(R.styleable.CardDividerView_cdvDashHeight, DisplayUtils.dp2px(getContext(), 1));
//            mDashWidth = a.getFloat(R.styleable.CardDividerView_cdvDashWidth, DisplayUtils.dp2px(getContext(), 6));
//            mDashSpaceWidth = a.getFloat(R.styleable.CardDividerView_cdvDashSpaceWidth, DisplayUtils.dp2px(getContext(), 6));
//        }
//        a.recycle();
//
//        init();
//    }
//
//    private void init() {
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(mCircleColor);
//        mPaint.setStyle(Paint.Style.STROKE);
//
//        if (isShowDivider) {
//            //设置虚线的间隔和点的长度
//            PathEffect mEffects = new DashPathEffect(new float[]{
//                    mDashWidth, mDashSpaceWidth, mDashWidth, mDashSpaceWidth}, 0);
//            mPath = new Path();
//            mDashPaint = new Paint();
//            mDashPaint.setAntiAlias(true);
//            mDashPaint.setColor(mCircleColor);
//            mDashPaint.setStyle(Paint.Style.STROKE);
//            mDashPaint.setStrokeWidth(mDashPaintWidth);
//            mDashPaint.setPathEffect(mEffects);
//        }
//
//
//    }
//
//
//    RectF rectRT;
//    RectF rectRB;
//    RectF rectLT;
//    RectF rectLB;
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        int measuredWidth = getMeasuredWidth();
//        int measuredHeight = getMeasuredHeight();
//        Path path = new Path();
//        Path path2 = new Path();
//        path.addCircle(measuredHeight/2, measuredWidth, mRadius, Path.Direction.CCW);
//        path2.addRect(0, 0, measuredWidth, measuredHeight, Path.Direction.CCW);
//        path.op(path2, Path.Op.REVERSE_DIFFERENCE);
//        canvas.drawPath(path, mPaint);
//
//
//
//
//
////        int offSet = DisplayUtils.dp2px(getContext(), 1);
////        int y = getMeasuredHeight() / 2;
////        canvas.drawCircle(offSet, y, getRadius(), mPaint);
////        canvas.drawCircle(getMeasuredWidth() - offSet, y, getRadius(), mPaint);
////
////        if (isShowDivider) {
////            //虚线 起始坐标
////            mPath.moveTo(getRadius() + 10, y);
////            mPath.lineTo(getMeasuredWidth() - getRadius() - 10, y);
////            canvas.drawPath(mPath, mDashPaint);
////        }
//    }
//}