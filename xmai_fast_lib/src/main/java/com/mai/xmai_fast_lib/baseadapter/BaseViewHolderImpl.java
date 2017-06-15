package com.mai.xmai_fast_lib.baseadapter;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ViewHolder实现类
 * Created by mai on 16/4/29.
 */
public class BaseViewHolderImpl {

    protected SparseArray<View> mCahceViews = new SparseArray<View>();
    protected View mView;
    private int position;

    public View getView() {
        return mView;
    }

    public BaseViewHolderImpl(View mView){
        this.mView = mView;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }


    public <T extends View> T findViewById(int resId) {
        View view = mCahceViews.get(resId);
        if (view == null) {
            view = mView.findViewById(resId);
            mCahceViews.put(resId, view);
        }
        return (T) view;
    }


    /**
     * 设置文字
     *
     * @param resId
     * @param text
     */
    public BaseViewHolderImpl setText(int resId, String text) {
        if (text == null) {
            text = "";
        }
        ((TextView) findViewById(resId)).setText(text);
        return this;
    }

    /**
     * 设置可见度（加上判断条件）
     *
     * @param resIds
     * @param visibility visible为false的时候设置不显示类型
     * @param isVisibled 是否显示
     */
    public BaseViewHolderImpl setVisibility(int visibility, boolean isVisibled, int... resIds) {
        for (int i = 0; i < resIds.length; i++) {
            if (isVisibled) {
                findViewById(resIds[i]).setVisibility(View.VISIBLE);
            } else {
                findViewById(resIds[i]).setVisibility(visibility);
            }
        }
        return this;
    }

    /**
     * 设置文字图片
     */
    public BaseViewHolderImpl setCompoundDrawablesWithIntrinsicBounds(int resId, int left, int top, int right, int bottom) {
        ((TextView) findViewById(resId)).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        ;
        return this;
    }

    /**
     * 设置tag
     * @param resId
     * @param tag
     */
    public BaseViewHolderImpl setTag(int resId, Object tag) {
        findViewById(resId).setTag(tag);
        return this;
    }

    /**
     * 设置图片
     * @param resId
     * @param drawable
     */
    public BaseViewHolderImpl setImageDrawable(int resId, Drawable drawable) {
        ((ImageView) findViewById(resId)).setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置背景
     *
     * @param viewId
     * @param drawable
     */
    public BaseViewHolderImpl setBackgroundDrawable(int viewId, Drawable drawable) {
        findViewById(viewId).setBackgroundDrawable(drawable);
        return this;
    }

    /**
     * 设置是否选中
     * @param resId
     * @param checked
     */
    public BaseViewHolderImpl setChecked(int resId, boolean checked) {
        ((CompoundButton) findViewById(resId)).setChecked(checked);
        return this;
    }

    /**
     * 设置可见度
     *
     * @param resId
     * @param visibility visible为false的时候设置不显示类型
     * @param isVisibled    是否显示
     */
    public BaseViewHolderImpl setVisibility(int resId, int visibility, boolean isVisibled) {
        if (isVisibled) {
            findViewById(resId).setVisibility(View.VISIBLE);
        } else {
            findViewById(resId).setVisibility(visibility);
        }
        return this;
    }

    /**
     * 设置可见度
     * @param resId 控件id
     * @param visibility  是否显示
     */
    public BaseViewHolderImpl setVisibility(int resId, int visibility) {
        findViewById(resId).setVisibility(visibility);
        return this;
    }

    public BaseViewHolderImpl setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener, int... resIds) {
        for (int i = 0; i < resIds.length; i++) {
            ((CompoundButton) findViewById(resIds[i])).setOnCheckedChangeListener(listener);
        }
        return this;
    }

    /**
     * 设置监听器
     *
     * @param onClickListener
     * @param resIds
     */
    public BaseViewHolderImpl setOnClickListener(View.OnClickListener onClickListener, int... resIds) {
        for (int i = 0; i < resIds.length; i++) {
            findViewById(resIds[i]).setOnClickListener(onClickListener);
        }
        return this;
    }
}
