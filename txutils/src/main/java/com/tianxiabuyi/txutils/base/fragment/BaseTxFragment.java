package com.tianxiabuyi.txutils.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianxiabuyi.txutils.base.impl.IBaseTxFragment;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author wangyd
 * @date 2018/5/10
 * @description description
 */
public abstract class BaseTxFragment extends Fragment implements IBaseTxFragment {

    private static final Handler handler = new Handler();
    protected View mView;
    private Unbinder unbinder;
    /**
     * RxJava
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 网络请求
     */
    private List<TxCall> mCalls = new ArrayList<>();
    /**
     * EventBus
     */
    private boolean isEventBusEnabled = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutByXml(), container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initArguments(getArguments());
        initView();
        initData();

        if (isEventBusEnabled) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initArguments(Bundle data) {

    }

    @Override
    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        clearCall();
        clearDisposable();
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
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

    @Override
    @NonNull
    public View getView() {
        return mView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }

    protected final Handler getHandler() {
        return handler;
    }

    protected final void postRunnable(final Runnable runnable) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!isAdded()) {
                    return;
                }
                runnable.run();
            }
        });
    }

    protected final void postDelayed(final Runnable runnable, long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAdded()) {
                    return;
                }
                runnable.run();
            }
        }, delay);
    }

    @Override
    public void startAnimActivity(Class<?> cla) {
        getActivity().startActivity(new Intent(getActivity(), cla));
    }

    @Override
    public void startAnimActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showShort(getActivity(), msg);
    }

    @Override
    public void toast(int msgId) {
        ToastUtils.showShort(getActivity(), msgId);
    }

    @Override
    public void toast(TxException exception) {
        ToastUtils.showShort(getActivity(), exception.getDetailMessage());
    }

    @Override
    public void toastLong(String msg) {
        ToastUtils.showLong(getActivity(), msg);
    }

    @Override
    public void toastLong(int msgId) {
        ToastUtils.showLong(getActivity(), msgId);
    }

}
