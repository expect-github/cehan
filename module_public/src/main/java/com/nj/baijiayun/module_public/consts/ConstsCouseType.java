package com.nj.baijiayun.module_public.consts;

import android.text.TextUtils;

import com.nj.baijiayun.module_public.helper.config.ConfigManager;

import java.util.HashMap;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.consts
 * @describe
 */
public class ConstsCouseType {
//    课程类型 10图文, 2大班课, 3小班课, 5视频课,8音频课, 9系统课 4 公开课 5录播课/点播课

    //公开课点击直接进入 直播页面

    private static final int COURSE_TYPE_LARGE_CLASS = 2;
    private static final int COURSE_TYPE_SMALL_CLASS = 3;
    private static final int COURSE_TYPE_SYSTEM = 9;
    private static final int COURSE_TYPE_VIP = 11;
    private static final int COURSE_TYPE_PUBLIC = 4;
    private static final int COURSE_TYPE_IMG_TXT = 10;
    private static final int COURSE_TYPE_FACE = 7;
    private static final int COURSE_TYPE_AUDIO = 8;
    private static final int COURSE_TYPE_RECORDING = 5;
    private static final int COURSE_TYPE_O2O = 1;

//    课程类型：1一对一, 2大班课, 3小班课, 4公开课, 5点播课, 7面授课, 8音频课, 9系统课,10图文课

    public static String getCourseTypeName(int courseType) {
        String result = "";
        HashMap<String, String> courseNameMap = ConfigManager.getInstance().getOtherSetting().getCourseNameMap();
        if (courseNameMap != null) {
            result = courseNameMap.get(String.valueOf(courseType));
            if (!TextUtils.isEmpty(result)) {
                return result;
            }
        }
        switch (courseType) {
            case COURSE_TYPE_O2O:
                result = "一对一";
                break;
            case COURSE_TYPE_LARGE_CLASS:
                result = "大班课";
                break;
            case COURSE_TYPE_SMALL_CLASS:
                result = "小班课";
                break;
            case COURSE_TYPE_PUBLIC:
                result = "公开课";
                break;
            case COURSE_TYPE_RECORDING:
                result = "点播课";
                break;
            case COURSE_TYPE_FACE:
                result = "面授课";
                break;
            case COURSE_TYPE_AUDIO:
                result = "音频课";
                break;
            case COURSE_TYPE_SYSTEM:
                result = "系统课";
                break;
            case COURSE_TYPE_IMG_TXT:
                result = "图文课";
                break;
            default:
                result = "";
                break;

        }
        return result;
    }


    public static boolean isLive(int courseType) {
        return courseType == COURSE_TYPE_LARGE_CLASS
                || courseType == COURSE_TYPE_SMALL_CLASS
                || courseType == COURSE_TYPE_PUBLIC || courseType == COURSE_TYPE_O2O;
    }

    public static boolean isSmallGroup(int courseType) {
        return courseType == COURSE_TYPE_O2O || courseType == COURSE_TYPE_SMALL_CLASS;
    }

    public static boolean isOto(int courseType) {
        return courseType == COURSE_TYPE_O2O;
    }

    public static boolean isSystem(int courseType) {
        return courseType == COURSE_TYPE_SYSTEM;
    }

    public static boolean isVipCourse(int courseType) {
        return courseType == COURSE_TYPE_VIP;
    }

    public static boolean isFace(int courseType) {
        return COURSE_TYPE_FACE == courseType;
    }

    public static boolean isAudio(int courseType) {
        return COURSE_TYPE_AUDIO == courseType;
    }

    public static boolean isVideo(int courseType) {
        return COURSE_TYPE_RECORDING == courseType;
    }

    public static boolean isImgTxt(int courseType) {
        return COURSE_TYPE_IMG_TXT == courseType;
    }

    public static boolean isCanClickInCourseDetail(int courseType) {
        return isLive(courseType)
                || isImgTxt(courseType)
                || isVideo(courseType)
                || isAudio(courseType);
    }

    public static int getOtoCourseType() {
        return COURSE_TYPE_O2O;
    }

    public static int getCourseTypePublic() {
        return COURSE_TYPE_PUBLIC;
    }

    public static boolean isPublic(int courseType) {
        return COURSE_TYPE_PUBLIC == courseType;
    }


}
