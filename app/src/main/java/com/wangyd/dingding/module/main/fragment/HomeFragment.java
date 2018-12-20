package com.wangyd.dingding.module.main.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianxiabuyi.txutils.base.fragment.BaseTxFragment;
import com.wangyd.dingding.R;
import com.wangyd.dingding.core.utils.RecyclerUtils;
import com.wangyd.dingding.module.main.adapter.HomeAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author wangyd
 * @date 2018/12/20
 * @description
 */
public class HomeFragment extends BaseTxFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private HomeAdapter mAdapter;

    @Override
    public int getLayoutByXml() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        mAdapter = new HomeAdapter(new ArrayList<>());
        RecyclerUtils.setLinearRecyclerDivider(getActivity(), rvList, mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void initData() {

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
