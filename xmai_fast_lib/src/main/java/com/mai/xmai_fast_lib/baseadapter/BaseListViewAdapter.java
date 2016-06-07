package com.mai.xmai_fast_lib.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * list集合的基础适配器
 * @author maijuntian
 *
 * @param <T>
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {
	
	protected List<T> mData;
	protected Context context;
	
	public BaseListViewAdapter(List<T> mData){
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
		BaseViewHolder baseViewHolder = BaseViewHolder.get(convertView, parent, bindLayoutId(position));
		baseViewHolder.setPosition(position);
		initView(mData.get(position), baseViewHolder);
		return baseViewHolder.getView();
	}

	/**
	 * 绑定相对应的item界面
	 * @return
	 */
	protected abstract int bindLayoutId(int position);
	
	/**
	 * 将ViewHolder的控件跟内容绑定在一起
	 * @param viewHolder   
	 */
	protected abstract void initView(T data, BaseViewHolder viewHolder);
}
