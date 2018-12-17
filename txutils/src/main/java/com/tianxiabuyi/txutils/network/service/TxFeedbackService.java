package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.HttpResult;

import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author xjh1994
 * @date 16/11/15
 * @description 意见反馈接口
 */
public interface TxFeedbackService {

    @POST("app/feedback")
    TxCall<HttpResult> sendFeedback(@Query("device") int device,
                                    @Query("version") String version,
                                    @Query("version_code") int version_code,
                                    @Query("suggestion") String suggestion,
                                    @Query("grade") float grade,
                                    @Query("uid") String uid);
}
