package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe 动态属性的分类
 */
public class PublicAttrClassifyBean  {

    private int id;
    private String name;
    @SerializedName("parent_id")
    private int parentId;
    private List<PublicAttrClassifyBean.Child>child;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Child> getChild() {
        return child;
    }

    public void setChild(List<Child> child) {
        this.child = child;
    }

   public static class Child  {
        private int id;
        private String name;
        private boolean type = false;

       public boolean isType() {
           return type;
       }

       public void setType(boolean type) {
           this.type = type;
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
    }
}
