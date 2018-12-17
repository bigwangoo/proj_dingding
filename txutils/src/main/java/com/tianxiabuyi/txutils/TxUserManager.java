package com.tianxiabuyi.txutils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.tianxiabuyi.txutils.config.TxKeys;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.callback.LoginCallback;
import com.tianxiabuyi.txutils.network.callback.LogoutCallback;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.callback.UpdateCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.model.LoginResult;
import com.tianxiabuyi.txutils.network.model.TxUser;
import com.tianxiabuyi.txutils.network.service.TxUserService;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.tianxiabuyi.txutils.util.MD5EncryptUtils;
import com.tianxiabuyi.txutils.util.SpUtils;

import java.util.Map;

/**
 * @author xjh1994
 * @date 2016/8/18
 * @description 用户管理
 */
public class TxUserManager {
    private static volatile TxUserManager mInstance;
    private static TxUser currentUser;
    private SpUtils spUtils;
    private String token;

    protected TxUserManager() {
        spUtils = new SpUtils(TxUtils.getInstance().getContext());
    }

    public static TxUserManager getInstance() {
        if (mInstance == null) {
            synchronized (TxUserManager.class) {
                if (mInstance == null) {
                    mInstance = new TxUserManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取token
     */
    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = (String) spUtils.get(TxKeys.KEY_TOKEN, "");
        }
        return token;
    }

    /**
     * 保存token
     */
    public void setToken(String token) {
        this.token = token;
        spUtils.put(TxKeys.KEY_TOKEN, token);
    }

    /**
     * 是否已登录
     */
    public boolean isLogin() {
        return getCurrentUser() != null;
    }

    /**
     * 获取当前用户
     */
    public TxUser getCurrentUser() {
        if (currentUser == null) {
            String json = (String) spUtils.get(TxKeys.KEY_USER, "");
            currentUser = GsonUtils.fromJson(json, TxUser.class);
        }
        return currentUser;
    }

    /**
     * 获取当前用户
     *
     * @param clazz 自定义用户
     */
    public <T extends TxUser> T getCurrentUser(Class<T> clazz) {
        String json = (String) spUtils.get(TxKeys.KEY_USER, "");
        T t = GsonUtils.fromJson(json, clazz);
        currentUser = t;
        return t;
    }

    /**
     * 设置当前用户
     */
    public void setCurrentUser(TxUser user) {
        TxUserManager.currentUser = user;
        spUtils.put(TxKeys.KEY_USER, GsonUtils.toJson(user));
    }

    /**
     * 设置当前用户json信息  自定义用户
     */
    public void saveCurrentUser(String userJson) {
        TxUserManager.currentUser = null;
        spUtils.put(TxKeys.KEY_USER, userJson);
    }

    /**
     * 更新本地用户信息, 后台修改信息成功后调用
     */
    public void updateLocalUser(TxUser newUser) {
        setCurrentUser(newUser);
    }

    /**
     * 更新本地用户信息, 后台修改信息成功后调用
     */
    public void updateLocalUserString(String newUserJson) {
        saveCurrentUser(newUserJson);
    }


    /////////////////////////  默认用户接口，非通用 ////////////////////////////////////////////

    private static TxUserService userService;

    private static void checkUserService() {
        if (userService == null) {
            userService = TxServiceManager.createService(TxUserService.class,
                    TxUtils.getInstance().getDefaultOkHttp());
        }
    }

    /**
     * 用户注册
     */
    public static TxCall register(Map<String, String> queryMap, ResponseCallback<HttpResult> callback) {
        checkUserService();

        TxCall<HttpResult> call = userService.register(queryMap);
        call.enqueue(callback);
        return call;
    }

    /**
     * 用户登录，无需MD5加密
     */
    public static <T extends TxUser> TxCall login(String username, String password, final LoginCallback<T> callback) {
        checkUserService();

        TxCall<JsonElement> call = userService.login(username, MD5EncryptUtils.encryptMD5(password));
        call.enqueue(new ResponseCallback<JsonElement>(callback.getContext()) {
            @Override
            public void onSuccess(JsonElement result) {
                JsonElement userObject = result.getAsJsonObject().get("user");
                JsonElement authObject = result.getAsJsonObject().get("auth");

                if (userObject == null) {
                    // 登录失败回调
                    HttpResult errorResult = GsonUtils.fromJson(result.toString(), HttpResult.class);
                    TxException e;
                    if (errorResult == null) {
                        e = new TxException("");
                    } else {
                        e = new TxException(errorResult);
                    }
                    callback.onError(e);
                } else {
                    // 登录成功回调
                    T t = GsonUtils.fromJson(userObject.toString(), new TypeToken<T>() {
                    });
                    // 保存user
                    TxUserManager.getInstance().setCurrentUser(t);
                    LoginResult.AuthBean auth = GsonUtils.fromJson(authObject.toString(), LoginResult.AuthBean.class);
                    // 保存token
                    if (auth != null) {
                        TxUserManager.getInstance().setToken(auth.getToken());
                    }
                    callback.onSuccess(t);
                }
            }

            @Override
            public void onError(TxException e) {
                callback.onError(e);
            }
        });
        return call;
    }

    /**
     * 退出登录, 带进度条
     */
    public static TxCall logout(@NonNull final LogoutCallback callback) {
        checkUserService();

        TxCall<HttpResult> call = userService.logout();
        call.enqueue(new ResponseCallback<HttpResult>(callback.getContext()) {
            @Override
            public void onSuccess(HttpResult result) {
            }

            @Override
            public void onError(TxException e) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                // 不管有没有成功都清除本地用户信息
                TxUserManager.getInstance().setCurrentUser(null);
                TxUserManager.getInstance().setToken(null);
                TxLog.d("logout success");
                callback.onSuccess();
            }
        });
        return call;
    }

    /**
     * 修改密码
     */
    public static TxCall updatePassword(String oldPassword, String newPassword,
                                        final UpdateCallback<HttpResult> callback) {
        checkUserService();

        TxCall<HttpResult> call = userService.updatePassword(
                MD5EncryptUtils.encryptMD5(oldPassword),
                MD5EncryptUtils.encryptMD5(newPassword),
                MD5EncryptUtils.encryptMD5(newPassword));
        call.enqueue(new ResponseCallback<HttpResult>(callback.getContext()) {
            @Override
            public void onSuccess(HttpResult result) {
                if (result.isSuccess()) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(new TxException(result.getErrmsg()));
                }
            }

            @Override
            public void onError(TxException e) {
                callback.onError(e);
            }
        });
        return call;
    }

    /**
     * 修改头像
     */
    public static TxCall updateAvatar(String url, final ResponseCallback<HttpResult> callback) {
        checkUserService();

        TxCall<HttpResult> call = userService.updateAvatar(url);
        call.enqueue(new ResponseCallback<HttpResult>(callback.getContext()) {
            @Override
            public void onSuccess(HttpResult result) {
                if (result.isSuccess()) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(new TxException(result.getErrmsg()));
                }
            }

            @Override
            public void onError(TxException e) {
                callback.onError(e);
            }
        });
        return call;
    }
}
