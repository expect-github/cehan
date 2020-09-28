package com.nj.baijiayun.module_public.helper;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public interface IAccount<T> {

    boolean isLogin();

    void saveInfo(T data);

    void saveInfo();

    T initInfo();

    T getInfo();

    void updateUserInfoByNet();

    String getToken();

    boolean isVip();

    void logout();

    void logoutOnlyRemoveInfo();

    void login(Object userInfo);

    boolean isCertify();

    void saveOauthUnionid(String unionid);

    String getOauthUnionid();

    void saveOauthAccessToken(String token);

    String getOauthAccessToken();

}
