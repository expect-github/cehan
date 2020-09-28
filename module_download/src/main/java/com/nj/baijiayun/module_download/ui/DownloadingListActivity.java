package com.nj.baijiayun.module_download.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.config.DownloadRealmWrapper;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.widget.CommonLineDividerDecoration;
import com.nj.baijiayun.module_download.R;
import com.nj.baijiayun.module_download.adapter.DownloadingListAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DownloadingListActivity extends BaseAppActivity {
    private TextView selectAllTv;
    private TextView deleteTv;
    private View line;
    private RecyclerView recyclerView;
    private DownloadingListAdapter adapter;
    private Disposable disposable;
    private TextView editTv;
    private boolean isSelectedAll;

    @Override
    protected void initView(Bundle savedInstanceState) {
        line = findViewById(R.id.line);
        selectAllTv = findViewById(R.id.tv_select_all);
        deleteTv = findViewById(R.id.tv_delete);
        recyclerView = findViewById(R.id.recycler_view);
        CommonLineDividerDecoration decor = new CommonLineDividerDecoration(this, CommonLineDividerDecoration.VERTICAL)
                .setLineWidthDp(10);
        recyclerView.addItemDecoration(decor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new DownloadingListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);
        setPageTitle(R.string.download_my_downloading);
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
        adapter.setOnItemClickListener((position, view, item) -> {
            if (adapter.isInEdit()) {
                adapter.changePositionSelection(position);
            } else {
                changeDownloadingStatus(item.getItem());
            }
        });
        adapter.setSelectionChangedListener(isSelectedAll -> {
            this.isSelectedAll = isSelectedAll;
            selectAllTv.setText(isSelectedAll ? R.string.down_cancel_all_select : R.string.down_all_select);
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

    private void changeDownloadingStatus(DownloadItem item) {
        int downloadStatus = item.getDownloadStatus();
        if (downloadStatus == DownloadItem.DOWNLOAD_STATUS_WAITING
                || downloadStatus == DownloadItem.DOWNLOAD_STATUS_DOWNLOADING) {
            DownloadManager.pauseDownload(item);
        } else if (downloadStatus == DownloadItem.DOWNLOAD_STATUS_STOP||downloadStatus == DownloadItem.DOWNLOAD_STATUS_ERROR) {
            DownloadManager.resumeDownload(item);
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        showLoadView();
        DownloadRealmWrapper allDownloadInfo = DownloadManager.getAllDownloadInfo(this
                , new Integer[]{DownloadItem.DOWNLOAD_STATUS_WAITING,
                        DownloadItem.DOWNLOAD_STATUS_STOP,
                        DownloadItem.DOWNLOAD_STATUS_DOWNLOADING, DownloadItem.DOWNLOAD_STATUS_ERROR});
//        allDownloadInfo.setOnUpdateListener(() -> adapter.notifyDataSetChanged());
        disposable = allDownloadInfo.getAsFlow()
                .subscribe((Consumer<List<DownloadItem>>) downloadItems -> {
                    if (downloadItems.size() == 0) {
                        showNoDataView();
                    } else {
                        showContentView();
                        if (adapter.getItemCount() != downloadItems.size()) {
                            adapter.addRawList(downloadItems, true);
                        }
                        for (int i = 0; i < downloadItems.size(); i++) {
                            updateItem(downloadItems.get(i));
                        }

                    }


                });
    }

    private void updateItem(DownloadItem downloadItem) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (String.valueOf(adapter.getItem(i).getItem().getItemId()).equals(downloadItem.getItemId())) {
                Logger.i("DownloadItem notifyItemChanged"+adapter.getItem(i).getItem().getCurrentSize());
                adapter.notifyItemChanged(i);
            }
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
    public void onBackPressedSupport() {
        if (adapter != null && adapter.isInEdit()) {
            changeEditMode(false);
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    public boolean needAutoInject() {
        return false;
    }
}
