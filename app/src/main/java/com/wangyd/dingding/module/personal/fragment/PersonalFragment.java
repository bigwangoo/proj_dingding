package com.wangyd.dingding.module.personal.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils.base.fragment.BaseTxFragment;
import com.tianxiabuyi.txutils_ui.setting.SettingItemView;
import com.wangyd.dingding.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/12/20
 * @description
 */
public class PersonalFragment extends BaseTxFragment {

    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.siv_feedback)
    SettingItemView sivFeedback;
    @BindView(R.id.siv_service)
    SettingItemView sivService;
    @BindView(R.id.siv_help)
    SettingItemView sivHelp;
    @BindView(R.id.siv_share)
    SettingItemView sivShare;

    @Override
    public int getLayoutByXml() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initView() {
        tvName.setText("李某某");
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_avatar, R.id.tv_info, R.id.siv_feedback,
            R.id.siv_service, R.id.siv_help, R.id.siv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                break;
            case R.id.tv_info:
                break;
            case R.id.siv_feedback:
                break;
            case R.id.siv_service:
                break;
            case R.id.siv_help:
                break;
            case R.id.siv_share:
                break;
            default:
                break;
        }
    }

}
