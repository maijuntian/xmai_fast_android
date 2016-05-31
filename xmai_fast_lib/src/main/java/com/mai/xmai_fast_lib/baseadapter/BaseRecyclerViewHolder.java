package com.mai.xmai_fast_lib.baseadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemClickListener;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemLongClickListener;


/**
 * Created by mai on 16/4/29.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    BaseViewHolderImpl baseViewHolderImpl;

    private ROnItemClickListener onItemClickListener;

    private ROnItemLongClickListener onItemLongClickListener;

    private int viewType;

    private int mRealPosition; //真正对应的序号

    public BaseRecyclerViewHolder(View itemView, int viewType) {
        super(itemView);
        baseViewHolderImpl = new BaseViewHolderImpl(itemView);
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setOnItemClickListener(ROnItemClickListener onItemClickListener) {
        itemView.setOnClickListener(this);
        this.onItemClickListener = onItemClickListener;
    }

    public void setmRealPosition(int mRealPosition) {
        this.mRealPosition = mRealPosition;
        baseViewHolderImpl.setPosition(mRealPosition);
    }

    public void setOnItemLongClickListener(ROnItemLongClickListener onItemLongClickListener) {
        itemView.setOnLongClickListener(this);
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public BaseViewHolderImpl getBaseViewHolderImpl() {
        return baseViewHolderImpl;
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(v, mRealPosition);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(onItemLongClickListener != null){
            onItemLongClickListener.onItemLongClick(v, mRealPosition);
        }
        return true;
    }
}
