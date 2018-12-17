package com.tianxiabuyi.txutils_ui.datepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianxiabuyi.txutils_ui.R;
import com.tianxiabuyi.txutils_ui.datepicker.bizs.DPMode;
import com.tianxiabuyi.txutils_ui.datepicker.bizs.decors.DPDecor;
import com.tianxiabuyi.txutils_ui.datepicker.bizs.languages.DPLManager;
import com.tianxiabuyi.txutils_ui.datepicker.bizs.themes.DPTManager;
import com.tianxiabuyi.txutils_ui.utils.DisplayUtils;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * DatePicker，日期选择器
 *
 * @author AigeStudio 2015-06-29
 */
public class DatePicker extends LinearLayout {

    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private TextView tvYear;// 年份显示
    private TextView tvMonth;// 月份显示
    private MonthView monthView;// 月视图
    private OnMonthChangeListener onMonthChangeListener;// 月份选择监听
    private OnDateSelectedListener onDateSelectedListener;// 日期多选后监听

    private boolean isShowTitle, isShowDivider;

    /**
     * 月份切换监听器
     */
    public interface OnMonthChangeListener {
        void onMonthChange(String month);
    }

    /**
     * 日期单选监听器
     */
    public interface OnDatePickedListener {
        void onDatePicked(String date);
    }

    /**
     * 日期多选监听器
     */
    public interface OnDateSelectedListener {
        void onDateSelected(List<String> date);
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        TypedArray t = context.obtainStyledAttributes(R.styleable.DatePicker);
        isShowTitle = t.getBoolean(R.styleable.DatePicker_dpShowTitle, false);
        isShowDivider = t.getBoolean(R.styleable.DatePicker_dpShowDivider, false);
        t.recycle();

        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        // 标题栏子布局
        if (isShowTitle) {
            initTitle(context);
        }
        // 周视图根布局
        initWeeks(context);
        // 分割线
        if (isShowDivider) {
            initDivider(context);
        }
        // 月视图
        initMonths(context);
    }

    private void initTitle(Context context) {
        View llTitle = LayoutInflater.from(context).inflate(R.layout.tx_datepicker_title, null);
        ImageView ivPre = (ImageView) llTitle.findViewById(R.id.ivPre);
        ImageView ivNext = (ImageView) llTitle.findViewById(R.id.ivNext);
        tvYear = (TextView) llTitle.findViewById(R.id.tvYear);
        tvMonth = (TextView) llTitle.findViewById(R.id.tvMonth);
        ivPre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                monthView.preMonth();
            }
        });
        ivNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                monthView.nextMonth();
            }
        });

        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(llTitle, llParams);
    }

    private void initWeeks(Context context) {
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setOrientation(HORIZONTAL);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        int llWeekPadding = DisplayUtils.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(mTManager.colorG());
            tvWeek.setPadding(0, DisplayUtils.dp2px(context, 6), 0, DisplayUtils.dip2px(context, 6));
            llWeek.addView(tvWeek, lpWeek);
        }

        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(llWeek, llParams);
    }

    private void initDivider(Context context) {
        View divider = LayoutInflater.from(context).inflate(R.layout.tx_datepicker_divider, null);
        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(divider, llParams);
    }

    private void initMonths(Context context) {
        monthView = new MonthView(context);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
//                tvMonth.setText(mLManager.titleMonth()[month - 1]);
//                if (onMonthChangeListener != null) {
//                    onMonthChangeListener.onMonthChange(monthView.getShowMonth());
//                }
            }

            @Override
            public void onYearChange(int year) {
//                String tmp = String.valueOf(year);
//                if (tmp.startsWith("-")) {
//                    tmp = tmp.replace("-", mLManager.titleBC());
//                }
//                tvYear.setText(tmp);
            }

            @Override
            public void onDateChange(int month, int year) {
                if (onMonthChangeListener != null) {
                    onMonthChangeListener.onMonthChange(monthView.getShowMonth());
                }
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                if (tvMonth != null) {
                    tvMonth.setText(format(month) + "月");
                }
                if (tvYear != null) {
                    tvYear.setText(tmp + "年");
                }
            }
        });

        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(monthView, llParams);
    }

    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        monthView.setDPMode(mode);
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

    public void toSpecificDate(int month, int year) {
        monthView.toSpecificMonth(month, year);
    }

    /**
     * 由于设置decor数据可能是异步的，所以暴露此方法更新界面
     */
    public void notifyDecorChange() {
        monthView.invalidate();
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
        this.onMonthChangeListener = onMonthChangeListener;
    }

    /**
     * 设置单选监听器
     *
     * @param onDatePickedListener ...
     */
    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        if (monthView.getDPMode() != DPMode.SINGLE) {
            throw new RuntimeException("Current DPMode does not SINGLE! Please call setMode set DPMode to SINGLE!");
        }
        monthView.setOnDatePickedListener(onDatePickedListener);
    }

    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException("Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }
        this.onDateSelectedListener = onDateSelectedListener;
    }

    /**
     * @param value
     * @return
     */
    private String format(int value) {
        if (value == 1) {
            return "一";
        } else if (value == 2) {
            return "二";
        } else if (value == 3) {
            return "三";
        } else if (value == 4) {
            return "四";
        } else if (value == 5) {
            return "五";
        } else if (value == 6) {
            return "六";
        } else if (value == 7) {
            return "七";
        } else if (value == 8) {
            return "八";
        } else if (value == 9) {
            return "九";
        } else if (value == 10) {
            return "十";
        } else if (value == 11) {
            return "十一";
        } else {
            return "十二";
        }
    }
}
