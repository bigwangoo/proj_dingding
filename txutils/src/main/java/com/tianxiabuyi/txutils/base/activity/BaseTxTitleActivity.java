package com.tianxiabuyi.txutils.base.activity;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxUtils;

/**
 * @author xjh1994
 * @date 2016/08/24
 */
public abstract class BaseTxTitleActivity extends ToolBarActivity {

    // 返回键
    private ImageView ivBack;
    // 标题
    private TextView titleName;
    // 标题右边文本
    private TextView titleRightText;
    // 从右到左第一个
    private ImageView titleImageOne;
    // 从右到左第二个
    private ImageView titleImageTwo;

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        // 设置背景颜色
        toolbar.setBackgroundColor(ContextCompat.getColor(this, getToolbarColor()));
        // 设置自定义布局
        getLayoutInflater().inflate(R.layout.tx_layout_title, toolbar);
        titleName = (TextView) toolbar.findViewById(R.id.tvTitle);
        titleRightText = (TextView) toolbar.findViewById(R.id.tvRight);
        titleImageOne = (ImageView) toolbar.findViewById(R.id.ivRightOne);
        titleImageTwo = (ImageView) toolbar.findViewById(R.id.ivRightTwo);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置标题
        setTitleName(titleName);
    }

    /**
     * 获取toolbar的颜色 你可以重写这个方法自定义
     */
    protected int getToolbarColor() {
        return TxUtils.getInstance().getConfiguration().getColorPrimary();
    }

    /**
     * 获取标题名称
     */
    protected abstract String getTitleString();

    /**
     * 设置title的标题
     */
    protected void setTitleName(TextView titleName) {
        titleName.setText(getTitleString());
    }

    protected void setTitleRightText(String name) {
        titleRightText.setVisibility(View.VISIBLE);
        titleRightText.setText(name);
    }

    protected void setTitleRightText(String name, View.OnClickListener listener) {
        titleRightText.setVisibility(View.VISIBLE);
        titleRightText.setText(name);
        titleRightText.setOnClickListener(listener);
    }

    protected void setTitleImageOne(@DrawableRes int resId, View.OnClickListener listener) {
        titleImageOne.setVisibility(View.VISIBLE);
        titleImageOne.setImageResource(resId);
        titleImageOne.setOnClickListener(listener);
    }

    protected void setTitleImageTwo(@DrawableRes int resId, View.OnClickListener listener) {
        titleImageTwo.setVisibility(View.VISIBLE);
        titleImageTwo.setImageResource(resId);
        titleImageTwo.setOnClickListener(listener);
    }

    protected void setTitleRightTextVisible(Boolean visible) {
        if (visible) {
            titleRightText.setVisibility(View.VISIBLE);
        } else {
            titleRightText.setVisibility(View.GONE);
        }
    }

    protected void setTitleImageOneVisible(Boolean visible) {
        if (visible) {
            titleImageOne.setVisibility(View.VISIBLE);
        } else {
            titleImageOne.setVisibility(View.GONE);
        }
    }

    protected void setTitleImageTwoVisible(Boolean visible) {
        if (visible) {
            titleImageTwo.setVisibility(View.VISIBLE);
        } else {
            titleImageTwo.setVisibility(View.GONE);
        }
    }

    protected ImageView getBackImage() {
        return ivBack;
    }

    protected TextView getTitleName() {
        return titleName;
    }

    protected TextView getTitleRightText() {
        return titleRightText;
    }

    protected ImageView getTitleImageOne() {
        return titleImageOne;
    }

    protected ImageView getTitleImageTwo() {
        return titleImageTwo;
    }

    /**
     * exit activity, maybe you can rewrite this method
     */
    protected void finishActivity() {
        finish();
    }
}
