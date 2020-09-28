package com.nj.baijiayun.module_public.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.widget.EyeEditText;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.CodeSendHelper;
import com.nj.baijiayun.module_public.helper.CountDownHandler;
import com.nj.baijiayun.module_public.mvp.contract.SetPwdContract;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui
 * @describe
 */
@Route(path = RouterConstant.PAGE_PUBLIC_SET_PWD)
public class SetPwdActivity extends BaseAppActivity<SetPwdContract.Presenter> implements SetPwdContract.View {
    private EyeEditText mEditPwd;
    private EyeEditText mEditPwdAgain;
    private Button mBtnConfirm;
    private boolean isFromAppInner = false;
    private TextView mPhoneTv;
    private TextView mGetCodeTv;
    private EditText mEditCode;

    private CountDownHandler mCountDownHandler;
    private String smsCode;


    @Override
    protected void initParams() {
        super.initParams();
        isFromAppInner = getIntent().getBooleanExtra("isFromAppInner", true);
        if (!isFromAppInner) {
            smsCode = getIntent().getStringExtra("smsCode");
        }
    }

    @Override
    protected int bindContentViewLayoutId() {
        if (isFromAppInner) {
            return R.layout.public_layout_set_pwd_in_app;
        }
        return R.layout.public_activity_set_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (isFromAppInner) {
            mPhoneTv = findViewById(R.id.tv_phone);
            mGetCodeTv = findViewById(R.id.tv_get_code);
            mEditCode = findViewById(R.id.edit_code);
            if (AccountHelper.getInstance().getInfo() != null) {
                mPhoneTv.setText(AccountHelper.getInstance().getInfo().getMobile());
            }
            mCountDownHandler = CodeSendHelper.initCountDownHandler(mGetCodeTv, () -> mPresenter.sendCode());

        }
        mEditPwd = findViewById(R.id.edit_pwd);
        mEditPwdAgain = findViewById(R.id.edit_pwd_again);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnConfirm.setText(R.string.public_submit);
        setPageTitle(R.string.public_set_pwd);
        if (!isFromAppInner) {
            ToolBarHelper.addRightText(getToolBar(), getString(R.string.public_skip), v -> finish());
        }

    }

    @Override
    protected void registerListener() {
        mBtnConfirm.setOnClickListener(v -> mPresenter.submit());
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }


    @Override
    public String getPwd() {
        return mEditPwd.getText().toString();
    }

    @Override
    public String getConfirmPwd() {
        if (isFromAppInner) {
            return getPwd();
        }
        return mEditPwdAgain.getText().toString();
    }

    @Override
    public String getCode() {
        if (isFromAppInner) {
            return mEditCode.getText().toString();

        }
        return smsCode;
    }

    @Override
    public String getPhone() {
        return mPhoneTv.getText().toString();
    }


    @Override
    public void startCountDown() {
        if (mCountDownHandler != null) {
            mCountDownHandler.start();
        }

    }

    @Override
    public void endCountDown() {
        if (mCountDownHandler != null) {
            mCountDownHandler.endCountDown();
        }

    }
}
