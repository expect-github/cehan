package com.nj.baijiayun.module_public.manager;

import android.annotation.SuppressLint;
import android.os.Message;

import com.baijiayun.videoplayer.IBJYVideoPlayer;
import com.baijiayun.videoplayer.player.PlayerStatus;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.api.PublicService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2020-01-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.manager
 * @describe
 */
public class VideoUploadRecordManager implements IVideoUploadRecord {
    private static final int POLLING_DURATION = 5000;
    private static final int POLLING_WHAT = 1;
    private static volatile VideoUploadRecordManager singleton = null;
    private Map<String, VideoUploadItemBean> uploadItemBeanMap;
    private android.os.Handler handler;
    //这里的currentItemId 是章节id ,是章跟节的映射id
    private String currentItemId;
    private IBJYVideoPlayer ibjyVideoPlayer;
    private long lastPauseTime;

    private VideoUploadRecordManager() {
        uploadItemBeanMap = new HashMap<>();
    }

    public static VideoUploadRecordManager getInstance() {
        if (singleton == null) {
            synchronized (VideoUploadRecordManager.class) {
                if (singleton == null) {
                    singleton = new VideoUploadRecordManager();
                }
            }
        }
        return singleton;
    }


    @Override
    public void uploadCurrentRecord() {
        int duration = 0, cur = 0;
        VideoUploadItemBean videoUploadItem = getVideoUploadItem(currentItemId);
        if (ibjyVideoPlayer != null) {
            duration = ibjyVideoPlayer.getDuration();
            cur = ibjyVideoPlayer.getCurrentPosition();
        }
        if (!checkNeedUpload(currentItemId, cur)) {
            return;
        }
        videoUploadItem.setPlayCur(Math.max(getVideoUploadItem(currentItemId).getPlayCur(), cur));
        videoUploadItem.setMax(Math.max(getVideoUploadItem(currentItemId).getMax(), duration));
        upload(videoUploadItem);
    }

    @Override
    public void saveItemInfo(String itemId, int pgr) {
        currentItemId = itemId;
        getVideoUploadItem(itemId).setPgr(pgr);
    }

    @Override
    public void savePlayBackItemInfo(String itemId, int courseId, int periodId) {
        currentItemId = itemId;
        getVideoUploadItem(itemId).setCourseId(courseId);
        getVideoUploadItem(itemId).setPeriodId(periodId);
        getVideoUploadItem(itemId).setPlayBack();
    }


    @Override
    public VideoUploadItemBean getVideoUploadItem(String itemId) {
        VideoUploadItemBean videoUploadItemBean = uploadItemBeanMap.get(itemId);
        if (videoUploadItemBean == null) {
            videoUploadItemBean = new VideoUploadItemBean(itemId);
            uploadItemBeanMap.put(itemId, videoUploadItemBean);
        }
        return videoUploadItemBean;
    }

    @Override
    public boolean checkNeedUpload(String itemId, int cur) {
        //不需要的情况 取反
        return !(getVideoUploadItem(itemId).isPrgEnd() || cur <= getVideoUploadItem(itemId).getHasUploadedCur() || cur == 0);
    }


    @SuppressLint("HandlerLeak")
    @Override
    public void startPollingUpload() {
        if (handler == null) {
            handler = new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == POLLING_WHAT) {
                        uploadCurrentRecordByPolling();
                    }
                    sendStartPollingMsg();
                }
            };
        }
        sendStartPollingMsg();
    }

    @Override
    public void stopPollingUpload() {
        if (handler != null) {
            handler.removeMessages(POLLING_WHAT);
        }
    }

    @Override
    public void uploadCurrentRecordByPolling() {
        if (ibjyVideoPlayer == null) {
            return;
        }
        if (ibjyVideoPlayer.isPlaying()) {
            Logger.i("uploadProgress by polling");
            uploadCurrentRecord();
        }
    }

    @Override
    public void setPlayer(IBJYVideoPlayer player) {
        if (player == this.ibjyVideoPlayer || player == null) {
            return;
        }
        stopPollingUpload();
        this.ibjyVideoPlayer = player;
        this.ibjyVideoPlayer.addOnPlayerStatusChangeListener(playerStatus -> {
            Logger.i("VideoUploadPlayStatus" + playerStatus + "---" + ibjyVideoPlayer.getCurrentPosition() + "/" + ibjyVideoPlayer.getDuration());
            if (playerStatus == PlayerStatus.STATE_PAUSED) {
                lastPauseTime = System.currentTimeMillis();
                Logger.i("uploadProgress by pause");
                uploadRecordAndResetPolling();
            } else if (playerStatus == PlayerStatus.STATE_STOPPED) {
                if (System.currentTimeMillis() - lastPauseTime < 500) {
                    return;
                }
                Logger.i("uploadProgress by stop");
                uploadRecordAndResetPolling();
            } else if (playerStatus == PlayerStatus.STATE_STARTED) {
                startPollingUpload();
            }
        });

        this.ibjyVideoPlayer.addOnPlayingTimeChangeListener((cur, dur) -> {
            getVideoUploadItem(currentItemId).setPlayCur(cur);
            getVideoUploadItem(currentItemId).setMax(dur);
            if (cur == dur) {
                Logger.i("uploadProgress by complete");
                uploadRecordAndResetPolling();

            }
        });
    }

    private void uploadRecordAndResetPolling() {
        stopPollingUpload();
        uploadCurrentRecord();
    }

    @Override
    public void updatePgrFromServer() {
        final String itemId = currentItemId;
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getVideoRecord(itemId)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<BaseResponse<Integer>>() {
                    @Override
                    public void onSuccess(BaseResponse<Integer> baseResponse) {
                        getVideoUploadItem(itemId).setHasUploadedCur(
                                Math.max(baseResponse.getData(), getVideoUploadItem(itemId).getHasUploadedCur())
                        );
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }

    private void sendStartPollingMsg() {
        handler.sendEmptyMessageDelayed(POLLING_WHAT, POLLING_DURATION);
    }


    private void upload(VideoUploadItemBean bean) {

        final int playCur = bean.getPlayCur();
        PublicService publicService = NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class);
        Observable<BaseResponse> baseResponseObservable = bean.isPlayBack() ?
                publicService.uploadPlayBack(bean.getCourseId(), bean.getPeriodId(), bean.getMax(), bean.getPlayCur()) :
                publicService.uploadVideoRecord(bean.getItemId(), bean.getPlayCur());
        baseResponseObservable
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        bean.setHasUploadedCur(Math.max(playCur, bean.getHasUploadedCur()));
                        bean.setPgr(bean.getHasUploadedCur() * 100 / bean.getMax());

                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });

    }


}
