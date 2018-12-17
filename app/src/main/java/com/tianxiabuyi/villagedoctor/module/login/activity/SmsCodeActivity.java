package com.tianxiabuyi.villagedoctor.module.login.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wangyd.dingding.core.utils.PatternUtils;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.wangyd.dingding.Constant;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.tianxiabuyi.villagedoctor.common.view.SmsCodeInputLayout;
import com.tianxiabuyi.villagedoctor.module.login.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/6/14
 * @description 发送短信验证码
 */
public class SmsCodeActivity extends BaseTxTitleActivity {

    @BindView(R.id.tvTip)
    TextView tvTip;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.smsCodeInputLayout)
    SmsCodeInputLayout smsCodeInputLayout;
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.btnNext)
    Button btnNext;

    private int type;
    private String phone;
    private String smsCode;

    /**
     * @param context
     * @param phone
     * @param type    操作类型
     */
    public static void newInstance(Context context, String phone, int type) {
        Intent intent = new Intent(context, SmsCodeActivity.class);
        intent.putExtra(Constant.EXTRA_KEY_1, phone);
        intent.putExtra(Constant.EXTRA_KEY_2, type);
        context.startActivity(intent);
    }

    @Override
    protected String getTitleString() {
        return "验证码";
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_sms_code;
    }

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        phone = intent.getStringExtra(Constant.EXTRA_KEY_1);
        type = intent.getIntExtra(Constant.EXTRA_KEY_2, SmsPhoneActivity.INPUT_PHONE_FIND);
    }

    @Override
    public void initView() {
        tvPhone.setText(PatternUtils.getInstance().maskPhone(phone));
        tvCount.setEnabled(false);
        btnNext.setEnabled(false);

        // 新手机号完成
        if (SmsPhoneActivity.INPUT_PHONE_NEW == type) {
            btnNext.setText("完成");
        } else {
            btnNext.setText("下一步");
        }

        // 自定义验证码输入监听
        smsCodeInputLayout.setOnCompleteListener(new SmsCodeInputLayout.Listener() {
            @Override
            public void onComplete(String content) {
                hideSoftKeyboard();
                smsCode = content;
                btnNext.setEnabled(true);
            }

            @Override
            public void onInput(String content) {
                smsCode = content;
            }
        });
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(phone) && Pattern.matches(PatternUtils.PHONE_PATTERN, phone)) {
            sendSms();
        } else {
            ToastUtils.show("手机号码不正确");
            finishActivity();
        }
    }

    @OnClick({R.id.tvCount, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCount:
                // 重发
                sendSms();
                break;
            case R.id.btnNext:
                // 下一步
                checkSmsCode();
                break;
            default:
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendSms() {
        // 校验
        if (PatternUtils.getInstance().checkPhone(this, phone)) {
            if (SmsPhoneActivity.INPUT_PHONE_FIND == type) {
                // 发送
                TxCall txCall = SmsServiceManager.sendCodeFind(phone, new MyResponseCallback<MyHttpResult>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult result) {
                        ToastUtils.show("验证码已发送");

                        tvTip.setText("我们已将验证码发送到您的手机，请及时查收");
                        btnNext.setEnabled(true);

                        CountDownUtil countDownUtil = new CountDownUtil(60 * 1000, 1000);
                        countDownUtil.start();
                    }

                    @Override
                    public void onError(TxException e) {
                        ToastUtils.show("验证码发送失败");
                    }
                });
                addCallList(txCall);

            } else if (SmsPhoneActivity.INPUT_PHONE_OLD == type) {
                // 发送
                TxCall txCall = SmsServiceManager.sendCodeSwitch(phone, new MyResponseCallback<MyHttpResult>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult result) {
                        ToastUtils.show("验证码已发送");

                        tvTip.setText("我们已将验证码发送到您的手机，请及时查收");
                        btnNext.setEnabled(true);

                        CountDownUtil countDownUtil = new CountDownUtil(60 * 1000, 1000);
                        countDownUtil.start();
                    }

                    @Override
                    public void onError(TxException e) {
                        ToastUtils.show("验证码发送失败");
                    }
                });
                addCallList(txCall);

            } else if (SmsPhoneActivity.INPUT_PHONE_NEW == type) {
                // 发送
                TxCall txCall = SmsServiceManager.sendCodeBind(phone, new MyResponseCallback<MyHttpResult>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult result) {
                        ToastUtils.show("验证码已发送");

                        tvTip.setText("我们已将验证码发送到您的手机，请及时查收");
                        btnNext.setEnabled(true);

                        CountDownUtil countDownUtil = new CountDownUtil(60 * 1000, 1000);
                        countDownUtil.start();
                    }

                    @Override
                    public void onError(TxException e) {
                        ToastUtils.show("验证码发送失败");
                    }
                });
                addCallList(txCall);
            }
        }
    }

    /**
     * 校验验证码
     */
    private void checkSmsCode() {
        // 校验
        if (PatternUtils.getInstance().checkCode(this, smsCode)) {
            // 跳转修改
            if (SmsPhoneActivity.INPUT_PHONE_FIND == type) {
                // 找回密码
                SmsServiceManager.checkCodeFind(phone, smsCode, new MyResponseCallback<MyHttpResult>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult result) {
                        btnNext.setEnabled(false);
                        // 找回密码
                        ForgetPwdActivity.newInstance(SmsCodeActivity.this, phone);
                        finishActivity();
                    }

                    @Override
                    public void onError(TxException e) {
                        btnNext.setEnabled(true);
                    }
                });

            } else if (SmsPhoneActivity.INPUT_PHONE_OLD == type) {
                // 输入新手机
                SmsServiceManager.checkCodeSwitch(phone, smsCode, new MyResponseCallback<MyHttpResult>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult result) {
                        btnNext.setEnabled(false);
                        // 输入新手机
                        SmsPhoneActivity.newInstance(SmsCodeActivity.this, SmsPhoneActivity.INPUT_PHONE_NEW);
                        finishActivity();
                    }

                    @Override
                    public void onError(TxException e) {
                        btnNext.setEnabled(true);
                    }
                });
            } else if (SmsPhoneActivity.INPUT_PHONE_NEW == type) {
                // 绑定手机
                SmsServiceManager.checkCodeBind(phone, smsCode, new MyResponseCallback<MyHttpResult>(this) {
                    @Override
                    public void onSuccessResult(MyHttpResult result) {
                        btnNext.setEnabled(false);
                        // 输入新手机
                        bindPhone(phone);
                    }

                    @Override
                    public void onError(TxException e) {
                        btnNext.setEnabled(true);
                    }
                });
            }
        }
    }

    /**
     * 绑定手机
     */
    private void bindPhone(String phone) {
        User currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
        String id = currentUser.getId() + "";

        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        TxCall txCall = UserServiceManager.modifyUserInfo(id, map, new MyResponseCallback(this) {
            @Override
            public void onSuccessResult(Object result) {
                currentUser.setPhone(phone);
                TxUserManager.getInstance().setCurrentUser(currentUser);
                showSendTip();
            }

            @Override
            public void onError(TxException e) {

            }
        });
        addCallList(txCall);
    }

    /**
     * 绑定成提示
     */
    private void showSendTip() {
        new MaterialDialog.Builder(this)
                .title("修改成功")
                .content("你已成功修改账号，下次登录时请使用新的手机号登录")
                .positiveText("确定")
                .negativeText("取消")
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finishActivity();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finishActivity();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finishActivity();
                    }
                })
                .build().show();
    }

    /**
     * 短信验证码倒计时工具
     */
    private class CountDownUtil extends CountDownTimer {

        public CountDownUtil(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            tvCount.setEnabled(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //设置倒计时
            String count = "接收短信大约需要" + millisUntilFinished / 1000 + "秒";
            tvCount.setText(count);
        }

        @Override
        public void onFinish() {
            // 倒计时结束
            tvCount.setEnabled(true);
            tvCount.setText("点击将重新发送");
        }
    }
}
