package com.nj.baijiayun.module_public.temple;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseWebViewActivity;
import com.nj.baijiayun.module_common.base.BaseWebViewFragment;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_public.consts.RouterConstant;

import androidx.annotation.Nullable;

/**
 * @author chengang
 * @date 2019-08-08
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */

@Route(path=RouterConstant.PAGE_WEB_VIEW)
public class BaseAppWebViewActivity extends BaseWebViewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setElevation(DensityUtil.dip2px(1));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(baseAppWebViewFragment!=null) {
            baseAppWebViewFragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    

    @Override
    public BaseWebViewFragment createFragment(Bundle bundle) {
        return FragmentCreateHelper.newInstance(bundle,BaseAppWebViewFragment.class);
    }
}
