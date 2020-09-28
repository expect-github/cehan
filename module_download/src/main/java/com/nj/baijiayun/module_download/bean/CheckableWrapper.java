package com.nj.baijiayun.module_download.bean;

import androidx.annotation.NonNull;

public class CheckableWrapper<T> {
    private boolean isChecked;
    private T item;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }



    @NonNull
    public static <T> CheckableWrapper<T> wrapper(T item) {
        CheckableWrapper<T> wrapper = new CheckableWrapper<>();
        wrapper.setChecked(false);
        wrapper.setItem(item);
        return wrapper;
    }
}
