package com.nj.baijiayun.module_main.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.widget.RoundImageView;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.adapter.holder.UserItemHolder;
import com.nj.baijiayun.module_main.adapter.holder.UserItemNewHolder;
import com.nj.baijiayun.module_main.bean.UserItemBean;
import com.nj.baijiayun.module_main.bean.UserItemListBean;
import com.nj.baijiayun.module_main.bean.wx.UserCenterBean;
import com.nj.baijiayun.module_main.mvp.contract.UserCenterContract;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.bean.AppConfigBean;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.ConstsHomeTab;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.widget.GridItemDecoration;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class UserFragment extends BaseAppFragment<UserCenterContract.Presenter> implements UserCenterContract.View {
    private RecyclerView mRv;
    private RecyclerView mRvNew;
    private BaseMultipleTypeRvAdapter userAdapter,newAdapter;
    private TextView mUserNameTv;
    private TextView mCourseNumTv;
    private TextView mAppointNumTv;
    private TextView mGoldNumTv;

    private CircleImageView mUserHeadIv;
    private TextView mVipActionTv;
    private View mViewLogin;
    private View mViewUnLogin;
    private View mVipLl;
    private NestedScrollView mNsv;

    /**
     * 这标在userInfo.json 需要跟json文件保持一致
     */
    private static final String ICON_MSG = "main_ic_msg";
    private static final String ICON_VIP_ORDER = "main_ic_vip_order";
    private static final String ICON_LEARN_CARD = "main_ic_learn_card";
    private static final String ICON_COUPON = "main_ic_coupon";
    private static final String ICON_BOOKS = "main_ic_books";
    private static final String ICON_ADDRESS = "main_ic_address";
    private static final String ICON_APPOINT_ORDER = "main_ic_appoint_order";
    private static final String ICON_HOMEWORK = "main_ic_homework";
    private static final String ICON_COMMUNITY = "main_ic_community";
    private static final String ICON_DISTRIBUTION = "main_ic_distribution";
    private static final String ICON_PERSON_SERVICE = "main_ic_service";
    private boolean needUpdateUseInfoUi = true;
    private LinearLayout mTabMyLearned1Ll;
    private LinearLayout mTabAppointLl;
    private LinearLayout mTabLearnCalendarLl;
    private LinearLayout mTabIntergralLl;
    private LinearLayout mTabTopLl;
    private TextView mIntegralNumberTv;
    private LinearLayout mSignLl;
    private TextView mSignStatusTv;
    private TextView mIntegralDescTv;
    private View mScrollChildCl;

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.main_fragment_wx_user_v2;


    }


    @Override
    protected void initView(View mContextView) {

        mScrollChildCl = mContextView.findViewById(R.id.cl_scroll_child);
        mNsv = mContextView.findViewById(R.id.nsv);
        mViewUnLogin = mContextView.findViewById(R.id.view_un_login);
        mViewLogin = mContextView.findViewById(R.id.view_login);
        mUserHeadIv = mContextView.findViewById(R.id.iv_user_head);
        mVipActionTv = mContextView.findViewById(R.id.tv_vip_action);
        mVipLl = mContextView.findViewById(R.id.ll_vip);
        mUserHeadIv = mContextView.findViewById(R.id.iv_user_head);
        mUserNameTv = mContextView.findViewById(R.id.tv_user_name);
        mCourseNumTv = mContextView.findViewById(R.id.tv_course_num);
        mAppointNumTv = mContextView.findViewById(R.id.tv_appoint_num);
        mGoldNumTv = mContextView.findViewById(R.id.tv_gold_num);
        mIntegralNumberTv = mContextView.findViewById(R.id.tv_integral_number);
        mSignLl = mContextView.findViewById(R.id.ll_sign_group);
        mRv = mContextView.findViewById(R.id.rv);
        mRvNew = mContextView.findViewById(R.id.rv_new);
        mTabMyLearned1Ll = mContextView.findViewById(R.id.ll_tab_1);
        mTabAppointLl = mContextView.findViewById(R.id.ll_tab_2);
        mTabLearnCalendarLl = mContextView.findViewById(R.id.ll_tab_3);
        mTabIntergralLl = mContextView.findViewById(R.id.ll_tab_4);
        mTabTopLl = mContextView.findViewById(R.id.ll_top_tabs);
        mSignStatusTv = mContextView.findViewById(R.id.tv_sign_status);
        mIntegralDescTv = mContextView.findViewById(R.id.tv_integral_desc);
        mIntegralDescTv.setText(ConfigManager.getInstance().getAppConfig().getPointInfo().getPointName());
        mTabTopLl.setVisibility(View.INVISIBLE);
        //设置这个触发未显示逻辑
        mViewUnLogin.setVisibility(View.GONE);
        showUnLogin();

    }


    @Override
    public void onResume() {
        super.onResume();
        checkUiVisible();
        if (isVisible()) {
            loadData();
        }
    }

    private void loadData() {
        if (needUpdateUseInfoUi) {
            mPresenter.getUserCenterInfo();
        }
        mPresenter.getMessageCount();
        mPresenter.updatUserInfo();
        mPresenter.getSignAndIntegral();
    }

    private void checkUiVisible() {
        if (AccountHelper.getInstance().getInfo() == null) {
            needUpdateUseInfoUi = true;
            clearAdapterApplyInfoTag();
            showUnLogin();
        } else {
            showLoginIn();

        }

    }

    private void clearAdapterApplyInfoTag() {
        if (userAdapter != null) {
            /**
             * userAdapter.setTag(UserItemNewHolder.TAG_NEED_GO_APPLY, null);以前的
             * 下面是修改后的2020/8/7
             */
            userAdapter.setTag(UserItemNewHolder.TAG_NEED_GO_APPLY, null);
        }
        /**
         * 新加的
         */
        if (newAdapter!=null){
            newAdapter.setTag(UserItemNewHolder.TAG_NEED_GO_APPLY, null);
        }
    }

    private void showLoginIn() {
        if (mViewLogin == null || mViewLogin.getVisibility() == View.VISIBLE) {
            return;
        }
        ViewHelper.setViewVisible(mSignLl, ConfigManager.getInstance().getAppConfig().needShowIntegralModule());
        mViewUnLogin.setVisibility(View.GONE);
        mViewLogin.setVisibility(View.VISIBLE);
        mVipLl.setVisibility(ConfigManager.getInstance().getAppConfig().needShowVipModule() ? View.VISIBLE : View.GONE);

    }

    private void showUnLogin() {
        if (mViewUnLogin == null || mViewUnLogin.getVisibility() == View.VISIBLE) {
            return;
        }
        mSignLl.setVisibility(View.GONE);
        mSignStatusTv.setText(BaseApp.getInstance().getString(R.string.main_sign_status_init));
        mCourseNumTv.setText("0");
        mAppointNumTv.setText("0");
        mGoldNumTv.setText("0");
        mIntegralNumberTv.setText("0");
        mViewUnLogin.setVisibility(View.VISIBLE);
        mViewLogin.setVisibility(View.GONE);
    }


    @Override
    public void registerListener() {

    }


    @Override
    public void processLogic() {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRv.setLayoutManager(new GridLayoutManager(getContext(),3));
        mRv.addItemDecoration(new GridItemDecoration(getContext(),0,R.color.white));
        mRvNew.setLayoutManager(new GridLayoutManager(getContext(),3));
        mRvNew.addItemDecoration(new GridItemDecoration(getContext(),0,R.color.white));
        userAdapter = Module_mainAdapterHelper.getUserAdapter(getActivity());
        newAdapter = Module_mainAdapterHelper.getUserAdapter(getActivity());
        mRv.setAdapter(userAdapter);
        mRv.setNestedScrollingEnabled(false);
        mRvNew.setAdapter(newAdapter);
        mRvNew.setNestedScrollingEnabled(false);
        mScrollChildCl.setFocusable(true);
        mScrollChildCl.setFocusableInTouchMode(true);
        mScrollChildCl.requestFocus();
        initListener();
//        UserItemListBean item = GsonHelper.getGsonInstance().fromJson(getJson(), UserItemListBean.class);
        UserItemListBean itemOne = GsonHelper.getGsonInstance().fromJson(getJsonOne(), UserItemListBean.class);
        UserItemListBean itemTwo = GsonHelper.getGsonInstance().fromJson(getJsonTwo(), UserItemListBean.class);
//        Log.e("我的长度one",itemOne.getUserItemList().size()+"");
//        Log.e("我的长度two",itemTwo.getUserItemList().size()+"");
        handlerItemVisibleOne(itemOne.getUserItemList());
        handlerItemVisibleTwo(itemTwo.getUserItemList());
        userAdapter.addAll(itemOne.getUserItemList());

        newAdapter.addAll(itemTwo.getUserItemList());

        initTopTabVisible();

    }

    private void initTopTabVisible() {
        ViewHelper.setViewVisible(mTabAppointLl, ConfigManager.getInstance().getAppConfig().needShowOtoModule());
        ViewHelper.setViewVisible(mTabIntergralLl, ConfigManager.getInstance().getAppConfig().needShowIntegralModule());
        controlTopTabsLineVisible();
    }

    private void handlerItemVisible(List<UserItemBean> userItemList) {
        UserItemHideClass userItemHideClass = new UserItemHideClass();
        userItemHideClass.setUserItemList(userItemList);
        AppConfigBean appConfig = ConfigManager.getInstance().getAppConfig();
        userItemHideClass.configHideItem(ICON_VIP_ORDER, appConfig.needShowVipModule());
        userItemHideClass.configHideItem(ICON_LEARN_CARD, appConfig.needShowStudyModule());
        userItemHideClass.configHideItem(ICON_HOMEWORK,appConfig.needShowHomeWork());
        userItemHideClass.configHideItem(ICON_APPOINT_ORDER, appConfig.needShowOtoModule());
        userItemHideClass.configHideItem(ICON_COUPON,appConfig.needShowCouponModule());
        userItemHideClass.configHideItem(ICON_BOOKS, appConfig.needShowBookModule());
        userItemHideClass.configHideItem(ICON_COMMUNITY, appConfig.needShowCommunity());
        userItemHideClass.configHideItem(ICON_DISTRIBUTION, appConfig.needShowDistribution());
        userItemHideClass.configHideItem(ICON_PERSON_SERVICE, ConfigManager.getInstance().isShowPersonService());

    }

    private void handlerItemVisibleOne(List<UserItemBean> userItemList) {
        UserItemHideClass userItemHideClass = new UserItemHideClass();
        userItemHideClass.setUserItemList(userItemList);
        AppConfigBean appConfig = ConfigManager.getInstance().getAppConfig();
        userItemHideClass.configHideItem(ICON_VIP_ORDER, appConfig.needShowVipModule());
        userItemHideClass.configHideItem(ICON_APPOINT_ORDER, appConfig.needShowOtoModule());
        userItemHideClass.configHideItem(ICON_BOOKS, appConfig.needShowBookModule());

    }

    private void handlerItemVisibleTwo(List<UserItemBean> userItemList) {
        UserItemHideClass userItemHideClass = new UserItemHideClass();
        userItemHideClass.setUserItemList(userItemList);
        AppConfigBean appConfig = ConfigManager.getInstance().getAppConfig();
        userItemHideClass.configHideItem(ICON_LEARN_CARD, appConfig.needShowStudyModule());
        userItemHideClass.configHideItem(ICON_HOMEWORK,appConfig.needShowHomeWork());
        userItemHideClass.configHideItem(ICON_COUPON,appConfig.needShowCouponModule());
        userItemHideClass.configHideItem(ICON_COMMUNITY, appConfig.needShowCommunity());
        userItemHideClass.configHideItem(ICON_DISTRIBUTION, appConfig.needShowDistribution());
        userItemHideClass.configHideItem(ICON_PERSON_SERVICE, ConfigManager.getInstance().isShowPersonService());
    }

    private void initListener() {
        LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean o) {
                if (o != null && o) {
                    mNsv.scrollTo(0, 0);
                }
            }
        });
        mUserHeadIv.setOnClickListener(v -> {
            if (AccountHelper.getInstance().getInfo() != null) {
                JumpHelper.jumpPreViewSingleImgView(AccountHelper.getInstance().getInfo().getAvatar());
            }
        });

        mVipLl.setOnClickListener(v -> JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getVip()));

        mTabMyLearned1Ll.setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_MY_LEARNED_LIST).navigation();

        });
        mTabAppointLl.setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }

            LiveDataBus.get().with(LiveDataBusEvent.MAIN_TAB_SWITCH).postValue(ConstsHomeTab.TAB_APPOINT_COURSE);
        });
        mTabLearnCalendarLl.setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_LEARN_CALENDAR).navigation();

        });
        mTabIntergralLl.setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getIntegral());

        });
        mSignLl.setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getSign());

        });
        mViewLogin.setOnClickListener(v -> {

            if (JumpHelper.checkLogin()) {
                return;
            }
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getInfo());
        });
        mViewUnLogin.setOnClickListener(v -> ARouter.getInstance().build(RouterConstant.PAGE_LOGIN).navigation());

    }


    @Override
    public void setUserCenterInfo(UserCenterBean userCenterInfo) {
        Logger.d("setUserCenterInfo" + mUserNameTv);
        if (mUserNameTv == null) {
            return;
        }
        mUserNameTv.setText(userCenterInfo.getNickname());
        ImageLoader.with(getContext()).load(userCenterInfo.getAvatar()).asCircle().error(R.drawable.main_ic_unlogin_head).into(mUserHeadIv);
        mCourseNumTv.setText(String.valueOf(userCenterInfo.getCourses()));
        mAppointNumTv.setText(String.valueOf(userCenterInfo.getOto()));
        mGoldNumTv.setText(String.valueOf(userCenterInfo.getIntegral()));
        mVipActionTv.setText(AccountHelper.getInstance().isVip() ? getString(R.string.main_vip_append) : getString(R.string.main_vip_open));

    }

    @Override
    public void setMessageUnRead(boolean unRead) {
        if (userAdapter != null
                && userAdapter.getItemCount() > 1) {
            UserItemBean item = (UserItemBean) userAdapter.getItem(getItemIndex(ICON_MSG, userAdapter.getAllItems()));
            item.setUnRead(unRead);
            userAdapter.notifyItemChanged(getItemIndex(ICON_MSG, userAdapter.getAllItems()));
        }

        if (newAdapter != null
                && newAdapter.getItemCount() > 1) {
            UserItemBean item = (UserItemBean) newAdapter.getItem(getItemIndex(ICON_MSG, newAdapter.getAllItems()));
            item.setUnRead(unRead);
            newAdapter.notifyItemChanged(getItemIndex(ICON_MSG, newAdapter.getAllItems()));
        }

    }

    @Override
    public void setUserIntegral(int integral) {
        mIntegralNumberTv.setText(String.valueOf(integral));
    }

    @Override
    public void setSignStatus(boolean isSign) {
        mSignStatusTv.setText(BaseApp.getInstance().getString(isSign ? R.string.main_sign_status_signed : R.string.main_sign_status_init));
    }

    @Override
    public void setMessageUnReadCount(int count) {
        if (userAdapter != null
                && userAdapter.getItemCount() > 1) {
            UserItemBean item = (UserItemBean) userAdapter.getItem(getItemIndex(ICON_MSG, userAdapter.getAllItems()));
            item.setUnReadCount(count);
            userAdapter.notifyItemChanged(getItemIndex(ICON_MSG, userAdapter.getAllItems()));
        }

        if (newAdapter != null
                && newAdapter.getItemCount() > 1) {
            UserItemBean item = (UserItemBean) newAdapter.getItem(getItemIndex(ICON_MSG, newAdapter.getAllItems()));
            item.setUnReadCount(count);
            newAdapter.notifyItemChanged(getItemIndex(ICON_MSG, newAdapter.getAllItems()));
        }
    }

    private int getItemIndex(String iconName, List<UserItemBean> datas) {
        for (int i = datas.size() - 1; i >= 0; i--) {
            if (iconName.equals(datas.get(i).getIcon())) {
                return i;
            }
        }
        return 0;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPresenter != null) {
                loadData();
            }
            checkUiVisible();
        }
    }

    private void controlTopTabsLineVisible() {
        for (int i = 0; i < mTabTopLl.getChildCount(); i++) {
            if (!(mTabTopLl.getChildAt(i) instanceof ViewGroup)) {
                mTabTopLl.getChildAt(i).setVisibility(View.GONE);
                for (int k = i - 1; k >= 0; k--) {
                    if (mTabTopLl.getChildAt(k).getVisibility() == View.VISIBLE && mTabTopLl.getChildAt(k) instanceof ViewGroup) {
                        mTabTopLl.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        mTabTopLl.setVisibility(View.VISIBLE);
    }


    public class UserItemHideClass {
        private List<UserItemBean> userItemList;

        void setUserItemList(List<UserItemBean> userItemList) {
            this.userItemList = userItemList;
        }

        void configHideItem(String icon, boolean visible) {
            if (!visible) {
                userItemList.remove(getItemIndex(icon, userItemList));
            }
        }


    }

    /**
     * 旧的
     * @return
     */
    private String getJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assets = getContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assets.open("userInfo.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 新的 订单
     * @return
     */
    private String getJsonOne() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assets = getContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assets.open("userInfoOne.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 新的工具
     * @return
     */
    private String getJsonTwo() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assets = getContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assets.open("userInfoTwo.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
