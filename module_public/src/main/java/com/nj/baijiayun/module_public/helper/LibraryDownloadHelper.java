package com.nj.baijiayun.module_public.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.nj.baijiayun.basic.permissions.PerMissionsManager;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.request.DownloadRequest;
import com.nj.baijiayun.module_public.helper.videoplay.BjyTokenData;
import com.nj.baijiayun.module_public.helper.videoplay.ICourseInfo;
import com.nj.baijiayun.module_public.helper.videoplay.IDownloadInfo;

/**
 * @author chengang
 * @date 2019-11-08
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class LibraryDownloadHelper {

    public static void download(final Context context, final BjyTokenData bjyTokenData,
                                final IDownloadInfo downloadInfo,
                                final ICourseInfo data) {
        PerMissionsManager.newInstance().getUserPerMissions((Activity) context, is -> {


            DownloadRequest downloadRequest;
            downloadRequest = DownloadManager.downloadFile(DownloadManager.DownloadType.TYPE_LIBRARY);
            downloadRequest
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

    public static void downloadTest(final Context context) {

        //存一个文库的名称 链接 名称
        PerMissionsManager.newInstance().getUserPerMissions((Activity) context, is -> {
            if (!is) {
                ToastUtil.shortShow(context, "您需要打开存储权限");
                return;
            }

            DownloadRequest downloadRequest = DownloadManager.downloadFile(DownloadManager.DownloadType.TYPE_LIBRARY);
            downloadRequest
                    .parentName("测试文库")
                    .parentId("1")
                    .chapterId("1")
                    .itemId("2")
                    .parentType(DownloadManager.DownloadType.TYPE_LIBRARY.value())
                    .fileGenre("xlsx")
                    .url("https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/file/2019AqdnztOV391573092844.xlsx")
                    .fileName("测试文库名称");
            downloadRequest.start();

        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


}
