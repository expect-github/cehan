package com.nj.baijiayun.module_public.widget.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_public.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.filter
 * @describe
 */
public class TagLayoutAdapter extends TagAdapter<FilterChildBean> {
    public TagLayoutAdapter(List<FilterChildBean> datas) {
        super(datas);
    }
//    TagLayoutAdapter(List<FilterChildBean> data) {
//        super(data);
//    }
    //这里getView

    @Override
    public View getView(FlowLayout parent, int position, FilterChildBean filterChildBean) {
        if (filterChildBean.isCheckBox()) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(parent.getContext()).inflate(R.layout.public_layout_filter_child_checkbox, parent, false);
            checkBox.setText(filterChildBean.getContent());
            setTagLayoutChildMiniWidth(checkBox);
            return checkBox;
        }

        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.common_filter_child, parent, false);
        textView.setText(filterChildBean.getContent());
        //这里默认按照设计图4个算的，左右15margin 这个比较合适 感觉不需要改动
        setTagLayoutChildMiniWidth(textView);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();

        if (position % 4 == 3) {
            layoutParams.rightMargin = 0;
        } else {
            layoutParams.rightMargin = DensityUtil.dip2px(6);
        }
        textView.setLayoutParams(layoutParams);




        return textView;
    }

    private void setTagLayoutChildMiniWidth(TextView textView) {
        textView.setWidth((DensityUtil.getScreenWidth() - DensityUtil.dip2px(18 * 2 + 6 * 3)) / 4);
    }


}
