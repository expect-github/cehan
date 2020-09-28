package com.nj.baijiayun.module_main.bean;

import com.nj.baijiayun.module_public.bean.PublicTeacherBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class AppointTeacherListWrapperBean {
    private List<PublicTeacherBean>datas;

    public AppointTeacherListWrapperBean(List<PublicTeacherBean> datas) {
        this.datas = datas;
    }

    public List<PublicTeacherBean> getDatas() {
        return datas;
    }
}
