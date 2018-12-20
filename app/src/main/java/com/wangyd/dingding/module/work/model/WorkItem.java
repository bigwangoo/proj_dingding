package com.wangyd.dingding.module.work.model;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.wangyd.dingding.core.model.ItemBean;

/**
 * @author wangyd
 * @date 2018/12/20
 * @description
 */
public class WorkItem extends SectionEntity<ItemBean> {

    public WorkItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public WorkItem(ItemBean itemBean) {
        super(itemBean);
    }
}
