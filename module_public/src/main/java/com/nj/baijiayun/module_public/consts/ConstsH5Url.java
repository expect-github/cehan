package com.nj.baijiayun.module_public.consts;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengang
 * @date 2019-08-08
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.consts
 * @describe
 */
public class ConstsH5Url {
    private static final String COMMENT = "/comments";
    private static final String OTO = "/oto";
    private static final String RECORD = "/record";
    private static final String TEACHER = "/teacher";
    private static final String MY_PLAN = "/my-plan";
    private static final String MY_MSG = "/message";
    private static final String MY_PERIOD = "/my-";
    private static final String MY_INFO = "/info";
    private static final String PURCHASE = "/purchase";
    private static final String VIP = "/vip";
    private static final String FEEDBACK = "/options/feedback";
    private static final String IMG_TXT_DETAIL = "/picture-course-detail";
    private static final String PROTOCOL = "/treaty?name=";
    private static final String PRACTISE = "/practise";
    private static final String HOMEWORK = "/homework";
    private static final String SHOW_TEACHERS = "/show-teachers";
    private static final String ASSEMBLE_SHOP = "/show-teachers";
    private static final String MY_ASSEMBLE = "/my-group";
    private static final String LIBRARY = "/library";
    private static final String ASSEMBLE_RULE = "/group-rule";
    private static final String ASSEMBLE_DETAIL = "/group-detail?group_id=";
    private static final String COMMUNITY = "/community";
    private static final String ORDER_DETAIL = "/order-detail";
    private static final String SIGN = "/sign";
    private static final String INTEGRAL = "/integral/detail";
    private static final String QUES = "/ques";
    private static final String ERROR_LIST = "/error-list";
    private static final String GROUP = "/group";
    private static final String INTEGRAL_STORE = "/integral/store";
    private static final String STUDY_CALENDAR = "/study-calendar";
    private static final String NEWS = "/news";
    private static final String BOOK_LIST = "/book-list";
    private static final String DO_HOME_WORK = "/do-homework";
    public static final String ROUTER_PRACTISE = "Practise";
    public static final String ROUTER_COURSE = "Course";
    public static final String ROUTER_GROUP = "Group";
    public static final String ROUTER_INDEX = "Index";
    public static final String ROUTER_PERSON = "Person";
    public static final String ROUTER_STUDY_CALENDAR = "StudyCalendar";
    public static final String ROUTER_BOOK_LIST = "BookList";
    public static final String ROUTER_NEWS = "News";
    public static final String ROUTER_TEACHER_LIST = "ShowTeachers";
    private static String BASE_H5_URL = "";
    private static String BASE_API_URL = "";
    private static String DISTRIBUTE = "/distribute";
    private static String DISTRIBUTE_RECRUIT = "/distribute/recruit";
    private static String ABOUT = "/options/about";

    public static String getQues() {
        return QUES;
    }

    public static String getErrorList() {
        return ERROR_LIST;
    }

    public static String getIntegral() {
        return INTEGRAL;
    }

    public static String getSign() {
        return SIGN;
    }

    public static String getOrderDetail() {
        return ORDER_DETAIL;
    }

    public static String getAssembleDetail(String groupId) {
        return ASSEMBLE_DETAIL + groupId;
    }

    public static String getCOMMUNITY() {
        return COMMUNITY;
    }

    public static String getAssembleRule() {
        return ASSEMBLE_RULE;
    }

    public static String getLIBRARY() {
        return LIBRARY;
    }

    public static String getMyAssemble() {
        return MY_ASSEMBLE;
    }

    public static String getAssembleShop() {
        return ASSEMBLE_SHOP;
    }

    public static String getBooks() {
        return getUrl(BOOK_LIST);
    }

    public static String getNews() {
        return getUrl(NEWS);
    }


    public static String getImgTxtDetail() {
        return getUrl(IMG_TXT_DETAIL);
    }

    //    public static final String BASE_URL ="https://testwxwap.baijiayun.com";

    public static void setBaseH5Url(String baseUrl) {
        BASE_H5_URL = baseUrl;
    }

    public static String getComment() {
        return BASE_H5_URL + COMMENT;
    }

    public static String getProtocol(String name) {
        return getUrl(PROTOCOL) + name;
    }

    public static String getVip() {
        return getUrl(VIP);
    }


