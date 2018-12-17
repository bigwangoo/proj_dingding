package com.tianxiabuyi.txutils_ui.metro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

/**
 * Created by xjh1994 on 16/11/21.
 * 板块item
 */

public class MetroItem extends LinearLayout {

    ImageView ivIcon;
    TextView tvTitle;
    TextView tvIntro;

    public MetroItem(Context context) {
        this(context, null);
    }

    public MetroItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.tx_item_metro, this);
        initView();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MetroItem);

        String titleText = ta.getString(R.styleable.MetroItem_miTitleText);
        String introText = ta.getString(R.styleable.MetroItem_miIntroText);
        Drawable iconDrawable = ta.getDrawable(R.styleable.MetroItem_miIcon);
        Drawable bgDrawable = ta.getDrawable(R.styleable.MetroItem_miBackground);
        boolean clickable = ta.getBoolean(R.styleable.MetroItem_miClickable, true);
        int paddingDimen = (int) ta.getDimension(R.styleable.MetroItem_miPadding, DisplayUtils.dip2px(context, 10));

        ta.recycle();

        tvTitle.setText(titleText);
        tvIntro.setText(introText);
        ivIcon.setImageDrawable(iconDrawable);
        if (bgDrawable == null) {
            bgDrawable = getResources().getDrawable(R.drawable.tx_selector_bg_item_metro);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(bgDrawable);
        } else {
            setBackgroundDrawable(bgDrawable);
        }
        setClickable(clickable);
        setPadding(paddingDimen, paddingDimen, paddingDimen, paddingDimen);

    }

    private void initView() {
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvIntro = (TextView) findViewById(R.id.tvDesc);
    }
}
