package com.tianxiabuyi.txutils_ui.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

/**
 * @author xjh1994
 * @date 16/11/23
 * @description 设置子项 EditText
 */
public class SettingItemView extends LinearLayout {

    private ImageView ivLeftIcon;
    private TextView tvLeftTitle;
    private TextView tvLeftDesc;
    private EditText etContent;
    private ImageView ivRightIcon;
    private TextView tvRightTitle;
    private TextView tvRightDesc;
    private ImageView ivArrowForward;

    private int mLeftWidth;
    private int mLeftHeight;
    private int mLeftIconResId;
    private int mLeftIconWidth;
    private int mLeftIconHeight;
    private String mLeftTitle;
    private float mLeftTitleSize;
    private int mLeftTitleColor;
    private String mLeftDesc;
    private float mLeftDescSize;
    private int mLeftDescColor;

    private boolean mContentEnabled;
    private String mContentHintText;
    private int mContentHintTextColor;
    private String mContentText;
    private int mContentTextColor;
    private int mContentMinHeight;

    private int mRightIconResId;
    private int mRightIconWidth;
    private int mRightIconHeight;
    private String mRightTitle;
    private float mRightTitleSize;
    private int mRightTitleColor;
    private String mRightDesc;
    private float mRightDescSize;
    private int mRightDescColor;
    private boolean mArrowEnabled;
    private int mArrowIconResId;
    private int mBackgroundResId;
    private boolean mTopLineEnabled;
    private boolean mBottomLineEnabled;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(context, attrs);
        init(context, attrs);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);

        mLeftWidth = (int) ta.getDimension(R.styleable.SettingItemView_sivLeftWidth, 0);
        mLeftHeight = (int) ta.getDimension(R.styleable.SettingItemView_sivLeftHeight, 0);
        mLeftIconResId = ta.getResourceId(R.styleable.SettingItemView_sivLeftIcon, 0);
        mLeftIconWidth = (int) ta.getDimension(R.styleable.SettingItemView_sivLeftIconWidth, DisplayUtils.dp2px(context, 40));
        mLeftIconHeight = (int) ta.getDimension(R.styleable.SettingItemView_sivLeftIconHeight, DisplayUtils.dp2px(context, 40));
        mLeftTitle = ta.getString(R.styleable.SettingItemView_sivLeftText);
        mLeftDesc = ta.getString(R.styleable.SettingItemView_sivLeftSubText);
        mLeftTitleSize = ta.getDimension(R.styleable.SettingItemView_sivLeftTextSize, DisplayUtils.sp2px(context, 14));
        mLeftTitleColor = ta.getColor(R.styleable.SettingItemView_sivLeftTextColor, 0);
        mLeftDescSize = ta.getDimension(R.styleable.SettingItemView_sivLeftSubTextSize, DisplayUtils.sp2px(context, 12));
        mLeftDescColor = ta.getColor(R.styleable.SettingItemView_sivLeftSubTextColor, 0);

        mContentEnabled = ta.getBoolean(R.styleable.SettingItemView_sivContentEnabled, false);
        mContentHintText = ta.getString(R.styleable.SettingItemView_sivContentHintText);
        mContentHintTextColor = ta.getColor(R.styleable.SettingItemView_sivContentHintTextColor, 0);
        mContentText = ta.getString(R.styleable.SettingItemView_sivContentText);
        mContentTextColor = ta.getColor(R.styleable.SettingItemView_sivContentTextColor, 0);
        mContentMinHeight = (int) ta.getDimension(R.styleable.SettingItemView_sivContentMinHeight, DisplayUtils.dp2px(context, 0));

        mRightIconResId = ta.getResourceId(R.styleable.SettingItemView_sivRightIcon, 0);
        mRightIconWidth = (int) ta.getDimension(R.styleable.SettingItemView_sivRightIconWidth, DisplayUtils.dp2px(context, 40));
        mRightIconHeight = (int) ta.getDimension(R.styleable.SettingItemView_sivRightIconHeight, DisplayUtils.dp2px(context, 40));
        mRightTitle = ta.getString(R.styleable.SettingItemView_sivRightText);
        mRightDesc = ta.getString(R.styleable.SettingItemView_sivRightSubText);
        mRightTitleSize = ta.getDimension(R.styleable.SettingItemView_sivRightTextSize, DisplayUtils.sp2px(context, 14));
        mRightTitleColor = ta.getColor(R.styleable.SettingItemView_sivRightTextColor, 0);
        mRightDescSize = ta.getDimension(R.styleable.SettingItemView_sivRightSubTextSize, DisplayUtils.sp2px(context, 12));
        mRightDescColor = ta.getColor(R.styleable.SettingItemView_sivRightSubTextColor, 0);

        mArrowEnabled = ta.getBoolean(R.styleable.SettingItemView_sivArrowEnabled, true);
        mArrowIconResId = ta.getResourceId(R.styleable.SettingItemView_sivArrowIcon, 0);

        mBackgroundResId = ta.getResourceId(R.styleable.SettingItemView_sivBackground, 0);
        mTopLineEnabled = ta.getBoolean(R.styleable.SettingItemView_sivTopLineEnabled, false);
        mBottomLineEnabled = ta.getBoolean(R.styleable.SettingItemView_sivBottomLineEnabled, false);
        ta.recycle();
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        setClickable(true);
        if (mBackgroundResId == 0) {
            setBg(ContextCompat.getDrawable(context, R.drawable.tx_selector_bg_item_setting));
        } else {
            setBg(ContextCompat.getDrawable(context, mBackgroundResId));
        }

        // 布局
        LayoutInflater.from(context).inflate(R.layout.tx_setting_item_view, this);
        View topLine = findViewById(R.id.viewLineTop);
        View bottomLine = findViewById(R.id.viewLineBottom);
        LinearLayout llContainer = (LinearLayout) findViewById(R.id.llContainer);
        LinearLayout llLeftContainer = (LinearLayout) findViewById(R.id.llLeftContainer);
        ivLeftIcon = (ImageView) findViewById(R.id.ivLeftIcon);
        tvLeftTitle = (TextView) findViewById(R.id.tvLeftTitle);
        tvLeftDesc = (TextView) findViewById(R.id.tvLeftDesc);
        ivRightIcon = (ImageView) findViewById(R.id.ivRightIcon);
        tvRightTitle = (TextView) findViewById(R.id.tvRightTitle);
        tvRightDesc = (TextView) findViewById(R.id.tvRightDesc);
        ivArrowForward = (ImageView) findViewById(R.id.ivArrowForward);
        etContent = (EditText) findViewById(R.id.etContent);
        etContent.setEnabled(mContentEnabled);

        // 内容区最小高度
        if (mContentMinHeight != 0) {
            llContainer.setMinimumHeight(mContentMinHeight);
        }
        // 左侧文本宽度
        int w = mLeftWidth == 0 ? LayoutParams.WRAP_CONTENT : mLeftWidth;
        int h = mLeftHeight == 0 ? LayoutParams.WRAP_CONTENT : mLeftHeight;
        llLeftContainer.setLayoutParams(new LinearLayout.LayoutParams(w, h));
        // 左侧图标大小
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mLeftIconWidth, mLeftIconHeight);
        lp.setMargins(0, 0, DisplayUtils.dip2px(context, 10), 0);
        ivLeftIcon.setLayoutParams(lp);
        // 右侧图标大小
        ivRightIcon.setLayoutParams(new LinearLayout.LayoutParams(mRightIconWidth, mRightIconHeight));

        if (mLeftIconResId != 0) {
            ivLeftIcon.setImageResource(mLeftIconResId);
            ivLeftIcon.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mLeftTitle)) {
            tvLeftTitle.setText(mLeftTitle);
            tvLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTitleSize);
            tvLeftTitle.setVisibility(VISIBLE);
        }
        if (mLeftTitleColor != 0) {
            tvLeftTitle.setTextColor(mLeftTitleColor);
        }
        if (!TextUtils.isEmpty(mLeftDesc)) {
            tvLeftDesc.setText(mLeftDesc);
            tvLeftDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftDescSize);
            tvLeftDesc.setVisibility(VISIBLE);
        }
        if (mLeftDescColor != 0) {
            tvLeftDesc.setTextColor(mLeftDescColor);
        }

        if (!TextUtils.isEmpty(mContentHintText)) {
            etContent.setHint(mContentHintText);
            etContent.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mContentText)) {
            etContent.setText(mContentText);
            etContent.setVisibility(VISIBLE);
        }
        if (mContentHintTextColor != 0) {
            etContent.setHintTextColor(mContentHintTextColor);
            etContent.setVisibility(VISIBLE);
        }
        if (mContentTextColor != 0) {
            etContent.setTextColor(mContentTextColor);
            etContent.setVisibility(VISIBLE);
        }

        if (mRightIconResId != 0) {
            ivRightIcon.setImageResource(mRightIconResId);
            ivRightIcon.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mRightTitle)) {
            tvRightTitle.setText(mRightTitle);
            tvRightTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTitleSize);
            tvRightTitle.setVisibility(VISIBLE);
        }
        if (mRightTitleColor != 0) {
            tvRightTitle.setTextColor(mRightTitleColor);
        }
        if (!TextUtils.isEmpty(mRightDesc)) {
            tvRightDesc.setText(mRightDesc);
            tvRightDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightDescSize);
            tvRightDesc.setVisibility(VISIBLE);
        }
        if (mRightDescColor != 0) {
            tvRightDesc.setTextColor(mRightDescColor);
        }
        if (mArrowIconResId != 0) {
            ivArrowForward.setImageResource(mArrowIconResId);
            ivArrowForward.setVisibility(VISIBLE);
        }
        ivArrowForward.setVisibility(mArrowEnabled ? VISIBLE : GONE);
        topLine.setVisibility(mTopLineEnabled ? VISIBLE : GONE);
        bottomLine.setVisibility(mBottomLineEnabled ? VISIBLE : GONE);
    }

    private void setBg(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 如果EditText不可用，则拦截触摸事件
        if (!mContentEnabled) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public ImageView getIvLeftIcon() {
        return ivLeftIcon;
    }

    public TextView getTvLeftTitle() {
        return tvLeftTitle;
    }

    public TextView getTvLeftDesc() {
        return tvLeftDesc;
    }

    public ImageView getIvRightIcon() {
        return ivRightIcon;
    }

    public TextView getTvRightTitle() {
        return tvRightTitle;
    }

    public TextView getTvRightDesc() {
        return tvRightDesc;
    }

    public ImageView getIvArrowForward() {
        return ivArrowForward;
    }

    public EditText getEtContent() {
        return etContent;
    }

    public EditText getContentView() {
        return etContent;
    }


    public void setContentViewClickNotEdit() {
        mContentEnabled = true;
        etContent.setFocusable(false);
        etContent.setCursorVisible(false);
        etContent.setFocusableInTouchMode(false);
    }


    public void setArrowEnabled(boolean isEnabled) {
        this.mArrowEnabled = isEnabled;
        ivArrowForward.setVisibility(mArrowEnabled ? VISIBLE : GONE);
    }

    public void setLeftText(String text) {
        tvLeftTitle.setText(text);
        tvLeftTitle.setVisibility(VISIBLE);
    }

    public void setRightText(String text) {
        tvRightTitle.setText(text);
        tvRightTitle.setVisibility(VISIBLE);
    }

    @Deprecated
    public void setContent(String text) {
        etContent.setText(text);
        etContent.setVisibility(VISIBLE);
    }

    public void setContentText(String text) {
        etContent.setText(text);
        etContent.setVisibility(VISIBLE);
    }


    public String getLeftText() {
        return tvLeftTitle.getText().toString().trim();
    }

    public String getRightText() {
        return tvRightTitle.getText().toString().trim();
    }

    @Deprecated
    public String getContent() {
        return etContent.getText().toString().trim();
    }

    public String getContentText() {
        return etContent.getText().toString().trim();
    }

}
