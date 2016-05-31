package com.mai.xmai_fast_lib.mvvm.databind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


import com.mai.xmai_fast_lib.mvvm.model.IModel;
import com.mai.xmai_fast_lib.mvvm.presenter.FragmentPresenter;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

import java.lang.reflect.ParameterizedType;


/**
 * 集成数据-视图绑定的Fragment基类(Presenter层)
 *
 */
public abstract class DataBindFragment<T extends IDelegate, D extends IModel> extends
        FragmentPresenter<T> {

    protected DataBinder binder;
    protected D data;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass(); 
		Class<D> dataClazz = (Class<D>) pt.getActualTypeArguments()[1]; // 获取第一个类型参数的真实类型
		try {
			data = dataClazz.newInstance();
		} catch (Fragment.InstantiationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = getDataBinder();
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged() {
        if (binder != null)
            binder.viewBindModel(viewDelegate, data);
    }
}
