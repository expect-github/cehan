package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baijiayun.bjyrtcsdk.Util.LogUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.StatusBarUtil;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.basic.utils.UiStatusBarUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.config.ShapeTypeConfig;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_common.widget.dialog.CommonShareDialog;
import com.nj.baijiayun.module_course.BuildConfig;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailActivityInfoHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailAssembleRuleHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailBaseInfoHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailCommentHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailDescHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailJoinNumberHolder;
import com.nj.baijiayun.module_course.adapter.course_detail_holder.DetailOutlineHolder;
import com.nj.baijiayun.module_course.bean.LunbotuBean;
import com.nj.baijiayun.module_course.bean.wx.AssembleCourseBean;
import com.nj.baijiayun.module_course.bean.wx.AssembleJoinInfoBean;
import com.nj.baijiayun.module_course.bean.wx.SectionBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_course.helper.RvHelper;
import com.nj.baijiayun.module_course.widget.AssembleActionView;
import com.nj.baijiayun.module_course.widget.GlideImageLoader;
import com.nj.baijiayun.module_public.adapter.CommonAdapter;
import com.nj.baijiayun.module_public.adapter.ViewHolder;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.bean.PublicDistributionBean;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.ConstsHomeTab;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.helper.RefreshListDataHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.helper.WebViewHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.helper.share_login.JPushHelper;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;
import com.nj.baijiayun.module_public.holder.CheckUtil;
import com.nj.baijiayun.module_public.widget.ListItemDecoration;
import com.nj.baijiayun.processor.Module_courseAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.ExpandHelper;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;
import com.nj.baijiayun.refresh.recycleview.extend.HeaderAndFooterRecyclerViewAdapter;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx_layout
 * @describe
 */
