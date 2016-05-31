package com.mai.xmai_fast_lib.utils;

import android.util.Log;

/**
 * Created by mai on 16/4/25.
 */
public class MLog {
    public static String TAG = "mai";
    private static boolean isLog = true;

    public static void log(String msg){
        if(isLog)
            Log.e("Mlog-->" + TAG, msg);
    }

    public static void log(String tag, String msg){
        if(isLog)
            Log.e("Mlog-->" + tag + "--->", msg);
    }

    public static void setIsLog(boolean isLog) {
        MLog.isLog = isLog;
    }
}
