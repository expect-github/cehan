package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-11-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class AssembleJoinInfoBean {


    /**
     * user_num : 2
     * join_num : 1
     * start_time : 1573727417
     * end_time : 1573731017
     * group_succ_time : 0
     * group_id : 3
     * spell_id : 21
     * group_leader_id : 1
     * group_leader_nickname : 150****7130
     * group_leader_avatar : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019owZ12LpLsv1571738438.jpg
     * left_num : 1
     */

    @SerializedName("user_num")
    private int userNum;
    @SerializedName("join_num")
    private int joinNum;
    @SerializedName("start_time")
    private long startTime;
    @SerializedName("end_time")
    private long endTime;
    @SerializedName("group_succ_time")
    private long groupSuccTime;
    @SerializedName("group_id")
    private int groupId;
    @SerializedName("spell_id")
    private int spellId;
    @SerializedName("group_leader_id")
    private int groupLeaderId;
    @SerializedName("group_leader_nickname")
    private String groupLeaderNickname;
    @SerializedName("group_leader_avatar")
    private String groupLeaderAvatar;
    @SerializedName("left_num")
    private int leftNum;

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(int joinNum) {
        this.joinNum = joinNum;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getGroupSuccTime() {
        return groupSuccTime;
    }

    public void setGroupSuccTime(long groupSuccTime) {
        this.groupSuccTime = groupSuccTime;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSpellId() {
        return spellId;
    }

    public void setSpellId(int spellId) {
        this.spellId = spellId;
    }

    public int getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(int groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
    }

    public String getGroupLeaderNickname() {
        return groupLeaderNickname;
    }

    public void setGroupLeaderNickname(String groupLeaderNickname) {
        this.groupLeaderNickname = groupLeaderNickname;
    }

    public String getGroupLeaderAvatar() {
        return groupLeaderAvatar;
    }

    public void setGroupLeaderAvatar(String groupLeaderAvatar) {
        this.groupLeaderAvatar = groupLeaderAvatar;
    }

    public int getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(int leftNum) {
        this.leftNum = leftNum;
    }
}
