package com.tianxiabuyi.txutils.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wangyd
 * @date 2018/10/25
 * @description 格式化工具
 */
public class FormatUtils {

    /**
     * 金额格式化 第一种方式
     */
    public static String formatMoney(String money) {
        try {
            Double value = Double.valueOf(money);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.00";
    }

    /**
     * 金额格式化 第二种方式
     */
    public static String formatMoney2(String money) {
        try {
            Double value = Double.valueOf(money);
            BigDecimal bg = new BigDecimal(value);
            double result = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.00";
    }

}
