package com.wangyd.dingding.core.utils;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.util.SpUtils;

/**
 * @author wangyd
 * @date 2018/9/28
 * @description 应用数据缓存
 */
public class AppSpUtils {
    // 文件名
    private static final String FILE_NAME = "tx_app_config";
    private static AppSpUtils instance;
    private SpUtils spUtils;

    private AppSpUtils() {
        spUtils = new SpUtils(TxUtils.getInstance().getContext(), FILE_NAME);
    }

    public static AppSpUtils getInstance() {
        if (instance == null) {
            synchronized (AppSpUtils.class) {
                if (instance == null) {
                    instance = new AppSpUtils();
                }
            }
        }
        return instance;
    }

    public void clear() {
        spUtils.clear();
    }

    /**
     * 是否首次使用
     */
    public boolean isFirstOpen() {
        return (boolean) spUtils.get("is_first_open", true);
    }

    public void setFirstOpen(boolean isFirstOpen) {
        spUtils.put("is_first_open", isFirstOpen);
    }

    /**
     * 获取登录名
     */
    public String getLoginName() {
        return (String) spUtils.get("login_name", "");
    }

    public void setLoginName(String loginName) {
        spUtils.put("login_name", loginName);
    }

    /**
     * 个推cid
     */
    public String getClientId() {
        return (String) spUtils.get("client_id", "");
    }

    public void setClientId(String clientId) {
        spUtils.put("client_id", clientId);
    }
}
