package com.nj.baijiayun.module_public.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baijiayun.lib_push.alipay.AliPayConfig;
import com.baijiayun.lib_push.alipay.AliPayManager;
import com.baijiayun.lib_push.wxpay.WxPayConfig;
import com.baijiayun.lib_push.wxpay.WxPayManager;
import com.google.gson.Gson;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.PayBean;
import com.nj.baijiayun.module_public.bean.WeChatPayBean;
import com.nj.baijiayun.module_public.bean.response.CmbPayBean;
import com.nj.baijiayun.module_public.bean.response.PayResponse;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.utlis.YWTBaseJsonRequestData;
import com.nj.baijiayun.module_public.utlis.YWTConfig;
import com.nj.baijiayun.module_public.utlis.YWTPayReqData;
import com.nj.baijiayun.module_public.utlis.YWTUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import cmbapi.CMBApi;
import cmbapi.CMBApiFactory;
import cmbapi.CMBConstants;
import cmbapi.CMBEventHandler;
import cmbapi.CMBRequest;
import cmbapi.CMBResponse;
import io.reactivex.schedulers.Schedulers;

//import com.baijiayun.lib_push.alipay.AliPayConfig;
//import com.baijiayun.lib_push.alipay.AliPayManager;
//import com.baijiayun.lib_push.wxpay.WxPayConfig;
//import com.baijiayun.lib_push.wxpay.WxPayManager;

