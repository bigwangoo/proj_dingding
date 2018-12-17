package com.tianxiabuyi.txutils_ui.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.edittext.CleanableEditText;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

/**
 * TODO 偷懒用于随访录入，导致布局极其臃肿！！！看到请重写新录入布局，移除当前仅保留 SettingItemView
 *
 * @author xjh1994
 * @date 16/11/23
 * @description 设置子项 CleanableEditText
 */
public class SettingEditView extends LinearLayout {

    private ImageView ivLeftIcon;
    private TextView tvLeftTitle;
    private TextView tvLeftDesc;
    private TextView tvRightTitle;
    private CleanableEditText cetContent;

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

    private String mContentHintText;
    private int mContentHintTextColor;
    private String mContentText;
    private int mContentTextColor;
    private int mContentInputType;
    private int mContentTextLength;
    private int mContentMinHeight;
    private boolean mContentSingleLine;
    private boolean mContentEnable;
    private String mRightTitle;
    private float mRightTitleSize;
    private int mRightTitleColor;

    private int mBackgroundResId;
    private boolean mTopLineEnabled;
    private boolean mBottomLineEnabled;

    public SettingEditView(Context context) {
        this(context, null);
    }

    public SettingEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(context, attrs);
        init(context, attrs);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingEditView);

        mLeftWidth = (int) ta.getDimension(R.styleable.SettingEditView_sevLeftWidth, 0);
        mLeftHeight = (int) ta.getDimension(R.styleable.SettingEditView_sevLeftHeight, 0);
        mLeftIconResId = ta.getResourceId(R.styleable.SettingEditView_sevLeftIcon, 0);
        mLeftIconWidth = (int) ta.getDimension(R.styleable.SettingEditView_sevLeftIconWidth, DisplayUtils.dp2px(context, 40));
        mLeftIconHeight = (int) ta.getDimension(R.styleable.SettingEditView_sevLeftIconHeight, DisplayUtils.dp2px(context, 40));
        mLeftTitle = ta.getString(R.styleable.SettingEditView_sevLeftText);
        mLeftDesc = ta.getString(R.styleable.SettingEditView_sevLeftSubText);
        mLeftTitleSize = ta.getDimension(R.styleable.SettingEditView_sevLeftTextSize, DisplayUtils.sp2px(context, 14));
        mLeftTitleColor = ta.getColor(R.styleable.SettingEditView_sevLeftTextColor, 0);
        mLeftDescSize = ta.getDimension(R.styleable.SettingEditView_sevLeftSubTextSize, DisplayUtils.sp2px(context, 12));
        mLeftDescColor = ta.getColor(R.styleable.SettingEditView_sevLeftSubTextColor, 0);

        mContentText = ta.getString(R.styleable.SettingEditView_sevContentText);
        mContentTextColor = ta.getColor(R.styleable.SettingEditView_sevContentTextColor, 0);
        mContentHintText = ta.getString(R.styleable.SettingEditView_sevContentHintText);
        mContentHintTextColor = ta.getColor(R.styleable.SettingEditView_sevContentHintTextColor, 0);
        mContentInputType = ta.getInt(R.styleable.SettingEditView_sevContentInputType, 0);
        mContentTextLength = ta.getInt(R.styleable.SettingEditView_sevContentTextLength, 0);
        mContentMinHeight = (int) ta.getDimension(R.styleable.SettingEditView_sevContentMinHeight, DisplayUtils.dp2px(context, 0));
        mContentSingleLine = ta.getBoolean(R.styleable.SettingEditView_sevContentSingleLine, true);
        mContentEnable = ta.getBoolean(R.styleable.SettingEditView_sevContentEnable, true);

        mRightTitle = ta.getString(R.styleable.SettingEditView_sevRightText);
        mRightTitleSize = ta.getDimension(R.styleable.SettingEditView_sevRightTextSize, DisplayUtils.sp2px(context, 14));
        mRightTitleColor = ta.getColor(R.styleable.SettingEditView_sevRightTextColor, 0);

        mTopLineEnabled = ta.getBoolean(R.styleable.SettingEditView_sevTopLineEnabled, false);
        mBottomLineEnabled = ta.getBoolean(R.styleable.SettingEditView_sevBottomLineEnabled, false);

        mBackgroundResId = ta.getResourceId(R.styleable.SettingEditView_sevBackground, 0);
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
        LayoutInflater.from(context).inflate(R.layout.tx_setting_input_view, this);
        LinearLayout llContainer = (LinearLayout) findViewById(R.id.llContainer);
        View topLine = findViewById(R.id.viewLineTop);
        View bottomLine = findViewById(R.id.viewLineBottom);
        LinearLayout llLeftContainer = (LinearLayout) findViewById(R.id.llLeftContainer);
        ivLeftIcon = (ImageView) findViewById(R.id.ivLeftIcon);
        tvLeftTitle = (TextView) findViewById(R.id.tvLeftTitle);
        tvLeftDesc = (TextView) findViewById(R.id.tvLeftDesc);
        cetContent = (CleanableEditText) findViewById(R.id.cetContent);
        tvRightTitle = (TextView) findViewById(R.id.tvRightTitle);
        cetContent.setEnabled(mContentEnable);

        // 左侧宽度
        int w = mLeftWidth == 0 ? LayoutParams.WRAP_CONTENT : mLeftWidth;
        int h = mLeftHeight == 0 ? LayoutParams.WRAP_CONTENT : mLeftHeight;
        llLeftContainer.setLayoutParams(new LinearLayout.LayoutParams(w, h));
        // 左侧图标大小
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mLeftIconWidth, mLeftIconHeight);
        lp.setMargins(0, 0, DisplayUtils.dip2px(context, 10), 0);
        ivLeftIcon.setLayoutParams(lp);
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
            cetContent.setHint(mContentHintText);
            cetContent.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mContentText)) {
            cetContent.setText(mContentText);
            cetContent.setVisibility(VISIBLE);
        }
        if (mContentHintTextColor != 0) {
            cetContent.setHintTextColor(mContentHintTextColor);
            cetContent.setVisibility(VISIBLE);
        }
        if (mContentTextColor != 0) {
            cetContent.setTextColor(mContentTextColor);
            cetContent.setVisibility(VISIBLE);
        }
        // 输入类型
        if (mContentInputType == 0) {
            cetContent.setInputType(EditorInfo.TYPE_CLASS_TEXT |
                    EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
        } else if (mContentInputType == 1) {
            cetContent.setInputType(EditorInfo.TYPE_CLASS_NUMBER
                    | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
        } else if (mContentInputType == 2) {
            cetContent.setInputType(EditorInfo.TYPE_CLASS_PHONE
                    | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
        } else if (mContentInputType == 3) {
            cetContent.setInputType(EditorInfo.TYPE_CLASS_NUMBER
                    | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
                    | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
        } else {
            cetContent.setInputType(EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
        }
        // 最大长度
        if (mContentTextLength != 0) {
            cetContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mContentTextLength)});
        }
        // 设置最小高度
        if (mContentMinHeight != 0) {
            llContainer.setMinimumHeight(mContentMinHeight);
        }
        // 是否单行显示
        cetContent.setSingleLine(mContentSingleLine);

        if (!TextUtils.isEmpty(mRightTitle)) {
            tvRightTitle.setText(mRightTitle);
            tvRightTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTitleSize);
            tvRightTitle.setVisibility(VISIBLE);
        }
        if (mRightTitleColor != 0) {
            tvRightTitle.setTextColor(mRightTitleColor);
        }

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


    public ImageView getIvLeftIcon() {
        return ivLeftIcon;
    }

    public TextView getTvLeftTitle() {
        return tvLeftTitle;
    }

    public TextView getTvLeftDesc() {
        return tvLeftDesc;
    }

    public TextView getTvRightTitle() {
        return tvRightTitle;
    }

    public CleanableEditText getCetContent() {
        return cetContent;
    }

    public CleanableEditText getContentView() {
        return cetContent;
    }


    public void setLeftText(String text) {
        tvLeftTitle.setText(text);
    }

    public void setRightText(String text) {
        tvRightTitle.setText(text);
    }

    public void setContentText(String text) {
        cetContent.setText(text);
        cetContent.setVisibility(VISIBLE);
    }


    public String getLeftText() {
        return tvLeftTitle.getText().toString().trim();
    }

    public String getRightText() {
        return tvRightTitle.getText().toString().trim();
    }

    public String getContentText() {
        return cetContent.getText().toString().trim();
    }

}
