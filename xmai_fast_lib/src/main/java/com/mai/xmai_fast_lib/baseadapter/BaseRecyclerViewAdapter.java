package com.mai.xmai_fast_lib.baseadapter;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mai.xmai_fast_lib.R;
import com.mai.xmai_fast_lib.baseadapter.listener.RBaseAnimator;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemClickListener;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnItemLongClickListener;
import com.mai.xmai_fast_lib.baseadapter.listener.ROnLoadingMoreListener;

import java.util.Date;
import java.util.List;

/**
 * 1、加头部和底部 16/5/23
 * 2、加上拉加载更多(仅支持LinearLayout) 16/5/24
 * 3、加动画效果(预设一些动画效果，开发者可以另外加一些动画效果) 16/6/01
 * 4、加重新加载
 * Created by mai on 16/4/29.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private View mHeaderView; //头部
    private View mFooterView; //底部
    private View mLoadingMoreView; //加载更多
    private final int VIEWTYPE_HEADER_VIEW = 0X00001001;
    private final int VIEWTYPE_FOOTER_VIEW = 0X00001002;
    private final int VIEWTYPE_DEFAULT_VIEW = 0X00001003;
    private final int VIEWTYPE_LOADMORE_VIEW = 0X0001004;


    ROnItemClickListener itemClickListener;

    ROnItemLongClickListener itemLongClickListener;

    ROnLoadingMoreListener onLoadingMoreListener;

    private RBaseAnimator mAnimator;
    private int mLastAnimatorPosition = -1;
    private boolean isRecycleAnimator = false;

    private boolean isLoadMoreComplete = false;
    private boolean isLoading = false;
    private boolean isTimeOut = false;

    List<T> mData;

    public BaseRecyclerViewAdapter(List<T> mData) {
        this.mData = mData;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
        mLastAnimatorPosition = -1;
    }

    public List<T> getDatas() {
        return mData;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder baseRecyclerViewHolder = null;
        switch (viewType) {
            case VIEWTYPE_HEADER_VIEW:
                baseRecyclerViewHolder = new BaseRecyclerViewHolder(mHeaderView, viewType);
                break;
            case VIEWTYPE_FOOTER_VIEW:
                baseRecyclerViewHolder = new BaseRecyclerViewHolder(mFooterView, viewType);
                break;
            case VIEWTYPE_LOADMORE_VIEW:
                if (mLoadingMoreView == null)
                    mLoadingMoreView = inflateView(bindLoadingView(), parent);

                baseRecyclerViewHolder = new BaseRecyclerViewHolder(mLoadingMoreView, viewType);
                initReloadClickListener(baseRecyclerViewHolder);

                break;
            default:
                baseRecyclerViewHolder = new BaseRecyclerViewHolder(inflateView(viewType, parent), VIEWTYPE_DEFAULT_VIEW);
                initViewClickListener(baseRecyclerViewHolder);
                break;
        }
        return baseRecyclerViewHolder;
    }

    protected void initViewClickListener(BaseRecyclerViewHolder baseRecyclerViewHolder) {
        if (itemClickListener != null)
            baseRecyclerViewHolder.setOnItemClickListener(itemClickListener);
        if (itemLongClickListener != null) {
            baseRecyclerViewHolder.setOnItemLongClickListener(itemLongClickListener);
        }
    }

    protected void initReloadClickListener(BaseRecyclerViewHolder baseRecyclerViewHolder) {
            baseRecyclerViewHolder.setOnItemClickListener(new ROnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (isTimeOut) {
                        isTimeOut = false;
                        notifyDataSet();
                    }
                }
            });
    }


    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {

        switch (holder.getViewType()) {
            case VIEWTYPE_HEADER_VIEW: //头部
                break;
            case VIEWTYPE_FOOTER_VIEW: //底部
                break;
            case VIEWTYPE_LOADMORE_VIEW: //加载更多
                if (!isLoadMoreComplete) { //还有更多
                    if (!isLoading) { //没有在加载
                        if(isTimeOut){ //超时了
                            initReloadView(holder.getBaseViewHolderImpl());
                        } else { //继续加载
                            isLoading = true;
                            onLoadingMoreListener.onLoadingMore();
                            initLoadingView(holder.getBaseViewHolderImpl());
                        }
                    }
                } else { //没有更多
                    initFinishLoadView(holder.getBaseViewHolderImpl());
                }
                break;
            case VIEWTYPE_DEFAULT_VIEW:
                holder.setmRealPosition(position - getHearderCount());
                T data;
                if (mData == null || mData.size() <= position - getHearderCount()) {
                    data = null;
                } else {
                    data = mData.get(position - getHearderCount());
                }
                initView(data, holder.getBaseViewHolderImpl());
                addAnimator(holder);
                break;
        }
    }

    protected void addAnimator(BaseRecyclerViewHolder holder) {
        if (isAnimator()) { //加动画效果
            if (isRecycleAnimator || holder.getRealPosition() > mLastAnimatorPosition) {
                mAnimator.getAnimator(holder.itemView).start();
                mLastAnimatorPosition = holder.getRealPosition();
            }
        }
    }

    @Override
    public int getItemCount() {
        int dataCount = (mData == null ? 0 : mData.size());
        return dataCount + getHearderCount() + getFooterCount() + (isLoadMore() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader() && position == 0) {
            return VIEWTYPE_HEADER_VIEW;
        } else if (hasFooter() && position == mData.size() + getHearderCount()) {
            return VIEWTYPE_FOOTER_VIEW;
        } else if (isLoadMore() && position == mData.size() + getHearderCount() + getFooterCount()) {
            return VIEWTYPE_LOADMORE_VIEW;
        }
        return bindLayoutId(position - getHearderCount());
    }


    public void addDatas(List<T> newDatas) {
        mData.addAll(newDatas);
        isLoading = false;
        notifyDataSetChanged();
    }

    public void removeDatas(List<T> removeDatas) {
        mData.removeAll(removeDatas);
        isLoading = false;
        notifyDataSetChanged();
    }

    public void notifyDataSet() {
        isLoading = false;
        notifyDataSetChanged();
    }

    private boolean isLoadMore() {
        return onLoadingMoreListener != null;
    }


    protected void initLoadingView(BaseViewHolderImpl viewHolder) {
        viewHolder.setVisibility(R.id.loading_view, View.VISIBLE)
                .setVisibility(R.id.tv_reload, View.GONE)
                .setVisibility(R.id.tv_complete, View.GONE);
    }

    protected void initFinishLoadView(BaseViewHolderImpl viewHolder) {
        viewHolder.setVisibility(R.id.loading_view, View.GONE)
                .setVisibility(R.id.tv_complete, View.VISIBLE)
                .setVisibility(R.id.tv_reload, View.GONE);
    }

    protected void initReloadView(BaseViewHolderImpl viewHolder){
        viewHolder.setVisibility(R.id.loading_view, View.GONE)
                .setVisibility(R.id.tv_complete, View.GONE)
                .setVisibility(R.id.tv_reload, View.VISIBLE);;
    }

    public void addHeaderView(View view) {
        mHeaderView = view;
        if (mHeaderView != null)
            setLayoutParams(mHeaderView);
    }

    public void addFooterView(View view) {
        mFooterView = view;
        if (mFooterView != null)
            setLayoutParams(mFooterView);
    }

    private View inflateView(int resouceId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resouceId, parent, false);
    }

    private void setLayoutParams(View view) {
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private int getHearderCount() {
        return hasHeader() ? 1 : 0;
    }

    private int getFooterCount() {
        return hasFooter() ? 1 : 0;
    }

    public boolean hasHeader() {
        return mHeaderView != null;
    }

    public boolean hasFooter() {
        return mFooterView != null;
    }

    /**
     * 加载更多监听器
     *
     * @param onLoadingMoreListener
     */
    public void setOnLoadingMoreListener(ROnLoadingMoreListener onLoadingMoreListener) {
        this.onLoadingMoreListener = onLoadingMoreListener;
    }

    /**
     * 设置item短按监听器
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(ROnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 设置item长按监听器
     *
     * @param itemLongClickListener
     */
    public void setOnItemLongClickListener(ROnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    /**
     * 绑定相对应的item界面
     *
     */
    protected abstract int bindLayoutId(int position);

    /**
     * 将ViewHolder的控件跟内容绑定在一起
     *
     * @param viewHolder
     */
    protected abstract void initView(T data, BaseViewHolderImpl viewHolder);

    /**
     * 加载更多的界面
     *
     */
    protected int bindLoadingView() {
        return R.layout.default_loading;
    }

    /**
     * 设置是否完成加载更多
     *
     * @param loadMoreComplete
     */
    public void setLoadMoreComplete(boolean loadMoreComplete) {
        isLoadMoreComplete = loadMoreComplete;
//        isLoading = false;
        notifyDataSetChanged();
    }

    public void setTimeOut(boolean isTimeOut) {
        this.isTimeOut = isTimeOut;
    }

    public boolean isAnimator() {
        return mAnimator != null;
    }

    /**
     * 设置动画效果
     *
     * @param animator
     */
    public void setAnimator(RBaseAnimator animator) {
        this.mAnimator = animator;
    }

    /**
     * 设置是否重复动画
     *
     * @param recycleAnimator
     */
    public void setRecycleAnimator(boolean recycleAnimator) {
        isRecycleAnimator = recycleAnimator;
    }
}
