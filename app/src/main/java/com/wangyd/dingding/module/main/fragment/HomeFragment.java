package com.wangyd.dingding.module.main.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.fragment.BaseLazyFragment;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.DisplayUtils;
import com.tianxiabuyi.villagedoctor.BuildConfig;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.wangyd.dingding.api.model.WeatherResult;
import com.wangyd.dingding.core.utils.LocationUtils;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.tianxiabuyi.villagedoctor.module.device.activity.BindDeviceActivity;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.wangyd.dingding.module.main.adapter.ScheduleAdapter;
import com.wangyd.dingding.module.main.model.PovertyBean;
import com.wangyd.dingding.module.main.model.TownBean;
import com.wangyd.dingding.module.main.model.VillageBean;
import com.tianxiabuyi.villagedoctor.module.message.IntentService;
import com.wangyd.dingding.module.personal.activity.PersonalActivity;
import com.tianxiabuyi.villagedoctor.module.search.model.SearchBean;
import com.tianxiabuyi.villagedoctor.module.team.activity.TeamVillageActivity;
import com.tianxiabuyi.villagedoctor.module.team.event.SignInEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 首页
 */
public class HomeFragment extends BaseLazyFragment implements BaseQuickAdapter.OnItemClickListener,
        EasyPermissions.PermissionCallbacks, IntentService.noticeInterface {

    /**
     * 权限申请Code
     */
    protected static final int RC_PERMISSIONS = 10001;
    /**
     * 申请的权限
     */
    public static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivRightOne)
    ImageView ivRightOne;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    private View header;
    private TextView tvName;
    private TextView tvTip;
    private TextView tvWeather;
    private TextView tvTemperature;
    private ProgressLineView pvProgress;
    private RedPointDrawable mRedPointDrawable;

    /**
     * 团队日程
     */
    private ScheduleAdapter mAdapter;
    private List<TownBean> mData = new ArrayList<>();
    private User currentUser;
    /**
     * 高德定位
     */
    private String city;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    /**
     * 消息通知
     */
    public static IntentService.noticeInterface sNoticeInterface;

    /**
     * 获取首页标题
     */
    public String getHomeTitleByCountyId() {
        String countyId = UserSpUtils.getInstance().getCountyId();

        if ("140221000000".equals(countyId)) {
            return "阳高县公共卫生服务";

        } else if ("140621000000".equals(countyId)) {
            return "山阴县公共卫生服务";

        } else if ("140226000000".equals(countyId)) {
            return "左云县公共卫生服务";

        } else {
            return "公共卫生服务";
        }
    }

    @Override
    public int getLayoutByXml() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        if (getActivity() == null) {
            return;
        }
        sNoticeInterface = this;

        // title
        tvTitle.setText(getHomeTitleByCountyId());
        int dip2px = DisplayUtils.dip2px(getActivity(), 18);
        ivBack.setImageResource(R.drawable.ic_home_device);
        ivBack.setPadding(dip2px, dip2px, dip2px, dip2px);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BindDeviceActivity.class));
            }
        });

        ivRightOne.setVisibility(View.VISIBLE);
        mRedPointDrawable = new RedPointDrawable(getActivity(),
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_home_center, null));
        mRedPointDrawable.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ivRightOne.setImageDrawable(mRedPointDrawable);
        notice();
        ivRightOne.setPadding(0, dip2px, 0, dip2px);
        addDisposable(RxView.clicks(ivRightOne)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(getActivity(), PersonalActivity.class));
                    }
                }));

        // rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ScheduleAdapter(mData);

        header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_top, rv, false);
        tvName = header.findViewById(R.id.tvName);
        tvTip = header.findViewById(R.id.tvTip);
        tvWeather = header.findViewById(R.id.tvWeather);
        tvTemperature = header.findViewById(R.id.tvTemperature);
        pvProgress = header.findViewById(R.id.pvProgress);
        mAdapter.addHeaderView(header);
        mAdapter.setHeaderAndEmpty(true);
        mAdapter.setOnItemClickListener(this);
        rv.setAdapter(mAdapter);
        mAdapter.setEmptyView(getLoadingView());

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                onRefreshData();
            }
        });

        currentUser = TxUserManager.getInstance().getCurrentUser(User.class);

        // 初始化定位
        initLocation();
        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        tvName.setText(currentUser.getName());
        tvTip.setText("欢迎登录,祝您生活愉快");
        getProgress();
        getSchedule();
        getTeamList();
        // 申请定位权限
        applyPermissionTask();
    }

    /**
     * 下拉刷新
     */
    public void onRefreshData() {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvName.setText(currentUser.getName());
                tvTip.setText("欢迎登录,祝您生活愉快");
                getProgress();
                getSchedule();
                getTeamList();
                // 申请定位权限
                applyPermissionTask();
            }
        }, 800);
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        refreshLayout.autoRefresh(500);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<TownBean> data = adapter.getData();
        TownBean townBean = data.get(position);
        if (townBean == null) {
            return;
        }
        TeamVillageActivity.newInstance(getActivity(), townBean.getName(), (ArrayList<VillageBean>) townBean.getList());
    }

    /**
     * 获取用户信息和团队列表 更新用户信息
     */
    private void getTeamList() {
        if (currentUser == null) {
            return;
        }

        int id = currentUser.getId();
        addCallList(TeamServiceManager.getTeamDataById(id,
                new MyResponseCallback<MyHttpResult<User>>(false) {
                    @Override
                    public void onSuccessResult(MyHttpResult<User> result) {
                        User newUser = result.getData();
                        if (newUser != null) {
                            TxUserManager.getInstance().setCurrentUser(newUser);
                        }
                    }

                    @Override
                    public void onError(TxException e) {

                    }
                }));
    }

    /**
     * 获取任务进度
     */
    private void getProgress() {
        addCallList(TeamServiceManager.getPovertyCount(new MyResponseCallback<MyHttpResult<PovertyBean>>(false) {
            @Override
            public void onSuccessResult(MyHttpResult<PovertyBean> result) {
                PovertyBean povertyBean = result.getData();
                pvProgress.setMaxProgress(povertyBean.getSumCount());
                pvProgress.setCurProgress(povertyBean.getCount());
            }

            @Override
            public void onError(TxException e) {

            }
        }));
    }

    /**
     * 获取团队日程
     */
    private void getSchedule() {
        String teamId = UserSpUtils.getInstance().getTeamIdString();
        //TxLog.e(teamId);

        if (!TextUtils.isEmpty(teamId) && currentUser != null) {
            String uid = currentUser.getId() + "";
            addCallList(TeamServiceManager.getScheduleByTown(teamId, uid,
                    new MyResponseCallback<MyHttpResult<List<TownBean>>>() {
                        @Override
                        public void onSuccessResult(MyHttpResult<List<TownBean>> result) {
                            List<TownBean> data = result.getData();

                            if (refreshLayout != null) {
                                refreshLayout.finishRefresh();
                            }
                            if (data != null && data.size() > 0) {
                                mData.clear();
                                mData.addAll(data);
                                mAdapter.notifyDataSetChanged();

                                // 保存团队信息
                                ArrayList<SearchBean> beans = new ArrayList<>();
                                for (int i = 0; i < data.size(); i++) {
                                    TownBean townBean = data.get(i);
                                    beans.add(new SearchBean(townBean.getCode(), townBean.getName()));
                                }
                                UserSpUtils.getInstance().setTeamArea(beans);
                            } else {
                                mData.clear();
                                mAdapter.notifyDataSetChanged();
                                mAdapter.setEmptyView(getEmptyView());
                            }
                        }

                        @Override
                        public void onError(TxException e) {
                            if (refreshLayout != null) {
                                refreshLayout.finishRefresh();
                            }
                            mData.clear();
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setEmptyView(getEmptyView());
                        }
                    }));
        } else {
            if (refreshLayout != null) {
                refreshLayout.finishRefresh();
            }
            mAdapter.setEmptyView(getEmptyView());
        }
    }

    /**
     * 获取天气信息
     */
    private void getWeather() {
        addCallList(AppServiceManager.getWeather(city, new MyResponseCallback<WeatherResult>(false) {
            @Override
            public void onSuccessResult(WeatherResult result) {
                try {
                    List<WeatherResult.HeWeather6Bean> heWeather6 = result.getHeWeather6();
                    if (heWeather6 != null && heWeather6.size() > 0) {
                        WeatherResult.HeWeather6Bean heWeather6Bean = heWeather6.get(0);
                        if (heWeather6Bean != null) {
                            String status = heWeather6Bean.getStatus();
                            if ("ok".equals(status)) {
                                WeatherResult.HeWeather6Bean.NowBean now = heWeather6Bean.getNow();
                                setWeatherNow(now.getCond_txt(), now.getTmp());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(TxException e) {
                setWeatherNow("", "");
            }
        }));
    }

    /**
     * 天气转换
     */
    private void setWeatherNow(String weatherTxt, String tmp) {
        if (TextUtils.isEmpty(weatherTxt) && TextUtils.isEmpty(tmp)) {
            tvWeather.setText("暂无天气信息");
            tvTemperature.setText("-- °C");
        } else {
            tvWeather.setText(weatherTxt);
            tvTemperature.setText(tmp + " °C");
        }
    }

    /**
     * 加载中布局
     */
    private View getLoadingView() {
        //todo
        View empty = View.inflate(getActivity(), R.layout.def_layout_empty, null);
        int height = rv.getMeasuredHeight() - header.getMeasuredHeight();
        empty.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        TextView tv = empty.findViewById(R.id.tv_empty);
        tv.setText("正在加载...");
        return empty;
    }

    /**
     * 空布局
     */
    private View getEmptyView() {
        //todo
        View empty = View.inflate(getActivity(), R.layout.def_layout_empty, null);
        int height = rv.getMeasuredHeight() - header.getMeasuredHeight();
        empty.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        TextView tv = empty.findViewById(R.id.tv_empty);
        tv.setText("暂无工作事项");
        return empty;
    }


    /**
     * 定位监听
     */
    private AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    city = location.getCity();
                    getWeather();
                    stopLocation();
                    if (BuildConfig.DEBUG) {
                        LocationUtils.logForSuccessLocation(location);
                    }
                } else {
                    //ToastUtils.show("定位失败");
                    if (BuildConfig.DEBUG) {
                        LocationUtils.logForFailureLocation(location);
                    }
                }
            } else {
                //ToastUtils.show("定位失败");
            }
        }
    };

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //默认参数
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(false);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        mOption.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(true);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(true);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(true);
        //可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);
        return mOption;
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        if (EasyPermissions.hasPermissions(getActivity(), PERMISSIONS)) {
            // 设置定位参数
            locationClient.setLocationOption(locationOption);
            // 启动定位
            locationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != locationClient) {
            //如果AMapLocationClient是在当前Activity实例化的，
            // 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
        EventBus.getDefault().unregister(this);
        sNoticeInterface = null;
    }

    /**
     * 消息通知
     */
    public static void showRedPoint() {
        if (sNoticeInterface != null) {
            sNoticeInterface.notice();
        }
    }

    @Override
    public void notice() {
        getActivity().runOnUiThread(() -> {
            mRedPointDrawable.setShowRedPoint(IntentService.getMessageNum() > 0);
        });
    }


    //// 权限申请
    private void applyPermissionTask() {
        // 申请权限
        if (EasyPermissions.hasPermissions(getActivity(), PERMISSIONS)) {
            onPermissionGranted();
        } else {
            String rationale = getString(R.string.tx_rationale_location);
            EasyPermissions.requestPermissions(this, rationale, RC_PERMISSIONS, PERMISSIONS);
        }
    }

    protected void onPermissionGranted() {
        // 获得权限
        startLocation();
    }

    protected void showPermissionDeniedDialog() {
        //权限拒绝提示 申请或退出
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage(getString(R.string.tx_rationale_denied))
                .setCancelable(false)
                .setPositiveButton("申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermissionTask();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        TxLog.d("onPermissionsGranted:" + requestCode + ":" + perms.size());

        onPermissionGranted();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        TxLog.d("onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // 拒绝权限（不再询问）
            new AppSettingsDialog.Builder(this)
                    .setRationale(getString(R.string.tx_rationale_ask_again)).build().show();
        } else if (requestCode == RC_PERMISSIONS) {
            // 拒绝权限
            showPermissionDeniedDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignInEvent(SignInEvent event) {
        // 签到刷新界面
        if (refreshLayout != null) {
            getSchedule();
        }
    }
}
