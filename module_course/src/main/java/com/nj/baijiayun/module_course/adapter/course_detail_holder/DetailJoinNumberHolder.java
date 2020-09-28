package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.AssembleJoinInfoAdapter;
import com.nj.baijiayun.module_course.bean.wx.AssembleJoinInfoBean;
import com.nj.baijiayun.module_course.widget.AutoVerticalScrollRecycleView;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.helper.ContextGetHelper;

import java.text.MessageFormat;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author chengang
 * @date 2019-11-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
public class DetailJoinNumberHolder extends BaseMultipleTypeViewHolder {

    AssembleJoinInfoAdapter adapter;
    AutoVerticalScrollRecycleView mRv;

    public DetailJoinNumberHolder(ViewGroup parent) {
        super(parent);

    }

    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_assemble_person;
    }

    @Override
    public void bindData(Object model, int position, BaseRecyclerAdapter adapter) {

    }


    public void bindData(int number, List<AssembleJoinInfoBean> datas) {
        mRv = (AutoVerticalScrollRecycleView) getView(R.id.rv);
        ContextGetHelper.getActivityFromView(mRv).getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (adapter != null) {
                    adapter.cancelAllTimers();
                }

            }
        });

        setRvHeight(datas);
        //设置人数颜色
        String format = MessageFormat.format(getContext().getString(R.string.course_fmt_assemble_join_number), String.valueOf(number));
        setText(R.id.tv_join_number, TextSpanHelper.matcherSearchKeyWord(format, String.valueOf(number),
                TextSpanHelper.getTextColorSpan(ContextCompat.getColor(getContext(), R.color.public_orange))));
        if (datas.size() <= 0) {
            return;
        }
        adapter = new AssembleJoinInfoAdapter(getContext());
        //重置数据
        adapter.getAllItems().clear();
        adapter.clear();
        adapter.setCallBack(bean -> {
            mRv.stopAutoScroll();
            //可能会回调多次
            int position = adapter.getAllItems().indexOf(bean);
            if (position < 0) {
                return;
            }
            adapter.getAllItems().remove(position);
            adapter.notifyDataSetChanged();
            setRvHeight(adapter.getAllItems());
            tryAutoScroll();

        });
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        //禁用自己的滑动
        mRv.setOnTouchListener((v, event) -> true);
        adapter.getAllItems().addAll(datas);
        mRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tryAutoScroll();


    }

    private void tryAutoScroll() {
        if (adapter.getAllItems().size() > 2) {
            if (mRv.isAutoScroll()) {
                mRv.startScroll();
                return;
            }
            AutoVerticalScrollRecycleView.ScrollCallBack scrollCallBack = () -> {
                if (!mRv.isAutoScroll()) {
                    return;
                }
                Logger.d("setCallBack-->  changePosition");
                //这里移动数据位置，使得无限滚动
                adapter.getAllItems().add(adapter.getItem(0));
                adapter.getAllItems().remove(0);
                adapter.notifyDataSetChanged();
                if (mRv.getLayoutManager() != null) {
                    mRv.getLayoutManager().scrollToPosition(0);
                }
            };
            mRv.setScrollCallBack(scrollCallBack);
            mRv.startScroll();
        } else {
            mRv.stopAutoScroll();
        }
    }


    private void setRvHeight(List<AssembleJoinInfoBean> datas) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        int count = (datas == null || datas.size() < 2) ? datas.size() : 2;
        layoutParams.height = DensityUtil.dip2px(64 * count);
        mRv.setLayoutParams(layoutParams);
    }


}
