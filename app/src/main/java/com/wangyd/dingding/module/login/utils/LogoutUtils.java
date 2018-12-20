package com.wangyd.dingding.module.login.utils;

import android.content.Context;

import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.util.CookiesUtils;
import com.wangyd.dingding.core.utils.UserSpUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangyd
 * @date 2018/6/20
 * @description 退出登录
 */
public class LogoutUtils {

    /**
     * 退出登录操作
     */
    public static void logout(Context context) {
        // 停止个推服务
        // 停止下载服务
        // 清除COOKIES
        CookiesUtils.getInstance().clear();
        // 清除用户数据
        UserSpUtils.getInstance().clear();
        // 清除登录信息
        TxUserManager.getInstance().setCurrentUser(null);
        // 清除缓存数据库
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {

                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
