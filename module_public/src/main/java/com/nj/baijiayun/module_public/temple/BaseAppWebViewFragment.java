package com.nj.baijiayun.module_public.temple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.ValueCallback;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.base.BaseWebViewActivity;
import com.nj.baijiayun.module_common.base.BaseWebViewFragment;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_common.widget.WebUtils;
import com.nj.baijiayun.module_common.widget.dialog.CommonBottomDialog;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.PublicUploadBean;
import com.nj.baijiayun.module_public.bean.response.PublicUploadRes;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.AppBarHelper;
import com.nj.baijiayun.module_public.helper.FileHelper;
import com.nj.baijiayun.module_public.helper.FileUploadHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.PayHelper;
import com.nj.baijiayun.module_public.helper.SelectPhotoHelper;
import com.nj.baijiayun.module_public.manager.URLCacheManager;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebView;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author chengang
 * @date 2019-08-08
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */
public class BaseAppWebViewFragment extends BaseWebViewFragment {

    private Map<String, String> map = new HashMap<>(2);
    private boolean isRegisterPayListener = false;
    List<PublicUploadBean> fileBeans = new ArrayList<>();
    private Compressor compressor;

    @Override
    public void initParms(Bundle params) {
        super.initParms(params);
        if(TextUtils.isEmpty(data)) {
            this.url = ConstsH5Url.getUrl(url);
        }
        Log.e("支付页面：",url);
    }

    @Override
    public void loadUrl() {
        setConfigUrl();
        refreshCookie(this.url);
        super.loadUrl();
    }

    public void setConfigUrl() {
        if (configUrl() != null && configUrl().length() > 0) {
            String linkStringByGet = ConstsH5Url.createLinkStringByGet(configParams(map));
            url = configUrl();
            if (!TextUtils.isEmpty(linkStringByGet)) {
                url += "?" + linkStringByGet;
            }
        }
    }


    public Map<String, String> configParams(Map<String, String> map) {
        return map;
    }

    public String configUrl() {
        return "";
    }

    public void refreshCookie(String url) {
        syncCookie(getWebView(), url, AccountHelper.getInstance().getToken());

    }

    private boolean syncCookie(AppWebView webView, String url, String token) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (token == null) {
            token = "clear";
        }
//        //cookie存在则不需要同步
        if (!needSyncCookie(cookieManager.getCookie(url), token)) {
            Logger.d("Cookie==>noNeedSync" + url);
            return false;
        }
        Logger.d("Cookie==>syncCookie" + url);

