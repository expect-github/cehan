package com.nj.baijiayun.module_course.adapter.outline_holder.start_type;

import com.nj.baijiayun.basic.utils.StringUtils;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-08-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.outline_holder.start_type
 * @describe
 */
public class StartTypeWrapper {
    private int showColor;
    private String showTxt;

    public StartTypeWrapper(int showColor, String showTxt) {
        this.showColor = showColor;
        this.showTxt = showTxt;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(showTxt);
    }


    public int getShowColor() {
        return showColor;
    }

    public void setShowColor(int showColor) {
        this.showColor = showColor;
    }

    public String getShowTxt() {
        if (!isEmpty()) {
            return MessageFormat.format("[{0}]", showTxt);
        }
        return "";
    }

    public void setShowTxt(String showTxt) {
        this.showTxt = showTxt;
    }
}
