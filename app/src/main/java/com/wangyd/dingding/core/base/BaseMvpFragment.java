package com.wangyd.dingding.core.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tianxiabuyi.txutils.base.fragment.BaseTxFragment;
import com.tianxiabuyi.villagedoctor.common.mvp.IPresenter;
import com.tianxiabuyi.villagedoctor.common.mvp.IView;

/**
 * @author wangyd
 * @date 2018/10/31
 * @description MVP 基类封装
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends BaseTxFragment implements IView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter == null) {
            throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
        }
        mPresenter.onAttachView(this, savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetachView(false);
            mPresenter.onDestroy();
        }
    }
}
