package com.nj.baijiayun.module_public.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.widget.EyeEditText;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.CodeSendHelper;
import com.nj.baijiayun.module_public.helper.CountDownHandler;
import com.nj.baijiayun.module_public.mvp.contract.BindPhoneContract;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui
 * @describe
 */
@Route(path = RouterConstant.PAGE_PUBLIC_BIND_PHONE)
public class BindPhoneActivity extends BaseAppActivity<BindPhoneContract.Presenter> implements BindPhoneContract.View {
    private EditText mEditPhone;
    private EditText mEditCode;
    private Button mBtnConfirm;
    private CountDownHandler countDownHandler;
    private EyeEditText mEditPwd;


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_bind_phone;

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPageTitle(R.string.public_bind_phone);
        mEditPhone = findViewById(R.id.edit_phone);
        TextView mGetCodeTv = findViewById(R.id.tv_get_code);
        mEditCode = findViewById(R.id.edit_code);
        mEditPwd = findViewById(R.id.edit_pwd);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        countDownHandler = CodeSendHelper.initCountDownHandler(mGetCodeTv, () -> mPresenter.sendCode());
    }

    @Override
    protected void registerListener() {
        mBtnConfirm.setOnClickListener(v -> mPresenter.confirm());
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }


    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getCode() {
        return mEditCode.getText().toString();
    }

    @Override
    public String getPwd() {
        return mEditPwd.getText().toString();
    }

    @Override
    public void stopCountDown() {
        if (countDownHandler != null) {
            countDownHandler.endCountDown();
        }

    }
}
