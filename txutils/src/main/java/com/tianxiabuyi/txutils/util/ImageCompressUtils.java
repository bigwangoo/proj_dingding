package com.tianxiabuyi.txutils.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.tianxiabuyi.txutils.db.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @author wangyd
 * @date 2018/7/6
 * @description 图片压缩工具 ,请不要使用
 */
@Deprecated
public class ImageCompressUtils {

    /**
     * 图片压缩
     *
     * @param filePath 图片路径
     * @return 返回bitmap用于显示
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Decode bitmap
        options.inSampleSize = calculateInSampleSize(options, 960, 1600);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 图片压缩
     *
     * @param context
     * @param filePath 图片路径
     * @return 返回path用于显示
     */
    public static String compressImage(Context context, String filePath) {
        // 分辨率压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        // 打开空图片获取分辨率
        BitmapFactory.decodeFile(filePath, newOpts);
        // 设置缩放倍数
        newOpts.inSampleSize = calculateInSampleSize(newOpts, 960, 1600);
        newOpts.inJustDecodeBounds = false;
        try {
            Bitmap bitmap1 = BitmapFactory.decodeFile(filePath, newOpts);
            // 质量压缩 aplha 通道深度变小
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        } catch (OutOfMemoryError e) {
            //图片上传压缩初次分辨率失败
            newOpts.inSampleSize = newOpts.inSampleSize + 2;
            Bitmap bitmap1 = BitmapFactory.decodeFile(filePath, newOpts);
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        }

        LogUtil.e("压缩后分辨率：" + newOpts.outWidth + "*" + newOpts.outHeight);
        LogUtil.e("分辨率压缩后的大小:" + (baos.toByteArray().length / 1024) + "");

        Bitmap bitmap = null;
        int options = 90;
        // 限制图片的大小最多为500k
        while (baos.toByteArray().length / 1024 > 500) {
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
                        baos.toByteArray().length);
            } else {
                baos.reset();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 5;
        }

        LogUtil.e("质量压缩后的大小:" + (baos.toByteArray().length / 1024) + "");

        FileOutputStream out = null;
        // 新建文件复制图片
        File file = getOutputMediaFile(context);
        try {
            out = new FileOutputStream(file);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            try {
                out.close();
                baos.reset();
                baos = null;
            } catch (Exception e1) {
                e1.printStackTrace();
                // 保证图片的地址里面有内容
                return filePath;
            }
            e.printStackTrace();
            // 保证图片的地址里面有内容
            return filePath;
        }
        return file.getAbsolutePath();
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * @param context
     * @return
     */
    public static File getImageDir(Context context) {
        File dir = null;
        try {
            dir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getPackageName());
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    // <uses-permission
                    // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }

    /**
     * @param context
     * @return
     */
    public static File getOutputMediaFile(Context context) {
        File dir = getImageDir(context);
        String filename = UUID.randomUUID().toString();
        return new File(dir.getPath() + File.separator + filename + ".jpg");
    }

    /**
     * 删除所有图片
     */
    public static void deleteImages(Context context) {
        File[] files = getImageDir(context).listFiles();
        for (File file : files) {
            file.delete();
        }
    }

}
