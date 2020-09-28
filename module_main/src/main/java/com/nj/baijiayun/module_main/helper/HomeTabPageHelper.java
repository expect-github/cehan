package com.nj.baijiayun.module_main.helper;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_main.MainActivity;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.api.MainService;
import com.nj.baijiayun.module_main.bean.HomeBottomTabBean;
import com.nj.baijiayun.module_main.bean.HomeBottomTabHelperBean;
import com.nj.baijiayun.module_main.bean.HomeBottomTabWrapperBean;
import com.nj.baijiayun.module_main.fragments.HomeCalendarFragment;
import com.nj.baijiayun.module_main.fragments.HomeMainFragment;
import com.nj.baijiayun.module_main.fragments.MyGroupImpByNativeFragment;
import com.nj.baijiayun.module_main.fragments.SelectCourseFragment;
import com.nj.baijiayun.module_main.fragments.UserFragment;
import com.nj.baijiayun.module_main.fragments.temple.WebViewAsMainPageFragment;
import com.nj.baijiayun.module_main.fragments.temple.WebViewNormalTabFragment;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.temple.BaseAppWebViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.fragment.app.Fragment;

/**
 * @author chengang
 * @date 2020-02-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.helper
 * @describe
 */
public class HomeTabPageHelper {


//    课程  Course
//    一对一约课  Oto
//    约课记录  Record
//    嗨拼团  Group
//    讲师  ShowTeachers
//    图书  BookList  1
//    资讯  News
//    文库  Library
//    社区  Community
//    练习  Practise
//    学习日历  StudyCalendar
//    积分商城  IntegralStore
//    签到  Sign
//    作业  Homework
//    消息  Message

    private static List<HomeBottomTabBean> homeTabNavs;

    public static List<HomeBottomTabBean> getHomeTabNavs() {
        if (homeTabNavs == null) {
            homeTabNavs = new ArrayList<>();
        }
        return homeTabNavs;
    }

    public static boolean isRouterPathInHomeBottomTab(String key) {
        List<HomeBottomTabBean> homeTabNavs = getHomeTabNavs();
        for (int i = 0; i < homeTabNavs.size(); i++) {
            if (homeTabNavs.get(i).getRouter().equals(key)) {
                return true;

            }
        }
        return false;
    }

