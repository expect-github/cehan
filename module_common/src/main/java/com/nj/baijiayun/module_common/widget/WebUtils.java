package com.nj.baijiayun.module_common.widget;

import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.ValueCallback;

import java.io.File;
import java.util.List;

/**
 * @author chengang
 * @date 2019-09-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget
 * @describe
 */
public class WebUtils {
    public static void seleteH5File(Intent data, ValueCallback valueCallback) {
        if (valueCallback == null) {
            // todo valueCallback 为空的逻辑
            return;
        }
        try {
            Uri[] results = null;
            String dataString = data.getDataString();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
            }

            if (dataString != null) {
                results = new Uri[]{Uri.parse(dataString)};
                valueCallback.onReceiveValue(results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        valueCallback = null;
    }


    public static void selectH5File(Context context,List<String> datas, ValueCallback valueCallback) {
        if (valueCallback == null) {
            // todo valueCallback 为空的逻辑
            return;
        }
        try {
            Uri[] results = new Uri[datas.size()];
            for (int i = 0; i < datas.size(); i++) {
//                results[i]=getMediaUriFromPath(context,datas.get(i));
                results[i]=Uri.fromFile(new File(datas.get(i)));
            }
            valueCallback.onReceiveValue(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        valueCallback = null;
    }


    public static Uri getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[]{path.substring(path.lastIndexOf("/") + 1)},
                null);

        Uri uri = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                uri = ContentUris.withAppendedId(mediaUri,
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
            }
            cursor.close();
        }
        return uri;
    }


}

