package com.nj.baijiayun.module_distribution.holder;

import android.content.Intent;
import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_distribution.R;
import com.nj.baijiayun.module_distribution.bean.ChannelBean;
import com.nj.baijiayun.module_distribution.consts.GoodsType;
import com.nj.baijiayun.module_distribution.ui.list.DistributionFliterListActivity;
import com.nj.baijiayun.module_public.bean.IChannel;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.holder
 * @describe
 */
@AdapterCreate
public class DtbChannelHolder extends BaseMultipleTypeViewHolder<ChannelBean> {
    public DtbChannelHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> jump(getClickModel()));
    }

    @Override
    public int bindLayout() {
        return R.layout.public_item_channel;
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public void bindData(ChannelBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv_title, model.getTitle());
    }


    private void jump(IChannel clickModel) {
        Intent intent = new Intent(getContext(), DistributionFliterListActivity.class);
        intent.putExtra("type", ((ChannelBean) clickModel).isCourse() ? GoodsType.TYPE_COURSE : GoodsType.TYPE_BOOK);
        getContext().startActivity(intent);

    }
}