/**
 * @author chengang
 * @date 2019-08-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class PayHelper {
    public static String PAYMENT_NUMBER;
    public static String PAY_TYPE;
    private static String H5URL = "http://99.12.69.116/netpayment/BaseHttp.dll?H5PayJsonSDK";
    private static String PAYURL = "cmbmobilebank://cmbls/functionjump?action=gofuncid/funcid=0027016/clean=false/needlogin=false/requesttype=post/cmb_app_trans_parms_start=here/";
    private static final String APPID = "1230612";
    private static String appTestUrl  = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200013&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here&charset=utf-8&jsonRequestData=";
    private static String response = null;
    public static Activity mActivity;
    public static String getPaymentNumberJson() {
        PayBean payBean = new PayBean();
        payBean.setPaymentNumber(PAYMENT_NUMBER);
//        payBean.setPaymentNumber(PAY_TYPE);
        return GsonHelper.getGsonInstance().toJson(payBean);

    }

    public static void payOrder(AppCompatActivity activity, String orderNumber, String payType) {
        mActivity = activity;
        PAY_TYPE = payType;
        Log.e("支付数据1：", orderNumber + "动作：" + payType);
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getPayResponse(orderNumber, payType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain(activity))
                .subscribe(new BaseSimpleObserver<PayResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(PayResponse baseResponse) {
                        PAYMENT_NUMBER = baseResponse.getData().getPaymentNumber();
                        pay(activity, baseResponse.getData().getResponse(), payType);

                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtil.shortShow(activity, e.getMessage());

                    }
                });

    }

    private static boolean isAliPay(String payType) {
        return "ali".equals(payType);
    }

    private static boolean isWeChatPay(String payType) {
        return "wx".equals(payType);
    }

    private static boolean isCmbPay(String payType) {
        return "cmb".equals(payType);
    }


    private static void pay(Activity activity, String response, String payType) {
        Log.e("支付数据：", response + "动作：" + payType);
        if (isAliPay(payType)) {
            AliPayConfig aliPayConfig = new AliPayConfig.Builder()
                    .with(activity)
                    .setSignedOrder(response)
                    .setmCall((msg, isPaySuccess) -> {
                        Logger.d("payStatus--> ali " + isPaySuccess);
                        //isPaySuccess返回true 表示支付成功
                        ToastUtil.shortShow(activity, isPaySuccess ? "支付成功" : msg);
                        if (isPaySuccess) {
                            LiveDataBus.get().with(LiveDataBusEvent.PAY_SUCCESS).postValue(true);
                        }
                    }).builder();
            AliPayManager.getInstance().sendPay(aliPayConfig);

        } else if (isWeChatPay(payType)) {
            WeChatPayBean weChatPayBean = GsonHelper.getGsonInstance().fromJson(response, WeChatPayBean.class);
            WxPayConfig wxPayConfig = new WxPayConfig.Builder()
                    .with(activity)
                    .setAppId(weChatPayBean.getAppid())
                    .setNoncestr(weChatPayBean.getNoncestr())
                    .setPackagex(weChatPayBean.getPackageX())
                    .setPartnerid(weChatPayBean.getPartnerid())
                    .setPrepayid(weChatPayBean.getPrepayid())
                    .setSign(weChatPayBean.getSign())
                    .setTimestamp(weChatPayBean.getTimestamp())
                    .builder();
            WxPayManager.getInstance().sendPay(wxPayConfig);
        } else if (isCmbPay(payType)) {
            //一网通支付
            CmbPayBean cmbPayBean = GsonHelper.getGsonInstance().fromJson(response, CmbPayBean.class);
            Log.e("支付数据：", cmbPayBean.getCharset() + ":" + cmbPayBean.getSignType() + "时间：" + cmbPayBean.getReqData().getDateTime());
            ARouter.getInstance().build(RouterConstant.PAGE_CMB_WEB_NEW).withString("date",response).navigation();
//            jumpToCMBApp(activity, cmbPayBean);
        }
    }

//    private static void jumpToCMBApp(Activity activity, CmbPayBean cmbPayBean) {
//        CMBRequest request = getCMBRequestByInput(activity, cmbPayBean);
//        if (TextUtils.isEmpty(request.CMBJumpAppUrl)) {
//            Toast.makeText(activity, "调用失败,cmbJumpUrl不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        request.CMBH5Url = "";
//
//        //mCMBJumpUrl需要以http://或者https://开头，参数错误会抛异常:IllegalArgumentException
//        try {
////            cmbApi.sendReq(request);
//        } catch (IllegalArgumentException e) {
//            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static CMBRequest getCMBRequestByInput(Activity activity, CmbPayBean cmbPayBean) {
//        String jsondata = "jsonRequestData=" + getJsonRequestData(cmbPayBean.getReqData().getOrderNo(),
//                cmbPayBean.getReqData().getAmount(), cmbPayBean.getReqData().getSignNoticeUrl(),
//                cmbPayBean.getReqData().getPayNoticeUrl(), "http://www.baidu.com",
//                cmbPayBean.getReqData().getAgrNo(), cmbPayBean.getReqData().getMerchantSerialNo(),
//                cmbPayBean.getSign(), cmbPayBean.getReqData().getMerchantNo(),
//                cmbPayBean.getReqData().getBranchNo());
//        Log.e("支付参数redate：", jsondata);
//        cmbApi = CMBApiFactory.createCMBAPI(activity, APPID);
//        CMBRequest request = new CMBRequest();
//        request.requestData = jsondata;
//        request.CMBJumpAppUrl = PAYURL;
//        request.CMBH5Url = H5URL;
//        request.method = "pay";
//        request.isShowNavigationBar = true;
//        return request;
//    }
//
//
//    /**
//     * 获得支付请求的参数
//     *
//     * @param orderId          订单号
//     * @param amount           金额
//     * @param signInformUrl    签约成功通知后台的地址
//     * @param payInformUrl     支付成功通知后台的地址
//     * @param returnUrl        支付成功的返回商户的地址
//     * @param agrNo            协议号
//     * @param merchantSerialNo 协议开通的流水号
//     * @return
//     */
//    private static String getJsonRequestData(String orderId, String amount, String signInformUrl,
//                                             String payInformUrl, String returnUrl,
//                                             String agrNo, String merchantSerialNo,
//                                             String sin, String merchantNo, String branchNo) {
//        YWTBaseJsonRequestData<YWTPayReqData> requestData = new YWTBaseJsonRequestData<>();
//        YWTPayReqData ywtPayReqData = new YWTPayReqData(
//                YWTUtil.getCurrentTime(),
//                branchNo,
//                merchantNo,
//                YWTUtil.getYWTOrderTime(),
//                orderId,
//                amount,
//                signInformUrl,
//                payInformUrl,
//                agrNo,
//                merchantSerialNo,
//                returnUrl
//        );
//        requestData.setSign(sin);
//        requestData.setReqData(ywtPayReqData);
//
//        String json = new Gson().toJson(requestData);
//        return json;
//    }




}
