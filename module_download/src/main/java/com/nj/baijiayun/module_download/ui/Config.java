package com.nj.baijiayun.module_download.ui;

import com.nj.baijiayun.downloader.DownloadManager;

/**
 * @author chengang
 * @date 2019-08-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_download.ui
 * @describe
 */
public class Config {

    public final static int SHOW_TYPE_COURSE=1;
    public final static int SHOW_TYPE_LIBRARY=2;


    public final static boolean ALL_TYPE=true;
    public final static DownloadManager.DownloadType[] DOWNLOAD_SHOW_TYPE=new DownloadManager.DownloadType[]{
            DownloadManager.DownloadType.TYPE_PLAY_BACK,
            DownloadManager.DownloadType.TYPE_VIDEO,
    };



    public final static DownloadManager.DownloadType[] COURSE_RESOURCE_TYPE =new DownloadManager.DownloadType[]{
            DownloadManager.DownloadType.TYPE_PLAY_BACK,
            DownloadManager.DownloadType.TYPE_VIDEO,
    };




//    public final static boolean TYPE=true;
}
