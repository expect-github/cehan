package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.bean.ICourseStudy;

/**
 * @author chengang
 * @date 2019-08-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class CalendarCourseBean implements ICourseStudy {
    /**
     * course_basis_id : 34
     * course_chapter_id : 114
     * start_play : 1565020800
     * end_play : 1565107200
     * live_status : 0
     * live_time : 00:00-00:00
     */
    @SerializedName("course_basis_id")
    private int courseBasisId;
    @SerializedName("course_chapter_id")
    private int courseChapterId;
    @SerializedName("start_play")
    private long startPlay;
    @SerializedName("end_play")
    private long endPlay;
    @SerializedName("live_status")
    private int liveStatus;
    @SerializedName("live_time")
    private String liveTime;

    @SerializedName("course_title")
    private String courseTitle;
    @SerializedName("is_go_to_study")
    private int isCanStudyUndercarriage;
    @SerializedName("status")
    private int carriageStatus;

    /**
     * start_play : 1565020800
     * end_play : 1565107200
     * title : 第二节
     * course_type : 2
     */

    private String title;
    @SerializedName("course_type")
    private int courseType;

    @SerializedName("is_play_back")
    private int isPlayBack;

    @SerializedName("oto_desc")
    private String otoDesc;

    /**
     * 节id 服务器端的
     */
    @SerializedName("periods_id")
    private int periodsId;

    public int getPeriodsId() {
        return periodsId;
    }

    private HomeWorkBean homework;

    public HomeWorkBean getHomework() {
        if (homework == null) {
            homework = new HomeWorkBean();
        }
        return homework;
    }

    public String getOtoDesc() {
        return otoDesc;
    }

    public int getCourseId() {
        return courseBasisId;
    }

    public void setCourseBasisId(int courseBasisId) {
        this.courseBasisId = courseBasisId;
    }

    public int getCourseChapterId() {
        return courseChapterId;
    }

    public void setCourseChapterId(int courseChapterId) {
        this.courseChapterId = courseChapterId;
    }

    public long getStartPlay() {
        return startPlay;
    }

    public void setStartPlay(long startPlay) {
        this.startPlay = startPlay;
    }

    public long getEndPlay() {
        return endPlay;
    }

    public void setEndPlay(long endPlay) {
        this.endPlay = endPlay;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public boolean isLiveStatus() {
        return liveStatus == 1;
    }

    public boolean isEnd() {
        return liveStatus == 2;
    }

    public boolean isUnStart() {
        return liveStatus == 0;
    }

    public boolean isHavePlayBack() {
        return isPlayBack == 1;
    }

    public boolean isCanStudy() {
        return CourseHelper.isCanStudy(this);
    }

    @Override
    public int isGoToStudy() {
        return isCanStudyUndercarriage;
    }

    @Override
    public int courseStatus() {
        return carriageStatus;
    }
}
