package com.wangyd.dingding.module.work.activity;

import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.wangyd.dingding.R;

/**
 * @author wangyd
 * @date 2018/12/21
 * @description
 */
public class SignActivity extends BaseTxTitleActivity {
    @Override
    protected String getTitleString() {
        return "打卡";
    }

    @Override
    public int geyXml() {
        return R.layout.activity_sign;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
