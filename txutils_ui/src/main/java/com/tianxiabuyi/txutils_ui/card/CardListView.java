package com.tianxiabuyi.txutils_ui.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

/**
 * Created by xjh1994 on 17/3/2.
 */
public class CardListView extends LinearLayout {

    protected TextView tvTitle;
    protected TextView tvMore;
    protected RecyclerView rvList;

    private String mTitleText;
    private String mMoreText;

    public CardListView(Context context) {
        this(context, null);
    }

    public CardListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tx_card_list, this);

        initView(context);

        obtainAttrs(context, attrs);

        setView();
    }

    private void initView(Context context) {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMore = (TextView) findViewById(R.id.tvMore);
        rvList = (RecyclerView) findViewById(R.id.rvList);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CardListItem);
        mTitleText = ta.getString(R.styleable.CardListItem_cardListTitle);
        mMoreText = ta.getString(R.styleable.CardListItem_cardListMoreText);

        ta.recycle();
    }

    private void setView() {
        if (!TextUtils.isEmpty(mTitleText)) {
            tvTitle.setText(mTitleText);
        }

        if (!TextUtils.isEmpty(mMoreText)) {
            tvMore.setText(mMoreText);
        }

        setOrientation(VERTICAL);

        int padding = DisplayUtils.dp2px(getContext(), 10);
        setPadding(padding, padding, padding, padding);

        setClickable(true);
        setBackgroundResource(R.drawable.tx_selector_bg_item_setting);
    }

    public RecyclerView getRecyclerView() {
        return rvList;
    }
}
