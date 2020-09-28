package com.nj.baijiayun.module_main.adapter;

import android.view.View;
import android.widget.FrameLayout;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.widget.dropdownmenu.AbstractMenuAdapter;
import com.nj.baijiayun.module_common.widget.tabs.SingleListModel;
import com.nj.baijiayun.module_common.widget.tabs.SingleListView;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.CourseTypeBean;
import com.nj.baijiayun.module_public.bean.PublicAttrClassifyBean;
import com.nj.baijiayun.module_public.widget.filter.FilterChildBean;
import com.nj.baijiayun.module_public.widget.filter.FilterNewView;
import com.nj.baijiayun.module_public.widget.filter.FilterParentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter
 * @describe
 */
public class DropDownAdapter extends AbstractMenuAdapter {

    private CourseFilterResult courseFilterResult = new CourseFilterResult();


    private static final String[] TITLES = new String[]{"分类", "排序", "筛选"};
    private String[] ranks = new String[]{"综合排序", "最新", "最热", "价格从高到低", "价格从低到高"};
    private int[] rankIds = new int[]{0, 1, 2, 4, 3};

    private FilterNewView mAttrView;
    private FilterNewView mCourseTypeView;
    private SingleListView mOrderView;


    @Override
    public int getMenuCount() {
        return 3;
    }

    @Override
    public String getMenuTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getBottomMargin(int position) {
        return 0;
    }


    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);
        switch (position) {
            case 0:
                view = getAttrView(parentContainer);
                break;
            case 1:
                view = getSortView(parentContainer);
                break;
            case 2:
                view = getCourseTypeView(parentContainer);
                break;
            default:
                break;
        }
        view.setBackgroundResource(R.drawable.public_bg_white_round_10_bottom);
        return view;
    }

    private View getCourseTypeView(FrameLayout parentContainer) {
        FilterNewView view = new FilterNewView(parentContainer.getContext());
        this.mCourseTypeView = view;

        view.setPadding(0, DensityUtil.dip2px(19), 0, 0);
        view.setNeedBottomConfirm(false);
        view.setCallBack(data -> {

            int selectId = 0;
            if (data != null && data.size() > 0) {
                selectId = data.get(0).getSelectId();
            }
            if (selectId == 99) {
                courseFilterResult.setVip();
                courseFilterResult.setCourseType(0);

            } else {
                courseFilterResult.setNotVip();
                courseFilterResult.setCourseType(selectId);
            }
            getOnFilterDoneListener().onFilterDone(courseFilterResult);
        });
        return view;
    }

    private View getSortView(FrameLayout parentContainer) {

        mOrderView = new SingleListView(parentContainer.getContext());
        List<SingleListModel> datas = new ArrayList<>();
        for (String rank : ranks) {
            SingleListModel singleListModel = new SingleListModel(rank);
            datas.add(singleListModel);
        }
        mOrderView.getAdapter().addAll(datas);

        mOrderView.getAdapter().setOnItemClickListener((holder, position, view1, item) -> {
            mOrderView.selectPosition(position);
            courseFilterResult.setOrderBy(rankIds[position]);
            getOnFilterDoneListener().onFilterDone(courseFilterResult);
        });
        return mOrderView;
    }

    private View getAttrView(FrameLayout parentContainer) {
        FilterNewView view = new FilterNewView(parentContainer.getContext());
        this.mAttrView = view;
        view.setCallBack(data -> {

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                if (i > 0) {
                    result.append(",");
                }
                result.append(data.get(i).getSelectId());
            }

            courseFilterResult.setAttrValId(result.toString());
            getOnFilterDoneListener().onFilterDone(courseFilterResult);
        });
        return view;
    }

    public void addCategoryData(List<CourseTypeBean> data) {
        if (data == null) {
            return;
        }
        FilterParentBean filterParentBean = new FilterParentBean();
        for (int i = 0; i < data.size(); i++) {
            FilterChildBean filterChildBean = new FilterChildBean();
            if (data.get(i).isShow()) {
                filterChildBean.setId(data.get(i).getId());
                filterChildBean.setContent(data.get(i).getName());
                filterParentBean.getChildBeans().add(filterChildBean);

            }
        }
        ArrayList<FilterParentBean> objects = new ArrayList<>();
        objects.add(filterParentBean);
        mCourseTypeView.setData(objects);

    }


    public void addAttrData(List<PublicAttrClassifyBean> data) {
        List<FilterParentBean> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            FilterParentBean filterParentBean = new FilterParentBean();
            filterParentBean.setContent(data.get(i).getName());
            List<PublicAttrClassifyBean.Child> child = data.get(i).getChild();
            if (child == null) {
                continue;
            }
            for (int j = 0; j < child.size(); j++) {
                FilterChildBean filterChildBean = new FilterChildBean();
                filterChildBean.setContent(child.get(j).getName());
                filterChildBean.setId(child.get(j).getId());
                filterParentBean.getChildBeans().add(filterChildBean);
            }
            result.add(filterParentBean);
        }
        mAttrView.setData(result);

    }

    @Override
    public void setOnTitleChangeListener(OnTitleChangeListener onTitleChange) {

    }

    public void resetAll() {
        if (mAttrView != null) {
            mAttrView.reset();
        }
        if (mCourseTypeView != null) {
            mCourseTypeView.reset();
        }
        if (mOrderView != null) {
            mOrderView.reset();
        }


    }


}
