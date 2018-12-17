package com.wangyd.dingding.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.module.main.model.TownBean;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/5/15
 * @description 团队任务
 */
public class ScheduleAdapter extends BaseQuickAdapter<TownBean, BaseViewHolder> {

    public ScheduleAdapter(@Nullable List<TownBean> data) {
        super(R.layout.item_schedule, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TownBean item) {
        helper.setText(R.id.tvName, item.getName())
                .setText(R.id.tvTip, "工作开始前请及时签到");
    }
}
