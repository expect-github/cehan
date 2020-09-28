package com.nj.baijiayun.module_course.bean.wx;

import com.nj.baijiayun.refresh.recycleview.ITreeModel;
import com.nj.baijiayun.refresh.recycleview.TreeItemExpandAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class ChapterBean implements ITreeModel {
    private String title;
    private int id;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private List<SectionBean> child;

    private TreeItemExpandAttr abstractTreeItemAttr = new TreeItemExpandAttr(this);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SectionBean> getChild() {
        if (child == null) {
            child = new ArrayList<>();
        }
        return child;
    }

    public void setChild(List<SectionBean> child) {
        this.child = child;
    }

    @Override
    public List<? extends ITreeModel> getChilds() {
        return child;
    }

    @Override
    public TreeItemExpandAttr getTreeItemAttr() {
        return abstractTreeItemAttr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
