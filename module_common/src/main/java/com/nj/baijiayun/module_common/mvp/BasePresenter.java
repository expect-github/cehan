package com.nj.baijiayun.module_common.mvp;

import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by desin on 2017/1/12.
 */

public abstract class BasePresenter<T extends BaseView> {
    protected T mView;

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param
     */
    public void takeView(T mView) {
        this.mView = mView;
    }

    /**
     * Drops the reference to the view when destroyed
     */
    public void dropView() {
        unSubscribe();
    }

    private CompositeDisposable mCompositeSubscription;

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }

        mCompositeSubscription.add(subscription);
    }

    public <V extends BaseResponse> void submitRequest(Observable<V> observable, BaseObserver<V> baseObserver) {
        baseObserver.onPreRequest();
        observable.compose(RxJavaHelper.subscribeOnIoObserveOnMain()).subscribe(baseObserver);
    }

    public void unSubscribe() {
        if (mView != null) {
            mView = null;
        }
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }


}
