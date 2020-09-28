package com.nj.baijiayun.module_public.ui.library;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.PublicLibraryBean;
import com.nj.baijiayun.module_public.bean.response.LibraryDetailResponse;
import com.nj.baijiayun.module_public.bean.response.LibraryDownloadResponse;
import com.nj.baijiayun.module_public.consts.ConstsCollect;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.AppBarHelper;
import com.nj.baijiayun.module_public.ui.FilePreViewActivity;
import com.nj.baijiayun.module_public.widget.CollectView;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-11-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui.library
 * @describe 这个页面逻辑基于FilePreViewActivity 修改的时候需要注意
 */
@Route(path = RouterConstant.PAGE_PUBLIC_LIBRARY)
public class PublicLibraryActivity extends FilePreViewActivity {

    private TextView mTitleTv;
    private TextView mAuthorTv;
    private TextView mDateTv;
    private TextView mBrowseNumTv;
    private CollectView mPublicCollectview;
    private PublicService publicService;
    private ProgressBar mPgr;
    int libraryId;

    @Override
    protected void initParams() {
        super.initParams();
        libraryId = getIntent().getIntExtra("libraryId", 0);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mTitleTv = findViewById(R.id.tv_title);
        mAuthorTv = findViewById(R.id.tv_author);
        mDateTv = findViewById(R.id.tv_date);
        mBrowseNumTv = findViewById(R.id.tv_browse_num);
        mPublicCollectview = findViewById(R.id.public_collectview);
        mPgr = findViewById(R.id.public_progressbar);
        setPageTitle("文库预览");
        mPublicCollectview.setVisibility(View.GONE);

    }

    @Override
    protected void registerListener() {
        super.registerListener();
//        RxBus.getInstanceBus().registerRxBus(this, LoginEvent.class, loginEvent -> processLogic(null));

    }

    @Override
    protected int bindMenuLayout() {
        return R.menu.public_meun_library_preview_file;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            AppBarHelper.libraryShare(this, String.valueOf(libraryId));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        //这里自己处理
        if (publicService == null) {
            publicService = NetMgr.getInstance().getDefaultRetrofit().create(PublicService.class);
        }
        publicService.getLibraryDetail(libraryId).compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this)).subscribe(new BaseSimpleObserver<LibraryDetailResponse>() {
            @Override
            public void onPreRequest() {
                mStatusView.showLoading();

            }

            @Override
            public void onSuccess(LibraryDetailResponse libraryDetailResponse) {
                mStatusView.showContent();
                setContentData(libraryDetailResponse.getData());
                getDownloadFile(libraryDetailResponse.getData().getId());

            }

            @Override
            public void onFail(Exception e) {
                mStatusView.showError();
                ToastUtil.shortShow(getActivity(), e.getMessage());
            }
        });
    }

    private void setContentData(PublicLibraryBean data) {
        mTitleTv.setText(data.getName());
        mAuthorTv.setText(data.getAuthor());
        mPublicCollectview.setCollectId(data.getId())
                .setType(ConstsCollect.LIBRARY)
                .setCollectStatus(data.isCollect());
        mDateTv.setText(TimeFormatHelper.getDateByFormat(data.getCreateAt(), "yyyy.MM.dd"));
        mBrowseNumTv.setText(MessageFormat.format("{0}浏览", data.getBrowseNum() > 999 ? "999+" : String.valueOf(data.getBrowseNum())));
    }

    private void getDownloadFile(int id) {
        publicService.getLibraryDownloadUrl(id).compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(this)).subscribe(new BaseSimpleObserver<LibraryDownloadResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(LibraryDownloadResponse libraryDownloadResponse) {
                setFileUrl(libraryDownloadResponse.getData().getDownloadUrl());
                loadFileUrl();
            }

            @Override
            public void onFail(Exception e) {
                showErrorDataView();
                showToastMsg(e.getMessage());

            }
        });
    }

    @Override
    public void showContentView() {
        mPgr.setVisibility(View.GONE);

    }

    @Override
    public void showLoadView() {
        mPgr.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorDataView() {
        mPgr.setVisibility(View.GONE);
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_library;
    }

}
