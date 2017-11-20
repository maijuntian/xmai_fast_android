package com.mai.xmai;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by mai on 2017/9/11.
 */
public class Test extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new AsyncTask<Void, String, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }
        }.execute();
    }
}
