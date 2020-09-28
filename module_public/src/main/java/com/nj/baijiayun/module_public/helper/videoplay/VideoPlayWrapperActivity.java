package com.nj.baijiayun.module_public.helper.videoplay;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.baijiayun.videoplayer.IBJYVideoPlayer;
import com.baijiayun.videoplayer.ui.activity.VideoPlayActivity;
import com.baijiayun.videoplayer.ui.widget.BJYVideoView;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.manager.VideoUploadRecordManager;
import com.nj.baijiayun.sdk_player.manager.VideoHelper;

import java.lang.reflect.Field;

import androidx.annotation.Nullable;

/**
 * @author chengang
 * @date 2020-01-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.videoplay
 * @describe
 */
public class VideoPlayWrapperActivity extends VideoPlayActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String videoUrl = getIntent().getStringExtra("videoUrl");
        try {
            super.onCreate(savedInstanceState);
        } catch (Exception ee) {
            Logger.d(ee.getMessage());
        }
        //上面使用默认播放器失败的话 因为传入的videoUrl不支持，用这个进行播放
        try {
            if (!TextUtils.isEmpty(videoUrl)) {
                VideoHelper.setUpOnlineVideoUrl(getPlayer(getPlayerView()), videoUrl, "");
            }
        } catch (Exception ee) {
            Logger.d(ee.getMessage());
        }
        //更新进度
        updateRecord();


    }

    public IBJYVideoPlayer getPlayer(BJYVideoView baseVideoView) {
        IBJYVideoPlayer player = null;
        try {
            Class<?> superclass = baseVideoView.getClass().getSuperclass();
            View parent = (View) baseVideoView.getParent();
            parent.setBackgroundColor(Color.BLACK);
            Field playerFiled = superclass.getDeclaredField("bjyVideoPlayer");
            Logger.d("VideoPlayWrapperActivity--->" + playerFiled);
            playerFiled.setAccessible(true);
            player = (IBJYVideoPlayer) playerFiled.get(baseVideoView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return player;
    }

    public BJYVideoView getPlayerView() {
        return findViewById(R.id.bjyvideoview);
    }


    private void updateRecord() {
        try {
            IBJYVideoPlayer player = getPlayer(getPlayerView());
            VideoUploadRecordManager.getInstance().setPlayer(player);
            VideoUploadRecordManager.getInstance().saveItemInfo(String.valueOf(getIntent().getIntExtra("courseChapterId",0)), 0);
            VideoUploadRecordManager.getInstance().updatePgrFromServer();
        } catch (Exception ee) {
            Logger.e(ee.getMessage());
        }

    }


}
