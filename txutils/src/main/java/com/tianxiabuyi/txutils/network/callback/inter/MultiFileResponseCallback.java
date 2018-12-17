package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxMultiFileResult;

/**
 * @author xjh1994
 * @date 2016/9/13
 * @description 多文件下载回调
 */
public interface MultiFileResponseCallback {

    void onProgress(int progress, long total);

    void onSuccess(TxMultiFileResult files);

    void onError(TxException e);
}
