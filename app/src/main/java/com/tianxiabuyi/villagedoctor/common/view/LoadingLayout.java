package com.tianxiabuyi.villagedoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianxiabuyi.villagedoctor.R;


/**
 * @author wangyd
 * @date 2018/5/15
 * @description 加载状态布局 显示状态：加载中 成功 空 错误 无网络
 */
public class LoadingLayout extends FrameLayout {

    /**
     * 绑定显示数据的控件
     */
    private View bindView;

    /**
     * 数据加载时的布局
     */
    private View loadingView;
    private ImageView loadingImg;
    private TextView loadingTv;
    private int loadingImgId;
    private String loadingString;
    /**
     * 空数据时的布局
     */
    private View emptyView;
    private ImageView emptyImg;
    private TextView emptyTv;
    private int emptyImgId;
    private String emptyString;
    /**
     * 错误时的布局
     */
    private View errorView;
    private ImageView errorImg;
    private TextView errorTv;
    private int errorImgId;
    private String errorString;
    /**
     * 无网络时的布局
     */
    private View netView;
    private ImageView netImg;
    private TextView netTv;
    private int netImgId;
    private String netString;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        //数据加载
        int loadingLayoutId = array.getResourceId(R.styleable.LoadingLayout_ldl_loading_layout, R.layout.def_layout_loading);
        loadingImgId = array.getResourceId(R.styleable.LoadingLayout_ldl_loading_img, 0);
        loadingString = array.getString(R.styleable.LoadingLayout_ldl_loading_tv);
        loadingView = View.inflate(getContext(), loadingLayoutId, null);
        if (loadingLayoutId == R.layout.def_layout_loading) {
            //如果是默认布局
            loadingImg = (ImageView) loadingView.findViewById(R.id.iv_loading);
            loadingTv = (TextView) loadingView.findViewById(R.id.tv_loading);
        }
        //数据为空
        int emptyLayoutId = array.getResourceId(R.styleable.LoadingLayout_ldl_empty_layout, R.layout.def_layout_empty);
        emptyImgId = array.getResourceId(R.styleable.LoadingLayout_ldl_empty_img, 0);
        emptyString = array.getString(R.styleable.LoadingLayout_ldl_empty_tv);
        emptyView = View.inflate(getContext(), emptyLayoutId, null);
        if (emptyLayoutId == R.layout.def_layout_empty) {
            emptyImg = (ImageView) emptyView.findViewById(R.id.iv_empty);
            emptyTv = (TextView) emptyView.findViewById(R.id.tv_empty);
        }
        //数据错误
        int errorLayoutId = array.getResourceId(R.styleable.LoadingLayout_ldl_error_layout, R.layout.def_layout_error);
        errorImgId = array.getResourceId(R.styleable.LoadingLayout_ldl_error_img, 0);
        errorString = array.getString(R.styleable.LoadingLayout_ldl_error_tv);
        errorView = View.inflate(getContext(), errorLayoutId, null);
        if (errorLayoutId == R.layout.def_layout_error) {
            errorImg = (ImageView) errorView.findViewById(R.id.iv_error);
            errorTv = (TextView) errorView.findViewById(R.id.tv_error);
        }
        //无网络
        int netLayoutId = array.getResourceId(R.styleable.LoadingLayout_ldl_net_layout, R.layout.def_layout_network);
        netImgId = array.getResourceId(R.styleable.LoadingLayout_ldl_net_img, 0);
        netString = array.getString(R.styleable.LoadingLayout_ldl_net_tv);
        netView = View.inflate(getContext(), netLayoutId, null);
        if (loadingLayoutId == R.layout.def_layout_network) {
            netImg = (ImageView) netView.findViewById(R.id.iv_net);
            netTv = (TextView) netView.findViewById(R.id.tv_net);
        }
        array.recycle();

