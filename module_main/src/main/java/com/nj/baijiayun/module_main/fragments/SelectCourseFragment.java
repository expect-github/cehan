package com.nj.baijiayun.module_main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.widget.attrtab.ITab;
import com.nj.baijiayun.basic.widget.attrtab.TabIndicatorView;
import com.nj.baijiayun.module_common.temple.AbstractListFragment;
import com.nj.baijiayun.module_common.widget.tabs.SingleListModel;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.CourseTypeBean;
import com.nj.baijiayun.module_main.mvp.contract.SelectContract;
import com.nj.baijiayun.module_public.adapter.CommonAdapter;
import com.nj.baijiayun.module_public.adapter.MultiItemTypeAdapter;
import com.nj.baijiayun.module_public.adapter.ViewHolder;
import com.nj.baijiayun.module_public.bean.PublicAttrClassifyBean;
import com.nj.baijiayun.module_public.consts.ConstsComment;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.RefreshListDataHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.mvp.contract.CourseContract;
import com.nj.baijiayun.module_public.widget.BaseDialog;
import com.nj.baijiayun.module_public.widget.GridItemDecoration;
import com.nj.baijiayun.module_public.widget.ListItemDecoration;
import com.nj.baijiayun.module_public.widget.filter.FilterDataChangeHelper;
import com.nj.baijiayun.module_public.widget.filter_tabs.MultiAttrTab;
import com.nj.baijiayun.module_public.widget.filter_tabs.SingleListTab;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-07-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class SelectCourseFragment extends AbstractListFragment<CourseContract.Presenter> implements SelectContract.View {

    public static final String SELECT_COURSE_TYPE_TAB = "select_course_type_tab";
    @Inject
    SelectContract.Presenter mSelectPresenter;
    private int filterCourseType;
    private boolean needAppBar = true;
    private TabIndicatorView mTabIndicatorView;
    private FrameLayout mFrameLayout;
    private View mViewSearch;
    private MultiAttrTab mTabCategory;
    private SingleListTab mTabSort;
    private SingleListTab mTabCourseType;
    private int[] ranks = new int[]{
            R.string.public_sort_common
            , R.string.public_sort_new
            , R.string.public_sort_hot
            , R.string.public_sort_price_asc
            , R.string.public_sort_price_desc};
    private int[] rankIds = new int[]{0, 1, 2, 3, 4};
    private List<CourseTypeBean> courseTypeBeanList;
    private BaseDialog classfiyDialog;
    private BaseDialog multipleDialog;
    private String classifyId;
    private int multipleIndex = -1;
    CommonAdapter<PublicAttrClassifyBean> commonAdapter;
    List<PublicAttrClassifyBean> classifyBeans;

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
        ViewHelper.setViewVisible(mContextView.findViewById(R.id.cl_tool_bar), needAppBar);
        ViewHelper.setViewVisible(mContextView.findViewById(R.id.view_status_bar), needAppBar);
        classifyBeans = new ArrayList<>();
        initSomeViews();
        setOncik();
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
                if (mTabCourseType != null) {
                    mTabCourseType.reset();
                }
                if (mTabSort != null) {
                    mTabSort.reset();
                }
                if (mTabCategory != null) {
                    mTabCategory.reset();
                }
                mPresenter.setCourseType(integer);
                mPresenter.getList(true);
            }
        });
        mViewSearch.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_SEARCH).navigation();
        });
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        if (filterCourseType != 0) {
            mPresenter.setCourseType(filterCourseType);
        }
        //需要加参数 在这里之前 onLazyInitView 会自动调用list
        super.onLazyInitView(savedInstanceState);
        initTabTitleView();
        addTabToIndicator();
        mSelectPresenter.getFliter();


    }

    /**
     * 单击事件
     */
    private void setOncik() {
        /**
         * 分类
         */
        mContextView.findViewById(R.id.select_course_classify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classifyBeans.size() > 0) {
                    classfiyDialog.show();
                }
            }
        });
        /**
         * 综合
         */
        mContextView.findViewById(R.id.select_course_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleDialog.show();
            }
        });
    }

    /**
     * 分类
     */
    private void setClassfiyDialog() {
        classfiyDialog = new BaseDialog(getContext()) {
            @Override
            public int getLayoutResId() {
                return R.layout.dialog_classfiy;
            }
        };
        RecyclerView recyclerView = classfiyDialog.findViewById(R.id.dialog_classfiy_recycler);
        classfiyDialog.findViewById(R.id.dialog_classfiy_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classfiyDialog.dismiss();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ListItemDecoration(getContext(), 0, R.color.white));
        commonAdapter = new CommonAdapter<PublicAttrClassifyBean>(getContext(), classifyBeans, R.layout.item_list_classfiy) {
            @Override
            public void convert(ViewHolder holder, PublicAttrClassifyBean item, int position) {
                holder.setText(R.id.item_list_classify_name, item.getName());
                RecyclerView recyclerView1 = holder.getView(R.id.item_list_classify_recycler);
                recyclerView1.setLayoutManager(new GridLayoutManager(mContext, 3));
                recyclerView1.addItemDecoration(new GridItemDecoration(mContext, 12, R.color.white));
                CommonAdapter<PublicAttrClassifyBean.Child> childCommonAdapter =
                        new CommonAdapter<PublicAttrClassifyBean.Child>(mContext, item.getChild(), R.layout.item_list_classfiy_new) {
                            @Override
                            public void convert(ViewHolder holder, PublicAttrClassifyBean.Child item, int position) {
                                TextView tv_name = holder.getView(R.id.item_list_classify_name);
                                tv_name.setText(item.getName());
                                if (item.isType()) {
                                    tv_name.setBackgroundResource(R.drawable.classfiy_list_nor_bg);
                                    tv_name.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                                } else {
                                    tv_name.setBackgroundResource(R.drawable.classfiy_list_sel_bg);
                                    tv_name.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                                }
                            }
                        };
                recyclerView1.setAdapter(childCommonAdapter);
                childCommonAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int index) {
                        classifyId = childCommonAdapter.getItem(index).getId() + "";
                        commonAdapter.getItem(position).getChild().get(index).setType(true);
                        childCommonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
        /**
         * 重置
         */
        classfiyDialog.findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < commonAdapter.getItemCount(); i++) {
                    for (int j = 0; j < commonAdapter.getItem(i).getChild().size(); j++) {
                        commonAdapter.getItem(i).getChild().get(j).setType(false);
                    }
                }
                commonAdapter.notifyDataSetChanged();
                classfiyDialog.dismiss();
                mPresenter.setAttrValId("");
                mPresenter.getList();
            }
        });
        /**
         * 确认
         */
        classfiyDialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer delete_id = new StringBuffer();
                for (int i = 0; i < commonAdapter.getItemCount(); i++) {
                    for (int j = 0; j < commonAdapter.getItem(i).getChild().size(); j++) {
                        if (commonAdapter.getItem(i).getChild().get(j).isType()) {
                            if (delete_id.length() > 0) {
                                delete_id.append(",");
                            }
                            delete_id.append(commonAdapter.getItem(i).getChild().get(j).getId());
                        }
                    }
                }
                Log.e("选中的id:",delete_id.toString());
                classfiyDialog.dismiss();
                mPresenter.setAttrValId(delete_id.toString());
                mPresenter.getList();
            }
        });

    }

    /**
     * 综合
     */
    private void setMultipleDialog(List<SingleListModel> result) {
        multipleDialog = new BaseDialog(getContext()) {
            @Override
            public int getLayoutResId() {
                return R.layout.dialog_multiple;
            }
        };
        RecyclerView recyclerView = multipleDialog.findViewById(R.id.dialog_classfiy_recycler);
        multipleDialog.findViewById(R.id.dialog_classfiy_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleDialog.dismiss();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ListItemDecoration(getContext(), 20, R.color.white));
        CommonAdapter<SingleListModel> commonAdapter = new
                CommonAdapter<SingleListModel>(getContext(), result, R.layout.item_list_multiple) {
                    @Override
                    public void convert(ViewHolder holder, SingleListModel item, int position) {
                        TextView tv_name = holder.getView(R.id.dialog_multiple_name);
                        tv_name.setText(item.getText());
                        if (multipleIndex == position) {
                            tv_name.setBackgroundResource(R.drawable.classfiy_list_nor_bg);
                            tv_name.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        } else {
                            tv_name.setBackgroundResource(R.drawable.classfiy_list_sel_bg);
                            tv_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
                        }
                    }
                };
        recyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int position) {
                multipleIndex = position;
                commonAdapter.notifyDataSetChanged();
                multipleDialog.dismiss();
                mPresenter.setOrderBy(rankIds[position]);
                mPresenter.getList();
            }
        });
    }

    private void initSomeViews() {
        mTabIndicatorView = mContextView.findViewById(R.id.tabIndicator);
        mViewSearch = mContextView.findViewById(R.id.view_search);
        mFrameLayout = mContextView.findViewById(R.id.frameLayout);
        TextView mTvTitle = mContextView.findViewById(R.id.tv_page_title);
        mTvTitle.setText(getActivity().getString(R.string.main_course_list));

    }

    private void addTabToIndicator() {
        ArrayList<ITab> tabs = new ArrayList<>();
        tabs.add(mTabCategory.setTitle(getString(R.string.design_category)));
        tabs.add(mTabSort.setTitle(getString(R.string.design_sort_com)));
        tabs.add(mTabCourseType.setTitle(getString(R.string.design_course_type)));
        mTabIndicatorView.addTabs(tabs.toArray(new ITab[tabs.size()]));
        initTabDataAndListener();
        //需要在
        mTabCategory.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        mTabCategory.getTabView().setPadding(DensityUtil.dip2px(18), 0, 0, 0);
        mTabCourseType.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        mTabCourseType.getTabView().setPadding(0, 0, DensityUtil.dip2px(18), 0);


    }

    private void initTabTitleView() {
        mTabCategory = new MultiAttrTab(mFrameLayout);
        mTabSort = new SingleListTab(mFrameLayout);
        mTabCourseType = new SingleListTab(mFrameLayout);
    }

    private void initTabDataAndListener() {
        List<SingleListModel> result = new ArrayList<>();
        for (int rankResId : ranks) {
            result.add(new SingleListModel(getString(rankResId)));
        }
        setMultipleDialog(result);

        mTabSort.addData(result);
        mTabSort.setItemClickCallBack((position, singleListModel) -> {
            Log.e("选中的综合id:", rankIds[position] + "");
            mPresenter.setOrderBy(rankIds[position]);
            mPresenter.getList();
        });

        mTabCourseType.setItemClickCallBack((position, singleListModel) -> {
            if (courseTypeBeanList == null) {
                return;
            }
            mPresenter.setCourseType(courseTypeBeanList.get(position).getId());
            mPresenter.setVip(courseTypeBeanList.get(position).getVip());
            mPresenter.getList();
        });
        mTabCategory.setClickCallBack(ids -> {
            Log.e("选中的分类id:", ids);
            mPresenter.setAttrValId(ids);
            mPresenter.getList();
        });

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
    public void setFilterCourseType(List<CourseTypeBean> datas) {
        if (datas == null) {
            return;
        }
        this.courseTypeBeanList = datas;
        List<SingleListModel> result = new ArrayList<>();
        for (CourseTypeBean data : datas) {
            result.add(new SingleListModel(data.getName()));
        }
        mTabCourseType.addData(result);
        for (int i = 0; i < datas.size(); i++) {
            Log.e("数据1：", datas.get(i).getName());
        }
    }

    @Override
    public void setFilterAttrs(List<PublicAttrClassifyBean> data) {
        classifyBeans.addAll(data);
        setClassfiyDialog();
        mTabCategory.addData(FilterDataChangeHelper.getFilterParentList(data));
        for (int i = 0; i < data.size(); i++) {
            Log.e("数据2：", data.get(i).getName());
            for (int j = 0; j < data.get(i).getChild().size(); j++) {
                Log.e("数据3：", data.get(i).getChild().get(j).getName()
                );
            }
        }
    }
}
