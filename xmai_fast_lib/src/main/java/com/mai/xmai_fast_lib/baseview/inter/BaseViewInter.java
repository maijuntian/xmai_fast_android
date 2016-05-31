package com.mai.xmai_fast_lib.baseview.inter;

import android.view.View;

/**
 * 自定义基础标题栏布局
 * @author mai
 *
 */
public interface BaseViewInter {
	
	/**
	 * 返回布局文件id
	 * @return
	 */
	int getBaseLayoutId();
	
	/**
	 * 返回布局里面内容id
	 * @return
	 */
	int getBaseCenterLayoutId();
	
	/**
	 * 返回标题栏的左边id
	 * 	注意是textview
	 * @return
	 */
	int getBaseBarLeftId();
	
	/**
	 * 返回标题栏的右边id
	 * 	注意是textview
	 * @return
	 */
	int getBaseBarRightId();
	
	/**
	 * 返回标题栏的标题id
	 * 	注意是textview
	 * @return
	 */
	int getBaseBarTitleId();
	
	/**
	 * 初始化基础布局
	 * @param view
	 */
	void initBaseView(View view);
}
