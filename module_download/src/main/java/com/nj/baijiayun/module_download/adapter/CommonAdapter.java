package com.nj.baijiayun.module_download.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本的适配器
 */
@SuppressWarnings("unused")
public abstract class CommonAdapter<T extends Object, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected final Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mItems;
    protected OnItemClickListener<T> onItemClickListener;
    protected OnClickListener onClickListener;


    public CommonAdapter(Context context) {
        this.mItems = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, view, mItems.get(position));
            }
        };

    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        final V holder = onCreateDefaultViewHolder(parent, viewType);
        if (holder != null) {
            holder.itemView.setTag(holder);
            holder.itemView.setOnClickListener(onClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        onBindViewHolder(holder, mItems.get(position), position);
    }

    protected abstract V onCreateDefaultViewHolder(ViewGroup parent, int type);

    protected abstract void onBindViewHolder(V holder, T item, int position);

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addAll(List<T> items) {
        addAll(items, false);
    }

    public void addAll(List<T> items, boolean clearAll) {
        if (clearAll) {
            mItems.clear();
        }
        if (items != null && items.size() > 0) {
            int size = mItems.size();
            mItems.addAll(items);
            if (!clearAll) {
                notifyItemRangeInserted(size, items.size());
            } else {
                notifyDataSetChanged();
            }
        }
    }

    final void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }

    public final List<T> getItems() {
        return mItems;
    }


    public final T getItem(int position) {
        if (position < 0 || position >= mItems.size())
            return null;
        return mItems.get(position);
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


    public interface OnItemClickListener<T> {
        void onItemClick(int position, View view, T item);
    }

    public final void removeItem(T item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    protected final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected final void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView, HolderClickListener listener) {
            super(itemView);
            listener.setHolder(this);
        }

        public abstract static class HolderClickListener implements View.OnClickListener {
            private ViewHolder holder;

            private void setHolder(ViewHolder holder) {
                this.holder = holder;
            }

            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    onClick(adapterPosition, v);
                }
            }

            public abstract void onClick(int position, View view);
        }
    }

}
