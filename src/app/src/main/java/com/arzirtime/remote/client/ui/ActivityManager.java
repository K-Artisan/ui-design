package com.arzirtime.remote.client.ui;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    private static List<Activity> activities = new ArrayList<>();

    /**
     * 添加活动
     * */
    public static void  addActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * 移除活动
     * */
    public static void  removeActivity(Activity activity){
        activities.remove(activity);
    }

    /**
     * 销毁所以活动
     * */
    public static  void finishAllActivity(){
        for (Activity activity: activities) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
