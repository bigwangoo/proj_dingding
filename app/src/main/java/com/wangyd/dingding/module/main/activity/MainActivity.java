package com.wangyd.dingding.module.main.activity;

import android.Manifest;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.igexin.sdk.PushManager;
import com.tianxiabuyi.txutils.TxUpdateManager;
import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;
import com.tianxiabuyi.txutils.base.tab.TabEntity;
import com.tianxiabuyi.txutils.receiver.NetStateReceiver;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.wangyd.dingding.module.main.adapter.HomePagerAdapter;
import com.wangyd.dingding.module.main.fragment.FollowupFragment;
import com.wangyd.dingding.module.main.fragment.HomeFragment;
import com.wangyd.dingding.module.main.fragment.StatisticsFragment;
import com.wangyd.dingding.module.main.fragment.VillageFragment;
import com.tianxiabuyi.villagedoctor.module.message.IntentService;
import com.tianxiabuyi.villagedoctor.module.message.PushService;

import java.util.ArrayList;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description Home
 */
public class MainActivity extends BaseTxActivity {
    /**
     * 申请的权限
     */
    protected static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.vpMain)
    ViewPager viewPager;
    @BindView(R.id.tabMain)
    CommonTabLayout tabLayout;
    private String[] mTitles = {"首页", "居民", "随访", "统计"};
    private int[] mIconSelectIds = {
            R.drawable.ic_tab_home_presses, R.drawable.ic_tab_villager_pressed,
            R.drawable.ic_tab_question_presses, R.drawable.ic_tab_statistics_presses};
    private int[] mIconUnSelectIds = {R.drawable.ic_tab_home, R.drawable.ic_tab_villager,
            R.drawable.ic_tab_question, R.drawable.ic_tab_statistics};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MaterialDialog connectDialog;
    private MaterialDialog disconnectDialog;
    private MyNetStateReceiver netStateReceiver = new MyNetStateReceiver();

    @Override
    public int getViewByXml() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        registerNetworkReceiver();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mFragments.add(new HomeFragment());
        mFragments.add(new VillageFragment());
        mFragments.add(new FollowupFragment());
        mFragments.add(new StatisticsFragment());
        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        viewPager.setOffscreenPageLimit(mTitles.length - 1);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void initData() {
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            // 更新检测
            TxUpdateManager.update(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (EasyPermissions.hasPermissions(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)) {
            // 初始化服务
            PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
            // 注册接受消息服务
            PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), IntentService.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkReceiver();
    }

    /**
     * 双击退出
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.common_press_again_to_exit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                // 结束退出
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ActivityUtils.getInstance().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 注册网络连接广播监听
     */
    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(netStateReceiver, filter);
    }

    /**
     * 反注册网络连接广播监听
     */
    private void unregisterNetworkReceiver() {
        unregisterReceiver(netStateReceiver);
    }

    /**
     * 网络连接
     */
    private void onConnectDialog() {
        if (disconnectDialog != null && disconnectDialog.isShowing()) {
            disconnectDialog.dismiss();
        }
        boolean offlineMode = UserSpUtils.getInstance().isOfflineMode();
        if (offlineMode) {
            //今天不再提醒
            boolean showConnectDialog = UserSpUtils.getInstance().isShowConnectTip();
            if (!showConnectDialog) {
                return;
            }
            if (connectDialog == null) {
                connectDialog = new MaterialDialog.Builder(this)
                        .canceledOnTouchOutside(false)
                        .title("网络连接")
                        .content("网络已连接，是否要启用联网模式")
                        .positiveText("联网模式")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                UserSpUtils.getInstance().setOfflineMode(false);
                                // 刷新界面
                            }
                        })
                        .negativeText("取消")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .neutralText("不再提醒")
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                UserSpUtils.getInstance().setShowDialogTomorrow();
                            }
                        })
                        .build();
            }
            connectDialog.show();
        } else {
            //ToastUtils.show("网络已连接");
        }
    }

    /**
     * 网络未连接
     */
    private void showDisconnectDialog() {
        if (connectDialog != null && connectDialog.isShowing()) {
            connectDialog.dismiss();
        }
        boolean offlineMode = UserSpUtils.getInstance().isOfflineMode();
        if (offlineMode) {
            ToastUtils.show("已切换到离线模式");
        } else {
            if (disconnectDialog == null) {
                disconnectDialog = new MaterialDialog.Builder(this)
                        .canceledOnTouchOutside(false)
                        .title("网络错误")
                        .content("网络连接发生错误，是否要启用离线模式")
                        .positiveText("启用离线模式")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                UserSpUtils.getInstance().setOfflineMode(true);
                                // 刷新界面
                            }
                        })
                        .negativeText("取消")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .build();
            }
            disconnectDialog.show();
        }
    }

    /**
     * 网络连接广播监听
     */
    private static class MyNetStateReceiver extends NetStateReceiver {
        @Override
        protected void onConnected() {
            super.onConnected();
            // onConnectDialog();
        }

        @Override
        protected void onDisconnected() {
            super.onDisconnected();
            // showDisconnectDialog();
        }
    }
}