package com.tianxiabuyi.villagedoctor.module.login.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.txutils_ui.edittext.CleanableEditText;
import com.wangyd.dingding.Constant;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.wangyd.dingding.core.utils.PatternUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 重置密码
 */
public class ForgetPwdActivity extends BaseTxTitleActivity {

    @BindView(R.id.edtPwd)
    CleanableEditText edtPwd;
    @BindView(R.id.edtRePwd)
    CleanableEditText edtRePwd;
    @BindView(R.id.btnRest)
    Button btnRest;

    private String phone;

    public static void newInstance(Context context, String phone) {
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        intent.putExtra(Constant.EXTRA_KEY_1, phone);
        context.startActivity(intent);
    }

    @Override
    protected String getTitleString() {
        return "重置密码";
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        phone = intent.getStringExtra(Constant.EXTRA_KEY_1);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btnRest)
    public void onViewClicked() {
        resetPassword();
    }

    private void resetPassword() {
        String pwd = edtPwd.getText().toString().trim();
        String rePwd = edtRePwd.getText().toString().trim();

        if (PatternUtils.getInstance().checkPassword(this, pwd)
                && PatternUtils.getInstance().checkConfirmPassword(pwd, rePwd)) {
            btnRest.setEnabled(false);
            TxCall txCall = UserServiceManager.forgetPassword(phone, pwd, new MyResponseCallback<MyHttpResult>(this) {
                @Override
                public void onSuccessResult(MyHttpResult result) {
                    ToastUtils.show("密码重置成功");
                    finishActivity();
                }

                @Override
                public void onError(TxException e) {
                    btnRest.setEnabled(true);
                }
            });
            addCallList(txCall);
        }
    }

}
