package com.nj.baijiayun.module_course.ui.wx.search;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.temple.SearchActivity;
import com.nj.baijiayun.module_course.adapter.CourseSearchAdapter;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.RefreshListDataHelper;
import com.nj.baijiayun.processor.Module_publicDefaultAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-08-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx
 * @describe
 */
@Route(path = RouterConstant.PAGE_COURSE_SEARCH)
public class CourseSearchActivity extends SearchActivity<CourseSearchContract.Presenter> {

    public static final String SP_NAME_SEARCH = "wangxaio";
    public static final String SP_SEARCH_HISTORY = "wangxaio";
    private CourseSearchAdapter courseSearchAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        getBackImg().setPadding(0, 0, 0, 0);
        ViewGroup.LayoutParams layoutParams = getBackImg().getLayoutParams();
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) layoutParams).leftMargin = DensityUtil.dip2px(15);
            ((RelativeLayout.LayoutParams) layoutParams).rightMargin = DensityUtil.dip2px(10);
            getBackImg().setLayoutParams(layoutParams);
        }
        getSearchContentRv().addItemDecoration(SpaceItemDecoration.create().setSpace(15));
    }

    @Override
    public boolean needMultipleStatus() {
        return false;
    }

    @Override
    public BaseRecyclerAdapter createRecyclerAdapter() {
        return new Module_publicDefaultAdapter(this);
    }

    @Override
    public BaseRecyclerAdapter createRelativeAdapter() {
        courseSearchAdapter = new CourseSearchAdapter(this);
        return courseSearchAdapter;
    }

    @Override
    protected String getSPFileName() {
        return SP_NAME_SEARCH;
    }

    @Override
    protected String getSPName() {
        return SP_SEARCH_HISTORY;
    }

    @Override
    protected void courseClick(Object o) {
        PublicCourseBean t = (PublicCourseBean) o;
        ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL).withInt("id", t.getId()).navigation();
    }

    @Override
    public void dataSuccess2(List data) {
        courseSearchAdapter.setSearchKeyWord(getSearchStr());

        if (data != null && data.size() > 0) {
            List<String> list = new ArrayList<>();
            for (Object o : data) {
                PublicCourseBean t = (PublicCourseBean) o;
                list.add(t.getTitle());
            }
            getRelativeAdapter().addAll(list, true);
        } else {
            getRelativeAdapter().clear();
        }

    }

    @Override
    protected void registerListener() {
        super.registerListener();
        RefreshListDataHelper.registerCourseHasBuyChange(this, getAdapter());
    }
}


