package com.nj.baijiayun.module_course.adapter.my_course;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.outline_holder.ChapterHolder;
import com.nj.baijiayun.module_course.bean.wx.ChapterBean;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.my_course
 * @describe
 */
@AdapterCreate(group = "learnedDetail")
public class MyLearnedCourseChapterHolder extends ChapterHolder {


    public MyLearnedCourseChapterHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return R.layout.course_item_learned_detail_chapter;
    }

    @Override
    public void bindData(ChapterBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv_chapter_name, model.getTitle());
        setImageResource(R.id.iv_dir, model.getTreeItemAttr().isExpand() ? R.drawable.common_ic_filter_up : R.drawable.common_ic_filter_down);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getConvertView().getLayoutParams();
        // 这里的margin 首先 可自定义ItemDecoration 实现
        layoutParams.topMargin = position == 0 ? DensityUtil.dip2px(15) : 0;
        layoutParams.bottomMargin = model.getTreeItemAttr().isExpand() ? 0 : DensityUtil.dip2px(20);
        getConvertView().setLayoutParams(layoutParams);

    }
}