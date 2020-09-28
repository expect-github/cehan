package com.nj.baijiayun.module_distribution.bean;

import com.nj.baijiayun.module_public.bean.IChannel;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.bean
 * @describe
 */
public class ChannelBean implements IChannel {


    private String title;
    private int type;

    public boolean isBook() {
        return type == 1;
    }

    public boolean isCourse() {
        return type == 0;
    }

    public ChannelBean setBookType() {
        this.type = 1;
        return this;
    }

    public ChannelBean setCourseType() {
        this.type = 0;
        return this;
    }

    @Override
    public String getTitle() {
        return isCourse() ? "课程" : "图书";
    }

    @Override
    public boolean needShowMore() {
        return false;
    }
}
