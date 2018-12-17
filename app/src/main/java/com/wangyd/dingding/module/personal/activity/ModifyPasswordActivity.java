package com.wangyd.dingding.module.personal.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.KeyBoardUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.txutils_ui.edittext.CleanableEditText;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.wangyd.dingding.core.utils.PatternUtils;
import com.tianxiabuyi.villagedoctor.module.login.activity.LoginActivity;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.tianxiabuyi.villagedoctor.module.login.utils.LogoutUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/6/19
 * @description 修改密码
 */
public class ModifyPasswordActivity extends BaseTxTitleActivity {

    @BindView(R.id.etOld)
    CleanableEditText etOld;
    @BindView(R.id.etNew)
    CleanableEditText etNew;
    @BindView(R.id.etConfirm)
    CleanableEditText etConfirm;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    private User currentUser;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, ModifyPasswordActivity.class));
    }

    @Override
    protected String getTitleString() {
        return getString(R.string.person_title_change_password);
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_personal_change_password;
    }

    @Override
    public void initView() {
        currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btnConfirm, R.id.activity_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnConfirm:
                changePassword();
                break;
            case R.id.activity_change_password:
                hideSoftKeyboard();
                break;
            default:
                break;
        }
    }

    private void changePassword() {
        if (checkPwd()) {
            KeyBoardUtils.closeKeybord(getCurrentFocus(), this);
            requestModify();
        }
    }

    /**
     * 验证输入的密码信息
     */
    private boolean checkPwd() {
        String oldStr = etOld.getText().toString().trim();
        String newStr = etNew.getText().toString().trim();
        String newPassConfirm = etConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(oldStr)) {
            ToastUtils.show("请输入原密码");
            return false;
        }
        if (TextUtils.isEmpty(newStr)) {
            ToastUtils.show("请输入新密码");
            return false;
        }
        return PatternUtils.getInstance().checkPassword(this, newStr) &&
                PatternUtils.getInstance().checkConfirmPassword(newStr, newPassConfirm);
    }

    /**
     * 修改密码
     */
    private void requestModify() {
        String id = currentUser.getId() + "";
        String oldStr = etOld.getText().toString();
        String newPwd = etNew.getText().toString();

        addCallList(UserServiceManager.resetPassword(id, oldStr, newPwd,
                new MyResponseCallback<MyHttpResult>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult result) {
                        toast("密码修改成功");
                        // 退出登录
                        LogoutUtils.logout(ModifyPasswordActivity.this);
                        startActivity(new Intent(ModifyPasswordActivity.this, LoginActivity.class));
                        ActivityUtils.getInstance().finishAllActivityExcept(LoginActivity.class);
                    }

                    @Override
                    public void onError(TxException e) {

                    }
                }));
    }
}
