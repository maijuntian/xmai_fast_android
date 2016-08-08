package com.mai.xmai_fast_lib.baseadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewHolder基础类 <p/>
 * 解决adapter不用重新定义ViewHolder类
 * @author maijuntian
 *
 */
public class BaseViewHolder extends BaseViewHolderImpl{
	

	private int viewResId;

	public BaseViewHolder(View mView) {
		super(mView);
	}

	public int getViewResId() {
		return viewResId;
	}
	
	public void setViewResId(int viewResId) {
		this.viewResId = viewResId;
	}


	public static BaseViewHolder get(View convertView, ViewGroup parent, int layoutId){
		BaseViewHolder viewHolder = null;
        if (convertView == null || ((BaseViewHolder) convertView.getTag()).getViewResId() != layoutId) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            viewHolder = new BaseViewHolder(convertView);
            viewHolder.setViewResId(layoutId);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BaseViewHolder) convertView.getTag();
        }
        return viewHolder;
	}
	
}
