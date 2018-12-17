package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;

import java.io.File;

/**
 * @author xjh1994
 * @date 2016/8/31
 * @description 文件下载回调
 */
public interface FileResponseCallback {

    void onProgress(int progress, long total);

    void onSuccess(File response);

    void onError(TxException e);
}
