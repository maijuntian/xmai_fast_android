package com.mai.xmai_fast_lib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * activity管理类
 *
 * @author maijuntian
 */
public class XAppManager {
    private static CopyOnWriteArrayList<Activity> activityStack;
    private static XAppManager instance;

    private XAppManager() {
    }

    /**
     * 单一实例
     */
    public static XAppManager getInstance() {
        if (instance == null) {
            synchronized (XAppManager.class) {
                if (instance == null) {
                    instance = new XAppManager();
                }
            }
        }
        return instance;
    }

    public static CopyOnWriteArrayList<Activity> getActivityStack() {
        return activityStack;
    }

    public Activity findActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new CopyOnWriteArrayList<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.get(activityStack.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = currentActivity();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity act : activityStack) {
            act.finish();
        }
        activityStack.clear();
    }

    public void doInAllActivity(final DoAllActivityListener listener) {
        for (final Activity act : activityStack) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.doAll(act);
                }
            });
        }
    }

    public void finishALLActivityExcept(Class clazz) {
        List<Activity> acts = new ArrayList<>();
        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(clazz)) {
                acts.add(activity);
            }
        }
        for (Activity act : acts) {
            act.finish();
        }
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }

    public interface DoAllActivityListener {
        void doAll(Activity act);
    }
}
