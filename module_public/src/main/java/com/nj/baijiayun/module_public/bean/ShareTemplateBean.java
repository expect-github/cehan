package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.widget.banner.IBannerImage;

/**
 * @author chengang
 * @date 2019-09-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class ShareTemplateBean implements IBannerImage {

    /**
     * id : 1
     * sort : 1
     * img : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019WgjQGrEpZo1567576465.jpg
     * created_at : 1567758317
     * creator_name : admin
     */

    private int id;
    private int sort;
    private String img;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("creator_name")
    private String creatorName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String getBannerCover() {
        return img;
    }
}
