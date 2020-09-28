package com.nj.baijiayun.module_download.ui;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baijiayun.download.DownloadTask;
import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.widget.CommonLineDividerDecoration;
import com.nj.baijiayun.module_download.R;
import com.nj.baijiayun.module_download.adapter.DownloadedListAdapter;
import com.nj.baijiayun.module_download.bean.CheckableWrapper;
import com.nj.baijiayun.module_download.helper.DownloadTypeHelper;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.JumpHelper;

import java.io.File;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DownloadedListActivity extends BaseAppActivity {
    public static final String EXTRA_FOLDER_ID = "folder_id";
    private RecyclerView recyclerView;
    private DownloadedListAdapter adapter;
    private TextView selectAllTv;
    private TextView deleteTv;
    private View line;
    private Disposable disposable;
    private TextView editTv;
    private boolean isSelectedAll = false;

    @Override
    protected void initView(Bundle savedInstanceState) {
        line = findViewById(R.id.line);
        selectAllTv = findViewById(R.id.tv_select_all);
        deleteTv = findViewById(R.id.tv_delete);
        recyclerView = findViewById(R.id.recycler_view);
        CommonLineDividerDecoration decor = new CommonLineDividerDecoration(this, CommonLineDividerDecoration.VERTICAL)
                .setLineWidthDp(10);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(decor);
        adapter = new DownloadedListAdapter(this);
        recyclerView.setAdapter(adapter);
        setPageTitle(R.string.download_my_downloaded);
        editTv = (TextView) View.inflate(this, R.layout.download_layout_edit, null);
        ToolBarHelper.addRightView(getToolBar(), editTv);
    }

    @Override
    protected void registerListener() {
        selectAllTv.setOnClickListener(v -> adapter.selectedAllVideo(!isSelectedAll));
        deleteTv.setOnClickListener(v -> {
            DownloadManager.delete(adapter.getSelectedItems());
            if (adapter.removeAllSelected()) {
                showNoDataView();
            }
            changeEditMode(false);
        });
        adapter.setSelectionChangedListener(isSelectedAll -> {
            this.isSelectedAll = isSelectedAll;
            selectAllTv.setText(isSelectedAll ? R.string.down_cancel_all_select : R.string.down_all_select);
        });
        adapter.setOnItemClickListener((position, view, item) -> {
            if (adapter.isInEdit()) {
                adapter.changePositionSelection(position);
            } else {
                handleClick(item);
            }
        });
        adapter.setOnItemLongClickListener((position, view, item) -> {
            boolean inEdit = adapter.isInEdit();
            if (!inEdit) {
                changeEditMode(true);
                adapter.changePositionSelection(position);
                return true;
            }
            return false;
        });
        editTv.setOnClickListener(v -> {
            if (adapter != null) {
                boolean inEdit = !adapter.isInEdit();
                changeEditMode(inEdit);
            }
        });
    }

    private void changeEditMode(boolean inEdit) {
        adapter.setInEditMode(inEdit);
        editTv.setText(inEdit ? R.string.common_cancel : R.string.common_edit);
        line.setVisibility(inEdit ? View.VISIBLE : View.GONE);
        selectAllTv.setVisibility(inEdit ? View.VISIBLE : View.GONE);
        deleteTv.setVisibility(inEdit ? View.VISIBLE : View.GONE);
    }

    private void handleClick(CheckableWrapper<DownloadItem> item) {
        DownloadItem videoItem = item.getItem();
        String videoPath = videoItem.getFilePath();
        Logger.d("downloaded video play ,the video path is " + videoPath);

        switch (videoItem.getFileType()) {
            default:
                break;
            case DownloadItem.FILE_TYPE_COURSE_WAVE:
            case DownloadItem.FILE_TYPE_LIBRARY:
            case DownloadItem.FILE_TYPE_FILE_COURSE:
                JumpHelper.jumpPreViewFile(videoItem.getFilePath(), videoItem.getFileName());
                break;
            case DownloadItem.FILE_TYPE_FILE_AUDIO:
                ARouter.getInstance().build(RouterConstant.PAGE_COURSE_AUDIO)
                        .withBoolean("isLocal", true)
                        .withString("title", videoItem.getFileName())
                        .withString("path", videoItem.getFilePath())
                        .withString("localCover", videoItem.getParent().getParentCover())
                        .navigation();
                break;

            case DownloadItem.FILE_TYPE_FILE_VIDEO:
                ARouter.getInstance().build(RouterConstant.PAGE_VIDEO_PROXY).withString("type", "video").withString("videoPath", videoItem.getFilePath()).navigation();
                break;
            case DownloadItem.FILE_TYPE_VIDEO_AUDIO:
            case DownloadItem.FILE_TYPE_VIDEO:
                //TODO 点播
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File file = new File(videoPath);
                    if (file.exists()) {
                        ARouter.getInstance()
                                .build(RouterConstant.PAGE_VIDEO_PROXY)
                                .withString("itemId",videoItem.getItemId())
                                .withBoolean("isOffline", true)
                                .withSerializable("videoDownloadModel", DownloadManager.getPlayBackDownloadTask(videoItem).getVideoDownloadInfo())
                                .withString("videoPath", videoPath)
                                .withString("type", "video")
                                .navigation();

                    } else {
                        Toast.makeText(DownloadedListActivity.this, "下载视频不存在或已删除！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DownloadedListActivity.this, "请检查是否存在sd卡！", Toast.LENGTH_LONG).show();
                }
                break;
            case DownloadItem.FILE_TYPE_PLAY_BACK:
                //TODO 回放
                try {
                    DownloadTask playBackDownloadTask = DownloadManager.getPlayBackDownloadTask(videoItem);
                    ARouter.getInstance().build(RouterConstant.PAGE_VIDEO_PROXY)
                            .withSerializable("videoDownloadModel", playBackDownloadTask.getVideoDownloadInfo())
                            .withSerializable("signalDownloadModel", playBackDownloadTask.getSignalDownloadInfo())
                            .withBoolean("isOffline", true)
                            .withString("type", "backplay")
                            .navigation();

                } catch (Exception e) {
                    e.printStackTrace();
                    showToastMsg(e.getMessage());
                }
                break;
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String courseId = getIntent().getStringExtra(EXTRA_FOLDER_ID);
        int type = getIntent().getIntExtra("type", 0);
//        mPresenter.listenVideoInfo(courseId);
        showLoadView();
        disposable = DownloadManager.getAllDownloadInfo(this, courseId,
                DownloadTypeHelper.getDownloadTypeByShowType(type),
                new DownloadManager.DownloadStatus[]{})
                .getAsFlow()
                .subscribe((Consumer<List<DownloadItem>>) downloadItems -> {
                    if (downloadItems.size() == 0) {
                        showNoDataView();
                    } else {
                        showContentView();
                        adapter.addRawList(downloadItems, true);
                    }
                });
    }


    @Override
    public void onBackPressedSupport() {
        if (adapter != null && adapter.isInEdit()) {
            changeEditMode(false);
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.download_fragment_my_download;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public boolean needAutoInject() {
        return false;
    }
}
