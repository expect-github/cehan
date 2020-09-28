package com.nj.baijiayun.module_course.adapter.holder;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

@AdapterCreate(group = "search")
public class SearchRelativeHolder extends BaseMultipleTypeViewHolder<String> {
    public SearchRelativeHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.course_item_course_search;
    }

    @Override
    public void bindData(String model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.textview, model);
    }
}
