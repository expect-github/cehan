package com.nj.baijiayun.module_public.helper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.AppUtils;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.SharedPrefsUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.UserInfoBean;
import com.nj.baijiayun.module_public.bean.response.UserInfoRes;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;

import java.lang.reflect.Field;

import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class AccountHelper implements IAccount<UserInfoBean> {
    private static final String SAVE_LOGIN_DATA = "save_user_login";
    private static UserInfoBean sUserLoginBean;
    private String oauthUnionId;
    private String oauthAccessToken;
    Field[] UserInfoBeanFields = getAllFields(UserInfoBean.class);

    public AccountHelper() {
        initInfo();
    }


    public static AccountHelper getInstance() {
        return AccountDataRepoManager.instance;
    }

    private static class AccountDataRepoManager {
        static AccountHelper instance = new AccountHelper();
    }


    @Override
    public boolean isLogin() {

        return sUserLoginBean != null && sUserLoginBean.getLoginToken() != null;
    }

    @Override
    public void saveInfo(UserInfoBean data) {
        if (getInfo() != null) {
            data.tryCopyLoginInfo(getInfo());
        }
        if (!beanIsChange(getInfo(), data)) {
            Logger.d("UserInfo not change");

            return;
        }
        Logger.d("UserInfo is change");
        ObjectToObservableHelper.getInstance(UserInfoBean.class).saveContent(data);
        String value = GsonHelper.getGsonInstance().toJson(data);
        updateUserInfo(value);
        parseUserInfo(value);
    }

    @Override
    public void saveInfo() {
        saveInfo(getInfo());
    }


    @Override
    public UserInfoBean initInfo() {
        String json = SharedPrefsUtil.getValue(AppUtils.getContext(), SAVE_LOGIN_DATA, SAVE_LOGIN_DATA, "");
        parseUserInfo(json);
        return sUserLoginBean;
    }

    @Override
    public UserInfoBean getInfo() {
        return sUserLoginBean;
    }

    @Override
    public void updateUserInfoByNet() {
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getUserInfoNew()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSimpleObserver<UserInfoRes>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(UserInfoRes baseResponse) {
                        //更新vip信息
                        AccountHelper.getInstance().saveInfo(baseResponse.getData());


                    }

                    @Override
                    public void onNext(UserInfoRes userInfoRes) {
                        if (userInfoRes.isSuccess()) {
                            onSuccess(userInfoRes);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }

    @Override
    public String getToken() {
        if (sUserLoginBean != null) {
            return sUserLoginBean.getLoginToken();
        }
        return null;
    }

    @Override
    public boolean isVip() {
        if (getInfo() != null) {
            return getInfo().isVip();
        }
        return false;
    }

    @Override
    public void logout() {
        logoutOnlyRemoveInfo();
        ARouter.getInstance().build(RouterConstant.PAGE_LOGIN).navigation();

    }

    @Override
    public void logoutOnlyRemoveInfo() {

        boolean needNotify = sUserLoginBean != null;
        ObjectToObservableHelper.getInstance(UserInfoBean.class).saveContent(null);
        sUserLoginBean = null;
        updateUserInfo("");
        if (needNotify) {
            LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE).postValue(true);
        }


    }

    @Override
    public void login(Object userInfo) {
        if (userInfo instanceof UserInfoBean) {
            saveInfo((UserInfoBean) userInfo);
        }
        updateUserInfoByNet();
        LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE).postValue(true);
        //服务器已经去掉重复了 没有单独的注册口 都是登陆自动注册的
    }

    @Override
    public boolean isCertify() {
        return getInfo() != null && getInfo().isCertify();
    }

    @Override
    public void saveOauthUnionid(String unionid) {
        this.oauthUnionId = unionid;
    }

    @Override
    public String getOauthUnionid() {
        return oauthUnionId;
    }

    @Override
    public void saveOauthAccessToken(String token) {
        this.oauthAccessToken = token;
    }

    @Override
    public String getOauthAccessToken() {
        return oauthAccessToken;
    }

    private void updateUserInfo(String value) {
        SharedPrefsUtil.putValue(AppUtils.getContext(), SAVE_LOGIN_DATA, SAVE_LOGIN_DATA, value);
    }

    private void parseUserInfo(String value) {
        try {
            sUserLoginBean = GsonHelper.getGsonInstance().fromJson(value, UserInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean beanIsChange(UserInfoBean source, UserInfoBean target) {
        try {
            if (source == null) {
                return true;
            }
            Field[] fields = this.UserInfoBeanFields;
            if (fields == null) {
                return true;
            }
            for (Field field : fields) {
                field.setAccessible(true);

                Object o = field.get(source);
                Object obj = field.get(target);
                Logger.d("beanIsChange:" + field.getName() + "__" + o + "----" + obj);

                if (o == null && obj == null) {
                    continue;
                }
                if (o != null && !o.equals(obj)) {
                    return true;
                }

            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public Field[] getAllFields(Class clazz) {
        return clazz.getDeclaredFields();
    }
}
