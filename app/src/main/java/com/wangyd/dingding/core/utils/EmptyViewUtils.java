package com.wangyd.dingding.core.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.villagedoctor.R;

/**
 * @author wangyd
 * @date 2018/5/16
 * @description description
 */
public class EmptyViewUtils {

    private static final String DEF_BG_COLOR = "#a2a2a2";
    private static final float DEF_TOP_WEIGHT = 1f;
    private static final float DEF_BOTTOM_WEIGHT = 1f;

    /**
     * 默认空界面
     */
    public static View getEmptyView(Context context, RecyclerView rv) {
        return getEmptyView(context, rv, new TxException("暂时没有相关数据哦~"));
    }

    /**
     * 默认空界面
     */
    public static View getEmptyView(Context context, RecyclerView rv, TxException e) {
        return getErrorView(context, rv, Color.parseColor(DEF_BG_COLOR), DEF_TOP_WEIGHT, DEF_BOTTOM_WEIGHT, e, null);
    }


    /**
     * 默认错误界面
     */
    public static View getErrorView(Context context, RecyclerView rv) {
        return getErrorView(context, rv, new TxException("系统开小差，请稍后\n点击屏幕重试"));
    }

    /**
     * 默认错误界面
     */
    public static View getErrorView(Context context, RecyclerView rv, TxException e) {
        return getErrorView(context, rv, e, null);
    }

    /**
     * 默认错误界面
     */
    public static View getErrorView(Context context, RecyclerView rv, TxException e, final onRetryListener listener) {
        return getErrorView(context, rv, Color.parseColor(DEF_BG_COLOR), DEF_TOP_WEIGHT, DEF_BOTTOM_WEIGHT, e, listener);
    }


    /**
     * 自定义背景颜色
     */
    public static View getErrorView(Context context, RecyclerView rv, int color, TxException e) {
        return getErrorView(context, rv, color, e, null);
    }

    /**
     * 自定义背景颜色
     */
    public static View getErrorView(Context context, RecyclerView rv, int color, TxException e, final onRetryListener listener) {
        return getErrorView(context, rv, color, DEF_TOP_WEIGHT, DEF_BOTTOM_WEIGHT, e, listener);
    }

    /**
     * 自定义布局比例
     */
    public static View getErrorView(Context context, RecyclerView rv, float weight1, float weight2, TxException e) {
        return getErrorView(context, rv, weight1, weight2, e, null);
    }

    /**
     * 自定义布局比例
     */
    public static View getErrorView(Context context, RecyclerView rv, float weight1, float weight2, TxException e, final onRetryListener listener) {
        return getErrorView(context, rv, Color.parseColor(DEF_BG_COLOR), weight1, weight2, e, listener);
    }

    /**
     * recyclerView
     *
     * @param rv RecyclerView
     */
    public static View getErrorView(Context context, RecyclerView rv, int color, float weight1, float weight2,
                                    TxException e, final onRetryListener listener) {
        ViewGroup group = null;
        if (rv != null) {
            group = (ViewGroup) rv.getParent();
        }
        return getCommonErrorView(context, group, color, weight1, weight2, e, listener);
    }

    /**
     * 获取错误界面
     *
     * @param context  Context
     * @param e        异常TxException
     * @param parent   ViewGroup
     * @param color    字体颜色
     * @param weight1  上部分比例
     * @param weight2  下部分比例
     * @param listener 点击监听
     * @return View
     */
    public static View getCommonErrorView(Context context, ViewGroup parent,  int color, float weight1, float weight2,
                                          TxException e, final onRetryListener listener) {
        if (context == null || parent == null || e == null) {
            return null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.def_empty_view, parent, false);
        View topView = view.findViewById(R.id.topView);
        View bottomView = view.findViewById(R.id.bottomView);
        topView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight1));
        bottomView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight2));
        TextView tvError = view.findViewById(R.id.tv_error_msg);
        tvError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvError.setTextColor(color);
        tvError.setText(getErrorString(e));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRetryClick();
                }
            }
        });

        return view;
    }

    private static String getErrorString(TxException e) {
        return e.getDetailMessage();
    }

    public interface onRetryListener {
        void onRetryClick();
    }
}
