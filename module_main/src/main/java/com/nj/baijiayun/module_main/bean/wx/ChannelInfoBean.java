package com.nj.baijiayun.module_main.bean.wx;

import com.nj.baijiayun.module_public.bean.IChannel;

/**
 * @author chengang
 * @date 2019-08-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean.wx
 * @describe
 */
public class ChannelInfoBean implements IChannel {

    /**
     * id : 1
     * name : SZS会员课1
     * type : 2
     * sort : 1
     */

    private int id;
    private String name;
    private int type;
    private int sort;


    public boolean isCourseType() {
        return type == 1 || type == 2;
    }

    public boolean isTeacherType() {
        return type == 3 || type == 4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public boolean needShowMore() {
        return true;
    }

    public boolean isPublicOpenCourse() {
        return type == 5;
    }

    public boolean isBookType() {
        return type == 7;
    }

    public boolean isNewsType() {
        return type == 6;
    }



}
