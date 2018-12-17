package com.tianxiabuyi.txutils.adapter.entity;

import java.io.Serializable;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
@Deprecated
public abstract class MultiItemEntity implements Serializable {
    protected int itemType;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
