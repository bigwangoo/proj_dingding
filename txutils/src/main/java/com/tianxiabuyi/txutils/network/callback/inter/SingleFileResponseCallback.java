package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxFileResult;

/**
 * @author xjh1994
 * @date 2016/8/31
 * @description 单文件下载回调
 */
public interface SingleFileResponseCallback {

    void onProgress(int progress, long total);

    void onSuccess(TxFileResult response);

    void onError(TxException e);
}
