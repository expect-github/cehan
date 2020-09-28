package com.nj.baijiayun.module_public.helper.videoplay;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baijiayun.constant.VideoDefinition;
import com.nj.baijiayun.basic.permissions.PerMissionsManager;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.request.DownloadRequest;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.manager.VideoUploadRecordManager;

import java.util.ArrayList;
import java.util.List;


/**
 * @project zywx_android
 * @class name：com.baijiayun.zywx.module_course.helper
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2018/12/28 18:14
 * @change
 * @time
 * @describe classType
 * 判断classType
 * if (1 || 3) {
 * 老版大班课 UI
 * }
 * // 默认 2 跟 6 一样用三分屏 UI
 * // 但是如果是老客户升级，需要跟客户确认
 * // 如果客户还希望用老的 UI，2 要和上面 1、3 放一起
 * else if (2 || 6) {
 * 新版三分屏 UI
 * }
 * else if (4) {
 * 专业小班课 UI
 * }
 * else {
 * 报错
 * }
 */
public class VideoPlayHelper {

    public static final String TYPE_ZHIBO = "zhibo";
    private static final String TYPE_RECORD = "shipin";
    private static final String TYPE_PLAY_BACK = "huifang";


    public static void playVideo(BjyTokenWrapperBean bjyTokenBean, int courseType) {
        if (checkEnterNotPass(bjyTokenBean)) {
            return;
        }
        Bundle bundle = new Bundle();

        BjyTokenData bjyTokenData = bjyTokenBean.getTokenData();
        if ("1".equals(bjyTokenData.getType())) {
            //直播
            if (TYPE_ZHIBO.equals(bjyTokenData.getSub_type())) {
                BjyTokenWrapperBean.LiveRoomParams roomParams = bjyTokenBean.getLiveRoomParams();
                if (roomParams != null) {
                    bundle.putString("name", roomParams.getUser_name());
                    bundle.putString("room_id", roomParams.getRoom_id());
                    bundle.putString("avatar", roomParams.getUser_avatar());
                    bundle.putInt("userType", roomParams.getUser_role());
                    bundle.putString("userNum", roomParams.getUser_number());
                    bundle.putInt("group_id", roomParams.getGroup_id());
                    bundle.putString("sign", roomParams.getSign());
                } else {
                    bundle.putString("name", AccountHelper.getInstance().getInfo().getNickname());
                    bundle.putString("code", bjyTokenData.getStudentCode());
                    bundle.putString("avatar", AccountHelper.getInstance().getInfo().getAvatar());
                }
                if (ConstsCouseType.isSmallGroup(courseType)) {
                    bundle.putString("type", "smallCourse");
                } else {
                    bundle.putString("type", "live");
                }
                //回放
            } else if (TYPE_PLAY_BACK.equals(bjyTokenData.getSub_type())) {
                if (bjyTokenBean.getTokenData().isRoomEmpty()) {
                    ToastUtil.shortShow(BaseApp.getInstance(), "暂无回放");
                    return;
                }
                if (bjyTokenData.getCourseChapterId() != 0) {
                    VideoUploadRecordManager.getInstance().savePlayBackItemInfo(String.valueOf(bjyTokenData.getCourseChapterId()), bjyTokenData.getCourseId(), bjyTokenData.getCoursePeriodsId());
                }
                bundle.putString("pb_room_id", bjyTokenData.getRoom_id() + "");
                bundle.putString("pb_room_token", bjyTokenData.getToken());
                bundle.putString("pb_room_session_id", "-1");
                bundle.putString("type", "backplay");
            } else {
                return;
            }
        } else {
            if (bjyTokenData.getCourseChapterId() != 0) {
                VideoUploadRecordManager.getInstance().saveItemInfo(String.valueOf(bjyTokenData.getCourseChapterId()), 0);
            }
            //点播
            if (TYPE_RECORD.equals(bjyTokenData.getSub_type())) {
                bundle.putString("token", bjyTokenData.getToken());
                bundle.putBoolean("isOffline", false);
                bundle.putLong("videoId", Long.parseLong(bjyTokenData.getVideo_id()));
                bundle.putString("type", "video");

            } else {
                return;
            }
        }

        ARouter.getInstance().build(RouterConstant.PAGE_VIDEO_PROXY).with(bundle).navigation();

    }

