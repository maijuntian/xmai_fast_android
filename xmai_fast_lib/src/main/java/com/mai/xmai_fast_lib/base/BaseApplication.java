package com.mai.xmai_fast_lib.base;

import android.app.Application;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * 1、加入Bugly
 * Created by mai on 16/5/16.
 */
public abstract class BaseApplication extends Application{

    private static BaseApplication instance;
    public  static BaseApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        if(!TextUtils.isEmpty(getBuglyAppid())) {
            CrashReport.initCrashReport(getApplicationContext(), getBuglyAppid(), false);
        }
    }

    /**
     * bugly APPId
     */
    public abstract String getBuglyAppid();
}
