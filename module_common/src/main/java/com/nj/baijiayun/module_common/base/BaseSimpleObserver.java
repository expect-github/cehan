package com.nj.baijiayun.module_common.base;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-06-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.basic.demo.network
 * @describe
 */
public abstract class BaseSimpleObserver<T extends BaseResponse> extends BaseObserver<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 这不会主动调用 需要自己调用
     */
    @Override
    public void onPreRequest() {

    }
}

