package com.nj.baijiayun.module_distribution.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.widget.attrtab.ITab;
import com.nj.baijiayun.basic.widget.attrtab.TabIndicatorView;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.temple.AbstractListPresenter;
import com.nj.baijiayun.module_common.widget.tabs.RepeatSelectTab;
import com.nj.baijiayun.module_distribution.R;
import com.nj.baijiayun.module_distribution.api.DistruibutionService;
import com.nj.baijiayun.module_distribution.bean.res.DtbBookListResponse;
import com.nj.baijiayun.module_distribution.bean.res.DtbCourseListResponse;
import com.nj.baijiayun.module_distribution.consts.GoodsType;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.PublicCourseClassifyResponse;
import com.nj.baijiayun.module_public.helper.data.DataHelper;
import com.nj.baijiayun.module_public.temple.BaseSimpleListFragment;
import com.nj.baijiayun.module_public.widget.filter_tabs.SingleListTab;
import com.nj.baijiayun.processor.Module_distributionAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution
 * @describe
 */
public class DistributionWithFilterFragment extends BaseSimpleListFragment {
    private int type;
    private TabIndicatorView mTabIndicator;
    private FrameLayout mFrameLayout;
    private int courseType;
    private String priceSort;
    private String commissionSort;
    private String salesNumSort;
    private SingleListTab mTabCourseType;

    private boolean isAddCourseType = false;

    //商品类型 （0：课程 1：图书）


    @Override
    public void initParms(Bundle params) {
        super.initParms(params);
        type = FragmentCreateHelper.getType(params);

    }


    @Override
    public BaseRecyclerAdapter createRecyclerAdapter() {
        return Module_distributionAdapterHelper.getDefaultAdapter(getContext());
    }


    @Override
    protected int bindLayout() {
        return R.layout.distribution_fragment_list;
    }


    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        mTabIndicator = mContextView.findViewById(R.id.tabIndicator);
        mFrameLayout = mContextView.findViewById(R.id.frameLayout);
        getNxRefreshView().addItemDecoration(SpaceItemDecoration.create().setSpace(10));
        initTabs();
    }

    private void initTabs() {
        ArrayList<ITab> tabs = new ArrayList<>();
        mTabCourseType = initCourseTab();
        if (mTabCourseType != null) {
            tabs.add(mTabCourseType);
        }
        RepeatSelectTab repeatSelectTab1 = new RepeatSelectTab().setTitle("价格");
        RepeatSelectTab repeatSelectTab2 = new RepeatSelectTab().setTitle("佣金");
        RepeatSelectTab repeatSelectTab3 = new RepeatSelectTab().setTitle("销量");
        repeatSelectTab1.setTabSelectCallBack(isSelect -> {
            priceSort = repeatSelectTab1.isSelectTab() ? "desc" : "asc";
            refreshData();
        });
        repeatSelectTab2.setTabSelectCallBack(isSelect -> {
            commissionSort = repeatSelectTab2.isSelectTab() ? "desc" : "asc";
            refreshData();
        });
        repeatSelectTab3.setTabSelectCallBack(isSelect -> {
            salesNumSort = repeatSelectTab3.isSelectTab() ? "desc" : "asc";
            refreshData();
        });
        tabs.add(repeatSelectTab1);
        tabs.add(repeatSelectTab2);
        tabs.add(repeatSelectTab3);
        mTabIndicator.addTabs(tabs.toArray(new ITab[tabs.size()]));


    }

    private SingleListTab initCourseTab() {
        SingleListTab result = null;
        if (GoodsType.isCourse(type)) {
            result = new SingleListTab(mFrameLayout);
            result.setTitle(getContext().getString(R.string.public_course_type));
            result.setTabSelectCallBack(isSelect -> {
                if (isSelect) {
                    getCourseType();
                }
            });
            result.setItemClickCallBack((position, singleListModel) -> {
                courseType = singleListModel.getId();
                refreshData();
            });

        }
        return result;
    }


    @Override
    public void registerListener() {
        super.registerListener();
    }

    @Override
    protected void onPageItemClick(BaseViewHolder holder, int position, View view, Object item) {

    }

    @Override
    protected AbstractListPresenter createPresenter() {
        DistruibutionService distruibutionService = NetMgr.getInstance().getDefaultRetrofit().create(DistruibutionService.class);
        if (GoodsType.isCourse(type)) {
            return new AbstractListPresenter<DtbCourseListResponse>() {

                @Override
                public List resultCovertToList(DtbCourseListResponse result) {
                    addCommissionType(result);

                    return result.getData().getList();
                }

                @Override
                public Observable<DtbCourseListResponse> getListObservable(int page) {
                    return distruibutionService.getDtbCourseListByFilter(courseType, priceSort, commissionSort, salesNumSort, page);
                }
            };
        } else {
            return new AbstractListPresenter<DtbBookListResponse>() {

                @Override
                public List resultCovertToList(DtbBookListResponse result) {
                    addCommissionType(result);
                    return result.getData().getList();
                }

                @Override
                public Observable<DtbBookListResponse> getListObservable(int page) {
                    return distruibutionService.getDtbBookListByFilter(priceSort, commissionSort, salesNumSort, page);
                }
            };
        }


    }

    private void addCommissionType(DtbCourseListResponse result) {
        int commissionType = result.getData().getCommissionType();
        if (result.getData().getList() != null) {
            for (int i = 0; i < result.getData().getList().size(); i++) {
                result.getData().getList().get(i).setCommissionType(commissionType);
            }
        }
    }

    private void addCommissionType(DtbBookListResponse result) {
        int commissionType = result.getData().getCommissionType();
        if (result.getData().getList() != null) {
            for (int i = 0; i < result.getData().getList().size(); i++) {
                result.getData().getList().get(i).setCommissionType(commissionType);
            }
        }
    }

    @Override
    public void processLogic() {
        getCourseType();
    }

    private void getCourseType() {
        if (isAddCourseType) {
            return;
        }
        PublicService publicService = NetMgr.getInstance().getDefaultRetrofit().create(PublicService.class);
        publicService.getCourseClassify()
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this))
                .subscribe(new BaseSimpleObserver<PublicCourseClassifyResponse>() {
                    @Override
                    public void onSuccess(PublicCourseClassifyResponse publicCourseClassifyResponse) {
                        isAddCourseType = true;


                        DataHelper.addCategoryData(publicCourseClassifyResponse.getData().getAppCourseType(), mTabCourseType);
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }


}
