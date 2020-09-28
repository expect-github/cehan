package com.nj.baijiayun.module_public.helper;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.config.ShapeTypeConfig;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonShareDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.UserInfoBean;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.helper.share_login.JPushHelper;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author chengang
 * @date 2019-12-25
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe 举报部分
 * type    是	int	1-课程评价，2-讲师评价，3-图书评价，4-帖子，5-贴子回复
 * type_id	是	int	根据 type = 1-课程评价id，2-讲师评价id，3-图书评价id，4-帖子id，5-帖子回复id
 */
public class AppBarHelper {

    private static final String TYPE_BOOK = "1";
    private static final String TYPE_LIBRARY = "2";
    private static final String TYPE_NEWS = "3";
    private static final String TYPE_POST = "4";
    private static final String TYPE_TEACHER = "5";
    private static LinkedHashMap<String, URL> linkedHashMap = new LinkedHashMap<String, URL>() {
        @Override
        protected boolean removeEldestEntry(Entry eldest) {
            return size() > 20;
        }
    };

    public static void tryAddShare(Activity activity, Toolbar toolBar, String url) {
        if (toolBar == null || toolBar.getChildCount() <= 0) {
            return;
        }
        boolean needShowShare = needShowShareByUrl(url);
        View mShare = toolBar.getChildAt(toolBar.getChildCount() - 1);
        //mShare.getTag()!=null 说明已经分享按钮绑定了数据
        if (mShare instanceof ImageView && mShare.getTag() != null) {
            mShare.setVisibility(needShowShare ? View.VISIBLE : View.INVISIBLE);
            mShare.setTag(url);
            return;
        }

        if (needShowShare && ConfigManager.getInstance().isShowShare()) {
            try {
                ToolBarHelper.addRightImageView(toolBar
                        , TYPE_POST.equals(getType(getURL(url).getPath())) ? R.drawable.public_ic_more : R.drawable.public_ic_share
                        , v -> {
                            clickShare(activity, v.getTag().toString());
                        });
                mShare = toolBar.getChildAt(toolBar.getChildCount() - 1);
                //保存url
                mShare.setTag(url);

            } catch (Exception e) {
                Logger.e(e);
            }
        }

    }

    public static void clickShare(Context context, String url) {
        clickShare(context, url, "", ShareWrapper.SOURCE_NORMAL);
    }

    public static void clickDistributeShare(Context context, String url, String posterUrl) {
        clickShare(context, url, posterUrl, ShareWrapper.SOURCE_DISTRIBUTION);
    }


    private static void clickShare(Context context, String url, String posterUrl, int source) {
        try {
            ShareWrapper shareWrapper = new ShareWrapper();
            shareWrapper.url = url;
            shareWrapper.id = getId("id", url);
            shareWrapper.uid = getId("u_id", url);
            shareWrapper.type = getType(getURL(url).getPath());
            shareWrapper.posterUrl = posterUrl;
            shareWrapper.source = source;
            getShareInfo(context, shareWrapper);
        } catch (Exception e) {
            Logger.e(e.getCause());
        }
    }


    private static String getId(String key, String url) throws MalformedURLException {
        String query = getURL(url).getQuery();
        String[] split = query.split("&");
        String id = "";
        for (String s : split) {
            String[] split1 = s.split("=");
            if (key.equals(split1[0])) {
                id = split1[1];
                break;
            }
        }
        return id;
    }


    private static boolean needShowShareByUrl(String url) {
        try {
            URL urlWrapper = getURL(url);
            if (urlWrapper.getPath() == null) {
                return false;
            }
            for (String key : NEED_SHOW_SHARE_URLS.keySet()) {
                if (urlWrapper.getPath().startsWith(key)) {
                    return true;
                }
            }

        } catch (MalformedURLException e) {
            return false;
        }
        return false;

    }

    @NotNull
    private static URL getURL(String url) throws MalformedURLException {
        URL urlWrapper = linkedHashMap.get(url);
        if (urlWrapper == null) {
            urlWrapper = new URL(url);
            linkedHashMap.put(url, urlWrapper);
        }
        return urlWrapper;
    }

