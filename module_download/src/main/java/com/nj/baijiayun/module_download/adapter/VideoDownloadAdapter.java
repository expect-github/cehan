package com.nj.baijiayun.module_download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.RealmManager;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.downloader.realmbean.DownloadParent;
import com.nj.baijiayun.downloader.utils.VideoDownloadUtils;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_download.R;
import com.nj.baijiayun.module_download.bean.CheckableWrapper;
import com.nj.baijiayun.module_download.bean.VideoWrapperBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

public class VideoDownloadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_DOWNLOADING = 1;
    private static final int VIEW_TYPE_VIDEO = 2;
    private static final int VIEW_TYPE_LIBRARY = 3;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private VideoFolderClickListener videoFolderListener;
    private LibraryClickListener libraryClickListener;
    private DownloadingFolderClickListener downloadingFolderListener;
    protected OnClickListener onClickListener;
    private List<DownloadItem> downloadingItems;
    private Map<DownloadParent, List<DownloadItem>> downloadedItems;
    private List<CheckableWrapper<DownloadParent>> courseWraper = new ArrayList<>();
    private boolean inEdit = false;
    private OnSelectionChangedListener selectionChangedListener;

    public VideoDownloadAdapter(final Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, View view) {
                if (videoFolderListener != null) {
                    DownloadParent course = getCourse(position).getItem();
                    videoFolderListener.videoFolderClick(course.getParentId(),getDownloadedItem(position), position);
                }
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DOWNLOADING) {
            return new DownloadingFolderHolder(
                    mInflater.inflate(R.layout.download_recycler_item_downloading_folder, null),
                    downloadingFolderListener);
        }
        if(viewType==VIEW_TYPE_LIBRARY)
        {
          return   new LibraryHolder(
                    mInflater.inflate(R.layout.download_recycler_item_library, parent,false),
                    onClickListener, (buttonView, isChecked) -> {
              LibraryHolder viewHolder1 = (LibraryHolder) buttonView.getTag();
                int position = viewHolder1.getAdapterPosition();
                if (position >= 0) {
                    courseWraper.get(position).setChecked(isChecked);
                }
                if (selectionChangedListener != null) {
                    selectionChangedListener.selectionChanged(isAllChecked(isChecked));
                }
            });
        }
        return new VideoFolderHolder(
                mInflater.inflate(R.layout.download_recycler_item_video_folder, null),
                onClickListener, (buttonView, isChecked) -> {
            VideoFolderHolder viewHolder1 = (VideoFolderHolder) buttonView.getTag();
            int position = viewHolder1.getAdapterPosition();
            if (position >= 0) {
                courseWraper.get(position).setChecked(isChecked);
            }
            if (selectionChangedListener != null) {
                selectionChangedListener.selectionChanged(isAllChecked(isChecked));
            }
        });
    }

    private boolean isAllChecked(boolean isChecked) {
        if (!isChecked) {
            return false;
        } else {
            for (CheckableWrapper<DownloadParent> item : courseWraper) {
                if (!item.isChecked()) {
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_DOWNLOADING) {
            DownloadingFolderHolder downloadingHolder = (DownloadingFolderHolder) holder;
            downloadingHolder.lastCourseTv.setText(downloadingItems.get(0).getParent().getParentName());
            downloadingHolder.downloadPb.setProgress(getDownloadProgress(downloadingItems));
            downloadingHolder.downloadCountTv.setText(mContext.getString(R.string.download_count_format, downloadingItems.size()));
            downloadingHolder.downloadSpeedTv.setText(mContext.getString(R.string.download_speed_format, getDownloadSpeed(downloadingItems)));
        } else if (getItemViewType(position) == VIEW_TYPE_VIDEO) {
            VideoFolderHolder videoHolder = (VideoFolderHolder) holder;
            CheckableWrapper<DownloadParent> wrapper = getCourse(position);
            videoHolder.deleteCb.setVisibility(inEdit ? View.VISIBLE : View.GONE);
            videoHolder.deleteCb.setChecked(wrapper.isChecked());
            DownloadParent course = wrapper.getItem();
            videoHolder.courseNameTv.setText(course.getParentName());
            //不去jia
//            ImageLoader.with(mContext).load(course.getParentCover()).into(videoHolder.courseIv);
            List<DownloadItem> downloadItems = downloadedItems.get(course);
            videoHolder.courseDescTv.setText(mContext.getString(R.string.download_folder_desc_format,
                    downloadItems == null ? 0 : downloadItems.size(), getSize(downloadItems)));
        } else if (getItemViewType(position) == VIEW_TYPE_LIBRARY) {

            LibraryHolder videoHolder = (LibraryHolder) holder;
            CheckableWrapper<DownloadParent> wrapper = getCourse(position);
            videoHolder.deleteCb.setVisibility(inEdit ? View.VISIBLE : View.GONE);
            videoHolder.deleteCb.setChecked(wrapper.isChecked());
            DownloadParent course = wrapper.getItem();
            videoHolder.mTvTitle.setText(course.getParentName());
//            videoHolder.mTvAbstract.setText(getDownloadedItem(position).getChapter().chapterName);
            //不去jia
//            ImageLoader.with(mContext).load(course.getParentCover()).into(videoHolder.courseIv);
//            List<DownloadItem> downloadItems = downloadedItems.get(course);
//            videoHolder.courseDescTv.setText(mContext.getString(R.string.download_folder_desc_format,
//                    downloadItems == null ? 0 : downloadItems.size(), getSize(downloadItems)));

        }
    }


    private CheckableWrapper<DownloadParent> getCourse(int position) {
        if (showDownloadingItem()) {
            position--;
        }
        return courseWraper.get(position);
    }

    private DownloadItem getDownloadedItem(int position) {
        if (showDownloadingItem()) {
            position--;
        }

        return downloadedItems.get(courseWraper.get(position).getItem()).get(0);
    }


    private String getDownloadSpeed(List<DownloadItem> downloadingItems) {
        long speed = 0;
        for (DownloadItem downloadingItem : downloadingItems) {
            speed += downloadingItem.getDownloadSpeed();
        }
        return VideoDownloadUtils.getFormatSize(speed);
    }

    private String getSize(List<DownloadItem> downloadItems) {
        long size = 0;
        if (downloadItems != null) {
            for (DownloadItem downloadingItem : downloadItems) {
                size += downloadingItem.getFileSize();
            }
        }
        return VideoDownloadUtils.getFormatSize(size);
    }

    private int getDownloadProgress(List<DownloadItem> downloadingItems) {
        long total = 0;
        long current = 0;
        for (DownloadItem downloadingItem : downloadingItems) {
            total += downloadingItem.getFileSize();
            current += downloadingItem.getCurrentSize();
        }
        if (total == 0) {
            return 0;
        }
        return (int) (current * 100 / total);
    }


    @Override
    public int getItemCount() {
        return (showDownloadingItem() ? 1 : 0) + (courseWraper.size());
    }

    private boolean showDownloadingItem() {
        return downloadingItems != null && downloadingItems.size() > 0 && !inEdit;
    }

    @Override
    public int getItemViewType(int position) {
        RealmManager.getRealmInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getCourse(0).getItem().type = 3;
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Logger.e("success");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Logger.e(error.getMessage());

            }
        });
        if (position == 0 && showDownloadingItem()) {
            return VIEW_TYPE_DOWNLOADING;
        } else if (getCourse(position).getItem().getType() == DownloadManager.DownloadType.TYPE_LIBRARY.value()) {
            return VIEW_TYPE_LIBRARY;
        }

