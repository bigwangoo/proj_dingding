package com.wangyd.dingding.core.base;

import android.os.Bundle;

import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;
import com.wangyd.dingding.core.mvp.IPresenter;
import com.wangyd.dingding.core.mvp.IView;

/**
 * @author wangyd
 * @date 2018/10/30
 * @description MVP 基类封装
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseTxActivity implements IView {

    protected P mPresenter;

    /**
     * 初始化 Presenter
     */
    protected abstract P createPresenter();

    @SuppressWarnings("unchecked")
    @Override
    protected void init(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter == null) {
            throw new NullPointerException("Presenter is null! Did you return null in createPresenter()?");
        }
        mPresenter.onAttachView(this, savedInstanceState);

        super.init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetachView(false);
            mPresenter.onDestroy();
        }
    }

}
