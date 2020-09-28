package com.nj.baijiayun.module_public.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicTeacherBean {

    private int id;
    @SerializedName("teacher_id")
    private int teacherId;
    @SerializedName("teacher_name")
    private String name;
    @SerializedName("teacher_avatar")
    private String teacherAvatar;
    private String avatar;
    private String introduction;
    @SerializedName("level_name")
    private String levelName;
    @SerializedName("is_oto_teacher")
    private int isOtoTeacher;
    private String course_basis_id;
    private List<String> teacher_title;

    public List<String> getTeacher_title() {
        return teacher_title;
    }

    public void setTeacher_title(List<String> teacher_title) {
        this.teacher_title = teacher_title;
    }

    public String getTeacherAvatar() {
        return teacherAvatar;
    }

    public void setTeacherAvatar(String teacherAvatar) {
        this.teacherAvatar = teacherAvatar;
    }

    public String getCourse_basis_id() {
        return course_basis_id;
    }

    public void setCourse_basis_id(String course_basis_id) {
        this.course_basis_id = course_basis_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        if (!TextUtils.isEmpty(avatar)) {
            return avatar;
        }
        return teacherAvatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTeacherId() {
        if (teacherId == 0) {
            return id;
        }
        return teacherId;
    }

    public String getLevelName() {
        if (levelName == null) {
            return "";
        }
        return levelName;
    }

    public boolean isOtoTeacher() {
        return isOtoTeacher == 1;
    }

    public String getIntroduction() {
        return introduction;
    }

    public boolean isCanAppoint() {
        return !TextUtils.isEmpty(levelName);
    }
    public boolean isHasLevel() {
        return !TextUtils.isEmpty(levelName);
    }

}
