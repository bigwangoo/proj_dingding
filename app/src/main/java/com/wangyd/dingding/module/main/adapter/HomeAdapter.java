package com.wangyd.dingding.module.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangyd.dingding.R;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/12/20
 * @description
 */
public class HomeAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public HomeAdapter(@Nullable List<String> data) {
        super(R.layout.item_main_home,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
