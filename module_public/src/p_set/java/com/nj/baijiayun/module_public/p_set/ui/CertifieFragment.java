package com.nj.baijiayun.module_public.p_set.ui;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.UserInfoBean;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;
import com.nj.baijiayun.module_public.p_set.api.SetService;
import com.nj.baijiayun.module_public.p_set.helper.IDCardValidate;
import com.nj.baijiayun.module_public.p_set.helper.NameValidate;

/**
 * @author chengang
 * @date 2020-03-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_set
 * @describe
 */
public class CertifieFragment extends BaseAppFragment {

    private EditText mEditName;
    private EditText mEditIdNumber;
    private Button mBtnConfirm;
    private TextView mWarningTv;


    @Override
    protected boolean needAutoInject() {
        return false;

    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.p_set_fragment_certifie;
    }

    @Override
    protected void initView(View mContextView) {
        mEditName = mContextView.findViewById(R.id.edit_name);
        mEditIdNumber = mContextView.findViewById(R.id.edit_id_number);
        mBtnConfirm = mContextView.findViewById(R.id.btn_confirm);
        mWarningTv = mContextView.findViewById(R.id.tv_warning);
        mBtnConfirm.setEnabled(false);
        mContextView.setBackgroundColor(Color.WHITE);

        TextSpanHelper.insertImgAtHead(mWarningTv, R.drawable.p_set_warning, 14, " ");

    }

    @Override
    public void registerListener() {
        mEditName.addTextChangedListener(new TextWatcher() {
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
        mEditIdNumber.addTextChangedListener(new TextWatcher() {
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
        mBtnConfirm.setOnClickListener(v -> {
            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
            if (!NameValidate.isLegalName(getUserName())) {
                showToastMsg("请输入正确的姓名");
                return;
            }
            String s = IDCardValidate.validateEffective(getIdNumber());
            if (!getIdNumber().equals(s)) {
                showToastMsg("请输入正确身份证号");
                return;
            }
            CommonMDDialog commonMDDialog = BJYDialogHelper.buildMDDialog(getActivity())
                    .setPositiveTxt(R.string.confirm)
                    .setNegativeTxt(R.string.cancel)
                    .setContentTxt(getString(R.string.public_id_number_confirm));
            commonMDDialog.getContentTxt().setGravity(Gravity.LEFT);
            TextSpanHelper.insertImgAtHead(commonMDDialog.getContentTxt(), R.drawable.p_set_warning, 14, " ");
            commonMDDialog.setOnPositiveClickListener(() -> uploadInfo());
            commonMDDialog.setOnNegativeClickListener(() -> commonMDDialog.dismiss());
            commonMDDialog.show();

        });

    }

    private String getIdNumber() {
        return mEditIdNumber.getText().toString();
    }

    private String getUserName() {
        return mEditName.getText().toString();
    }

    private void uploadInfo() {
        showLoadV();
        NetMgr.getInstance().getDefaultRetrofit()
                .create(SetService.class)
                .setAuthInfo(mEditName.getText().toString(), mEditIdNumber.getText().toString())
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this))
                .subscribe(new BaseSimpleObserver<BaseResponse<UserInfoBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserInfoBean> baseResponse) {
                        closeLoadV();
                        AccountHelper.getInstance().saveInfo(baseResponse.getData());
                        if(getActivity()!=null) {
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        showToastMsg(e.getMessage());
                        closeLoadV();

                    }
                });
    }

    private void checkBtnEnable() {
        mBtnConfirm.setEnabled(getUserName().length() > 0 && getIdNumber().length() > 0);
    }

    @Override
    public void processLogic() {

    }
}
