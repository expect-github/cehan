package com.nj.baijiayun.module_course.adapter.outline_holder;

import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.WxCourseDetailActivity;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.holder.PublicCourseHolder;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.outline_holder
 * @describe
 */
@AdapterCreate(group = "outLine")
public class SystemOutLineholder extends PublicCourseHolder {
    public SystemOutLineholder(ViewGroup parent) {
        super(parent);
        getView(R.id.cl_root).setBackground(null);
        getConvertView().setOnClickListener(v -> {

            if (ConstsCouseType.isPublic(getClickModel().getCourseType())) {
                playPublicOpenCourse(getContext(), getClickModel().getPlayId(), getClickModel().getCourseType());
                return;
            }
            Postcard postcard = ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL)
                    .withInt("courseId", getClickModel().getId());
            if (getContext() instanceof WxCourseDetailActivity) {
                PublicCourseDetailBean courseBean = ((WxCourseDetailActivity) getContext()).getPresenter().getCourseBean();
                if (ConstsCouseType.isSystem(courseBean.getCourseType())) {
                    postcard.withInt("system_course_id", courseBean.getId());
                }
            }
            postcard.navigation();

        });

//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getConvertView().getLayoutParams();
//        layoutParams.leftMargin=3;
//        layoutParams.rightMargin=3;
//        getConvertView().setLayoutParams(layoutParams);
    }

//    @Override
//    public int bindLayout() {
//        return R.layout.course_item_system_course;
//    }
}
