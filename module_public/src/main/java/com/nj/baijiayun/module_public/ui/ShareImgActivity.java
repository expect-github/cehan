package com.nj.baijiayun.module_public.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.imageloader.transform.RoundedCornersTransformation;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.widget.dialog.CommonShareDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.QrCodeImgResponse;
import com.nj.baijiayun.module_public.bean.response.ShareTemplateResponse;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.share_login.JPushHelper;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;
import com.nj.baijiayun.module_public.widget.banner.ImageResourceAdapter;
import com.zhpan.bannerview.BannerViewPager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-09-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui
 * @describe
 */
@Route(path = RouterConstant.PAGE_PUBLIC_SHARE_IMG)
public class ShareImgActivity extends BaseAppActivity {
    private BannerViewPager mBanner;
    private RecyclerView mRecyclerView;
    CommonShareDialog.CommonBottomDialogAdapter adapter = new CommonShareDialog.CommonBottomDialogAdapter(this);
    ShareInfo mShareInfo;
    private ImageView mQrCodeIv;
    private TextView mTitleTv;
    private TextView mContentTv;
    private RelativeLayout mRelShareImg;

    @Override
    public boolean needAutoInject() {
        return false;


    }

    @Override
    protected void initParams() {
        super.initParams();
        mShareInfo = getIntent().getParcelableExtra("shareInfo");
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_share_img;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        hideToolBar();
        mBanner = findViewById(R.id.banner);
        mRecyclerView = findViewById(R.id.recyclerView);
        mQrCodeIv = findViewById(R.id.iv_qr_code);
        mTitleTv = findViewById(R.id.tv_title);
        mRelShareImg = findViewById(R.id.rel_share_img);
        mContentTv = findViewById(R.id.tv_content);
        if (mShareInfo != null) {
            mTitleTv.setText(mShareInfo.getTitle());
            mContentTv.setText(mShareInfo.getAbstract());
        }
        mBanner.setAdapter(new ImageResourceAdapter(10)).setAutoPlay(false).setIndicatorVisibility(View.GONE);
    }


    @Override
    protected void registerListener() {
        adapter.setOnItemClickListener((position, view, item) -> {
            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
            if (mShareInfo != null) {
                shotAndShare(item);
            }

        });
        findViewById(R.id.img_close).setOnClickListener(v -> finish());

    }

    private void shotAndShare(CommonShareDialog.ShareBean item) {
        showLoadV();
        View view = mRelShareImg;
        if (view.getWidth() == 0) {
            return;
        }
        Observable.create(emitter -> {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bitmap);
            view.draw(c);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            emitter.onNext(bytes);
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this)).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                closeLoadV();
                Glide.with(getActivity()).downloadOnly().load(o).into(new CustomTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        Logger.d("glideload onResourceReady");

                        if (mShareInfo != null) {
                            mShareInfo.setLocalImgPath(resource.getPath());
                            mShareInfo.openShareImg();
                            JPushHelper.platFormShare(getActivity(), mShareInfo, item, null);

                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        Logger.d("glideload onLoadCleared");

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        // Intentionally empty, this can be optionally implemented by subclasses.
                        Logger.d("glideload onLoadFailed");
                    }
                });

            }

            @Override
            public void onError(Throwable e) {
                closeLoadV();
            }

            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(adapter);
        List<CommonShareDialog.ShareBean> items = CommonShareDialog.generateDefaultShareList();
        adapter.setItems(items);

        getTemplate();
        getQrCodeImg(mShareInfo.getUrl());

    }

    private void getTemplate() {
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getShareTemplateList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseSimpleObserver<ShareTemplateResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(ShareTemplateResponse baseResponse) {
                        mBanner.setData(baseResponse.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtil.shortShow(getActivity(), e.getMessage());

                    }
                });
    }

    private String mbase64;

    private void getQrCodeImg(String url) {
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getQrCodeImg(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(this)).subscribe(new BaseSimpleObserver<QrCodeImgResponse>() {


            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(QrCodeImgResponse qrCodeImgResponse) {
                mbase64 = qrCodeImgResponse.getData().getBase64();

                Glide.with(getActivity())
                        .load(Base64.decode(mbase64.split(",")[1], Base64.DEFAULT))
                        .apply(new RequestOptions().transform(new RoundedCornersTransformation(DensityUtil.dip2px(6), 0, RoundedCornersTransformation.CornerType.ALL)))
                        .into(mQrCodeIv);


            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

}
