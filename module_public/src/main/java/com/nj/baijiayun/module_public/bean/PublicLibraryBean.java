package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicLibraryBean {


    /**
     * id : 48
     * name : 新建一哭
     * author : 安静安静
     * file_ext : xlsx
     * instruction : 哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶哈萨诶
     * browse_base : 10
     * browse_num : 61
     */

    private int id;
    private String name;
    private String author;
    @SerializedName("file_ext")
    private String fileExt;
    private String instruction;
    @SerializedName("browse_base")
    private int browseBase;
    @SerializedName("browse_num")
    private int browseNum;
    @SerializedName("is_collected")
    private int isCollected = 0;
    @SerializedName("created_at")
    private long createAt;

    public long getCreateAt() {
        return createAt;
    }

    public boolean isCollect() {
        return isCollected == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getBrowseBase() {
        return browseBase;
    }

    public void setBrowseBase(int browseBase) {
        this.browseBase = browseBase;
    }

    public int getBrowseNum() {
        return browseNum+browseBase;
    }

    public void setBrowseNum(int browseNum) {
        this.browseNum = browseNum;
    }
}
