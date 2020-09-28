package com.nj.baijiayun.module_public.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.AbstractDefaultNetProvider;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.lib_http.retrofit.RequestHandler;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.config.ShapeTypeConfig;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.LoginRes;
import com.nj.baijiayun.module_public.consts.ConstsLoginType;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.StringConverterFactory;
import com.nj.baijiayun.module_public.helper.share_login.JPushHelper;
import com.nj.baijiayun.module_public.mvp.contract.LoginContract;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.realm.internal.Keep;
import retrofit2.Converter;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.nj.baijiayun.module_public.consts.ConstsLoginType.QQ_LOGIN_BASE_URL;

/**
 * @author chengang
 * @date 2019-06-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_user.ui.login
 * @describe
 */
public class LoginPresenter extends LoginContract.Presenter {

    private final PublicService mApiService;

    @Inject
    LoginPresenter(PublicService apiService) {
        this.mApiService = apiService;
    }


    @Override
    public void qqLogin() {
        if (!mView.checkAgreeProtocolPass()) {
            return;
        }
        mView.showLoadV();
        Logger.d("qqLogin start");

        JPushHelper.getInstance().UserLoginByJShareLogin(ShapeTypeConfig.QQ, (s, isSuccessLogin, toastMsg) -> {
            Logger.d("qqLogin mView" + mView);

            if (mView == null) {
                return;
            }
            ((Activity) mView).runOnUiThread(() -> {
                mView.closeLoadV();
                Logger.d("qqLogin close");

                if (s == null) {
                    if (mView != null) {
                        mView.showToastMsg(toastMsg);
                    }
                    return;
                }

                Logger.d("qqLogin-->" + s.toString());
                getQqloginUnionid(s.getToken(), s.getOpenid());
            });

        });
    }

    @Override
    public void wechatLogin() {
        if (!mView.checkAgreeProtocolPass()) {
            return;
        }
        mView.showLoadV();
        Logger.d("wechatLogin start");

        JPushHelper.getInstance().UserLoginByJShareLogin(ShapeTypeConfig.WX, (s, isSuccessLogin, toastMsg) -> {
            Logger.d("wechatLogin mView" + mView);

            if (mView == null) {
                return;
            }
            ((Activity) mView).runOnUiThread(() -> {
                Logger.d("wechatLogin close 1");
                mView.closeLoadV();
                if (s == null) {
                    mView.showToastMsg(toastMsg);
                    return;
                }
                Logger.d("wechatLogin" + s.toString());
                try {
                    getWeChatUnionId(s.getToken(),s.getOpenid(),new JSONObject(s.getOriginData()).optString("unionid"));
                } catch (Exception e) {
                   ToastUtil.show("获取unionid错误");
                }

//                loginByOpenId(s.getOpenid(), ConstsLoginType.LOGIN_BY_WE_CHAT, s.getToken());
            });


        });
    }

    private void loginByOpenId(String openId, int loginType, String accessToken) {

        AccountHelper.getInstance().saveOauthAccessToken(accessToken);
        Logger.d("wechatLogin loginByOpenId ");
        mView.showLoadV();
        submitRequest(mApiService.loginByThirdPlatform("", "", openId, loginType), new BaseObserver<LoginRes>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onNext(LoginRes loginRes) {
                mView.closeLoadV();
                Logger.d("wechatLogin close 2 ");
                if (loginRes.getStatus() == 201) {
                    ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_BIND_PHONE)
                            .withString("openId", openId)
                            .withInt("loginType", loginType)
                            .navigation();
                } else if (loginRes.isSuccess()) {
                    onSuccess(loginRes);
                } else {
                    onFail(new Exception(loginRes.getMsg()));
                }
            }

            @Override
            public void onSuccess(LoginRes loginRes) {
                AccountHelper.getInstance().login(loginRes.getData());
                mView.showToastMsg(((Activity) mView).getString(R.string.public_login_success));
                ((Activity) mView).finish();
            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getWeChatUnionId(String accessToken,String openId,String unionid)
    {
        AccountHelper.getInstance().saveOauthUnionid(unionid);
        loginByOpenId(openId, ConstsLoginType.LOGIN_BY_WE_CHAT, accessToken);
    }

    @SuppressLint("CheckResult")
    private void getQqloginUnionid(String accessToken, String openId) {

        NetMgr.getInstance().registerProvider(QQ_LOGIN_BASE_URL, "", new AbstractDefaultNetProvider() {
            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public String configBaseUrl() {
                return QQ_LOGIN_BASE_URL;
            }

            @Override
            public Converter.Factory configConvertFactory() {
                return new StringConverterFactory();
            }
        });
        NetMgr.getInstance().get(QQ_LOGIN_BASE_URL, QqUnionIdService.class)
                .getQqUnionId(accessToken)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(s -> {
                    QqCallBackBean qqCallBackBean = parseQqCallBack(s);
                    if (qqCallBackBean.isSuccess()) {
                        AccountHelper.getInstance().saveOauthUnionid(qqCallBackBean.unionid);
                        loginByOpenId(openId, ConstsLoginType.LOGIN_BY_QQ, accessToken);
                    } else {
                        ToastUtil.show(qqCallBackBean.getErrorDescription());
                    }
                }, throwable -> ToastUtil.show(throwable.getMessage()));
    }

    private QqCallBackBean parseQqCallBack(String s) {
        int startIndex = s.indexOf("{");
        int endIndex = s.lastIndexOf("}");
        String substring = s.substring(startIndex, endIndex + 1);
        return GsonHelper.getGsonInstance().fromJson(substring, QqCallBackBean.class);
    }


    @Keep
    public static class QqCallBackBean {

        /**
         * client_id : YOUR_APPID
         * openid : YOUR_OPENID
         * unionid : YOUR_UNIONID
         */

        @SerializedName("client_id")
        private String clientId;
        @SerializedName("openid")
        private String openid;
        @SerializedName("unionid")
        private String unionid;
        /**
         * error : 100016
         * error_description : access token check failed
         */

        private int error;
        @SerializedName("error_description")
        private String errorDescription;

        public boolean isSuccess() {
            return !TextUtils.isEmpty(unionid);
        }

        public String getErrorDescription() {
            return errorDescription;
        }
    }


    public interface QqUnionIdService {
        @GET("oauth2.0/me?unionid=1")
        Observable<String> getQqUnionId(@Query("access_token") String accessToken);
    }


}
