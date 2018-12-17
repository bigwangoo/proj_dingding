package com.wangyd.dingding.core.base;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.CookiesUtils;
import com.wangyd.dingding.Constant;
import com.wangyd.dingding.R;

import java.util.Set;

/**
 * @author wangyd
 * @date 2018/10/25
 * @description X5 WebView
 */
public abstract class BaseX5TitleActivity extends BaseTxTitleActivity {

    protected ProgressBar mProgressBar;
    protected WebView mWebView;
    protected boolean isSupportZoom = false;
    protected boolean isShowSupportZoom = false;

    protected abstract String getUrl();

    @Override
    public int getViewByXml() {
        return R.layout.activity_yzkb;
    }

    @Override
    public void initView() {
        mWebView = findViewById(R.id.tbs_webView);
        mProgressBar = findViewById(R.id.progressBar);

        mProgressBar.setMax(100);
        mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_webview));
        mWebView.setWebViewClient(new MyWebClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());

        initWebSetting();
        syncCookies();
    }

    /**
     * 设置WebView
     */
    private void initWebSetting() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(isSupportZoom);
        webSetting.setBuiltInZoomControls(isSupportZoom);
        webSetting.setDisplayZoomControls(isShowSupportZoom);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
    }

    /**
     * 同步Cookies
     */
    private void syncCookies() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookie();
        } else {
            cookieManager.removeSessionCookies(null);
        }

        String baseUrl = Constant.getBaseUrl();
        String host = Uri.parse(baseUrl).getHost();
        Set<String> cookies = CookiesUtils.getInstance().getCookie(baseUrl, host);
        for (String cookie : cookies) {
            cookieManager.setCookie(host, cookie);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        } else {
            cookieManager.flush();
        }
    }

    /**
     * 获取域名
     */
    private String getDomain(String url) {
        if (url == null) {
            return "";
        }

        url = url.replace("http://", "").replace("https://", "");
        if (url.contains("/")) {
            url = url.substring(0, url.indexOf('/'));
        }
        return url;
    }

    @Override
    public void initData() {
        String url = getUrl();
        mWebView.loadUrl(url);

        TxLog.e("url=" + url);
        TxLog.e("base url=" + Constant.getBaseUrl());
        TxLog.e("cookies: " + CookieManager.getInstance().getCookie(Constant.getBaseUrl()));
        TxLog.e("cookies: " + CookieManager.getInstance().getCookie(getDomain(Constant.getBaseUrl())));
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mWebView != null && mWebView.canGoBack()) {
//                mWebView.goBack();
//                return true;
//            } else {
//                return super.onKeyDown(keyCode, event);
//            }
//        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView.loadUrl(request.getUrl().toString());
            }
            return super.shouldOverrideUrlLoading(webView, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            return super.shouldInterceptRequest(webView, s);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {
            return super.shouldInterceptRequest(webView, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request, Bundle bundle) {
            return super.shouldInterceptRequest(webView, request, bundle);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int progress) {
            if (mProgressBar != null) {
                if (progress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(progress);
                }
            }
            super.onProgressChanged(webView, progress);
        }

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
            runOnUiThread(() ->
                    new AlertDialog.Builder(BaseX5TitleActivity.this)
                            .setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("确定", null)
                            .show());
            //这里必须调用，否则页面会阻塞造成假死
            result.confirm();
            return true;
        }
    }
}
