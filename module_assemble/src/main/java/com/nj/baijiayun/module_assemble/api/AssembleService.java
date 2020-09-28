package com.nj.baijiayun.module_assemble.api;

import com.nj.baijiayun.module_assemble.bean.response.AssembleListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.api
 * @describe
 */
public interface AssembleService {

    String TYPE_BOOK="book";
    String TYPE_COURSE="course";

    @GET("api/app/spell/group")
    Observable<AssembleListResponse> getAssembleList(@Query("type")String type,@Query("page")int page);
}
