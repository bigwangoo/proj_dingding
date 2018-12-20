package com.wangyd.dingding.core.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianxiabuyi.txutils_ui.recyclerview.RecycleViewDivider;
import com.wangyd.dingding.R;

/**
 * @author wangyd
 * @date 2018/12/20
 * @description RecyclerView工具
 */
public class RecyclerUtils {

    public static void setLinearRecycler(Context context, RecyclerView recyclerview, BaseQuickAdapter mAdapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setAdapter(mAdapter);
    }

    public static void setLinearRecyclerDiveder(Context context, RecyclerView recyclerview, BaseQuickAdapter mAdapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        recyclerview.setAdapter(mAdapter);
    }

    public static void setLinearRecyclerDivederHeight(Context context, RecyclerView recyclerview, BaseQuickAdapter mAdapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.addItemDecoration(new RecycleViewDivider(context,
                LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.line_c)));
        recyclerview.setAdapter(mAdapter);
    }

}
