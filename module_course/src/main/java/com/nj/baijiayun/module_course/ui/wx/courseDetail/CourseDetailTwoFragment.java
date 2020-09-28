package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.nj.baijiayun.basic.ui.BaseFragment;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailCommentHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailOutlineHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailOutlineNewHolder;
import com.nj.baijiayun.module_course.bean.wx.AssembleCourseBean;
import com.nj.baijiayun.module_course.bean.wx.AssembleJoinInfoBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.bean.PublicDistributionBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;
import com.nj.baijiayun.processor.Module_courseAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.ExpandHelper;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;
import com.nj.baijiayun.refresh.recycleview.extend.HeaderAndFooterRecyclerViewAdapter;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Kai
 * @date 2020/8/11
 * @time 14:17
 */
public class CourseDetailTwoFragment extends BaseAppFragment {
    private RecyclerView recyclerView;
    public BaseMultipleTypeRvAdapter outlineAdapter;
    private List<PublicCourseBean> model;
    @Override
    protected void initSomethingAfterBindView() {

    }

    @Override
    protected int bindContentViewLayoutId() {
        return 0;
    }

    @Override
    protected int bindLayout() {
        return R.layout.course_fragment_detail_new;
    }

    public static CourseDetailTwoFragment getIntet(List<Object> type) {
        CourseDetailTwoFragment courseDetailFragment = new CourseDetailTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", (Serializable) type);
        courseDetailFragment.setArguments(bundle);
        return courseDetailFragment;
    }

    @Override
    protected void initView(View mContextView) {
        Bundle bundle = getArguments();
        model = (List<PublicCourseBean>) bundle.getSerializable("type");
        recyclerView = mContextView.findViewById(R.id.course_detail_below_rv);
        Log.e("转过来的：",model.size()+"");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        outlineAdapter = Module_courseAdapterHelper.getOutlineAdapter(getContext());
        recyclerView.setAdapter(outlineAdapter);
        recyclerView.setNestedScrollingEnabled(false);
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
        outlineAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, View view, Object item) {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void registerListener() {

    }

    @Override
    public void processLogic() {

    }


    @Override
    public void showLoadView() {

    }

    @Override
    public void showNoNetWorkView() {

    }

    @Override
    public void showNoDataView() {

    }

    @Override
    public void showErrorDataView() {

    }

    @Override
    public void showContentView() {

    }

    @Override
    public void showNoLogin() {

    }

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public void showToastMsg(int strIds) {

    }

    @Override
    public void showLoadV(String msg) {

    }

    @Override
    public void showLoadV() {

    }

    @Override
    public void closeLoadV() {

    }


}
