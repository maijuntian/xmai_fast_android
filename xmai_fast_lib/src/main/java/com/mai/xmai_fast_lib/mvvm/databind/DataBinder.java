package com.mai.xmai_fast_lib.mvvm.databind;


import com.mai.xmai_fast_lib.mvvm.model.IModel;
import com.mai.xmai_fast_lib.mvvm.view.IDelegate;

/**
 * ViewModel实现
 */
public interface DataBinder<T extends IDelegate, D extends IModel> {

    void viewBindModel(T viewDelegate, D data);

//    void modelBindView(T viewDelegate, D data);
}
