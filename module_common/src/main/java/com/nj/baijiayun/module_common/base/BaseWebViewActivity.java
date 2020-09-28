package com.nj.baijiayun.module_common.base;

import android.os.Bundle;

import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;

/**
 * @author chengang
 * @date 2019-06-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe
 */
public class BaseWebViewActivity extends BaseAppFragmentActivity {

    private String url, data;
    public String title;
    public BaseWebViewFragment baseAppWebViewFragment;
    boolean isCanPressBack=true;

    private boolean needAppBar = true;

    @Override
    protected void initParams() {
        super.initParams();
        url = getIntent().getStringExtra("url");
        data = getIntent().getStringExtra("data");
        title = getIntent().getStringExtra("title");
        needAppBar = getIntent().getBooleanExtra("appbar", true);
        Logger.d(url);
    }

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (!needAppBar) {
            hideToolBar();
        }
    }

    @Override
    protected BaseAppFragment createFragment() {

        setPageTitle(title);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("data", data);
        baseAppWebViewFragment = createFragment(bundle);
        return baseAppWebViewFragment;
    }

    public BaseWebViewFragment createFragment(Bundle bundle) {
        return FragmentCreateHelper.newInstance(bundle, BaseWebViewFragment.class);
    }

    @Override
    protected void registerListener() {
        baseAppWebViewFragment.setTitleCallBack(title -> {
            if (getActivityTitle() == null || getActivityTitle().length() <= 0) {
                setPageTitle(title);
            }
        });
    }

    public String getActivityTitle() {
        return title;
    }

    @Override
    public void onBackPressedSupport() {
        if(!isCanPressBack) {
            return;
        }
        if (baseAppWebViewFragment != null && baseAppWebViewFragment.getWebView() != null) {
            if (baseAppWebViewFragment.getWebView().canGoBack()) {
                baseAppWebViewFragment.getWebView().goBack();
            } else {
                super.onBackPressedSupport();
            }
        } else {
            super.onBackPressedSupport();
        }

    }

    public void setCanPressBack(boolean canPressBack) {
        isCanPressBack = canPressBack;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
