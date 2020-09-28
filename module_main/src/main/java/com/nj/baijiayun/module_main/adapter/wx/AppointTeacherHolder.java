package com.nj.baijiayun.module_main.adapter.wx;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2020/4/10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter
 * @describe
 */
@AdapterCreate(group = "appoint_teacher")
public class AppointTeacherHolder extends BaseMultipleTypeViewHolder<PublicTeacherBean> {
    public AppointTeacherHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> JumpHelper.jumpTeacherInfo(getClickModel().getTeacherId()));
    }


    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }


    @Override
    public int bindLayout() {
        return R.layout.main_item_appoint_teacher;
    }

    @Override
    public void bindData(PublicTeacherBean model, int position, BaseRecyclerAdapter adapter) {
        ImageLoader.with(getContext()).asCircle().load(model.getAvatar()).into((ImageView) getView(R.id.iv_cover));
        setText(R.id.tv_title, model.getName());
        setText(R.id.tv_content, model.getIntroduction());

    }

}
