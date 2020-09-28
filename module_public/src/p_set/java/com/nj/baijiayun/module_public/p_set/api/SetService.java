package com.nj.baijiayun.module_public.p_set.api;

import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_public.bean.UserInfoBean;
import com.nj.baijiayun.module_public.p_set.bean.response.SupervisionListRes;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author chengang
 * @date 2020-03-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_set.api
 * @describe
 */
public interface SetService {
    @GET("api/app/parentalSupervision")
    Observable<SupervisionListRes> getSupervisionList();

    @POST("api/app/setAuthInfo")
    Observable<BaseResponse<UserInfoBean>> setAuthInfo(@Query("auth_name") String name, @Query("auth_num") String idNumber);
}