    private static String lastBjTokenStr = "";

    private static long lastEnterTime;

    private static boolean isFastEnter() {
        long time = System.currentTimeMillis();
        long timeD = time - lastEnterTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastEnterTime = time;
        return false;
    }

    private static boolean checkEnterNotPass(BjyTokenWrapperBean bjyTokenBean) {
        String bjyTokenStr = GsonHelper.getGsonInstance().toJson(bjyTokenBean);
        if (bjyTokenStr == null) {
            ToastUtil.shortShow(BaseApp.getInstance(), "未获取到播放数据");
            return true;
        }
        if (bjyTokenStr.equals(lastBjTokenStr) && isFastEnter()) {
            return true;
        }
        lastBjTokenStr = bjyTokenStr;
        return false;
    }

    public static void download(final Context context, final BjyTokenData bjyTokenData,
                                final IDownloadInfo downloadInfo,
                                final ICourseInfo data) {
        PerMissionsManager.newInstance().getUserPerMissions((Activity) context, is -> {
            if (!is) {
                ToastUtil.shortShow(context, "您需要打开存储权限");
                return;
            }


            DownloadRequest downloadRequest;
            if ("1".equals(bjyTokenData.getType())) {
                if (bjyTokenData.isRoomEmpty()) {
                    ToastUtil.shortShow(BaseApp.getInstance(), "暂无回放");
                    return;
                }

                //直播回放
                if ("huifang".equals(bjyTokenData.getSub_type())) {
                    downloadRequest = DownloadManager.downloadFile(DownloadManager.DownloadType.TYPE_PLAY_BACK);
                    downloadRequest.videoId(Long.parseLong(bjyTokenData.getRoom_id()));

                } else {
                    //showToastMsg("直播无法下载!");
                    return;
                }


            } else {
                if (StringUtils.isEmpty(bjyTokenData.getVideo_id())) {
                    ToastUtil.shortShow(BaseApp.getInstance(), "未获取到下载资源");
                    return;
                }
//                ConstsCouseType.isAudio(data.getCourseType()) ? DownloadManager.DownloadType.TYPE_VIDEO_AUDIO :
                downloadRequest = DownloadManager.downloadFile(
                        ConstsCouseType.isAudio(data.getCourseType()) ? DownloadManager.DownloadType.TYPE_VIDEO_AUDIO : DownloadManager.DownloadType.TYPE_VIDEO);
                downloadRequest.videoId(Long.parseLong(bjyTokenData.getRoom_id()));
                //点播
            }

            downloadRequest
                    .setVideoDefinitions(videoDefinition)
                    .parentType(data.getCourseType())
                    .parentName(data.getCourseName())
                    .parentCover(data.getCourseCover())
                    .parentId(data.getCourseId())
                    .chapterName(downloadInfo.getChapterName())
                    .chapterId(downloadInfo.getChapterId())
                    .itemId(downloadInfo.getSectionId())
                    .fileName(downloadInfo.getSectionNmae())
                    .token(bjyTokenData.getToken());
            downloadRequest.start();

        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private static List<VideoDefinition> videoDefinition = new ArrayList<>();

    static {
        videoDefinition.add(VideoDefinition._1080P);
        videoDefinition.add(VideoDefinition._720P);
        videoDefinition.add(VideoDefinition.SHD);
        videoDefinition.add(VideoDefinition.HD);
        videoDefinition.add(VideoDefinition.SD);
        videoDefinition.add(VideoDefinition.Audio);
    }


    public static void wrapperBjyTokenBean(BjyTokenData bjyTokenData, int courseId, int courseChapterId) {
        bjyTokenData.setCourseId(courseId);
        bjyTokenData.setCourseChapterId(courseChapterId);
    }

    private static void wrapperBundle(BjyTokenData bjyTokenData, Bundle bundle) {
        bundle.putInt("courseId", bjyTokenData.getCourseId());
        bundle.putInt("courseChapterId", bjyTokenData.getCourseChapterId());
        bundle.putInt("coursePeriodsId", bjyTokenData.getCoursePeriodsId());
    }

}
