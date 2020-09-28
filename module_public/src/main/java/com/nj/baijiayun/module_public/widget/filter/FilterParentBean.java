package com.nj.baijiayun.module_public.widget.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.filter
 * @describe
 */
public class FilterParentBean {
    private String content;
    private int selectId;
    public List<FilterChildBean> childBeans;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    public List< FilterChildBean> getChildBeans() {
        if(childBeans==null){
            childBeans=new ArrayList<>();
        }
        return childBeans;
    }

    public void setChildBeans(List< FilterChildBean> childBeans) {
        this.childBeans = childBeans;
    }
}
