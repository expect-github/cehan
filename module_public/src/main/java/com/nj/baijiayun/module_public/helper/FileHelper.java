package com.nj.baijiayun.module_public.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nj.baijiayun.basic.permissions.PerMissionsManager;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.downloader.utils.MD5Util;
import com.nj.baijiayun.logger.log.Logger;
import com.tencent.smtt.sdk.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-09-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class FileHelper {

    public static void saveImg(Activity activity, String url) {
        Glide.with(activity).downloadOnly().load(url).into(new CustomTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                saveGlideImage(activity, resource, MD5Util.encrypt(url) + "." + FileHelper.getFileType(url));
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }


    @SuppressLint("CheckResult")
    public static void saveGlideImage(Activity activity, File file, String saveImgName) {

        PerMissionsManager.newInstance().getUserPerMissions(activity, is -> {
            if (!is) {
                ToastUtil.shortShow(activity,"您需要打开存储权限");
                return;
            }
            Observable.create((ObservableOnSubscribe<File>) emitter -> {
                String saveDir = Environment.getExternalStorageDirectory() + "/" + activity.getPackageName() + "/images";
                if (!new File(saveDir).exists()) {
                    new File(saveDir).mkdirs();
                }

                File target = new File(saveDir, saveImgName);
                copy(file, target);
                emitter.onNext(target);
            }).observeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
                @Override
                public void accept(File o) throws Exception {
                    Uri uri = Uri.fromFile(o);

                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    ToastUtil.shortShow(activity, "保存至相册" + o.getAbsolutePath());
                }
            });


        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);


    }

    @SuppressLint("CheckResult")
    public static void saveGlideFile(Context context, File file, String downloadUrl, Consumer<File> consumer) {
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
            File files = new File(context.getCacheDir(), "files");
            if (!files.exists()) {
                files.mkdirs();
            }
            String saveDir = files.getPath();
            if (!new File(saveDir).exists()) {
                new File(saveDir).mkdirs();
            }
            File target = new File(saveDir, downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1));
            copy(file, target);
            emitter.onNext(target);
        }).observeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> null)
                .subscribe(consumer);

    }

    public static String getFileName(String url) {
        if (url == null) {
            return "";
        }
        if (url.contains("/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return "";
    }

    public static String getFileParent(String url) {
        if (url.contains("/")) {
            return url.substring(0, url.lastIndexOf("/"));
        }
        return "";
    }


    private static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = paramString.substring(i + 1);
        return str;
    }

    public static String getMimeType(String filePath) {
        String mime = "*/*";
        if (filePath != null) {
            try {
                String ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
                mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
            } catch (Exception e) {
                e.printStackTrace();
                return mime;
            }
        }
        return mime;
    }


    public static String getFileExt(String filePath) {
        String ext = "";
        try {
            ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
        } catch (Exception ee) {
            Logger.e(ee.getMessage());
        } finally {
            if (StringUtils.isEmpty(ext)) {
                ext = getFileType(filePath);
            }
        }

        return ext;
    }

    public static void openFileByOtherApp(String filePath, Activity activity) {
        openFileByOtherAppFroResult(filePath, activity, 0);
    }

    public static void openFileByOtherAppFroResult(String filePath, Activity activity, int requestCode) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            setIntentDataAndType(activity, intent, getMimeType(filePath), file, false);
            try {
                activity.startActivityForResult(intent, requestCode);
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        } else {
            ToastUtil.shortShow(activity, "文件不存在");
        }
    }

    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    public static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = FileProvider.getUriForFile(context,
                context.getPackageName() + ".fileprovider",
                file);
        return fileUri;
    }


    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

}
