package com.nj.baijiayun.module_public.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;

/**
 * @author chengang
 * @date 2019-09-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class AppConfigBean {


    @SerializedName("price_ico")
    private String priceIco = "https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019PJtrbVBBL71567997109.png";
    /**
     * app_video_cache : 1
     * course_statement : 本平台课程不支持下载，请付费后在线观看。
     * 支持windows、mac电脑浏览器观看，苹果手机、安卓手机观看。不支持ipad及iPhone5之前的手机型
     * student_percent : 100
     * teacher_percent : 90
     * upload_path : uploads/
     * switch_vip_module : 0
     * switch_study_module : 0
     * switch_question_module : 0
     * watch_live_type : 2
     */

    @SerializedName("switch_vip_module")
    private String switchVipModule;
    @SerializedName("switch_study_module")
    private String switchStudyModule;
    @SerializedName("switch_question_module")
    private String switchQuestionModule;
    /**
     * homework_module : {"is_open":"1","is_hw":{"is_dispatch":"1","is_dispatch_hw":"1,2","is_correct_hw":"1,2,3,4"},"is_oauth":{"teacher_oauth":"1","assist_teacher_oauth":"1"}}
     */

    @SerializedName("homework_module")
    private HomeworkModuleBean homeworkModule;
    @SerializedName("class_homework_module")
    private int classHomeworkModule;
    @SerializedName("price_svg")
    private String priceSvg;
    /**
     * switch_spell_module : 1
     * switch_integral_module : 0
     * switch_book_module : 1
     * switch_library_module : 0
     * switch_information_module : 0
     * switch_oto_module : 0
     * switch_coupon_module : 0
     */

    @SerializedName("switch_spell_module")
    private int switchSpellModule;
    @SerializedName("switch_integral_module")
    private int switchIntegralModule;
    @SerializedName("switch_book_module")
    private int switchBookModule;
    @SerializedName("switch_library_module")
    private int switchLibraryModule;
    @SerializedName("switch_information_module")
    private int switchInformationModule;
    @SerializedName("switch_oto_module")
    private int switchOtoModule;
    @SerializedName("switch_coupon_module")
    private int switchCouponModule;
    @SerializedName("switch_community_module")
    private int switchCommunityModule;
    @SerializedName("switch_distribute_module")
    private int switchDistributeModule;

    public boolean needShowDistribution() {
        return switchDistributeModule == 1;
    }

    public boolean needShowVipModule() {
        return "1".equals(switchVipModule);

    }

    public boolean needShowHomeWork() {
        return 1 == classHomeworkModule;
    }

    public boolean needShowStudyModule() {
        return "1".equals(switchStudyModule);
    }

    public boolean needShowQuestionModule() {
        return "1".equals(switchQuestionModule);
    }

    public boolean needShowSpellModule() {
        return 1 == switchSpellModule;
    }

    public boolean needShowIntegralModule() {
        return 1 == switchIntegralModule;
    }

    public boolean needShowBookModule() {
        return 1 == switchBookModule;
    }

    public boolean needShowLibraryModule() {
        return 1 == switchLibraryModule;
    }

    public boolean needShowInformationModule() {
        return 1 == switchInformationModule;
    }

    public boolean needShowOtoModule() {
        return 1 == switchOtoModule;
    }

    public boolean needShowCouponModule() {
        return 1 == switchCouponModule;
    }

    public boolean needShowCommunity() {
        return 1 == switchCommunityModule;
    }

    public String getPriceSvg() {
        return ConfigManager.getInstance().getOtherSetting().getPriceIcon();
    }

    public boolean isNoSvg() {
        return TextUtils.isEmpty(getPriceSvg());
    }


    public void setPriceIco(String priceIco) {
        this.priceIco = priceIco;
    }

    public String getPriceIco() {
        return priceIco;
    }

    public String getSwitchVipModule() {
        return switchVipModule;
    }

    public void setSwitchVipModule(String switchVipModule) {
        this.switchVipModule = switchVipModule;
    }

    public String getSwitchStudyModule() {
        return switchStudyModule;
    }

    public void setSwitchStudyModule(String switchStudyModule) {
        this.switchStudyModule = switchStudyModule;
    }

    public String getSwitchQuestionModule() {
        return switchQuestionModule;
    }

    public void setSwitchQuestionModule(String switchQuestionModule) {
        this.switchQuestionModule = switchQuestionModule;
    }

    public HomeworkModuleBean getHomeworkModule() {
        if (homeworkModule == null) {
            homeworkModule = new HomeworkModuleBean();
        }
        return homeworkModule;
    }

    public void setHomeworkModule(HomeworkModuleBean homeworkModule) {
        this.homeworkModule = homeworkModule;
    }

    public int getSwitchSpellModule() {
        return switchSpellModule;
    }

    public void setSwitchSpellModule(int switchSpellModule) {
        this.switchSpellModule = switchSpellModule;
    }

    public int getSwitchIntegralModule() {
        return switchIntegralModule;
    }

    public void setSwitchIntegralModule(int switchIntegralModule) {
        this.switchIntegralModule = switchIntegralModule;
    }

    public int getSwitchBookModule() {
        return switchBookModule;
    }

    public void setSwitchBookModule(int switchBookModule) {
        this.switchBookModule = switchBookModule;
    }

    public int getSwitchLibraryModule() {
        return switchLibraryModule;
    }

    public void setSwitchLibraryModule(int switchLibraryModule) {
        this.switchLibraryModule = switchLibraryModule;
    }

    public int getSwitchInformationModule() {
        return switchInformationModule;
    }

    public void setSwitchInformationModule(int switchInformationModule) {
        this.switchInformationModule = switchInformationModule;
    }

    public int getSwitchOtoModule() {
        return switchOtoModule;
    }

    public void setSwitchOtoModule(int switchOtoModule) {
        this.switchOtoModule = switchOtoModule;
    }

    public int getSwitchCouponModule() {
        return switchCouponModule;
    }

    public void setSwitchCouponModule(int switchCouponModule) {
        this.switchCouponModule = switchCouponModule;
    }

    public static class HomeworkModuleBean {
        /**
         * is_open : 1
         * is_hw : {"is_dispatch":"1","is_dispatch_hw":"1,2","is_correct_hw":"1,2,3,4"}
         * is_oauth : {"teacher_oauth":"1","assist_teacher_oauth":"1"}
         */

        @SerializedName("is_open")
        private String isOpen;
        @SerializedName("is_hw")
        private IsHwBean isHw;
        @SerializedName("is_oauth")
        private IsOauthBean isOauth;

        public String getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(String isOpen) {
            this.isOpen = isOpen;
        }

        public IsHwBean getIsHw() {
            return isHw;
        }

        public void setIsHw(IsHwBean isHw) {
            this.isHw = isHw;
        }

        public IsOauthBean getIsOauth() {
            return isOauth;
        }

        public void setIsOauth(IsOauthBean isOauth) {
            this.isOauth = isOauth;
        }

        public static class IsHwBean {
            /**
             * is_dispatch : 1
             * is_dispatch_hw : 1,2
             * is_correct_hw : 1,2,3,4
             */

            @SerializedName("is_dispatch")
            private String isDispatch;
            @SerializedName("is_dispatch_hw")
            private String isDispatchHw;
            @SerializedName("is_correct_hw")
            private String isCorrectHw;

            public String getIsDispatch() {
                return isDispatch;
            }

            public void setIsDispatch(String isDispatch) {
                this.isDispatch = isDispatch;
            }

            public String getIsDispatchHw() {
                return isDispatchHw;
            }

            public void setIsDispatchHw(String isDispatchHw) {
                this.isDispatchHw = isDispatchHw;
            }

            public String getIsCorrectHw() {
                return isCorrectHw;
            }

            public void setIsCorrectHw(String isCorrectHw) {
                this.isCorrectHw = isCorrectHw;
            }
        }

        public static class IsOauthBean {
            /**
             * teacher_oauth : 1
             * assist_teacher_oauth : 1
             */

            @SerializedName("teacher_oauth")
            private String teacherOauth;
            @SerializedName("assist_teacher_oauth")
            private String assistTeacherOauth;

            public String getTeacherOauth() {
                return teacherOauth;
            }

            public void setTeacherOauth(String teacherOauth) {
                this.teacherOauth = teacherOauth;
            }

            public String getAssistTeacherOauth() {
                return assistTeacherOauth;
            }

            public void setAssistTeacherOauth(String assistTeacherOauth) {
                this.assistTeacherOauth = assistTeacherOauth;
            }
        }
    }

    @SerializedName("point_info")
    private PointInfo pointInfo;

    public static class PointInfo {
        @SerializedName("point_name")
        private String pointName;

        public String getPointName() {
            if (pointName == null) {
                return "我的积分";
            }
            return "我的" + pointName;
        }
    }


    public PointInfo getPointInfo() {
        if (pointInfo == null) {
            pointInfo = new PointInfo();
        }
        return pointInfo;
    }
}
