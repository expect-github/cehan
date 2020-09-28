package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.videoplay.VideoDataChangeHelper;
import com.nj.baijiayun.module_public.helper.videoplay.VideoPlayHelper;
import com.nj.baijiayun.module_public.helper.videoplay.res.OneToOneTokenRes;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-08-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class PlayJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getBjyOneToOneToken(jsActionDataBean.getParams().getCourseId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain((LifecycleOwner) context))
                .subscribe(new BaseSimpleObserver<OneToOneTokenRes>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(OneToOneTokenRes baseResponse) {
                        VideoPlayHelper.playVideo(VideoDataChangeHelper.OneToOneDataToBjyToken(baseResponse.getData()), ConstsCouseType.getOtoCourseType());
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtil.shortShow(context, e.getMessage());

                    }
                });
    }
}
