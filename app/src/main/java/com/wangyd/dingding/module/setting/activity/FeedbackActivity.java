package com.wangyd.dingding.module.setting.activity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.villagedoctor.R;

import butterknife.BindView;

/**
 * Created by xjh1994 on 16/12/13.
 * 意见反馈
 */
public class FeedbackActivity extends BaseTxTitleActivity {

    @BindView(R.id.etContent)
    EditText etContent;

    private String mContent;

    @Override
    protected String getTitleString() {
        return getString(R.string.setting_feedback);
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        getBackImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitleRightText("提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFeedback()) {
                    performFeedback();
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 校验
     */
    private boolean checkFeedback() {
        mContent = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(mContent)) {
            ToastUtils.show(getString(R.string.setting_feedback_empty_content));
            return false;
        } else if (mContent.length() > 200) {
            toast(getString(R.string.setting_feedback_over_limit));
            return false;
        }
        return true;
    }

    /**
     * 提交
     */
    private void performFeedback() {
        MaterialDialog mUploadDialog = new MaterialDialog.Builder(this)
                .title(R.string.setting_feedback_committing)
                .content(R.string.common_please_wait)
                .progress(true, 100)
                .build();
        mUploadDialog.show();

//        TxCall call = HomeManager.feedback(mContent, new ResponseCallback<HttpResult<String>>(false) {
//            @Override
//            public void onSuccess(HttpResult<String> result) {
//                mUploadDialog.dismiss();
//
//                ToastUtils.show("反馈成功");
//                finish();
//            }
//
//            @Override
//            public void onError(TxException e) {
//                mUploadDialog.dismiss();
//
//                ToastUtils.show("反馈失败");
//            }
//        });
//        addCallList(call);
    }

    @Override
    public void onBackPressed() {
        if (etContent.getText().toString().trim().length() > 0) {
            new MaterialDialog.Builder(this)
                    .title(R.string.setting_feedback_cancel)
                    .positiveText(R.string.common_confirm)
                    .negativeText(R.string.common_cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            FeedbackActivity.super.onBackPressed();
                        }
                    })
                    .onNegative(null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}
