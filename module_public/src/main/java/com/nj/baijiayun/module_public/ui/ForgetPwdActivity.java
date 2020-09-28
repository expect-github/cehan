package com.nj.baijiayun.module_public.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.CountDownHandler;
import com.nj.baijiayun.module_public.mvp.contract.ForgetPwdContract;

@Route(path = RouterConstant.PAGE_PUBLIC_FORGET_PWD)
public class ForgetPwdActivity extends BaseAppActivity<ForgetPwdContract.Presenter> implements ForgetPwdContract.View {


    private EditText mPhoneEdit;
    private EditText mCodeEdit;
    private TextView mGetCodeTv;
    private EditText mNewPwdEdit;
    private EditText mNewPwdConfirmEdit;
    private Button mBtnConfirm;
    private CountDownHandler mCountDownHandler;
    private String phone;


    @Override
    protected void initParams() {
        super.initParams();
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mPhoneEdit = findViewById(R.id.edit_phone);
        mCodeEdit = findViewById(R.id.edit_code);
        mGetCodeTv = findViewById(R.id.tv_get_code);
        mNewPwdEdit = findViewById(R.id.edit_new_pwd);
        mNewPwdConfirmEdit = findViewById(R.id.edit_new_pwd_confirm);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mPhoneEdit.setText(phone);
        setPageTitle(R.string.public_activity_title_forget_pwd);
    }

    @Override
    protected void registerListener() {
        mGetCodeTv.setOnClickListener(v -> sendCode());
        mBtnConfirm.setOnClickListener(v -> mPresenter.changePwd());
        mPhoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBtnEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBtnEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewPwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBtnEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewPwdConfirmEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkBtnEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void checkBtnEnable() {
        mBtnConfirm.setEnabled(true);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_forget_pwd;
    }

    @SuppressLint("HandlerLeak")
    private void sendCode() {
        if (mCountDownHandler == null) {
            mCountDownHandler = new CountDownHandler( mGetCodeTv) {
                @Override
                protected void setTextByCountDown(TextView textView, int currentCount) {
                    textView.setText(String.format(getString(R.string.public_get_code_fmt), currentCount));
                }

                @Override
                protected void setTextInit(TextView textView) {
                    textView.setText(getString(R.string.common_sendcode));

                }
            };
        }
        startCountDown();
        mPresenter.sendCode();

    }

    @Override
    public String getNewPwd() {
        return mNewPwdEdit.getText().toString();
    }

    @Override
    public String getPhone() {
        return mPhoneEdit.getText().toString();
    }

    @Override
    public String getNewPwdAgain() {
        return getNewPwd();
    }

    @Override
    public String getCodeStr() {
        return mCodeEdit.getText().toString();
    }



    @Override
    public void startCountDown() {
        if(mCountDownHandler!=null) {
            mCountDownHandler.start();
        }

    }

    @Override
    public void endCountDown() {
        if(mCountDownHandler!=null) {
            mCountDownHandler.endCountDown();
        }

    }
}
