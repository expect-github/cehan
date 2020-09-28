package com.nj.baijiayun.module_main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.basic.rxbus.RxBus;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.basic.utils.UiStatusBarUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.base.BaseEmptyPresenter;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.event.LoginEvent;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.widget.MainTabView;
import com.nj.baijiayun.module_main.api.MainService;
import com.nj.baijiayun.module_main.bean.HomeBottomTabBean;
import com.nj.baijiayun.module_main.bean.HomeBottomTabHelperBean;
import com.nj.baijiayun.module_main.bean.HomeBottomTabWrapperBean;
import com.nj.baijiayun.module_main.helper.HomeTabPageHelper;
import com.nj.baijiayun.module_public.BuildConfig;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.HomeTabVisibleEvent;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.helper.update.UpdateHelper;
import com.nj.baijiayun.module_public.widget.dialog.ReminderDialog;

import java.util.List;

/**
 * @author chengang
 */
@Route(path = RouterConstant.PAGE_MAIN)
public class MainActivity extends BaseAppActivity<BaseEmptyPresenter> {


    private long mExitTime;
    private MainTabView mBottomBarView;
    public static final String BOTTOM_NAV_KEY = "bottom_nav_key";

    @Override
    public void initAppStatusBar() {
        setTranslucentBar();
        UiStatusBarUtil.statusBarLightMode(this);
    }

    @Override
    protected void registerListener() {
        //请勿删除
        RxBus.getInstanceBus().registerRxBus(this, LoginEvent.class, loginEvent -> AccountHelper.getInstance().logout());
        RxBus.getInstanceBus().registerRxBus(this, HomeTabVisibleEvent.class, homeTabVisibleEvent -> {
            if (homeTabVisibleEvent == null) {
                return;
            }
            if (mBottomBarView == null || mBottomBarView.getBottomNavigationBar() == null) {
                return;
            }
            mBottomBarView.switchBottomVisible(homeTabVisibleEvent.isShow() ? View.VISIBLE : View.GONE);
        });
        LiveDataBus.get().with(LiveDataBusEvent.MAIN_TAB_SWITCH, String.class).observe(this, HomeTabPageHelper::checkHomeTabOrJumpNewPageByRouter);
        LiveDataBus.get().with(LiveDataBusEvent.MAIN_TAB_SWITCH_EXIST_TAB, String.class).observe(this, this::switchHomeTab);


        mStatusView.setOnRetryClickListener(v -> loadHomeBottomNav());

//        ((ViewGroup) findViewById(android.R.id.content)).addView();

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        hideToolBar();
        mBottomBarView = findViewById(R.id.bottom_bar);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        loadHomeBottomNav();
        tryShowAppReminder();
        HomeTabPageHelper.preLoadH5(this);


    }

    private void tryShowAppReminder() {
        if (ConfigManager.getInstance().needShowReminder()) {
            new ReminderDialog(this).setCallBack(this::delayTask).show();
        } else {
            delayTask();
        }
    }

    private void initBottomTabs(List<HomeBottomTabBean> nav) {
        if (nav == null || nav.size() == 0) {
            return;
        }
        if (BuildConfig.DEBUG) {
            Logger.d("initBottomTabs" + GsonHelper.getGsonInstance().toJson(nav));
        }
        MainTabView mainTabView = mBottomBarView.setBottomNormalColor(R.color.main_tab_normal_color)
                .setRemoteUrl(HomeTabPageHelper.getUnSeletedIcons(nav), HomeTabPageHelper.getSelectIcons(nav))
                .setIconSize(20)
                .setFragments(HomeTabPageHelper.getFragmentsByHomeBottomTab(nav))
                .setBottomNoIcon(HomeTabPageHelper.getEmptyIcons(nav));
        if (!nav.get(0).isNoNeedShowTitle()) {
            mainTabView.setBottomTitles(HomeTabPageHelper.getTabTitles(nav));
        }
        mainTabView.bindFragment(getSupportFragmentManager(), 0);
        HomeTabPageHelper.setHomeTabNavs(nav);

    }

    private void delayTask() {
        //2代表是android
        UpdateHelper.configUpdateInfo(ConstsH5Url.getBaseApiUrl() + "api/app/version/latest/2", this);
        new android.os.Handler().postDelayed(UpdateHelper::checkUpdate, 500);
    }


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.main_activity_main;
    }


    @Override
    public void onBackPressedSupport() {
        //底部栏目 不显示的时候是单h5
        if (mBottomBarView != null && !mBottomBarView.isBottomVisible()) {
            return;
        }
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.shortShow(this, R.string.main_exit_confirm);
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstanceBus().unSubscribe(this);
        HomeTabPageHelper.preLoadHomeBottomNav();
    }

    private void loadHomeBottomNav() {

        HomeBottomTabHelperBean config = ConfigManager.getInstance().getConfig(HomeBottomTabHelperBean.class, BOTTOM_NAV_KEY);
        final boolean isHasHomeTab = config != null;
        if (isHasHomeTab) {
            initBottomTabs(config.getShowTabList());
        }
        if (!isHasHomeTab) {
            showLoadView();
        }
        NetMgr.getInstance().getDefaultRetrofit()
                .create(MainService.class)
                .getHomeBottomNav()
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this))
                .subscribe(new BaseSimpleObserver<BaseResponse<HomeBottomTabWrapperBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<HomeBottomTabWrapperBean> data) {
                        mStatusView.showContent();
                        HomeBottomTabHelperBean wrapperBean = new HomeBottomTabHelperBean(data.getData().getTabs());
                        ConfigManager.getInstance().saveConfig(BOTTOM_NAV_KEY, wrapperBean);
                        if (!isHasHomeTab) {
                            initBottomTabs(wrapperBean.getShowTabList());
                        }

                    }

                    @Override
                    public void onFail(Exception e) {
                        if (!isHasHomeTab) {
                            showErrorDataView();
                        }

                    }
                });

    }

    private void switchHomeTab(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        int childCount = mBottomBarView.getViewPager().getChildCount();
        List<HomeBottomTabBean> homeTabNavs = HomeTabPageHelper.getHomeTabNavs();
        for (int i = 0; i < homeTabNavs.size(); i++) {
            if (homeTabNavs.get(i).getRouter().equals(key)) {
                int index = i >= childCount ? childCount - 1 : i;
                if (mBottomBarView != null) {
                    mBottomBarView.switchFragment(index);
                }
                break;
            }
        }
    }


}
