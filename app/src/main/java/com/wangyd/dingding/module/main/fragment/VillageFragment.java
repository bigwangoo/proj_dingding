package com.wangyd.dingding.module.main.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.base.fragment.BaseLazyFragment;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.DisplayUtils;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.tianxiabuyi.villagedoctor.common.view.LoadingLayout;
import com.tianxiabuyi.villagedoctor.common.view.toprightmenu.TRMenuItem;
import com.tianxiabuyi.villagedoctor.common.view.toprightmenu.TopRightMenu;
import com.tianxiabuyi.villagedoctor.module.archives.activity.VillagerAddActivity;
import com.tianxiabuyi.villagedoctor.module.contract.activity.AddContractActivity;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.wangyd.dingding.module.main.adapter.VillageAdapter;
import com.wangyd.dingding.module.main.model.TownBean;
import com.wangyd.dingding.module.main.model.VillageBean;
import com.tianxiabuyi.villagedoctor.module.search.activity.SearchActivity;
import com.tianxiabuyi.villagedoctor.module.villager.activity.VillagerListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 村镇列表
 */
public class VillageFragment extends BaseLazyFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivRightOne)
    ImageView ivRightOne;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    private TopRightMenu mTopRightMenu;
    private VillageAdapter mAdapter;
    private ArrayList<MultiItemEntity> mData = new ArrayList<>();

    private List<TownBean> townData;
    private TxCall txCall;

    @Override
    public int getLayoutByXml() {
        return R.layout.fragment_village;
    }

    @Override
    public void initView() {
        // 是否是leader, leader显示高级搜索
        if (UserSpUtils.getInstance().isTeamLeader()) {
            rlSearch.setVisibility(View.VISIBLE);
        } else {
            rlSearch.setVisibility(View.GONE);
        }

        ivBack.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.village_title));
        int dip2px = DisplayUtils.dip2px(getActivity(), 18);
        ivRightOne.setVisibility(View.VISIBLE);
        ivRightOne.setPadding(0, dip2px, 0, dip2px);
        ivRightOne.setImageResource(R.drawable.ic_add_white);
        ivRightOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 新增
                mTopRightMenu.showAsDropDown(ivRightOne, -DisplayUtils.dip2px(getActivity(), 72), 0);
            }
        });

        // topRightMenu
        mTopRightMenu = new TopRightMenu(getActivity());
        mTopRightMenu.setHeight(DisplayUtils.dip2px(getActivity(), 100))
                .setWidth(DisplayUtils.dip2px(getActivity(), 112))
                .showIcon(true)
                .dimBackground(true)
                .needAnimationStyle(true)
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuItem(new TRMenuItem(R.drawable.ic_villager_sign_add, "新增签约"))
                .addMenuItem(new TRMenuItem(R.drawable.ic_villager_add, "新增居民"))
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        if (position == 0) {
                            // 新增签约
                            startActivity(new Intent(getActivity(), AddContractActivity.class));
                        } else if (position == 1) {
                            // 新增居民
                            startActivity(new Intent(getActivity(), VillagerAddActivity.class));
                        }
                    }
                });

        // list
        mAdapter = new VillageAdapter(mData, true, true);
        mAdapter.setOnItemClickListener(this);
        // 设置header宽度
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == VillageAdapter.TYPE_ITEM ? 1 : manager.getSpanCount();
            }
        });
        rv.setAdapter(mAdapter);
        // waring !!! important!!!
        // setLayoutManager should be called after setAdapter
        rv.setLayoutManager(manager);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                onRefreshData();
            }
        });
        loadingLayout.setBindView(rv);
        loadingLayout.showLoading();
    }

    @Override
    public void initData() {
        getTownList();
    }

    private void onRefreshData() {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getTownList();
            }
        }, 800);
    }

    @OnClick(R.id.rlSearch)
    public void onViewClicked() {
        // 居民搜索
        SearchActivity.newInstance(getActivity());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TxLog.e("position" + position);

        Object o = adapter.getData().get(position);
        if (o instanceof VillageBean) {
            // 村单位下居民列表
            ArrayList<TownBean> data = new ArrayList<>(townData);
            VillagerListActivity.newInstance(getActivity(), (VillageBean) o, data);
        }
    }

    /**
     * 获取所有镇区域
     */
    private void getTownList() {
        // 重新获取团队ID
        User currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
        String teamId = UserSpUtils.getInstance().getTeamIdString();

        if (TextUtils.isEmpty(teamId)) {
            refreshLayout.finishRefresh();
            loadingLayout.showEmpty();
        } else {
            if (txCall != null && !txCall.isCanceled()) {
                txCall.cancel();
            }
            String uid = currentUser.getId() + "";
            txCall = TeamServiceManager.getScheduleByTown(teamId, uid,
                    new MyResponseCallback<MyHttpResult<List<TownBean>>>() {
                        @Override
                        public void onSuccessResult(MyHttpResult<List<TownBean>> result) {
                            List<TownBean> data = result.getData();
                            townData = data;

                            refreshLayout.finishRefresh();
                            if (data != null && data.size() > 0) {
                                loadingLayout.showSuccess();
                                mData.clear();
                                mData.addAll(convertData(data));
                                mAdapter.notifyDataSetChanged();
                            } else {
                                loadingLayout.showEmpty();
                            }
                            mAdapter.expandAll();
                        }

                        @Override
                        public void onError(TxException e) {
                            refreshLayout.finishRefresh();
                            loadingLayout.showError();
                        }
                    });
            addCallList(txCall);
        }
    }

    /**
     * 数据转换
     */
    private ArrayList<MultiItemEntity> convertData(List<TownBean> data) {
        ArrayList<MultiItemEntity> entities = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            TownBean title = data.get(i);
            List<VillageBean> items = title.getList();
            if (items != null) {
                for (int j = 0; j < items.size(); j++) {
                    VillageBean item = items.get(j);
                    title.addSubItem(item);
                }
            }
            entities.add(title);
        }
        return entities;
    }
}
