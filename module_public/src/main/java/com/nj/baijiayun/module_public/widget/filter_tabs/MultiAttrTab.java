package com.nj.baijiayun.module_public.widget.filter_tabs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nj.baijiayun.module_common.widget.tabs.NoRepeatTab;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.widget.filter.FilterDataChangeHelper;
import com.nj.baijiayun.module_public.widget.filter.FilterNewView;
import com.nj.baijiayun.module_public.widget.filter.FilterParentBean;

import java.util.List;

/**
 * @author chengang
 * @date 2020/4/9
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.filter_tabs
 * @describe
 */
public class MultiAttrTab extends NoRepeatTab implements ITabAction<List<FilterParentBean>> {
    FilterNewView mAttrView;
    private ClickCallBack clickCallBack;
    private List<FilterParentBean> data;

    public MultiAttrTab(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View createContentView(Context context) {
        mAttrView = new FilterNewView(context);
        mAttrView.setBackgroundResource(R.drawable.public_bg_selector_top_line_bottom_radius);
        mAttrView.setNeedBottomConfirm(true);
        mAttrView.setCallBack(data1 -> {
            getTabCloseCallBack().close();
            if (clickCallBack != null) {
                clickCallBack.call(FilterDataChangeHelper.formatResultListSplit(data1));
            }

        });

        return mAttrView;
    }

    @Override
    public void select(boolean isSelect) {
        super.select(isSelect);
        int[] selectIds = mAttrView.getSelectIds();
        mAttrView.initSelectChild(selectIds);
        if (selectIds != null && selectIds.length > 0) {
            setTitleColorSelect();
        }
    }

    @Override
    public void viewCreate() {
        setData();
    }

    @Override
    public void reset() {
        if(mAttrView!=null) {
            mAttrView.reset();
        }
    }

    @Override
    public void addData(List<FilterParentBean> data) {
        //懒加载的缘故
        this.data = data;
        setData();

    }

    private void setData() {
        if (mAttrView != null && data != null) {
            mAttrView.setData(data);
        }
    }

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void call(String ids);
    }


}
