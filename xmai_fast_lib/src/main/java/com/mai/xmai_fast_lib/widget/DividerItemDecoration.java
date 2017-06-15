package com.mai.xmai_fast_lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;


/**
 * Created by mai on 16/8/22.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    /*
     * RecyclerView的布局方向，默认先赋值
     * 为纵向布局
     * RecyclerView 布局可横向，也可纵向
     * 横向和纵向对应的分割想画法不一样
     * */
    private int mOrientation = LinearLayoutManager.VERTICAL;

    /**
     * item之间分割线的size，默认为0.5
     */
    private float mItemSize;

    /**
     * 绘制item分割线的画笔，和设置其属性
     * 来绘制个性分割线
     */
    private Paint mPaint;

    public DividerItemDecoration(Context context, int colorId) {
        this(context, colorId, 0.5f, LinearLayoutManager.VERTICAL);
    }

    public DividerItemDecoration(Context context, int colorId, float itemSize) {
        this(context, colorId, itemSize, LinearLayoutManager.VERTICAL);
    }

    public DividerItemDecoration(Context context, int color, float itemSize, int orientation) {
        this.mOrientation = orientation;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
         /*设置填充*/
        mPaint.setStyle(Paint.Style.FILL);
        mItemSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemSize, context.getResources().getDisplayMetrics());
    }

    public DividerItemDecoration(Context context) {
        this(context, Color.parseColor("#cccccc"), 0.5f);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    /**
     * 绘制纵向 item_person 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize - 1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final float bottom = top + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 绘制横向 item_person 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize - 1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final float right = left + mItemSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 设置item分割线的size
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, (int) mItemSize);
        } else {
            outRect.set(0, 0, (int) mItemSize, 0);
        }
    }
}
