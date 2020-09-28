package com.nj.baijiayun.module_public.bean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
@Deprecated
public class PublicMessageWrapperBean {

   private List<PublicMessageReadBean> list;

    public List<PublicMessageReadBean> getList() {
        return list;
    }

    public void setList(List<PublicMessageReadBean> list) {
        this.list = list;
    }
}