@Route(path = RouterConstant.PAGE_COURSE_DETAIL)
public class WxCourseDetailActivity extends BaseAppActivity<CourseDetailContract.Presenter> implements CourseDetailContract.View, OutLineContract.View, DetailsScrollView.OnScrollChangedListener {
    @Inject
    OutLineContract.Presenter mOutLinePresenter;
    private RecyclerView mRv;
    private HeaderAndFooterRecyclerViewAdapter adapter, newAdapter;
    private TabLayout tabLayout;
    private TextView mConfirmTv;
    private ImageView mIvVip;
    private DetailBaseInfoHolder baseInfoHolder;
    private DetailActivityInfoHolder activityInfoHolder;
    private DetailOutlineHolder outlineHolder;
    //不可以被创建多次
    DetailCommentHolder commentHolder;
    DetailJoinNumberHolder detailJoinNumberHolder;
    DetailAssembleRuleHolder detailAssemblePlayHolder;
    private TextView mAssembleStockTv;
    private View mNormalBottom;
    private View mViewLine;
    private AssembleActionView mAssembleView;
    private int maxDistance = DensityUtil.dip2px(220);
    private int offset = 0;
    private ImageView mShareIv;
    private TextView tv_title;
    private TextView mShareProfitTv;
    private View mViewShareProfit;
    public static int adaNun = 0;
    public static boolean adaType = false;
    //新加的
    private TextView mBelowPageTv1;
    private TextView mBelowPageTv2;
    private TextView mBelowPageTv3;
    private LinearLayout mBelowPageLlne1;
    private LinearLayout mBelowPageLlne2;
    private LinearLayout mBelowPageLlne3;
    private View mBelowPageView1;
    private View mBelowPageView2;
    private View mBelowPageView3;
    //    private DetailsScrollView scrollView;
    private AutoHeightViewPager mViewpage;
    private List<View> mViewList = null;
    private RecyclerView mRvLine1;
    private RecyclerView mRvLine2;
    private AppWebView appWebView;
    private HeaderAndFooterRecyclerViewAdapter adapterVp1;
    public BaseMultipleTypeRvAdapter outlineAdapter;
    private String detailUrl;
    private int datailId;
    private List<Object> dataiList;
    private Banner mBanner;
    private List<Integer> BannerStrings;
    private TextView mBannerTitleTv;
    private ImageView mBannerHeadIv;
    private RecyclerView mBannerRv;
    private List<PublicTeacherBean> teacherBeans;
    private List<String> mListTeach;
    private CommonAdapter<String> commonAdapter;
    public static final int numAdd = 0;

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.course_activity_wx_detail;
    }


    @Override
    public void initAppStatusBar() {
        setTranslucentBar();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPageTitle("课程详情");
        setBackText("");
        getTitleTextView().setVisibility(View.VISIBLE);
        dataiList = new ArrayList<>();
        mListTeach = new ArrayList<>();
        teacherBeans = new ArrayList<>();
        BannerStrings = new ArrayList<>();
        mShareProfitTv = findViewById(R.id.tv_share_profit);
        mViewShareProfit = findViewById(R.id.view_share_profit);
        //只有一个按钮的
        mConfirmTv = findViewById(R.id.tv_confirm);
        mNormalBottom = findViewById(R.id.view_bottom_nomral);
        mRv = findViewById(R.id.rv);
        mIvVip = findViewById(R.id.iv_vip);
        //底部
        mAssembleView = findViewById(R.id.view_assemble);
        //拼团部分
        mAssembleStockTv = findViewById(R.id.tv_assemble_stock);
        mStatusView.setEmptyViewResId(R.layout.course_layout_empty);
        //新加
        mBanner = findViewById(R.id.course_detail_banner);
        mBannerRv = findViewById(R.id.banner_rv);
        mBannerTitleTv = findViewById(R.id.banner_title);
        mBannerHeadIv = findViewById(R.id.banner_head);
        mBelowPageTv1 = findViewById(R.id.detail_below_vp_1);
        mBelowPageTv2 = findViewById(R.id.detail_below_vp_2);
        mBelowPageTv3 = findViewById(R.id.detail_below_vp_3);
        mBelowPageLlne1 = findViewById(R.id.detail_below_vp_1_line);
        mBelowPageLlne2 = findViewById(R.id.detail_below_vp_2_line);
        mBelowPageLlne3 = findViewById(R.id.detail_below_vp_3_line);
        mBelowPageView1 = findViewById(R.id.detail_below_vp_1_view);
        mBelowPageView2 = findViewById(R.id.detail_below_vp_2_view);
        mBelowPageView3 = findViewById(R.id.detail_below_vp_3_view);
//        scrollView = findViewById(R.id.course_detail_scroll);
        mViewpage = findViewById(R.id.detail_below_view_page);
        mBelowPageLlne1.setOnClickListener(onClickListenerVp);
        mBelowPageLlne2.setOnClickListener(onClickListenerVp);
        mBelowPageLlne3.setOnClickListener(onClickListenerVp);
        adapter = CourseHelper.createDefaultAdapter();
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(adapter);
        mRv.addItemDecoration(SpaceItemDecoration.create().setSpace(0).setHeadItemCount(0).setIncludeEdge(false).setIncludeFirst(false));
//        scrollView.setOnScrollChangedListener(this);//滑动监听
        setmBannerGoodsDetails();
        setmViewpage();
        initShare();
        ViewHelper.setViewVisible(findViewById(R.id.view_service), ConfigManager.getInstance().isShowPersonService());
        //课程大纲
        setCommonAdapter();
        addInfoModule();
        addTabLayout();
        initToolBarSettings();

    }

    private void initShare() {
        ToolBarHelper.addRightImageView(getToolBar(), R.drawable.public_ic_share, v -> mPresenter.getShareInfo(CourseDetailPresenter.SHARE_FROM_NORMAL));
        mShareIv = (ImageView) getToolBar().getChildAt(getToolBar().getChildCount() - 1);
        ImageViewCompat.setImageTintList(mShareIv, ColorStateList.valueOf(Color.BLACK));
        ViewHelper.setViewVisible(mShareIv, ConfigManager.getInstance().isShowShare());
    }

    private void initToolBarSettings() {
        ((View) getToolBar().getParent()).setBackground(null);
        //添加toolbar分割线
        UiStatusBarUtil.statusBarLightMode(this);
        StatusBarUtil.setColor(this, 255, 255, 255, 255);
        mViewLine = LayoutInflater.from(getActivity()).inflate(R.layout.course_layout_detail_line, null);
//     getToolBar().getParent() is   ActionBarContainer
        ((FrameLayout) getToolBar().getParent()).addView(mViewLine);
        mViewLine.setVisibility(View.GONE);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mViewLine.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.height = DensityUtil.dip2px(1);
        layoutParams.width = Toolbar.LayoutParams.MATCH_PARENT;
        mViewLine.setLayoutParams(layoutParams);
        //需要在mViewLine 创建成功之后设置背景
        setIconShowInit(false);
//        setToolBarBg(225);
        //测量偏移量
        View parent = (View) getToolBar().getParent();
        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                offset = parent.getTop() + parent.getHeight();
                maxDistance -= offset;

            }
        });


        //需要在actionbar
    }


    private void setCommonAdapter() {
        mBannerRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBannerRv.addItemDecoration(new ListItemDecoration(getBaseContext(), 5, R.color.transparent));
        commonAdapter = new CommonAdapter<String>(getBaseContext(), mListTeach, R.layout.course_item_list_title) {
            @Override
            public void convert(ViewHolder holder, String item, int position) {
                holder.setText(R.id.item_list_content, item);
            }
        };
        mBannerRv.setAdapter(commonAdapter);
    }

    private void addTabLayout() {
        tabLayout = ToolBarHelper.addCenterView(getToolBar(), R.layout.course_layout_detail_control_tab).findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.GONE);
    }

    //用来装不需要定位的item
    private LinearLayout mHeadLl;

    /**
     * 课程大纲
     */
    private void addInfoModule() {
        if (mHeadLl == null) {
            createHeadView();
        }
        //这个需要提前new
        outlineHolder = new DetailOutlineHolder(mRv, WxCourseDetailActivity.this);

    }

    /**
     * 课程大纲
     */
    private void createHeadView() {
        mHeadLl = new LinearLayout(this);
        mHeadLl.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mHeadLl.setOrientation(LinearLayout.VERTICAL);
        mHeadLl.setVisibility(View.INVISIBLE);
    }

    private float mLastAlpha = -1;

    /**
     * 渐变标题
     *
     * @param mDistance
     */

    private void setToolBarBg(int mDistance) {
        float percent = mDistance * 1.0f / maxDistance;
        mViewLine.setVisibility(percent >= 1 ? View.VISIBLE : View.GONE);
        percent = percent > 1 ? 1 : percent;
        //得到当前应当设置给标题栏的透明度
        int alpha = (int) (percent * 255);
        if (mLastAlpha == alpha) {
            return;
        }
        if (mLastAlpha < 255 && alpha >= 255) {
            setIconShowInit(false);
        } else if (mLastAlpha >= 255 && percent < 255) {
            setIconShowInit(true);
        }

        Logger.d("aplha" + alpha);
//        getToolBar().setBackgroundColor(Color.argb(alpha, 255, 255, 255));
//        StatusBarUtil.setColor(this, alpha, 255, 255, 255);
        UiStatusBarUtil.statusBarLightMode(this);
        getToolBar().setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.white));
