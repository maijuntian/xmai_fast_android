package com.mai.xmai_fast_lib.baseview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mai.xmai_fast_lib.baseview.inter.BaseViewInter;
import com.mai.xmai_fast_lib.mvvm.view.AppDelegate;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;


/**
 * 带有标题栏的基础布局
 * 		可被自定义标题栏
 * @author mai
 *
 * @param <T>
 */
public abstract class BaseViewDelegate<T extends BaseViewInter> extends AppDelegate {

	protected ViewGroup center;

	protected TextView tvBaseBarLeft, tvBaseBarTitle, tvBaseBarRight;

	@Override
	public void create(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass(); 
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
    	BaseViewInter baseViewInter;
        try {
        	baseViewInter = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create BaseViewInter error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create BaseViewInter error");
        }
		
		mContext = inflater.getContext();
		
		
		int rootLayoutId = getRootLayoutId();
		rootView = inflater.inflate(baseViewInter.getBaseLayoutId(), container, false);
		View centerView = inflater.inflate(rootLayoutId, container, false);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		centerView.setLayoutParams(layoutParams);
		center = (ViewGroup) rootView.findViewById(baseViewInter.getBaseCenterLayoutId());
		center.addView(centerView);
		tvBaseBarTitle = (TextView) rootView.findViewById(baseViewInter.getBaseBarTitleId());
		tvBaseBarLeft = (TextView) rootView.findViewById(baseViewInter.getBaseBarLeftId());
		tvBaseBarRight = (TextView) rootView.findViewById(baseViewInter.getBaseBarRightId());
		
		baseViewInter.initBaseView(rootView);
		ButterKnife.bind(this, rootView);
	}

	/** 初始化标题按按钮 */
	private void setBaseTitle(String title) {
		tvBaseBarTitle.setText(title);
	}

	/** 初始化右边按钮 */
	private void setBaseRight(Drawable drawable, String rightText) {
		tvBaseBarRight.setText(rightText);

		if (drawable != null) {
			tvBaseBarRight.setCompoundDrawablesWithIntrinsicBounds(drawable,
					null, null, null);
		}
	}

	/**
	 * 初始化标题栏
	 * 
	 * @param titleId
	 */
	public void initBaseTitleBar(int titleId) {
		initBaseTitleBar(getString(titleId));
	}

	/**
	 * 初始化标题栏
	 */
	public void initBaseTitleBar(String title) {
		setBaseTitle(title);
	}

	/**
	 * 初始化标题栏
	 */
	protected void initBaseTitleBar(String title, String rightText) {
		setBaseTitle(title);
		setBaseRight(null, rightText);
	}

	/**
	 * 初始化标题栏
	 */
	protected void initBaseTitleBar(String title, String rightText,
			int rightResId) {
		setBaseTitle(title);
		setBaseRight(getResources().getDrawable(rightResId), rightText);
	}
}
