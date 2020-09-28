package com.nj.baijiayun.module_public.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author chengang
 * @date 2019-07-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class ClassifyBean implements Parcelable {
    /**
     * id : 0
     * value : 0
     * label : 全部
     * parent_id : 0
     * title : SZS课程分类1
     * children : [{"id":3,"parent_id":1,"title":"数学","value":3,"label":"数学"}]
     */

    private int id;
    private int value;
    private String label;
    private int parent_id;
    private String title;
    private List<ChildrenBean> children;

    protected ClassifyBean(Parcel in) {
        id = in.readInt();
        value = in.readInt();
        label = in.readString();
        parent_id = in.readInt();
        title = in.readString();
        children = in.createTypedArrayList(ChildrenBean.CREATOR);
    }

    public static final Creator<ClassifyBean> CREATOR = new Creator<ClassifyBean>() {
        @Override
        public ClassifyBean createFromParcel(Parcel in) {
            return new ClassifyBean(in);
        }

        @Override
        public ClassifyBean[] newArray(int size) {
            return new ClassifyBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(value);
        dest.writeString(label);
        dest.writeInt(parent_id);
        dest.writeString(title);
        dest.writeTypedList(children);
    }

    public static class ChildrenBean implements Parcelable {
        /**
         * id : 3
         * parent_id : 1
         * title : 数学
         * value : 3
         * label : 数学
         */

        private int id;
        private int parent_id;
        private String title;
        private int value;
        private String label;

        protected ChildrenBean(Parcel in) {
            id = in.readInt();
            parent_id = in.readInt();
            title = in.readString();
            value = in.readInt();
            label = in.readString();
        }

        public static final Creator<ChildrenBean> CREATOR = new Creator<ChildrenBean>() {
            @Override
            public ChildrenBean createFromParcel(Parcel in) {
                return new ChildrenBean(in);
            }

            @Override
            public ChildrenBean[] newArray(int size) {
                return new ChildrenBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(parent_id);
            dest.writeString(title);
            dest.writeInt(value);
            dest.writeString(label);
        }
    }
}


