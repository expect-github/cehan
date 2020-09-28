package com.nj.baijiayun.module_public.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.nj.baijiayun.basic.permissions.PerMissionsManager;
import com.nj.baijiayun.basic.permissions.interfaces.PerMissionCall;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.module_public.R;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-06-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class SelectPhotoHelper {

    private static final String[] PERMISSIONS_WRITE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String[] PERMISSIONS_TAKE_PHOTO = {
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,};


    public static final int REQUEST_CAMERA_CODE = 101;
    public static final int REQUEST_SELECT_PHOTO_CODE = 102;


    private static void goToCamera(Context context, boolean needCrop) {
        ISCameraConfig config = new ISCameraConfig.Builder()
                // 裁剪
                .needCrop(needCrop)
                .build();
        ISNav.getInstance().toCameraActivity(context, config, REQUEST_CAMERA_CODE);
    }

    private static void goToAlbumn(Context context, int num, boolean needCrop) {
        // 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(ContextCompat.getColor(context, R.color.common_main_color))
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#272727"))
                // 返回图标ResId
                .backResId(R.drawable.ic_back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#272727"))
                // 裁剪大小。needCrop为true的时候配置
//                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(num)
                .build();

        // 跳转到图片选择器
        ISNav.getInstance().toListActivity(context, config, REQUEST_SELECT_PHOTO_CODE);
    }


    public static void takePhoto(Activity activity, boolean needCrop) {
        PerMissionsManager.newInstance().getUserPerMissions(activity, is -> {
            if (is) {
                goToCamera(activity, needCrop);
            } else {
                ToastUtil.shortShow(activity, "请开启拍照权限");
            }
        }, PERMISSIONS_TAKE_PHOTO);
    }

    public static void openAlbumn(Activity activity, int num, boolean needCrop) {
        PerMissionsManager.newInstance().getUserPerMissions(activity, new PerMissionCall() {
            @Override
            public void userPerMissionStatus(boolean is) {
                if (is) {
                    goToAlbumn(activity, num, needCrop);
                } else {
                    ToastUtil.shortShow(activity, "请开启存储权限");
                }
            }
        }, PERMISSIONS_WRITE);
    }

}
