package com.tianxiabuyi.villagedoctor.module.login.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.txutils_ui.edittext.CleanableEditText;
import com.wangyd.dingding.Constant;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.core.utils.PatternUtils;
import com.tianxiabuyi.villagedoctor.module.login.model.User;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发送验证码类型：
 * INPUT_PHONE_FIND
 * INPUT_PHONE_OLD
 * INPUT_PHONE_NEW
 *
 * @author wangyd
 * @date 2018/6/15
 * @description 输入手机号
 */
public class SmsPhoneActivity extends BaseTxTitleActivity {
    // 找回密码
    public static final Integer INPUT_PHONE_FIND = 0;
    // 旧手机号
    public static final Integer INPUT_PHONE_OLD = 1;
    // 新手机号
    public static final Integer INPUT_PHONE_NEW = 2;

    @BindView(R.id.tvTip)
    TextView tvTip;
    @BindView(R.id.edtPhone)
    CleanableEditText edtPhone;
    @BindView(R.id.btnNext)
    Button btnNext;

    private int type;

    /**
     * 短信验证码统一入口
     *
     * @param context Context
     * @param type    需要操作的类型
     */
    public static void newInstance(Context context, int type) {
        Intent intent = new Intent(context, SmsPhoneActivity.class);
        intent.putExtra(Constant.EXTRA_KEY_1, type);
        context.startActivity(intent);
    }

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        type = intent.getIntExtra(Constant.EXTRA_KEY_1, INPUT_PHONE_OLD);
    }

    @Override
    protected String getTitleString() {
        // 找回密码
        if (INPUT_PHONE_FIND == type) {
            return "填写手机号";
        } else {
            return "修改帐号";
        }
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_sms_phone;
    }

    @Override
    public void initView() {
        // 绑定新手机号时提示信息
        if (INPUT_PHONE_NEW == type) {
            tvTip.setVisibility(View.VISIBLE);
        } else {
            tvTip.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btnNext)
    public void onViewClicked() {
        String phone = edtPhone.getText().toString();
        //输入检查
        if (PatternUtils.getInstance().checkPhone(this, phone)) {
            if (INPUT_PHONE_FIND == type) {
                // 找回密码
                SmsCodeActivity.newInstance(this, phone, INPUT_PHONE_FIND);
                finishActivity();
            } else if (INPUT_PHONE_OLD == type) {
                // 旧手机号
                User currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
                if (currentUser != null && phone.equals(currentUser.getUserName())) {
                    SmsCodeActivity.newInstance(this, phone, INPUT_PHONE_OLD);
                    finishActivity();
                } else {
                    ToastUtils.show("请输入当前用户手机号码");
                }
            } else if (INPUT_PHONE_NEW == type) {
                // 新手机号
                SmsCodeActivity.newInstance(this, phone, INPUT_PHONE_NEW);
                finishActivity();
            }
        }
    }

}
