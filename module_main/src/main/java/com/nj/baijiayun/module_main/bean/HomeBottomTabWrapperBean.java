package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2020-02-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class HomeBottomTabWrapperBean {
    private List<HomeBottomTabBean> index;

    private List<HomeBottomTabWrapperBean.Style> style;


    public List<HomeBottomTabBean> getTabs() {
        boolean noNeedShowTitle = isNoTitleStyle();
        if (index == null) {
            return new ArrayList<>();
        }
        for (int i = 0; i < index.size(); i++) {
            index.get(i).setNoNeedShowTitle(noNeedShowTitle);
        }
        return index;
    }

    //    boolean isNoTitleStyle;

    private boolean isNoTitleStyle() {
        if (style != null && style.size() > 0) {
            return style.get(0).check == 1;
        }
        return false;
    }


    /**
     * id : 1
     * name : 样式一
     * check : 1
     * style_img : https://testwx41.baijiayun.com/nav/style_1.png
     */

    public static class Style {
        private int id;
        private String name;
        private int check;
        @SerializedName("style_img")
        private String styleImg;


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

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getStyleImg() {
            return styleImg;
        }

        public void setStyleImg(String styleImg) {
            this.styleImg = styleImg;
        }
    }
}
