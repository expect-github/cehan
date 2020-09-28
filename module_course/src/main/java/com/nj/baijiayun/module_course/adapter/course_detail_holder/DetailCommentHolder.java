package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.ui.fragment.CourseCommentFragment;
import com.nj.baijiayun.module_public.manager.LifecycleManager;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe 这个holder 不要被二次创建
 */
public class DetailCommentHolder extends BaseMultipleTypeViewHolder<Integer> {

    private CourseCommentFragment mFragment;

    public DetailCommentHolder(ViewGroup parent, BaseAppActivity activity) {
        super(parent);
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof CourseCommentFragment) {
                mFragment = (CourseCommentFragment) fragments.get(i);
                break;
            }
        }
        LifecycleManager.create((LifecycleOwner) getContext()).addLifecycleCallBack(new LifecycleManager.BaseObserver() {
            @Override
            public void onDestory() {
                super.onDestory();
                if (mFragment!= null&&mFragment.getWebView()!=null) {
                    mFragment.getWebView().release();
                }
            }
        });

    }

    private boolean isLoadData = false;

    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_comment;
    }

    @Override
    public void bindData(Integer courseId, int position, BaseRecyclerAdapter adapter) {
        //避免支付，登陆改变二次加载
        if (isLoadData) {
            return;
        }
        mFragment.loadUrlByCourseId(courseId);
        isLoadData = true;
    }


}
