package com.wangyd.dingding.module.login.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 短信验证倒计时
 */
public class CountDownUtil extends CountDownTimer {

    // 要显示的倒计时TextView
    private TextView mTextView;

    public CountDownUtil(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        mTextView.setEnabled(false);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //设置倒计时
        String count = "接收短信大约需要" + millisUntilFinished / 1000 + "秒)";
        mTextView.setText(count);
    }

    @Override
    public void onFinish() {
        // 倒计时结束
        mTextView.setEnabled(true);
        mTextView.setText("获取验证码");
    }
}
