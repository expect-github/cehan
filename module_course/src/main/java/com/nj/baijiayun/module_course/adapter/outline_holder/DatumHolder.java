package com.nj.baijiayun.module_course.adapter.outline_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.bean.wx.DatumBean;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.outline_holder
 * @describe 节的点击事件在sectionHolder 里面
 */
@AdapterCreate(group = "outLine")
public class DatumHolder extends BaseMultipleTypeViewHolder<DatumBean> {


    public DatumHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.course_item_outline_datum;

    }

    @Override
    public void bindData(DatumBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv_datum_name,model.getFileName());

    }

}
