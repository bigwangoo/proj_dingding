package com.tianxiabuyi.txutils_ui.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xjh1994 on 17/3/2.
 */

public class LinearSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public LinearSpaceItemDecoration() {
        this.space = 20;
    }

    public LinearSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.top = space;
    }

}
