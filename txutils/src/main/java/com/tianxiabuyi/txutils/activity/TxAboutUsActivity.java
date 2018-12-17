package com.tianxiabuyi.txutils.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianxiabuyi.txutils.BuildConfig;
import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.model.AppInfo;
import com.tianxiabuyi.txutils.util.AppUtils;
import com.tianxiabuyi.txutils.util.TimeUtils;

/**
 * @author xjh1994
 * @date 2016/8/22
 * @description 关于我们 TODO 网络请求
 */
public class TxAboutUsActivity extends BaseTxTitleActivity {

    private ImageView ivLogo;
    private TextView tvName;
    private TextView tvVersion;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvMail;
    private TextView tvPostCode;
    private TextView tvWebsite;
    private TextView tvWeibo;
    private TextView tvWechat;
    private AppInfo appInfo;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, TxAboutUsActivity.class));
    }

    public static void newInstance(Context context, String title) {
        context.startActivity(new Intent(context, TxAboutUsActivity.class)
                .putExtra("title", title));
    }

    @Override
    protected String getTitleString() {
        String title = getIntent().getStringExtra("title");
        return TextUtils.isEmpty(title) ? getString(R.string.tx_about_us) : title;
    }

    @Override
    public int getViewByXml() {
        return R.layout.tx_activity_about_us;
    }

    @Override
    public void initView() {
        ivLogo = findViewById(R.id.ivLogo);
        tvName = findViewById(R.id.tvName);
        tvVersion = findViewById(R.id.tvVersion);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvMail = findViewById(R.id.tvMail);
        tvPostCode = findViewById(R.id.tvPostCode);
        tvWebsite = findViewById(R.id.tvWebsite);
        tvWeibo = findViewById(R.id.tvWeibo);
        tvWechat = findViewById(R.id.tvWechat);

        appInfo = new AppInfo();
        tvVersion.setText("版本 " + AppUtils.getVersionName(this));
        tvName.setText(appInfo.getName());
        tvPhone.setText(appInfo.getPhone());
        tvAddress.setText(appInfo.getAddress());
        tvMail.setText(appInfo.getMail());
        tvPostCode.setText(appInfo.getPost_code());
        tvWebsite.setText(appInfo.getWebsite());
        tvWeibo.setText(appInfo.getWeibo());
        tvWechat.setText(appInfo.getMpweixin());
    }

    @Override
    public void initData() {

    }

    private int count = 0;

    public void onVersionClick(View view) {
        count++;
        if (count > 5 && TxUtils.getInstance().isDebug()) {
            count = 0;
            new AlertDialog.Builder(this)
                    .setMessage(getDebugInfo())
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private String getDebugInfo() {
        long firstInstallTime = 0;
        long lastUpdateTime = 0;
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            //应用装时间
            firstInstallTime = packageInfo.firstInstallTime;
            //应用最后一次更新时间
            lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "软件版本：" + AppUtils.getVersionName(this) + " " + AppUtils.getVersionCode(this) + "\n"
                + "接口地址：" + TxUtils.getInstance().getConfiguration().getBaseUrl() + "\n"
                + "版本打包时间：" + BuildConfig.buildTime + "\n"
                + "首次安装时间：" + "" + TimeUtils.formatDate(firstInstallTime, "yyyy-MM-dd HH:mm:ss") + "\n"
                + "更新安装时间：" + "" + TimeUtils.formatDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss");
    }

    public void onPhoneClick(View view) {
        appInfo.onPhoneClick(view);
    }

    public void onWebsiteClick(View view) {
        appInfo.onWebsiteClick(view);
    }

    public void onClipWechat(View view) {
        appInfo.onClipWechat(view);
    }
}
