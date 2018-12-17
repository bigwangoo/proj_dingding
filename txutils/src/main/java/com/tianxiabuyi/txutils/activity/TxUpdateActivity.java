package com.tianxiabuyi.txutils.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxFileManager;
import com.tianxiabuyi.txutils.TxUpdateManager;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.callback.inter.FileResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxUpdateResult;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.FileProvider7;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wangyd
 * @date 2018/4/24
 * @description 软件下载更新, 调用请使用 {@link TxUpdateManager}
 */
public class TxUpdateActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final String EXTRA_APP_INFO = "extra_appInfo";

    private static final int RC_EXTERNAL_STORAGE = 100;
    private static final int RC_UNKNOWN_APP = 200;
    private static final String[] PERM_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private TxUpdateResult.AppBean info;
    private File apkFile;

    /**
     * 软件更新
     */
    public static void update(Activity activity, TxUpdateResult.AppBean info) {
        Intent intent = new Intent(activity, TxUpdateActivity.class);
        intent.putExtra(EXTRA_APP_INFO, info);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tx_activity_update_app);

        if (getIntent() != null) {
            info = getIntent().getParcelableExtra(EXTRA_APP_INFO);
        }
        if (info == null) {
            finish();
            return;
        }

        // 网络下载apk
        if (EasyPermissions.hasPermissions(this, PERM_STORAGE)) {
            downloadApk(TxUpdateActivity.this, getDownloadName(), info.getUrl());
        } else {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.tx_rationale_update), RC_EXTERNAL_STORAGE, PERM_STORAGE);
        }
    }

    // 下载APK文件
    private void downloadApk(final Activity context, final String appName, final String url) {
        final String path = getDownloadPath();

        // 本地已下载
        apkFile = new File(path, appName);
        if (apkFile.exists()) {
            installApk(context);
            return;
        }

        // 下载
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMax(100);
        dialog.setTitle(context.getString(R.string.tx_software_update));
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 取消下载请求
                        OkHttpUtils.getInstance().cancelTag(url);
                        finish();
                    }
                });
        dialog.show();

        TxFileManager.download(url, path, appName, new FileResponseCallback() {
            @Override
            public void onProgress(int progress, long total) {
                dialog.setProgress(progress);
            }

            @Override
            public void onSuccess(File response) {
                dialog.dismiss();
                apkFile = response;
                installApk(context);
            }

            @Override
            public void onError(TxException e) {
                dialog.dismiss();
                ToastUtils.show(context.getString(R.string.tx_cancel_update));
                deleteDownloadFile(path, appName);
                finish();
            }
        });
    }

    // 文件下载路径
    private String getDownloadPath() {
        return TxFileManager.getDownloadPath();
    }

    // 文件下载名
    private String getDownloadName() {
        return getPackageName() + "_" + info.getVersion_code() + "_" + info.getVersion() + ".apk";
    }

    // 适配安卓 8.0 安装 APK
    private void installApk(Context context) {
        if (apkFile == null) {
            return;
        }
        if (TxUtils.getInstance().isDebug()) {
            TxLog.e("apk path: " + apkFile.getAbsolutePath());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                !this.getPackageManager().canRequestPackageInstalls()) {
            // 8.0
            Toast.makeText(context, "更新安装缺少权限，请授予当前程序安装未知应用权限", Toast.LENGTH_LONG).show();
            startInstallPermissionSettingActivity();
        } else {
            // 7.0及以下
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            FileProvider7.setIntentDataAndType(context, intent,
                    "application/vnd.android.package-archive", apkFile, true);
            context.startActivity(intent);
            finish();
        }
    }

    // 适配安卓 8.0 跳转到设置-允许安装未知来源-页面
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新 API
        Uri uri = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
        startActivityForResult(intent, RC_UNKNOWN_APP);
    }

    // 删除APK文件
    private void deleteDownloadFile(String path, String appName) {
        File file = new File(path, appName);
        if (file.exists()) {
            boolean delete = file.delete();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // 获取权限继续下载apk
        if (requestCode == RC_EXTERNAL_STORAGE) {
            downloadApk(TxUpdateActivity.this, getDownloadName(), info.getUrl());
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // 不再询问
        if (requestCode == RC_EXTERNAL_STORAGE) {
            Toast.makeText(this, "缺少存储卡权限，软件更新失败，请在设置界面中手动打开", Toast.LENGTH_LONG).show();
        }
        TxUpdateActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 安装未知应用
        if (requestCode == RC_UNKNOWN_APP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                    !this.getPackageManager().canRequestPackageInstalls()) {
                Toast.makeText(TxUpdateActivity.this, "缺少安装应用权限，软件安装失败", Toast.LENGTH_LONG).show();
                TxUpdateActivity.this.finish();
            } else {
                // 获取权限继续安装
                installApk(TxUpdateActivity.this);
            }
        }
    }
}