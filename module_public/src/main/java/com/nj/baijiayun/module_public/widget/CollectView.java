package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.consts.ConstsCollect;
import com.nj.baijiayun.refresh.recycleview.helper.ContextGetHelper;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author chengang
 * @date 2019-11-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class CollectView extends LinearLayout {

    private ImageView mCollectIv;
    private TextView mCollectTv;
    private PublicService publicService;
    private int collectId;
    private int type;

    private boolean isCollect = false;

    public CollectView(Context context) {
        this(context, null, 0);

    }

    public CollectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        View inflate = LayoutInflater.from(context).inflate(R.layout.public_layout_collect, this);
        mCollectIv = inflate.findViewById(R.id.iv_collect);
        mCollectTv = inflate.findViewById(R.id.tv_collect);
        mCollectTv.setVisibility(GONE);
        publicService = NetMgr.getInstance().getDefaultRetrofit().create(PublicService.class);
        setOnClickListener(v -> {
            if (isCollect) {
                cancelCollect(collectId, type);
            } else {
                collect(collectId, type);
            }

        });
    }

    public CollectView setType(int type) {
        this.type = type;
        return this;
    }

    public CollectView setCollectId(int collectId) {
        this.collectId = collectId;
        return this;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public CollectView setCollectStatus(boolean isCollect) {
        this.isCollect = isCollect;
        initCollectUi();
        return this;
    }

    private void initCollectUi() {
        mCollectIv.setImageResource(isCollect ? R.drawable.public_ic_collected : R.drawable.public_ic_un_collect);
        mCollectTv.setText(isCollect ? R.string.public_collect_status_collected : R.string.public_collect_status_un_collect);
        mCollectTv.setTextColor(ContextCompat.getColor(getContext(), isCollect ? R.color.common_main_color : R.color.public_FF8C8C8C));
    }

    private void collect(int id, int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", String.valueOf(type));
        params.put(ConstsCollect.getParamsKey(type), String.valueOf(id));
        publicService.collect(params)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as((LifecycleOwner) ContextGetHelper.getActivityFromView(this)))
                .subscribe(new BaseSimpleObserver<BaseResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        isCollect = true;
                        initCollectUi();
                        ToastUtil.shortShow(getContext(), getContext().getString(R.string.public_collect_success));

                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtil.shortShow(getContext(), e.getMessage());

                    }
                });
    }

    private void cancelCollect(int id, int type) {

        publicService.cancelCollect(id, type)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as((LifecycleOwner) ContextGetHelper.getActivityFromView(this)))
                .subscribe(new BaseSimpleObserver<BaseResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        isCollect = false;
                        initCollectUi();
                        ToastUtil.shortShow(getContext(), getContext().getString(R.string.public_collect_cancel_success));

                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtil.shortShow(getContext(), e.getMessage());

                    }
                });
    }


}
