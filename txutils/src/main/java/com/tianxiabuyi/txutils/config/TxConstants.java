package com.tianxiabuyi.txutils.config;

/**
 * @author xjh1994
 * @date 2016/7/15
 */
public class TxConstants {

    // THEME
    public static final String THEME_LIGHT = "light";  // 白色状态栏
    public static final String THEME_DARK = "dark";    // 暗色状态栏

    // URL
    public static final String BASE_URL = "http://demoapi.eeesys.com:18088/v2/";
    public static final String UPDATE_URL = "http://api.eeesys.com:18088/v2/app/update";
    public static final String UPLOAD_FILE_URL = "http://cloud.eeesys.com/pu/upload.php";

    // KEY
    public static final String KEY_APP_TYPE = "app_type";
    public static final String KEY_HOSPITAL = "hospital";
    public static final String KEY_TOKEN = "token";

    // VALUE
    public static final long TIMEOUT = 3 * 60L;
    public static final long FILE_TIMEOUT = 5 * 60L;

    // API
    public static final String TOKEN_REFRESH_URL = "token/refresh";
    public static final String TOKEN_REVOKE_URL = "token/revoke";
    public static final String USER_CREATE_URL = "user/create";
    public static final String USER_LOGIN_URL = "user/login";
    public static final String USER_PASSWORD_URL = "user/password";
    public static final String USER_AVATAR_URL = "user/avatar";

    // EXTRA
    public static final String EXTRA_TOOLBAR_COLOR = "extra_toolbar_color";
    public static final String EXTRA_LOGO = "extra_logo";
    public static final String EXTRA_APP_NAME = "extra_app_name";
    public static final String EXTRA_VERSION_NAME = "extra_version_name";
    public static final String EXTRA_RECYCLER_ACTIVITY_TYPE = "extra_recycler_activity_type";
    public static final String EXTRA_TOKEN_EXPIRES = "extra_token_expires";
}
