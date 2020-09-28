package com.nj.baijiayun.module_public.widget.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_public.R;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.jetbrains.annotations.NotNull;

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
public class FilterNewView extends LinearLayout {
    private static final int MAX_SELECT = 1;
    List<TagLayoutAdapter> mFlowLayoutAdapters = new ArrayList<>();
    List<TagFlowLayout> tagFlowLayouts = new ArrayList<>();
    private CallBack callBack;
    private LinearLayout mLlRoot;
    private List<FilterParentBean> mDatas;
    private boolean isNeedBottomConfirm = true;
    private View mViewBottom;
    private int[] selectIds;

    public void setNeedBottomConfirm(boolean needBottomConfirm) {
        this.isNeedBottomConfirm = needBottomConfirm;
        if (!isNeedBottomConfirm) {
            mViewBottom.setVisibility(GONE);
        }
    }

    private void setTagLayoutListener() {
        for (int i = 0; i < tagFlowLayouts.size(); i++) {
            tagFlowLayouts.get(i).setOnTagClickListener((view, position, parent) -> {
                callbackSelect();

                return false;
            });
        }
    }

    public FilterNewView(Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.public_layout_filter, this);
        initView(inflate);
//        setBackgroundColor(Color.WHITE);

    }


    public void initView(View view) {
        mLlRoot = view.findViewById(R.id.ll_flow_contailner);
        mViewBottom = view.findViewById(R.id.ll_bottom);
        TextView mResetTv = view.findViewById(R.id.tv_reset);
        TextView mConfirmTv = view.findViewById(R.id.tv_confirm);
        mResetTv.setOnClickListener(v -> {
            resetInner();
            callbackSelect();
        });
        mConfirmTv.setOnClickListener(v -> {
            callbackSelect();

        });

    }

    private void callbackSelect() {

        if (callBack != null) {
            selectIds = getSelectIdsInner();
            callBack.back(getSelectParent());
        }
    }

    @NotNull
    private List<FilterParentBean> getSelectParent() {
        List<FilterParentBean> result = new ArrayList<>();
        for (int i = 0; i < tagFlowLayouts.size(); i++) {
            if (tagFlowLayouts.get(i).getSelectedList().size() > 0) {
//                int selectIndex = getSelectId(tagFlowLayouts.get(i));
                int selectIndex = getSelectIndex(tagFlowLayouts.get(i));
                mDatas.get(i).setSelectId(mDatas.get(i).getChildBeans().get(selectIndex).getId());
                result.add(mDatas.get(i));
            }
        }
        return result;
    }

    private int getSelectIndex(TagFlowLayout tagFlowLayout) {
        int resultIndex = 0;
        Set<Integer> s = tagFlowLayout.getSelectedList();
        for (Integer index : s) {
            resultIndex = index;
        }
        return resultIndex;
    }


    public void clearData() {
        mLlRoot.removeAllViews();
        this.mDatas = null;
        if (mFlowLayoutAdapters != null) {
            mFlowLayoutAdapters.clear();
        }
        if (tagFlowLayouts != null) {
            tagFlowLayouts.clear();
        }
        selectIds = null;
    }

    public void setData(List<FilterParentBean> datas) {
        clearData();
        this.mDatas = datas;
        for (int i = 0; i < datas.size(); i++) {
            addParent(i, datas.get(i));
        }
        if (!isNeedBottomConfirm) {
            setTagLayoutListener();
        }
        resetInner();
    }

    public List<FilterParentBean> getDatas() {
        return mDatas;
    }

    private void resetInner() {
        for (int i = 0; i < mFlowLayoutAdapters.size(); i++) {
            mFlowLayoutAdapters.get(i).setSelectedList();
        }
    }

    public void reset() {
        resetInner();
        selectIds = null;
    }

    private void addParent(int position, FilterParentBean parentBean) {

        if (isNeedLine() && position > 0) {
            getLineView(getContext(), mLlRoot);

        }

        TextView tvParent = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.common_filter_parent, null);
        tvParent.setText(parentBean.getContent());
        if (tvParent.getText().toString().length() <= 0) {
            tvParent.setVisibility(GONE);
        }

        //添加parent
        mLlRoot.addView(tvParent);
        setTitleParams(tvParent);

        //初始化适配器
        List<FilterChildBean> childBeans = parentBean.getChildBeans();
        TagLayoutAdapter adapter = new TagLayoutAdapter(childBeans);
        TagFlowLayout tagFlowLayout = new TagFlowLayout(getContext());
        tagFlowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tagFlowLayout.setMaxSelectCount(getMaxSelectCount());
        //添加子tagFlowLayout
        mLlRoot.addView(tagFlowLayout);
        setTagFlowLayoutLayoutParams(tagFlowLayout);
        tagFlowLayout.setAdapter(adapter);


        //保存所有的tagFlowLayout 跟适配器
        tagFlowLayouts.add(tagFlowLayout);
        mFlowLayoutAdapters.add(adapter);
    }

    /**
     * 可重写
     */
    public View getLineView(Context context, ViewGroup root) {
        return LayoutInflater.from(context).inflate(R.layout.public_layout_filter_line, root);
    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void back(List<FilterParentBean> data);
    }

    public boolean isNeedLine() {
        return true;
    }

    /**
     * 可以重写
     */
    public int getMaxSelectCount() {
        return MAX_SELECT;
    }

    /**
     * 如果需要修改重写
     */
    public void setTitleParams(TextView tvParent) {
        LayoutParams layoutParams = (LayoutParams) tvParent.getLayoutParams();
        layoutParams.topMargin = DensityUtil.dip2px(10);
        layoutParams.leftMargin = DensityUtil.dip2px(18);
        layoutParams.rightMargin = DensityUtil.dip2px(18);
        layoutParams.bottomMargin = DensityUtil.dip2px(10);
        tvParent.setLayoutParams(layoutParams);
    }

    /**
     * 如果需要修改重写
     */
    public void setTagFlowLayoutLayoutParams(TagFlowLayout tagFlowLayout) {
        LayoutParams layoutParams = (LayoutParams) tagFlowLayout.getLayoutParams();
        layoutParams.leftMargin = DensityUtil.dip2px(18);
        layoutParams.rightMargin = DensityUtil.dip2px(18);
        tagFlowLayout.setLayoutParams(layoutParams);
    }

    public void initSelectChild(int[] selectChildIds) {
        resetInner();
        if (selectChildIds == null || selectChildIds.length == 0) {
            return;
        }
        for (int i = 0; i < mFlowLayoutAdapters.size(); i++) {
            trySelectOne(selectChildIds, mFlowLayoutAdapters.get(i));
        }
    }

    private void trySelectOne(int[] selectChildIds, TagLayoutAdapter tagLayoutAdapter) {
        for (int j = 0; j < tagLayoutAdapter.getCount(); j++) {
            for (int selectChildId : selectChildIds) {
                if (tagLayoutAdapter.getItem(j).getId() == selectChildId) {
                    tagLayoutAdapter.setSelectedList(j);
                }
            }
        }
    }

    private int[] getSelectIdsInner() {
        List<FilterParentBean> selectParent = getSelectParent();
        int[] selectIds = new int[selectParent.size()];
        for (int i = 0; i < selectParent.size(); i++) {
            selectIds[i] = selectParent.get(i).getSelectId();
        }
        return selectIds;
    }

    public int[] getSelectIds() {
        return selectIds;
    }
}
