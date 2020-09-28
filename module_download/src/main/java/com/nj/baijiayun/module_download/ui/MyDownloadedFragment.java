package com.nj.baijiayun.module_download.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.config.DownloadRealmWrapper;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.downloader.realmbean.DownloadParent;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.widget.CommonLineDividerDecoration;
import com.nj.baijiayun.module_download.R;
import com.nj.baijiayun.module_download.adapter.VideoDownloadAdapter;
import com.nj.baijiayun.module_download.bean.VideoWrapperBean;
import com.nj.baijiayun.module_download.helper.DownloadTypeHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.tencent.mm.opensdk.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @project neixun_android
 * @class name：com.nj.baijiayun.module_download.ui
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019-06-20 14:18
 * @change
 * @time
 * @describe
 */
public class MyDownloadedFragment extends BaseAppFragment {
    private TextView selectAllTv;
    private TextView deleteTv;
    private View line;
    private RecyclerView recyclerView;
    private VideoDownloadAdapter adapter;
    private Disposable disposable;
    private int showType;
    private boolean inEdit;
    private boolean isSelectedAll;
    private String tag = getClass().getSimpleName();

    public void setEditStatusChanged(boolean inEdit) {
        this.inEdit = inEdit;
        if (adapter != null) {
            adapter.setInEdit(inEdit);
            line.setVisibility(inEdit ? View.VISIBLE : View.GONE);
            deleteTv.setVisibility(inEdit ? View.VISIBLE : View.GONE);
            selectAllTv.setVisibility(inEdit ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.download_fragment_my_download;
    }

    @Override
    protected void initView(View mContextView) {
        line = mContextView.findViewById(R.id.line);
        selectAllTv = mContextView.findViewById(R.id.tv_select_all);
        deleteTv = mContextView.findViewById(R.id.tv_delete);
        recyclerView = mContextView.findViewById(R.id.recycler_view);
        CommonLineDividerDecoration decor = new CommonLineDividerDecoration(getActivity(), CommonLineDividerDecoration.VERTICAL)
                .setLineWidthDp(10);
        recyclerView.addItemDecoration(decor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new VideoDownloadAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);
        if (inEdit) {
            setEditStatusChanged(true);
        }
    }

    @Override
    public void registerListener() {
        adapter.setDownloadingFolderClickListener(() -> {
            Intent intent = new Intent(getActivity(), DownloadingListActivity.class);
            intent.putExtra("type", showType);
            startActivity(intent);
        });
        adapter.setSelectionChangedListener(isSelectedAll -> {
            this.isSelectedAll = isSelectedAll;
            selectAllTv.setText(isSelectedAll ? R.string.down_cancel_all_select : R.string.down_all_select);
        });
        adapter.setVideoFolderClickListener((folderId, downloadItem, position) -> {
            if (!inEdit) {
                if (downloadItem.getFileType() == DownloadManager.DownloadType.TYPE_LIBRARY.value()) {
                    JumpHelper.jumpPreViewFile(downloadItem.getFilePath(), downloadItem.getFileName());
                    return;
                }
                Intent intent = new Intent(getActivity(), DownloadedListActivity.class);
                intent.putExtra("type", showType);
                intent.putExtra(DownloadedListActivity.EXTRA_FOLDER_ID, folderId);
                startActivity(intent);
            } else {
                adapter.changePositionSelection(position);
            }
        });

        selectAllTv.setOnClickListener(v -> adapter.selectedAllVideo(!isSelectedAll));
        deleteTv.setOnClickListener(v -> {
            DownloadManager.delete(adapter.getSelectedItems());
            if (adapter.removeAllSelected()) {
                showNoDataView();
            }
        });
    }

    @Override
    public void processLogic() {
        showLoadView();
        showType = getArguments().getInt("type");
        DownloadRealmWrapper allDownloadInfo = DownloadManager
                .getAllDownloadInfo(this
                        , DownloadTypeHelper.getDownloadTypeByShowType(showType));
//        allDownloadInfo.setOnUpdateListener(() -> adapter.updated());

        disposable = allDownloadInfo.getAsFlow()
//                .map(new Function<List<DownloadItem>, Object>() {
//            @Override
//            public Object apply(List<DownloadItem> downloadItems) throws Exception {
//                Log.e(tag ,"RealmResults downloadItems changed ");
//                VideoWrapperBean videoWrapperBean = new VideoWrapperBean();
//                List<DownloadItem> items = new ArrayList<>();
//                videoWrapperBean.setDownloadingItems(items);
//                Map<DownloadParent, List<DownloadItem>> map = new HashMap<>();
//                for (DownloadItem downloadItem : downloadItems) {
//                    if (downloadItem.getDownloadStatus() != DownloadItem.DOWNLOAD_STATUS_COMPLETE) {
//                        items.add(downloadItem);
//                    } else {
//                        List<DownloadItem> list = map.get(downloadItem.getParent());
//                        if (list == null) {
//                            list = new ArrayList<>();
//                            map.put(downloadItem.getParent(), list);
//                        }
//                        list.add(downloadItem);
//                    }
//
////                        updateItem(downloadItem);
//
//                }
//                videoWrapperBean.setDownloadedItems(map);
//                return videoWrapperBean;
//            }
//        }) 
                .map((Function<List<DownloadItem>, VideoWrapperBean>) downloadItems -> {
                    Log.e(tag ,"RealmResults downloadItems changed ");
                    VideoWrapperBean videoWrapperBean = new VideoWrapperBean();
                    List<DownloadItem> items = new ArrayList<>();
                    videoWrapperBean.setDownloadingItems(items);
                    Map<DownloadParent, List<DownloadItem>> map = new HashMap<>();
                    for (DownloadItem downloadItem : downloadItems) {
                        if (downloadItem.getDownloadStatus() != DownloadItem.DOWNLOAD_STATUS_COMPLETE) {
                            items.add(downloadItem);
                        } else {
                            List<DownloadItem> list = map.get(downloadItem.getParent());
                            if (list == null) {
                                list = new ArrayList<>();
                                map.put(downloadItem.getParent(), list);
                            }
                            list.add(downloadItem);
                        }
//                        updateItem(downloadItem);
                    }
                    videoWrapperBean.setDownloadedItems(map);
                    return videoWrapperBean;
                })
                .subscribe(videoWrapperBean -> {
                    if (videoWrapperBean.isEmpty()) {
                        showNoDataView();
                    } else {
                        adapter.setContent(videoWrapperBean);
                        adapter.updated();
                        multipleStatusView.showContent();
                    }
                }, Logger::e);
    }

//    private void updateItem(DownloadItem downloadItem) {
//        for (int i = 0; i < adapter.getItemCount(); i++) {
//            adapter.updated();
//            if (String.valueOf(adapter.get(i)).equals(downloadItem.getItemId())) {
//                adapter.notifyItemChanged(i);
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        Log.e(tag, "onDestroy");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected boolean needAutoInject() {
        return false;
    }
}
