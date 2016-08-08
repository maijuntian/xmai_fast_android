package com.mai.xmai_fast_lib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * activity管理类
 * @author maijuntian
 *
 */
public class XAppManager {
	private static Stack<Activity> activityStack;
	private static XAppManager instance;

	private XAppManager()
	{
	}

	/**
	 * 单一实例
	 */
	public static XAppManager getInstance()
	{
		if (instance == null) {
			synchronized (XAppManager.class) {
				if (instance == null) {
					instance = new XAppManager();
				}
			}
		}
		return instance;
	}

	public static Stack<Activity> getActivityStack() {
		return activityStack;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity)
	{
		if (activityStack == null)
		{
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity()
	{
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity()
	{
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity)
	{
		if (activity != null)
		{
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 移除指定的Activity
	 */
	public void removeActivity(Activity activity)
	{
		if (activity != null)
		{
			activityStack.remove(activity);
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls)
	{
		Activity finishActviity = null;
		for (Activity activity : activityStack)
		{
			if (activity.getClass().equals(cls))
			{
				finishActviity = activity;
			}
		}
		finishActviity.finish();
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity()
	{
		for (int i = 0, size = activityStack.size(); i < size; i++)
		{
			if (null != activityStack.get(i))
			{
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 * 
	 * @param context
	 *            上下文
	 * @param isBackground
	 *            是否开开启后台运行
	 */
	public void AppExit(Context context, Boolean isBackground)
	{
		try
		{
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
		} catch (Exception e)
		{

		} finally
		{
			// 注意，如果您有后台程序运行，请不要支持此句子
			if (!isBackground)
			{
				System.exit(0);
			}
		}
	}
}
