package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.processor.Module_courseAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.ExpandHelper;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
public class DetailOutlineNewHolder extends BaseMultipleTypeViewHolder<List<PublicCourseBean>> {

    public final BaseMultipleTypeRvAdapter outlineAdapter;

    public DetailOutlineNewHolder(ViewGroup parent) {
        super(parent);
        RecyclerView recyclerView = (RecyclerView) getView(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        outlineAdapter = Module_courseAdapterHelper.getOutlineAdapter(getContext());
        recyclerView.setAdapter(outlineAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_outline;
    }

    public void setTitleByCourseType(int courseType) {
        setText(R.id.tv_title, getContext().getString(ConstsCouseType.isSystem(courseType) ? R.string.course_detail_public_course_outline : R.string.course_detail_title_outline));
    }

    @Override
    public void bindData(List<PublicCourseBean> model, int position, BaseRecyclerAdapter adapter) {
        if (model == null || model.size() <= 0) {
            return;
        }
        //为了刷新
        outlineAdapter.clear();
        if (CourseHelper.needAddOnlySectionTag(model)) {
            //标记纯section列表
            outlineAdapter.setTag(true);
        } else if (model.get(0) instanceof PublicCourseBean) {

//            ((RecyclerView) getView(R.id.rv)).addItemDecoration(SpaceItemDecoration.create().setIncludeEdge(true).setIncludeFirst(false).setSpace(12));
            //有阴影去掉4个dp
//            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getView(R.id.rv).getLayoutParams();
//            layoutParams.leftMargin = DensityUtil.dip2px(11);
//            layoutParams.rightMargin = DensityUtil.dip2px(11);
//            getView(R.id.rv).setLayoutParams(layoutParams);
        }
        outlineAdapter.addAll(model, true);
        if (CourseHelper.isChapterFirst(model)) {
            ExpandHelper.expandOrCollapseTree(outlineAdapter, 0);
        }
    }
}
