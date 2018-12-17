package com.wangyd.dingding.module.personal.activity;

import android.webkit.JavascriptInterface;

import com.wangyd.dingding.Constant;
import com.tianxiabuyi.villagedoctor.common.base.BaseX5Activity;
import com.wangyd.dingding.core.utils.UserSpUtils;

/**
 * 院长看板
 */
public class YzkbActivity extends BaseX5Activity {

    @Override
    protected String getUrl() {
        return Constant.H5_YZKB + "?county=" + UserSpUtils.getInstance().getCountyId();
    }

    @Override
    public void initView() {
        super.initView();
        mWebView.addJavascriptInterface(new JsMessageInterFace(), "nativeAPP");
    }

    /**
     * JS 接口
     */
    private class JsMessageInterFace {

        @JavascriptInterface
        public void backToAPP() {
            YzkbActivity.this.finish();
        }
    }
}