    public static String getType(String path) {
        String type = "";
        for (String key : NEED_SHOW_SHARE_URLS.keySet()) {
            if (path.startsWith(key)) {
                return NEED_SHOW_SHARE_URLS.get(key);
            }
        }
        return type;
    }


    private static final Map<String, String> NEED_SHOW_SHARE_URLS = new HashMap<String, String>() {{
        put("/book-detail", TYPE_BOOK);
        put("/news-detail", TYPE_NEWS);
        put("/post-detail", TYPE_POST);
        put("/teacher", TYPE_TEACHER);
    }};

//    1 图书 2 文库 3 资讯 4 帖子

    public static void libraryShare(Context context, String id) {

        ShareWrapper shareWrapper = new ShareWrapper();
        shareWrapper.id = id;
        shareWrapper.type = TYPE_LIBRARY;
        shareWrapper.url = "";
        getShareInfo(context, shareWrapper);
    }


    public static class ShareWrapper {
        String id;
        String type;
        String url;
        String posterUrl;
        String uid;
        int source;
        static final int SOURCE_NORMAL = 0;
        static final int SOURCE_DISTRIBUTION = 1;

        boolean isFromDistribution() {
            return source == SOURCE_DISTRIBUTION;
        }

        boolean needShareTypeImg() {
            return !TextUtils.isEmpty(posterUrl);
        }


        boolean isShowReport() {
            UserInfoBean info = AccountHelper.getInstance().getInfo();
            boolean isMe = false;
            if (info != null) {
                isMe = uid != null && uid.equals(String.valueOf(info.getId()));
            }
            return !isMe && TYPE_POST.equals(type);
        }
    }


    public static void getShareInfo(Context context, ShareWrapper shareWrapper) {
        if (context instanceof BaseAppActivity) {
            ((BaseAppActivity) context).showLoadV();
        }
        NetMgr.getInstance().getDefaultRetrofit().create(PublicService.class).getShareInfo(shareWrapper.id, shareWrapper.type, shareWrapper.url, shareWrapper.source)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as((LifecycleOwner) context))
                .subscribe(new BaseSimpleObserver<BaseResponse<ShareInfo>>() {
                    @Override
                    public void onPreRequest() {


                    }

                    @Override
                    public void onSuccess(BaseResponse<ShareInfo> shareInfoBaseResponse) {
                        if (context instanceof BaseAppActivity) {
                            ((BaseAppActivity) context).closeLoadV();
                        }


                        CommonShareDialog commonShareDialog = new CommonShareDialog(context, shareWrapper.needShareTypeImg());
                        if (shareWrapper.isShowReport()) {
                            commonShareDialog.getAdapter().addItem(new CommonShareDialog.ShareBean(R.drawable.public_ic_report, "举报", ShapeTypeConfig.REPORT));
                        }
                        commonShareDialog
                                .setOnItemClickListener((position, view, item) -> {
                                    //目前只有帖子 这块需要整体重构
                                    if (item.getType() == ShapeTypeConfig.REPORT) {
                                        //appBack 跳转h5页面 关闭使用finishActivity
                                        JumpHelper.jumpWebView(ConstsH5Url.getUrl(MessageFormat.format("/tip?type=4&id={0}&appBack=1", String.valueOf(shareWrapper.id))));
                                        return;
                                    }
                                    if (item.getType() == ShapeTypeConfig.IMG) {
                                        if (shareWrapper.isFromDistribution()) {
                                            JumpHelper.jumpWebViewNoNeedAppTitle(shareWrapper.posterUrl);
                                            return;
                                        }
                                    }
                                    JPushHelper.platFormShare(context, shareInfoBaseResponse.getData(), item, null);
                                })
                                .show();
                    }

                    @Override
                    public void onFail(Exception e) {
                        if (context instanceof BaseAppActivity) {
                            ((BaseAppActivity) context).closeLoadV();
                        }
                        ToastUtil.shortShow(context, e.getMessage());

                    }
                });
    }

}
