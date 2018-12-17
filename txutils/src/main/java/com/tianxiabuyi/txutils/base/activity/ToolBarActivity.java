package com.tianxiabuyi.txutils.base.activity;

import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

/**
 * @author wangyd
 * @date 2015/6/12
 * @description 定义标题栏
 */
public abstract class ToolBarActivity extends BaseTxActivity {
    private FrameLayout mContentView;
    private Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        ToolBarHelper mToolBarHelper = new ToolBarHelper(this, layoutResID);
        mContentView = mToolBarHelper.getContentView();
        toolbar = mToolBarHelper.getToolBar();

        //设置视图
        setContentView(mContentView);
        onCreateCustomToolBar(toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * 自定义Toolbar
     */
    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
