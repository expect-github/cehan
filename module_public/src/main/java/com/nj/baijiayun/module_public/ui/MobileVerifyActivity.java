package com.nj.baijiayun.module_public.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.consts.ConstsSmsCode;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.CodeSendHelper;
import com.nj.baijiayun.module_public.helper.CountDownHandler;
import com.nj.baijiayun.module_public.ui.logoff.LogOffConfirmActivity;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2020-01-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui
 * @describe
 */
public class MobileVerifyActivity extends BaseAppActivity {
    private EditText mEditCode;
    private CountDownHandler mCountDownHandler;
    private PublicService publicService;
    private Button mBtnConfirm;
    private String smsType;

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
        return R.layout.public_layout_moblie_verify;
    }

    @Override
    protected void initParams() {
        super.initParams();
        smsType = getIntent().getStringExtra("sms_type");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPageTitle(R.string.public_activity_account_verify);
        mEditCode = findViewById(R.id.edit_code);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mCountDownHandler = CodeSendHelper.initCountDownHandler(findViewById(R.id.tv_get_code), this::sendCode);
        //设置手机号
        ((TextView) findViewById(R.id.tv_current_mobile_number))
                .setText(
                        MessageFormat.format(
                                getString(R.string.public_fmt_bind_phone),
                                mobileMidToStart(AccountHelper.getInstance().getInfo().getMobile())
                        )
                );
        publicService = NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class);
    }


    @Override
    protected void registerListener() {
        mBtnConfirm.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mEditCode.getText().toString())) {
                showToastMsg(getString(R.string.public_check_code));
                return;
            }
            verifyCode();
        });


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    private void sendCode() {
        showLoadV();
        mCountDownHandler.start();
        publicService
                .sendCode(AccountHelper.getInstance().getInfo().getMobile(), smsType)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this))
                .subscribe(new BaseSimpleObserver<BaseResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        closeLoadV();
                        showToastMsg(getString(R.string.public_send_sms_code_success));
                    }

                    @Override
                    public void onFail(Exception e) {
                        closeLoadV();
                        mCountDownHandler.endCountDown();
                        showToastMsg(e.getMessage());

                    }
                });
    }

    private void verifyCode() {
        showLoadV();
        publicService.verifyLogOffSmsCode(getCode())
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this))
                .subscribe(new BaseSimpleObserver<BaseResponse>() {

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        closeLoadV();
                        goConfirmPage();

                    }

                    @Override
                    public void onFail(Exception e) {
                        closeLoadV();
                        showToastMsg(e.getMessage());

                    }
                });

    }

    private String getCode() {
        return mEditCode.getText().toString();
    }

    private String mobileMidToStart(String str) {
        if (str.length() < 7) {
            return str;
        }
        return str.substring(0, str.length() - (str.substring(3)).length()) + "****" + str.substring(7);

    }

    private void goConfirmPage() {
        Intent intent = null;
        switch (smsType) {
            case ConstsSmsCode.CODE_VERIFY_LOGOFF:
                intent = new Intent(this, LogOffConfirmActivity.class);
                break;
            default:
                break;
        }
        if (intent == null) {
            return;
        }
        intent.putExtra("code", getCode());
        startActivity(intent);
    }
}
