package com.nj.baijiayun.module_common.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.util.Log;

import com.nj.baijiayun.logger.log.Logger;

import java.io.File;

/**
 * @project neixun_android
 * @class name：com.nj.baijiayun.module_download.helper
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019-07-01 20:03
 * @change
 * @time
 * @describe
 */
public class FileOpenHelper {
    public static void open(String filePath, Activity activity) throws FileOpenException {
        String genre = getGenre(filePath);
        String fileType = checkFileType(genre);
        if ("*/*".equals(fileType)) {
            throw new FileOpenException("亲,请登录PC查看");
        }
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory("android.intent.category.DEFAULT");
            Log.e("openFiles: ", file.getPath());
            setIntentDataAndType(activity, intent, fileType, file, false);
            try {
                activity.startActivity(intent);
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }
    }

    public static String getGenre(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);

    }

    private static String checkFileType(String fileType) {
        String type = "*/*";
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (fileType.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
                break;
            }
        }
        return type;
    }


    public static String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {"3gp", "video/3gpp"},
            {"avi", "video/x-msvideo"},
            {"doc", "application/msword"},
            {"docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {"xls", "application/vnd.ms-excel"},
            {"xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {"mp3", "audio/x-mpeg"},
            {"mp4", "video/mp4"},
            {"pdf", "application/pdf"},
            {"ppt", "application/vnd.ms-powerpoint"},
            {"rmvb", "audio/x-pn-realaudio"},
            {"txt", "text/plain"},
            {"wav", "audio/x-wav"},
            {"wma", "audio/x-ms-wma"},
            {"wmv", "audio/x-ms-wmv"},
            {"wps", "application/vnd.ms-works"},
            {"", "*/*"}
    };

    public static class FileOpenException extends Exception {
        public FileOpenException() {
            super();
        }

        public FileOpenException(String message) {
            super(message);
        }

        public FileOpenException(String message, Throwable cause) {
            super(message, cause);
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
