package com.nj.baijiayun.module_common.api;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.nj.baijiayun.lib_http.BuildConfig;
import com.nj.baijiayun.lib_http.exception.ApiException;
import com.nj.baijiayun.logger.log.Logger;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.Response;
import retrofit2.HttpException;


public class ApiErrorHelper {
    public static Exception handleError(Throwable e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }

        Logger.e("handleHttpResponse  getMessage--->" + e + "--->" + e.getMessage());
        //HTTP错误
        if (e instanceof HttpException) {
            return new Exception("解析错误");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            return new Exception("解析错误");
        } else if (e instanceof UnknownHostException) {
            return new Exception("网络连接失败");
        } else if (e instanceof ConnectException) {
            return new Exception("连接失败");
            //服务器返回的错误
        } else if (e instanceof ApiException) {
            return new Exception(e.getMessage());

        } else {
            return new Exception("未知错误");
        }
    }

    public static Response handleHttpResponse(Response response) throws com.nj.baijiayun.lib_http.exception.ApiException {
        if (response == null) {
            return response;
        }
        if (401 == response.code()) {
            throw new ApiException("登录已过期,请重新登录!");
        } else if (403 == response.code()) {
            throw new ApiException("禁止访问!");
        } else if (404 == response.code()) {
            throw new ApiException("链接错误");
        } else if (503 == response.code()) {
            throw new ApiException("服务器升级中!");
        } else if (500 == response.code()) {
            throw new ApiException("服务器内部错误!");
        }
        return response;
    }


}
