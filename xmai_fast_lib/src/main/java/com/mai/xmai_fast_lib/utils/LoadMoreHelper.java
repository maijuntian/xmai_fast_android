package com.mai.xmai_fast_lib.utils;

import android.support.v7.widget.RecyclerView;

import com.mai.xmai_fast_lib.baseadapter.BaseRecyclerViewAdapter;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemClickListener;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemLongClickListener;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnLoadingMoreListener;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by mai on 16/9/6.
 */
public class LoadMoreHelper<T> {

    boolean hasInitAdapter = false;

    int page;
    BaseRecyclerViewAdapter<T> mAdapter;
    List<T> mData;
    RecyclerView mRvView;
    ROnItemClickListener onItemClickListener;
    ROnItemLongClickListener onItemLongClickListener;
    LoadDataListener<T> loadDataListener;
    noDataListener noDataListener;
    FinishListener finishListener;
    int pageSize = 10;

    protected void setmAdapter(BaseRecyclerViewAdapter<T> mAdapter) {
        this.mAdapter = mAdapter;
    }

    protected void setmData(List<T> mData) {
        this.mData = mData;
    }

    protected void setmRvView(RecyclerView mRvView) {
        this.mRvView = mRvView;
    }

    protected void setOnItemClickListener(ROnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected void setOnItemLongClickListener(ROnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    protected void setLoadDataListener(LoadDataListener<T> loadDataListener) {
        this.loadDataListener = loadDataListener;
    }

    public void setNoDataListener(LoadMoreHelper.noDataListener noDataListener) {
        this.noDataListener = noDataListener;
    }

    protected void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public void notifyAdapter(List<T> datas) {
        if (page == 1) {
            mData.clear();
        }
        mData.addAll(datas);
        if (mData.size() == 0) {
            if (noDataListener != null) {
                noDataListener.noData();
            }
        }
        if (datas.size() < pageSize) {
            mAdapter.setLoadMoreComplete(true);
        } else {
            mAdapter.setLoadMoreComplete(false);
        }
        if (!hasInitAdapter) {
            if (onItemClickListener != null)
                mAdapter.setOnItemClickListener(onItemClickListener);

            if (onItemLongClickListener != null)
                mAdapter.setOnItemLongClickListener(onItemLongClickListener);

            mAdapter.setOnLoadingMoreListener(new ROnLoadingMoreListener() {
                @Override
                public void onLoadingMore() {
                    notifyObservable(loadDataListener.loadData(page));
                }
            });
            mRvView.setAdapter(mAdapter);
            hasInitAdapter = true;
        } else {
            mAdapter.notifyDataSet();
        }
    }

    public void loadFirstPage() {
        page = 1;
        notifyObservable(loadDataListener.loadData(page));
    }

    public void notifyObservable(Observable<List<T>> observable) {
        observable.subscribe(new Action1<List<T>>() {
            @Override
            public void call(List<T> list) {
                if(finishListener != null)
                    finishListener.onFinish(true);
                notifyAdapter(list);
                page++;
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if(finishListener != null)
                    finishListener.onFinish(false);
                throwable.printStackTrace();
            }
        });
    }

    public static class Builder<T> {
        BaseRecyclerViewAdapter<T> mAdapter;
        List<T> mData;
        RecyclerView mRvView;
        ROnItemClickListener onItemClickListener;
        ROnItemLongClickListener onItemLongClickListener;
        LoadDataListener<T> loadDataListener;
        noDataListener noDataListener;
        FinishListener finishListener;
        int pageSize = 10;

        public Builder setAdapter(BaseRecyclerViewAdapter<T> adapter) {
            mAdapter = adapter;
            return this;
        }

        public Builder setData(List<T> data) {
            mData = data;
            return this;
        }

        public Builder setView(RecyclerView rvView, RecyclerView.LayoutManager layoutManager) {
            mRvView = rvView;
            mRvView.setLayoutManager(layoutManager);
            return this;
        }

        public Builder setOnItemClickListener(ROnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setOnItemLongClickListener(ROnItemLongClickListener onItemLongClickListener) {
            this.onItemLongClickListener = onItemLongClickListener;
            return this;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setLoadDataListener(LoadDataListener<T> loadDataListener) {
            this.loadDataListener = loadDataListener;
            return this;
        }

        public Builder setNoDataListener(LoadMoreHelper.noDataListener noDataListener) {
            this.noDataListener = noDataListener;
            return this;
        }

        public Builder setFinishListener(FinishListener finishListener) {
            this.finishListener = finishListener;
            return this;
        }

        public <T> LoadMoreHelper<T> build() {
            LoadMoreHelper loadMoreHelper = new LoadMoreHelper<T>();
            loadMoreHelper.setOnItemClickListener(onItemClickListener);
            loadMoreHelper.setLoadDataListener(loadDataListener);
            loadMoreHelper.setmAdapter(mAdapter);
            loadMoreHelper.setmData(mData);
            loadMoreHelper.setmRvView(mRvView);
            loadMoreHelper.setOnItemLongClickListener(onItemLongClickListener);
            loadMoreHelper.setPageSize(pageSize);
            loadMoreHelper.setNoDataListener(noDataListener);
            loadMoreHelper.setFinishListener(finishListener);
            return loadMoreHelper;
        }
    }

    public interface FinishListener {
        void onFinish(boolean isSucc);
    }

    public interface LoadDataListener<T> {
        Observable<List<T>> loadData(int page);

    }

    public interface noDataListener {
        void noData();
    }
}
