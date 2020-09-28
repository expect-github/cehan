package com.nj.baijiayun.module_public.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.widget.banner.IBannerImage;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.base.IIndicator;
import com.zhpan.indicator.enums.IndicatorSlideMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@Route(path = RouterConstant.PAGE_PUBLIC_IMAGE_PREVIEW)
public class ImgPreviewActivity extends BaseAppActivity {

    private String imageUrl;
    private int index;
    private List<String> imageUrls;
    private BannerViewPager mBanner;

    @Override
    public void initAppStatusBar() {
        setTranslucentBar();
    }

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    public boolean needMultipleStatus() {
        return false;
    }

    @Override
    protected void initParams() {
        super.initParams();
        imageUrl = getIntent().getStringExtra("path");
        //多个才需要
        index = getIntent().getIntExtra("index", -1);
        imageUrls = getIntent().getStringArrayListExtra("paths");
    }


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_img_preview;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        hideToolBar();
        if (TextUtils.isEmpty(imageUrl) && imageUrls == null) {
            return;
        }
        mBanner = findViewById(R.id.banner);
        mBanner.setAdapter(new ImageResourceAdapter(this))
                .setCanLoop(false).setAutoPlay(false).setIndicatorGravity(IndicatorGravity.END).setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorVisibility(View.VISIBLE).setIndicatorView(setupIndicatorView());
        List<IBannerImage> imgs = new ArrayList<>();
        if (imageUrl != null) {
            imgs.add(new PhotoBean(imageUrl));
        } else if (imageUrls != null) {
            for (int i = 0; i < imageUrls.size(); i++) {
                imgs.add(new PhotoBean(imageUrls.get(i)));
            }
        }
        mBanner.setData(imgs);
        mBanner.setCurrentItem(index, false);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public static class PhotoBean implements IBannerImage {
        private String url;

        PhotoBean(String url) {
            this.url = url;
        }

        @Override
        public String getBannerCover() {
            return url;
        }
    }

    public static class ImageResourceAdapter<T extends IBannerImage> extends BaseBannerAdapter<T, ImageResourceAdapter.PhotoViewHolder> {
        private Activity activity;

        ImageResourceAdapter(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onBind(PhotoViewHolder holder, T data, int position, int pageSize) {
            holder.bindData(data, position, pageSize);
        }

        @Override
        public PhotoViewHolder createViewHolder(View itemView, int viewType) {
            return new PhotoViewHolder(itemView, activity);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.public_banner_img_preview;
        }


        protected static class PhotoViewHolder<T extends IBannerImage> extends BaseViewHolder<T> {
            PhotoView photoView;
            ProgressBar mPgr;


            @Override
            public void bindData(T data, int position, int pageSize) {
                Glide.with(photoView.getContext()).load(data.getBannerCover()).fitCenter().into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mPgr.setVisibility(View.GONE);
                        photoView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        mPgr.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        mPgr.setVisibility(View.VISIBLE);

                    }
                });


            }

            public PhotoViewHolder(@NonNull View itemView, Activity activity) {
                super(itemView);
                photoView = findView(R.id.pv_preview_image);
                mPgr = findView(R.id.pgr);
                photoView.setOnClickListener(v -> activity.finish());
            }

        }

    }

    private IIndicator setupIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(this);
        indicatorView.setRadius(BannerUtils.dp2px(18));
        indicatorView.setTextSize(BannerUtils.dp2px(13));
        indicatorView.setBackgroundColor(Color.parseColor("#55000000"));
        return indicatorView;
    }
}
