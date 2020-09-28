package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.basic.widget.dialog.BaseBottomDialog;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.adapter.CouponAdapter;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.response.CouponGetResponse;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.ConstsProtocol;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.schedulers.Schedulers;

public class CouponDialog extends BaseBottomDialog {
    private Context context;
    private List<PublicCouponBean> list;
    private CouponAdapter adapter;


    public CouponDialog(@NonNull Context context, List<PublicCouponBean> list) {
        super(context);
        setContentView(R.layout.public_dialog_coupon);
        this.context = context;
        this.list = list;

        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
        findViewById(R.id.ll_rule).setOnClickListener(v -> {
//            JumpHelper.jumpWebViewNoNeedAppTitle("treaty?name=coupon_use_contract");
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getProtocol(ConstsProtocol.COUPON_USE));
        });
        setCanceledOnTouchOutside(true);
        //点击Dialog外部消失
        initRecycleView();
    }


    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //81 item 高度  21间距  69上面高度  47 规则高度
        lp.height = DensityUtil.dip2px(getRvDpHeight() + 69 + 47);
        getWindow().setAttributes(lp);

    }


    private int getSize() {
        return Math.min(3, this.list.size());
    }

    private int getRvDpHeight() {
        return 81 * getSize() + 21 * (getSize() - 1);
    }


    private void initRecycleView() {

        RecyclerView mRv = findViewById(R.id.rv_coupon);
        ViewGroup.LayoutParams layoutParams = mRv.getLayoutParams();
        layoutParams.height = DensityUtil.dip2px(getRvDpHeight());
        mRv.setLayoutParams(layoutParams);
        adapter = new CouponAdapter(context);
        mRv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        mRv.addItemDecoration(SpaceItemDecoration.create().setSpace(21).setIncludeEdge(false));
        mRv.setAdapter(adapter);
        if (adapter != null) {
            adapter.setOnItemClickListener((holder, position, view, item) -> {
                getCoupon(adapter.getItem(position), position);
            });

        }
        adapter.addAll(list);


    }


    private void getCoupon(PublicCouponBean item, int position) {
        if (item.isCanGet()) {
            NetMgr.getInstance()
                    .getDefaultRetrofit()
                    .create(PublicService.class)
                    .getCoupon(item.getId())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .as(RxLife.asOnMain((LifecycleOwner) context))
                    .subscribe(new BaseSimpleObserver<CouponGetResponse>() {
                        @Override
                        public void onPreRequest() {

                        }

                        @Override
                        public void onSuccess(CouponGetResponse baseResponse) {
                            ToastUtil.shortShow(context, "领取成功");
                            item.setIsCanGet(baseResponse.getData().getIsContinueGet());
                            adapter.notifyItemChanged(position);

                        }

                        @Override
                        public void onFail(Exception e) {
                            ToastUtil.shortShow(context, e.getMessage());

                        }
                    });
        }
    }


}
