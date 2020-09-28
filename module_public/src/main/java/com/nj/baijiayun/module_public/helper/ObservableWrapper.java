package com.nj.baijiayun.module_public.helper;

import java.util.Arrays;
import java.util.List;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/12/3 下午3:41
 * @change
 * @time
 * @describe
 */
public class ObservableWrapper<T> {
    private T content;
    private List<String> changeList;

    public ObservableWrapper(){}

    public ObservableWrapper(T item, String... changeList) {
        this.content = item;
        this.changeList = Arrays.asList(changeList);
    }

    public ObservableWrapper(T item, List<String> changeList) {
        this.content = item;
        this.changeList = changeList;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public List<String> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<String> changeList) {
        this.changeList = changeList;
    }
}
