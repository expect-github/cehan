package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class DatumBean implements Cloneable {

    /**
     * datum_id : 15
     * course_basis_id : 51
     * course_chapter_id : 127
     * file_name : 1.txt
     */

    @SerializedName("datum_id")
    private int datumId;
    @SerializedName("course_basis_id")
    private int courseBasisId;
    @SerializedName("course_chapter_id")
    private int courseChapterId;
    @SerializedName("file_name")
    private String fileName;

    public int getDatumId() {
        return datumId;
    }

    public void setDatumId(int datumId) {
        this.datumId = datumId;
    }

    public int getCourseBasisId() {
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Object clone() {
        Object obj=null;
        //调用Object类的clone方法，返回一个Object实例
        try {
            obj= super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
