package com.tianxiabuyi.txutils.network.okhttp;

import android.text.TextUtils;

import com.tianxiabuyi.txutils.db.util.IOUtil;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import okhttp3.Response;

/**
 * @author xjh1994
 * @date 16/11/4
 */
public abstract class TxFileCallBack extends Callback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public TxFileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
        return saveFile(response, id);
    }

    private File saveFile(Response response, final int id) throws IOException {
        // 文件名
        if (TextUtils.isEmpty(destFileName)) {
            destFileName = getResponseFileName(response);
            if (TextUtils.isEmpty(destFileName)) {
                String header = response.header("Content-Type");
                destFileName = System.currentTimeMillis() + getFileType(header);
            }
        }
        // 读取
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            final long total = response.body().contentLength();
            is = response.body().byteStream();

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);

            byte[] buf = new byte[2048];
            int len = 0;
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        inProgress(finalSum * 1.0f / total, total, id);
                    }
                });
            }
            fos.flush();

            return file;
        } finally {
            IOUtil.closeQuietly(fos);
            IOUtil.closeQuietly(is);
            IOUtil.closeQuietly(response.body());
        }
    }

    private String getResponseFileName(Response response) {
        String disposition = response.header("Content-Disposition");
        if (disposition == null) {
            return "";
        }

        int startIndex = disposition.indexOf("filename=");
        if (startIndex <= 0) {
            return "";
        }

        // "filename=".length()
        startIndex += 9;
        int endIndex = disposition.indexOf(";", startIndex);
        if (endIndex < 0) {
            endIndex = disposition.length();
        }
        if (endIndex <= startIndex) {
            return "";
        }

        String contentType = response.header("Content-Type");
        if (contentType == null) {
            return "";
        }

        try {
            int index = contentType.lastIndexOf("charset");
            String encode = contentType.substring(index, contentType.length());
            encode = encode.substring(encode.lastIndexOf("=") + 1, encode.length());
            TxLog.e(encode);

            String str = disposition.substring(startIndex, endIndex);
            String name = URLDecoder.decode(str, encode);
            if (name.startsWith("\"") && name.endsWith("\"")) {
                name = name.substring(1, name.length() - 1);
            }
            return name;
        } catch (Exception ex) {
            TxLog.e(ex.getMessage(), ex);
        }

        return "";
    }

    private String getFileType(String header) {
        String type = "";
        for (String[] strings : MIME_MapTable) {
            if (header.equals(strings[1])) {
                type = strings[0];
                break;
            }
        }
        TxLog.e(header + " ==>" + type);

        return type;
    }

    //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
    private final String[][] MIME_MapTable = {
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
            {".apk", "application/vnd.android.package-archive"},
            {"", "*/*"}
    };
}
