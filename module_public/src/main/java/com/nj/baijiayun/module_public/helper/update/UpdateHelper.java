package com.nj.baijiayun.module_public.helper.update;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_public.R;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.creator.ApkFileCreator;
import org.lzh.framework.updatepluginlib.creator.DialogCreator;
import org.lzh.framework.updatepluginlib.creator.InstallCreator;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.HttpMethod;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


/**
 * @project zywx_android
 * @class name：www.baijiayun.module_common.helper
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/01/02 19:36
 * @change
 * @time
 * @describe api "org.lzh.nonview.updateplugin:UpdatePlugin:2.4"
 */
public class UpdateHelper {
    /**
     * 配置更新参数
     *
     * @param url     检查更新地址
     * @param context c
     */
    public static void configUpdateInfo(String url, final Context context) {
        Log.e("版本更新地址：",url);
        CheckEntity ch = new CheckEntity();
        ch.setMethod(HttpMethod.GET);
        ch.setUrl(url);
        UpdateConfig.getConfig()
                .checkEntity(ch)
                .updateDialogCreator(new DialogCreator() {
                    @Override
                    public Dialog create(final Update update, Activity context) {
                        return getUpdateTipDialogNew(this, update, context);
                    }

                })
                .downloadDialogCreator((update, activity) -> getDownloadDialog(activity))
                .fileCreator(new ApkFileCreator() {
                    @Override
                    public File create(String versionName) {
                        File cacheDir = getCacheDir();
                        cacheDir.mkdirs();
                        return new File(cacheDir, "update_v_" + versionName + ".apk");
                    }

                    private File getCacheDir() {
                        Context context = UpdateConfig.getConfig().getContext();
                        File cacheDir = context.getExternalCacheDir();
                        if (cacheDir == null) {
                            cacheDir = context.getCacheDir();
                        }
                        cacheDir = new File(cacheDir, "update");
                        return cacheDir;
                    }
                })
                .installDialogCreator(new InstallCreator() {
                    @Override
                    public Dialog create(final Update update, final String path, Activity activity) {
                        return getInstallDialog(update, path, activity);
                    }

                    @Nullable
                    private Dialog getInstallDialog(final Update update, final String path, Activity activity) {
                        if (activity == null || activity.isFinishing()) {
                            return null;
                        }
                        String updateContent = activity.getText(org.lzh.framework.updatepluginlib.R.string.update_version_name)
                                + ": " + update.getVersionName() + "\n\n\n"
                                + update.getUpdateContent();
                        final CommonMDDialog dialog = BJYDialogHelper.buildMDDialog(activity)
                                .setTitleTxt(org.lzh.framework.updatepluginlib.R.string.install_title)
                                .setContentTxt(updateContent)
                                .setPositiveTxt(org.lzh.framework.updatepluginlib.R.string.install_immediate);
                        dialog.setOnPositiveClickListener(() -> {
                            if (!update.isForced()) {
                                SafeDialogOper.safeDismissDialog(dialog);
                                InstallHelper.installApk(context, path);
                            } else {
                                InstallHelper.installApk(context, path);
//                                Process.killProcess(Process.myPid());
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        return dialog;
                    }
                })
                .updateChecker(update -> {
                    try {
                        isNeedUpgrade = checkVersionCode(update.getVersionCode(), getApkCode(context));
                        return isNeedUpgrade;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String httpResponse) {
                        try {
                            Logger.d("VersionUpdate" + httpResponse);
                            AppUpdateBean bean = new Gson().fromJson(httpResponse, AppUpdateBean.class);
                            Update update = new Update(httpResponse);
                            // 此apk包的更新时间
                            update.setUpdateTime(System.currentTimeMillis());
                            // 此apk包的下载地址
                            update.setUpdateUrl(bean.getData().getApk_address());

                            // 此apk包的版本号
                            update.setVersionCode((int) bean.getData().getVersion_code());
                            // 此apk包的版本名称
                            update.setVersionName(bean.getData().getVersion_name());
                            // 此apk包的更新内容
                            update.setUpdateContent(bean.getData().getVersion_detail());
                            // 此apk包是否为强制更新
                            update.setForced(bean.getData().isForceUpdate());
                            update.setIgnore(false);
                            return update;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                });
    }

    static boolean isNeedUpgrade = false;

    public static boolean isNeedUpgrade() {
        return isNeedUpgrade;
    }

    private static UpdateDownloadCB getDownloadDialog(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressDrawable(ContextCompat.getDrawable(activity, R.drawable.public_update_progress));
        SafeDialogOper.safeShowDialog(dialog);
        return new UpdateDownloadCB() {
            @Override
            public void onUpdateStart() {
            }

            @Override
            public void onUpdateComplete(File file) {
                SafeDialogOper.safeDismissDialog(dialog);
            }

            @Override
            public void onUpdateProgress(long current, long total) {
                int percent = (int) (current * 1.0f / total * 100);
                dialog.setProgress(percent);
                Logger.e("onUpdateProgress " + current + "total->" + total);

            }

            @Override
            public void onUpdateError(Throwable t) {
                SafeDialogOper.safeDismissDialog(dialog);
            }
        };
    }

    public static boolean checkVersionCode(int newCode, int oldCode) {
        return newCode > oldCode;
    }

    /**
     * 比较apk版本，这里使用VersionName比较格式为1.1.2
     *
     * @param newVersion 新的版本
     * @param oldVersion 旧的版本
     * @return 是否需要更新版本
     */
    private static boolean checkVersion(String newVersion, String oldVersion) {
        String[] split = newVersion.split("\\.");
        String[] split1 = oldVersion.split("\\.");
        for (int i = 0; i < Math.min(split.length, split1.length); i++) {
            int newV = Integer.parseInt(split[i]);
            int oldV = Integer.parseInt(split1[i]);
            if (newV > oldV) {
                return true;
            } else if (oldV > newV) {
                return false;
            }
        }
        return split.length > split1.length;
    }

    /**
     * 比较apk版本，这里使用VersionName比较格式为1.1.2
     *
     * @param newVersion 新的版本
     * @return 是否需要更新版本
     */
    public static boolean checkVersion(Context context, String newVersion) throws PackageManager.NameNotFoundException {
        String apkVersion = getApkVersion(context);
        return checkVersion(newVersion, apkVersion);
    }

    /**
     * 获取apk版本
     */
    public static String getApkVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionName;
    }

    /**
     * 获取apk版本
     */
    public static int getApkCode(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    }

    /**
     * 检查更新
     */
    public static void checkUpdate() {
        checkUpdate(null);
    }

    /**
     * 检查更新
     */
    public static void checkUpdate(UpdateCheckCB updateCheckCB) {
        UpdateBuilder.create()
                .checkCB(updateCheckCB)
                .strategy(new UpdateStrategy() {
                    private Update mUpdate;

                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        this.mUpdate = update;
                        return true;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        return !mUpdate.isForced();
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        return mUpdate.isForced();
                    }
                }).check();
    }

    /**
     * 检查更新
     */
    public static void checkVersionUpgrade(Context context, VerionCallback callback) {
        UpdateBuilder.create().updateChecker(new UpdateChecker() {
            @Override
            public boolean check(Update update) {

                if (callback != null) {
                    try {
                        callback.call(checkVersionCode(update.getVersionCode(), getApkCode(context)));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        }).check();
    }


    public interface VerionCallback {
        void call(boolean needUpgrade);
    }

    /**
     * 更新提示对话框
     */
    @NonNull
    private static CommonMDDialog getUpdateTipDialog(final DialogCreator creator, final Update update, Activity context) {
        final CommonMDDialog commonMDDialog = BJYDialogHelper.buildMDDialog(context)
                .setTitleTxt("有新版本了")
                .setContentTxt(update.getUpdateContent())
                .setPositiveTxt("立即更新");
        commonMDDialog.setOnPositiveClickListener(() -> {
            creator.sendDownloadRequest(update);
            SafeDialogOper.safeDismissDialog((Dialog) commonMDDialog);
        });
        if (!update.isForced()) {
            commonMDDialog.setNegativeTxt("暂不更新");
        }
        commonMDDialog.setCancelable(!update.isForced());
        commonMDDialog.setCanceledOnTouchOutside(!update.isForced());
        return commonMDDialog;
    }

    /**
     * 更新提示对话框
     */
    @NonNull
    private static Dialog getUpdateTipDialogNew(final DialogCreator creator, final Update update, Activity context) {
      final UpgradeDialog upgradeDialog = new UpgradeDialog(context);
        upgradeDialog.setUpgradeContent(update.getUpdateContent())
                .setUpgradeVersion(update.getVersionName())
                .needShowClose(!update.isForced())
                .setUpgradeOnClick(v -> {
                    creator.sendDownloadRequest(update);
                    SafeDialogOper.safeDismissDialog(upgradeDialog);

                });
        upgradeDialog
               .show();
        return upgradeDialog;
    }


    public static class UpgradeDialog extends Dialog {

        private TextView mVersionNameTv;
        private TextView mContentTv;
        private Button mBtnUpgrade;
        private View mViewLine;
        private ImageView mCloseIv;

        public UpgradeDialog(@NonNull Context context) {
            super(context, R.style.BasicCommonDialog);
            setContentView(R.layout.public_dialog_upgrade);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
            mVersionNameTv = findViewById(R.id.tv_version_name);
            mContentTv = findViewById(R.id.tv_content);
            mBtnUpgrade = findViewById(R.id.btn_upgrade);
            mViewLine = findViewById(R.id.view_line);
            mCloseIv = findViewById(R.id.iv_close);
            mContentTv.setMovementMethod(ScrollingMovementMethod.getInstance());

            mCloseIv.setOnClickListener(v -> dismiss());
        }

        public UpgradeDialog setUpgradeVersion(String versionName) {
            mVersionNameTv.setText(versionName);
            return this;
        }

        public UpgradeDialog setUpgradeContent(String content) {
            mContentTv.setText(Html.fromHtml(content));
            return this;
        }

        public UpgradeDialog setUpgradeOnClick(View.OnClickListener onClickListener) {
            mBtnUpgrade.setOnClickListener(onClickListener);
            return this;
        }

        public UpgradeDialog needShowClose(boolean show) {
            mCloseIv.setVisibility(show ? View.VISIBLE : View.GONE);
            mViewLine.setVisibility(show ? View.VISIBLE : View.GONE);
            return this;
        }


    }


}
