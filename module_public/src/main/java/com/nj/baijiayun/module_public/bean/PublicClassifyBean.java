package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe 静态属性的分类
 */
public class PublicClassifyBean {
    private int id;
    private String name;
    @SerializedName("parent_id")
    private int parentId;

    static class Child {
        private int id;
        private int name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
