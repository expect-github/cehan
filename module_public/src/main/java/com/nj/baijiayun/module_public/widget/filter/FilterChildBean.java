package com.nj.baijiayun.module_public.widget.filter;

/**
 * @author chengang
 * @date 2019-07-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.filter
 * @describe
 */
public class FilterChildBean {
    private int id;
    private String content;
    private boolean isCheckBox;

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

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
}
