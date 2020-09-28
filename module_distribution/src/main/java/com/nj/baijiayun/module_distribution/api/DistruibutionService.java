package com.nj.baijiayun.module_distribution.api;

import com.nj.baijiayun.module_distribution.bean.res.DtbBookListResponse;
import com.nj.baijiayun.module_distribution.bean.res.DtbCourseListResponse;
import com.nj.baijiayun.module_distribution.bean.res.DtbListResponse;
import com.nj.baijiayun.module_distribution.consts.GoodsType;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.api
 * @describe
 */
public interface DistruibutionService {


//    api/distribute/item


    @GET("api/distribute/item")
    Observable<DtbListResponse> getDtbList();


    @GET("api/distribute/item/all/"+ GoodsType.TYPE_COURSE)
    Observable<DtbCourseListResponse> getDtbCourseListByFilter(@Query("course_type") int courseType, @Query("price") String sortPrice, @Query("commission") String sortCommission, @Query("sales_num") String sortSalesNum,@Query("page")int page);

    @GET("api/distribute/item/all/"+ GoodsType.TYPE_BOOK)
    Observable<DtbBookListResponse> getDtbBookListByFilter(@Query("price") String sortPrice, @Query("commission") String sortCommission, @Query("sales_num") String sortSalesNum,@Query("page")int page);



//    www.bjy.com/api/distribute/item/all/{type}
//    type	是	int	0:课程 1：图书
//    course_type	否	int	课程类型
//    price	否	string	价格排序 desc/asc
//    commission	否	string	佣金排序 desc/asc
//    sales_num	否	string	销量排序 desc/asc

}
