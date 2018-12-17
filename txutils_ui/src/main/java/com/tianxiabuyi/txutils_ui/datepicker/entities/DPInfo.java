package com.tianxiabuyi.txutils_ui.datepicker.entities;

/**
 * 日历数据实体 封装日历绘制时需要的数据
 *
 * @author AigeStudio
 * @date 2015-03-26
 * @description Entity of calendar
 */
public class DPInfo {
    public String strG, strF;
    public boolean isHoliday;
    public boolean isToday, isWeekend;
    public boolean isSolarTerms, isFestival, isDeferred;
    public boolean isDecorBG;
    public boolean isDecorTL, isDecorT, isDecorTR, isDecorL, isDecorR;
    //add by zj@17/2/9
    public boolean isSelected;
}