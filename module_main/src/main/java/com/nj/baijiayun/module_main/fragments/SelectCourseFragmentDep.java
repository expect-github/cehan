package com.nj.baijiayun.module_main.fragments;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.temple.AbstractListFragment;
import com.nj.baijiayun.module_common.widget.dropdownmenu.AbstractMenuAdapter;
import com.nj.baijiayun.module_common.widget.dropdownmenu.DropDownMenu;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.adapter.CourseFilterResult;
import com.nj.baijiayun.module_main.adapter.DropDownAdapter;
import com.nj.baijiayun.module_main.bean.CourseTypeBean;
import com.nj.baijiayun.module_main.mvp.contract.SelectContract;
import com.nj.baijiayun.module_public.bean.PublicAttrClassifyBean;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.RefreshListDataHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.mvp.contract.CourseContract;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * @author chengang
 * @date 2019-07-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class SelectCourseFragmentDep extends AbstractListFragment<CourseContract.Presenter> implements SelectContract.View {

    public static final String SELECT_COURSE_TYPE_TAB = "select_course_type_tab";
    private Toolbar mToolbar;
    private DropDownMenu mDropDownMenu;

    @Inject
    SelectContract.Presenter mSelectPresenter;
    private DropDownAdapter dropDownAdapter;
    private int filterCourseType;
    private boolean needAppBar = true;


    @Override
    public void initParms(Bundle params) {
        super.initParms(params);
        if (params == null) {
            return;
        }
        filterCourseType = params.getInt("courseType");
        needAppBar = params.getBoolean("needAppBar", true);
    }

    @Override
    protected int bindLayout() {
        return R.layout.main_fragment_select_course;
    }

    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        ViewHelper.setViewVisible(mContextView.findViewById(R.id.toolbar), needAppBar);
        ViewHelper.setViewVisible(mContextView.findViewById(R.id.view_status_bar), needAppBar);

    }


    @Override
    public BaseMultipleTypeRvAdapter createRecyclerAdapter() {
        return Module_mainAdapterHelper.getDefaultAdapter(getContext());
    }

    @Override
    protected void onPageItemClick(BaseViewHolder holder, int position, View view, Object item) {

    }

    @Override
    public void processLogic() {

    }

    @Override
    public void registerListener() {
        super.registerListener();
        RefreshListDataHelper.registerCourseHasBuyChange(this, getAdapter());
        LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE, Boolean.class).observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                mPresenter.getList(true);
            }
        });
        LiveDataBus.get().with(SELECT_COURSE_TYPE_TAB, Integer.class).observe(this, integer -> {
            if (integer != null) {
                mPresenter.clearSelect();
                if (dropDownAdapter != null) {
                    dropDownAdapter.resetAll();
                }
                mPresenter.setCourseType(integer);
                mPresenter.getList(true);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        if (filterCourseType != 0) {
            mPresenter.setCourseType(filterCourseType);
        }
        //需要加参数 在这里之前 onLazyInitView 会自动调用list
        super.onLazyInitView(savedInstanceState);

        mToolbar = mContextView.findViewById(R.id.toolbar);
        mDropDownMenu = mContextView.findViewById(R.id.dropDownMenu);
        ToolBarHelper.setToolBarTextCenter(mToolbar, getActivity().getString(R.string.main_course_list));
        dropDownAdapter = new DropDownAdapter();
        mDropDownMenu.setMenuAdapter(dropDownAdapter
                .setOnFilterDoneListener((AbstractMenuAdapter.OnFilterDoneListener<CourseFilterResult>) result -> {
                    mPresenter.setAttrValId(result.getAttrValId());
                    mPresenter.setCourseType(result.getCourseType());
                    mPresenter.setOrderBy(result.getOrderBy());
                    mPresenter.setVip(result.getIsVip());
                    mPresenter.getList(true);
                    mDropDownMenu.close();
                }));
        mDropDownMenu.setStateChangeListener(isOpen -> {
            if (isOpen) {
                mSelectPresenter.getFliter();
            }
        });
        ToolBarHelper.addRightImageView(mToolbar, R.drawable.public_ic_search, v ->
                ARouter.getInstance().build(RouterConstant.PAGE_COURSE_SEARCH).navigation());

        getNxRefreshView().addItemDecoration(SpaceItemDecoration.create().setSpace(10));
        mSelectPresenter.getFliter();
    }

    @Override
    public void takeView() {
        super.takeView();
        mSelectPresenter.takeView(this);
    }

    @Override
    public void dropView() {
        super.dropView();
        mSelectPresenter.dropView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void setFilterCourseType(List<CourseTypeBean> data) {
        dropDownAdapter.addCategoryData(data);
    }

    @Override
    public void setFilterAttrs(List<PublicAttrClassifyBean> data) {
        dropDownAdapter.addAttrData(data);
    }
}
