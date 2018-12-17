package com.wangyd.dingding.core.utils;

import android.text.TextUtils;

/**
 * @author wangyd
 * @date 2018/6/22
 * @description description
 */
public class BitmapUtils {

    /**
     * 获取字符串首个字符
     */
    public static String getFirstChar(String name) {
        if (!TextUtils.isEmpty(name)) {
            char[] chars = name.trim().toCharArray();
            if (chars.length > 0) {
                return String.valueOf(chars[0]).toUpperCase();
            }
        }
        return " ";
    }

//    /**
//     * 画文字头像
//     *
//     * @param text         文字
//     * @param circleWidth  图片宽度
//     * @param circleHeight 图片高度
//     * @param backColor    圆的颜色
//     * @param txtColor     文字的颜色
//     * @return
//     */
//    public static Bitmap drawTxtBitmap(String text, int circleWidth, int circleHeight,
//                                       @ColorInt int backColor, @ColorInt int txtColor) {
//        //做判断，如果传入的宽度为0，则默认为60
//        if (circleWidth <= 0) {
//            circleWidth = 60;
//        }
//        //做判断，如果传入的高度为0，则默认为60
//        if (circleHeight <= 0) {
//            circleHeight = 60;
//        }
//        //如果传入的文字为空，则设置为空格
//        if (text == null) {
//            text = " ";
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(circleWidth, circleHeight, Bitmap.Config.ARGB_4444);
//        Canvas canvas = new Canvas(bitmap);
//        canvas.drawARGB(0, 0, 0, 0);
//        //canvas.drawColor(bitmapColor); // 图片背景颜色
//
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(backColor);
//        int radius = Math.min(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius, paint);
//
//        Paint txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        txtPaint.setTextSize(2 * bitmap.getHeight() / 5);
//        txtPaint.setColor(txtColor);
//        txtPaint.setStrokeWidth(10);
//        float txtWid = txtPaint.measureText(text);
//        if (text.length() == 1) {
//            canvas.drawText(text,
//                    (bitmap.getWidth() - txtWid) / 2,
//                    bitmap.getHeight() / 2 + bitmap.getHeight() / 7, txtPaint);
//        } else {
//            canvas.drawText(text,
//                    (bitmap.getWidth() - txtWid) / text.length(),
//                    bitmap.getHeight() / 2 + bitmap.getHeight() / 7, txtPaint);
//        }
//        canvas.save();
//        canvas.restore();
//
//        return bitmap;
//    }

}
