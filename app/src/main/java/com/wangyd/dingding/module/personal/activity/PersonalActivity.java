package com.wangyd.dingding.module.personal.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tianxiabuyi.txutils.TxImageLoader;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.util.AppUtils;
import com.tianxiabuyi.txutils.util.StringUtils;
import com.tianxiabuyi.txutils_ui.setting.SettingItemView;
import com.tianxiabuyi.villagedoctor.R;
import com.tianxiabuyi.villagedoctor.common.base.BaseMvpTitleActivity;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.tianxiabuyi.villagedoctor.module.cache.activity.CacheDataActivity;
import com.tianxiabuyi.villagedoctor.module.contacts.activity.ContactsActivity;
import com.tianxiabuyi.villagedoctor.module.login.activity.SmsPhoneActivity;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.tianxiabuyi.villagedoctor.module.message.IntentService;
import com.tianxiabuyi.villagedoctor.module.message.activity.MessageActivity;
import com.tianxiabuyi.villagedoctor.module.offline.OfflineDataActivity;
import com.wangyd.dingding.module.personal.event.UpdateUserEvent;
import com.wangyd.dingding.module.setting.activity.SettingActivity;
import com.tianxiabuyi.villagedoctor.module.weex.WeexSampleActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/5/10
 * @description 个人中心
 */
public class PersonalActivity extends BaseMvpTitleActivity<PersonalPresenter>
        implements PersonalContract.View, IntentService.noticeInterface {

    @BindView(R.id.llPersonal)
    LinearLayout llPersonal;
    @BindView(R.id.ivAvatar)
    RoundedImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLeader)
    TextView tvLeader;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTeam)
    TextView tvTeam;
    @BindView(R.id.sivMsg)
    SettingItemView sivMsg;
    @BindView(R.id.sivContact)
    SettingItemView sivContact;
    @BindView(R.id.sivAccount)
    SettingItemView sivAccount;
    @BindView(R.id.sivPwd)
    SettingItemView sivPwd;
    @BindView(R.id.sivOffline)
    SettingItemView sivOffline;
    @BindView(R.id.sivOfflineData)
    SettingItemView sivOfflineData;
    @BindView(R.id.sivSetting)
    SettingItemView sivSetting;
    @BindView(R.id.sivYzkb)
    SettingItemView sivYzkb;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private RedPointDrawable mRedPointDrawable;

    public static IntentService.noticeInterface sNoticeInterface;

    public static void showRedPoint() {
        if (sNoticeInterface != null) {
            sNoticeInterface.notice();
        }
    }

    @Override
    public void notice() {
        runOnUiThread(() -> {
            mRedPointDrawable.setShowRedPoint(IntentService.getMessageNum() > 0);
        });
    }

    @Override
    protected String getTitleString() {
        return "个人中心";
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_personal;
    }

    @Override
    protected PersonalPresenter createPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public void initView() {
        sNoticeInterface = this;
        User currentUser = TxUserManager.getInstance().getCurrentUser(User.class);

        // 院长看板
        if (User.TYPE_PRESIDENT == currentUser.getIsPresident()) {
            sivYzkb.setVisibility(View.VISIBLE);
        } else {
            sivYzkb.setVisibility(View.GONE);
        }

        // 姓名
        tvName.setText(currentUser.getName());
        // 头像
        int placeHolder = currentUser.getGender() == User.GENDER_MAN ? R.drawable.def_user_avatar_man : R.drawable.def_user_avatar_woman;
        TxImageLoader.getInstance().loadImageCircle(this, currentUser.getAvatar(), ivAvatar, placeHolder);
        // 职称
        String title = StringUtils.emptyIfNull(currentUser.getOrgName()) + "  " + StringUtils.emptyIfNull(currentUser.getTitle());
        tvTitle.setText(title);
        // 团队
        tvTeam.setText(UserSpUtils.getInstance().getTeamNameString());

        // 消息红点
        mRedPointDrawable = new RedPointDrawable(this, ResourcesCompat.getDrawable(getResources(), R.drawable.person_mgs, null));
        mRedPointDrawable.setGravity(Gravity.RIGHT | Gravity.TOP);
        sivMsg.getIvLeftIcon().setImageDrawable(mRedPointDrawable);
        // 当前版本
        tvVersion.setText("当前版本 v" + AppUtils.getVersionName(this));
    }

    @Override
    public void initData() {
        setEventBusEnabled();
        notice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOffLineModeState();
        mPresenter.getOfflineData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sNoticeInterface = null;
    }

    @OnClick({R.id.llPersonal, R.id.sivMsg, R.id.sivContact, R.id.sivAccount, R.id.sivPwd,
            R.id.sivOffline, R.id.sivOfflineData, R.id.sivSetting, R.id.sivYzkb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llPersonal:
                // 个人信息
                PersonalInfoActivity.newInstance(this);
                break;
            case R.id.sivMsg:
                // 消息通知
                MessageActivity.newInstance(this);
                break;
            case R.id.sivContact:
                // 通讯录
                ContactsActivity.newInstance(this);
                break;
            case R.id.sivAccount:
                // 账号修改
                SmsPhoneActivity.newInstance(this, SmsPhoneActivity.INPUT_PHONE_OLD);
                break;
            case R.id.sivPwd:
                // 修改密码
                ModifyPasswordActivity.newInstance(this);
                break;
            case R.id.sivOffline:
                // 缓存数据
                startActivity(new Intent(this, CacheDataActivity.class));
                break;
            case R.id.sivOfflineData:
                // 离线上传
                startActivity(new Intent(this, OfflineDataActivity.class));
                break;
            case R.id.sivSetting:
                // 设置界面
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.sivYzkb:
                // 院长看板
                startActivity(new Intent(this, YzkbActivity.class));
                break;
            case 100010001:
                startAnimActivity(new Intent(this, WeexSampleActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void setOffLineModeState() {
        boolean offlineMode = UserSpUtils.getInstance().isOfflineMode();
        if (offlineMode) {
            sivOffline.setRightText("已启用");
            sivOffline.getTvRightTitle().setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            sivOffline.setRightText("未启用");
            sivOffline.getTvRightTitle().setTextColor(ContextCompat.getColor(this, R.color.gray_light));
        }
    }

    @Override
    public void setOffLineDataSize(String size) {
        if (size != null) {
            sivOfflineData.setRightText("共" + size + "条记录");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoUpdate(UpdateUserEvent event) {
        // 更新头像
        TxImageLoader.getInstance().loadImageCircle(this, event.getAvatar(), ivAvatar);
    }
}
