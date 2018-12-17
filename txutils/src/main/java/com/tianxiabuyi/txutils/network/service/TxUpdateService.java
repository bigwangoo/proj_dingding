package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.TxUpdateResult;

import retrofit2.http.POST;

/**
 * @author xjh1994
 * @date 2016/8/31
 * @description 软件更新接口
 */
public interface TxUpdateService {

    @POST(TxConstants.UPDATE_URL)
    TxCall<TxUpdateResult> update();
}
