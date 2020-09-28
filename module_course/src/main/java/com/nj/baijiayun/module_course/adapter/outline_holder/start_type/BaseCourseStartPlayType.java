package com.nj.baijiayun.module_course.adapter.outline_holder.start_type;

import com.nj.baijiayun.module_course.R;

/**
 * @author chengang
 * @date 2019-08-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.outline_holder.start_type
 * @describe
 *
 * 1未开始 2进行中(直播中,开课中) 3停播(暂无回放,已结束) 4重播(查看回放)
 *
 */
public class BaseCourseStartPlayType {
    public StartTypeWrapper getShowStrByType(int type) {
        StartTypeWrapper startTypeWrapper;

        switch (type) {
            case 1:
                startTypeWrapper = new StartTypeWrapper(getUnStartColor(), getUnStartStr());
                break;
            case 2:
                startTypeWrapper = new StartTypeWrapper(getInProgressColor(), getInProgressStr());
                break;
            case 3:
                startTypeWrapper = new StartTypeWrapper(getEndColor(), getEndStr());
                break;
            case 4:
                startTypeWrapper = new StartTypeWrapper(getPlayBackColor(), getPlayBackStr());
                break;
            default:
                startTypeWrapper = new StartTypeWrapper(R.color.public_FF8C8C8C, "");
                break;
        }

        return startTypeWrapper;
    }

    public int getUnStartColor() {
        return R.color.public_FF8C8C8C;
    }

    public int getInProgressColor() {
        return R.color.common_main_color;
    }

    public int getPlayBackColor() {
        return R.color.public_orange;
    }

    public int getEndColor() {
        return R.color.public_FF8C8C8C;
    }


    public String getUnStartStr() {
        return "未开始";
    }

    public String getInProgressStr() {
        return "讲课中";
    }

    public String getEndStr() {
        return "无回放";
    }

    public String getPlayBackStr() {
        return "看回放";
    }
}
