package com.nj.baijiayun.module_public.widget.filter_tabs;

/**
 * @author chengang
 * @date 2020/4/9
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.filter_tabs
 * @describe
 */
public interface ITabAction<T> {
     void reset();
     void addData(T data);
}
