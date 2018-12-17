package com.tianxiabuyi.villagedoctor.common.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author jjj
 * @date 2018/6/11
 * @description
 */
public class CommonPopupWindow {

    private CommonPopupWindow.Builder mBuilder;
    private PopupWindow mPopupWindow;

    public CommonPopupWindow(CommonPopupWindow.Builder builder) {
        this.mBuilder = builder;
        mPopupWindow = builder.mPopupWindow;
    }

    public void show() {
        mBuilder.startShow();
    }

    public void dismiss() {
        if (isShowing()) {
            mBuilder.onDismiss();
        }
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public static class Builder {
        private Activity mActivity;
        private PopupWindow mPopupWindow;
        private View contentView;
        private int width;
        private int height;
        private float bgAlpha;
        private ShowInterface mShowInterface;
        private View anchor;
        private int gravity, xOff, yOff;
        private LayoutGravity mLayoutGravity;

        private enum SHOW {ShowAtParent, showAsDropDown, showBashOfAnchor}

        private SHOW mSHOW;

        public Builder(Activity activity) {
            this.mActivity = activity;
            setLayoutWrp();
        }

        public Builder setLayoutRes(int layoutRes) {
            contentView = LayoutInflater.from(mActivity).inflate(layoutRes, null, false);
            return this;
        }

        public Builder setLayout(int w, int h) {
            this.width = w;
            this.height = h;
            return this;
        }

        public Builder setLayoutWrp() {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
            return this;
        }

        public Builder setLayoutMap() {
            width = ViewGroup.LayoutParams.MATCH_PARENT;
            height = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 创建PopupWindows
         */
        public Builder createPopupWindow() {
            mPopupWindow = new PopupWindow(contentView, width, height, true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setTouchable(true);
            mPopupWindow.setFocusable(true);
            setBgAlpha(0.5f);
            setOnDismiss((view, builder) -> true);
            showAtLocation(mActivity.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
            return this;
        }

        /**
         * 自定义布局操作，需要在 setLayoutRes(layoutRes) 之后
         */
        public Builder initView(InitInterface iViewInterface) {
            iViewInterface.initView(contentView, this);
            return this;
        }

        public Builder setText(int id, String msg) {
            if (!TextUtils.isEmpty(msg)) {
                TextView textView = contentView.findViewById(id);
                textView.setText(msg);
                if (textView instanceof EditText) {
                    ((EditText) textView).setSelection(msg.length());
                }
            }
            return this;
        }

        public Builder setVisibility(int id, boolean visible) {
            contentView.findViewById(id).setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            return this;
        }

        public Builder setSelected(int id, boolean selected) {
            contentView.findViewById(id).setSelected(selected);
            return this;
        }

        public Builder setOnClickListener(int id, ClickInterface clickInterface) {
            contentView.findViewById(id).setOnClickListener(view -> clickInterface.onClick(contentView, this));
            return this;
        }

        public Builder setCancelButton(int buttonRes) {
            setCancelButton(buttonRes, view -> {
            });
            return this;
        }

        public Builder setCancelButton(int buttonRes, View.OnClickListener clickListener) {
            View view = contentView.findViewById(buttonRes);
            view.setOnClickListener(view1 -> {
                onDismiss();
                clickListener.onClick(view);
            });
            return this;
        }


        public Builder setCancelable(boolean cancelable) {
            mPopupWindow.setOutsideTouchable(cancelable);
            return this;
        }

        public Builder setSoftInputMode(int mode) {
            mPopupWindow.setSoftInputMode(mode);
            return this;
        }

        public Builder setBgAlpha(float bgAlpha) {
            this.bgAlpha = bgAlpha;
            return this;
        }

        public Builder setOnShow(ShowInterface showInterface) {
            this.mShowInterface = showInterface;
            return this;
        }

        public Builder setOnDismiss(DismissInterface dismissInterface) {
            mPopupWindow.setOnDismissListener(() -> {
                if (dismissInterface.onDismiss(contentView, this)) {
                    onDismiss();
                }
            });
            return this;
        }

        public Builder showAtLocation(View parent, int gravity, int x, int y) {
            setShowData(parent, gravity, null, x, y);
            mSHOW = SHOW.ShowAtParent;
            return this;
        }

        public Builder showAsDropDown(View anchor, int xoff, int yoff) {
            setShowData(anchor, 0, null, xoff, yoff);
            mSHOW = SHOW.showAsDropDown;
            return this;
        }

        public Builder showBashOfAnchor(View anchor, LayoutGravity layoutGravity, int xmerge, int ymerge) {
            setShowData(anchor, 0, layoutGravity, xmerge, ymerge);
            mSHOW = SHOW.showBashOfAnchor;
            return this;
        }

        private void setShowData(View anchor, int gravity, LayoutGravity layoutGravity, int x, int y) {
            this.anchor = anchor;
            this.gravity = gravity;
            this.mLayoutGravity = layoutGravity;
            this.xOff = x;
            this.yOff = y;
        }

        public CommonPopupWindow build() {
            return new CommonPopupWindow(this);
        }

        public void setWindowAlpha(float bgAlpha) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = bgAlpha;
            if (lp.alpha == 1) {
                //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            } else {
                //此行代码主要是解决在华为手机上半透明效果无效的bug
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
            mActivity.getWindow().setAttributes(lp);
        }

        public String getText(int id) {
            View view = contentView.findViewById(id);
            if (view instanceof TextView) {
                return ((TextView) view).getText().toString();
            }
            return "";
        }

        public <T extends View> T getView(int id) {
            T view = contentView.findViewById(id);
            return view;
        }

        public void startShow() {
            setWindowAlpha(bgAlpha);
            if (mShowInterface != null) {
                mShowInterface.onShow(contentView, this);
            }
            switch (mSHOW) {
                case ShowAtParent:
                    mPopupWindow.showAtLocation(anchor, gravity, xOff, yOff);
                    break;
                case showAsDropDown:
                    mPopupWindow.showAsDropDown(anchor, xOff, yOff);
                    break;
                case showBashOfAnchor:
                    int[] offset = mLayoutGravity.getOffset(anchor, mPopupWindow);
                    mPopupWindow.showAsDropDown(anchor, offset[0] + xOff, offset[1] + yOff);
                    break;
                default:
                    break;
            }
        }

        public void onDismiss() {
            setWindowAlpha(1f);
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        }
    }

    public static class LayoutGravity {
        private int layoutGravity;
        // waring, don't change the order of these constants!
        public static final int ALIGN_LEFT = 0x1;
        public static final int ALIGN_ABOVE = 0x2;
        public static final int ALIGN_RIGHT = 0x4;
        public static final int ALIGN_BOTTOM = 0x8;
        public static final int TO_LEFT = 0x10;
        public static final int TO_ABOVE = 0x20;
        public static final int TO_RIGHT = 0x40;
        public static final int TO_BOTTOM = 0x80;
        public static final int CENTER_HORI = 0x100;
        public static final int CENTER_VERT = 0x200;

        public LayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        public int getLayoutGravity() {
            return layoutGravity;
        }

        public void setLayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        public void setHoriGravity(int gravity) {
            layoutGravity &= (0x2 + 0x8 + 0x20 + 0x80 + 0x200);
            layoutGravity |= gravity;
        }

        public void setVertGravity(int gravity) {
            layoutGravity &= (0x1 + 0x4 + 0x10 + 0x40 + 0x100);
            layoutGravity |= gravity;
        }

        public boolean isParamFit(int param) {
            return (layoutGravity & param) > 0;
        }

        public int getHoriParam() {
            for (int i = 0x1; i <= 0x100; i = i << 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }
            return ALIGN_LEFT;
        }

        public int getVertParam() {
            for (int i = 0x2; i <= 0x200; i = i << 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }

            return TO_BOTTOM;
        }

        public int[] getOffset(View anchor, PopupWindow window) {
            int anchWidth = anchor.getWidth();
            int anchHeight = anchor.getHeight();

            int winWidth = window.getWidth();
            int winHeight = window.getHeight();
            View view = window.getContentView();
            if (winWidth <= 0) {
                winWidth = view.getWidth();
            }
            if (winHeight <= 0) {
                winHeight = view.getHeight();
            }

            int xoff = 0;
            int yoff = 0;

            switch (getHoriParam()) {
                case ALIGN_LEFT:
                    xoff = 0;
                    break;
                case ALIGN_RIGHT:
                    xoff = anchWidth - winWidth;
                    break;
                case TO_LEFT:
                    xoff = -winWidth;
                    break;
                case TO_RIGHT:
                    xoff = anchWidth;
                    break;
                case CENTER_HORI:
                    xoff = (anchWidth - winWidth) / 2;
                    break;
                default:
                    break;
            }
            switch (getVertParam()) {
                case ALIGN_ABOVE:
                    yoff = -anchHeight;
                    break;
                case ALIGN_BOTTOM:
                    yoff = -winHeight;
                    break;
                case TO_ABOVE:
                    yoff = -anchHeight - winHeight;
                    break;
                case TO_BOTTOM:
                    yoff = 0;
                    break;
                case CENTER_VERT:
                    yoff = (-winHeight - anchHeight) / 2;
                    break;
                default:
                    break;
            }
            return new int[]{xoff, yoff};
        }
    }

    public interface InitInterface {
        void initView(View view, Builder builder);
    }

    public interface ShowInterface {
        void onShow(View view, Builder builder);
    }

    public interface DismissInterface {
        boolean onDismiss(View view, Builder builder);
    }

    public interface ClickInterface {
        void onClick(View view, Builder builder);
    }
}
