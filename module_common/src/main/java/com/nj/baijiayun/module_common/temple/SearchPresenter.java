package com.nj.baijiayun.module_common.temple;

import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.config.CommonConfig;
import com.nj.baijiayun.module_common.mvp.BasePresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public abstract class SearchPresenter<Response extends BaseResponse> extends BasePresenter<SearchView<Response>> {
    private int mPage = 0;

    public void getList(String title) {
        getList2(title);
    }

    public void getList(String title, boolean refresh) {
        getList(title, true, refresh);
    }

    private void getList(String title, final boolean first, final boolean refresh) {
        mView.saveSearchString(title);
        if (refresh) {
            mPage = 0;
        }
        submitRequest(getListObservable(title, mPage + 1), new BaseObserver<Response>() {
            @Override
            public void onPreRequest() {
                if (first) {
                    mView.showLoadView();
                }
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onSuccess(Response response) {
                List list = resultCovertToList(response);
                if ((list == null || list.size() == 0)) {
                    if (first) {
                        mView.showNoDataView();
                    } else {
                        mView.loadFinish(false);
                    }
                } else {
                    mPage++;
                    mView.dataSuccess(list, refresh);
                    mView.loadFinish(list.size() == CommonConfig.PAGE_LIMIT);
                }
            }

            @Override
            public void onFail(Exception e) {
                if (first) {
                    mView.showErrorDataView();
                }
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void getList2(String title) {
        submitRequest(getListObservable(title, 1), new BaseObserver<Response>() {
            @Override
            public void onPreRequest() {
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onSuccess(Response response) {
                List list = resultCovertToList(response);
                mView.dataSuccess2(list);
            }

            @Override
            public void onFail(Exception e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    /**
     * 结果转换 1
     *
     * @param result result
     * @return
     */
    public abstract List resultCovertToList(Response result);

    /**
     * 获取网络请求，如果新增字段 外部传入即可
     */
    public abstract Observable<Response> getListObservable(String title, int page);

}
