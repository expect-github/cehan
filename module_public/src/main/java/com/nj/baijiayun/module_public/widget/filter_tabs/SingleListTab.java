package com.nj.baijiayun.module_public.widget.filter_tabs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.widget.tabs.SingleListModel;
import com.nj.baijiayun.module_common.widget.tabs.NoRepeatTab;
import com.nj.baijiayun.module_common.widget.tabs.SingleListView;
import com.nj.baijiayun.module_public.R;

import java.util.List;

/**
 * @author chengang
 * @date 2020/4/9
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget.tabs
 * @describe
 */
public class SingleListTab extends NoRepeatTab implements ITabAction<List<SingleListModel>> {

    private SingleListView singleListView;
    private ItemClickCallBack itemClickCallBack;
    private List<SingleListModel> data;

    public SingleListTab(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View createContentView(Context context) {
        singleListView = new SingleListView(context);
        singleListView.setBackgroundResource(R.drawable.public_bg_selector_top_line_bottom_radius);
        singleListView.setPadding(DensityUtil.dip2px(12), DensityUtil.dip2px(15), DensityUtil.dip2px(12), DensityUtil.dip2px(30));
        singleListView.getAdapter().setOnItemClickListener((holder, position, view, item) -> {
            singleListView.selectPosition(position);
            getTabCloseCallBack().close();
            if (itemClickCallBack != null) {
                itemClickCallBack.call(position, singleListView.getAdapter().getItem(position));
            }
        });
        return singleListView;
    }

    @Override
    public void select(boolean isSelect) {
        super.select(isSelect);
        if (singleListView.isHasSelect()) {
            setTitleColorSelect();
        }
        setTitle(singleListView.getSelectText());

    }

    @Override
    public void viewCreate() {
        setData();
    }

    public void setItemClickCallBack(ItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public interface ItemClickCallBack {
        void call(int position, SingleListModel singleListModel);
    }

    @Override
    public void reset() {
        if (singleListView != null) {
            singleListView.reset();
        }
    }

    @Override
    public void addData(List<SingleListModel> data) {
        this.data = data;
        setData();

    }

    private void setData() {
        if (singleListView != null) {
            singleListView.getAdapter().clear();
            singleListView.getAdapter().addAll(data);
        }
    }

}
