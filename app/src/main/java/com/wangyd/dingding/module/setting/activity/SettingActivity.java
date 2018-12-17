package com.wangyd.dingding.module.setting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tianxiabuyi.txutils.TxUpdateManager;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.activity.TxAboutUsActivity;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.FileUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.txutils_ui.setting.SettingItemView;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.core.utils.ShareUtils;
import com.tianxiabuyi.villagedoctor.module.login.activity.LoginActivity;
import com.tianxiabuyi.villagedoctor.module.login.utils.LogoutUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xjh1994
 * @date 16/12/7
 * @description 设置界面
 */
public class SettingActivity extends BaseTxTitleActivity {

    @BindView(R.id.sivFeedback)
    SettingItemView sivFeedback;
    @BindView(R.id.checkUpdate)
    SettingItemView checkUpdate;
    @BindView(R.id.sivAboutUs)
    SettingItemView sivAboutUs;
    @BindView(R.id.sivShareApp)
    SettingItemView sivShareApp;
    @BindView(R.id.sivClearCache)
    SettingItemView sivClearCache;
    @BindView(R.id.btnLogout)
    Button btnLogout;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected String getTitleString() {
        return getString(R.string.common_setting);
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        // 设置缓存info
        // setCacheSize();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TxUserManager.getInstance().isLogin()) {
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.sivFeedback, R.id.sivAboutUs, R.id.checkUpdate, R.id.sivShareApp,
            R.id.sivClearCache, R.id.btnLogout})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.sivFeedback) {            //意见反馈
            startAnimActivity(FeedbackActivity.class);
        } else if (i == R.id.sivAboutUs) {      //技术支持
            TxAboutUsActivity.newInstance(this, "技术支持");
        } else if (i == R.id.checkUpdate) {     //软件更新
            TxUpdateManager.update(SettingActivity.this, true);
        } else if (i == R.id.sivShareApp) {     //软件分享
            ShareUtils.showShare(this);
        } else if (i == R.id.sivClearCache) {   //清除缓存
            showClearCacheDialog();
        } else if (i == R.id.btnLogout) {       //退出程序
            showLogoutDialog();
        }
    }

    /**
     * 设置缓存大小
     */
    private void setCacheSize() {
        String totalCacheSize = FileUtils.getTotalCacheSize(getApplicationContext());
        sivClearCache.getIvArrowForward().setVisibility(View.GONE);
        sivClearCache.getTvRightDesc().setVisibility(View.VISIBLE);
        sivClearCache.getTvRightDesc().setText(totalCacheSize);
    }

    /**
     * 清除缓存dialog
     */
    private void showClearCacheDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.setting_del_cache_confirm)
                .positiveText(R.string.common_confirm)
                .negativeText(R.string.common_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        // 删除
                        clearCache();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 清除缓存数据
     */
    private void clearCache() {
        // 异步
        FileUtils.clearAllCache(getApplicationContext());
        toast(getString(R.string.setting_del_cache_success));
        sivClearCache.getTvRightDesc().setText("0.0KB");
    }

    /**
     * 退出登录dialog
     */
    private void showLogoutDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.common_logout_confirm)
                .positiveText(R.string.common_confirm)
                .negativeText(R.string.common_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        // 退出登录
                        LogoutUtils.logout(SettingActivity.this);
                        ToastUtils.show(R.string.common_logout_success);
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                        ActivityUtils.getInstance().finishAllActivityExcept(LoginActivity.class);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

}
