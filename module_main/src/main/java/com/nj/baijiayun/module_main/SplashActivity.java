package com.nj.baijiayun.module_main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.nj.baijiayun.basic.network.NetType;
import com.nj.baijiayun.basic.network.NetWork;
import com.nj.baijiayun.basic.network.NetworkManager;
import com.nj.baijiayun.basic.network.NetworkUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_main.helper.HomeTabPageHelper;
import com.nj.baijiayun.module_public.bean.AppConfigBean;
import com.nj.baijiayun.module_public.helper.AppStartHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigLoadCallBack;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author chengang
 * @date 2019-06-24
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.ui
 * @describe
 */

public class SplashActivity extends AppCompatActivity {

    private boolean isCheckedPermission = false;
    private boolean isStartMain = false;
    private int retryCount = 0;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tryApplyPermissions();
        NetworkManager.getInstance().registerObserver(this);
        loadConfig();
    }

    private void loadConfig() {
        retryCount++;
        if (!AppStartHelper.isFirstStart()) {
            HomeTabPageHelper.preLoadHomeBottomNav();
        }
        ConfigManager.getInstance().loadAppConfig(new ConfigLoadCallBack<AppConfigBean>() {
            @Override
            public void preLoad() {

            }

            @Override
            public void loadSuccess(AppConfigBean bean) {
                Logger.d("loadSuccess" + isCheckedPermission);
                if (isCheckedPermission) {
                    goMain();
                }

            }

            @Override
            public void loadFail(Exception e) {
                if(!ConfigManager.getInstance().isHasAppConfig()) {
                    ToastUtil.shortShow(SplashActivity.this, "加载配置错误" + (NetworkUtils.isNetworkAvailable(getBaseContext()) ? "" : ",确保你的网络可用"));
                }
                if (retryCount < 2) {
                    new android.os.Handler().postDelayed(() -> {
                        loadConfig();
                    }, 3000);
                    retryCount++;
                }
            }
        });

    }

    @NetWork(netType = NetType.AUTO)
    public void onNetChanged(NetType netType) {
        if (netType == NetType.NONE) {
            ToastUtil.shortShow(SplashActivity.this, "请连接网络");
        } else {
            if (!ConfigManager.getInstance().isHasAppConfig()) {
                loadConfig();
            }
        }
    }

    @SuppressLint("CheckResult")
    private void tryApplyPermissions() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe(hasPremiss -> {
                    Logger.d("hasPremiss" + hasPremiss);
                    isCheckedPermission = true;
                    if (!ConfigManager.getInstance().isHasAppConfig()) {
                        return;
                    }
                    goMain();
                });

    }

    private void goMain() {
        Logger.d("loadSuccess goMain");

        new android.os.Handler().postDelayed(() -> {
            Logger.d("loadSuccess isStartMain--" + isStartMain);
            if (!isStartMain) {
                isStartMain = true;
                Logger.d("loadSuccess startMain");

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
            if (!isFinishing()) {
                new android.os.Handler().postDelayed(this::finish, 1000);
            }
        }, AppStartHelper.isFirstStart() ? 0 : 1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unRegisterObserver(this);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);

    }
}
