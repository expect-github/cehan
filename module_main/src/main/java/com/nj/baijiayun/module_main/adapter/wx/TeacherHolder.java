package com.nj.baijiayun.module_main.adapter.wx;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewSplitLineHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-08-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx
 * @describe
 */
@AdapterCreate
public class TeacherHolder extends BaseMultipleTypeViewHolder<PublicTeacherBean> {
    public TeacherHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> JumpHelper.jumpTeacherInfo(getClickModel().getTeacherId()));

    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_teacher_v1;
    }

    @Override
    public void bindData(PublicTeacherBean model, int position, BaseRecyclerAdapter adapter) {
        ViewSplitLineHelper.setLineVisible(this,position);
        ImageLoader.with(getContext()).asCircle().load(model.getAvatar()).into((ImageView) getView(R.id.iv_head));
        setText(R.id.tv_name, model.getName());
        setText(R.id.tv_content, model.getIntroduction());
        setText(R.id.tv_level, model.getLevelName());
        setVisible(R.id.tv_level, model.isHasLevel());
        setVisibleInVisible(R.id.tv_is_oto, model.isOtoTeacher());
    }
}
