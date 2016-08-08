package com.mai.xmai.baseadapter;

import com.mai.xmai.R;
import com.mai.xmai_fast_lib.baseadapter.BaseRecyclerViewAdapter;
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolderImpl;

import java.util.List;

/**
 * Created by mai on 16/5/9.
 */
public class TestBaseRecycleViewAdapter extends BaseRecyclerViewAdapter<String> {
    public TestBaseRecycleViewAdapter(List<String> mData) {
        super(mData);
    }

    @Override
    protected int bindLayoutId(int position) {
        if (position % 2 == 0) {
            return R.layout.item_test1;
        }
        return R.layout.item_test2;
    }

    @Override
    protected void initView(String data, BaseViewHolderImpl viewHolder) {
        if (viewHolder.getPosition() % 2 == 0) {
            viewHolder.setText(R.id.tv_test, data);
        } else {
            viewHolder.setText(R.id.bt_test, data);
        }
    }
}
