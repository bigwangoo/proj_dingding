package com.tianxiabuyi.txutils.base.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.TxUtils;

/**
 * @author wangyd
 * @date 2018/5/14
 * @description description
 */
public abstract class BaseTxTitleFragment extends ToolBarFragment {
    /**
     * 左边返回
     */
    private ImageView titleBack;
    /**
     * 左边 textView
     */
    private TextView titleLeftText;
    /**
     * 标题
     */
    private TextView titleName;
    /**
     * 标题最右边的textView
     */
    private TextView titleRightText;
    /**
     * 从右到左第一个imageView
     */
    private ImageView titleImageOne;
    /**
     * 从右到左第二个imageView
     */
    private ImageView titleImageTwo;

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        // 自定义
        LayoutInflater.from(getActivity()).inflate(R.layout.tx_layout_title, toolbar);
        titleBack = (ImageView) toolbar.findViewById(R.id.ivBack);
        titleLeftText = (TextView) toolbar.findViewById(R.id.tvLeft);
        titleName = (TextView) toolbar.findViewById(R.id.tvTitle);
        titleRightText = (TextView) toolbar.findViewById(R.id.tvRight);
        titleImageOne = (ImageView) toolbar.findViewById(R.id.ivRightOne);
        titleImageTwo = (ImageView) toolbar.findViewById(R.id.ivRightTwo);
        // 设置toolbar的颜色
        toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), getToolbarColor()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBack();
        setTitleName(titleName);
    }

    /**
     * 隐藏返回
     */
    protected void hideBack() {
        titleBack.setVisibility(View.GONE);
    }

    /**
     * 设置左侧图标
     */
    protected void setTitleBack(@DrawableRes int resId, View.OnClickListener listener) {
        titleLeftText.setVisibility(View.GONE);
        titleBack.setVisibility(View.VISIBLE);
        titleBack.setImageResource(resId);
        titleBack.setOnClickListener(listener);
    }

    /**
     * 设置左侧标题
     */
    protected void setTitleLeft(String text, View.OnClickListener listener) {
        titleBack.setVisibility(View.GONE);
        titleLeftText.setVisibility(View.VISIBLE);
        titleLeftText.setText(text);
        titleLeftText.setOnClickListener(listener);
    }

    /**
     * 设置标题
     */
    protected void setTitleName(TextView titleName) {
        titleName.setText(getTitleString());
    }

    /**
     * fragment中, 在 init()之前调用
     */
    protected abstract String getTitleString();

    /**
     * 设置右侧标题
     */
    protected void setTitleRight(String text, View.OnClickListener listener) {
        titleRightText.setVisibility(View.VISIBLE);
        titleRightText.setText(text);
        titleRightText.setOnClickListener(listener);
    }

    /**
     * 设置右侧img 1
     */
    protected void setTitleImageOne(@DrawableRes int resId, View.OnClickListener listener) {
        titleImageOne.setVisibility(View.VISIBLE);
        titleImageOne.setImageResource(resId);
        titleImageOne.setOnClickListener(listener);
    }

    /**
     * 设置右侧img 2
     */
    protected void setTitleImageTwo(@DrawableRes int resId, View.OnClickListener listener) {
        titleImageTwo.setVisibility(View.VISIBLE);
        titleImageTwo.setImageResource(resId);
        titleImageTwo.setOnClickListener(listener);
    }

    protected ImageView getBackImage() {
        return titleBack;
    }

    protected TextView getTitleLeft() {
        return titleLeftText;
    }

    protected TextView getTitleName() {
        return titleName;
    }

    protected TextView getTitleRight() {
        return titleRightText;
    }

    protected ImageView getTitleImageOne() {
        return titleImageOne;
    }

    protected ImageView getTitleImageTwo() {
        return titleImageTwo;
    }

    /**
     * 获取toolbar的颜色 你可以重写这个方法自定义
     */
    protected int getToolbarColor() {
        TxConfiguration configuration = TxUtils.getInstance().getConfiguration();
        return configuration == null ? R.color.colorPrimary : configuration.getColorPrimary();
    }
}