package com.mai.xmai_fast_lib.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * list集合的基础适配器
 *
 * @param <T>
 * @author maijuntian
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    protected List<T> mData;
    protected Context context;

    public BaseListViewAdapter(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        BaseViewHolder baseViewHolder = BaseViewHolder.get(convertView, parent, bindLayoutId(position), isRecycle());
        baseViewHolder.setPosition(position);
        T data;
        if(mData == null || mData.size() <= position){
            data = null;
        } else {
            data = mData.get(position);
        }
        initView(data, baseViewHolder);
        return baseViewHolder.getView();
    }

    /**
     * 是否循环利用布局
     *
     */
    protected boolean isRecycle() {
        return true;
    }

    /**
     * 绑定相对应的item界面
     *
     */
    protected abstract int bindLayoutId(int position);

    /**
     * 将ViewHolder的控件跟内容绑定在一起
     *
     * @param viewHolder
     */
    protected abstract void initView(T data, BaseViewHolder viewHolder);
}
