package com.nj.baijiayun.module_download.bean;

import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.downloader.realmbean.DownloadParent;

import java.util.List;
import java.util.Map;

/**
 * @project zywx_android
 * @class name：com.baijiayun.zywx.module_down.bean
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019-06-17 20:00
 * @change
 * @time
 * @describe
 */
public class VideoWrapperBean {
    private List<DownloadItem> downloadingItems;
    private Map<DownloadParent, List<DownloadItem>> downloadedItems;

    public List<DownloadItem> getDownloadingItems() {
        return downloadingItems;
    }

    public void setDownloadingItems(List<DownloadItem> downloadingItems) {
        this.downloadingItems = downloadingItems;
    }

    public Map<DownloadParent, List<DownloadItem>> getDownloadedItems() {
        return downloadedItems;
    }

    public void setDownloadedItems(Map<DownloadParent, List<DownloadItem>> downloadedItems) {
        this.downloadedItems = downloadedItems;
    }


    public boolean isEmpty() {
        return downloadingItems.size() == 0 && downloadedItems.size() == 0;
    }
}
