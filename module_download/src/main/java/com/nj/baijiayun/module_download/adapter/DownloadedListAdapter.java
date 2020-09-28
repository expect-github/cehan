package com.nj.baijiayun.module_download.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_download.R;
import com.nj.baijiayun.module_download.bean.CheckableWrapper;
import com.nj.baijiayun.module_download.helper.DownloadTypeHelper;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;

import java.util.ArrayList;
import java.util.List;

public class DownloadedListAdapter extends CommonDownloadAdapter<DownloadedListAdapter.ViewHolder> {

    public DownloadedListAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        final ViewHolder viewHolder = new ViewHolder(mInflater.inflate(R.layout.download_recycler_item_video_item, null), onCheckedChangeListener);
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
        holder.nameTv.setText(downloadItem.getFileName());
        holder.typeIv.setImageResource(DownloadTypeHelper.getTypeImg(item.getItem().getFileType()));
        holder.timeTv.setText(TimeFormatHelper.getFullDateSplitByLine(downloadItem.getStartTime()));

    }

    public void addRawList(List<DownloadItem> downloadItems, boolean clear) {
        List<CheckableWrapper<DownloadItem>> items = new ArrayList<>();
        for (DownloadItem downloadItem : downloadItems) {
            if (downloadItem.getDownloadStatus() == DownloadItem.DOWNLOAD_STATUS_COMPLETE) {
                items.add(wrapperVideoItem(downloadItem));
            } else {
//                downloadItem.setDownloadListener(listener);
            }
        }
        addAll(items, clear);
    }

    @NonNull
    protected  <T> CheckableWrapper<T> wrapperVideoItem(T item) {
        CheckableWrapper<T> wrapper = new CheckableWrapper<>();
        wrapper.setChecked(false);
        wrapper.setItem(item);
        return wrapper;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox deleteCb;
        ImageView typeIv;
        TextView nameTv;
        TextView timeTv;

        public ViewHolder(View itemView, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            super(itemView);
            deleteCb = itemView.findViewById(R.id.cb_delete);
            typeIv = itemView.findViewById(R.id.iv_type);
            nameTv = itemView.findViewById(R.id.tv_name);
            timeTv = itemView.findViewById(R.id.tv_download_time);
            deleteCb.setTag(this);
            deleteCb.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }
}
