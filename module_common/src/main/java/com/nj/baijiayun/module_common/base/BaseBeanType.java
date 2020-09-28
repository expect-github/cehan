package com.nj.baijiayun.module_common.base;

/**
 * @author chengang
 * @date 2019-06-18
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe 用来区分绑定的实体布局
 */
@Deprecated
public interface BaseBeanType {
    void setBeanType(int type);

    int getBeanType();
}
