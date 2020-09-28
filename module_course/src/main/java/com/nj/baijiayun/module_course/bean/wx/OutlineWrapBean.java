package com.nj.baijiayun.module_course.bean.wx;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean
 * @describe
 */
public class OutlineWrapBean {
    private List<ChapterBean> chapter;
    private List<SectionBean> periods;


    public List getResult() {
        if (chapter != null) {
            return chapter;
        }
        return periods;
    }

    public List<ChapterBean> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterBean> chapter) {
        this.chapter = chapter;
    }

    public List<SectionBean> getPeriods() {
        return periods;
    }

    public void setPeriods(List<SectionBean> periods) {
        this.periods = periods;
    }
}
