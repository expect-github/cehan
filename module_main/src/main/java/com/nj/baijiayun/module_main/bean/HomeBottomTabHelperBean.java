package com.nj.baijiayun.module_main.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2020-02-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class HomeBottomTabHelperBean {
    private List<HomeBottomTabBean> list;

    public HomeBottomTabHelperBean(List<HomeBottomTabBean> list) {
        this.list = list;
    }

    public void setList(List<HomeBottomTabBean> list) {
        this.list = list;
    }

    public List<HomeBottomTabBean> getShowTabList() {
        List<HomeBottomTabBean> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isVisible()) {
                    result.add(list.get(i));
                }
            }
        }

        return result.size()>5?result.subList(0,5):result;
    }
}
