package com.nj.baijiayun.module_download.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.nj.baijiayun.module_download.bean.CheckableWrapper;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CommonDownloadAdapter<V extends RecyclerView.ViewHolder> extends CommonAdapter<CheckableWrapper<DownloadItem>, V> {
    protected boolean isInEdit = false;
    private OnSelectionChangedListener selectionChangedListener;
    protected CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) buttonView.getTag();
        int position = viewHolder.getAdapterPosition();
        if (position >= 0) {
            getItem(position).setChecked(isChecked);
        }
        if (selectionChangedListener != null) {
            selectionChangedListener.selectionChanged(isAllChecked(isChecked));
        }

    };

    private boolean isAllChecked(boolean isChecked) {
        if (!isChecked) {
            return false;
        } else {
            for (CheckableWrapper<DownloadItem> item : getItems()) {
                if (!item.isChecked()) {
                    return false;
                }
            }

        }
        return true;
    }

    protected OnItemLongClickListener<CheckableWrapper<DownloadItem>> onLongClickListener;

    public CommonDownloadAdapter(Context context) {
        super(context);
    }


    public void setInEditMode(boolean inEditMode) {
        isInEdit = inEditMode;
        clearEditInfo();
        notifyDataSetChanged();
    }

    protected void clearEditInfo() {
        for (CheckableWrapper item : getItems()) {
            item.setChecked(false);
        }
    }

    public boolean isInEdit() {
        return isInEdit;
    }


    @NonNull
    protected <T> CheckableWrapper<T> wrapperVideoItem(T item) {
        CheckableWrapper<T> wrapper = new CheckableWrapper<>();
        wrapper.setChecked(false);
        wrapper.setItem(item);
        return wrapper;
    }

    public List<DownloadItem> getSelectedItems() {
        ArrayList<DownloadItem> selectedItem = new ArrayList<>();
        for (CheckableWrapper<DownloadItem> checkableWrapper : getItems()) {
            if (checkableWrapper.isChecked()) {
                selectedItem.add(checkableWrapper.getItem());
            }
        }
        return selectedItem;
    }

    public boolean removeAllSelected() {
        Iterator<CheckableWrapper<DownloadItem>> iterator = getItems().iterator();
        while (iterator.hasNext()) {
            CheckableWrapper checkableWrapper = iterator.next();
            if (checkableWrapper.isChecked()) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();
        return getItems().isEmpty();
    }

    public void selectedItems() {
        for (CheckableWrapper checkableWrapper : getItems()) {
            checkableWrapper.setChecked(true);
        }
        notifyDataSetChanged();
    }

    public void changePositionSelection(int position) {
        CheckableWrapper item = getItem(position);
        item.setChecked(!item.isChecked());
        notifyItemChanged(position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<CheckableWrapper<DownloadItem>> onItemLongClickListener) {
        this.onLongClickListener = onItemLongClickListener;
    }

    public void selectedAllVideo(boolean selectAll) {
        for (CheckableWrapper checkableWrapper : getItems()) {
            checkableWrapper.setChecked(selectAll);
        }
        if (selectionChangedListener != null) {
            selectionChangedListener.selectionChanged(selectAll);
        }
        notifyDataSetChanged();
    }

    public abstract void addRawList(List<DownloadItem> downloadItems, boolean b);

    public interface OnItemLongClickListener<I> {

        boolean onLongClick(int position, View view, I item);
    }

    public void setSelectionChangedListener(OnSelectionChangedListener selectionChangedListener) {
        this.selectionChangedListener = selectionChangedListener;
    }

    public interface OnSelectionChangedListener {
        void selectionChanged(boolean isSelectedAll);
    }

}
