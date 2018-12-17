package com.tianxiabuyi.villagedoctor.module.login.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.txutils_ui.edittext.CleanableEditText;
import com.wangyd.dingding.Constant;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.wangyd.dingding.module.main.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author wangyd
 * @date 2018/6/13
 * @description 第三方登录绑定账户
 */
public class BindAccountActivity extends BaseTxTitleActivity {

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.edtUserId)
    CleanableEditText edtUserId;
    @BindView(R.id.edtPwd)
    CleanableEditText edtPwd;
    @BindView(R.id.tvSwitch)
    TextView tvSwitch;
    @BindView(R.id.tvForgot)
    TextView tvForgot;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private String userName;
    private String pwd;
    private String qqUnionId = null;
    private String wechatUnionId = null;

    public static void newInstance(Context context, String source, String unionId) {
        Intent intent = new Intent(context, BindAccountActivity.class);
        intent.putExtra(Constant.EXTRA_KEY_1, source);
        intent.putExtra(Constant.EXTRA_KEY_2, unionId);
        context.startActivity(intent);
    }

    @Override
    protected String getTitleString() {
        return "账户绑定";
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_login_bind_account;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        hideSoftKeyboard();
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });

        String source = getIntent().getStringExtra(Constant.EXTRA_KEY_1);
        if (QQ.NAME.equals(source)) {
            qqUnionId = getIntent().getStringExtra(Constant.EXTRA_KEY_2);
        } else if (Wechat.NAME.equals(source)) {
            wechatUnionId = getIntent().getStringExtra(Constant.EXTRA_KEY_2);
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tvSwitch, R.id.tvForgot, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSwitch:
                break;
            case R.id.tvForgot:
                break;
            case R.id.btnLogin:
                if (checkInput()) {
                    performLogin();
                }
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
        // 登录系统
        addCallList(UserServiceManager.loginByThird(userName, pwd, qqUnionId, wechatUnionId,
                new MyResponseCallback<MyHttpResult<User>>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult<User> result) {
                        User user = result.getData();
                        onLoginSuccess(user);
                    }

                    @Override
                    public void onError(TxException e) {
                        btnLogin.setEnabled(true);
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

        TxUserManager.getInstance().setCurrentUser(user);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