        setAllGone();

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        addView(netView, params);
        addView(errorView, params);
        addView(emptyView, params);
        addView(loadingView, params);
    }

    /**
     * 设置隐藏全部
     */
    private void setAllGone() {
        netView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        errorView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
    }

    /**
     * 设置数据View
     */
    public void setBindView(View view) {
        this.bindView = view;
    }

    /**
     * 显示加载布局（默认样式）
     */
    public void showLoading() {
        showLoading(null, null);
    }

    /**
     * 设置文字
     */
    public void showLoading(String s) {
        showLoading(s, null);
    }

    /**
     * 设置图片
     */
    public void showLoading(SetImgCallBack callBack) {
        showLoading(null, callBack);
    }

    /**
     * @param s        提示文字
     * @param callBack 设置图片回调接口
     */
    public void showLoading(String s, SetImgCallBack callBack) {
        if (bindView != null) {
            bindView.setVisibility(GONE);
        }

        if (loadingTv != null) {
            if (!TextUtils.isEmpty(s)) {
                loadingTv.setText(s);
            } else if (!TextUtils.isEmpty(loadingString)) {
                loadingTv.setText(loadingString);
            }
        }
        if (loadingImg != null) {
            if (loadingImgId != 0) {
                loadingImg.setImageResource(loadingImgId);
                loadingImg.setVisibility(VISIBLE);
            }
            if (callBack != null) {
                callBack.setImg(loadingImg);
                loadingImg.setVisibility(VISIBLE);
            }
        }

        setAllGone();
        loadingView.setVisibility(VISIBLE);
    }

    /**
     * 显示空布局
     */
    public void showEmpty() {
        showEmpty(null, null);
    }

    public void showEmpty(String s) {
        showEmpty(s, null);
    }

    public void showEmpty(SetImgCallBack callBack) {
        showEmpty(null, callBack);
    }

    public void showEmpty(String s, SetImgCallBack callBack) {
        if (bindView != null) {
            bindView.setVisibility(GONE);
        }

        if (emptyTv != null) {
            if (!TextUtils.isEmpty(s)) {
                emptyTv.setText(s);
            } else if (!TextUtils.isEmpty(emptyString)) {
                emptyTv.setText(emptyString);
            }
        }
        if (emptyImg != null) {
            if (emptyImgId != 0) {
                emptyImg.setImageResource(emptyImgId);
                emptyImg.setVisibility(VISIBLE);
            }
            if (callBack != null) {
                callBack.setImg(emptyImg);
                emptyImg.setVisibility(VISIBLE);
            }
        }

        setAllGone();
        emptyView.setVisibility(VISIBLE);
    }

    /**
     * 显示错误布局
     */
    public void showError() {
        showError(null, null);
    }

    public void showError(String s) {
        showError(s, null);
    }

    public void showError(SetImgCallBack callBack) {
        showError(null, callBack);
    }

    public void showError(String s, SetImgCallBack callBack) {
        if (bindView != null) {
            bindView.setVisibility(GONE);
        }

        if (errorTv != null) {
            if (!TextUtils.isEmpty(s)) {
                errorTv.setText(s);
            } else if (!TextUtils.isEmpty(errorString)) {
                errorTv.setText(errorString);
            }
        }

        if (errorImg != null) {
            if (errorImgId != 0) {
                errorImg.setImageResource(errorImgId);
                errorImg.setVisibility(VISIBLE);
            }
            if (callBack != null) {
                callBack.setImg(errorImg);
                errorImg.setVisibility(VISIBLE);
            }
        }

        setAllGone();
        errorView.setVisibility(VISIBLE);
    }

    /**
     * 显示无网络布局
     */
    public void showNetwork() {
        showNetwork(null, null);
    }

    public void showNetwork(String s) {
        showNetwork(s, null);
    }

    public void showNetwork(SetImgCallBack callBack) {
        showNetwork(null, callBack);
    }

    public void showNetwork(String s, SetImgCallBack callBack) {
        if (bindView != null) {
            bindView.setVisibility(GONE);
        }

        if (netTv != null) {
            if (!TextUtils.isEmpty(s)) {
                netTv.setText(s);
            } else if (!TextUtils.isEmpty(netString)) {
                netTv.setText(netString);
            }
        }

        if (netImg != null) {
            if (netImgId != 0) {
                netImg.setImageResource(netImgId);
                netImg.setVisibility(VISIBLE);
            }
            if (callBack != null) {
                callBack.setImg(netImg);
                netImg.setVisibility(VISIBLE);
            }
        }

        setAllGone();
        netView.setVisibility(VISIBLE);
    }

    /**
     * 显示成功布局
     */
    public void showSuccess() {
        if (bindView != null) {
            bindView.setVisibility(View.VISIBLE);
        }
        setAllGone();
    }

    /**
     * 设置点击重试
     */
    public void setOnRetryClickListener(OnClickListener listener) {
        emptyView.setOnClickListener(listener);
        errorView.setOnClickListener(listener);
    }


    /**
     * 图片回调
     */
    public interface SetImgCallBack {
        void setImg(@Nullable ImageView img);
    }
}