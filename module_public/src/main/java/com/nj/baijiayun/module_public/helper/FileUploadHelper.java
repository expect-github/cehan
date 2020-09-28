package com.nj.baijiayun.module_public.helper;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author chengang
 * @date 2019-06-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class FileUploadHelper {

    public static MultipartBody.Part getMultipartBodyByFile(File file) {
//        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestFile);
    }

}
