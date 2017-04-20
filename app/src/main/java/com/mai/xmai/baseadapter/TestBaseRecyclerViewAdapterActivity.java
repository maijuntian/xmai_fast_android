package com.mai.xmai.baseadapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mai.xmai.R;
import com.mai.xmai_fast_lib.baseadapter.animators.ScaleAnimator;
import com.mai.xmai_fast_lib.baseadapter.animators.SlideBottomAnimator;
import com.mai.xmai_fast_lib.baseadapter.animators.SlideLeftAnimator;
import com.mai.xmai_fast_lib.baseadapter.animators.SlideRightAnimator;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemClickListener;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemLongClickListener;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnLoadingMoreListener;
import com.mai.xmai_fast_lib.utils.MLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mai on 16/5/9.
 */
public class TestBaseRecyclerViewAdapterActivity extends Activity {

    RecyclerView recyclerView;
    TestBaseRecycleViewAdapter textRecycleViewAdapter;
    final int COUNT = 100;
    final int PAGESIZE = 3;
    List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseadapter);
        initView();
    }

    private List<String> getDatas(int num) {
        Random random = new Random();
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            datas.add("随机数：" + random.nextInt(100));
        }
        return datas;
    }

    public void initView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_test, null, false);
        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_test, null, false);

        recyclerView = (RecyclerView) findViewById(R.id.cv_test);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = getDatas(PAGESIZE);
        textRecycleViewAdapter = new TestBaseRecycleViewAdapter(mDatas);
        textRecycleViewAdapter.addFooterView(footerView);
        textRecycleViewAdapter.addHeaderView(headerView);
        textRecycleViewAdapter.setAnimator(new SlideRightAnimator());
        textRecycleViewAdapter.setOnLoadingMoreListener(new ROnLoadingMoreListener() {
            @Override
            public void onLoadingMore() {
                loadMore();
            }
        });
        textRecycleViewAdapter.setOnItemClickListener(new ROnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "短按：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        textRecycleViewAdapter.setOnItemLongClickListener(new ROnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {

                Toast.makeText(getApplicationContext(), "长按：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(textRecycleViewAdapter);
    }

    private void loadMore(){
        Observable.just(PAGESIZE).map(new Func1<Integer, List<String>>() {
            @Override
            public List<String> call(Integer num) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (new Random().nextInt(2) == 1) {
                    throw new RuntimeException("超时");
                }
                List<String> lists = getDatas(num);
                return lists;

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> newDats) {
                MLog.log("size:" + (mDatas.size() + newDats.size()));
                textRecycleViewAdapter.setTimeOut(false);
                if (mDatas.size() + newDats.size() > COUNT) {
                    textRecycleViewAdapter.setLoadMoreComplete(true);
                }
                textRecycleViewAdapter.addDatas(newDats);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                MLog.log("出错");
                textRecycleViewAdapter.setTimeOut(true);
                textRecycleViewAdapter.notifyDataSet();
                throwable.printStackTrace();
            }
        });
    }
}
