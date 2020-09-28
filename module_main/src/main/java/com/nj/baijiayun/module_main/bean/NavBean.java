package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2020-01-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class NavBean {


    /**
     * name : 好的
     * nav_img : www.baidu.com
     * url : www
     */

    private String name;
    @SerializedName("nav_img")
    private String navImg;
    @SerializedName("url")
    private String router;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNavImg() {
        return navImg;
    }

    public void setNavImg(String navImg) {
        this.navImg = navImg;
    }

    public String getRouter() {
        return router;
    }


    public String changeUrl(String str) {
        char[] result = new char[str.length() * 2];
        int index = 0;
        result[index++] = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) > 64 && str.charAt(i) < 91) {
                result[index++] = '-';
            }
            result[index++] = str.charAt(i);
        }
        return String.valueOf(result);
    }


}