//        StatusBarUtil.setColor(this, alpha, 255, 255, 255);
        Log.e("设置的数据：", alpha + "");
//        mLastAlpha = alpha;
        mLastAlpha = 225;

    }

    private void setIconShowInit(boolean showInit) {

        changeViewWidth(showInit ? 16 : 16);
        getBackIconView().setImageResource(showInit ? R.drawable.course_ic_back_init : R.drawable.share_info_new_arrow);
        mShareIv.setImageResource(showInit ? R.drawable.course_ic_share_init : R.drawable.share_info_new);
        ImageViewCompat.setImageTintList(getBackIconView(), showInit ? null : ColorStateList.valueOf(Color.BLACK));
        ImageViewCompat.setImageTintList(mShareIv, showInit ? null : ColorStateList.valueOf(Color.BLACK));
        tabLayout.setVisibility(View.GONE);
        if (showInit) {
            getTitleTextView().setVisibility(View.GONE);
        } else {
            getTitleTextView().setVisibility(View.VISIBLE);
        }
    }

    private void changeViewWidth(int size) {
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) mShareIv.getLayoutParams();
        layoutParams.width = DensityUtil.dip2px(size);
        layoutParams.height = layoutParams.width;
        LinearLayout.LayoutParams backlayoutParams = (LinearLayout.LayoutParams) getBackIconView().getLayoutParams();
        backlayoutParams.width = layoutParams.width;
        backlayoutParams.height = layoutParams.height;
        backlayoutParams.leftMargin = DensityUtil.dip2px(-1);
        mShareIv.setLayoutParams(layoutParams);
        getBackIconView().setLayoutParams(backlayoutParams);
        mShareIv.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }


    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    @Override
    protected void registerListener() {
        findViewById(R.id.view_service).setOnClickListener(v -> JumpHelper.jumpPersonService());
        mViewShareProfit.setOnClickListener(v -> {
            mPresenter.getShareInfo(CourseDetailPresenter.SHARE_FROM_DISTRIBUTION);
        });
        mStatusView.setOnRetryClickListener(v -> processLogic(null));
        RefreshListDataHelper.registerCourseHasBuyChange(this, outlineHolder.outlineAdapter);
        //大纲的适配器的点击
        outlineHolder.outlineAdapter.setOnItemClickListener((holder, position, view, item) -> {
            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
            //属于节
            if (!(item instanceof SectionBean)) {
                return;
            }
            SectionBean sectionBean = (SectionBean) item;
            if (checkNotAllowClickSection(sectionBean)) {
                return;
            }
            //图文课 去h5页面
            if (ConstsCouseType.isImgTxt(sectionBean.getCourseType())) {
                CourseHelper.goImgTxtCourseAndCheckLimit(this
                        , mPresenter.getCourseBean().getId()
                        , sectionBean.getId()
                        , sectionBean.getPeriodsTitle()
                        , String.valueOf(CourseHelper.getSystemCourseId(getActivity())));
                return;
            }
            //可以播放的课
            if (CourseHelper.isCanPlay(sectionBean.getCourseType())) {
                mOutLinePresenter.play(sectionBean.getId(), sectionBean.getCourseType());
            }

        });
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dy为0说明是tablelayout定位的
                if (dy != 0) {
                    changeTabVisible();
                }
//                setToolBarBg(RvHelper.getScollyDistance(recyclerView));
            }
        });
        mConfirmTv.setOnClickListener(v -> {
            mPresenter.btnConfirm();
        });


        LiveDataBus.get().with(LiveDataBusEvent.COURSE_HAS_BUY_SUCCESS_BY_PAY, Integer.class).observe(this, integer -> {
            if (integer != null && integer >= 0 && mPresenter != null) {
                processLogic(null);
            }
            //如果是公开课的话刷新一下
        });

        LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE, Boolean.class).observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                processLogic(null);
            }
        });
        LiveDataBus.get().with(LiveDataBusEvent.REMOVE_COURSE_SUCCESS, Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer courseId) {
                if (mPresenter.checkIsCurrentCourse(courseId == null ? 0 : courseId)) {
                    processLogic(null);
                }
            }
        });

        mAssembleView.setOnLeftClickListener(v -> mPresenter.singleBuy());
        mAssembleView.setOnRightClickListener(v -> mPresenter.assembleBuy());
    }

    private void registerTabChangeListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                RvHelper.forceStopRecyclerViewScroll(mRv);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRv.getLayoutManager();
                mLayoutManager.scrollToPositionWithOffset(noNeedPositionNum + (int) tab.getTag(), offset);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public boolean checkNotAllowClickSection(SectionBean sectionBean) {
        //不可点击的课程类型
//        if (!ConstsCouseType.isCanClickInCourseDetail(sectionBean.getCourseType())) {
//            return true;
//        }
        if (mPresenter.isCourseLimit()) {
            ToastUtil.shortShow(this, R.string.course_limit);
            return true;
        }
        if (sectionBean.isCanTrySee()) {
            return false;
        }
        //非试看状态下 判断登录状态
        if (JumpHelper.checkLogin()) {
            return true;
        }
        // 没有加入学习活着购买的情况
        if (mPresenter.isJoinSpell() && !mPresenter.isSpellSuceess()) {
            ToastUtil.shortShow(getActivity(), "未成团不能观看");
            return true;
        }
        if (!this.publicCourseDetailBean.isBuy()) {
            ToastUtil.shortShow(getActivity(), "请购买课程后查看");
            return true;
        }
        if (!this.publicCourseDetailBean.isJoinStudy()) {
            ToastUtil.shortShow(getActivity(), "请加入学习后查看");
            return true;
        }
        return false;
    }

    /**
     * 不需要定位的数量 不需要定位的都放在了header了
     */
    private int noNeedPositionNum = 1;

    public void changeTabVisible() {

        LinearLayoutManager layoutManager = (LinearLayoutManager) mRv.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();

        //判断是head 并且 head的bottom 超出范围
        if (first == 0) {
            View viewByPosition = layoutManager.findViewByPosition(first + 1);
            if (viewByPosition == null || viewByPosition.getTop() > offset) {
                tabLayout.setVisibility(View.GONE);
                return;
            }
        }
        tabLayout.setVisibility(View.VISIBLE);
        int end = layoutManager.findLastVisibleItemPosition();

        for (int i = end; i >= first; i--) {
            if (layoutManager.findViewByPosition(i).getTop() <= offset) {
                //-1 是因为头部占了一个位置
                tabLayout.setScrollPosition(i - 1, 0, true);
                break;
            }
        }

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getDetail();
    }


    @Override
    public void takeView() {
        super.takeView();
        mOutLinePresenter.takeView(this);
    }

    @Override
    public void dropView() {
        mOutLinePresenter.dropView();
        super.dropView();

    }

    /**
     * 课程大纲数据
     *
     * @param data
     */
    @Override
    public void setOutLineData(List<Object> data) {
        Log.e("数据测试：", detailUrl + "id:" + datailId);
        dataiList = data;
        setmOutLineDate(data);
//        outlineHolder.bindData(data, 0, null);

    }

    @Override
    public PublicCourseBean getCourseBean() {
        return publicCourseDetailBean;
    }

    @Override
    public void updateSignAndLimitNumber(PublicCourseDetailBean publicCourseDetailBean) {
        baseInfoHolder.updateSignUpAndLimitNumber(publicCourseDetailBean);
    }

    private PublicCourseDetailBean publicCourseDetailBean;

    @Override
    public void setInfo(List<PublicCouponBean> couponInfo, PublicCourseDetailBean
            publicCourseDetailBean) {
        this.publicCourseDetailBean = publicCourseDetailBean;
        teacherBeans.clear();
        teacherBeans.addAll(publicCourseDetailBean.getTeachers());
        //新加的
        setmBanner();
//        mBanner.setImages(BannerStrings).setImageLoader(new GlideImageLoader())
//                .setBannerStyle(BannerConfig.NUM_INDICATOR).setIndicatorGravity(BannerConfig.RIGHT).start();

        setBaseInfoBelow(publicCourseDetailBean);
        initBottomUi();
        //课程大纲
        reSetRecycleViewHeader();
        setBaseInfo(publicCourseDetailBean, couponInfo);
        setActivityInfo(couponInfo, publicCourseDetailBean);
        //需要在显示的时候加载，否则高度不对  课程介绍
//        setDescInfo(publicCourseDetailBean);
        setAppWebViewUrl(publicCourseDetailBean.getCourseDetails());
        setAssembleInfo();
        setOutLineInfo(publicCourseDetailBean);
        setTeacherInfoUi(publicCourseDetailBean.getTeachers());
        //课程评价
//        setCommentInfo(publicCourseDetailBean);
//        setEvalualeDate(publicCourseDetailBean);
        setCommentInfoNew(publicCourseDetailBean);
    }

    private void initBottomUi() {
        mNormalBottom.setVisibility(View.GONE);
        mAssembleStockTv.setVisibility(View.GONE);
        mAssembleView.setVisibility(View.GONE);

    }

    private void setAssembleInfo() {
        detailJoinNumberHolder = new DetailJoinNumberHolder(mHeadLl);
        mHeadLl.addView(detailJoinNumberHolder.itemView);
        detailAssemblePlayHolder = new DetailAssembleRuleHolder(mHeadLl);
        mHeadLl.addView(detailAssemblePlayHolder.itemView);
        detailJoinNumberHolder.itemView.setVisibility(View.GONE);
        detailAssemblePlayHolder.itemView.setVisibility(View.GONE);

    }

    /**
     * 教学团队
     *
     * @param data
     */
    private void setTeacherInfoUi(List<PublicTeacherBean> data) {
//        DetailTeacherHolder detailTeacherHolder = new DetailTeacherHolder(mHeadLl);
//        mHeadLl.addView(detailTeacherHolder.itemView);
//        detailTeacherHolder.bindData(data, 0, null);

    }


    /**
     * 课程评价
     *
     * @param publicCourseDetailBean
     */
    private void setCommentInfo(PublicCourseDetailBean publicCourseDetailBean) {
        if (ConstsCouseType.isSystem(publicCourseDetailBean.getCourseType())) {
            return;
        }
        if (commentHolder == null) {
            commentHolder = new DetailCommentHolder(mRvLine2, this);
        }
        //非系统课才有
        adapter.addHeaderView(commentHolder.getConvertView());
        //  这个为了避免 二次刷新重新加载的问题
        commentHolder.bindData(publicCourseDetailBean.getId(), 0, null);


    }

    /**
     * 课程评价
     *
     * @param publicCourseDetailBean
     */
    private boolean commentInfoNew = false;

    private void setCommentInfoNew(PublicCourseDetailBean publicCourseDetailBean) {
        if (commentInfoNew) {
            return;
        }
        if (ConstsCouseType.isSystem(publicCourseDetailBean.getCourseType())) {
            return;
        }
        if (commentHolder == null) {
            commentHolder = new DetailCommentHolder(mRvLine2, this);
        }
        //非系统课才有
        newAdapter.addHeaderView(commentHolder.getConvertView());
        //  这个为了避免 二次刷新重新加载的问题
        commentHolder.bindData(publicCourseDetailBean.getId(), 0, null);
        commentInfoNew = true;
    }


    /**
     * 课程大纲数据
     *
     * @param publicCourseDetailBean
     */
    private void setOutLineInfo(PublicCourseDetailBean publicCourseDetailBean) {
        //大纲
        adapter.addHeaderView(outlineHolder.getConvertView());
        outlineHolder.setTitleByCourseType(publicCourseDetailBean.getCourseType());
        int courseType = publicCourseDetailBean.getCourseType();
        mBelowPageTv2.setText(getString(ConstsCouseType.isSystem(courseType) ? R.string.course_detail_public_course_outline : R.string.course_detail_title_outline));
        if (ConstsCouseType.isSystem(publicCourseDetailBean.getCourseType())) {
            mOutLinePresenter.getPublicCourseOutLine();
        } else {
            mOutLinePresenter.getOutLine();

        }
    }

    /**
     * 课程介绍
     *
     * @param publicCourseDetailBean
     */
    private void setDescInfo(PublicCourseDetailBean publicCourseDetailBean) {
        //以前的
        DetailDescHolder descHolder = new DetailDescHolder(mRv);
        //详情
        descHolder.bindData(publicCourseDetailBean.getCourseDetails(), 0, null);
        adapter.addHeaderView(descHolder.getConvertView());

    }

    /**
     * 优惠 服务
     *
     * @param couponInfo
     * @param publicCourseDetailBean
     */
    private void setActivityInfo(List<PublicCouponBean> couponInfo, PublicCourseDetailBean publicCourseDetailBean) {
//        activityInfoHolder = new DetailActivityInfoHolder(mHeadLl);
//
//        mHeadLl.addView(activityInfoHolder.itemView);
//        //优惠券
//        boolean needShowActivityInfo = (couponInfo != null && couponInfo.size() > 0)
//                || publicCourseDetailBean.isVipCourse()
//                || !publicCourseDetailBean.isServiceEmpty();
//        if (needShowActivityInfo) {
//            activityInfoHolder.setInfo(couponInfo, publicCourseDetailBean);
//        }
//        activityInfoHolder.itemView.setVisibility(needShowActivityInfo ? View.VISIBLE : View.GONE);
//        activityInfoHolder.setMarginBottom(activityInfoHolder.itemView.getVisibility() == View.VISIBLE ? 0 : 10);

    }

    /**
     * 头部布局
     *
     * @param publicCourseDetailBean
     */
    private void setBaseInfo(PublicCourseDetailBean publicCourseDetailBean, List<PublicCouponBean> couponInfo) {
        baseInfoHolder = new DetailBaseInfoHolder(mHeadLl);

        //作为顶部不需要定位的
        mHeadLl.addView(baseInfoHolder.itemView);
        //基础信息
        baseInfoHolder.bindData(publicCourseDetailBean, 0, null);

        boolean needShowActivityInfo = (couponInfo != null && couponInfo.size() > 0)
                || publicCourseDetailBean.isVipCourse()
                || !publicCourseDetailBean.isServiceEmpty();
        baseInfoHolder.setInfo(couponInfo, publicCourseDetailBean, needShowActivityInfo);
        baseInfoHolder.setClickCallBack(isCollect -> mPresenter.collect(isCollect));
    }


    private void setBaseInfoBelow(PublicCourseDetailBean publicCourseDetailBean) {
//        DetailBelowHolder detailBelowHolder = new DetailBelowHolder(mRv);
//        detailBelowHolder.bindData(publicCourseDetailBean,0,null);
//        adapter.addHeaderView(detailBelowHolder.getConvertView());
        detailUrl = publicCourseDetailBean.getCourseDetails();
        datailId = publicCourseDetailBean.getId();

    }


    /**
     * 课程大纲
     */
    private void reSetRecycleViewHeader() {
        if (mHeadLl == null) {
            //课程大纲
            createHeadView();
        }
        mHeadLl.setVisibility(View.VISIBLE);
        mHeadLl.removeAllViews();
        adapter.getHeaderViews().clear();
        adapter.notifyDataSetChanged();
        adapter.addHeaderView(mHeadLl);
    }


    @Override
    public void collectStateChange(int id, boolean collectState) {
        baseInfoHolder.setImageResource(R.id.iv_collect, collectState ? R.drawable.public_ic_collected : R.drawable.public_ic_un_collect);
    }

    @Override
    public void setBottomBtnTxt(String text, boolean showVipLogo) {
        mNormalBottom.setVisibility(View.VISIBLE);
        mConfirmTv.setText(text);
        mIvVip.setVisibility(showVipLogo ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showShare(ShareInfo shareInfo, int form) {
        boolean fromDistribution = CourseDetailPresenter.SHARE_FROM_DISTRIBUTION == form;
        //分销一定需要
        boolean needShareImg = true;
        if (!fromDistribution) {
            needShareImg = ConfigManager.getInstance().isAvailableTemplateShare();
        }
        new CommonShareDialog(this, needShareImg)
                .setShareTip(shareInfo.getShareTip())
                .setOnItemClickListener((position, view, item) -> {
                    if (item.getType() == ShapeTypeConfig.IMG) {
                        if (fromDistribution) {
                            mPresenter.goDistributionSharePage();
                            return;
                        }
                        ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_SHARE_IMG).withParcelable("shareInfo", shareInfo).navigation();
                        return;
                    }
                    JPushHelper.platFormShare(getApplicationContext(), shareInfo, item, new JPushHelper.ShareListener(WxCourseDetailActivity.this));

                }).show();
    }

    @Override
    public void refreshSignUpInfo(PublicCourseDetailBean publicCourseDetailBean) {
        if (baseInfoHolder != null) {
            baseInfoHolder.updateSignUpAndLimitNumber(publicCourseDetailBean);
        }
    }

    @Override
    public void jumpSystemCourseFirst() {
        if (outlineHolder.outlineAdapter.getItemCount() > 0 && (outlineHolder.outlineAdapter.getItem(0) instanceof PublicCourseBean)) {
            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL)
                    .withInt("courseId", ((PublicCourseBean) outlineHolder.outlineAdapter.getItem(0)).getId())
                    .navigation();
        }
    }


    @Override
    public void setJoinInfo(List<AssembleJoinInfoBean> datas, int assembleJoinNumber, boolean needShow) {
        if (needShow) {
            detailJoinNumberHolder.itemView.setVisibility(View.VISIBLE);
            detailJoinNumberHolder.bindData(assembleJoinNumber, datas);
        }

    }

    @Override
    public void showAssembleAction(boolean isNeedShowAssembleAction) {
        detailAssemblePlayHolder.itemView.setVisibility(isNeedShowAssembleAction ? View.VISIBLE : View.GONE);
        mAssembleView.setVisibility(isNeedShowAssembleAction ? View.VISIBLE : View.GONE);
        mAssembleStockTv.setVisibility(isNeedShowAssembleAction ? View.VISIBLE : View.GONE);
        mNormalBottom.setVisibility(isNeedShowAssembleAction ? View.GONE : View.VISIBLE);

    }

    @Override
    public void setAssembleActionUi(boolean isJoinedAssemble, int stockNumber, String price, String assemblePrice, int userNumber) {

        //放在 设置价格上面
        mAssembleView.setLeftEnable(!isJoinedAssemble);
        //有库存，或者已经参与了拼团
        mAssembleView.setRightEnable(isJoinedAssemble || stockNumber > 0);

        mAssembleStockTv.setText(stockNumber > 0 ? MessageFormat.format(getString(R.string.course_fmt_assemble_left_stock_over_zero), String.valueOf(stockNumber)) :
                getString(R.string.course_fmt_assemble_left_stock_empty));
        String singleBuyStr = getString(R.string.course_assemble_single_buy);
        String assembleBuyStr = getString(isJoinedAssemble ? R.string.course_assemble_look_detail : R.string.course_assemble_together_buy);
        mAssembleView.setLeftPrice(price);
        mAssembleView.setRightPrice(assemblePrice);
        mAssembleView.setLeftText(singleBuyStr);
        mAssembleView.setRightText(assembleBuyStr);


    }

    @Override
    public void setAssemnleInfo(AssembleCourseBean assemnleInfo) {
        if (baseInfoHolder != null) {
            baseInfoHolder.setAssembleInfo(assemnleInfo);
        }
    }

    @Override
    public void setShowCouponByAssemble(boolean isShow) {
        //这个只能做隐藏
        if (activityInfoHolder != null && activityInfoHolder.itemView.getVisibility() == View.VISIBLE) {
            if (!isShow) {
                activityInfoHolder.itemView.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void setTab(int courseType) {
        if (tabLayout.getTabCount() > 0) {
            return;
        }
        tabLayout.addTab(tabLayout.newTab().setTag(0).setText("课程介绍"));
        tabLayout.addTab(tabLayout.newTab().setTag(1).setText(getString(R.string.course_detail_title_outline)));
        if (ConstsCouseType.isSystem(courseType)) {
            tabLayout.getTabAt(1).setText(getString(R.string.course_detail_public_course_outline));
        } else {
            tabLayout.addTab(tabLayout.newTab().setTag(2).setText("课程评价"));
        }
        //这个需要在add之后否则刚add 会触发选中的方法回调 导致页面移动
        registerTabChangeListener();

    }

    @Override
    public void setShareProfit(PublicDistributionBean data) {
        ViewHelper.setViewVisible(mViewShareProfit, data != null);
        if (data == null) {
            return;
        }
        String result = data.isShowRate() ? (data.getCommissionRate() + "%") : PriceHelper.getCommonPrice(data.getCommission());
        mShareProfitTv.setText(String.format("分享赚%s", result));
    }

    @Override
    public void showNoDataView() {
        super.showNoDataView();
        setIconShowInit(false);
        mViewLine.setVisibility(View.VISIBLE);
        mShareIv.setVisibility(View.GONE);
        getTitleTextView().setVisibility(View.VISIBLE);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        mStatusView.getEmptyView().findViewById(R.id.tv_look_other).setOnClickListener(v -> {
                    LiveDataBus.get().with(LiveDataBusEvent.MAIN_TAB_SWITCH).postValue(ConstsHomeTab.TAB_INDEX);
                    ARouter.getInstance().build(RouterConstant.PAGE_MAIN).navigation();
                }
        );
    }


    private View.OnClickListener onClickListenerVp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.detail_below_vp_1_line) {
                //课程介绍
                mViewpage.setCurrentItem(0);
            } else if (v.getId() == R.id.detail_below_vp_2_line) {
                //课程大纲
                Log.e("单机了课程大纲", "");
                mViewpage.setCurrentItem(1);
            } else if (v.getId() == R.id.detail_below_vp_3_line) {
                //课程评价
//                setCommentInfoNew(publicCourseDetailBean);
                mViewpage.setCurrentItem(2);
            }
        }
    };

    private void setmViewpage() {
        mViewList = new ArrayList<>();
        newAdapter = CourseHelper.createDefaultAdapter();
        LayoutInflater lf = LayoutInflater.from(getBaseContext());
        View line_1 = lf.inflate(R.layout.course_layout_detail_wx_desc, null);
        View line_2 = lf.inflate(R.layout.course_fragment_detail_new, null);
        View line_3 = lf.inflate(R.layout.course_fragment_detail, null);
        mViewList.add(line_1);
        mViewList.add(line_2);
        mViewList.add(line_3);
        appWebView = line_1.findViewById(R.id.webView);
        mRvLine1 = line_2.findViewById(R.id.course_detail_below_rv);
        mRvLine2 = line_3.findViewById(R.id.course_detail_below_rv);
        mViewpage.setAdapter(new ViewPagerAdatper(mViewList));
        mViewpage.setCurrentItem(0);
        mViewpage.setOffscreenPageLimit(3);

        setAppWebView(appWebView);

        mRvLine1.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        outlineAdapter = Module_courseAdapterHelper.getOutlineAdapter(getBaseContext());
        mRvLine1.setAdapter(outlineAdapter);
        mRvLine1.setNestedScrollingEnabled(false);
        mRvLine2.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        mRvLine2.setAdapter(newAdapter);
//        setEvaluale(line_3);
        TextPaint tp1 = mBelowPageTv1.getPaint();
        tp1.setFakeBoldText(true);
        mViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDetailColor(position);
                mViewpage.requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置课程介绍
     */
    private void setAppWebView(AppWebView webView) {
        webView.setOnLongClickListener(v -> true);
        webView.addJavascriptInterface(new WebViewHelper.BjyJavascriptInterface(), WebViewHelper.BjyJavascriptInterface.FUNCTIONNAME);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                WebViewHelper.addImageClickListener((AppWebView) webView);
                ((AppWebView) webView).pageFinishLoadImg();
//                ((AppWebView) getView(R.id.webView)).resizeVideo();
                Logger.d("AppWebView onPageFinished");
                webView.loadUrl("javascript:document.body.style.marginTop=\"" + 10 + "px\"; void 0");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                JumpHelper.jumpWebView(s);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                JumpHelper.jumpWebView(webResourceRequest.getUrl().toString());
                return true;
            }
        });
    }

    /**
     * 设置课程介绍数据
     *
     * @param model
     */
    private void setAppWebViewUrl(String model) {
        String htmlData = model;
        if (BuildConfig.DEBUG) {
            htmlData = "<!DOCTYPE HTML>\n" +
                    "<html>\n" +
                    "<body style=\"background:red\">\n" +
                    "\n" +
                    "\n" +
                    "Your browser does not support the video tag.\n" +
                    "</video>\n" +
                    "\n" +
                    "<img width=\"100px\" style=\"clear:both\" src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "<img width=\"100px\" src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "   <img src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "   \n" +
                    "   <img width=\"100px\" src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        }

        appWebView.loadHtmlContent(htmlData);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (appWebView != null) {
            appWebView.release();
        }
        adaType = false;
        adaNun = 0;
        if (mBanner != null) {
            mBanner.releaseBanner();
        }
    }

    /**
     * 设置大纲数据
     *
     * @param model
     */
    private boolean OutLineDate = false;

    private void setmOutLineDate(List<Object> model) {
        if (OutLineDate) {
            return;
        }
        //为了刷新
        outlineAdapter.clear();
        if (CourseHelper.needAddOnlySectionTag(model)) {
            //标记纯section列表
            outlineAdapter.setTag(true);
        } else if (model.get(0) instanceof PublicCourseBean) {

//            ((RecyclerView) getView(R.id.rv)).addItemDecoration(SpaceItemDecoration.create().setIncludeEdge(true).setIncludeFirst(false).setSpace(12));
            //有阴影去掉4个dp
//            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getView(R.id.rv).getLayoutParams();
//            layoutParams.leftMargin = DensityUtil.dip2px(11);
//            layoutParams.rightMargin = DensityUtil.dip2px(11);
//            getView(R.id.rv).setLayoutParams(layoutParams);
        }
        outlineAdapter.addAll(model, true);
        if (CourseHelper.isChapterFirst(model)) {
            ExpandHelper.expandOrCollapseTree(outlineAdapter, 0);
        }
        OutLineDate = true;
        outlineAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, View view, Object item) {
                if (ClickUtils.isFastDoubleClick()) {
                    return;
                }
                //属于节
                if (!(item instanceof SectionBean)) {
                    return;
                }
                SectionBean sectionBean = (SectionBean) item;
                if (checkNotAllowClickSection(sectionBean)) {
                    return;
                }
                //图文课 去h5页面
                if (ConstsCouseType.isImgTxt(sectionBean.getCourseType())) {
                    CourseHelper.goImgTxtCourseAndCheckLimit(WxCourseDetailActivity.this
                            , mPresenter.getCourseBean().getId()
                            , sectionBean.getId()
                            , sectionBean.getPeriodsTitle()
                            , String.valueOf(CourseHelper.getSystemCourseId(getActivity())));
                    return;
                }
                //可以播放的课
                if (CourseHelper.isCanPlay(sectionBean.getCourseType())) {
                    mOutLinePresenter.play(sectionBean.getId(), sectionBean.getCourseType());
                }
            }
        });
    }


    /**
     * 设置选中颜色
     *
     * @param index
     */
    private void setDetailColor(int index) {
        TextPaint tp1 = mBelowPageTv1.getPaint();
        TextPaint tp2 = mBelowPageTv2.getPaint();
        TextPaint tp3 = mBelowPageTv3.getPaint();
        tp1.setFakeBoldText(false);
        tp2.setFakeBoldText(false);
        tp3.setFakeBoldText(false);
        mBelowPageTv1.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.common_main_text_color_subtitle));
        mBelowPageTv2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.common_main_text_color_subtitle));
        mBelowPageTv3.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.common_main_text_color_subtitle));
        setViis(mBelowPageView1, false);
        setViis(mBelowPageView2, false);
        setViis(mBelowPageView3, false);
        if (index == 0) {
            tp1.setFakeBoldText(true);
            setViis(mBelowPageView1, true);
            mBelowPageTv1.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.course_detail));
        } else if (index == 1) {
            tp2.setFakeBoldText(true);
            setViis(mBelowPageView2, true);
            mBelowPageTv2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.course_detail));
        } else {
            tp3.setFakeBoldText(true);
            setViis(mBelowPageView3, true);
            mBelowPageTv3.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.course_detail));
        }
    }

    private void setViis(View viis, boolean type) {
        if (type) {
            viis.setVisibility(View.VISIBLE);
        } else {
            viis.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScrollChanged(NestedScrollView who, int l, int t, int oldl, int oldt) {
        int scrollY = who.getScrollY();
//        setToolBarBg(scrollY);
    }

    public class ViewPagerAdatper extends PagerAdapter {
        private List<View> mViewList;

        public ViewPagerAdatper(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));

            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

    /**
     * 轮播数据
     */
    private void setmBanner() {
        BannerStrings.clear();
        for (int i = 0; i < teacherBeans.size(); i++) {
            BannerStrings.add(R.drawable.banner_bg);
        }
        mBanner.setImages(BannerStrings).setImageLoader(new GlideImageLoader()).start();
//                .setBannerStyle(BannerConfig.NUM_INDICATOR).setIndicatorGravity(BannerConfig.RIGHT).start();
    }


    /**
     * 轮播图操作
     */
    private void setmBannerGoodsDetails() {
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (CheckUtil.isListNotEmpty(teacherBeans)) {
                    mBannerTitleTv.setText(teacherBeans.get(i).getName());
                    Glide.with(getBaseContext())
                            .load(teacherBeans.get(i).getTeacherAvatar())
                            .into(mBannerHeadIv);
//                    StringBuilder builder = new StringBuilder();
//                    for (String s : teacherBeans.get(i).getTeacher_title()) {
//                        builder.append("·  " + s + "\n");
//                    }
//                    mBannerContentTv.setText(builder.toString());
                    mListTeach.clear();
                    for (int j = 0; j < teacherBeans.get(i).getTeacher_title().size(); j++) {
                        if (j<2){
                            mListTeach.add(teacherBeans.get(i).getTeacher_title().get(j));
                        }
                    }
                    commonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                JumpHelper.jumpWebViewNoNeedAppTitle(MessageFormat.format("{0}?id={1}&back=1",
                        ConstsH5Url.getTeacher(), String.valueOf(
                                teacherBeans.get(position).getTeacherId())));
            }
        });
    }

}
