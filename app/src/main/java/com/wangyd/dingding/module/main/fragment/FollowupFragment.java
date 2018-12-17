package com.wangyd.dingding.module.main.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianxiabuyi.txutils.base.fragment.BaseLazyFragment;
import com.tianxiabuyi.txutils.base.recyclerview.TxDividerGridItemDecoration;
import com.wangyd.dingding.Constant;
import com.tianxiabuyi.villagedoctor.R;
import com.tianxiabuyi.villagedoctor.common.model.ItemBean;
import com.wangyd.dingding.core.utils.UserSpUtils;
import com.tianxiabuyi.villagedoctor.common.view.LoadingLayout;
import com.tianxiabuyi.villagedoctor.module.followup.activity.TownPickActivity;
import com.wangyd.dingding.module.main.adapter.FollowupAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 随访模块
 */
public class FollowupFragment extends BaseLazyFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvLeft)
    TextView tvLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ll_tip)
    LinearLayout llTip;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.rvFollowup)
    RecyclerView rvFollowup;

    private FollowupAdapter mAdapter;
    private List<ItemBean> mData = new ArrayList<>();

    @Override
    public int getLayoutByXml() {
        return R.layout.fragment_followup;
    }

    @Override
    public void initView() {
        ivBack.setVisibility(View.GONE);
        tvLeft.setVisibility(View.GONE);
        tvLeft.setText("区域选择");
        tvLeft.setOnClickListener(v -> {
        });
        tvTitle.setText(getString(R.string.followup_title));

        rvFollowup.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvFollowup.addItemDecoration(new TxDividerGridItemDecoration(getActivity()));
        mAdapter = new FollowupAdapter(mData);
        mAdapter.setOnItemClickListener(this);
        rvFollowup.setAdapter(mAdapter);

        loadingLayout.setBindView(rvFollowup);
        loadingLayout.showLoading();
    }

    @Override
    public void initData() {
        mData.add(new ItemBean(getString(R.string.followup_newborn), R.drawable.f_newburn, Constant.FOLLOWUP_TYPE_NEW_BORN));
        mData.add(new ItemBean(getString(R.string.followup_child), R.drawable.f_child, Constant.FOLLOWUP_TYPE_CHILD));
        mData.add(new ItemBean(getString(R.string.followup_pregnant), R.drawable.f_preganat, Constant.FOLLOWUP_TYPE_PREGNANT));
        mData.add(new ItemBean(getString(R.string.followup_old_medicine), R.drawable.f_old_man, Constant.FOLLOWUP_TYPE_MEDICINE_OLD));
        mData.add(new ItemBean(getString(R.string.followup_blood_sugar), R.drawable.f_tangniaobing, Constant.FOLLOWUP_TYPE_BLOOD_SUGAR));
        mData.add(new ItemBean(getString(R.string.followup_blood_pressure), R.drawable.f_gaoxueya, Constant.FOLLOWUP_TYPE_BLOOD_PRESSURE));
        mData.add(new ItemBean(getString(R.string.followup_mental), R.drawable.f_mental, Constant.FOLLOWUP_TYPE_MENTAL));
        mData.add(new ItemBean(getString(R.string.followup_tuberculosis), R.drawable.f_feijiehe, Constant.FOLLOWUP_TYPE_TUBERCULOSIS));

        mAdapter.notifyDataSetChanged();
        loadingLayout.showSuccess();
    }

    @OnClick(R.id.iv_close_tip)
    public void onViewClicked() {
        llTip.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ItemBean item = mAdapter.getData().get(position);
        UserSpUtils.getInstance().setFollowupType(item.getType());
        // 选择区域
        TownPickActivity.newInstance(getActivity(), item.getTitle());
    }
}
