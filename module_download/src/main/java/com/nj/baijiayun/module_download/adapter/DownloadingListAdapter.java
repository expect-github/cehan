package com.nj.baijiayun.module_download.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nj.baijiayun.module_download.R;
import com.nj.baijiayun.module_download.bean.CheckableWrapper;
import com.nj.baijiayun.module_download.helper.DownloadTypeHelper;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.downloader.utils.VideoDownloadUtils;

import java.util.ArrayList;
import java.util.List;


public class DownloadingListAdapter extends CommonDownloadAdapter<DownloadingListAdapter.ViewHolder> {

    public DownloadingListAdapter(Context context) {
        super(context);
    }

    @Override
    public void addRawList(List<DownloadItem> downloadItems, boolean clear) {

        List<CheckableWrapper<DownloadItem>> items = new ArrayList<>();
        for (DownloadItem downloadItem : downloadItems) {
            items.add(wrapperVideoItem(downloadItem));
        }
        addAll(items, clear);
    }

    @Override
    protected ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        final DownloadingListAdapter.ViewHolder viewHolder =
                new DownloadingListAdapter.ViewHolder
                        (mInflater.inflate(R.layout.download_recycler_item_downloading_item, null),
                                onCheckedChangeListener);
        viewHolder.itemView.setOnLongClickListener(v -> {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (adapterPosition >= 0) {
                if (onLongClickListener != null) {
                    return onLongClickListener.onLongClick(adapterPosition, v, getItem(adapterPosition));
                }
            }
            return true;
        });
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, CheckableWrapper<DownloadItem> item, int position) {
        if (isInEdit) {
            holder.deleteCb.setVisibility(View.VISIBLE);
            holder.deleteCb.setChecked(item.isChecked());
        } else {
            holder.deleteCb.setVisibility(View.GONE);
        }
        DownloadItem downloadItem = item.getItem();
        holder.courseNameTv.setText(downloadItem.getFileName());
        holder.typeIv.setImageResource(DownloadTypeHelper.getTypeImg(downloadItem.getFileType()));
//        ImageLoader.with(mContext).load(item.getParent().getParentCover()).into(holder.typeIv);
        updateItem(holder, downloadItem);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void updateItem(ViewHolder holder, DownloadItem item) {
        switch (item.getDownloadStatus()) {
            case DownloadItem.DOWNLOAD_STATUS_DOWNLOADING:
                holder.downloadStatusTv.setText(mContext.getString(R.string.download_speed_format, VideoDownloadUtils.getFormatSize(item.getDownloadSpeed())));
                break;
            case DownloadItem.DOWNLOAD_STATUS_STOP:
                holder.downloadStatusTv.setText(R.string.download_pause);
                break;
            case DownloadItem.DOWNLOAD_STATUS_WAITING:
                holder.downloadStatusTv.setText(R.string.download_waiting);
                break;
        }
        holder.downloadPb.setProgress(item.getDownloadRate());
        holder.downloadSizeTv.setText(mContext.getString(R.string.down_size_format,
                VideoDownloadUtils.getFormatSize(item.getCurrentSize()),
                VideoDownloadUtils.getFormatSize(item.getFileSize())));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox deleteCb;
        TextView courseNameTv;
        TextView downloadStatusTv;
        TextView downloadSizeTv;
        ImageView typeIv;
        ProgressBar downloadPb;

        public ViewHolder(View itemView, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            super(itemView);
            typeIv = itemView.findViewById(R.id.iv_type);
            courseNameTv = itemView.findViewById(R.id.tv_name);
            downloadSizeTv = itemView.findViewById(R.id.tv_download_size);
            downloadStatusTv = itemView.findViewById(R.id.tv_download_status);
            downloadPb = itemView.findViewById(R.id.pb_download);
            deleteCb = itemView.findViewById(R.id.cb_delete);
            deleteCb.setTag(this);
            deleteCb.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }
}
