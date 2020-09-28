package com.nj.baijiayun.module_main.api;

import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_main.bean.HomeBottomTabWrapperBean;
import com.nj.baijiayun.module_main.bean.NavBean;
import com.nj.baijiayun.module_main.bean.res.CourseClassifyResponse;
import com.nj.baijiayun.module_main.bean.res.CourseTypeResponse;
import com.nj.baijiayun.module_main.bean.res.DistributeApplyResponse;
import com.nj.baijiayun.module_main.bean.res.HomeBannerResponse;
import com.nj.baijiayun.module_main.bean.res.HomePageResponse;
import com.nj.baijiayun.module_main.bean.res.IntegralResponse;
import com.nj.baijiayun.module_main.bean.res.SignResponse;
import com.nj.baijiayun.module_main.bean.res.UserCenterResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.api
 * @describe
 */
public interface MainService {


    @GET("api/app/banner")
    Observable<HomeBannerResponse> getBanner();


    @GET("api/app/courseClassify")
    Observable<CourseClassifyResponse> getCourseClassify();

    @GET("api/app/get/config/course_type")
    Observable<CourseTypeResponse> getCourseType();

    @GET("api/app/getUCenterInfo")
    Observable<UserCenterResponse> getUserCenterInfo();


    @GET("api/app/recommend/appIndex")
    Observable<HomePageResponse> getHomePageIndex();

    @GET("api/user/totalIntegral/{user_id}")
    Observable<IntegralResponse> getIntegral(@Path("user_id") int uid);

    @GET("api/app/user/integral/isSign")
    Observable<SignResponse> getSign();

    @GET("api/app/nav")
    Observable<BaseResponse<List<NavBean>>> getHomePageNav();

    @GET("api/app/nav/bottom")
    Observable<BaseResponse<HomeBottomTabWrapperBean>> getHomeBottomNav();

    @GET("api/app/distribute/apply/result")
    Observable<DistributeApplyResponse>getApplyInfo();


}
