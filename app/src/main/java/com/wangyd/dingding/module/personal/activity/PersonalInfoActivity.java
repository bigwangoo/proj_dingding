package com.wangyd.dingding.module.personal.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tianxiabuyi.txutils.TxImageLoader;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.activity.TxImagePreViewActivity;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxFileResult;
import com.tianxiabuyi.txutils.util.avatar.TxPhotoHelper;
import com.tianxiabuyi.txutils_ui.setting.SettingItemView;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.tianxiabuyi.villagedoctor.module.login.utils.LoginApiUtil;
import com.wangyd.dingding.module.personal.event.UpdateUserEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;

/**
 * @author wangyd
 * @date 2018/6/7
 * @description 个人信息
 */
public class PersonalInfoActivity extends BaseTxTitleActivity implements LoginApiUtil.OnAuthorizeListener {

    @BindView(R.id.sivAvatar)
    SettingItemView sivAvatar;
    @BindView(R.id.sivName)
    SettingItemView sivName;
    @BindView(R.id.sivGender)
    SettingItemView sivGender;
    @BindView(R.id.sivHospital)
    SettingItemView sivHospital;
    @BindView(R.id.sivTitle)
    SettingItemView sivTitle;
    @BindView(R.id.sivFpTeam)
    SettingItemView sivFpTeam;
    @BindView(R.id.sivFpArea)
    SettingItemView sivFpArea;
//    @BindView(R.id.siv_wechat)
//    SettingItemView sivWechat;
//    @BindView(R.id.siv_qq)
//    SettingItemView sivQq;

