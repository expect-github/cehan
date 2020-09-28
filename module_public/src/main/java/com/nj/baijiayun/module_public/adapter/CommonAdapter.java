package com.nj.baijiayun.module_public.adapter;

import android.content.Context;


import java.util.List;

/**
 * 公共的RecyclerView.Adapter
 *
 * @author yangfei
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, List<T> list, final int layoutId) {
        super(context, list);
        super.addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
        // 解决全局刷新时图片闪烁
        super.setHasStableIds(true);
    }

    /**
     * 解决全局刷新时图片闪烁
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract void convert(ViewHolder holder, T item, int position);

}