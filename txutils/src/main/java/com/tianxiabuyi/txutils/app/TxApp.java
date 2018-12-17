package com.tianxiabuyi.txutils.app;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovos20-2 on 2015/11/19.
 */
@Deprecated
public abstract class TxApp extends Application {
    public static List<Map<String, Activity>> destroyList = new LinkedList<Map<String, Activity>>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public abstract int getToolbarColor();

    /**
     * an activity is added to the list
     *
     * @param activityname
     * @param activity
     */
    public static void addDestroyActivity(String activityname, Activity activity) {
        Map<String, Activity> map = new HashMap<String, Activity>();
        map.put(activityname, activity);
        destroyList.add(map);
    }

    /**
     * all of the activity is destoryed
     *
     * @param isExit you are sure destrory all the activity or exit app
     */
    public static void destroyALLActivity(Boolean isExit) {
        try {
            for (int i = destroyList.size() - 1; i >= 0; i--) {
                Map<String, Activity> map = destroyList.get(i);
                Set<String> set = map.keySet();
                for (String name : set) {
                    Activity activity = map.get(name);
                    if (activity != null)
                        activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            destroyList.clear();
            if (isExit) {
                System.exit(0);
            }
        }
    }

    /**
     * 销毁指定Activity
     * <p class = "note">
     * if lauchMode of activity is standard,there are two activity with same activityName
     * But we can find two activity are finished
     * </p>
     *
     * @param activityname this is name of activity
     */
    public static void destroyToActivity(String activityname) {
        for (int i = 0; i < destroyList.size(); i++) {
            if (destroyList.get(i).containsKey(activityname)) {
                destroyList.get(i).get(activityname).finish();
            }
        }
    }

    /**
     * 移除某个activity
     *
     * @param activityname
     */
    public static void removeActivity(String activityname) {

        for (int i = 0; i < destroyList.size(); i++) {
            if (destroyList.get(i).containsKey(activityname)) {
                destroyList.remove(i);
            }
        }
    }

}
