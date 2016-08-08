package com.mai.xmai.basehttp;

import android.app.ProgressDialog;
import android.content.Context;

import com.mai.xmai_fast_lib.basehttp.BaseRetrofitService;

import rx.Observable;
import rx.functions.Action0;

/**
 * mai
 */
public class KaolaApi extends BaseRetrofitService<IKaolaService> {

    private static KaolaApi mKaolaApi;

    private ProgressDialog dialog;

    public static KaolaApi getInstance() {
        if (mKaolaApi == null) {
            mKaolaApi = new KaolaApi();
        }
        return mKaolaApi;
    }

    public Observable<DayBriefReport> getDayBriefReport(Context context, String uid, String startDate) {
        return checkAll(mService.getDayBriefReport(uid, startDate, "day"), context);
    }

    @Override
    public String getBaseUrl() {
        return "http://kaola.xtremeprog.com/api/";
    }

    @Override
    protected void showDialog(Context context) {
        dialog = ProgressDialog.show(context, "提示:", "加载中...");
    }

    @Override
    protected void dismissDialog() {
        dialog.dismiss();
    }
}
