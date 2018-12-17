package com.wangyd.dingding.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxiabuyi.villagedoctor.R;
import com.tianxiabuyi.villagedoctor.common.model.ItemBean;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/9/11
 * @description 随访分类
 */
public class FollowupAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {
    public FollowupAdapter(@Nullable List<ItemBean> data) {
        super(R.layout.item_followup_cate, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setImageResource(R.id.iv_icon, item.getResId());
    }
}
