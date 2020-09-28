package com.nj.baijiayun.module_common.base;

import com.nj.baijiayun.basic.rxbus.RxBus;
import com.nj.baijiayun.module_common.api.ApiErrorHelper;
import com.nj.baijiayun.module_common.event.LoginEvent;

import io.reactivex.Observer;

/**
 * @author chengang
 * @date 2019-06-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.basic.demo.network
 * @describe
 */
public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {


    protected BaseObserver() {
    }

    public abstract void onPreRequest();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void onNext(T t) {

        if (t.isSuccess()) {
            onSuccess(t);
        }else if(t.isNeedLogin()){
            RxBus.getInstanceBus().post(new LoginEvent());
            onFail(new Exception(t.getMsg()));
        } else {
            onFail(new Exception(t.getMsg()));
        }
    }


    @Override
    public void onError(Throwable e) {
        onFail(ApiErrorHelper.handleError(e));
    }

    public abstract void onSuccess(T t);

    public abstract void onFail(Exception e);

}

