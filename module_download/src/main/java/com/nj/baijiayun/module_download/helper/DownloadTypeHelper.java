package com.nj.baijiayun.module_download.helper;

import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.module_download.R;
import com.nj.baijiayun.module_download.ui.Config;

/**
 * @project neixun_android
 * @class name：com.nj.baijiayun.module_download.helper
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019-07-01 19:28
 * @change
 * @time
 * @describe
 */
public class DownloadTypeHelper {

    public static int getTypeImg(int DownType) {
        switch (DownType) {
            case DownloadItem.FILE_TYPE_FILE_AUDIO:
            case DownloadItem.FILE_TYPE_VIDEO_AUDIO:
                return R.drawable.download_type_audio;
            case DownloadItem.FILE_TYPE_FILE_VIDEO:
            case DownloadItem.FILE_TYPE_VIDEO:
                return R.drawable.download_type_video;
            case DownloadItem.FILE_TYPE_FILE_COURSE:
            case DownloadItem.FILE_TYPE_LIBRARY:
                return R.drawable.download_type_file;
            default:
                return 0;
        }
    }

    public static DownloadManager.DownloadType[] getDownloadTypeByShowType(int showType) {
        switch (showType) {
            case Config.SHOW_TYPE_COURSE:
                return new DownloadManager.DownloadType[]{
                        DownloadManager.DownloadType.TYPE_PLAY_BACK,
                        DownloadManager.DownloadType.TYPE_VIDEO,
                        DownloadManager.DownloadType.TYPE_VIDEO_AUDIO,
                };
            case Config.SHOW_TYPE_LIBRARY:
                return new DownloadManager.DownloadType[]{
                        DownloadManager.DownloadType.TYPE_LIBRARY};
            default:
                return null;
        }


    }
}
