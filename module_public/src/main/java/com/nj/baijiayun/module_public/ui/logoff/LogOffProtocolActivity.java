package com.nj.baijiayun.module_public.ui.logoff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseAppFragmentActivity;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.ConstsProtocol;
import com.nj.baijiayun.module_public.consts.ConstsSmsCode;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.fragment.ProtocolFragment;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;
import com.nj.baijiayun.module_public.ui.MobileVerifyActivity;

/**
 * @author chengang
 * @date 2020-01-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui.logoff
 * @describe
 */
@Route(path = RouterConstant.PAGE_PUBLIC_LOGOFF)
public class LogOffProtocolActivity extends BaseAppFragmentActivity {
    private Button mBtnConfirm;
    private TextView mHintTv;

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    public boolean needMultipleStatus() {
        return super.needMultipleStatus();
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_logoff_protocol;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setPageTitle(R.string.public_activity_title_logoff);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mHintTv = findViewById(R.id.tv_hint);
        TextSpanHelper.insertImgAtHead(mHintTv,R.drawable.public_logoff_ic_warn,12," ");

    }

    @Override
    protected BaseAppFragment createFragment() {
        return ProtocolFragment.newInstance(ConstsProtocol.LOG_OFF);
    }

    @Override
    protected void registerListener() {
        mBtnConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MobileVerifyActivity.class);
            intent.putExtra("sms_type", ConstsSmsCode.CODE_VERIFY_LOGOFF);
            startActivity(intent);
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
