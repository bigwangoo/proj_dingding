package com.wangyd.dingding.module;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.villagedoctor.BuildConfig;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.core.utils.AppSpUtils;
import com.tianxiabuyi.villagedoctor.module.login.activity.LoginActivity;
import com.wangyd.dingding.module.main.activity.MainActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 闪屏页 必须申请读取存储卡权限
 */
public class SplashActivity extends BaseTxActivity implements EasyPermissions.PermissionCallbacks {

    /**
     * 权限申请Code
     */
    protected static final int RC_PERMISSIONS = 10001;
    /**
     * 申请的权限
     */
    protected static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 延时时间
     */
    private static final int TIME_DELAY_MILLIS = 1000;
    /**
     * 延时任务
     */
    private final Runnable homeTask = new Runnable() {
        @Override
        public void run() {
            showHome();
        }
    };

    private final Handler HANDLER = new Handler();

    @Override
    public int getViewByXml() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        // 调试使用
        if (BuildConfig.DEBUG) {
            AppSpUtils.getInstance().setFirstOpen(false);
        }
    }

    @Override
    public void initData() {
        // 申请权限
        applyPermissionTask();
    }

    /**
     * 初始化
     */
    private void initSplash() {
        if (AppSpUtils.getInstance().isFirstOpen()) {
            // 引导界面
            setContentView(R.layout.activity_guide);
            showGuide();
        } else {
            // 闪屏页面
            setContentView(R.layout.activity_splash);
            HANDLER.postDelayed(homeTask, TIME_DELAY_MILLIS);
        }
    }

    /**
     * 跳转引导
     */
    private void showGuide() {
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }

    /**
     * 跳转主页
     */
    private void showHome() {
        if (TxUserManager.getInstance().isLogin()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HANDLER.removeCallbacks(homeTask);
        //HANDLER = null;
    }


    //// 权限申请
    private void applyPermissionTask() {
        // 申请权限
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            onPermissionGranted();
        } else {
            String rationale = getString(R.string.tx_rationale_sdcard);
            EasyPermissions.requestPermissions(this, rationale, RC_PERMISSIONS, PERMISSIONS);
        }
    }

    protected void onPermissionGranted() {
        // 获得权限
        initSplash();
    }

    protected void showPermissionDeniedDialog() {
        //权限拒绝提示 申请或退出
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(getString(R.string.tx_rationale_denied))
                .setCancelable(false)
                .setPositiveButton("申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermissionTask();
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        TxLog.d("onPermissionsGranted:" + requestCode + ":" + perms.size());

        onPermissionGranted();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        TxLog.d("onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // 拒绝权限（不再询问）
            new AppSettingsDialog.Builder(this)
                    .setRationale(getString(R.string.tx_rationale_ask_again)).build().show();
        } else if (requestCode == RC_PERMISSIONS) {
            // 拒绝权限
            showPermissionDeniedDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TxLog.d("onActivityResult:" + requestCode);

        // 不再询问后的手动申请回调，继续判断权限是否获取
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
                onPermissionGranted();
            } else {
                showPermissionDeniedDialog();
            }
        }
    }
}
