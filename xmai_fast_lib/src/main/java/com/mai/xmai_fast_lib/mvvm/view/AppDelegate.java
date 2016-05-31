package com.mai.xmai_fast_lib.mvvm.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * View delegate base class
 * @author maijuntian
 * 视图层代理的基类
 */
public abstract class AppDelegate implements IDelegate {

	protected OnClickListener mOnClickListener;
	
	private String log_tag = this.getClass().getSimpleName() + ":mai";
	
    protected final SparseArray<View> mViews = new SparseArray<View>();

    public View rootView;

    public abstract int getRootLayoutId();
    
    protected Context mContext;
    
    public String getString(int resId){
    	return mContext.getString(resId);
    }
    
    public Resources getResources(){
    	return mContext.getResources();
    }
    
    public Context getBaseContext(){
    	return mContext;
    }

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mContext = inflater.getContext();
    	getResources();
        int rootLayoutId = getRootLayoutId();
        rootView = inflater.inflate(rootLayoutId, container, false);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initWidget() {
    }

    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public void setOnClickListener(OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }
    
    public void setTextViewText(int id, String text){
    	if(id == 0 || text == null)
    		return;
    	((TextView)get(id)).setText(text);
    }
    
    public void setVisibility(int id, int visibility){
    	get(id).setVisibility(visibility);
    }
    
    public void setTextViewText(int id, int resId){
    	setTextViewText(id, getString(resId));
    }
    
    public String getViewText(int id){
    	TextView textView = get(id);
    	if(textView == null)
    		return null;
    	return textView.getText().toString();
    }
    
    public void setOnClickListener(OnClickListener onClickListener){
		this.mOnClickListener = onClickListener;
	}
    
    protected void log(String msg){
		Log.e(log_tag, msg);
	}
}
