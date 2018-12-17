package com.wangyd.dingding.core.utils;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationQualityReport;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author wangyd
 * @date 2018/7/11
 * @description 定位相关
 */
public class LocationUtils {
    private static final String TAG = "map";

    /**
     * 打印定位成功信息
     */
    public static void logForSuccessLocation(AMapLocation location) {
        Log.e(TAG, "定位成功");
        //定位完成的时间
        Log.e(TAG, "定位类型: " + location.getLocationType());
        Log.e(TAG, "经    度: " + location.getLongitude());
        Log.e(TAG, "纬    度: " + location.getLatitude());
        Log.e(TAG, "精    度: " + location.getAccuracy() + "米");
        Log.e(TAG, "提 供 者: " + location.getProvider());
        Log.e(TAG, "速    度: " + location.getSpeed() + "米/秒");
        Log.e(TAG, "角    度: " + location.getBearing());
        // 获取当前提供定位服务的卫星个数
        Log.e(TAG, "星    数: " + location.getSatellites());
        Log.e(TAG, "国    家: " + location.getCountry());
        Log.e(TAG, "省      : " + location.getProvince());
        Log.e(TAG, "市      : " + location.getCity());
        Log.e(TAG, "城市编码: " + location.getCityCode());
        Log.e(TAG, "区      : " + location.getDistrict());
        Log.e(TAG, "区 域 码: " + location.getAdCode());
        Log.e(TAG, "地    址: " + location.getAddress());
        Log.e(TAG, "兴 趣 点: " + location.getPoiName());
        Log.e(TAG, "定位时间: " + LocationUtils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 打印定位失败信息
     */
    public static void logForFailureLocation(AMapLocation location) {
        //定位失败
        Log.e(TAG, "定位失败");
        Log.e(TAG, "错 误 码:" + location.getErrorCode());
        Log.e(TAG, "错误信息:" + location.getErrorInfo());
        Log.e(TAG, "错误描述:" + location.getLocationDetail());
    }

    /**
     * 获取定位质量信息
     *
     * @param location AMapLocation
     */
    public static void getLocationDoc(AMapLocation location) {
        //定位之后的回调时间
        Log.e(TAG, "***定位质量报告***");
        Log.e(TAG, "* WIFI开关：" + (location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭"));
        Log.e(TAG, "* GPS状态：" + LocationUtils.getGPSStatusString(location.getLocationQualityReport().getGPSStatus()));
        Log.e(TAG, "* GPS星数：" + location.getLocationQualityReport().getGPSSatellites());
        Log.e(TAG, "* 网络类型：" + location.getLocationQualityReport().getNetworkType());
        Log.e(TAG, "* 网络耗时：" + location.getLocationQualityReport().getNetUseTime());
        Log.e(TAG, "****************");
        Log.e(TAG, "回调时间: " + LocationUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return GPS状态码信息
     */
    private static String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
            default:
                break;
        }
        return str;
    }

    /**
     * 格式化时间
     *
     * @param l          time
     * @param strPattern format
     */
    private static String formatUTC(long l, String strPattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            sdf.applyPattern(strPattern);
            return sdf.format(l);
        } catch (Throwable ignored) {
        }
        return "NULL";
    }
}
