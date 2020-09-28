package com.nj.baijiayun.module_public.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nj.baijiayun.basic.utils.ApplicationUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.BuildConfig;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.SystemWebConfigBean;
import com.nj.baijiayun.module_public.helper.config.ConfigLoaderManager;
import com.nj.baijiayun.module_public.helper.update.UpdateHelper;
import com.nj.baijiayun.module_public.widget.UserItemView;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;

import java.text.MessageFormat;

public class AboutActivity extends BaseAppActivity {


    private TextView mVersionTv;
    private UserItemView mItemViewCommentScore;
    private UserItemView mItemViewVersionCheck;
    private ImageView mAppIconIv;
    private TextView mCopyrightTv;
    private TextView mRecordNoTv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mVersionTv = findViewById(R.id.tv_version);
        mItemViewCommentScore = findViewById(R.id.item_view_comment_score);
        mItemViewVersionCheck = findViewById(R.id.item_view_version_check);
        mAppIconIv = findViewById(R.id.iv_app_icon);
        mCopyrightTv = findViewById(R.id.tv_copyright);
        mRecordNoTv = findViewById(R.id.tv_record_no);

    }

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    protected void registerListener() {
        mItemViewCommentScore.setOnItemClickListener(new UserItemView.OnItemClickListener() {
            @Override
            public void onItemClick(String path, View v) {
                goMarket();
            }

            private void goMarket() {
                try {

                    String packageName = getPackageName();
                    if (BuildConfig.DEBUG) {
                        packageName = "com.tencent.qqmusic";
                    }
                    Uri uri = Uri.parse("market://details?id=" + packageName);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        mItemViewVersionCheck.setOnItemClickListener((path, v) -> checkVersion());


    }

    private void checkVersion() {

        UpdateHelper.checkUpdate(new UpdateCheckCB() {
            @Override
            public void onCheckStart() {

            }

            @Override
            public void hasUpdate(Update update) {

            }

            @Override
            public void noUpdate() {
                try {
                    BJYDialogHelper.buildMDDialog(getActivity())
                            .setTitleTxt("当前版本")
                            .setContentTxt(UpdateHelper.getApkVersion(getActivity()) + "当前版本为最新版本")
                            .setPositiveTxt(R.string.common_confirm)
                            .show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCheckError(Throwable t) {
                Log.e("版本更新失败",t.toString());
                ToastUtil.shortShow(BaseApp.getInstance(), "检查更新失败");
            }

            @Override
            public void onUserCancel() {

            }

            @Override
            public void onCheckIgnore(Update update) {

            }
        });
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        setPageTitle(getString(R.string.public_activity_title_about) + ApplicationUtils.getAppName(this));
        SystemWebConfigBean systemWebConfigBean = ConfigLoaderManager.get().getSystemConfigLoader().get();
        ImageLoader.with(this).load(systemWebConfigBean.getMobileLogo()).into(mAppIconIv);
        mCopyrightTv.setText(systemWebConfigBean.getCopyright());
        mRecordNoTv.setText(systemWebConfigBean.getRecordNo());
        mVersionTv.setText(MessageFormat.format("版本号：{0}", ApplicationUtils.getVersionName(this)));
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_about;
    }
}
