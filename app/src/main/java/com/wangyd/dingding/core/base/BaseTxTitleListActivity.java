package com.wangyd.dingding.core.base;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.api.callback.MyResponseCallback;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.tianxiabuyi.villagedoctor.common.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author wangyd
 * @date 2018/7/3
 * @description 列表界面封装
 */
public abstract class BaseTxTitleListActivity<T, S> extends BaseTxTitleActivity
        implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    @BindView(R.id.rv)
    protected RecyclerView rv;
    @BindView(R.id.loadingLayout)
    protected LoadingLayout loadingLayout;

    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    protected List<T> mData = new ArrayList<>();

    @Override
    public int getViewByXml() {
        return R.layout.activity_base_list_loading;
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
        rv.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        loadingLayout.setBindView(rv);
        loadingLayout.showLoading();
        loadData();
    }

    private void loadData() {
        MyResponseCallback<MyHttpResult<S>> mCallback = new MyResponseCallback<MyHttpResult<S>>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccessResult(MyHttpResult<S> result) {
                mData.clear();
                if (result == null) {
                    loadingLayout.showEmpty();
                } else {
                    S data = result.getData();
                    if (data instanceof List && ((List) data).size() > 0) {
                        loadingLayout.showSuccess();
                        mData.addAll(convertData(data));
                    } else {
                        loadingLayout.showEmpty();
                    }
                }
                mAdapter.notifyDataSetChanged();
                // 成功后
                onLoadSuccess();
            }

            @Override
            public void onError(TxException e) {
                loadingLayout.showError();
                mData.clear();
                mAdapter.notifyDataSetChanged();
            }
        };

        loadNew(mCallback);
    }

    protected void onLoadSuccess() {

    }

    /**
     * 新建Adapter
     *
     * @return
     */
    protected abstract BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    /**
     * 网络请求
     *
     * @param callback
     */
    protected abstract void loadNew(MyResponseCallback<MyHttpResult<S>> callback);

    /**
     * 数据转换
     *
     * @param data
     * @return
     */
    protected abstract List<T> convertData(S data);

    /**
     * 是否显示分割线 默认 true
     *
     * @return
     */
    protected boolean showItemDecoration() {
        return true;
    }

    /**
     * 是否显示提示 默认 false
     *
     * @return
     */
    protected boolean showToast() {
        return false;
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
