package com.nj.baijiayun.module_common.demo;

import android.util.Log;

import com.nj.baijiayun.module_common.mvp.BaseView;

import javax.inject.Inject;

/**
 * @author chengang
 * @date 2019-06-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.demo
 * @describe
 */
public class DemoPresenter extends DemoContract.Presenter {
    private String id;
    @Inject
    DemoPresenter(@Remote String taskId,@Local String taskId2) {
        this.id=taskId+taskId2;
    }

    @Override
    public void test()
    {
        Log.e("DemoPresenter",this.id+"----");
    }


    @Override
    public void takeView(BaseView mView) {
        this.mView=mView;
    }
}
