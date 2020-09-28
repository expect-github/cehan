package com.nj.baijiayun.module_public.manager;

import com.baijiayun.videoplayer.IBJYVideoPlayer;

/**
 * @author chengang
 * @date 2020-01-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.manager
 * @describe
 */
public interface IVideoUploadRecord {


    void uploadCurrentRecord();

    void saveItemInfo(String itemId, int pgr);

    void savePlayBackItemInfo(String itemId,int courseId,int periodId);

    VideoUploadItemBean getVideoUploadItem(String itemId);

    boolean checkNeedUpload(String itemId, int cur);

    void startPollingUpload();

    void stopPollingUpload();

    void uploadCurrentRecordByPolling();

    void setPlayer(IBJYVideoPlayer player);

    void updatePgrFromServer();
}
