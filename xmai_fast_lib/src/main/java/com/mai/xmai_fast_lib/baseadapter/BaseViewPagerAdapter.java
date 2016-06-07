package com.mai.xmai_fast_lib.baseadapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 抽象的PagerAdapter实现类,封装了内容为View的公共操作.
 * @author maijuntian
 */
public abstract class BaseViewPagerAdapter<T> extends PagerAdapter {
	protected List<T> mData;
    protected SparseArray<View> mViews;

    public BaseViewPagerAdapter(List<T> data) {
        mViews = new SparseArray<View>();
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 :mData.size();
    }


    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        BaseViewHolder baseViewHolder = BaseViewHolder.get(view, container, bindLayoutId());
        initView(mData.get(position), baseViewHolder);
        baseViewHolder.setPosition(position);
        if(view == null)
        	mViews.put(position, baseViewHolder.getView());
        container.addView(baseViewHolder.getView());
        return baseViewHolder.getView();
    }
    
    /**
	 * 绑定相对应的item界面
	 * @return
	 */
	protected abstract int bindLayoutId();
	
	/**
	 * 将ViewHolder的控件跟内容绑定在一起
	 * @param data  数据内容
	 * @param viewHolder   
	 */
	protected abstract void initView(T data, BaseViewHolder viewHolder);


    public void notifyUpdateView(int position) {
        View view = updateView(mViews.get(position), position);
        mViews.put(position, view);
        notifyDataSetChanged();
    }

    public View updateView(View view, int position) {
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

}
