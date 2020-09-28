package com.nj.baijiayun.module_course.adapter.outline_holder.start_type;

/**
 * @author chengang
 * @date 2019-08-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.outline_holder.start_type
 * @describe
 */
public class LiveType extends BaseCourseStartPlayType {

    @Override
    public String getInProgressStr() {
        return "直播中";
    }

    @Override
    public String getEndStr() {
        return "暂无回放";
    }

    @Override
    public String getPlayBackStr() {
        return "回放";
    }





}
