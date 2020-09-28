package com.nj.baijiayun.module_main.bean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class PublicOpenListWrapperBean {
    private List<PublicOpenCourseBean>datas;

    public PublicOpenListWrapperBean(List<PublicOpenCourseBean> datas) {
        this.datas = datas;
    }

    public List<PublicOpenCourseBean> getDatas() {
        return datas;
    }
}