    public static void checkHomeTabOrJumpNewPageByRouter(String router) {
        if (router == null) {
            return;
        }
        if (router.startsWith("http")) {
            JumpHelper.jumpWebViewNoNeedAppTitle(router);
            return;
        }
        if (isRouterPathInHomeBottomTab(router)) {
            LiveDataBus.get().with(LiveDataBusEvent.MAIN_TAB_SWITCH_EXIST_TAB).postValue(router);
        } else {
            switch (router) {
                case ConstsH5Url.ROUTER_COURSE:
                    ARouter.getInstance().build(RouterConstant.PAGE_SELECT_COURSE)
                            .navigation();
                    break;
                case ConstsH5Url.ROUTER_GROUP:
                    ARouter.getInstance().build(RouterConstant.PAGE_ASSEMBLE).navigation();
                    break;
                case ConstsH5Url.ROUTER_STUDY_CALENDAR:
                    ARouter.getInstance().build(RouterConstant.PAGE_COURSE_LEARN_CALENDAR).navigation();
                    break;
                default:
                    JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getUrlByRouterKey(router));
                    break;
            }
        }
    }

    public static void setHomeTabNavs(List<HomeBottomTabBean> homeTabNavs) {
        HomeTabPageHelper.homeTabNavs = homeTabNavs;
    }

    public static final Map<String, Class<? extends BaseAppFragment>> APP_INNER_FRAGMENT_MAP = new HashMap<String, Class<? extends BaseAppFragment>>() {{
        put(ConstsH5Url.ROUTER_COURSE, SelectCourseFragment.class);
        put(ConstsH5Url.ROUTER_INDEX, HomeMainFragment.class);
        put(ConstsH5Url.ROUTER_PERSON, UserFragment.class);
        put(ConstsH5Url.ROUTER_STUDY_CALENDAR, HomeCalendarFragment.class);
        put(ConstsH5Url.ROUTER_GROUP, MyGroupImpByNativeFragment.class);
    }};


    private static final String[] NEED_PAY_LISTENER_URL_PATH = new String[]{ConstsH5Url.getPractisePath(), ConstsH5Url.getRecordPath()};
    //h5已经实现了跳转新页面
    private static final String[] H5_IMPL_OPEN_NEW_PAGE = new String[]{ConstsH5Url.getRecordPath()};


    private static <T extends BaseAppWebViewFragment> T newInstance(Class<T> cls, String url, String title, boolean lazyLoad) {
        Bundle bundle = new Bundle();
        //基础类默认取url
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putBoolean("lazyLoad", lazyLoad);
        return FragmentCreateHelper.newInstance(bundle, cls);
    }

    public static <T extends BaseAppWebViewFragment> T newInstance(Class<T> cls, String url, String title) {
        return newInstance(cls, url, title, true);
    }

    public static WebViewAsMainPageFragment newInstanceMainPage(String url, String title) {
        return newInstance(WebViewAsMainPageFragment.class, url, title, true);
    }

    public static WebViewAsMainPageFragment newInstanceMainPage(String url, String title, boolean lazyLoad) {
        return newInstance(WebViewAsMainPageFragment.class, url, title, lazyLoad);
    }

    public static WebViewNormalTabFragment newInstanceNormalPage(String url, String title) {
        return newInstance(WebViewNormalTabFragment.class, url, title, true);
    }

    public static WebViewNormalTabFragment newInstanceNormalPage(String url, String title, boolean lazyLoad) {
        return newInstance(WebViewNormalTabFragment.class, url, title, lazyLoad);
    }


    public static boolean needLazy(Bundle bundle) {
        return bundle.getBoolean("lazyLoad");
    }

    public static String getTitle(Bundle bundle) {
        return bundle.getString("title");
    }


    public static List<Fragment> getFragmentsByHomeBottomTab(List<HomeBottomTabBean> data) {
        List<Fragment> result = new ArrayList<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            HomeBottomTabBean bean = data.get(i);
            if (bean.isAppInnerRouter()) {
                result.add(FragmentCreateHelper.newInstance(null, Objects.requireNonNull(APP_INNER_FRAGMENT_MAP.get(bean.getRouter()))));
            } else if (bean.isWebUrlLink()) {
                result.add(newInstanceMainPage(bean.getWebUrl(), bean.getTitle()));
            } else {
                //H5 页面
                if (isWebPageHasImplJumpNewPage(bean.getH5Path())) {
                    result.add(newInstanceNormalPage(bean.getH5Path(), bean.getTitle(), true));
                } else {
                    result.add(newInstanceMainPage(bean.getH5Path(), bean.getTitle(), true));
                }
            }

        }
        return result;
    }


    public static String[] getTabTitles(List<HomeBottomTabBean> data) {
        String[] result = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).getTitle();
        }
        return result;
    }

    public static String[] getSelectIcons(List<HomeBottomTabBean> data) {
        String[] result = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).getSelectIcon();
        }
        return result;
    }

    public static String[] getUnSeletedIcons(List<HomeBottomTabBean> data) {
        String[] result = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).getUnSelectIcon();
        }
        return result;
    }

    public static int[] getEmptyIcons(List<HomeBottomTabBean> data) {
        int[] result = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = R.drawable.main_bottom_tab_home;
        }
        return result;
    }


    private static boolean isWebPageHasImplJumpNewPage(String path) {
        for (String s : H5_IMPL_OPEN_NEW_PAGE) {
            if (s.equals(path)) {
                return true;
            }
        }
        return false;
    }

    public static void preLoadHomeBottomNav() {
        NetMgr.getInstance().getDefaultRetrofit()
                .create(MainService.class)
                .getHomeBottomNav()
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<BaseResponse<HomeBottomTabWrapperBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<HomeBottomTabWrapperBean> data) {
                        HomeBottomTabHelperBean wrapperBean = new HomeBottomTabHelperBean(data.getData().getTabs());
                        ConfigManager.getInstance().saveConfig(MainActivity.BOTTOM_NAV_KEY, wrapperBean);


                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }

    public static void preLoadH5(Context context) {
        AppWebView appWebView = new AppWebView(context);
        appWebView.loadUrl(ConstsH5Url.getBaseH5Url());
    }

}
