package com.nj.baijiayun.module_public.api;

import com.google.gson.JsonElement;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_public.bean.PublicOauthBean;
import com.nj.baijiayun.module_public.bean.PublicOtherSettingBean;
import com.nj.baijiayun.module_public.bean.PublicShareAvaiableBean;
import com.nj.baijiayun.module_public.bean.response.AppConfigResponse;
import com.nj.baijiayun.module_public.bean.response.CouponGetResponse;
import com.nj.baijiayun.module_public.bean.response.CourseListRes;
import com.nj.baijiayun.module_public.bean.response.LibraryDetailResponse;
import com.nj.baijiayun.module_public.bean.response.LibraryDownloadResponse;
import com.nj.baijiayun.module_public.bean.response.LoginRes;
import com.nj.baijiayun.module_public.bean.response.MessageResponse;
import com.nj.baijiayun.module_public.bean.response.PayResponse;
import com.nj.baijiayun.module_public.bean.response.PublicContractResponse;
import com.nj.baijiayun.module_public.bean.response.PublicCourseClassifyResponse;
import com.nj.baijiayun.module_public.bean.response.PublicUploadRes;
import com.nj.baijiayun.module_public.bean.response.QrCodeImgResponse;
import com.nj.baijiayun.module_public.bean.response.ShareImgResponse;
import com.nj.baijiayun.module_public.bean.response.ShareTemplateResponse;
import com.nj.baijiayun.module_public.bean.response.SystemWebConfigResponse;
import com.nj.baijiayun.module_public.bean.response.UserInfoRes;
import com.nj.baijiayun.module_public.consts.ConstsProtocol;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;
import com.nj.baijiayun.module_public.helper.videoplay.res.BjyTokensRes;
import com.nj.baijiayun.module_public.helper.videoplay.res.OneToOneTokenRes;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author chengang
 * @date 2019-06-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_user.api
 * @describe
 */
public interface PublicService {


    @GET("api/app/courseClassify")
    Observable<PublicCourseClassifyResponse> getCourseClassify();


    /**
     * @param phone
     * @param pwd
     * @return
     */
    @POST("api/app/login")
    Observable<LoginRes> login(@Query("mobile") String phone, @Query("password") String pwd, @Query("type") int type);

    @POST("api/app/login?client=1")
    Observable<LoginRes> loginByCode(@Query("mobile") String phone, @Query("sms_code") String smsCode, @Query("type") int type);

    @POST("api/app/login")
    Observable<LoginRes> loginByThirdPlatform(@Query("mobile") String phone, @Query("sms_code") String smsCode, @Query("openid") String openid, @Query("type") int type);

    @POST("api/app/login")
    Observable<LoginRes> loginByThirdPlatform(@Query("mobile") String phone, @Query("sms_code") String smsCode, @Query("openid") String openid, @Query("type") int type, @Query("password") String pwd);


    @POST("api/app/findpass")
    Observable<BaseResponse> findPwd(@Query("mobile") String phone,
                                     @Query("new_pass") String newPwd,
                                     @Query("confirm_new_pass") String confirmNewPwd,
                                     @Query("code") String code);


    /**
     * 设置密码跟 找回密码都调用这个
     */

//    @Deprecated
    @POST("api/app/password")
    Observable<BaseResponse> changePwd(@Query("mobile") String phone,
                                       @Query("password") String newPwd,
                                       @Query("sms_code") String code);


    @POST("api/app/smsCode")
    Observable<BaseResponse> sendCode(@Query("mobile") String mobile, @Query("sms_type") String sms_type);


    @Multipart
    @POST("api/app/public/img")
    Observable<PublicUploadRes> uploadPics(@Part MultipartBody.Part file);


    /**
     * 1:最新 2:最热 3:价格升序 4:价格降序
     *
     * @param courseType 课程类型 10图文, 2大班课, 3小班课, 5视频课,8音频课, 9系统课
     * @param classifyId 课程分类ID
     * @param attrValIds 属性ID，多个之间用逗号连接
     * @param isVip      1 会员课
     * @param keyWords   课程名称搜索
     * @param orderBy    1:最新 2:最热 3:价格升序 4:价格降序
     * @param page       页
     * @return
     */
    @GET("api/app/courseBasis")
    Observable<CourseListRes> getCourseList(@Query("course_type") int courseType,
                                            @Query("classify_id") int classifyId,
                                            @Query("attr_val_id") String attrValIds,
                                            @Query("is_vip") int isVip,
                                            @Query("keywords") String keyWords,
                                            @Query("order_by") int orderBy,
                                            @Query("page") int page);

    @GET("api/app/courseBasis")
    Observable<CourseListRes> getCourseList(@Query("page") int page);

    /**
     * @param courseId 课程ID
     * @param type     类型 1 课程收藏 2 老师ID
     * @return BaseResponse
     */

    @FormUrlEncoded
    @POST("api/app/collect")
    Observable<BaseResponse<JsonElement>> collect(@Field("course_basis_id") int courseId, @Field("teacher_id") int teacherId, @Field("type") int type);


    @POST("api/app/collect")
    Observable<BaseResponse<JsonElement>> collect(@QueryMap HashMap<String, String> params);


    @PUT("api/app/collect/cancel/{id}/{type}")
    Observable<BaseResponse<JsonElement>> cancelCollect(@Path("id") int id, @Path("type") int type);


