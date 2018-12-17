package com.wangyd.dingding.core.mvp;

import android.app.Activity;
import android.os.Bundle;

import com.tianxiabuyi.txutils.network.TxCall;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author wangyd
 * @date 2018/10/30
 * @description P层实现的基类，Presenter生命周期包装、View的绑定和解除
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<TxCall> txCallList = new ArrayList<>();
    private WeakReference<V> viewRef;
    public Activity mActivity;

    public BasePresenter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onAttachView(V view, Bundle savedInstanceState) {
        viewRef = new WeakReference<>(view);
        onAttached();
    }

    public V getView() {
        return viewRef.get();
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    /**
     * view attached
     */
    protected abstract void onAttached();

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onDetachView(boolean retainInstance) {
        clearDisposable();
        cancelTxCalls();

        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void onDestroy() {

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
     * 添加网络请求
     */
    public void addTxCall(TxCall call) {
        txCallList.add(call);
    }

    /**
     * 取消网络请求，onDestroy取消
     */
    public void cancelTxCalls() {
        if (txCallList == null || txCallList.size() == 0) {
            return;
        }
        for (int i = 0; i < txCallList.size(); i++) {
            TxCall txCall = txCallList.get(i);
            if (txCall != null && !txCall.isCanceled()) {
                txCall.cancel();
            }
        }
    }
}
