package com.nj.baijiayun.module_public.helper.videoplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baijiayun.groupclassui.InteractiveClassUI;
import com.baijiayun.live.ui.LiveSDKWithUI;
import com.baijiayun.livecore.LiveSDK;
import com.baijiayun.livecore.context.LPConstants;
import com.baijiayun.videoplayer.ui.activity.PBRoomActivity;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.sdk_player.manager.BjyVideoPlayManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * @project zywx_android
 * @class name：com.baijiayun.zywx.sdk_bjy.activity
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/03/26 20:58
 * @change
 * @time
 * @describe
 */
@Route(path = RouterConstant.PAGE_VIDEO_PROXY)
public class VideoProxyActivity extends AppCompatActivity {
    private static final String TYPE_LIVE_PLAY = "live";
    private static final String TYPE_BACK_PLAY = "backplay";
    private static final String TYPE_VIDEO_PLAY = "video";
    private static final String TYPE_SMALL_COURSE = "smallCourse";
    private int onResumeCount = 0;

    public static void init(String customDomain) {
        LiveSDK.customEnvironmentPrefix = customDomain;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("VideoProxyActivity", "VideoProxyActivity onCreate");
        //尝试关闭悬浮
        BjyVideoPlayManager.releaseMedia();;
        jump();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeCount++;
        if (onResumeCount > 1) {
            finish();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        jump();
    }

    private void jump() {
        String type = getIntent().getStringExtra("type");
        switch (type) {
            case TYPE_LIVE_PLAY:
                startLiveRoomActivity(getIntent());
                break;
            case TYPE_BACK_PLAY:
                startBackPlayActivity(getIntent());
                break;
            case TYPE_VIDEO_PLAY:
                startVideoPlayActivity(getIntent());
                break;
            case TYPE_SMALL_COURSE:
                startSmallGroupActivity(getIntent());
                break;
            default:
                break;
        }
    }

    /**
     * videoPath 本地文件资源播放
     * token videoId  在线播放
     * videoUrl 非百家云连接播放
     *
     * @param intent i
     */
    private void startVideoPlayActivity(Intent intent) {
        intent.setClass(this, VideoPlayWrapperActivity.class);
        startActivity(intent);
    }

    private void startBackPlayActivity(Intent intent) {
        intent.setClass(this, PBRoomActivity.class);
        startActivity(intent);
    }

    private void startLiveRoomActivity(Intent intent) {
        String roomId = intent.getStringExtra("room_id");
        String name = intent.getStringExtra("name");
        String avatar = intent.getStringExtra("avatar");
        if (StringUtils.isEmpty(roomId)) {
            Toast.makeText(this, "没有房间号", Toast.LENGTH_SHORT).show();

            String code = intent.getStringExtra("code");
            LiveSDKWithUI.enterRoom(this, code, name, avatar, msg -> {
                Logger.e(msg);
                ToastUtil.shortShow(VideoProxyActivity.this, msg);
            });
        } else {
            int userType = intent.getIntExtra("userType", 0);
            String sing = intent.getStringExtra("sign");
            String userNum = intent.getStringExtra("userNum");
            int groupId = intent.getIntExtra("group_id", -1);
            LiveSDKWithUI.LiveRoomUserModel liveRoomUserModel = new LiveSDKWithUI.LiveRoomUserModel(name, avatar, userNum, LPConstants.LPUserType.from(userType), groupId);
            LiveSDKWithUI.enterRoom(this, Long.parseLong(roomId), sing, liveRoomUserModel, msg -> {
                Logger.e(msg);
                ToastUtil.shortShow(VideoProxyActivity.this, msg);

            });
        }
    }

    private void startSmallGroupActivity(Intent intent) {
        String roomId = intent.getStringExtra("room_id");
        String name = intent.getStringExtra("name");
        String avatar = intent.getStringExtra("avatar");
        if (StringUtils.isEmpty(roomId)) {
            Toast.makeText(this, "没有房间号", Toast.LENGTH_SHORT).show();

            String code = intent.getStringExtra("code");
            InteractiveClassUI.enterRoom(this, code, name, avatar, msg -> {
                Logger.e(msg);
                ToastUtil.shortShow(VideoProxyActivity.this, msg);
            });
        } else {
            int userType = intent.getIntExtra("userType", 0);
            String sing = intent.getStringExtra("sign");
            String userNum = intent.getStringExtra("userNum");
            int groupId = intent.getIntExtra("group_id", -1);
            InteractiveClassUI.LiveRoomUserModel userModel = new InteractiveClassUI.LiveRoomUserModel(name, avatar, userNum, LPConstants.LPUserType.from(userType));
            InteractiveClassUI.enterRoom(this, Long.parseLong(roomId), sing, userModel, msg -> {
                Logger.e(msg);
                ToastUtil.shortShow(VideoProxyActivity.this, msg);
            });

        }
    }

}
