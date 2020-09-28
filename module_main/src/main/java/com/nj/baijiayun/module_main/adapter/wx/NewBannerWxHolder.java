package com.nj.baijiayun.module_main.adapter.wx;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.adapter.viewpager.DrawableIndicator;
import com.nj.baijiayun.module_main.bean.NavBean;
import com.nj.baijiayun.module_main.bean.NewBannerBean;
import com.nj.baijiayun.module_main.bean.WxBannerItemBean;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.JumpWrapBean;
import com.nj.baijiayun.module_public.manager.LifecycleManager;
import com.nj.baijiayun.module_public.widget.banner.ImageResourceAdapter;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.holder
 * @describe
 */
@AdapterCreate
public class NewBannerWxHolder extends BaseMultipleTypeViewHolder<WxBannerItemBean> {


    BaseMultipleTypeRvAdapter entranceAdapter;
    private List<NewBannerBean> bannerData;
    private BannerViewPager<NewBannerBean, ImageResourceAdapter.ImageResourceViewHolder> bannerViewPager;

    @SuppressLint("ClickableViewAccessibility")
    public NewBannerWxHolder(ViewGroup parent) {
        super(parent);
        initEntranceView();
        initBanner();
    }

    private void initBanner() {
        bannerViewPager = (BannerViewPager) getView(R.id.banner_view);
        initBannerHeight(bannerViewPager);
        bannerViewPager
                .setOnPageClickListener(position -> {
                    try {
                        NewBannerBean newBannerBean = this.bannerData.get(position);
                        clickBanner(newBannerBean);
                    } catch (Exception ee) {
                        Logger.e("data click Exception" + ee.getMessage());
                    }

                })
                .setAdapter(new ImageResourceAdapter(13));
        DrawableIndicator drawableIndicator = new DrawableIndicator(getContext());
        drawableIndicator.setIndicatorGap(DensityUtil.dip2px(3))
                .setIndicatorDrawable(R.drawable.banner_indicator_nornal, R.drawable.banner_indicator_focus)
                .setIndicatorSize(DensityUtil.dip2px(5), DensityUtil.dip2px(5),
                        DensityUtil.dip2px(18), DensityUtil.dip2px(5));
        bannerViewPager
                .setIndicatorView(drawableIndicator)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorVisibility(View.VISIBLE)
                .setIndicatorGravity(IndicatorGravity.CENTER);

//        bannerViewPager.setIndicatorStyle(IndicatorStyle.DASH)
//                .setIndicatorHeight(BannerUtils.dp2px(3f))
//                .setIndicatorWidth(BannerUtils.dp2px(3), BannerUtils.dp2px(10));
//                .setHolderCreator(() -> new ImageResourceViewHolder(0))
//                .create(mDrawableList)

        LifecycleManager.create((LifecycleOwner) getContext()).addLifecycleCallBack(new LifecycleManager.BaseObserver() {
            @Override
            public void onDestory() {
                super.onDestory();
                if (bannerViewPager != null) {
                    bannerViewPager.stopLoop();
                }
            }
        });
    }

    /**
     * 把点放下面
     * @param bannerViewPager
     */
    private void initBannerHeight(BannerViewPager bannerViewPager) {
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) bannerViewPager.getLayoutParams();
//        layoutParams.height = (DensityUtil.getScreenWidth() - DensityUtil.dip2px(18 * 2)) * 9 / 16;
//        bannerViewPager.setLayoutParams(layoutParams);
    }

    private void clickBanner(NewBannerBean newBannerBean) {
        JumpWrapBean jumpWrapBean = new JumpWrapBean();
        jumpWrapBean.setTypeContent(newBannerBean.getLinkContent());
        jumpWrapBean.setType(newBannerBean.getLinkType());
        JumpHelper.jump(jumpWrapBean);
    }

    private void initEntranceView() {
        RecyclerView view = (RecyclerView) getView(R.id.rv);
        if (entranceAdapter == null) {
            entranceAdapter = Module_mainAdapterHelper.getEntranceAdapter(getContext());
        }
        view.setLayoutManager(new GridLayoutManager(getContext(), 4));
        view.setAdapter(entranceAdapter);
    }

    public void setEntranceNavData(List<NavBean> datas) {
        entranceAdapter.addAll(datas, true);
    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_wx_banner;
    }

    @Override
    public void bindData(WxBannerItemBean model, int position, BaseRecyclerAdapter adapter) {
        this.bannerData = model.getData();
        if (model.getData() != null && model.getData().size() > 0) {
            bannerViewPager.setVisibility(View.VISIBLE);
            bannerViewPager
                    .setPageMargin(DensityUtil.dip2px(7))
                    .setRevealWidth(DensityUtil.dip2px(11))
                    .setPageStyle(PageStyle.NORMAL)
                    .setData(model.getData());
            bannerViewPager.startLoop();
        } else {
            bannerViewPager.setVisibility(View.GONE);
        }


    }
}
