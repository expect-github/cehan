package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.nj.baijiayun.basic.ui.BaseFragment;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailDescHolder;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;
import com.nj.baijiayun.refresh.recycleview.extend.HeaderAndFooterRecyclerViewAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Kai
 * @date 2020/8/11
 * @time 14:17
 */
public class CourseDetailFragment extends BaseFragment {
    private RecyclerView mRv;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private String url;
    @Override
    protected void initSomethingAfterBindView() {

    }

    public static CourseDetailFragment getIntet(String url) {
        CourseDetailFragment courseDetailFragment = new CourseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        courseDetailFragment.setArguments(bundle);
        return courseDetailFragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.course_fragment_detail;
    }

    @Override
    protected void initView(View mContextView) {
        Bundle bundle = getArguments();
        url = bundle.getString("url");
        mRv = mContextView.findViewById(R.id.course_detail_below_rv);
        adapter = CourseHelper.createDefaultAdapter();
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(adapter);
        mRv.addItemDecoration(SpaceItemDecoration.create().setSpace(10).setHeadItemCount(0).setIncludeEdge(false).setIncludeFirst(false));
        setDescInfo(url);

    }

    @Override
    public void registerListener() {

    }

    @Override
    public void processLogic() {

    }

    /**
     * 课程介绍
     *
     * @param url
     */
    private void setDescInfo(String url) {
        DetailDescHolder descHolder = new DetailDescHolder(mRv);

        //详情
        descHolder.bindData(url, 0, null);
        adapter.addHeaderView(descHolder.getConvertView());
    }

}
