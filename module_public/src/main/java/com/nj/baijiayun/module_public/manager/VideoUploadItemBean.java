package com.nj.baijiayun.module_public.manager;

/**
 * @author chengang
 * @date 2020-01-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.manager
 * @describe 这个可以不keep
 */
public class VideoUploadItemBean {
    private String itemId;
    private int max;
    private int hasUploadedCur;
    private int pgr;
    private int playCur;
    private boolean isPlayBack;
    private int courseId;
    private int periodId;
    public VideoUploadItemBean(String itemId)
    {
        this.itemId=itemId;
    }

    public int getPlayCur() {
        return playCur;
    }

    public void setPlayCur(int playCur) {
        this.playCur = playCur;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getHasUploadedCur() {
        return hasUploadedCur;
    }

    public void setHasUploadedCur(int hasUploadedCur) {
        this.hasUploadedCur = hasUploadedCur;
    }

    public int getPgr() {
        return pgr;
    }

    public void setPgr(int pgr) {
        this.pgr = pgr;
    }

    public boolean isPrgEnd() {
        return pgr == 100;
    }


    public void setPlayBack() {
        this.isPlayBack = true;
    }

    public boolean isPlayBack() {
        return isPlayBack;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }
}