    public static String getFeedback() {
        return getUrl(FEEDBACK);
    }

    public static String getMyPlan() {
        return getUrl(MY_PLAN);
    }

    public static String getInfo() {
        return getUrl(MY_INFO);
    }

    public static String getMsg() {
        return getUrl(MY_MSG);
    }

    public static String getMyPeriod() {
        return getUrl(MY_PERIOD);
    }

    public static String getShowTeachers() {
        return getUrl(SHOW_TEACHERS);
    }

    public static String getUrl(String extra) {
        if (extra == null) {
            return BASE_H5_URL;
        }
        if (extra.startsWith("http") && extra.contains("//")) {
            return extra;
        }
        if (!extra.startsWith("/")) {
            return BASE_H5_URL + "/" + extra;
        }
        return BASE_H5_URL + extra;
    }

    public static String getRecord() {
        return getUrl(RECORD);
    }

    public static String getPractise() {
        return getUrl(PRACTISE);
    }

    public static String getPayMidder() {
        return getUrl("pay-middle?pay_type=wx&payment_number=P2019112017351677768");
    }

    public static String getTeacher() {
        return getUrl(TEACHER);
    }

    public static String getPurchase() {
        return getUrl(PURCHASE);
    }

    public static String getOto() {
        return BASE_H5_URL + OTO;
    }

    public static String getHomework() {
        return getUrl(HOMEWORK);
    }


    public static String createLinkStringByGet(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        StringBuilder prestr = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            try {
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (i == keys.size() - 1) {
                //拼接时，不包括最后一个&字符
                prestr.append(key).append("=").append(value);
            } else {
                prestr.append(key).append("=").append(value).append("&");
            }
        }
        return prestr.toString();
    }


    //    课程  Course
//    一对一约课  Oto
//    约课记录  Record
//    嗨拼团  Group
//    讲师  ShowTeachers
//    图书  BookList
//    资讯  News
//    文库  Library
//    社区  Community
//    练习  Practise
//    学习日历  StudyCalendar
//    积分商城  IntegralStore
//    签到  Sign
//    作业  Homework
//    消息  Message

    private static Map<String, String> h5UrlMap = new HashMap<>();

    static {
//        h5UrlMap.put("Course",OTO);
        h5UrlMap.put("Oto", OTO);
        h5UrlMap.put("Record", RECORD);
        h5UrlMap.put(ROUTER_TEACHER_LIST, SHOW_TEACHERS);
        h5UrlMap.put("BookList", BOOK_LIST);
        h5UrlMap.put(ROUTER_NEWS, NEWS);
        h5UrlMap.put("Library", LIBRARY);
        h5UrlMap.put("Community", COMMUNITY);
        h5UrlMap.put(ROUTER_PRACTISE, PRACTISE);
        h5UrlMap.put(ROUTER_STUDY_CALENDAR, STUDY_CALENDAR);
        h5UrlMap.put("IntegralStore", INTEGRAL_STORE);
        h5UrlMap.put("Sign", SIGN);
        h5UrlMap.put("Homework", HOMEWORK);
        h5UrlMap.put("Message", MY_MSG);
        h5UrlMap.put(ROUTER_GROUP, GROUP);
    }

    static Map<String, String> getH5UrlMap() {
        return h5UrlMap;
    }

    public static String getUrlByRouterKey(String key) {
        return TextUtils.isEmpty(h5UrlMap.get(key)) ? "" : h5UrlMap.get(key);
    }

    public static String getRecordPath() {
        return RECORD;
    }

    public static String getPractisePath() {
        return PRACTISE;
    }

    public static String getHomeWorkPath() {
        return DO_HOME_WORK;
    }

    public static String getMyGroupPath() {
        return MY_ASSEMBLE;
    }

    public static String getOTOPath() {
        return OTO;
    }

    public static void setBaseApiUrl(String baseApiUrl) {
        BASE_API_URL = baseApiUrl;
    }

    public static String getBaseApiUrl() {
        return BASE_API_URL;
    }

    public static String getBaseH5Url() {
        return BASE_H5_URL;
    }

    public static String getDistributePath() {
        return DISTRIBUTE;
    }

    public static String getDistributeRecruitPath() {
        return DISTRIBUTE_RECRUIT;
    }

    public static String getAboutUrl() {
        return ConstsH5Url.getUrl(ABOUT);
    }

}
