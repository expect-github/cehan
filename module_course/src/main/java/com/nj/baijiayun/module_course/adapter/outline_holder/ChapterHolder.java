package com.nj.baijiayun.module_course.adapter.outline_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.bean.wx.ChapterBean;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.WxCourseDetailActivity;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.ExpandHelper;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.outline_holder
 * @describe
 */
@AdapterCreate(group = "outLine")
public class ChapterHolder extends BaseMultipleTypeViewHolder<ChapterBean> {
    public ChapterHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> {
            WxCourseDetailActivity.adaNun = 0;
            ExpandHelper.expandOrCollapseTree(getAdapter(), getClickPosition());
//            updateExpandUi(getClickModel());
            //刷新当前图标变化
            getAdapter().notifyItemChanged(getClickPosition());
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.course_item_outline_chapter;
    }

    @Override
    public void bindData(ChapterBean model, int position, BaseRecyclerAdapter adapter) {
//        if (WxCourseDetailActivity.numType){
//            WxCourseDetailActivity.num = WxCourseDetailActivity.num+1;
//            model.setNum(WxCourseDetailActivity.num);
//        }
//        setText(R.id.tv_chapter_num,model.getNum()+"");
        WxCourseDetailActivity.adaType = true;
        setVisible(R.id.view_line, position != 0);
        setText(R.id.tv_chapter_name, model.getTitle());
        updateExpandUi(model);

    }


    private void updateExpandUi(ChapterBean model) {
        setImageResource(R.id.iv_dir, model.getTreeItemAttr().isExpand() ? R.drawable.common_ic_filter_up : R.drawable.common_ic_filter_down);
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }
}
