package com.mai.xmai_fast_lib.mvvm.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * View delegate base class
 * 视图层代理的接口协议
 */
public interface IDelegate {
    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    int getOptionsMenuId();

//    Toolbar getToolbar();

    View getRootView();

    void initWidget();
    
    void setOnClickListener(OnClickListener onClickListener);
}
