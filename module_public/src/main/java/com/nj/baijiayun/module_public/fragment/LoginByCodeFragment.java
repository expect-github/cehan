package com.nj.baijiayun.module_public.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.CodeSendHelper;
import com.nj.baijiayun.module_public.helper.CountDownHandler;
import com.nj.baijiayun.module_public.mvp.contract.LoginContract;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.fragment
 * @describe
 */
public class LoginByCodeFragment extends BaseAppFragment<LoginContract.BaseLoginByCodePresenter> implements LoginContract.LoginByCodeView {
    private EditText mEditPhone;
    private EditText mEditCode;
    private TextView mLoginByPwdTv;
    private CountDownHandler mCountDownHandler;


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_fragment_login_by_code;

    }

    @Override
    protected void initView(View mContextView) {
        mEditPhone = mContextView.findViewById(R.id.edit_phone);
        mEditCode = mContextView.findViewById(R.id.edit_code);
        mLoginByPwdTv = mContextView.findViewById(R.id.tv_login_by_pwd);
        TextView mGetCodeTv = mContextView.findViewById(R.id.tv_get_code);
        mCountDownHandler = CodeSendHelper.initCountDownHandler(mGetCodeTv, () -> mPresenter.sendCode());

    }

    @Override
    public void registerListener() {

        mLoginByPwdTv.setOnClickListener(v -> {

            clearContent();
            mPresenter.loginByPWd();
        });


    }

    private void clearContent() {
        mEditPhone.setText("");
        mEditCode.setText("");
    }

    @Override
    public void processLogic() {

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
    public void stopCountDown() {
        if (mCountDownHandler != null) {
            mCountDownHandler.endCountDown();
        }
    }
}
