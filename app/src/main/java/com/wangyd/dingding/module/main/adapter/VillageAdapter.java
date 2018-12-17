package com.wangyd.dingding.module.main.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.module.main.model.TownBean;
import com.wangyd.dingding.module.main.model.VillageBean;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/5/14
 * @description 村镇列表 （二级结构）
 */
public class VillageAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;   // 镇
    public static final int TYPE_ITEM = 1;      // 村

    private boolean expandable = true;          // 是否可以展开
    private boolean showCount = true;           // 是否显示人数

    public VillageAdapter(@Nullable List<MultiItemEntity> data, boolean expandable, boolean showCount) {
        super(data);
        this.expandable = expandable;
        this.showCount = showCount;
        addItemType(TYPE_LEVEL_0, R.layout.item_village_multi_title);
        addItemType(TYPE_ITEM, R.layout.item_village_multi_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (item.getItemType()) {
            // 镇
            case TYPE_LEVEL_0:
                TownBean town = (TownBean) item;
                helper.setText(R.id.tv_title, town.getName());

                if (expandable) {
                    if (town.isExpanded()) {
                        helper.setImageResource(R.id.iv_arrow, R.drawable.ic_arrow_down_black);
                    } else {
                        helper.setImageResource(R.id.iv_arrow, R.drawable.ic_arrow_next_black);
                    }
                    helper.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (town.isExpanded()) {
                                collapse(helper.getAdapterPosition());
                            } else {
                                expand(helper.getAdapterPosition());
                            }
                        }
                    });
                }
                break;
            // 村
            case TYPE_ITEM:
                VillageBean villageBean = (VillageBean) item;
                helper.setText(R.id.tvName, villageBean.getName());

                if (showCount) {
                    helper.setText(R.id.tvNumber, "（" + villageBean.getCount() + "）");
                }
                break;
            default:
                break;
        }
    }
}
