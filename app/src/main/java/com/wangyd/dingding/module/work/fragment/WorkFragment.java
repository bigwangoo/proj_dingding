package com.wangyd.dingding.module.work.fragment;

import android.support.v7.widget.RecyclerView;

import com.tianxiabuyi.txutils.base.fragment.BaseTxFragment;
import com.tianxiabuyi.txutils.base.fragment.BaseTxTitleFragment;
import com.wangyd.dingding.R;
import com.wangyd.dingding.core.model.ItemBean;
import com.wangyd.dingding.core.utils.RecyclerUtils;
import com.wangyd.dingding.module.work.adapter.WorkAdapter;
import com.wangyd.dingding.module.work.model.WorkItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author wangyd
 * @date 2018/12/20
 * @description
 */
public class WorkFragment extends BaseTxFragment {

    @BindView(R.id.rv_work)
    RecyclerView rvWork;

    private WorkAdapter mAdapter;

    @Override
    public int getLayoutByXml() {
        return R.layout.fragment_work;
    }

//    @Override
//    protected String getTitleString() {
//        return "济南";
//    }

    @Override
    public void initView() {
        mAdapter = new WorkAdapter(new ArrayList<>());
        RecyclerUtils.setGridRecycler(getActivity(), rvWork, 4, mAdapter);
    }

    @Override
    public void initData() {
        mAdapter.setNewData(getWorkItems());
    }

    private List<WorkItem> getWorkItems() {
        List<WorkItem> items = new ArrayList<>();
        items.add(new WorkItem(true, "工作管理"));
        items.add(new WorkItem(new ItemBean("打卡", R.mipmap.def_empty)));
        items.add(new WorkItem(new ItemBean("请假", R.mipmap.def_empty)));
        items.add(new WorkItem(new ItemBean("考勤统计", R.mipmap.def_empty)));
        items.add(new WorkItem(true, "管理员权限"));
        items.add(new WorkItem(new ItemBean("排版规则", R.mipmap.def_empty)));
        items.add(new WorkItem(new ItemBean("考勤审批", R.mipmap.def_empty)));
        items.add(new WorkItem(new ItemBean("人员调动", R.mipmap.def_empty)));

        return items;
    }

}
