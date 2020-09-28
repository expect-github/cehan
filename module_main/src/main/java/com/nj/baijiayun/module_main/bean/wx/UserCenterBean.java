package com.nj.baijiayun.module_main.bean.wx;

/**
 * @author chengang
 * @date 2019-08-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class UserCenterBean {

    /**
     * uid : 31
     * avatar : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019Cln4ghRfIk1564992053.png
     * nickname : 156****9207
     * courses : 12
     * oto : 1
     * lessons : 1
     */

    private int uid;
    private String avatar;
    private String nickname;
    private int courses;
    private int oto;
    private int lessons;
    private int integral;

    public int getIntegral() {
        return integral;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCourses() {
        return courses;
    }

    public void setCourses(int courses) {
        this.courses = courses;
    }

    public int getOto() {
        return oto;
    }

    public void setOto(int oto) {
        this.oto = oto;
    }

    public int getLessons() {
        return lessons;
    }

    public void setLessons(int lessons) {
        this.lessons = lessons;
    }
}
