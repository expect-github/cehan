package com.nj.baijiayun.module_public.helper;

import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;

/**
 * @author chengang
 * @date 2020/4/13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class ViewSplitLineHelper {
    public static void setLineVisible(BaseMultipleTypeViewHolder baseMultipleTypeViewHolder, int pos) {
        if(baseMultipleTypeViewHolder.getView(R.id.view_list_split_line)!=null) {
            baseMultipleTypeViewHolder.setVisible(R.id.view_list_split_line, pos > 0
                    && (baseMultipleTypeViewHolder.getAdapter().getItem(pos - 1).getClass()
                    .equals(baseMultipleTypeViewHolder.getAdapter().getItem(pos).getClass())));
        }
    }
}
