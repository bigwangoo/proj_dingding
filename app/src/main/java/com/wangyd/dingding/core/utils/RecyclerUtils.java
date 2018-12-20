package com.wangyd.dingding.core.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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

    /**
     * linear
     */
    public static void setLinearRecycler(Context context, RecyclerView recyclerView, BaseQuickAdapter mAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
    }

    public static void setLinearRecyclerDivider(Context context, RecyclerView recyclerView, BaseQuickAdapter mAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(mAdapter);
    }

    public static void setLinearRecyclerDividerHeight(Context context, RecyclerView recyclerView, BaseQuickAdapter mAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new RecycleViewDivider(context,
                LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.line_c)));
        recyclerView.setAdapter(mAdapter);
    }


    /**
     * grid
     */
    public static void setGridRecycler(Context context, RecyclerView recyclerView, int spanCount, BaseQuickAdapter mAdapter) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        recyclerView.setAdapter(mAdapter);
    }

    public static void setGridRecyclerDivider(Context context, RecyclerView recyclerView, int spanCount, BaseQuickAdapter mAdapter) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(mAdapter);
    }

    public static void setGridRecyclerDividerHeight(Context context, RecyclerView recyclerView, int spanCount, BaseQuickAdapter mAdapter) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        recyclerView.addItemDecoration(new RecycleViewDivider(context,
                LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.line_c)));
        recyclerView.setAdapter(mAdapter);
    }

}