    @GET("api/app/userGetCoupon/{id}")
    Observable<CouponGetResponse> getCoupon(@Path("id") int id);

    /**
     * @param orderNumber 订单编号
     * @param type        支付类型: ali支付宝 wx微信
     * @return PayResponse
     */
    @FormUrlEncoded
    @POST("api/app/pay")
    Observable<PayResponse> getPayResponse(@Field("order_number") String orderNumber, @Field("type") String type);


    @GET("api/app/userInfo")
    Observable<UserInfoRes> getUserInfoNew();


    @FormUrlEncoded
    @POST("api/app/appStudentRoomCode")
    Observable<BjyTokensRes> getBjyVideoToken(@Field("chapter_id") String sectionId);

    /**
     * 公开课 chapterId 为null
     *
     * @param chapterId
     * @param courseId
     * @return
     */

    @FormUrlEncoded
    @POST("api/app/appStudentRoomCode")
    Observable<BjyTokensRes> getBjyVideoTokenPublicCourse(@Field("chapter_id") String chapterId, @Field("periods_id") String courseId);

    @POST("api/app/message/classifyMessage")
    Observable<MessageResponse> getMessageUnRead();


    @GET("api/app/oto/getLiveRoomCode/{oto_course_id}/1")
    Observable<OneToOneTokenRes> getBjyOneToOneToken(@Path("oto_course_id") String courseId);

    /**
     * a
     *
     * @return Observable
     */
    @GET("api/app/shareTemplate/list")
    Observable<ShareTemplateResponse> getShareTemplateList();

    @POST("api/app/shareTemplate/sharePic")
    Observable<QrCodeImgResponse> getQrCodeImg(@Query("template_id") int templateId, @Query("url") String url);

    @POST("api/app/getBase64")
    Observable<QrCodeImgResponse> getQrCodeImg(@Query("url") String url);

    @GET("api/contract/get")
    Observable<BaseResponse> getProtocol(@Query("params") String params);

    @GET("api/app/config/" + ConstsProtocol.APP_REMINDER)
    Observable<PublicContractResponse> getAppReminder();

    @GET("api/app/config/project_common_setting")
    Observable<AppConfigResponse> getAppConfig();

    @GET("api/config/system_webConfig")
    Observable<SystemWebConfigResponse> getSystemConfig();


    @GET("{api}")
    Observable<ShareImgResponse> getShareImg(@Path(value = "api", encoded = true) String api);

    @GET("api/app/library/download/{library_id}")
    Observable<LibraryDownloadResponse> getLibraryDownloadUrl(@Path("library_id") int libraryId);

    @GET("api/app/library/detail/{id}")
    Observable<LibraryDetailResponse> getLibraryDetail(@Path("id") int id);

    /**
     * @param eventType 触发事件 1 注册 2 完善资料 3 提交作业 4 订单评价 5 收藏 6 分享 7 成功交易
     * @param eventMark 如果是分享 md5(分享链接) 如果是收藏 问题/帖子 需要 type_collect_id
     *                  返回示例
     * @return
     */
    @GET("api/app/integral/run")
    Observable<BaseObserver> getIntegral(@Query("event_type") int eventType, @Query("event_mark") String eventMark);


    @GET("api/app/integral/run?event_type=6")
    Observable<BaseObserver> getShareIntegral(@Query("event_mark") String eventMark);


    @GET("api/app/share/content/{id}")
    Observable<BaseResponse<ShareInfo>> getShareInfo(@Path("id") String id, @Query("type") String type, @Query("url") String url, @Query("source") int source);

    @GET("api/app/verify_code")
    Observable<BaseResponse> verifySmsCode(@Query("code") String code, @Query("sms_type") String smsType);

    /**
     * 这块写成通用的比较好，后台先写好了接口没办法，只能做验证
     */
    @FormUrlEncoded
    @POST("api/app/checkCancelAccount/code")
    Observable<BaseResponse> verifyLogOffSmsCode(@Field("sms_code") String code);

    @PUT("api/app/cancel/account")
    Observable<BaseResponse> logoff(@Query("sms_code") String code);

    @POST("api/app/remember/video/time")
    Observable<BaseResponse> uploadVideoRecord(@Query("chapter_id") String itemId, @Query("time") int time);

    @GET("api/app/video/time/{chapterId}")
    Observable<BaseResponse<Integer>> getVideoRecord(@Path("chapterId") String itemId);

    @GET("api/app/systemSet/available/oauthConfig")
    Observable<BaseResponse<PublicOauthBean>> getOauthConfig();

    @GET("api/app/systemSet/available/shareConfig")
    Observable<BaseResponse<PublicShareAvaiableBean>> getShareConfig();

    @GET("api/app/config/other_setting")
    Observable<BaseResponse<PublicOtherSettingBean>> getOtherSettingConfig();

    @POST("api/app/course/playBack")
    Observable<BaseResponse> uploadPlayBack(@Query("course_basis_id") int courseId,
                                            @Query("course_periods_id") int periodsId,
                                            @Query("total_time") int total,
                                            @Query("current_time") int cur);

    @GET("api/app/config/{params}")
    Observable<BaseResponse<Object>> getConfigByParams(@Path("params")String params);



}