    private ImageView ivAvatar;
    private User currentUser;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, PersonalInfoActivity.class));
    }

    @Override
    protected String getTitleString() {
        return "个人信息";
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initView() {
        setEventBusEnabled();

        ivAvatar = sivAvatar.getIvRightIcon();
        currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
    }

    @Override
    public void initData() {
        if (currentUser != null) {
            // 头像预览
            ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TxImagePreViewActivity.newInstance(PersonalInfoActivity.this, "头像", currentUser.getAvatar());
                }
            });
            TxImageLoader.getInstance().loadImageCircle(this, currentUser.getAvatar(), ivAvatar, getPlaceHolder());
            // 个人信息
            sivName.setRightText(currentUser.getName());
            sivGender.setRightText(currentUser.getGender() == User.GENDER_MAN ? "男" : "女");
            sivHospital.setRightText(currentUser.getOrgName());
            sivTitle.setRightText(currentUser.getTitle());
            // 区域
            sivFpArea.setRightText(UserSpUtils.getInstance().getTeamAreaName());
            // 团队
            sivFpTeam.setRightText(UserSpUtils.getInstance().getTeamNameString());
            // 第三方登录
            // setThirdAccounts();
        }
    }

    @OnClick({R.id.sivAvatar, R.id.sivFpTeam, R.id.sivFpArea, R.id.siv_wechat, R.id.siv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sivAvatar:
                TxPhotoHelper.newInstance().quality(100).start(this);
                break;
            case R.id.sivFpTeam:
                showDetailInfo(sivFpTeam.getRightText());
                break;
            case R.id.sivFpArea:
                showDetailInfo(sivFpArea.getRightText());
                break;
            case R.id.siv_wechat:
                break;
            case R.id.siv_qq:
                break;
            default:
                break;
        }
    }

    /**
     * 获取默认头像
     */
    private int getPlaceHolder() {
        if (currentUser != null) {
            if (currentUser.getGender() == User.GENDER_MAN) {
                return R.drawable.def_user_avatar_man;
            }
        }
        return R.drawable.def_user_avatar_woman;
    }

    /**
     * 显示过长信息
     */
    private void showDetailInfo(String info) {
        if (TextUtils.isEmpty(info)) {
            return;
        }
        new MaterialDialog.Builder(this)
                .content(info)
                .positiveText("确认")
                .contentColor(ContextCompat.getColor(this, R.color.TextColor1))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 修改头像
     */
    private void requestModify(String url) {
        String id = currentUser.getId() + "";
        Map<String, String> map = new HashMap<>(1);
        map.put("avatar", url);
        addCallList(UserServiceManager.modifyUserInfo(id, map,
                new MyResponseCallback(this) {
                    @Override
                    public void onSuccessResult(Object result) {
                        toast("头像修改成功");
                        EventBus.getDefault().post(new UpdateUserEvent(url));
                    }

                    @Override
                    public void onError(TxException e) {

                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TxPhotoHelper.TAKE_PHOTO || requestCode == TxPhotoHelper.CHOOSE_PICTURE) {
                TxFileResult result = (TxFileResult) data.getSerializableExtra(TxPhotoHelper.EXTRA_RESULT);
                if (result != null) {
                    toast("头像上传成功");
                    requestModify(result.getImg());
                }
            }
        } else if (resultCode == TxPhotoHelper.RESULT_ERROR) {
            if (data == null) {
                return;
            }
            String error = data.getStringExtra(TxPhotoHelper.EXTRA_RESULT_ERROR);
            toast("头像上传失败，" + error);
        }
    }

    /**
     * 头像修改事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoUpdate(UpdateUserEvent event) {
        // 更新头像
        String avatar = event.getAvatar();
        TxImageLoader.getInstance().loadImageCircle(this, avatar, ivAvatar, getPlaceHolder());
        User currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
        if (currentUser != null) {
            currentUser.setAvatar(avatar);
        }
        TxUserManager.getInstance().setCurrentUser(currentUser);
    }


    /////////////////////////// 第三方账号

//    /**
//     * 设置第三方
//     */
//    private void setThirdAccounts() {
//        String qqUnionId = currentUser.getQqUnionId();
//        if (TextUtils.isEmpty(qqUnionId)) {
//            sivQq.getTvRightTitle().setText(getString(R.string.person_unbind));
//            sivQq.getTvRightTitle().setTextColor(ContextCompat.getColor(this, R.color.gray));
//        } else {
//            sivQq.getTvRightTitle().setText(getString(R.string.person_binded));
//            sivQq.getTvRightTitle().setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        }
//
//        String wechatUnionId = currentUser.getWechatUnionId();
//        if (TextUtils.isEmpty(wechatUnionId)) {
//            sivWechat.getTvRightTitle().setText(getString(R.string.person_unbind));
//            sivWechat.getTvRightTitle().setTextColor(ContextCompat.getColor(this, R.color.gray));
//        } else {
//            sivWechat.getTvRightTitle().setText(getString(R.string.person_binded));
//            sivWechat.getTvRightTitle().setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        }
//    }

    @Override
    public void authorizeSuccess(Platform platform) {

    }

    @Override
    public void authorizeFail() {

    }

//    /**
//     * 绑定/解绑第三方账号
//     */
//    private void operateAccount(final String name) {
//        final AccountBean accountBean = AccountService.getAccount(getSource(name));
//        if (accountBean != null) {
//            new AlertDialog.Builder(this)
//                    .setTitle("温馨提示")
//                    .setMessage("确定要解除绑定吗？")
//                    .setNegativeButton(R.string.common_cancel, null)
//                    .setPositiveButton(R.string.common_confirm, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            unBindAccount(accountBean);
//                        }
//                    })
//                    .show();
//        } else {
//            loginApi.setPlatform(name);
//            loginApi.login(this);
//        }
//    }
//
//    /**
//     * 绑定第三方账号
//     */
//    private void bindAccount(final String source, final PlatformDb platformDb) {
//        TxUserService userService = TxServiceManager.createService(TxUserService.class);
//        userService.bindAccount(source, platformDb.getUserId(), platformDb.getToken())
//                .enqueue(new ResponseCallback<HttpResult>(true) {
//                    @Override
//                    public void onSuccess(HttpResult result) {
//                        AccountService.save(accountBean);
//                        setThirdAccounts();
//                    }
//
//                    @Override
//                    public void onError(TxException e) {
//                        ToastUtils.show(e.getDetailMessage());
//                    }
//                });
//    }
//
//    /**
//     * 解绑第三方账号
//     */
//    private void unbindAccount() {
//        TxUserService userService = TxServiceManager.createService(TxUserService.class);
//        userService.unBindAccount(accountBean.getSource(), accountBean.getUnion_id(), accountBean.getToken())
//                .enqueue(new ResponseCallback<HttpResult>(true) {
//                    @Override
//                    public void onSuccess(HttpResult result) {
//                        AccountService.remove(accountBean.getUnion_id());
//                        if (accountBean.getSource().equals(SOURCE_QQ)) {
//                            ShareSDK.getPlatform(QQ.NAME).removeAccount(true);
//                        } else if (accountBean.getSource().equals(SOURCE_WECHAT)) {
//                            ShareSDK.getPlatform(Wechat.NAME).removeAccount(true);
//                        } else if (accountBean.getSource().equals(SOURCE_WEIBO)) {
//                            ShareSDK.getPlatform(SinaWeibo.NAME).removeAccount(true);
//                        }
//                        initThirdAccounts();
//                    }
//
//                    @Override
//                    public void onError(TxException e) {
//                        ToastUtils.show(e.getDetailMessage());
//                    }
//                });
//    }
}