        cookieManager.setCookie(url, MessageFormat.format("token={0}", token));
        cookieManager.setCookie(url, MessageFormat.format("deviceType={0}", "android"));
        String newCookie = CookieManager.getInstance().getCookie(url);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
            cookieSyncManager.sync();
        } else {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
            cookieManager.flush();
        }

        return !TextUtils.isEmpty(newCookie);
    }

    private boolean needSyncCookie(String cookie, String token) {
        if (cookie != null) {
            String[] split = cookie.split(";");
            if (split.length <= 0) {
                return true;
            }
            for (String s : split) {
                String[] cookieItems = s.split("=");
                for (int j = 0; j < cookieItems.length; j++) {
                    if (cookieItems.length == 2) {
                        if ("token".equals(cookieItems[0].trim()) && cookieItems[1].trim().equals(token)) {
                            return false;
                        }
                    }
                }
            }

        }
        return true;
    }

    @Override
    public void registerListener() {
        super.registerListener();
        getWebView().setOnLongClickListener(v -> {
            if (trySaveImg()) {
                return true;
            }
            return false;
        });
        getWebView().registerHandler("action", (data, function) -> {
            Logger.d("jsActionData--->" + data + "---form:" + getWebView().getUrl());
            JsActionDataBean webDataBean = GsonHelper.getGsonInstance().fromJson(data, JsActionDataBean.class);
            handleJsAction(webDataBean);

            if (webDataBean.isPay()) {
                registerPayListener(webDataBean.getParams().getShopId(), webDataBean.getParams().getRedirctPath());
            }
            JumpHelper.handlerWebAction(getActivity(), getWebView(), webDataBean);

        });

        LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE, Boolean.class).observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                loginStatusChange();
            }

        });
        LiveDataBus.get().with(LiveDataBusEvent.COLLECTION_STATUES_CHANGE, Boolean.class).observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                reloadUrl();
            }
        });
    }

    protected void loginStatusChange() {
        reloadUrl();
    }

    private boolean trySaveImg() {
        final WebView.HitTestResult hitTestResult = getWebView().getHitTestResult();
        if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            List<String> choice = new ArrayList<>();
            choice.add("保存图片");
            CommonBottomDialog commonBottomDialog = BJYDialogHelper.buildCommonBottomDialog(getActivity());
            commonBottomDialog
                    .setContents(choice)
                    .setOnItemClickListener((position, view, item) -> {
                        commonBottomDialog.dismiss();
                        if (position == 0) {
                            FileHelper.saveImg(getActivity(), hitTestResult.getExtra());
                        }
                    });
            commonBottomDialog.show();
            return true;
        }
        return false;
    }

    public void reloadUrl() {
        refreshCookie(getWebView().getUrl());
        getWebView().loadUrl(getWebView().getUrl());
        Log.e("支付页面url",getWebView().getUrl());
    }


    private void registerPayListener(int shopId, String redirctPath) {
        Log.e("调用成功",shopId+"1");
        if (isRegisterPayListener) {
            return;
        }
        LiveDataBus.get().with(LiveDataBusEvent.PAY_SUCCESS, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    paySuccessCallBack(shopId);
                }
            }
        });
        isRegisterPayListener = true;
    }

    private void paySuccessCallBack(int shopId) {
        //刷新root页面
//        reloadFirstLoadUrl();
        LiveDataBus.get().with(LiveDataBusEvent.COURSE_HAS_BUY_SUCCESS_BY_PAY).postValue(shopId);
        Logger.i("PaySuccessCallBack" + getActivity() + "------" + isRegisterPayListener);
        //实现取出变量
        Log.e("调用成功",shopId+"4");
        getWebView().callHandler("functionInJs", PayHelper.getPaymentNumberJson(), null);
        //当前堆栈数量大于1 当前页面不属于mainActivity
        Log.e("调用成功参数",PayHelper.getPaymentNumberJson());
        Log.e("调用成功",shopId+"5");
    }


    private void wrapperSelectPathAndUpload(List<String> pathList) {
        if (pathList == null) {
            return;
        }
        fileBeans.clear();
        showLoadV();
        for (int i = 0; i < pathList.size(); i++) {
            PublicUploadBean fileBean = new PublicUploadBean();
            fileBeans.add(fileBean);
            fileBean.setLocalPath(pathList.get(i));
            uploadImg(pathList.get(i));
        }
    }


    private void uploadImg(String filePath) {
        if (compressor == null) {
            compressor = new Compressor(getActivity())
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setQuality(80)
                    .setMaxWidth(720)
                    .setMaxHeight(1080);
        }
        try {
            Observable.just(compressor.compressToFile(new File(filePath)))
                    .flatMap((Function<File, Observable<PublicUploadRes>>) file -> NetMgr.getInstance()
                            .getDefaultRetrofit(NetMgr.UPLOAD_DEFAULT_KEY)
                            .create(PublicService.class)
                            .uploadPics(FileUploadHelper.getMultipartBodyByFile(file)))
                    .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                    .as(RxLife.asOnMain(getActivity()))
                    .subscribe(new BaseSimpleObserver<PublicUploadRes>() {

                        @Override
                        public void onPreRequest() {

                        }

                        @Override
                        public void onSuccess(PublicUploadRes publicUploadRes) {
                            updateFileBeans(filePath, publicUploadRes.getData());
                            if (isAllUploadSuccess()) {
                                closeLoadV();
                                getWebView().callHandler("functionInJs", GsonHelper.getGsonInstance().toJson(fileBeans), null);
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            closeLoadV();
                            showToastMsg(e.getMessage());
                        }
                    });

        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }


    private void updateFileBeans(String localPath, PublicUploadBean publicUploadBean) {
        for (int i = 0; i < fileBeans.size(); i++) {
            if (localPath.equals(fileBeans.get(i).getLocalPath())) {
                fileBeans.get(i).setExt(publicUploadBean.getExt());
                fileBeans.get(i).setLocalPath(publicUploadBean.getLocalPath());
                fileBeans.get(i).setOriginalName(publicUploadBean.getOriginalName());
                fileBeans.get(i).setPath(publicUploadBean.getPath());
                fileBeans.get(i).setSize(publicUploadBean.getSize());
            }
        }
    }

    private boolean isAllUploadSuccess() {
        for (int i = 0; i < fileBeans.size(); i++) {
            if (StringUtils.isEmpty(fileBeans.get(i).getPath())) {
                return false;
            }
        }
        return true;
    }

    public void handleJsAction(JsActionDataBean webDataBean) {
    }

    public void sendDataToJs(String json) {
        getWebView().callHandler("functionInJs", json, null);

    }

    public void sendDataToJs(Object bean) {
        getWebView().callHandler("functionInJs", GsonHelper.getGsonInstance().toJson(bean), null);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LiveDataBus.get().with(LiveDataBusEvent.PAY_SUCCESS).postValue(false);
    }


    //具体页面自己实现处理jsAction


    @Override
    protected boolean needAutoInject() {
        return false;
    }

    @Override
    public void urlChange(String url) {
        super.urlChange(url);
        if (getActivity() != null && getActivity() instanceof BaseWebViewActivity) {
            ((BaseAppActivity) getActivity()).setToolBarVisible(needAppTitle(url));
        }
        AppBarHelper.tryAddShare(getActivity(), getAppToolBar(), url);


    }

    protected Toolbar getAppToolBar() {
        if (getActivity() instanceof BaseAppWebViewActivity) {
            return ((BaseAppWebViewActivity) getActivity()).getToolBar();
        }
        return null;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getWebView() != null && getWebView().canGoBack()) {
            //只要带有标题的 可以带返回
            if (needAppTitle(getWebView().getUrl())) {
                getWebView().goBack();
            }
            return true;
        }
        return super.onBackPressedSupport();
    }

    protected static final String[] FILTER_URL_NO_ACTION_BAR = new String[]{
            ConstsH5Url.getQues(),
            ConstsH5Url.getErrorList()
            , ConstsH5Url.getHomeWorkPath()
    };


    private boolean needAppTitle(String url) {
        return !URLCacheManager.getInstance().isUrlInPathList(url, FILTER_URL_NO_ACTION_BAR);
    }

    public void reloadFirstLoadUrl() {
        refreshCookie(getFirstLoadUrl());
        getWebView().loadUrl(getFirstLoadUrl());
    }

    //判断是来自选择图片
    private boolean isFromFileChose = false;
    //判断是否注定关闭，
    boolean isDismissByClickItem = false;
    //重写用与选择照片
    @Override
    protected void fileChoseListener(ValueCallback valueCallback) {
        isFromFileChose = true;
        isDismissByClickItem=false;
        CommonBottomDialog commonBottomDialog = BJYDialogHelper.buildCommonBottomDialog(getContext());
        commonBottomDialog.setOnDismissListener(dialog -> {
            if (!isDismissByClickItem) {
                clearValueCallBack();
            }
        });
        commonBottomDialog.setContents(new ArrayList<String>() {{
            add("拍照");
            add("从手机相册选择");
        }}).setOnItemClickListener((position, view, item) -> {
            if (position == 0) {
                SelectPhotoHelper.takePhoto(getActivity(), false);
            } else if (position == 1) {
                SelectPhotoHelper.openAlbumn(getActivity(), 1, false);
            }
            isDismissByClickItem = true;
            commonBottomDialog.dismiss();

        }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onActivityResult onResume");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("onActivityResult back");
        if (resultCode == RESULT_OK && data != null) {
            ArrayList<String> pathList = new ArrayList<>();
            if (requestCode == SelectPhotoHelper.REQUEST_SELECT_PHOTO_CODE) {
                pathList = data.getStringArrayListExtra("result");
            } else if (requestCode == SelectPhotoHelper.REQUEST_CAMERA_CODE) {
                pathList.add(data.getStringExtra("result"));
            }
            for (int i = 0; i < pathList.size(); i++) {
                Logger.d("pathList-->" + pathList.get(i));
            }
            if (isFromFileChose) {
                isFromFileChose = false;
                WebUtils.selectH5File(getContext(), pathList, valueCallback);
            } else {
                wrapperSelectPathAndUpload(pathList);
            }
        } else {
            clearValueCallBack();
        }

    }
}
