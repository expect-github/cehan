package com.nj.baijiayun.module_common.widget.tabs;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget.dropdownmenu.typeview
 * @describe
 */
public class SingleListModel {
    private int id;
    private String text;
    private boolean isSelect = false;

    public SingleListModel(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public SingleListModel(String text) {
        this.text = text;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
