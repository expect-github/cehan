package com.nj.baijiayun.module_public.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.adapter
 * @describe
 */
public class SimpleTeacherInfoAdapter extends BaseRecyclerAdapter<PublicTeacherBean> {
    public SimpleTeacherInfoAdapter(Context context) {
        super(context);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.public_item_teacher_simple;
    }

    @Override
    protected void bindViewAndData(BaseViewHolder holder, PublicTeacherBean publicTeacherBean, int position) {
        holder.setText(R.id.tv_name, publicTeacherBean.getName());
        ImageLoader.with(getContext()).load(publicTeacherBean.getAvatar()).asCircle().into((ImageView) holder.getView(R.id.iv_head));
    }
}
