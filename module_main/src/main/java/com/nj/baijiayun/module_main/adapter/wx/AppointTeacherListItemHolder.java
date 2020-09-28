package com.nj.baijiayun.module_main.adapter.wx;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.AppointTeacherListWrapperBean;
import com.nj.baijiayun.module_main.helper.BeanSpaceItemDecoration;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2020/4/10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter
 * @describe
 */
@AdapterCreate
public class AppointTeacherListItemHolder extends BaseMultipleTypeViewHolder<AppointTeacherListWrapperBean> {

    private final BaseMultipleTypeRvAdapter defaultAdapter;

    public AppointTeacherListItemHolder(ViewGroup parent) {
        super(parent);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(RecyclerView.HORIZONTAL);
        ((RecyclerView) getView(R.id.rv)).setLayoutManager(layout);
        defaultAdapter = Module_mainAdapterHelper.getAppoint_teacherAdapter(getContext());
        BeanSpaceItemDecoration decor = new BeanSpaceItemDecoration(defaultAdapter);
        decor.addMarginEdge(PublicTeacherBean.class,new BeanSpaceItemDecoration.EdgeBean().setLeftMargin(14).setPreLeftMargin(18).setNextRightMargin(18));
        ((RecyclerView) getView(R.id.rv)).addItemDecoration(decor);

        ((RecyclerView) getView(R.id.rv)).setAdapter(defaultAdapter);
    }


    @Override
    public int bindLayout() {
        return R.layout.main_item_appoint_teacher_list;
    }

    @Override
    public void bindData(AppointTeacherListWrapperBean model, int position, BaseRecyclerAdapter adapter) {
        defaultAdapter.addAll(model.getDatas());
    }
}
