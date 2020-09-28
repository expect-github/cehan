package com.nj.baijiayun.module_public.temple;

import com.nj.baijiayun.module_common.temple.AbstractListFragment;
import com.nj.baijiayun.module_common.temple.AbstractListPresenter;

/**
 * @author chengang
 * @date 2019-09-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */
public abstract class BaseSimpleListFragment extends AbstractListFragment {
    @Override
    protected boolean needAutoInject() {
        return false;
    }

    @Override
    public void initInject() {
        super.initInject();
        mPresenter=createPresenter();
    }

    protected abstract AbstractListPresenter createPresenter();

    @Override
    public void processLogic() {

    }


}
