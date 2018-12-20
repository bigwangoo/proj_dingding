package com.wangyd.dingding.core.utils;

import com.google.gson.reflect.TypeToken;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.tianxiabuyi.txutils.util.SpUtils;
import com.wangyd.dingding.module.login.model.User;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 用户数据缓存
 */
public class UserSpUtils {
    // 文件名
    private static final String FILE_NAME = "tx_user";
    private static UserSpUtils instance;
    private SpUtils spUtils;

    private UserSpUtils() {
        spUtils = new SpUtils(TxUtils.getInstance().getContext(), FILE_NAME);
    }

    public static UserSpUtils getInstance() {
        if (instance == null) {
            synchronized (UserSpUtils.class) {
                if (instance == null) {
                    instance = new UserSpUtils();
                }
            }
        }
        return instance;
    }

    public void clear() {
        spUtils.clear();
    }




}
