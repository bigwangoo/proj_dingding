package com.tianxiabuyi.txutils_ui.metro;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;

/**
 * Created by xjh1994 on 16/12/5.
 */

public class SubModuleItem extends LinearLayout {

    private TextView tvTitle;
    private ImageView ivPic;

    private String mTitle;
    private int mPic;

    public SubModuleItem(Context context) {
        this(context, null);
    }

    public SubModuleItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubModuleItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tx_item_sub_module, this);
        initView();

        obtainAttrs(context, attrs);

        tvTitle.setText(mTitle);
        ivPic.setImageResource(mPic);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivPic = (ImageView) findViewById(R.id.ivPic);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SubModuleItem);

        mTitle = ta.getString(R.styleable.SubModuleItem_mSubItemTitle);
        mPic = ta.getResourceId(R.styleable.ModuleItem_mItemPic, 0);

        ta.recycle();
    }
}
