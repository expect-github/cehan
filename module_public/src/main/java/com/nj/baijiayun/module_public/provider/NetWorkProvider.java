package com.nj.baijiayun.module_public.provider;

import android.text.TextUtils;

import com.nj.baijiayun.lib_http.retrofit.AbstractDefaultNetProvider;
import com.nj.baijiayun.lib_http.retrofit.RequestHandler;
import com.nj.baijiayun.module_common.api.ApiErrorHelper;
import com.nj.baijiayun.module_common.config.CommonConfig;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.ConstsLoginType;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.umeng.message.PushAgent;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author chengang
 * @date 2019-06-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.basic.demo.network
 * @describe
 */
public class NetWorkProvider extends AbstractDefaultNetProvider {
    private boolean debugEnable;

    public NetWorkProvider(boolean debugEnable) {
        this.debugEnable = debugEnable;
    }

    @Override
    public RequestHandler configHandler() {
        return new RequestHandler() {
            @Override
            public Request handleRequest(Request request, Interceptor.Chain chain) {

                HttpUrl url = chain.request().url();
                HttpUrl.Builder builder = chain.request().url().newBuilder();
                if (TextUtils.isEmpty(url.queryParameter("limit"))) {
                    builder.addQueryParameter("limit", String.valueOf(CommonConfig.PAGE_LIMIT));
                }
                if (url.toString().contains("api/app/login")) {
                    //qq
                    if (String.valueOf(ConstsLoginType.LOGIN_BY_QQ).equals(url.queryParameter("type"))
                            || String.valueOf(ConstsLoginType.LOGIN_BY_WE_CHAT).equals(url.queryParameter("type"))) {
                        builder.addQueryParameter("unionid", AccountHelper.getInstance().getOauthUnionid());
                        builder.addQueryParameter("access_token", AccountHelper.getInstance().getOauthAccessToken());
                    }
                    builder.addQueryParameter("device_id", PushAgent.getInstance(BaseApp.getInstance()).getRegistrationId());
                }
                Request.Builder headBuilder = chain.request().newBuilder();
                if (!TextUtils.isEmpty(AccountHelper.getInstance().getToken())) {
                    headBuilder.addHeader("Authorization", "Bearer " + AccountHelper.getInstance().getToken());
                }
                return headBuilder.addHeader("DeviceType", "ANDROID")
                        .url(builder.build())
                        .build();
            }

            @Override
            public Response handleResponse(Response response, Interceptor.Chain chain) throws IOException {
                return ApiErrorHelper.handleHttpResponse(response);
            }
        };
    }

    @Override
    public Interceptor[] configInterceptors() {
        return new Interceptor[]{new TimeCalibrationInterceptor()};
    }

    @Override
    public Converter.Factory configConvertFactory() {

        return GsonConverterFactory.create(GsonHelper.getGsonInstance());
    }

    @Override
    public String configBaseUrl() {
        return ConstsH5Url.getBaseApiUrl();
    }


    @Override
    public boolean configLogEnable() {
        return debugEnable;
    }

    @Override
    public int configMaxRetry() {
        return 1;
    }
}
