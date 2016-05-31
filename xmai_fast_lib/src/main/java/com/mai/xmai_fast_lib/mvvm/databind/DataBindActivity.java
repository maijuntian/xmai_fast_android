package com.mai.xmai_fast_lib.mvvm.databind;

import android.os.Bundle;

import com.mai.xmai_fast_lib.mvvm.model.IModel;
import com.mai.xmai_fast_lib.mvvm.presenter.ActivityPresenter;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

import java.lang.reflect.ParameterizedType;


/**
 * 集成数据-视图绑定的Activity基类(Presenter�?)
 *
 * @author maijuntian
 */
public abstract class DataBindActivity<T extends IDelegate, D extends IModel> extends
		ActivityPresenter<T> {
    protected DataBinder binder;
    
    protected D data;

    public DataBindActivity() {
		super();
		
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass(); 
		Class<D> dataClazz = (Class<D>) pt.getActualTypeArguments()[1]; // 获取第一个类型参数的真实类型
		try {
			data = dataClazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = getDataBinder();
    }

    public abstract DataBinder getDataBinder();
    

    public void notifyModelChanged() {
        if (binder != null)
            binder.viewBindModel(viewDelegate, data);
    }
}
