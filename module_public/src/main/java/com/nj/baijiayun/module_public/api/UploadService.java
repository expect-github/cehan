package com.nj.baijiayun.module_public.api;

import com.nj.baijiayun.module_public.bean.response.PublicUploadRes;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author chengang
 * @date 2019-06-28
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.api
 * @describe
 */
public interface UploadService {

    @Multipart
    @POST("api/app/public/img")
    Observable<PublicUploadRes> uploadPics(@Part MultipartBody.Part file);

}
