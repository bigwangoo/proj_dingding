package com.tianxiabuyi.villagedoctor.module.login.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jaeger.library.StatusBarUtil;
import com.tianxiabuyi.txutils.TxUpdateManager;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.txutils.util.TxStatusBarUtil;
import com.tianxiabuyi.txutils_ui.edittext.CleanableEditText;
import com.tianxiabuyi.villagedoctor.BuildConfig;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.wangyd.dingding.core.utils.AppSpUtils;
import com.wangyd.dingding.core.utils.PatternUtils;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.tianxiabuyi.villagedoctor.module.login.utils.LoginApiUtil;
import com.tianxiabuyi.villagedoctor.module.login.utils.LogoutUtils;
import com.wangyd.dingding.module.main.activity.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 登录界面
 */
public class LoginActivity extends BaseTxActivity implements LoginApiUtil.OnAuthorizeListener,
        EasyPermissions.PermissionCallbacks {

    // 申请的权限
    protected static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    // 权限申请Code
    protected static final int RC_PERMISSIONS = 10001;

    @BindView(R.id.edtUserId)
    CleanableEditText edtUserId;
    @BindView(R.id.edtPwd)
    CleanableEditText edtPwd;
    @BindView(R.id.ivEye)
    ImageView ivEye;
    @BindView(R.id.tvForgot)
    TextView tvForgot;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.llLoginWechat)
    LinearLayout llLoginWechat;
    @BindView(R.id.llLoginQQ)
    LinearLayout llLoginQQ;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private Context context;
    private String userName;
    private String pwd;

    @Override
    public int getViewByXml() {
        return R.layout.activity_login;
    }

    @Override
    protected void setAppTheme() {
        TxStatusBarUtil.setStatusBarLightMode(this);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        boolean isExpires = intent.getBooleanExtra(TxConstants.EXTRA_TOKEN_EXPIRES, false);
        if (isExpires) {
            LogoutUtils.logout(this);
            ActivityUtils.getInstance().finishAllActivityExcept(LoginActivity.class);
        }
    }

    @Override
    public void initView() {
        context = this;
        // 重置避免登录错误
        UserSpUtils.getInstance().setOfflineMode(false);
    }

    @Override
    public void initData() {
        // 用户名
        String loginName = AppSpUtils.getInstance().getLoginName();
        if (TextUtils.isEmpty(loginName)) {
            edtUserId.setText("");
            edtUserId.requestFocus();
        } else {
            edtUserId.setText(loginName);
            edtPwd.requestFocus();
        }
        // 调试
        if (BuildConfig.DEBUG) {
            edtUserId.setText("18344684233");
            edtPwd.setText("666");
        }
        // 更新权限检测
        applyPermissionTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
    }

    @OnClick({R.id.ivEye, R.id.tvForgot, R.id.btnLogin,
            R.id.llLoginWechat, R.id.llLoginQQ, R.id.scrollView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivEye:
                // 密码显隐
                int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                if (edtPwd.getInputType() == type) {
                    edtPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edtPwd.setSelection(edtPwd.getText().length());
                    ivEye.setImageResource(R.drawable.ic_eye_open);
                } else {
                    edtPwd.setInputType(type);
                    edtPwd.setSelection(edtPwd.getText().length());
                    ivEye.setImageResource(R.drawable.ic_eye_close);
                }
                break;
            case R.id.tvForgot:
                // 忘记密码
                SmsPhoneActivity.newInstance(this, SmsPhoneActivity.INPUT_PHONE_FIND);
                break;
            case R.id.btnLogin:
                // 登录
                if (checkInput()) {
                    performLogin();
                }
                break;
            case R.id.llLoginWechat:
                // 微信登录
                new LoginApiUtil().login(this, Wechat.NAME, this);
                break;
            case R.id.llLoginQQ:
                // QQ登录
                new LoginApiUtil().login(this, QQ.NAME, this);
                break;
            case R.id.scrollView:
                hideSoftKeyboard();
                break;
            default:
                break;
        }
    }

    /**
     * 输入校验
     */
    private boolean checkInput() {
        userName = edtUserId.getText().toString();
        pwd = edtPwd.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.show("请输入用户名");
            return false;
        }
        if (!PatternUtils.getInstance().checkPhone2(this, userName)) {
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show("请输入密码");
            return false;
        }
        return true;
    }

    /**
     * 登录系统
     */
    private void performLogin() {
        addCallList(UserServiceManager.login(userName, pwd,
                new MyResponseCallback<MyHttpResult<User>>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult<User> result) {
                        // 保存用户名
                        AppSpUtils.getInstance().setLoginName(userName);
                        onLoginSuccess(result.getData());
                    }

                    @Override
                    public void onError(TxException e) {

                    }
                }));
    }

    /**
     * 登录成功
     */
    private void onLoginSuccess(User user) {
        if (user == null) {
            ToastUtils.show("登录获取用户信息异常");
            return;
        }
        // 保存跳转
        TxUserManager.getInstance().setCurrentUser(user);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    /////////////////// 第三方登录回调

    @Override
    public void authorizeSuccess(Platform platform) {
        TxLog.e("授权成功" + platform.getName());

        if (context != null) {
            llLoginWechat.setEnabled(true);
            llLoginQQ.setEnabled(true);
            new MaterialDialog.Builder(this)
                    .content(R.string.login_please_wait)
                    .progress(true, 100)
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(final DialogInterface dialog) {
                            // 第三方登录
                            performThirdLogin(dialog, platform);
                        }
                    }).show();
        }
    }

    @Override
    public void authorizeFail() {
        TxLog.e("授权失败");
        if (context != null) {
            llLoginWechat.setEnabled(true);
            llLoginQQ.setEnabled(true);
        }
    }

    /**
     * 第三方登录
     */
    private void performThirdLogin(DialogInterface dialog, Platform platform) {
        String qqUnionId = null;
        String wechatUnionId = null;
        String source = platform.getName();
        PlatformDb platformDb = platform.getDb();
        // 获取id
        if (QQ.NAME.equals(source)) {
            qqUnionId = platformDb.getUserId();
        } else if (Wechat.NAME.equals(source)) {
            wechatUnionId = platformDb.getUserId();
        }

        // 登录系统
        addCallList(UserServiceManager.loginByThird(null, null, qqUnionId, wechatUnionId,
                new MyResponseCallback<MyHttpResult<User>>(this, false) {
                    @Override
                    public void onSuccessResult(MyHttpResult<User> result) {
                        TxLog.e("登录成功");
                        dialog.dismiss();

                        User user = result.getData();
                        if (user != null && TextUtils.isEmpty(user.getAvatar())) {
                            String userIcon = platformDb.getUserIcon();
                            user.setAvatar(userIcon);
                            requestModify(user.getId() + "", userIcon);
                        }
                        onLoginSuccess(user);
                    }

                    @Override
                    public void onError(TxException e) {
                        TxLog.e("登录失败");
                        dialog.dismiss();
                        btnLogin.setEnabled(true);

                        if (e.getResultCode() == 105) {
                            ToastUtils.show("授权成功，请先绑定账号");
                            BindAccountActivity.newInstance(LoginActivity.this, source, platformDb.getUserId());
                        }
                    }
                }));
    }

    /**
     * 第三方登录，如果没有头像设置第三方头像
     */
    private void requestModify(String id, String url) {
        Map<String, String> map = new HashMap<>();
        map.put("avatar", url);
        UserServiceManager.modifyUserInfo(id, map, new MyResponseCallback(false) {
            @Override
            public void onSuccessResult(Object result) {
            }

            @Override
            public void onError(TxException e) {

            }
        });
    }


    /////////////////// 权限申请

    // 获得权限
    protected void onPermissionGranted() {
        TxUpdateManager.update(this);
    }

    // 申请权限
    private void applyPermissionTask() {
        // 申请权限
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            onPermissionGranted();
        } else {
            String rationale = getString(R.string.tx_rationale_update);
            EasyPermissions.requestPermissions(this, rationale, RC_PERMISSIONS, PERMISSIONS);
        }
    }

    // 拒接提示
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
                    .setRationale(getString(R.string.tx_rationale_ask_again))
                    .build()
                    .show();
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