//        Realm realmInstance = RealmManager.getRealmInstance();
//        realmInstance.insertOrUpdate();
//        RealmManager.getRealmInstance().insertOrUpdate(getCourse(position).getItem());
        return VIEW_TYPE_VIDEO;
    }

    public void setContent(VideoWrapperBean videoWrapperBean) {
        boolean reload = false;
        Map<DownloadParent, List<DownloadItem>> downloadedItems = videoWrapperBean.getDownloadedItems();
        if (downloadingItems == null || this.downloadedItems == null) {
            reload = true;
        } else if (this.downloadingItems.size() != videoWrapperBean.getDownloadingItems().size()
                || downloadedItems.size() != this.downloadedItems.size()) {
            reload = true;
        } else {
            for (List<DownloadItem> value : downloadedItems.values()) {
                List<DownloadItem> list = this.downloadedItems.get(value);
                if (list == null || list.size() != downloadedItems.get(value).size()) {
                    reload = true;
                    break;
                }
            }
        }

        reload(videoWrapperBean, reload);
    }

    private void reload(VideoWrapperBean videoWrapperBean, boolean reload) {
        if (reload) {
            this.downloadingItems = videoWrapperBean.getDownloadingItems();
            this.downloadedItems = videoWrapperBean.getDownloadedItems();
            courseWraper.clear();
            if (this.downloadedItems != null) {
                for (DownloadParent downloadParent : this.downloadedItems.keySet()) {
                    courseWraper.add(wrapperVideoItem(downloadParent));
                }
            }
            notifyDataSetChanged();
        }
    }

    public void updated() {
        if (showDownloadingItem()) {
            notifyItemChanged(0);
        }
    }

    public void setInEdit(boolean inEdit) {
        this.inEdit = inEdit;
        notifyDataSetChanged();
    }

    public boolean getInEdit() {
        return inEdit;
    }


    public void selectedAllVideo(boolean selectAll) {
        for (CheckableWrapper checkableWrapper : courseWraper) {
            checkableWrapper.setChecked(selectAll);
        }
        if (selectionChangedListener != null) {
            selectionChangedListener.selectionChanged(selectAll);
        }
        notifyDataSetChanged();
    }

    public List<DownloadItem> getSelectedItems() {
        ArrayList<DownloadItem> selectedItem = new ArrayList<>();
        for (CheckableWrapper<DownloadParent> checkableWrapper : courseWraper) {
            if (checkableWrapper.isChecked()) {
                List<DownloadItem> list = downloadedItems.get(checkableWrapper.getItem());
                if (list != null) {
                    selectedItem.addAll(list);
                }
            }
        }
        return selectedItem;
    }

    @NonNull
    protected <T> CheckableWrapper<T> wrapperVideoItem(T item) {
        CheckableWrapper<T> wrapper = new CheckableWrapper<>();
        wrapper.setChecked(false);
        wrapper.setItem(item);
        return wrapper;
    }

    public boolean removeAllSelected() {
        Iterator<CheckableWrapper<DownloadParent>> iterator = courseWraper.iterator();
        while (iterator.hasNext()) {
            CheckableWrapper checkableWrapper = iterator.next();
            if (checkableWrapper.isChecked()) {
                downloadedItems.remove(checkableWrapper.getItem());
                iterator.remove();
            }
        }
        notifyDataSetChanged();
        return courseWraper.isEmpty();
    }

    public void changePositionSelection(int position) {
        CheckableWrapper item = courseWraper.get(position);
        item.setChecked(!item.isChecked());
        notifyItemChanged(position);
    }

    private class DownloadingFolderHolder extends RecyclerView.ViewHolder {
        TextView downloadCountTv;
        TextView lastCourseTv;
        TextView downloadSpeedTv;
        ProgressBar downloadPb;

        public DownloadingFolderHolder(View itemView, final DownloadingFolderClickListener downloadingFolderClickListener) {
            super(itemView);
            downloadCountTv = itemView.findViewById(R.id.tv_download_count);
            lastCourseTv = itemView.findViewById(R.id.tv_last_course);
            downloadSpeedTv = itemView.findViewById(R.id.tv_download_speed);
            downloadPb = itemView.findViewById(R.id.pb_download);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (downloadingFolderClickListener != null) {
                        downloadingFolderClickListener.downloadingFolderClick();
                    }
                }
            });
        }
    }

    private static class VideoFolderHolder extends RecyclerView.ViewHolder {
        CheckBox deleteCb;
        ImageView courseIv;
        TextView courseNameTv;
        TextView courseDescTv;

        public VideoFolderHolder(View itemView, OnClickListener onClickListener, CompoundButton.OnCheckedChangeListener listener) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
            courseIv = itemView.findViewById(R.id.iv_course);
            courseNameTv = itemView.findViewById(R.id.tv_course_name);
            courseDescTv = itemView.findViewById(R.id.tv_course_desc);
            deleteCb = itemView.findViewById(R.id.cb_delete);
            deleteCb.setTag(this);
            deleteCb.setOnCheckedChangeListener(listener);
        }

    }

    private static class LibraryHolder extends RecyclerView.ViewHolder {

        AppCompatCheckBox deleteCb;
        TextView mTvTitle;
        TextView mTvAbstract;
        TextView mTvReadStatus;
        ImageView mIvFileType;

        public LibraryHolder(@NonNull View itemView, OnClickListener onClickListener, CompoundButton.OnCheckedChangeListener listener) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvAbstract = itemView.findViewById(R.id.tv_abstract);
            mTvReadStatus = itemView.findViewById(R.id.tv_status_read);
            mIvFileType = itemView.findViewById(R.id.iv_file_type);
            deleteCb = itemView.findViewById(R.id.cb_delete);
            deleteCb.setTag(this);
            deleteCb.setOnCheckedChangeListener(listener);

        }
    }

    public interface VideoFolderClickListener {
        void videoFolderClick(String folderId,DownloadItem downloadItem, int position);

    }

    public interface LibraryClickListener {
        void libraryClick(String filePath,String itemId, int position);
    }


    public interface DownloadingFolderClickListener {
        void downloadingFolderClick();
    }

    public void setVideoFolderClickListener(VideoFolderClickListener listener) {
        this.videoFolderListener = listener;
    }

    public void setDownloadingFolderClickListener(DownloadingFolderClickListener listener) {
        this.downloadingFolderListener = listener;
    }

    public void setLibraryClickListener(LibraryClickListener libraryClickListener) {
        this.libraryClickListener = libraryClickListener;
    }

    protected static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            if (holder != null) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    onClick(adapterPosition, v);
                }
            }
        }

        public abstract void onClick(int position, View view);
    }

    public void setSelectionChangedListener(OnSelectionChangedListener selectionChangedListener) {
        this.selectionChangedListener = selectionChangedListener;
    }

    public interface OnSelectionChangedListener {
        void selectionChanged(boolean isSelectedAll);
    }
}
