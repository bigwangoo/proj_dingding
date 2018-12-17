package com.tianxiabuyi.txutils_ui.title;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

/**
 * Created by xjh1994 on 16/11/23.
 * 详情页项目标题
 */

public class TitleBarView extends RelativeLayout {

    protected ImageView ivLeftIcon;
    protected TextView tvTitle;
    protected TextView tvMore;

    private int mLeftIconResId;
    private String mTitleText;
    private float mTitleTextSize;
    private String mMoreText;
    private float mMoreTextSize;
    private int mArrowIconResId;
    private boolean mArrowEnabled;

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tx_title_bar_view, this);

        initView(context);

        obtainAttrs(context, attrs);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);

        mLeftIconResId = ta.getResourceId(R.styleable.TitleBarView_tbvLeftIcon, 0);
        mTitleText = ta.getString(R.styleable.TitleBarView_tbvTitleText);
        mTitleTextSize = ta.getDimension(R.styleable.TitleBarView_tbvTitleTextSize, 18f);
        mArrowIconResId = ta.getResourceId(R.styleable.TitleBarView_tbvArrowIcon, 0);
        mMoreText = ta.getString(R.styleable.TitleBarView_tbvMoreText);
        mMoreTextSize = ta.getDimension(R.styleable.TitleBarView_tbvMoreTextSize, 16f);
        mArrowEnabled = ta.getBoolean(R.styleable.TitleBarView_tbvArrowEnabled, true);

        tvTitle.setTextSize(mTitleTextSize);
        tvMore.setTextSize(mMoreTextSize);

        if (mLeftIconResId != 0) {
            ivLeftIcon.setImageResource(mLeftIconResId);
        }

        if (!TextUtils.isEmpty(mTitleText)) {
            tvTitle.setText(mTitleText);
        }

        if (!TextUtils.isEmpty(mMoreText)) {
            tvMore.setText(mMoreText);
        }

        if (mArrowIconResId != 0) {
            tvMore.setCompoundDrawables(null, null, getResources().getDrawable(mArrowIconResId), null);
        }

        if (!mArrowEnabled) {
            tvMore.setCompoundDrawables(null, null, null, null);
        }
    }

    private void initView(Context context) {
        int padding = DisplayUtils.dp2px(context, 10f);
        setPadding(padding, padding, padding, padding);

        ivLeftIcon = (ImageView) findViewById(R.id.ivLeftIcon);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMore = (TextView) findViewById(R.id.tvMore);
    }

    public ImageView getIvLeftIcon() {
        return ivLeftIcon;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvMore() {
        return tvMore;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setOnMoreClickListener(OnClickListener listener) {
        tvMore.setOnClickListener(listener);
    }
}
