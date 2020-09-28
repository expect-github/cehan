package com.nj.baijiayun.module_public.consts;

/**
 * @author chengang
 * @date 2019-07-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.consts
 * @describe
 */
public class ConstsLoginType {
    /**
     * 1 账号密码登录 2 短信验证码登录 3 QQ 登录 4 微信登录 5 老师登录
     */
    public static final int LOGIN_BY_PWD=1;
    public static final int LOGIN_BY_SMS_CODE=2;
    public static final int LOGIN_BY_QQ=3;
    public static final int LOGIN_BY_WE_CHAT=4;
    public static final int LOGIN_BY_TEACHER=5;
    public static final String QQ_LOGIN_BASE_URL="https://graph.qq.com/";
}
