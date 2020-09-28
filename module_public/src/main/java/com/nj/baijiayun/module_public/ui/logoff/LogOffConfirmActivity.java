package com.nj.baijiayun.module_public.ui.logoff;

import android.os.Bundle;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.BuildConfig;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.AccountHelper;

/**
 * @author chengang
 * @date 2020-01-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui.logoff
 * @describe
 */
public class LogOffConfirmActivity extends BaseAppActivity {
    private Button mBtnConfirm;
    private Button mBtnCancel;
    private String code;
    private PublicService publicService;

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    public boolean needMultipleStatus() {
        return false;
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_logoff_confirm;
    }

    @Override
    protected void initParams() {
        super.initParams();
        code = getIntent().getStringExtra("code");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPageTitle(R.string.public_activity_title_logoff_confirm);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnCancel = findViewById(R.id.btn_cancel);
        publicService = NetMgr.getInstance().getDefaultRetrofit().create(PublicService.class);

    }

    @Override
    protected void registerListener() {
        mBtnConfirm.setOnClickListener(v -> {
            logOff();
        });
        mBtnCancel.setOnClickListener(v -> backUserCenter());

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    private void clearInfoAndClosePage() {
        AccountHelper.getInstance().logout();
        backUserCenter();
    }

    private void backUserCenter() {
        ARouter.getInstance().build(RouterConstant.PAGE_MAIN).navigation();
    }

    private void logOff() {
        if (BuildConfig.DEBUG) {
            if ("123456".equals(code)) {
                code = "";
            }
        }
        publicService.logoff(code)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this))
                .subscribe(new BaseSimpleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        showToastMsg("操作成功");
                        clearInfoAndClosePage();
                    }

                    @Override
                    public void onFail(Exception e) {
                        showToastMsg(e.getMessage());
                    }
                });
    }

}
