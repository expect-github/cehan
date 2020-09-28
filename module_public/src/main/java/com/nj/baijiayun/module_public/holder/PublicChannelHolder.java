package com.nj.baijiayun.module_public.holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.IChannel;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;


/**
 * @author chengang
 * @date 2019-08-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.holder
 * @describe
 */
public abstract class PublicChannelHolder extends BaseMultipleTypeViewHolder<IChannel> {
    public PublicChannelHolder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.iv_right, v -> jump(getClickModel()));
        setOnClickListener(R.id.tv_more, v -> jump(getClickModel()));
    }

    public abstract void jump(IChannel clickModel);


    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.public_item_channel;
    }

    @Override
    public void bindData(IChannel model, int position, BaseRecyclerAdapter adapter) {

    }
}
