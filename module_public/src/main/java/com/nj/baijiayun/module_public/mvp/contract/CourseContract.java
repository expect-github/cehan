package com.nj.baijiayun.module_public.mvp.contract;

import com.nj.baijiayun.module_common.temple.AbstractListPresenter;
import com.nj.baijiayun.module_public.bean.response.CourseListRes;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.contract
 * @describe
 */
public interface CourseContract {


    abstract class Presenter extends AbstractListPresenter<CourseListRes> {

        public abstract void setOrderBy(int orderBy);

        public abstract void setCourseType(int courseType);

        public abstract void setAttrValId(String attrValId);

        public abstract void setVip(int vip);

        public abstract void clearSelect();

    }
}
