package com.nj.baijiayun.module_common.temple;

import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.config.CommonConfig;
import com.nj.baijiayun.module_common.mvp.BasePresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-06-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.temple
 * @describe
 */
public abstract class AbstractListPresenter<Response extends BaseResponse> extends BasePresenter<IMultiRecyclerView<Response>> {

    private int mPage = 0;

    public void getList() {
        getList(true, true);
    }

    public void getList(boolean refresh) {
        getList(false, refresh);
    }

    private void getList(final boolean first, final boolean refresh) {
        if (refresh) {
            mPage = 0;
        }
        submitRequest(getListObservable(mPage + 1),new BaseObserver<Response>() {
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
                    if (refresh) {
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
                mView.loadFinish(false);

                if (first) {
                    mView.showErrorDataView();
                }
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
    public abstract Observable<Response> getListObservable(int page);
}
