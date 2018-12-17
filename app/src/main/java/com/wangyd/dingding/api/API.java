package com.wangyd.dingding.api;

/**
 * @author wangyd
 * @date 2018/11/5
 * @description 接口地址
 */
public class API {

    // 天气
    public static final String ULR_WEATHER = "https://free-api.heweather.com/s6/weather/now?parameters";

    // 短信
    public static final String SMS_SEND = "sms/sendSms";
    public static final String SMS_CHECK = "sms/checkCode";

    // 用户
    public static final String USER_LOGIN = "user/login";
    public static final String USER_INFO = "user/selectInfo";

}