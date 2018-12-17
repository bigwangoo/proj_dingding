package com.tianxiabuyi.txutils.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.util.TxLog;

import java.lang.reflect.Field;

/**
 * @author wangyd
 * @date 2018/8/1
 * @description WebView
 */
public class TxWebViewActivity extends BaseTxTitleActivity {

    protected ProgressBar progressbar;
    protected WebView webView;
    protected String mTitle;
    protected String mUrl;
    protected boolean mIsSupportZoom = false;
    protected boolean isShowProgress = true;

    public static void newInstance(Context context, String title, String url) {
        context.startActivity(new Intent(context, TxWebViewActivity.class)
                .putExtra("title", title)
                .putExtra("url", url));
    }

    @Override
    public int getViewByXml() {
        return R.layout.tx_activity_base_webview;
    }

    @Override
    protected String getTitleString() {
        return TextUtils.isEmpty(mTitle) ? "" : mTitle;
    }

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
    }

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.webView);

        if (isShowProgress) {
            progressbar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            progressbar.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 18));
            webView.addView(progressbar);
        }
        initWebViewSettings();
        webView.setWebViewClient(new CustomWebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initWebViewSettings() {
        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
        settings.setUserAgentString("");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSupportZoom(true);
        if (mIsSupportZoom) {
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
        }
        settings.setTextZoom(settings.getTextZoom());
        settings.setDefaultTextEncodingName("utf-8");
    }

    @Override
    public void initData() {
        TxLog.e(mUrl);

        if (TextUtils.isEmpty(mUrl)) {
            return;
        }
        webView.loadUrl(mUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        try {
            if (webView != null) {
                ((ViewGroup) webView.getParent()).removeView(webView);
                webView.destroy();
                webView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        releaseAllWebViewCallback();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            webView.loadData("", "text/html; charset=UTF-8", null);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (Exception ignored) {
            }
        } else {
            try {
                @SuppressLint("PrivateApi")
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame")
                        .getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Web视图
     */
    protected class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString());
            }
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //隐藏默认的404页面
            // view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            //加载自定义页面
            //loadFailure();
        }
    }

    /**
     * Web视图
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressbar != null) {
                if (newProgress == 100) {
                    progressbar.setVisibility(View.GONE);
                } else {
                    if (progressbar.getVisibility() == View.GONE) {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                    progressbar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(TxWebViewActivity.this)
                            .setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("确定", null)
                            .show();
                }
            });
            //这里必须调用，否则页面会阻塞造成假死
            result.confirm();
            return true;
        }
    }

}
