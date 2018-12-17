package com.tianxiabuyi.txutils.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.util.TxLog;

import java.io.File;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * @author wangyd
 * @date 2017/12/15
 * @description 文件相关    建议把项目文件统一处理，放在txby/app包名/分类
 */
public class FileUtils {

    public static final String ROOT = "txby";           // 存储卡下目录
    public static final String DOWNLOAD = "download";   // 下载
    public static final String FILE = "file";           // 文件
    public static final String ICON = "icon";           // 图片
    public static final String CACHE = "cache";         // 缓存
    public static final String TEMP = "temp";           // 临时

    /**
     * SDCard 是否存在
     */
    public static boolean isSDCardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable();
    }

    /**
     * 存储卡目录
     * <p>
     * txby/com.x.x/uniqueName/
     */
    public static String getExternalRootPtah(@Nullable String uniqueName) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardExist()) {
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            sb.append(File.separator);
            sb.append(ROOT);
            sb.append(File.separator);
            sb.append(TxUtils.getInstance().getContext().getPackageName());
            sb.append(File.separator);
        } else {
            sb.append(TxUtils.getInstance().getContext().getCacheDir().getAbsolutePath());
            sb.append(File.separator);
        }
        if (!TextUtils.isEmpty(uniqueName)) {
            sb.append(uniqueName);
            sb.append(File.separator);
        }
        return sb.toString();
    }

    public static File getExternalRootDir(@Nullable String uniqueName) {
        try {
            // 权限
            File file = new File(getExternalRootPtah(uniqueName));
            if (!file.exists() || !file.isDirectory()) {
                boolean mkdirs = file.mkdirs();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sd卡应用缓存目录
     * <p>
     * android/data/com.x.x/cache/uniqueName/
     */
    public static String getExternalCachePath(@Nullable String uniqueName) {
        String cachePath;
        File cacheDir;
        if (isSDCardExist() && (cacheDir = TxUtils.getInstance().getContext().getExternalCacheDir()) != null) {
            cachePath = cacheDir.getAbsolutePath();
        } else {
            cachePath = TxUtils.getInstance().getContext().getCacheDir().getAbsolutePath();
        }
        if (!TextUtils.isEmpty(uniqueName)) {
            cachePath = cachePath + File.separator + uniqueName + File.separator;
        }
        return cachePath;
    }

    public static File getExternalCacheDir(@Nullable String uniqueName) {
        try {
            File file = new File(getExternalCachePath(uniqueName));
            if (!file.exists() || !file.isDirectory()) {
                boolean mkdirs = file.mkdirs();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sd卡下载目录
     * <p>
     * download/
     */
    public static String getExternalDownloadPath() {
        String cacheDir;
        if (isSDCardExist()) {
            cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            cacheDir = TxUtils.getInstance().getContext().getCacheDir().getAbsolutePath();
        }
        return cacheDir + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator;
    }

    public static File getExternalDownloadDir() {
        try {
            File file = new File(getExternalDownloadPath());
            if (!file.exists() || !file.isDirectory()) {
                boolean mkdirs = file.mkdirs();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    ///////////////////////////////////

    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long fileSize) {
        String fileSizeString = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileSize < 1024) {
            if (fileSize == 0) {
                fileSizeString = "0.0MB";
            } else {
                fileSizeString = df.format((double) fileSize) + "B";
            }
        } else if (fileSize < 1024 * 1024) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 清除所有缓存
     *
     * @param context context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir().getAbsolutePath());
        if (isSDCardExist()) {
            deleteDir(context.getExternalCacheDir().getAbsolutePath());
        }
    }

    /**
     * 获取缓存大小
     *
     * @param context context
     * @return size
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = 0;
        // Cache路径
        cacheSize = getFileSize(context.getCacheDir());
        if (isSDCardExist()) {
            // SD卡获取路径
            cacheSize += getFileSize(context.getExternalCacheDir());
        }
        return formatFileSize(cacheSize);
    }


    /**
     * 获取剩余空间
     */
    public static String getRestSize() {
        // TODO
        return "";
    }

    /**
     * 递归求取文件大小
     */
    public static long getFileSize(File path) {
        if (path == null) {
            return 0;
        }
        long size = 0;
        File[] files = path.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size = size + getFileSize(file);
                } else {
                    size = size + file.length();
                }
            }
        } else {
            size = path.length();
        }
        return size;
    }

    /**
     * 递归求取目录文件个数
     */
    public static long getFilelist(File path) {
        long size = 0;
        File[] files = path.listFiles();
        size = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                size = size + getFilelist(file);
                size--;
            }
        }
        return size;
    }

    /**
     * 检索目录中的以head为头的文件;
     */
    public static LinkedList<File> getFile(File parent, String head) {
        LinkedList<File> listsFiles = new LinkedList<File>();
        if (parent.exists()) {
            String[] list = parent.list();
            if (list != null) {
                for (String name : list) {
                    if (name.startsWith(head)) {
                        listsFiles.add(new File(parent.getAbsoluteFile() + File.separator + name));
                    }
                }
            }
        }
        return listsFiles;
    }

    /**
     * 删除文件夹下文件
     * <p>
     * mmp CoolPad手机 删除cache目录后, 再次调用context.getExternalCacheDir()闪退
     * 所以只能删除目录下文件 不包括目录
     *
     * @param path path
     */
    public static void deleteDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            String[] children = file.list();
            for (String name : children) {
                deleteDir(new File(file, name).getAbsolutePath());
            }
        } else {
            if (file.isFile()) {
                boolean delete = file.delete();
            }
        }
    }

    /**
     * 递归创建文件夹
     *
     * @param dirPath 目录
     * @return 创建失败返回 ""
     */
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if (file.getParentFile().exists()) {
                TxLog.i("----- 创建文件夹" + file.getAbsolutePath());
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                TxLog.i("----- 创建文件夹" + file.getAbsolutePath());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }

    /**
     * 递归创建文件夹
     *
     * @param file
     * @return 创建失败返回 ""
     */
    public static String createFile(File file) {
        try {
            if (file.getParentFile().exists()) {
                TxLog.i("----- 创建文件" + file.getAbsolutePath());
                boolean newFile = file.createNewFile();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                boolean newFile = file.createNewFile();
                TxLog.i("----- 创建文件" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断文件路径是否存在
     *
     * @param filePath path
     * @return true 存在 false 不存在
     */
    public static boolean isExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 获取文件名
     */
    public static String getFileName(File file) {
        return file.getName().substring(file.getName().indexOf("."));
    }

    /**
     * 获取文件名
     */
    public static String getFileNameFromUrl(String url) {
        try {
            String[] urlArray = url.split("/");
            int length = urlArray.length;
            return urlArray[length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 Bitmap大小
     */
    public static long getBitmapsize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 获取文件 MIMEType
     */
    public static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        // 获取文件的后缀名
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end.equals("")) return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (String[] mimeTable : MIME_MAP_TABLE) {
            // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(mimeTable[0])) {
                type = mimeTable[1];
            }
        }
        return type;
    }

    private static final String[][] MIME_MAP_TABLE = {
            //{后缀名，MIME类型}
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".pdf", "application/pdf"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    /**
     * 获字符串MD5
     */
    public static String md5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            if (paramString != null) {
                localMessageDigest.update(paramString.getBytes());
            }
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * 将指定byte数组转换成16进制字符串
     */
    @SuppressLint("DefaultLocale")
    public static String byteToHexString(byte[] b) {
        StringBuilder hexString = new StringBuilder();
        for (byte aB : b) {
            String hex = Integer.toHexString(aB & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

}
