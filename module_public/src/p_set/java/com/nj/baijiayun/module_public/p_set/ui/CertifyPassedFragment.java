package com.nj.baijiayun.module_public.p_set.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.UserInfoBean;
import com.nj.baijiayun.module_public.helper.AccountHelper;

/**
 * @author chengang
 * @date 2020-03-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_set
 * @describe
 */
public class CertifyPassedFragment extends BaseAppFragment {


    private ImageView mHeadIv;
    private TextView mNameTv;
    private TextView mIdNumberTv;

    @Override
    protected boolean needAutoInject() {
        return false;


    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.p_set_fragment_certify_pass;
    }

    @Override
    protected void initView(View mContextView) {
        mHeadIv = mContextView.findViewById(R.id.iv_head);
        mNameTv = mContextView.findViewById(R.id.tv_name);
        mIdNumberTv = mContextView.findViewById(R.id.tv_id_number);
        mContextView.setBackgroundColor(Color.WHITE);


    }

    @Override
    public void registerListener() {

    }

    @Override
    public void processLogic() {
        UserInfoBean info = AccountHelper.getInstance().getInfo();
        if (info == null) {
            return;
        }
        ImageLoader.with(getContext()).load(info.getAvatar()).asCircle().into(mHeadIv);
        mNameTv.setText(info.getAuthName());
        mIdNumberTv.setText(info.getAuthNum());
    }
}
