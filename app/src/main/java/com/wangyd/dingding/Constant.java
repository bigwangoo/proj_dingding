package com.wangyd.dingding;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 配置常量
 */
public class Constant {

    /*BaseUrl*/
    private static String BASE_URL = BuildConfig.BASE_URL;
    private static String BASE_H5 = "http://wechat.eeesys.com/";

    public static String H5_YZKB = BASE_H5 + "xxt/director/index.html#/";                           // 院长看板
    public static String H5_OLD_MEDICINE = BASE_H5 + "xxt/bodyTest/html/new.html";                  // 老年人中医药
    public static String H5_OLD_MEDICINE_DETAIL = BASE_H5 + "xxt/bodyTest/html/index.html";         // 老年人中医药详情
    public static String H5_OLD_SELF_CARE = BASE_H5 + "xxt/bodyTest/html/assessList.html";          // 老年人生活自理能力
    public static String H5_OLD_SELF_CARE_DETAIL = BASE_H5 + "xxt/bodyTest/html/assessInfo.html";   // 老年人生活自理能力详情

    public static String getBaseUrl() {
        if (BuildConfig.DEBUG) {
            // 开发用
            BASE_H5 = "http://192.168.2.71:8020/";
            H5_YZKB = BASE_H5 + "director-app/dist/index.html#/";
            H5_OLD_MEDICINE = BASE_H5 + "director-app/appshow/html/new.html";
            H5_OLD_MEDICINE_DETAIL = BASE_H5 + "director-app/appshow/html/index.html";
            H5_OLD_SELF_CARE = BASE_H5 + "director-app/appshow/html/assessList.html";
            H5_OLD_SELF_CARE_DETAIL = BASE_H5 + "director-app/appshow/html/assessInfo.html";

            BASE_URL = "http://api.tianxiabuyi.com:18999/";
            BASE_URL = "http://192.168.2.104:8998/"; // 陈恒亮
            BASE_URL = "http://192.168.2.95:8999/";  // 陈佳磊
            BASE_URL = "http://192.168.2.122:8998/"; // 群锅
        }

        return BASE_URL;
    }

    /*基本参数*/
    public static final boolean MODE_DEBUG = BuildConfig.MODE_DEBUG;
    public static final String APP_THEME = BuildConfig.APP_THEME;
    public static final String APP_TYPE = BuildConfig.APP_TYPE;
    public static final String HOSPITAL = BuildConfig.HOSPITAL;
    public static final String USER_TYPE = BuildConfig.USER_TYPE;
    public static final String BUGLY_APP_ID = BuildConfig.BUGLY_APP_ID;
    public static final String UMENG_APP_KEY = BuildConfig.UMENG_APP_KEY;
    public static final String DB_NAME = "villager.db";
    /*传参key*/
    public static final String EXTRA_KEY_1 = "key_1";
    public static final String EXTRA_KEY_2 = "key_2";
    public static final String EXTRA_KEY_3 = "key_3";
    public static final String EXTRA_KEY_4 = "key_4";
    public static final String EXTRA_KEY_RESULT = "key_result";
    /*每页数量*/
    public static final int DEFAULT_PAGE_SIZE = 50;

    /*离线随访*/
    public static final String FOLLOWUP_KEY_ID = "key_followup_id";
    public static final String FOLLOWUP_KEY_INFO = "key_followup_info";
    //新生儿
    public static final String FOLLOWUP_TYPE_NEW_BORN = "100";
    //儿童
    public static final String FOLLOWUP_TYPE_CHILD = "200";
    public static final String FOLLOWUP_TYPE_CHILD1 = "201";
    public static final String FOLLOWUP_TYPE_CHILD2 = "202";
    public static final String FOLLOWUP_TYPE_CHILD3 = "203";
    public static final String FOLLOWUP_TYPE_CHILD4 = "204";
    public static final String FOLLOWUP_TYPE_CHILD5 = "205";
    public static final String FOLLOWUP_TYPE_CHILD6 = "206";
    public static final String FOLLOWUP_TYPE_CHILD7 = "207";
    public static final String FOLLOWUP_TYPE_CHILD8 = "208";
    public static final String FOLLOWUP_TYPE_CHILD9 = "209";
    public static final String FOLLOWUP_TYPE_CHILD10 = "210";
    public static final String FOLLOWUP_TYPE_CHILD11 = "211";
    public static final String FOLLOWUP_TYPE_CHILD12 = "212";
    public static final String FOLLOWUP_TYPE_CHILD13 = "213";
    public static final String FOLLOWUP_TYPE_CHILD14 = "214";
    public static final String FOLLOWUP_TYPE_CHILD15 = "215";
    public static final String FOLLOWUP_TYPE_CHILD16 = "216";
    public static final String FOLLOWUP_TYPE_CHILD17 = "217";
    public static final String FOLLOWUP_TYPE_CHILD18 = "218";
    //孕妇
    public static final String FOLLOWUP_TYPE_PREGNANT = "300";
    public static final String FOLLOWUP_TYPE_PREGNANT1 = "301";
    public static final String FOLLOWUP_TYPE_PREGNANT2 = "302";
    public static final String FOLLOWUP_TYPE_PREGNANT3 = "303";
    public static final String FOLLOWUP_TYPE_PREGNANT4 = "304";
    public static final String FOLLOWUP_TYPE_PREGNANT5 = "305";
    public static final String FOLLOWUP_TYPE_PREGNANT_0 = "306";
    public static final String FOLLOWUP_TYPE_PREGNANT_42 = "307";
    //老年人
    public static final String FOLLOWUP_TYPE_MEDICINE_OLD = "400";
    public static final String FOLLOWUP_TYPE_MEDICINE_SELF_CAFE = "401";
    //血糖
    public static final String FOLLOWUP_TYPE_BLOOD_SUGAR = "500";
    //血压
    public static final String FOLLOWUP_TYPE_BLOOD_PRESSURE = "600";
    //精神
    public static final String FOLLOWUP_TYPE_MENTAL = "700";
    public static final String FOLLOWUP_TYPE_MENTAL_SUPPLY = "701";
    //肺结核
    public static final String FOLLOWUP_TYPE_TUBERCULOSIS = "800";
    public static final String FOLLOWUP_TYPE_TUBERCULOSIS_FIRST = "801";
    public static final String FOLLOWUP_TYPE_TUBERCULOSIS_STOP = "802";

}
