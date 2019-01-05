package com.wangyd.dingding.api.manager;

import com.tianxiabuyi.txutils.TxServiceManager;
import com.tianxiabuyi.txutils.network.TxCall;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.WeatherResult;
import com.wangyd.dingding.api.service.AppService;

/**
 * Created by wangyd on 2018/12/10.
 */
public class AppServiceManager {

    private static AppService service = TxServiceManager.createService(AppService.class);

    public static TxCall getWeather(String location, MyResponseCallback<WeatherResult> callback) {
        TxCall<WeatherResult> txCall = service.getWeather("", "");
        txCall.enqueue(callback);
        return txCall;
    }
}
