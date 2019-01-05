package com.wangyd.dingding.api.service;

import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.wangyd.dingding.api.API;

import retrofit2.http.POST;

/**
 * @author wangyd
 * @date 2018/12/27
 * @description
 */
public interface UserService {

    @POST(API.USER_REGISTER)
    TxCall<HttpResult> register();
}
