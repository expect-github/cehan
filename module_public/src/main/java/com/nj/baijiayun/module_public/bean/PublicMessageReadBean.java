package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-08-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicMessageReadBean {


    /**
     * id : 48
     * content : 您报名的课程《系统课》已经有3天没有学习了，快去学习吧
     * is_read : 0
     * created_at : 1564158406
     * message_classify : course
     */

    private int id;
    private String content;
    @SerializedName("is_read")
    private int isRead;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("message_classify")
    private String messageClassify;
    @SerializedName("not_read")
    private int notReadCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsRead() {
        return isRead;
    }

    public boolean isUnRead() {
        return isRead == 0;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessageClassify() {
        return messageClassify;
    }

    public void setMessageClassify(String messageClassify) {
        this.messageClassify = messageClassify;
    }

    public int getNotReadCount() {
        return notReadCount;
    }
}
