package com.tianxiabuyi.txutils.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.base.impl.IBaseTxActivity;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.txutils.util.TxStatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author xjh1994
 * @date 2016/8/24
 * @description Activity基类
 */
public abstract class BaseTxActivity extends AppCompatActivity implements IBaseTxActivity {

    /**
     * RxJava
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 网络请求
     */
    private List<TxCall> mCalls = new ArrayList<>();
    /**
     * 软键盘
     */
    private InputMethodManager inputMethodManager;
    /**
     * EventBus
     */
    private boolean isEventBusEnabled = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        afterOnCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);

        if (getViewByXml() != 0) {
            setContentView(getViewByXml());
        }
        ButterKnife.bind(this);

        init(savedInstanceState);

        if (isEventBusEnabled) {
            EventBus.getDefault().register(this);
        }
    }

    protected void beforeOnCreate(Bundle savedInstanceState) {
        // 主题
        setAppTheme();
    }

    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    protected void init(Bundle savedInstanceState) {
        getIntentData(getIntent());
        initView();
        initData();
    }

    protected void setAppTheme() {
        String theme = TxUtils.getInstance().getConfiguration().getTheme();

        // 设置 statusBar
        if (TxConstants.THEME_LIGHT.equals(theme)) {
            TxStatusBarUtil.setStatusBarDarkMode(this);
        } else {
            TxStatusBarUtil.setStatusBarLightMode(this);
        }
        // 设置主题
//        switch (BuildConfig.THEME) {
//            case 1:
//                setTheme(R.style.theme_1);
//                break;
//            case 2:
//                setTheme(R.style.theme_2);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void getIntentData(Intent intent) {

    }

    /**
     * 开启EventBus
     */
    public void setEventBusEnabled() {
        isEventBusEnabled = true;
    }

    /**
     * 添加网络请求
     */
    public void addCallList(TxCall call) {
        if (mCalls == null) {
            mCalls = new ArrayList<>();
        }
        mCalls.add(call);
    }

    /**
     * 取消网络请求，onDestroy取消
     */
    public void clearCall() {
        if (mCalls != null) {
            for (int i = 0; i < mCalls.size(); i++) {
                TxCall call = mCalls.get(i);
                if (call != null) {
                    //if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
            }
        }
        mCalls = null;
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消订阅, onDestroy取消
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (inputMethodManager != null && getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AnalysisUtils.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        AnalysisUtils.getInstance().onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.getInstance().finishActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        clearCall();
        clearDisposable();

        inputMethodManager = null;
    }

    @Override
    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    @Override
    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    @Override
    public void toast(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    public void toast(@StringRes int msgId) {
        ToastUtils.show(this, msgId);
    }

    @Override
    public void toast(TxException exception) {
        toast(exception.getDetailMessage());
    }

    @Override
    public void toastLong(String msg) {
        ToastUtils.showLong(this, msg);
    }

    @Override
    public void toastLong(int msgId) {
        ToastUtils.showLong(this, msgId);
    }

}
