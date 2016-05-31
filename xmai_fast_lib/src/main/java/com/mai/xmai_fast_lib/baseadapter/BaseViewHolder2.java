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
public class BaseViewHolder2 extends BaseViewHolderImpl{
	

	private int viewResId;

	public BaseViewHolder2(View mView) {
		super(mView);
	}

	public int getViewResId() {
		return viewResId;
	}
	
	public void setViewResId(int viewResId) {
		this.viewResId = viewResId;
	}


	public static BaseViewHolder2 get(View convertView, ViewGroup parent, int layoutId){
		BaseViewHolder2 viewHolder = null;
        if (convertView == null || ((BaseViewHolder2) convertView.getTag()).getViewResId() != layoutId) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            viewHolder = new BaseViewHolder2(convertView);
            viewHolder.setViewResId(layoutId);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BaseViewHolder2) convertView.getTag();
        }
        return viewHolder;
	}
	
}
