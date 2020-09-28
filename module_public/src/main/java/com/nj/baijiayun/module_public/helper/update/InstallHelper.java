package com.nj.baijiayun.module_public.helper.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.util.List;

import androidx.core.content.FileProvider;

/**
 * @author chengang
 * @date 2019/5/14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication
 * @describe
 */
public class InstallHelper {

    private static final int REQUEST_INSTALL = 0x000010;

    public static void installApk(Context context, String filename) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        File pluginFile = new File(filename);

        String var4 = "chmod 777 " + pluginFile.getAbsolutePath();
        try {
            Runtime.getRuntime().exec(var4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            // Adaptive with api version 24+
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(filename));
//            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(pluginFile);
        }
        intent.setDataAndType(uri, type);
        //查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置setDataAndType的方法之后
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        //然后全部授权
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, REQUEST_INSTALL);
        }
        context.startActivity(intent);
    }

}
