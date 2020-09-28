package com.nj.baijiayun.module_public.provider;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public
 * @describe
 */
@Interceptor(name = "login", priority = 1)
public class LoginInterceptor implements IInterceptor {
    private static final String TAG = "LoginInterceptor";

    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        Log.i(TAG, "LoginInterceptor 开始执行");

        //给需要跳转的页面添加值为Constant.LOGIN_NEEDED的extra参数，用来标记是否需要用户先登录才可以访问该页面
        //先判断需不需要

//        boolean isLogin = AccountHelper.getInstance().isLogin();
        callback.onContinue(postcard);
//        if (isLogin) {
//            callback.onContinue(postcard);
//        } else {
//            switch (path) {
//                // 不需要登录的直接进入这个页面
//                case RouterConstant.PAGE_LOGIN:
//                    callback.onContinue(postcard);
//                    break;
//                case RouterConstant.PAGE_PUBLIC_FORGET_PWD:
//                    callback.onContinue(postcard);
//                    break;
//                default:
//                    callback.onInterrupt(null);
//                    ARouter.getInstance().build(RouterConstant.PAGE_LOGIN).navigation();
//                    // 需要登录的直接拦截下来
//                    break;
//            }
//
//        }

    }

    @Override
    public void init(Context context) {

        mContext = context;

        Log.i(TAG, "LoginInterceptor 初始化");

    }

}
