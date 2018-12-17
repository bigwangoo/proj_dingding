package com.wangyd.dingding.core.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.tianxiabuyi.txutils.util.ToastUtils;

import java.util.regex.Pattern;

/**
 * @author wangyd
 * @date 2018/6/15
 * @description 正则验证工具
 */
public class PatternUtils {

    // 手机号码
    public static final String PHONE_PATTERN = "^1\\d{10}$";
    // 验证码
    public static final String PATTERN_SMS_CODE = "^[0-9]{6}$";
    // 密码
    public static final String PATTERN_PASSWORD = "[^\\u4e00-\\u9fa5]{6,20}";

    private static PatternUtils instance;

    private PatternUtils() {
    }

    public static PatternUtils getInstance() {
        if (instance == null) {
            synchronized (PatternUtils.class) {
                if (instance == null) {
                    instance = new PatternUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 检查手机号码
     */
    public boolean checkPhone(Context context, String phone) {
        return checkPhone(context, phone, PHONE_PATTERN, true,
                "请输入手机号码", "手机号码不合法");
    }

    public boolean checkPhone2(Context context, String phone) {
        return checkPhone(context, phone, PHONE_PATTERN, true,
                "请输入手机号码", "请输入正确的手机号码");
    }

    public boolean checkPhone(Context context, String phone, String StringFormat, boolean isJudgeEmpty,
                              String emptyToast, String errToast) {
        if (isJudgeEmpty && TextUtils.isEmpty(phone)) {
            Toast.makeText(context, emptyToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("\\s").matcher(phone).find()) {
            Toast.makeText(context, "手机号码不能包含空格", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.matches(StringFormat, phone)) {
            Toast.makeText(context, errToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查验证码
     */
    public boolean checkCode(Context context, String code) {
        return checkCode(context, code, PATTERN_SMS_CODE, true,
                "请输入验证码", "验证码应为6位数字");
    }

    public boolean checkCode(Context context, String code, String StringFormat, boolean isJudgeEmpty,
                             String emptyToast, String errToast) {
        if (isJudgeEmpty && TextUtils.isEmpty(code)) {
            Toast.makeText(context, emptyToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.matches(StringFormat, code)) {
            Toast.makeText(context, errToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查用户名
     */
    public boolean checkUserName(Context context, String userName) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("\\s").matcher(userName).find()) {
            Toast.makeText(context, "用户名不能包含空格", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userName.length() < 4) {
            Toast.makeText(context, "用户名长度不能少于4位字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userName.length() > 10) {
            Toast.makeText(context, "用户名长度不能多于10位字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z]").matcher(userName).find()) {
            Toast.makeText(context, "用户名必须以字母开头", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("[\u4e00-\u9fa5]").matcher(userName).find()) {
            Toast.makeText(context, "用户名不能包含中文", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]+$", userName)) {
            Toast.makeText(context, "用户名仅支持字母、数字", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查密码
     */
    public boolean checkPassword(Context context, String password) {
        return checkPassword(context, password, PATTERN_PASSWORD, true,
                "请输入密码", "密码不符合要求");
    }

    public boolean checkPassword(Context context, String password, String StringFormat,
                                 boolean isJudgeEmpty, String emptyToast, String errToast) {
        if (isJudgeEmpty && TextUtils.isEmpty(password)) {
            Toast.makeText(context, emptyToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("\\s").matcher(password).find()) {
            Toast.makeText(context, "密码不能包含空格", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(context, "密码长度不能少于6位字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() > 20) {
            Toast.makeText(context, "密码长度不能多于20位字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("[\u4e00-\u9fa5]").matcher(password).find()) {
            Toast.makeText(context, "密码不能包含中文", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.matches(StringFormat, password)) {
            Toast.makeText(context, errToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查确认密码
     */
    public boolean checkConfirmPassword(String password, String pwdConfirm) {
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(pwdConfirm)) {
            ToastUtils.show("请输入确认密码");
            return false;
        }
        if (!password.equals(pwdConfirm)) {
            ToastUtils.show("两次输入密码不一致");
            return false;
        }
        return true;
    }

    /**
     * 检查真实姓名
     */
    public boolean checkRealName(Context context, String realUserName) {
        if (TextUtils.isEmpty(realUserName)) {
            Toast.makeText(context, "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("\\s").matcher(realUserName).find()) {
            Toast.makeText(context, "真实姓名不能包含空格", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (realUserName.length() < 2) {
            Toast.makeText(context, "真实姓名应不少于2位字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (realUserName.length() > 10) {
            Toast.makeText(context, "真实姓名不能多于10位字符", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.matches("^[\\u4e00-\\u9fa5]+$", realUserName)) {
            Toast.makeText(context, "真实姓名必须为中文", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检测真实姓名, (不做限制版)
     */
    public boolean simpleCheckRealName(Context context, String realUserName) {
        if (TextUtils.isEmpty(realUserName)) {
            Toast.makeText(context, "请输入真实姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (realUserName.length() < 2 || realUserName.length() > 20) {
            Toast.makeText(context, "真实姓名2-20位", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查身份证
     */
    public boolean checkIdCard(Context context, String idCard) {
        return checkIdCard(context, idCard,
                true, "请输入身份证号码", "身份证号不合法");
    }

    public boolean checkIdCard2(Context context, String idCard) {
        return checkIdCard(context, idCard,
                true, "请输入身份证号码", "请输入合法的身份证号");
    }

    public boolean checkIdCard(Context context, String idCard,
                               boolean isJudgeEmpty, String emptyToast, String errToast) {
        if (isJudgeEmpty && TextUtils.isEmpty(idCard)) {
            Toast.makeText(context, emptyToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("\\s").matcher(idCard).find()) {
            Toast.makeText(context, "身份证号码不能包含空格", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!IdcardTool.validateCard(idCard)) {
            Toast.makeText(context, errToast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 手机号Mask  150****0000
     */
    public String maskPhone(String phone) {
        // 校验
        if (!TextUtils.isEmpty(phone) && Pattern.matches(PatternUtils.PHONE_PATTERN, phone)) {
            return phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        }
        return phone;
    }

    /**
     * 身份证号Mask 320
     */
    public String maskCardNum(String cardNum) {
        if (IdcardTool.validateCard(cardNum)) {
            StringBuilder mask = new StringBuilder();
            for (int i = 0; i < cardNum.length() - 7; i++) {
                mask.append("*");
            }
            return cardNum.substring(0, 3) + mask.toString()
                    + cardNum.substring(cardNum.length() - 4, cardNum.length());
        }
        return cardNum;
    }

}
