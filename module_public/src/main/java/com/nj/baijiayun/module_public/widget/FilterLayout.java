package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.nj.baijiayun.module_common.widget.FilterView;
import com.nj.baijiayun.module_common.widget.dropdownmenu.DropDownView;

import java.util.List;

/**
 * @author chengang
 * @date 2019-06-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class FilterLayout extends DropDownView {
    public FilterLayout(Context context) {
        this(context, null, 0);
    }

    public FilterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private FilterView contentView;

    private void init(Context context) {
        contentView = new FilterView(context);
        setContentView(contentView);
    }

    public void setData(List<FilterView.ParentBean> datas) {
        contentView.setData(datas);
    }

    public FilterView getFilterView() {
        return contentView;
    }

    public void setCallBack(FilterView.CallBack callBack) {
        contentView.setCallBack(new FilterView.CallBack() {
            @Override
            public void back(List<Integer> data) {
                close();
                if (callBack != null) {
                    callBack.back(data);
                }
            }
        });

    }
}
