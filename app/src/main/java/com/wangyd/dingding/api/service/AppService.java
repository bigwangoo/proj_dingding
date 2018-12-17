package com.wangyd.dingding.api.service;

import com.tianxiabuyi.txutils.network.TxCall;
import com.wangyd.dingding.api.model.WeatherResult;
import com.wangyd.dingding.api.API;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author wangyd
 * @date 2018/12/10
 */
public interface AppService {
    /**
     * 和风天气
     *
     * @param location 定位方式
     * @param key      api key
     */
    @GET(API.ULR_WEATHER)
    TxCall<WeatherResult> getWeather(@Query("location") String location, @Query("key") String key);
}
