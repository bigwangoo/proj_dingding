package com.tianxiabuyi.txutils.base.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.adapter.BaseQuickAdapter;
import com.tianxiabuyi.txutils.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh1994 on 2016/8/26.
 * 通用列表界面
 */

public abstract class BaseTxRecyclerViewActivity<T> extends BaseTxTitleActivity {

    public static final int LINEARLAYOUT = 0;
    public static final int GRIDLAYOUT = 1;

    protected int type = LINEARLAYOUT;
    private int spanCount = 2;

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLinearLayoutManager;
    protected GridLayoutManager mGridLayoutManager;

    protected BaseQuickAdapter<T> mAdapter;
    protected List<T> mData = new ArrayList<>();
    protected View mEmptyView;

    @Override
    public int getViewByXml() {
        return getBaseLayout();
    }

    private int getBaseLayout() {
        return R.layout.tx_activity_base_list_srl;
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        initType();
        if (type == LINEARLAYOUT) {
            mLinearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.addItemDecoration(new TxDividerItemDecoration(this, TxDividerItemDecoration.VERTICAL_LIST));
        } else {
            mGridLayoutManager = new GridLayoutManager(this, spanCount);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
        }

        mAdapter = new BaseQuickAdapter<T>(getItemLayoutId(), mData) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                setItemView(helper, item);
            }
        };
        mAdapter.setEmptyView(getEmptyView());
        mRecyclerView.setAdapter(mAdapter);

        onRefresh();
    }

    /**
     * 列表为空时显示的View
     * @return
     */
    protected View getEmptyView() {
        if (mEmptyView == null) {
            ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
            if (content == null) {
                return null;
            }

            mEmptyView = LayoutInflater.from(this).inflate(R.layout.tx_def_empty_view, content, false);
            mEmptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.clear();
                    onRefresh();
                }
            });
        }

        return mEmptyView;
    }

    /**
     * item布局文件
     * @return
     */
    protected abstract int getItemLayoutId();

    /**
     * 设置列表item布局
     * @param helper
     * @param item
     */
    protected abstract void setItemView(BaseViewHolder helper, T item);

    /**
     * 刷新数据
     */
    protected abstract void onRefresh();

    /**
     * 设置类型 LinearLayout还是GridLayout
     */
    protected void initType() {
        setType(LINEARLAYOUT, 2);
    }

    @Override
    public void initData() {

    }

    protected void setType(int type, int spanCount) {
        if (type != LINEARLAYOUT && type != GRIDLAYOUT) {
            throw new IllegalArgumentException("type must be one of LINEARLAYOUT and GRIDLAYOUT");
        }

        this.type = type;
        this.spanCount = spanCount;
    }
}
