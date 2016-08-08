package com.mai.xmai.basehttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mai.xmai.R;
import com.mai.xmai_fast_lib.utils.MLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by mai on 16/6/3.
 */
public class BaseHttpActivity extends Activity {

    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basehttp);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void button() {
        KaolaApi.getInstance().getDayBriefReport(BaseHttpActivity.this, "b345b8d87ae14768b312fbdfd72134e1", "2016-06-01")
                .subscribe(new Action1<DayBriefReport>() {
                    @Override
                    public void call(DayBriefReport dayBriefReportKaolaResponse) {
                        MLog.log(dayBriefReportKaolaResponse.toString());
                        textView.setText(dayBriefReportKaolaResponse.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

}
