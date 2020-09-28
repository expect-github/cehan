package com.nj.baijiayun.module_main.adapter.wx;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.PublicOpenCourseBean;
import com.nj.baijiayun.module_main.bean.PublicOpenListWrapperBean;
import com.nj.baijiayun.module_main.helper.BeanSpaceItemDecoration;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx
 * @describe
 */
@AdapterCreate
public class PublicOpenCourseListHolder extends BaseMultipleTypeViewHolder<PublicOpenListWrapperBean> {

    private final BaseMultipleTypeRvAdapter defaultAdapter;

    public PublicOpenCourseListHolder(ViewGroup parent) {
        super(parent);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(RecyclerView.HORIZONTAL);
        ((RecyclerView) getView(R.id.rv)).setLayoutManager(layout);


        defaultAdapter = Module_mainAdapterHelper.getOpen_courseAdapter(getContext());
        BeanSpaceItemDecoration decor = new BeanSpaceItemDecoration(defaultAdapter);
        decor.addMarginEdge(PublicOpenCourseBean.class,new BeanSpaceItemDecoration.EdgeBean().setLeftMargin(12).setPreLeftMargin(18).setNextRightMargin(18));
        ((RecyclerView) getView(R.id.rv)).addItemDecoration(decor);

        ((RecyclerView) getView(R.id.rv)).setAdapter(defaultAdapter);
    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_public_open_course_list;
    }

    @Override
    public void bindData(PublicOpenListWrapperBean model, int position, BaseRecyclerAdapter adapter) {
        defaultAdapter.clear();
        defaultAdapter.addAll(model.getDatas());

    }
}
