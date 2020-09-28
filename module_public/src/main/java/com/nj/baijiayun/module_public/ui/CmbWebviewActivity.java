package com.nj.baijiayun.module_public.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.response.CmbPayBean;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.utlis.YWTBaseJsonRequestData;
import com.nj.baijiayun.module_public.utlis.YWTPayReqData;
import com.nj.baijiayun.module_public.utlis.YWTUtil;
import com.nj.baijiayun.module_public.utlis.YWTWebView;

import cmbapi.CMBApi;

/**
 * @author Kai
 * @date 2020/8/15
 * @time 17:25
 */
@Route(path = RouterConstant.PAGE_CMB_WEB)
public class CmbWebviewActivity extends BaseAppActivity {
    static CMBApi cmbApi = null;
    private  String requestdata = "jsonRequestData=%7b%22charset%22%3a%22UTF-8%22%2c%22" +
            "sign%22%3a%227C52658402B98C6D5525F866DA15851BD76F7070730AC893852042B9F8F77562%22%2c%22" +
            "reqData%22%3a%7b%22dateTime%22%3a%2220190531165802%22%2c%22" +
            "merchantSerialNo%22%3a%2220190531165802%22%2c%22" +
            "agrNo%22%3a%222019053116580288%22%2c%22b" +
            "ranchNo%22%3a%220755%22%2c%22" +
            "merchantNo%22%3a%22000054%22%2c%22" +
            "mobile%22%3a%2213888888888%22%2c%22" +
            "userID%22%3a%221%22%2c%22lon%22%3a%2250.949506%22%2c%22lat%22%3a%2230.949505%22%2c%22" +
            "noticeUrl%22%3a%221%22%2c%22" +
            "noticePara%22%3a%22%22%2c%22" +
            "returnUrl%22%3a%220%22%7d%7d";
    private String H5URL = "http://99.12.69.116/netpayment/BaseHttp.dll?H5PayJsonSDK";
    private String PAYURL = "cmbmobilebank://cmbls/functionjump?action=gofuncid/funcid=0027016/clean=false/needlogin=false/requesttype=post/cmb_app_trans_parms_start=here/";
    private String APPID = "1230612";
    /**支付地址地址*/
    private static final String payUrl = "http://121.15.180.66:801/NetPayment/BaseHttp.dll?MB_EUserPay";

    private String response;
    private YWTWebView webview;
    private CmbPayBean cmbPayBean;
    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_cmb_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPageTitle("支付");
        response = getIntent().getStringExtra("date");
        webview = findViewById(R.id.webView_cmb);
        cmbPayBean = GsonHelper.getGsonInstance().fromJson(response, CmbPayBean.class);
        LoadUrl();
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

    private void LoadUrl() {
        String jsondata = "jsonRequestData=" + getJsonRequestData(cmbPayBean.getReqData().getOrderNo(),
                cmbPayBean.getReqData().getAmount(), cmbPayBean.getReqData().getSignNoticeUrl(),
                cmbPayBean.getReqData().getPayNoticeUrl(), "http://www.baidu.com",
                cmbPayBean.getReqData().getAgrNo(), cmbPayBean.getReqData().getMerchantSerialNo(),
                cmbPayBean.getSign(), cmbPayBean.getReqData().getMerchantNo(),
                cmbPayBean.getReqData().getBranchNo());
        webview.postUrl(payUrl,jsondata.getBytes());
    }

    /**
     * 获得支付请求的参数
     *
     * @param orderId          订单号
     * @param amount           金额
     * @param signInformUrl    签约成功通知后台的地址
     * @param payInformUrl     支付成功通知后台的地址
     * @param returnUrl        支付成功的返回商户的地址
     * @param agrNo            协议号
     * @param merchantSerialNo 协议开通的流水号
     * @return
     */
    private static String getJsonRequestData(String orderId, String amount, String signInformUrl,
                                             String payInformUrl, String returnUrl,
                                             String agrNo, String merchantSerialNo,
                                             String sin, String merchantNo, String branchNo) {
        YWTBaseJsonRequestData<YWTPayReqData> requestData = new YWTBaseJsonRequestData<>();
        YWTPayReqData ywtPayReqData = new YWTPayReqData(
                YWTUtil.getCurrentTime(),
                branchNo,
                merchantNo,
                YWTUtil.getYWTOrderTime(),
                orderId,
                amount,
                signInformUrl,
                payInformUrl,
                agrNo,
                merchantSerialNo,
                returnUrl
        );
        requestData.setSign(sin);
        requestData.setReqData(ywtPayReqData);

        String json = new Gson().toJson(requestData);
        return json;
    }


}
