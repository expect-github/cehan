package com.nj.baijiayun.module_common.base;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2019-06-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe
 */
public class BaseListEmptyPresenter extends BaseListEmptyContract.Presenter {
    @Inject
    BaseListEmptyPresenter() {
    }

    @Override
    public List resultCovertToList(BaseResponse result) {
        return null;
    }

    @Override
    public Observable getListObservable(int page) {
        return null;
    }
}
