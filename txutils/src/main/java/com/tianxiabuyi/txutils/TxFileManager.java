package com.tianxiabuyi.txutils;

import android.net.Uri;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.db.util.LogUtil;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.callback.inter.FileResponseCallback;
import com.tianxiabuyi.txutils.network.callback.inter.MultiFileResponseCallback;
import com.tianxiabuyi.txutils.network.callback.inter.SingleFileResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxFileResult;
import com.tianxiabuyi.txutils.network.model.TxMultiFileResult;
import com.tianxiabuyi.txutils.network.okhttp.TxFileCallBack;
import com.tianxiabuyi.txutils.network.service.TxFileService;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.FileUtils;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.tianxiabuyi.txutils.util.UriUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * @author xjh1994
 * @date 2016/8/19
 * @description 文件管理（图片上传、下载）
 */
public class TxFileManager {

    /**
     * 下载目录地址
     */
    public static String getDownloadPath() {
        String downloadPath = FileUtils.getExternalDownloadPath();
        String pkg = TxUtils.getInstance().getContext().getPackageName();
        // String pathName = pkg.substring(pkg.lastIndexOf(".") + 1, pkg.length());
        // File file = new File(downloadPath, pathName);
        File file = new File(downloadPath, pkg);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
        }
        return file.getAbsolutePath();
    }


    //////////////////////////////////  上传 //////////////////////////////////////////////////////

    /**
     * 通过path上传文件
     *
     * @param path     文件地址
     * @param callback 回调
     * @return TxCall
     */
    public static TxCall upload(String path, ResponseCallback<TxFileResult> callback) {
        LogUtil.e("upload: " + path);
        if (path == null) {
            callback.onError(new TxException("文件路径为空"));
            return null;
        }

        TxFileService service = TxServiceManager.createService(TxFileService.class,
                new OkHttpClient.Builder()
                        .connectTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                        .build());

        String fileName = getRandomFileName(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(path));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestBody);

        TxCall<TxFileResult> txCall = service.uploadImage(fileName, "file", body);
        txCall.enqueue(callback);
        return txCall;
    }

    /**
     * 通过Uri上传文件
     *
     * @param uri      资源地址
     * @param callback 回调
     * @return TxCall
     */
    public static TxCall upload(Uri uri, ResponseCallback<TxFileResult> callback) {
        LogUtil.e("upload: " + uri);

        String path = uri.getPath();
        File file = null;
        if (path != null) {
            file = new File(path);
        }
        if (file == null || !file.exists()) {
            path = UriUtils.getPathFromUri(TxUtils.getInstance().getContext(), uri);
        }

        return upload(path, callback);
    }

    /**
     * 通过Uri上传单个文件  带进度
     *
     * @param uri      资源地址
     * @param callback 回调
     */
    public static void upload(Uri uri, final SingleFileResponseCallback callback) {
        LogUtil.e("upload: " + uri);

        String path = uri.getPath();
        File file = null;
        if (path != null) {
            file = new File(path);
        }
        if (file == null || !file.exists()) {
            path = UriUtils.getPathFromUri(TxUtils.getInstance().getContext(), uri);
        }

        OkHttpUtils.post()
                .addFile("file", getRandomFileName(path), new File(path))
                .url(TxConstants.UPLOAD_FILE_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(String response, int id) {
                        TxFileResult result = GsonUtils.fromJson(response, TxFileResult.class);
                        if (result != null && result.isSuccess()) {
                            callback.onSuccess(result);
                        } else {
                            callback.onError(new TxException(response));
                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        callback.onProgress((int) (100 * progress), total);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(new TxException(e.getMessage()));
                    }
                });
    }

    /**
     * 多文件上传
     *
     * @param paths    路径集合
     * @param callback 回调
     */
    public static RequestCall uploadMulti(List<String> paths,
                                          final MultiFileResponseCallback callback) {
        if (paths == null) {
            callback.onError(new TxException("图片路径为空"));
            return null;
        }

        Map<String, String> params = new HashMap<>(2);
        params.put("act", "multi");

        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        int size = paths.size();
        for (int i = 0; i < size; i++) {
            File file = new File(paths.get(i));
            if (!file.exists()) {
                TxLog.d("文件不存在");
                return null;
            }
            postFormBuilder.addFile("file[]", file.getName(), file);
        }
        RequestCall requestCall = postFormBuilder
                .url(TxConstants.UPLOAD_FILE_URL).params(params).build();
        requestCall.execute(new StringCallback() {
            @Override
            public void onResponse(String response, int id) {
                TxMultiFileResult result = GsonUtils.fromJson(response, TxMultiFileResult.class);
                if (result != null && result.isSuccess()) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(new TxException(response));
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                callback.onProgress((int) (100 * progress), total);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                callback.onError(new TxException(e.getMessage()));
            }
        });

        return requestCall;
    }


    //////////////////////////////////  下载 //////////////////////////////////////////////////////

    /**
     * 下载文件
     *
     * @param fileUrl  文件地址
     * @param filePath 下载后保存的路径
     * @param fileName 文件名
     * @param callback 回调
     */
    public static RequestCall download(String fileUrl, String filePath, String fileName,
                                       final FileResponseCallback callback) {
        RequestCall call = OkHttpUtils.get().url(fileUrl).tag(fileUrl).build();
        call.execute(new TxFileCallBack(filePath, fileName) {
            @Override
            public void onResponse(File response, int id) {
                callback.onSuccess(response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                callback.onProgress((int) (100 * progress), total);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                callback.onError(new TxException(e.getMessage()));
            }
        });
        return call;
    }

    /**
     * 下载文件  需要url、保存路径
     *
     * @param fileUrl  文件地址
     * @param filePath 下载后保存的路径
     * @param callback 回调
     */
    public static RequestCall download(String fileUrl, String filePath,
                                       final FileResponseCallback callback) {
        return download(fileUrl, filePath, "", callback);
    }

    /**
     * 下载文件  需要url、文件名
     *
     * @param fileUrl  文件地址
     * @param fileName 文件名
     * @param callback 回调
     */
    public static RequestCall download2(String fileUrl, String fileName,
                                        final FileResponseCallback callback) {
        return download(fileUrl, getDownloadPath(), fileName, callback);
    }

    /**
     * 下载文件 只需url
     *
     * @param fileUrl  文件地址
     * @param callback 回调
     */
    public static RequestCall download2(String fileUrl, final FileResponseCallback callback) {
        return download(fileUrl, getDownloadPath(), "", callback);
    }


    /**
     * 获取文件类型
     */
    private static String guessMimeType(String path) {
        if (path.toLowerCase().endsWith(".jpg")) {
            return "image/jpeg";
        }
        if (path.toLowerCase().endsWith(".png")) {
            return "image/png";
        }
        if (path.toLowerCase().endsWith(".gif")) {
            return "image/gif";
        }
        //任意的二进制数据
        return "application/octet-stream";
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isExistFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 根据当前日期拼接文件名
     */
    public static String getRandomFileName(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return TextUtils.concat(sdf.format(new Date()), path).toString();
    }

}
