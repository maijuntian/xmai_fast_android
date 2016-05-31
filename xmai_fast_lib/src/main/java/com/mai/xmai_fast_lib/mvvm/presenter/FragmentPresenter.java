package com.mai.xmai_fast_lib.mvvm.presenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import com.mai.xmai_fast_lib.base.BaseFragment;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;

/**
 * Presenter base class for Fragment Presenter层的实现基类
 * 
 * @author maijuntian
 */
public abstract class FragmentPresenter<T extends IDelegate> extends
		BaseFragment implements OnClickListener {
	public T viewDelegate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];

		try {
			viewDelegate = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("create IDelegate error");
		} catch (IllegalAccessException e) {
			throw new RuntimeException("create IDelegate error");
		} catch (java.lang.InstantiationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		viewDelegate.create(inflater, container, savedInstanceState);
		return viewDelegate.getRootView();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);
		viewDelegate.setOnClickListener(this);
		viewDelegate.initWidget();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		if (viewDelegate.getOptionsMenuId() != 0) {
			inflater.inflate(viewDelegate.getOptionsMenuId(), menu);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		viewDelegate = null;
	}

	@Override
	public void onClick(View v) {

	}
}
