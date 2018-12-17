package com.tianxiabuyi.txutils.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author xjh1994
 * @date 2015/10/25
 * @description 用于管理Activity和应用程序退出
 */
public class ActivityUtils {
    private static Stack<Activity> activityStack;
    private static ActivityUtils instance;

    private ActivityUtils() {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
    }

    public static ActivityUtils getInstance() {
        if (instance == null) {
            instance = new ActivityUtils();
        }
        return instance;
    }

    public int size() {
        if (activityStack == null) {
            return 0;
        }
        return activityStack.size();
    }

    /**
     * 添加 Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束指定 Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定 Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束当前 Activity（栈顶Activity）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 获取当前 Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 获取某个 Activity
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束所有 Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有 activity 除了cls，如果cls不存在于栈中，则栈全部清空
     */
    public void finishAllActivityExcept(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!(activity.getClass().equals(cls))) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 应用程序退出
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                am.killBackgroundProcesses(context.getPackageName());
            }
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
