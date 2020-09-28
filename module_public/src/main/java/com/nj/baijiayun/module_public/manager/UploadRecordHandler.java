package com.nj.baijiayun.module_public.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.baijiayun.videoplayer.IBJYVideoPlayer;
import com.baijiayun.videoplayer.ui.activity.PBRoomActivity;
import com.nj.baijiayun.logger.log.Logger;

import java.lang.reflect.Field;

/**
 * @author chengang
 * @date 2020-03-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.manager
 * @describe
 */
public class UploadRecordHandler implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {


    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity instanceof PBRoomActivity)
        {
            try {
                Field videoPlayerFiled = activity.getClass().getDeclaredField("videoPlayer");
                videoPlayerFiled.setAccessible(true);
                VideoUploadRecordManager.getInstance().setPlayer((IBJYVideoPlayer) videoPlayerFiled.get(activity));
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
