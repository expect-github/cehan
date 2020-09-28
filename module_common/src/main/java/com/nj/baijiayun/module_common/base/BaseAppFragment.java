package com.nj.baijiayun.module_common.base;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.nj.baijiayun.basic.ui.BaseFragment;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.basic.widget.MultipleStatusView;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.MultiStateView;

import java.util.Objects;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author chengang
 * @date 2019-06-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe
 */
public abstract class BaseAppFragment<T extends BasePresenter> extends BaseFragment implements MultiStateView, HasSupportFragmentInjector {

    public Dialog mDiaLog;
    protected MultipleStatusView multipleStatusView;
    @Inject
    public T mPresenter;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    @Override
    protected int bindLayout() {
        if (needMultipleStatus()) {
            return R.layout.layout_multstatus;
        } else {
            return bindContentViewLayoutId();
        }
    }

    public boolean needMultipleStatus() {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initInject();
        Log.d("BaseAppFragment", "onAttach");
    }

    public void initInject() {
        if (needAutoInject()) {
            try {
                AndroidSupportInjection.inject(this);
            } catch (Exception ee) {
                Log.e("AndroidSupportInjection", "Please Check Your Presenter is Inject!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                ee.printStackTrace();
            }
        }
    }


    protected boolean needAutoInject() {
        return true;
    }


    @Override
    protected void initSomethingAfterBindView() {
        if (needMultipleStatus()) {
            multipleStatusView = mContextView.findViewById(R.id.multiple_status_view);
            multipleStatusView.setContentViewResId(bindContentViewLayoutId());
        }

        takeView();

    }

    public void takeView() {
        if (getPresenter() != null) {
            getPresenter().takeView(this);
        }

    }

    protected abstract int bindContentViewLayoutId();

    @Override
    public void showLoadView() {
        if (multipleStatusView != null) {
            multipleStatusView.showLoading();
        }
    }

    @Override
    public void showNoNetWorkView() {
        if (multipleStatusView != null) {
            multipleStatusView.showNoNetwork();
        }

    }

    @Override
    public void showNoDataView() {
        if (multipleStatusView != null) {
            multipleStatusView.showEmpty();
        }
    }

    @Override
    public void showErrorDataView() {
        if (multipleStatusView != null) {
            multipleStatusView.showError();
        }

    }

    @Override
    public void showNoLogin() {
        if (multipleStatusView != null) {
            multipleStatusView.showNoLogin();
        }
    }

    @Override
    public void showToastMsg(String msg) {
        ToastUtil.shortShow(getContext(), msg);
    }

    @Override
    public void showToastMsg(int strIds) {
        ToastUtil.shortShow(getContext(), Objects.requireNonNull(getContext()).getString(strIds));
    }


    @Override
    public void showLoadV(String msg) {
        mDiaLog = BJYDialogHelper.buildLoadingDialog(getContext()).setLoadingTxt(msg);
        mDiaLog.show();
    }

    @Override
    public void showLoadV() {
        showLoadV("");
    }

    @Override
    public void closeLoadV() {
        if (mDiaLog != null) {
            mDiaLog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dropView();
        mPresenter = null;

    }

    public void dropView() {
        if (getPresenter() != null) {
            getPresenter().dropView();
        }
    }

    @Override
    public void showContentView() {
        if (multipleStatusView != null) {
            multipleStatusView.showContent();
        }
    }


    public T getPresenter() {
        return mPresenter;
    }


}
