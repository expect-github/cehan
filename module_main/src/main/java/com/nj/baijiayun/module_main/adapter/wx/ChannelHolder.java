package com.nj.baijiayun.module_main.adapter.wx;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.wx.ChannelInfoBean;
import com.nj.baijiayun.module_main.helper.HomeTabPageHelper;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import static com.nj.baijiayun.module_main.fragments.SelectCourseFragment.SELECT_COURSE_TYPE_TAB;

/**
 * @author chengang
 * @date 2019-08-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.holder
 * @describe
 */
@AdapterCreate
public class ChannelHolder extends BaseMultipleTypeViewHolder<ChannelInfoBean> {
    public ChannelHolder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.iv_right, v -> jump(getClickModel()));
        setOnClickListener(R.id.tv_more, v -> jump(getClickModel()));
    }

    private void jump(ChannelInfoBean clickModel) {

        String router = getRouter(clickModel);
        if (clickModel.isPublicOpenCourse()) {
            if (HomeTabPageHelper.isRouterPathInHomeBottomTab(router)) {
                HomeTabPageHelper.checkHomeTabOrJumpNewPageByRouter(router);
                LiveDataBus.get().with(SELECT_COURSE_TYPE_TAB).postValue(ConstsCouseType.getCourseTypePublic());

            } else {
                ARouter.getInstance().build(RouterConstant.PAGE_SELECT_COURSE)
                        .withInt("courseType", ConstsCouseType.getCourseTypePublic())
                        .navigation();
            }

        } else {
            HomeTabPageHelper.checkHomeTabOrJumpNewPageByRouter(router);

        }
    }

    private static String getRouter(ChannelInfoBean model) {
        if (model.isBookType()) {
            return ConstsH5Url.ROUTER_BOOK_LIST;
        } else if (model.isCourseType() || model.isPublicOpenCourse()) {
            return ConstsH5Url.ROUTER_COURSE;
        } else if (model.isNewsType()) {
            return ConstsH5Url.ROUTER_NEWS;
        } else if (model.isTeacherType()) {
            return ConstsH5Url.ROUTER_TEACHER_LIST;
        }
        return null;
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_channel_v1;
    }

    @Override
    public void bindData(ChannelInfoBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv_title, model.getTitle());
        setVisible(R.id.tv_more, model.needShowMore());
        setVisible(R.id.iv_right, model.needShowMore());
        ((TextView) getView(R.id.tv_title)).setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getContext().getResources().getDimensionPixelSize(
                        model.isPublicOpenCourse() ? R.dimen.design_title_big_19 : R.dimen.design_title_big));
        setBackgroundColor(R.id.cl_bg, model.isPublicOpenCourse() ? Color.TRANSPARENT : Color.TRANSPARENT);
    }
}
