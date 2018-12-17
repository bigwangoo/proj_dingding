package com.tianxiabuyi.txutils_ui.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

/**
 * Created by xjh1994 on 17/3/2.
 */
public class CardFrameView extends LinearLayout {

    private Context context;

    protected TextView tvTitle;
    protected TextView tvMore;
    protected FrameLayout flContent;

    private String mTitleText;
    private String mMoreText;
    private boolean mMoreTextEnabled;

    public CardFrameView(Context context) {
        this(context, null);
    }

    public CardFrameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.tx_card_frame, this);

        initView();

        obtainAttrs(attrs);

        setView();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMore = (TextView) findViewById(R.id.tvMore);
        flContent = (FrameLayout) findViewById(R.id.flContent);
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CardFrameItem);
        mTitleText = ta.getString(R.styleable.CardFrameItem_cardFrameTitle);
        mMoreText = ta.getString(R.styleable.CardFrameItem_cardFrameMoreText);
        mMoreTextEnabled = ta.getBoolean(R.styleable.CardFrameItem_cardFrameMoreTextEnabled, true);

        ta.recycle();
    }

    private void setView() {
        if (!TextUtils.isEmpty(mTitleText)) {
            tvTitle.setText(mTitleText);
        }

        if (!TextUtils.isEmpty(mMoreText)) {
            tvMore.setText(mMoreText);
        }
        tvMore.setVisibility(mMoreTextEnabled ? VISIBLE : GONE);

        setOrientation(VERTICAL);

        int padding = DisplayUtils.dp2px(getContext(), 10);
        setPadding(padding, padding, padding, padding);

        setClickable(true);
        setBackgroundResource(R.drawable.tx_selector_bg_item_setting);
    }

    public void addContentView(View view) {
        flContent.removeAllViews();
        flContent.addView(view);
    }

    public void setText(Spanned text) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_expandable_text, flContent, false);
        ExpandableTextView expandableTextView = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
        expandableTextView.setText(text);
        addContentView(view);
    }

    public void setText(String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_expandable_text, flContent, false);
        ExpandableTextView expandableTextView = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
        expandableTextView.setText(text);
        addContentView(view);
    }

    public FrameLayout getContentView() {
        return flContent;
    }
}
