package com.nj.baijiayun.module_public.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.widget.EyeEditText;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.mvp.contract.LoginContract;
import com.nj.baijiayun.module_public.ui.ForgetPwdActivity;
import com.nj.baijiayun.module_public.ui.LoginActivity;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.fragment
 * @describe
 */
public class LoginByPwdFragment extends BaseAppFragment<LoginContract.BaseLoginByPwdPresenter> implements LoginContract.LoginByPwdView {
    private EditText mEditPhone;
    private EyeEditText mEditPwd;
    private TextView mForgetPwdTv;
    private TextView mLoginByCodeTv;


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_fragment_login_by_pwd;

    }


    @Override
    protected void initView(View mContextView) {
        mEditPhone = mContextView.findViewById(R.id.edit_phone);
        mEditPwd = mContextView.findViewById(R.id.edit_pwd);
        mForgetPwdTv = mContextView.findViewById(R.id.tv_forget_pwd);
        mLoginByCodeTv = mContextView.findViewById(R.id.tv_login_by_code);

    }

    @Override
    public void registerListener() {
        mForgetPwdTv.setOnClickListener(v -> startActivity(new Intent(getActivity(), ForgetPwdActivity.class)));
        mLoginByCodeTv.setOnClickListener(v -> {
            clearContent();
            ((LoginActivity) getActivity()).pop();
        });

    }

    private void clearContent() {
        mEditPhone.setText("");
        mEditPwd.setText("");
    }

    @Override
    public void processLogic() {

    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getPwd() {
        return mEditPwd.getText().toString();
    }


}
