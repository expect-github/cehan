package com.nj.baijiayun.module_public.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nj.baijiayun.basic.utils.MediaFileUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.FileHelper;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author chengang
 * 文件预览 图片预览待完善
 */
@Route(path = RouterConstant.PAGE_PUBLIC_FILE_PREVIEW)
public class FilePreViewActivity extends BaseAppActivity implements TbsReaderView.ReaderCallback {

    @Autowired(name = "fileUrl")
    String fileUrl;
    @Autowired(name = "fileName")
    String fileName;
    private TbsReaderView mTbsReaderView;
    private View mTvOpenByOtherApp;


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_file_preview;
    }

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        FrameLayout mTbsView = findViewById(R.id.frameLayout);
        mTvOpenByOtherApp = findViewById(R.id.tv_open_by_other_app);
        Logger.d("fileUrl" + fileUrl);
        if (MediaFileUtil.isImageFileType(fileUrl)) {
            ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_IMAGE_PREVIEW).withString("path", fileUrl).navigation();
            finish();
        } else {
            mTbsReaderView = new TbsReaderView(this, this);
            mTbsView.addView(mTbsReaderView, new FrameLayout.LayoutParams(-1, -1));
        }
        setPageTitle(fileName == null ? FileHelper.getFileName(fileUrl) : fileName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(bindMenuLayout(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected int bindMenuLayout() {
        return R.menu.public_meun_preview_file;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_other) {
            if (filePath == null) {
                showToastMsg("文件未找到...");
                return true;
            }
            FileHelper.openFileByOtherApp(filePath, getActivity());

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void registerListener() {
        mStatusView.setOnRetryClickListener(v -> processLogic(null));
        mTvOpenByOtherApp.setOnClickListener(v -> FileHelper.openFileByOtherApp(filePath, getActivity()));
    }

    private String filePath;

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (MediaFileUtil.isImageFileType(fileUrl)) {
            return;
        }
        loadFileUrl();


    }

    public void loadFileUrl() {
        if (fileUrl != null && fileUrl.startsWith("http")) {
            showLoadView();
            Glide.with(getActivity())
                    .downloadOnly()
                    .load(fileUrl)
                    .into(new CustomTarget<File>() {
                        @Override
                        public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                            FileHelper.saveGlideFile(getActivity(), resource, fileUrl, file -> {
                                if (file != null) {
                                    filePath = file.getPath();
                                    showContentView();
                                    displayFile(file);
                                } else {
                                    showErrorDataView();
                                }
                            });
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            showErrorDataView();
                        }
                    });
        } else {
            File file = new File(FileHelper.getFileParent(fileUrl), FileHelper.getFileName(fileUrl));
            filePath = file.getPath();
            displayFile(file);
        }
    }


    private void displayFile(File file) {
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        Bundle bundle = new Bundle();
        bundle.putString("filePath", file.getPath());
        bundle.putString("tempPath", file.getParent());
        String fileExt = FileHelper.getFileExt(file.getPath());
        boolean result = mTbsReaderView.preOpen(fileExt, false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        } else {
            mTvOpenByOtherApp.setVisibility(View.VISIBLE);
            QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {

                }

                @Override
                public void onViewInitFinished(boolean b) {

                }
            });
        }
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
