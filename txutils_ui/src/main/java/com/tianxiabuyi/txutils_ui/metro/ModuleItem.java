package com.tianxiabuyi.txutils_ui.metro;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;

/**
 * Created by xjh1994 on 16/12/5.
 */

public class ModuleItem extends RelativeLayout {


    private TextView tvTitle;
    private TextView tvDesc;
    private ImageView ivPic;

    private String mTitle;
    private String mDesc;
    private int mPic;

    public ModuleItem(Context context) {
        this(context, null);
    }

    public ModuleItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ModuleItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tx_item_module, this);
        initView();

        obtainAttrs(context, attrs);

        tvTitle.setText(mTitle);
        tvDesc.setText(mDesc);
        ivPic.setImageResource(mPic);
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        ivPic = (ImageView) findViewById(R.id.ivPic);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ModuleItem);

        mTitle = ta.getString(R.styleable.ModuleItem_mItemTitle);
        mDesc = ta.getString(R.styleable.ModuleItem_mItemDesc);
        mPic = ta.getResourceId(R.styleable.ModuleItem_mItemPic, 0);

        ta.recycle();
    }
}
