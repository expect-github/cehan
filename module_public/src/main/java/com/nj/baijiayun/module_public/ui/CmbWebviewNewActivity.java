package com.nj.baijiayun.module_public.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baijiayun.bjyrtcsdk.Util.LogUtil;
import com.google.gson.Gson;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.response.CmbPayBean;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.utlis.LogManager;
import com.nj.baijiayun.module_public.utlis.YWTBaseJsonRequestData;
import com.nj.baijiayun.module_public.utlis.YWTPayReqData;
import com.nj.baijiayun.module_public.utlis.YWTUtil;
import com.nj.baijiayun.module_public.utlis.YWTWebView;

import cmbapi.CMBApi;
import cmbapi.CMBApiFactory;
import cmbapi.CMBApiUtils;
import cmbapi.CMBConstants;
import cmbapi.CMBEventHandler;
import cmbapi.CMBRequest;
import cmbapi.CMBResponse;
import cmbapi.CMBTitleBar;
import cmbapi.CMBWebViewListener;
import cmbapi.CMBWebview;

/**
 * 一网通支付
 *
 * @author Kai
 * @date 2020/8/15
 * @time 17:25
 */
@Route(path = RouterConstant.PAGE_CMB_WEB_NEW)
public class CmbWebviewNewActivity extends BaseAppActivity implements CMBEventHandler {
    CMBApi cmbApi = null;

    private int mRespCode = 8;
    private String mRespString = "";
    //测试地址：http://121.15.180.66:801/netpayment/BaseHttp.dll?H5PayJsonSDK
    private String H5URL = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?H5PayJsonSDK";
    private String PAYURL = "cmbmobilebank://cmbls/functionjump?action=gofuncid/funcid=0027016/clean=false/needlogin=false/requesttype=post/cmb_app_trans_parms_start=here/";
    private String APPID = "1230612";
    /**
     * 支付地址地址
     */
//    private static final String payUrl = "http://121.15.180.66:801/NetPayment/BaseHttp.dll?MB_EUserPay";
    private boolean backType = true;
    private String response;
    private CMBWebview webview;
    CMBTitleBar titlebar;
    private CmbPayBean cmbPayBean;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int MAX_LOG_LENGTH = 4;
//    private String payDateUrl;
//    private String appTestUrlNew = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200007&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here&charset=utf-8&jsonRequestData=";
    private String appTestUrl = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200013&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here&charset=utf-8&jsonRequestData=";

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_cmb_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        response = getIntent().getStringExtra("date");
        webview = findViewById(R.id.webView_cmb);
        titlebar = findViewById(R.id.titleBar);
        cmbPayBean = GsonHelper.getGsonInstance().fromJson(response, CmbPayBean.class);
        cmbApi = CMBApiFactory.createCMBAPI(this, APPID);
        if (null != getIntent()) {
        }
        cmbApi.handleIntent(getIntent(), this);
//        LoadUrl();
//        jumpToCMBApp();
//        callCMBApp();
        checkCMBApp();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        cmbApi.handleIntent(intent, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(CMBConstants.TAG, "MainActivity-onActivityResult data:" + data.getDataString());
        cmbApi.handleIntent(data, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        PERMISSIONS_STORAGE,
                        100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {

        }
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public boolean needAutoInject() {
        return false;

    }

    /**
     * 网页支付
     */
    private void LoadUrl() {
        String jsondata = appTestUrl + response;
//        webview.postUrl(payUrl, jsondata.getBytes());

        CMBRequest request = new CMBRequest();
        request.CMBH5Url = H5URL;
        request.method = "pay";
        request.requestData = jsondata;
        if (null != webview) {
            webview.sendRequest(request, new CMBWebViewListener() {
                @Override
                public void onClosed(int respCode, String respString) {
                    mRespCode = respCode;
                    mRespString = respString;
                    Toast.makeText(CmbWebviewNewActivity.this, "code" + respCode + "   msg" + respString, Toast.LENGTH_LONG);
                    handleRespMessage();
                }

                @Override
                public void onTitleChanged(String title) {
                    titlebar.setTitle(title);
                }
            });
        }
        titlebar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CMBResponse response = webview.getCMBResponse();
                mRespCode = response.respCode;
                mRespString = response.respMsg;
                handleRespMessage();
            }
        });

    }


    @Override
    public void onResp(CMBResponse cmbResponse) {

        Log.d(CMBConstants.TAG, "MainActivity-onResp 进入:");
        if (cmbResponse.respCode == 0) {
//            Log.d(CMBConstants.TAG, "MainActivity-onResp responseMSG:" + cmbResponse.respMsg + "responseCODE= " + cmbResponse.respCode);
//            Toast.makeText(this, "调用成功.str:" + cmbResponse.respMsg, Toast.LENGTH_SHORT).show();
            Log.e("调用成功", cmbResponse.respMsg);
            paySucces();
        } else {
            payFail();
            Log.d(CMBConstants.TAG, "MainActivity-onResp 调用失败:");
            Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        }

//        String resMsg = String.format("onResp：respcode:%s.respmsg:%s", cmbResponse.respCode, cmbResponse.respMsg);
//        LogManager.getInstance().addFirst(resMsg);
//        Log.d(CMBConstants.TAG, "Mainactivity-onResp-resMsg= " + resMsg);
//        showLog();
    }

    private void showLog() {
        if (LogManager.getInstance().isEmpty()) {
            return;
        }

        if (LogManager.getInstance().getSize() >= MAX_LOG_LENGTH) {
            LogManager.getInstance().removeLast();
        }

        StringBuffer strLog = new StringBuffer();
        for (int i = 0; i < LogManager.getInstance().getSize(); i++) {

            strLog.append(LogManager.getInstance().getIndex(i)).append("\n");
        }

        Log.e("支付信息：", strLog + "");
//        tvShowlog.setText(strLog);
    }

    private void paySucces() {
        finish();
        LiveDataBus.get().with(LiveDataBusEvent.PAY_SUCCESS).postValue(true);

    }

    private void payFail() {
        Toast.makeText(CmbWebviewNewActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 检查是否安装了一网通支付  true 安装了
     */
    private void checkCMBApp() {

        boolean bOk = cmbApi.isCMBAppInstalled();
        if (bOk) {
            callCMBApp();
            setPageTitle("支付");
//            hideToolBar();
//            LoadUrl();

        } else {
            hideToolBar();
            LoadUrl();
        }

    }


    /**
     * 唤醒一网通客户端
     */
    private void callCMBApp() {
        String url =
                appTestUrl + response;
        try {
            Intent intent = new Intent();
            Uri data = Uri.parse(url);
            intent.setData(data);
            intent.setAction("android.intent.action.VIEW");
            startActivity(intent);
            backType = false;
        } catch (Exception e) {
        }
    }


    private void handleRespMessage() {
        if (mRespCode == 0) {
            paySucces();
//            LiveDataBus.get().with(LiveDataBusEvent.PAY_SUCCESS).postValue(true);
        } else {
            payFail();
        }
        Log.e("调用成功", "handleRespMessage respCode:" + mRespCode +
                "respMessage:" + mRespString);
    }


}
