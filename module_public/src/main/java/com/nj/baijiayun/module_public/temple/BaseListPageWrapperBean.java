package com.nj.baijiayun.module_public.temple;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.internal.Keep;

/**
 * @author chengang
 * @date 2019-09-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */
@Keep
public class BaseListPageWrapperBean<T> {
    @SerializedName("total")
    private int total;
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("list")
    private List<T>list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
