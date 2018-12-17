package com.wangyd.dingding.core.base;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.wangyd.dingding.Constant;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.wangyd.dingding.api.model.PageBean;
import com.tianxiabuyi.villagedoctor.common.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author wangyd
 * @date 2018/7/3
 * @description 列表界面封装，下拉刷新，上拉加载更多
 */
public abstract class BaseTxTitleListRefreshActivity<T, S> extends BaseTxTitleActivity
        implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    @BindView(R.id.loadingLayout)
    protected LoadingLayout loadingLayout;
    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv)
    protected RecyclerView rv;

    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    protected List<T> mData = new ArrayList<>();
    protected int currentPage = 1;                            // 列表分页
    protected int pageSize = Constant.DEFAULT_PAGE_SIZE;      // 每页数据

    @Override
    public int getViewByXml() {
        return R.layout.activity_base_list_loading_refresh;
    }

    @Override
    public void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        if (showItemDecoration()) {
            rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
        mAdapter = getAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMoreData();
            }
        }, rv);
        rv.setAdapter(mAdapter);

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                loadData();
            }
        });

        loadingLayout.setBindView(rv);
        loadingLayout.showLoading();
    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyResponseCallback<MyHttpResult<PageBean<S>>> mCallback =
                        new MyResponseCallback<MyHttpResult<PageBean<S>>>(showToast()) {
                            @Override
                            public void onSuccessResult(MyHttpResult<PageBean<S>> result) {
                                refreshLayout.finishRefresh();

                                PageBean<S> pageData = result.getData();
                                List<S> data = null;
                                if (pageData != null) {
                                    data = pageData.getList();
                                }

                                if (data != null && data.size() >= pageSize) {
                                    mAdapter.setEnableLoadMore(true);
                                    currentPage++;
                                } else {
                                    mAdapter.setEnableLoadMore(false);
                                }

                                mData.clear();
                                if (data != null && data.size() > 0) {
                                    loadingLayout.showSuccess();
                                    mData.addAll(convertData(data));
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    loadingLayout.showEmpty();
                                }
                                // 成功后
                                onLoadSuccess();
                            }

                            @Override
                            public void onError(TxException e) {
                                refreshLayout.finishRefresh();
                                loadingLayout.showError();
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }
                        };

                clearCall();
                loadNew(mCallback);
            }
        }, 800);
    }

    public void loadMoreData() {
        MyResponseCallback<MyHttpResult<PageBean<S>>> callback =
                new MyResponseCallback<MyHttpResult<PageBean<S>>>(showToast()) {
                    @Override
                    public void onSuccessResult(MyHttpResult<PageBean<S>> result) {
                        PageBean<S> pageData = result.getData();
                        List<S> data = null;
                        if (pageData != null) {
                            data = pageData.getList();
                        }

                        if (data != null && data.size() >= pageSize) {
                            currentPage++;
                            mAdapter.loadMoreComplete();
                        } else {
                            mAdapter.loadMoreEnd();
                        }

                        if (data != null && data.size() > 0) {
                            mData.addAll(convertData(data));
                            mAdapter.notifyDataSetChanged();
                        }
                        // 成功后
                        onLoadMoreSuccess();
                    }

                    @Override
                    public void onError(TxException e) {
                        mAdapter.loadMoreFail();
                    }
                };

        clearCall();
        loadMore(callback);
    }

    protected void onLoadSuccess() {

    }

    protected void onLoadMoreSuccess() {

    }

    /**
     * 新建Adapter
     */
    protected abstract BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    /**
     * 网络请求
     *
     * @param callback callback
     */
    protected abstract void loadNew(MyResponseCallback<MyHttpResult<PageBean<S>>> callback);

    /**
     * 网络请求
     *
     * @param callback callback
     */
    protected abstract void loadMore(MyResponseCallback<MyHttpResult<PageBean<S>>> callback);

    /**
     * 数据转换
     *
     * @param data data
     */
    protected abstract List<T> convertData(List<S> data);

    /**
     * 是否显示分割线 默认 true
     */
    protected boolean showItemDecoration() {
        return true;
    }

    /**
     * 是否显示提示 默认 false
     */
    protected boolean showToast() {
        return false;
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
