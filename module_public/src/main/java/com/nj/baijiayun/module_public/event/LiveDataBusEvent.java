package com.nj.baijiayun.module_public.event;

/**
 * @author chengang
 * @date 2019-07-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.event
 * @describe
 */
public class LiveDataBusEvent {

    /**
     * 提交考试成功
     */
    public static final String TASK_SUBMIT_EXAM_ANSWER_SUCCESS = "exam_answer";
    @Deprecated
    public static final String SUBMIT_LEARN_PROGRESS_SUCCESS = "learn_success";
    /**
     *预览文件
     */
    public static final String PREVIEW_FILE = "preview_file";
    public static final String REMOVE_COURSE_SUCCESS = "remove_course";
    /**
     * 首页tab 切换
     */
    public static final String MAIN_TAB_SWITCH = "main_tab_switch";
    public static final String MAIN_TAB_SWITCH_EXIST_TAB = "main_tab_switch_exist_tab";
    //只对内部 sdk 开放 以及 base类开放
    public static final String PAY_SUCCESS = "pay_success";
    /**
     * 支付报名成功
     */
    public static final String COURSE_HAS_BUY_SUCCESS_BY_PAY = "course_has_buy_success_by_pay";
    /**
     * 登陆状态改变
     */
    public static final String LOGIN_STATUS_CHANGE = "login_status_change";
    /**
     * 免费课程购买成功，vip加入会员成功
     */
    public static final String COURSE_HAS_BUY_SUCCESS_BY_JOIN_OR_FREE = "course_has_buy_success";

    /**
     * 收藏状态改变
     */
    public static final String COLLECTION_STATUES_CHANGE = "collection_status_change";


    /**
     * 评论成功
     */
    public static final String COURSE_COMMENT_SUCCESS = "comment_status_success";


    public static final String LIBRARY_PREVIEW ="LIBRARY_PREVIEW";
    public static final String NEWS_PREVIEW ="NEWS_PREVIEW";

    public static final String COURSE_REFRESH = "COURSE_REFRESH";
    public static final String RECOVER_COURSE_SUCCESS = "recover_course";
    public static final String COURSE_HIDE_RECOVER = "COURSE_HIDE_RECOVER";







}
