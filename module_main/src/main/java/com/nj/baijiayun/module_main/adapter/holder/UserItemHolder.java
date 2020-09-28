package com.nj.baijiayun.module_main.adapter.holder;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.MaskViewHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.mvp.BaseView;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.api.MainService;
import com.nj.baijiayun.module_main.bean.UserItemBean;
import com.nj.baijiayun.module_main.bean.res.DistributeApplyResponse;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.config.SpManger;
import com.nj.baijiayun.module_public.widget.UserItemView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.holder
 * @describe
 */
@AdapterCreate(group = "user_new")
public class UserItemHolder extends BaseMultipleTypeViewHolder<UserItemBean> {
    public static final int TAG_NEED_GO_APPLY = 1;

    public UserItemHolder(ViewGroup parent) {
        super(parent);
        UserItemView view = (UserItemView) getView(R.id.user_item_view);
        view.setBackgroundColor(Color.WHITE);
        view.setLeftIconVisible(true);
        view.setRightIconTint(R.color.public_FFB7B7B7);
        view.setLineColor(R.color.common_F5F5F5);
        view.setCheckListener(isChecked -> {
            MaskViewHelper.getInstance().showMask((Activity) getContext(), isChecked);
        });

        view.setOnClickListener(v -> {

            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
            if (getClickModel().isProtectEyes()) {
                return;
            }
                if (getClickModel().isPersonService()) {
                JumpHelper.jumpPersonService();
                return;
            }
            if (getClickModel().isNeedCheckLogin() && JumpHelper.checkLogin()) {
                return;
            }
            UserItemBean clickModel = getClickModel();
            if (clickModel.isUrlNotEmpty()) {
                if (ConstsH5Url.getDistributePath().equals(clickModel.getUrlPath())) {
                    getApplyInfo();
                    return;

                }

                JumpHelper.jumpWebViewNoNeedAppTitle(clickModel.getUrl());
            } else {
                ARouter.getInstance().build(getClickModel().getPath()).navigation();
            }
        });
    }

    private void goDistribution(boolean apply) {

        JumpHelper.jumpWebViewNoNeedAppTitle(apply ? ConstsH5Url.getDistributeRecruitPath() : ConstsH5Url.getDistributePath());
    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_user_info;
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

//    private BadgeView badgeView;

    @Override
    public void bindData(UserItemBean model, int position, BaseRecyclerAdapter adapter) {
        ((UserItemView) getConvertView()).setTitleStr(model.getTitle());
        int resourceId = getResource(model.getIcon());
        if (resourceId != 0) {
            ((UserItemView) getConvertView()).setLeftIcon(resourceId);
        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getConvertView().getLayoutParams();
        layoutParams.bottomMargin = DensityUtil.dip2px(isNeedShowBottomMargin(position) ? 15 : 0);
        getConvertView().setLayoutParams(layoutParams);
        ((UserItemView) getConvertView()).setViewLineVisible(!isNeedShowBottomMargin(position));
//        if(model)
        ((UserItemView) getConvertView()).setUnReadCount(model.getUnReadCount());


//        if (model.isUnRead()) {
//            if (badgeView == null) {
//                badgeView = new BadgeView(getContext());
//            }
//
//            badgeView.setTargetView(((UserItemView) getConvertView()).getContentTv());
//            badgeView.showRedPoint(model.isUnRead());
//            badgeView.setBadgeGravity(Gravity.START | Gravity.CENTER_VERTICAL);
//
//        } else {
//            if (badgeView != null) {
//                badgeView.setHideOnNull(true);
//            }
//        }
        if (model.isProtectEyes()) {
            ((UserItemView) getConvertView()).setCheck(SpManger.isOpenProtectEyes());
            ((UserItemView) getConvertView()).setImageIconSize(16);
        } else {
            ((UserItemView) getConvertView()).resetIconSize();
        }
        ((UserItemView) getConvertView()).showSwitch(model.isProtectEyes());


    }

    private boolean isNeedShowBottomMargin(int position) {
        return isLastModel(position) || isNextItemDiffGroup(position);
    }

    private boolean isNextItemDiffGroup(int position) {
        return (((UserItemBean) getAdapter().getItem(position))).getGroup() !=
                ((UserItemBean) getAdapter().getItem(position + 1)).getGroup();
    }


    private boolean isLastModel(int position) {
        return position == getAdapter().getItemCount() - 1;
    }

    public int getResource(String imageName) {
        //如果没有在"mipmap"下找到imageName,将会返回0
        return getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
    }


    private void getApplyInfo() {
        if (getContext() instanceof BaseView) {
            ((BaseView) getContext()).showLoadV();
        }
        NetMgr.getInstance().getDefaultRetrofit()
                .create(MainService.class)
                .getApplyInfo()
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as((LifecycleOwner) getContext()))
                .subscribe(new BaseSimpleObserver<DistributeApplyResponse>() {
                    @Override
                    public void onSuccess(DistributeApplyResponse distributeApplyResponse) {
                        if (getContext() instanceof BaseView) {
                            ((BaseView) getContext()).closeLoadV();
                        }
                        boolean isNeedApply = distributeApplyResponse.getData() == null || distributeApplyResponse.getData().isNeedGoApplyPage();
                        if (getAdapter() != null) {
                            getAdapter().setTag(TAG_NEED_GO_APPLY, isNeedApply);
                        }
                        goDistribution(isNeedApply);

                    }

                    @Override
                    public void onFail(Exception e) {
                        if (getContext() instanceof BaseView) {
                            ((BaseView) getContext()).closeLoadV();
                        }
                        ToastUtil.shortShow(getContext(), e.getMessage());
                    }
                });


    }


}
