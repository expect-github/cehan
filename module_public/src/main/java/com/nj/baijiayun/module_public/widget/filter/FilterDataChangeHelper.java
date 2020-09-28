package com.nj.baijiayun.module_public.widget.filter;

import com.nj.baijiayun.module_public.bean.PublicAttrClassifyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2020/4/9
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.filter
 * @describe
 */
public class FilterDataChangeHelper {

    public static List<FilterParentBean> getFilterParentList(List<PublicAttrClassifyBean> datas) {
        List<FilterParentBean> result = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            FilterParentBean filterParentBean = new FilterParentBean();
            filterParentBean.setContent(datas.get(i).getName());
            List<PublicAttrClassifyBean.Child> child = datas.get(i).getChild();
            if (child == null) {
                continue;
            }
            for (int j = 0; j < child.size(); j++) {
                FilterChildBean filterChildBean = new FilterChildBean();
                filterChildBean.setContent(child.get(j).getName());
                filterChildBean.setId(child.get(j).getId());
                filterParentBean.getChildBeans().add(filterChildBean);
            }
            result.add(filterParentBean);
        }
        return result;
    }
    public static String formatResultListSplit(List<FilterParentBean> datas)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append(datas.get(i).getSelectId());
        }
        return result.toString();
    }
}
