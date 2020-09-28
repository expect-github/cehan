package com.nj.baijiayun.sdk_player.ui;

import android.os.Bundle;

import com.baijiahulian.common.utils.StringUtils;
import com.baijiayun.videoplayer.IBJYVideoPlayer;
import com.baijiayun.videoplayer.event.BundlePool;
import com.baijiayun.videoplayer.event.EventKey;
import com.baijiayun.videoplayer.event.OnPlayerEventListener;
import com.baijiayun.videoplayer.player.PlayerStatus;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.nj.baijiayun.sdk_player.R;
import com.nj.baijiayun.sdk_player.manager.BjyVideoPlayManager;
import com.nj.baijiayun.sdk_player.manager.VideoHelper;
import com.nj.baijiayun.sdk_player.widget.NjVideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author chengang
 * 这个页面仅用于
 */
public class FullScreenVideoPlayActivity extends AppCompatActivity {

    private NjVideoView mPlvVideo;
    private String videoPath;
    private String token;
    private long videoId;
    /**
     * 普通链接
     */
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_can_back_play);
        initParams();
        initVideoView();
        processBySource();
        changeSwitchScreenUi();


    }

    @Override
    public void onBackPressed() {
        closePage();
    }


    private void initParams() {
        videoPath = getIntent().getStringExtra("videoPath");
        token = getIntent().getStringExtra("token");
        videoId = getIntent().getLongExtra("videoId", 0);
        videoUrl = getIntent().getStringExtra("videoUrl");
    }

    private void initVideoView() {

        mPlvVideo = findViewById(R.id.plv_video);
        registerListener();
        mPlvVideo.initPlayer(BjyVideoPlayManager.getMediaPlayer(this));
        VideoHelper.resetRenderTypeTexture(mPlvVideo);
        //隐藏标题，由于sdk内部部分情况保存标题设置有问题，除非自己发消息去设置controlComponent
//        mPlvVideo.getComponentContainer().dispatchCustomEvent(ExtraUIEventKey.CUSTOM_CODE_HIDE_TITLE_TXT, null);

    }

    private void processBySource() {

        if (isPlayLocalPathVideo()) {
            BjyVideoPlayManager.getMediaPlayer(this).setupLocalVideoWithFilePath(videoPath);
        } else if (isPlayOnlineVideo()) {
            BjyVideoPlayManager.getMediaPlayer(this).setupOnlineVideoWithId(videoId, token);
        } else if (isPlayRemoteVideoUrl()) {
            mPlvVideo.setUpOnlineVideoUrl(videoUrl, "");
        } else {
            IBJYVideoPlayer mediaPlayer = BjyVideoPlayManager.getMediaPlayer(this);
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.play();
            } else {
                mediaPlayer.pause();
                mediaPlayer.play();
            }
            mPlvVideo.updateVideoSize(VideoHelper.getCurrentPlayVideoInfo());
            //模拟事件  初始化状态 由于这里的播放并没有 init 会导致ui出问题，所以模拟init  的ui
            Bundle bundle = BundlePool.obtain(PlayerStatus.STATE_INITIALIZED);
            mPlvVideo.getComponentContainer().dispatchPlayEvent(OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE, bundle);
            mPlvVideo.getComponentContainer().dispatchPlayEvent(OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE, BundlePool.obtain(PlayerStatus.STATE_STARTED));

        }

    }

    private void changeSwitchScreenUi() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(EventKey.BOOL_DATA, true);
        mPlvVideo.getComponentContainer().dispatchCustomEvent(UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN, bundle);
    }


    private void registerListener() {
        mPlvVideo.setComponentEventListener((eventCode, bundle) -> {
            if (eventCode == UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN) {
                closePage();
            } else if (eventCode == UIEventKey.CUSTOM_CODE_REQUEST_BACK) {
                closePage();
            }

        });
    }

    private void closePage() {

//        setRequestedOrientation(
//                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //除了小窗口或者 非传源进来的 返回的时候都需要关闭
        if (mPlvVideo != null && mPlvVideo.getPlayer() != null) {
            if ((isPlayLocalPathVideo() || isPlayOnlineVideo() || isPlayRemoteVideoUrl())) {
                mPlvVideo.getPlayer().pause();
                BjyVideoPlayManager.releaseMedia();
            }
        }
        finish();
    }

    public boolean isPlayRemoteVideoUrl() {
        return videoUrl != null && videoUrl.length() > 0;
    }

    public boolean isPlayOnlineVideo() {
        return videoId != 0 && token != null && token.length() > 0;
    }

    public boolean isPlayLocalPathVideo() {
        return !StringUtils.isEmpty(videoPath);
    }

}

