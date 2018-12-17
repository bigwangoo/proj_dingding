package com.tianxiabuyi.villagedoctor.module.login.utils;

import android.content.Context;

import com.igexin.sdk.PushManager;
import com.orhanobut.logger.Logger;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.util.CookiesUtils;
import com.tianxiabuyi.villagedoctor.common.db.CacheDB;
import com.tianxiabuyi.villagedoctor.common.db.HistoryDB;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.tianxiabuyi.villagedoctor.module.cache.event.CacheServiceEvent;

import org.greenrobot.eventbus.EventBus;

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
        PushManager.getInstance().stopService(context.getApplicationContext());
        // 停止下载服务
        EventBus.getDefault().post(new CacheServiceEvent());
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
                        CacheDB.deleteCacheInfo();
                        CacheDB.deleteCacheLabel();
                        CacheDB.deleteCacheSP();
                        CacheDB.deleteCacheResidentAll();
                        CacheDB.deleteCacheContractAll();
                        CacheDB.deleteCacheMarkAll();
                        HistoryDB.deleteHistoryAll();
                        Logger.e("数据清除完成");
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
