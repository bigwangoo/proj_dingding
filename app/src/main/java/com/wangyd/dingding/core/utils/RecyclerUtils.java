package com.wangyd.dingding.core.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianxiabuyi.txutils_ui.recyclerview.RecycleViewDivider;
import com.tianxiabuyi.villagedoctor.R;

/**
 * @author:jjj
 * @data:2018/6/11
 * @description:
 */

public class RecyclerUtils {
    public static void setLinearLayoutRecycler(Context context, RecyclerView recyclerview, BaseQuickAdapter mAdapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL,
                2, ContextCompat.getColor(context, R.color.line_c)));
        recyclerview.setAdapter(mAdapter);
    }

    public static void setLinearLayoutRecycler2(Context context, RecyclerView recyclerview, BaseQuickAdapter mAdapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setAdapter(mAdapter);
    }
}
