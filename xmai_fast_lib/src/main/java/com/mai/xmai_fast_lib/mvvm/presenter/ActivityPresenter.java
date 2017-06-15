package com.mai.xmai_fast_lib.mvvm.presenter;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.mai.xmai_fast_lib.base.BaseFragmentActivity;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;


/**
 * Presenter base class for Activity
 * Presenter层的实现基类
 * @author maijuntian
 */
public abstract class ActivityPresenter<T extends IDelegate> extends BaseFragmentActivity implements OnClickListener{
	
    public T viewDelegate;

    public void initDelegate() {
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass(); 
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
    	
        try {
        	viewDelegate = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDelegate();
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(viewDelegate.getRootView());
        ButterKnife.bind(this);
        viewDelegate.setOnClickListener(this);
        viewDelegate.initWidget();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    @Override
    public void onClick(View v) {
    	
    }
}
