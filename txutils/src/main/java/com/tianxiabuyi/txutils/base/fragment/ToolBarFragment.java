package com.tianxiabuyi.txutils.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianxiabuyi.txutils.base.activity.ToolBarHelper;

/**
 * @author wangyd
 * @date 2018/5/10
 * @description description
 */
public abstract class ToolBarFragment extends BaseTxFragment {

    protected Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 重写 onCreateView

        ToolBarHelper toolBarHelper = new ToolBarHelper(getActivity(), getLayoutByXml());
        mView = toolBarHelper.getContentView();
        toolbar = toolBarHelper.getToolBar();
        // 自定义的一些操作
        onCreateCustomToolBar(toolbar);
        // Toolbar设置到Activity中
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return mView;
    }

    /**
     * 自定义toolBar
     */
    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }
}
