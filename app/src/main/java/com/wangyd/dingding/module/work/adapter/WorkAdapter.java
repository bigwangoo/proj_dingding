package com.wangyd.dingding.module.work.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangyd.dingding.R;
import com.wangyd.dingding.core.model.ItemBean;
import com.wangyd.dingding.module.work.model.WorkItem;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/12/20
 * @description 工作
 */
public class WorkAdapter extends BaseSectionQuickAdapter<WorkItem, BaseViewHolder> {

    public WorkAdapter(List<WorkItem> data) {
        super(R.layout.item_work_item, R.layout.item_work_title, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, WorkItem item) {
        helper.setText(R.id.tv_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkItem item) {
        ItemBean bean = item.t;
        helper.setText(R.id.tv_item, bean.getTitle())
                .setImageResource(R.id.iv_logo, bean.getDrawableId());
    }
}
