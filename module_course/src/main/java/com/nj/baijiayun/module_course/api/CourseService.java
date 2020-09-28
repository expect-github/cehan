package com.nj.baijiayun.module_course.api;

import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_course.bean.response.AssembleCourseInfoResponse;
import com.nj.baijiayun.module_course.bean.response.AssembleJoinInfoResponse;
import com.nj.baijiayun.module_course.bean.response.AudioVideoTokenResponse;
import com.nj.baijiayun.module_course.bean.response.CalendarCourseResponse;
import com.nj.baijiayun.module_course.bean.response.CalendarResponse;
import com.nj.baijiayun.module_course.bean.response.CourseDetailResponse;
import com.nj.baijiayun.module_course.bean.response.DatumRealFileResponse;
import com.nj.baijiayun.module_course.bean.response.LiveAndPublicCoursePlayResponse;
import com.nj.baijiayun.module_course.bean.response.MyCourseResponse;
import com.nj.baijiayun.module_course.bean.response.MyLearnedDetailResponse;
import com.nj.baijiayun.module_course.bean.response.OutLineResponse;
import com.nj.baijiayun.module_course.bean.response.ShareResponse;
import com.nj.baijiayun.module_course.bean.response.SystemCourseListResponse;
import com.nj.baijiayun.module_course.bean.wx.CourseQrBean;
import com.nj.baijiayun.module_course.bean.CourseLimitBean;
import com.nj.baijiayun.module_public.helper.videoplay.res.BjyTokensRes;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CourseService {


    @GET("api/app/courseInfo/basis_id={basis_id}")
    Observable<CourseDetailResponse> getCourseDetail(@Path("basis_id") int basisId);

    @FormUrlEncoded
    @POST("api/app/courseChapterApp")
    Observable<OutLineResponse> getCourseOutLine(@Field("id") int courseId);


    /**
     * @param type 前端页面筛选项 2直播课, 5点播课, 8音频课, 10图文课, 7面授课, 11会员课
     * @return Observable<MyCourseResponse>
     */
    @GET("api/app/myStudy/{type}?type=open")
    Observable<MyCourseResponse> getMyCourse(@Path("type") int type);

    @GET("api/app/myStudy/{type}?type=hidden")
    Observable<MyCourseResponse> getMyHideCourse(@Path("type") int type);


    @GET("api/app/myStudy/course/{course_id}")
    Observable<MyLearnedDetailResponse> getMyLearnedDetail(@Path("course_id") int courseId);

    /**
     * @param courseId   课程ID 默认传0, 当从课程详情添加会员课程到我的学习时,必填
     * @param orderId    订单ID 默认传0,当从我的订单中加入学习时必填
     * @param joinForm   来源 1.会员课程详情 2.我的订单
     * @param courseType 课程类型
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/joinStudy")
    Observable<BaseResponse> joinLearnedList(@Field("course_basis_id") int courseId,
                                             @Field("order_id") int orderId,
                                             @Field("join_from") int joinForm,
                                             @Field("course_type") int courseType);


    @FormUrlEncoded
    @POST("api/app/order/downOrder")
    Observable<BaseResponse> freeCourseJoinLearnedList(@Field("shop_id") int courseId, @Field("type") int courseType);


    @DELETE("api/app/myStudy/course/{course_id}")
    Observable<BaseResponse> removeLearnedCourse(@Path("course_id") int courseId);

    /**
     * @param grade    星级 （1,2,3,4,5）
     * @param content  评论内容
     * @param courseId 课程id
     * @param type     评论类型 1课程 3图书 4.文库 5会员
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/myStudy/comment")
    Observable<BaseResponse> addCourseComment(
            @Field("grade") int grade,
            @Field("content") String content,
            @Field("course_id") int courseId,
            @Field("type") int type);

    @GET("api/app/getPlayToken/video_id={video_id}/course_id={course_id}")
    Observable<AudioVideoTokenResponse> getPlayToken(@Path("video_id") int videoId, @Path("course_id") int courseId);


    @GET("api/app/getPcRoomCode/course_id={course_id}/chapter_id={chapter_id}")
    Observable<LiveAndPublicCoursePlayResponse> getLiveAddressInfo(@Path("course_id") int courseId, @Path("chapter_id") int chapterId);


    @FormUrlEncoded
    @POST("api/app/sysCourseList")
    Observable<SystemCourseListResponse> getSystemCourseList(@Field("id") int courseId);


    @GET("api/app/share/{course_basis_id}")
    Observable<ShareResponse> getShareData(@Path("course_basis_id") int courseId, @Query("source") int form);

    @FormUrlEncoded
    @POST("api/app/appStudentRoomCode")
    Observable<BjyTokensRes> getBjyVideoToken(@Field("chapter_id") String sectionId, @Field("type") int type, @Field("mobile") String phone);

    @FormUrlEncoded
    @POST("api/app/appStudentRoomCode")
    Observable<BjyTokensRes> getBjyVideoToken(@Field("chapter_id") String sectionId, @Field("system_course_id") String systemCourseId);

    @FormUrlEncoded
    @POST("api/app/appStudentRoomCode")
    Observable<BjyTokensRes> getBjyVideoToken(@Field("chapter_id") String sectionId);


    @GET("api/app/study/schedule")
    Observable<CalendarResponse> getCalendar(@Query("date") String month);

    @GET("api/app/study/live/{time}")
    Observable<CalendarCourseResponse> getCalendarCourse(@Path("time") long timeStamp);

    /**
     * @param systemCourseId mSystemCourseId 为了给后台判断的拼团的
     */
    @GET("api/app/chapterDatum/{datum_id}/{num}")
    Observable<DatumRealFileResponse> getDatumFile(@Path("datum_id") int datumId, @Path("num") int num, @Query("system_course_id") int systemCourseId);

    @GET("api/app/spellGroup/{spell_id}")
    Observable<AssembleJoinInfoResponse> getAssembleJoinInfo(@Path("spell_id") int spellId);

    @GET("api/app/spellGroupInfo/{commodity_id}/course")
    Observable<AssembleCourseInfoResponse> getAssembleCourseInfo(@Path("commodity_id") int courseId);


    @GET("api/app/checkSpell/{spell_id}/{group_id}")
    Observable<BaseResponse> checkAssembleJoin(@Path("spell_id") int spellId, @Path("group_id") int groupId);

    @GET("api/app/checkSpell/0/{group_id}")
    Observable<BaseResponse> checkAssembleOpen(@Path("commodity_id") int groupId);

    @GET("api/app/myStudy/courseQrcode/{course_basis_id}")
    Observable<BaseResponse<CourseQrBean>> getCourseQr(@Path("course_basis_id") int id);


    @GET("api/app/course/limit/visit/{id}")
    Observable<BaseResponse<CourseLimitBean>> getCourseLimit(@Path("id") int courseId);

    @DELETE("api/app/myStudy/course/{course_id}")
    Observable<BaseResponse> hideOrRecoverCourse(@Path("course_id") int courseId);


}
