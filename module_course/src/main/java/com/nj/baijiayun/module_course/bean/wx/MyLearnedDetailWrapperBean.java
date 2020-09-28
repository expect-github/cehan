package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.bean.ICourseStudy;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe 我的学习详情返回的 api/app/myStudy/course/{course_id}
 */
public class MyLearnedDetailWrapperBean {

    private List<ChapterBean> chapters;
    private List<ChapterBean> chapter;
    private List<SectionBean> periods;
    private MyLearnedDetailWrapperBean.Course course;
    private int lastStudyChapterId;
    @SerializedName("room_id")
    private int roomId;


    public List getResult() {
        if (chapters != null) {
            return chapters;
        }
        if (chapter != null) {
            return chapter;
        }
        return periods;
    }

    public List<ChapterBean> getChapter() {
        return chapters;
    }

    public void setChapter(List<ChapterBean> chapter) {
        this.chapters = chapter;
    }

    public List<SectionBean> getPeriods() {
        return periods;
    }

    public void setPeriods(List<SectionBean> periods) {
        this.periods = periods;
    }

    public Course getCourse() {
        if (course == null) {
            course = new Course();
        }
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getLastStudyChapterId() {
        return lastStudyChapterId;
    }

    public void setLastStudyChapterId(int lastStudyChapterId) {
        this.lastStudyChapterId = lastStudyChapterId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public static class Course implements ICourseStudy {

        /**
         * course_id : 34
         * course_type : 2
         * title : 大班课001
         * teacher_name : 张一疯,关键技术,WMT001
         */

        @SerializedName("course_id")
        private int courseId;
        @SerializedName("course_type")
        private int courseType;
        private String title;
        @SerializedName("teacher_name")
        private String teacherName;
        @SerializedName("comment_content")
        private String commentContent;
        @SerializedName("comment_grade")
        private int grade;
        @SerializedName("has_homework")
        private int hasHomeWork;
        @SerializedName("limit")
        private int limit;
        @SerializedName("is_go_to_study")
        private int isCanStudyUndercarriage;
        @SerializedName("status")
        private int carriageStatus;
        @SerializedName("my_study_status")
        private int myStudyStatus;

        public boolean hasHomeWork() {
            return hasHomeWork == 1;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        /**
         * section_num : 0
         * is_comment : 0
         * progress_rate : 0
         */

        @SerializedName("section_num")
        private int sectionNum;
        @SerializedName("is_comment")
        private int isComment;
        @SerializedName("progress_rate")
        private int progressRate;
        @SerializedName("is_buy_order")
        private int isBuyOrder;

        public boolean isFromOrder() {
            return isBuyOrder == 1;
        }


        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getSectionNum() {
            return sectionNum;
        }

        public void setSectionNum(int sectionNum) {
            this.sectionNum = sectionNum;
        }

        public int getIsComment() {
            return isComment;
        }

        public void setIsComment(int isComment) {
            this.isComment = isComment;
        }

        public int getProgressRate() {
            return progressRate;
        }

        public void setProgressRate(int progressRate) {
            this.progressRate = progressRate;
        }

        public boolean isCommented() {
            return 1 == isComment;
        }

        public void setCommentSuccess() {
            isComment = 1;
        }

        public boolean isLimit() {
            return 1 == limit;
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

        public boolean isHide() {
            return myStudyStatus == 2;
        }

        public void setHide(boolean isHide) {
            myStudyStatus = isHide ? 2 : 1;
        }
    }
}
