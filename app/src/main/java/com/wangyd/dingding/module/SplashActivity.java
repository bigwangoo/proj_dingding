package com.wangyd.dingding.module;

import android.content.Intent;

import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;
import com.wangyd.dingding.R;
import com.wangyd.dingding.module.main.activity.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @author wangyd
 * @date 2018/12/10
 */
public class SplashActivity extends BaseTxActivity {

    @Override
    public int getViewByXml() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Observable
                .timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        startAnimActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
