package com.nj.baijiayun.module_course.ui.wx.mylearnlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseAppMultipleTabActivity;
import com.nj.baijiayun.module_common.base.BaseEmptyPresenter;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.response.MyCourseResponse;
import com.nj.baijiayun.module_course.bean.wx.MyCourseTypeBean;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.mucourse
 * @describe
 */
@Route(path = RouterConstant.PAGE_COURSE_MY_LEARNED_LIST)
public class MyCourseActivity extends BaseAppMultipleTabActivity<BaseEmptyPresenter> {
    private int mLastTabIndex = 0;
    private int mLastTabCount = 0;
    private boolean isHide;

    @Override
    public String[] addTabs() {
        return new String[0];
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setPageTitle(isHide ? R.string.course_my_hide_course_title : R.string.course_my_course_title);
        if (!isHide) {
            ToolBarHelper.addRightImageViewsLayoutRightToLeft(getToolBar(),23, new int[]{R.drawable.public_ic_calendar, R.drawable.course_ic_show_hide},
                    new View.OnClickListener[]{v -> ARouter.getInstance().build(RouterConstant.PAGE_COURSE_LEARN_CALENDAR).navigation(), v -> {
                        Intent intent = new Intent(getActivity(), MyCourseActivity.class);
                        intent.putExtra("isHide", true);
                        startActivity(intent);
                    }});
        }

    }

    @Override
    protected void initParams() {
        super.initParams();
        isHide = getIntent().getBooleanExtra("isHide", false);
    }

    @Override
    public void setTab() {

    }

    @Override
    public boolean isNeedAdjust() {
        return false;
    }

    @Override
    public ArrayList<Fragment> addFragment() {
        return null;
    }

    @Override
    protected void registerListener() {

        mStatusView.setOnRetryClickListener(v -> loadTab());
        LiveDataBus.get().with(LiveDataBusEvent.COURSE_HIDE_RECOVER, Integer.class).observe(this, courseId -> {
            if (courseId != null) {
                loadTab();
            }
        });
        getVp().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mLastTabIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        loadTab();
    }

    private void loadTab() {
        showLoadView();
        CourseService courseService = NetMgr.getInstance().getDefaultRetrofit()
                .create(CourseService.class);
        Observable<MyCourseResponse> myCourseResponseObservable = isHide ? courseService.getMyHideCourse(2) : courseService
                .getMyCourse(2);
        myCourseResponseObservable
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this))
                .subscribe(new BaseSimpleObserver<MyCourseResponse>() {
                    @Override
                    public void onSuccess(MyCourseResponse myCourseResponse) {
                        setTabs(myCourseResponse.getData().getTypeNum());
                        showContentView();
                        if (myCourseResponse.getData().getTypeNum().size() == 0) {
                            showNoDataView();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        showErrorDataView();
                    }
                });
    }

    public void setTabs(List<MyCourseTypeBean> data) {
        String[] result = new String[data.size()];
        fragments = new ArrayList<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            result[i] = MessageFormat.format("{0}({1})", data.get(i).getName(), data.get(i).getNum());

            Bundle bundle = new Bundle();
            bundle.putInt("type", data.get(i).getType());
            bundle.putBoolean("isHide", isHide);
            fragments.add(FragmentCreateHelper.newInstance(bundle, MyCourseFragment.class));
        }
        initTab(result, fragments);
        getVp().setOffscreenPageLimit(fragments.size());
        if (mLastTabCount == data.size()) {
            int index = mLastTabIndex > fragments.size() - 1 ? fragments.size() - 1 : mLastTabIndex;
            getVp().setCurrentItem(index);
        }
        mLastTabCount = data.size();

    }

}
