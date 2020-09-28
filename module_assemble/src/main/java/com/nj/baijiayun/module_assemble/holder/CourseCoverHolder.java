package com.nj.baijiayun.module_assemble.holder;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_assemble.R;
import com.nj.baijiayun.module_assemble.bean.AssembleBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.ConstsNormal;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewSplitLineHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.holder
 * @describe
 */
public class CourseCoverHolder extends BaseMultipleTypeViewHolder<AssembleBean> {
    public CourseCoverHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> JumpHelper.jumpCourseDetail(getClickModel().getCourseBasisId()));
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.assemble_item_course_v1;
    }

    @Override
    public void bindData(AssembleBean model, int position, BaseRecyclerAdapter adapter) {
        ViewSplitLineHelper.setLineVisible(this,position);
        ImageLoader.with(getContext()).load(model.getCoverImg()).rectRoundCorner(ConstsNormal.COVER_ROUND_CORNER).into((ImageView) getView(R.id.iv_cover));
        setText(R.id.tv_course_type, ConstsCouseType.getCourseTypeName(model.getCourseType()));
        AssembleJoinNumberHelper.setCommonInfo(this, model);
    }


}
