package com.nj.baijiayun.module_common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author chengang
 * @date 2019-06-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget
 * @describe 筛选布局  有待改进
 */
public class FilterView extends LinearLayout {

    List<MyAdapter> mFlowLayoutAdapters = new ArrayList<>();
    List<TagFlowLayout> tagFlowLayouts = new ArrayList<>();

    public static class ChildBean {
        private int id;
        private String content;

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

    }

    public static class ParentBean {
        private String content;
        private int selectId;
        private List<ChildBean> childBeans;

        public int getSelectId() {
            return selectId;
        }

        public void setSelectId(int selectId) {
            this.selectId = selectId;
        }

        public List<ChildBean> getChildBeans() {
            if (childBeans == null) {
                childBeans = new ArrayList<>();
            }
            return childBeans;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


    public FilterView(Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.common_layout_filter, null);
        this.addView(inflate);
        initView(inflate);
    }


    private LinearLayout mLlRoot;
    private TextView mResetTv;
    private TextView mConfirmTv;

    public void initView(View view) {
        mLlRoot = view.findViewById(R.id.ll_flow_contailner);
        mResetTv = view.findViewById(R.id.tv_reset);
        mConfirmTv = view.findViewById(R.id.tv_confirm);
        mResetTv.setOnClickListener(v -> reset());
        mConfirmTv.setOnClickListener(v -> {

            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < tagFlowLayouts.size(); i++) {
                int selectIndex = getSelectIndex(tagFlowLayouts.get(i));
                result.add(mDatas.get(i).getChildBeans().get(selectIndex).id);
            }
            if (callBack != null) {
                callBack.back(result);
            }

        });

    }

    private int getSelectIndex(TagFlowLayout tagFlowLayout) {
        int index = 0;
        Set<Integer> s = tagFlowLayout.getSelectedList();
        for (Integer id : s) {
            index = id;
        }
        return index;
    }

    CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void back(List<Integer> data);
    }

    private List<ParentBean> mDatas;

    public void setData(List<ParentBean> datas) {
        this.mDatas = datas;
        for (int i = 0; i < datas.size(); i++) {
            addParent(i, datas.get(i));
        }
        reset();
    }

    private void reset() {
        for (int i = 0; i < mFlowLayoutAdapters.size(); i++) {
            mFlowLayoutAdapters.get(i).setSelectedList(0);
        }
    }

    private void addParent(int position, ParentBean parentBean) {

        TextView tvParent = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.common_filter_parent, null);
        mLlRoot.addView(tvParent);
        tvParent.setText(parentBean.getContent());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvParent.getLayoutParams();
        if (position == 0) {
            layoutParams.topMargin = DensityUtil.dip2px(15);
        }
        layoutParams.leftMargin = DensityUtil.dip2px(15);
        layoutParams.bottomMargin = DensityUtil.dip2px(10);
        tvParent.setLayoutParams(layoutParams);


        MyAdapter adapter = new MyAdapter(parentBean.childBeans);
        TagFlowLayout tagFlowLayout = new TagFlowLayout(getContext());
        tagFlowLayout.setMaxSelectCount(1);
        mLlRoot.addView(tagFlowLayout);

        setTagFlowLayoutLayoutParams(tagFlowLayout);


        tagFlowLayout.setAdapter(adapter);
        tagFlowLayouts.add(tagFlowLayout);
        mFlowLayoutAdapters.add(adapter);
    }

    private void setTagFlowLayoutLayoutParams(TagFlowLayout tagFlowLayout) {
        LinearLayout.LayoutParams layoutParams1 = (LayoutParams) tagFlowLayout.getLayoutParams();
        layoutParams1.leftMargin = DensityUtil.dip2px(15);
        layoutParams1.bottomMargin = DensityUtil.dip2px(25);
        tagFlowLayout.setLayoutParams(layoutParams1);
    }


    class MyAdapter extends TagAdapter<ChildBean> {
        MyAdapter(List<ChildBean> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, ChildBean flowListBean) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.common_filter_child, parent, false);
            textView.setText(flowListBean.getContent());
            //这里默认按照设计图4个算的，左右15margin 这个比较合适 感觉不需要改动
            setTagLayoutChildMiniWidth(textView);
            return textView;
        }
    }

    public void setTagLayoutChildMiniWidth(TextView textView) {
        textView.setMinWidth((getScreenWidth() - DensityUtil.dip2px(15 * 2 + 3 * 8)) / 4);

    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
