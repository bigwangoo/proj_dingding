package com.tianxiabuyi.txutils.network.service;

import com.google.gson.JsonElement;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.model.TokenResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author xjh1994
 * @date 2016/8/18
 */
public interface TxUserService {

    @POST(TxConstants.TOKEN_REFRESH_URL)
    Call<TokenResult> refreshToken(@Query("token") String token);

    @POST(TxConstants.TOKEN_REVOKE_URL)
    TxCall<HttpResult> logout();

    @POST(TxConstants.USER_CREATE_URL)
    TxCall<HttpResult> register(@QueryMap Map<String, String> queryMap);

    @POST(TxConstants.USER_LOGIN_URL)
    TxCall<JsonElement> login(@Query("user_name") String username,
                              @Query("password") String password);

    @POST(TxConstants.USER_PASSWORD_URL)
    TxCall<HttpResult> updatePassword(@Query("old_password") String old_password,
                                      @Query("password") String password,
                                      @Query("repassword") String repassword);

    @POST(TxConstants.USER_AVATAR_URL)
    TxCall<HttpResult> updateAvatar(@Query("avatar") String avatar);
}
