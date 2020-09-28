package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.adapter.SimpleTeacherInfoAdapter;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.text.MessageFormat;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
public class DetailTeacherHolder extends BaseMultipleTypeViewHolder<List<PublicTeacherBean>> {
    public DetailTeacherHolder(ViewGroup parent) {
        super(parent);


    }

    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_teacher;
    }


    @Override
    public void bindData(List<PublicTeacherBean> model, int position, BaseRecyclerAdapter adapter) {
//        View vsRoot=((ViewStub) getView(R.id.vs)).inflate();
        RecyclerView rv = (RecyclerView) getView(R.id.rv);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.addItemDecoration(SpaceItemDecoration.create().setSpace(35).setIncludeEdge(false).setLayoutManagerType(SpaceItemDecoration.LINEARLAYOUT));
        SimpleTeacherInfoAdapter simpleTeacherInfoAdapter = new SimpleTeacherInfoAdapter(getContext());
        simpleTeacherInfoAdapter.setOnItemClickListener((holder, position1, view, item) -> JumpHelper.jumpWebViewNoNeedAppTitle(MessageFormat.format("{0}?id={1}&back=1", ConstsH5Url.getTeacher(), String.valueOf(item.getTeacherId()))));
        rv.setAdapter(simpleTeacherInfoAdapter);
        simpleTeacherInfoAdapter.addAll(model);
    }
}