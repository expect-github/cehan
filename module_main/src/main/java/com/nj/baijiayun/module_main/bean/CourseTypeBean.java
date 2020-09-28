package com.nj.baijiayun.module_main.bean;

import com.nj.baijiayun.module_public.bean.PublicCourseTypeBean;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class CourseTypeBean extends PublicCourseTypeBean {

    private boolean isVip() {
        return getId() == 99;
    }

    public int getVip() {
        return isVip() ? 1 : 0;
    }
}
