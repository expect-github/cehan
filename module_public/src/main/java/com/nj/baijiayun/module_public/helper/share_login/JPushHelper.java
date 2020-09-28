package com.nj.baijiayun.module_public.helper.share_login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.config.ShapeTypeConfig;
import com.nj.baijiayun.module_common.mvp.BaseView;
import com.nj.baijiayun.module_common.widget.dialog.CommonShareDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.RouterConstant;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import cn.jiguang.share.android.api.AuthListener;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.android.model.AccessTokenInfo;
import cn.jiguang.share.android.model.BaseResponseInfo;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 极光推送 分享 三方登录 工具类 维护工具类
 * 2018年5月5日11:56:03
 *
 * @author cj
 */
public class JPushHelper {
    private static JPushHelper JPushHelper;

    public static JPushHelper getInstance() {
        if (JPushHelper == null) {
            JPushHelper = new JPushHelper();
        }
        return JPushHelper;
    }

    /**
     * 初始化推送
     *
     * @param mContext
     */
    public void initJpush(Context mContext, boolean isDeBug) {

        JpushApp.getInstance().initJpush(mContext, isDeBug);
    }

    /**
     * 初始化分享
     *
     * @param mContext
     */
    public void initJshare(Context mContext, List<ShareConfigBean> shapeConfigBeans, boolean isDebug) {
        JshapeApp.getInstance().initJshape(mContext, shapeConfigBeans, isDebug);
    }


    /**
     * 吊起分享  如果 默认去带有模版的分享
     *
     * @param shareInfo 分享平台参数
     */
    public void openShape(final Context context, final ShareInfo shareInfo, final PlatActionListener platActionListener) {
        new CommonShareDialog(context, true).setShareTip(shareInfo.getShareTip())
                .setOnItemClickListener(new CommonShareDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view, CommonShareDialog.ShareBean item) {
                        if (item.getType() == ShapeTypeConfig.IMG) {
                            ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_SHARE_IMG).withParcelable("shareInfo", shareInfo).navigation();
                            return;
                        }
                        platFormShare(context, shareInfo, item, platActionListener);

                    }
                }).show();
    }

    @SuppressLint("CheckResult")
    public static void platFormShare(Context context, ShareInfo shareInfo, CommonShareDialog.ShareBean item, PlatActionListener platActionListener) {
        final ShareParams shareParams = new ShareParams();
        String shareTitle = shareInfo.getTitle();
        if (shareTitle != null && shareTitle.length() > 30) {
            shareTitle = shareTitle.substring(0, 30);
        }
        String content = shareInfo.getAbstract();
        if (content != null && content.length() > 40) {
            content = content.substring(0, 40);
        }
        shareParams.setTitle(shareTitle);
        shareParams.setText(content);
        shareParams.setShareType(shareInfo.getShareType());
        shareParams.setUrl(shareInfo.getUrl());
        shareParams.setImageUrl(shareInfo.getImage());
        shareParams.setImagePath(shareInfo.getLocalImgPath());
        final ShapeTypeConfig type = item.getType();
        Logger.d("分享工具" + shareInfo.toString());

        if (!StringUtils.isEmpty(shareInfo.getLocalImgPath())) {
            share(type, shareParams, platActionListener);
            return;
        }

        Flowable.defer((Callable<Flowable<File>>) () -> {
            try {
                return Flowable.just(Glide.with(context).downloadOnly().load(shareParams.getImageUrl()).submit(200, 200).get());
            } catch (Exception e) {
                return Flowable.error(e);
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file1 -> {
                    shareParams.setImagePath(file1.getPath());
                    share(type, shareParams, platActionListener);
                }, throwable -> {
                    share(type, shareParams, platActionListener);

                });

    }


    private static void share(ShapeTypeConfig type, ShareParams shareParams, PlatActionListener platActionListener) {
        if (type == ShapeTypeConfig.WX) {
            JShareInterface.share(Wechat.Name, shareParams, platActionListener);
        } else if (type == ShapeTypeConfig.WXP) {
            JShareInterface.share(WechatMoments.Name, shareParams, platActionListener);
        } else if (type == ShapeTypeConfig.QQ) {
            JShareInterface.share(QQ.Name, shareParams, platActionListener);
        } else if (type == ShapeTypeConfig.QQZONE) {
            JShareInterface.share(QZone.Name, shareParams, platActionListener);
        } else if (type == ShapeTypeConfig.SINA) {
            JShareInterface.share(SinaWeibo.Name, shareParams, platActionListener);
        }
    }

    /**
     * 第三方登录
     *
     * @param shapeTypeConfig 平台名称
     * @param call            回调
     */
    public void UserLoginByJShareLogin(ShapeTypeConfig shapeTypeConfig, final JShareLoginCall call) {
        JShareInterface.authorize(getPtName(shapeTypeConfig), new AuthListener() {
            @Override
            public void onComplete(Platform platform, int action, BaseResponseInfo data) {
                Logger.d("UserLoginByJShareLogin----->>>onComplete" + action + "*****");
                String toastMsg = null;
                switch (action) {
                    default:
                        break;
                    case Platform.ACTION_AUTHORIZING:
                        if (data instanceof AccessTokenInfo) {
                            //授权信息// token
                            String token = ((AccessTokenInfo) data).getToken();
                            // token有效时间，时间戳
                            long expiration = ((AccessTokenInfo) data).getExpiresIn();
                            //refresh_token
                            String refeshToken = ((AccessTokenInfo) data).getRefeshToken();
                            //openid
                            String openid = ((AccessTokenInfo) data).getOpenid();
                            //授权原始数据，开发者可自行处理
                            String originData = data.getOriginData();
                            toastMsg = "授权成功:" + data.toString();
                            Logger.d("openid:" + openid + ",token:" + token + ",expiration:" + expiration + ",refresh_token:" + refeshToken);
                            Logger.d("originData:" + originData);
                            call.getJShareLogin((AccessTokenInfo) data, true, toastMsg);
                        }
                        break;
                }
            }

            @Override
            public void onError(Platform platform, int action, int i1, Throwable throwable) {
                Logger.d("UserLoginByJShareLogin----->>>onError" + action + "*****" + throwable.getMessage());

                String toastMsg = null;
                switch (action) {
                    default:
                        break;

                    case Platform.ACTION_AUTHORIZING:
                        toastMsg = "授权失败";
                        call.getJShareLogin(null, false, toastMsg);
                        break;
                }
            }

            @Override
            public void onCancel(Platform platform, int action) {
                Logger.d("UserLoginByJShareLogin----->>>onCancel" + action + "*****");

                Logger.e("onCancel:" + platform + ",action:" + action);
                String toastMsg = null;
                switch (action) {
                    default:
                        break;
                    case Platform.ACTION_AUTHORIZING:
                        toastMsg = "取消授权";
                        call.getJShareLogin(null, false, toastMsg);
                        break;
                }
            }
        });
    }

    /**
     * 判断是否已经授权
     *
     * @param shapeTypeConfig
     * @return
     */
    public boolean isJuspLoginAuthorize(ShapeTypeConfig shapeTypeConfig) {
        return JShareInterface.isAuthorize(getPtName(shapeTypeConfig));
    }


    public void cancleTagAndAlias(Context context, TagAliasCallback tagAliasCallback) {
        //TODO 上下文、别名、标签、回调  退出后空数组与空字符串取消之前的设置
        Set<String> tags = new HashSet<String>();
        JPushInterface.setAliasAndTags(context, "", tags, tagAliasCallback);
    }

    /**
     * 删除标签
     *
     * @param context
     * @param userId
     */
    public void deletedJPushAlias(Context context, int userId) {
        JPushInterface.deleteAlias(context, userId);

    }


    /**
     * 设置标签  针对定向推送
     */
    public void setJPushAliasCallback(Context context, String useId, TagAliasCallback tagAliasCallback) {
//        JPushInterface.setAlias(context,tag,tags,tagAliasCallback);
        Set<String> tags = new HashSet<String>();
        String JPushTag = "push" + useId;
        tags.add(JPushTag);
        Logger.d("changpeng Login JPushTag =" + JPushTag);
        JPushInterface.setAliasAndTags(context, useId, tags, tagAliasCallback);

    }

    /**
     * 获取平台信息
     *
     * @return
     */
    public List<String> getPlats() {
        return JShareInterface.getPlatformList();
    }

    /**
     * 判断是否有效
     *
     * @param name
     * @return
     */
    public boolean isClient(String name) {
        return JShareInterface.isClientValid(name);
    }

    /**
     * 删除授权
     *
     * @param shapeTypeConfig
     * @param authListener
     */
    public void JuspLoginDeleteAuthorize(ShapeTypeConfig shapeTypeConfig, AuthListener authListener) {
        JShareInterface.removeAuthorize(getPtName(shapeTypeConfig), authListener);
    }

    private String getPtName(ShapeTypeConfig shapeTypeConfig) {
        String LoginName = "";
        if (shapeTypeConfig == ShapeTypeConfig.QQ) {
            LoginName = "QQ";
        } else if (shapeTypeConfig == ShapeTypeConfig.WX) {
            LoginName = "Wechat";
        } else {
            LoginName = "QQ";
        }
        return LoginName;
    }

    public static class ShareListener implements PlatActionListener {
        private final WeakReference<BaseView> weakView;

        public ShareListener(BaseView view) {
            this.weakView = new WeakReference<>(view);
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            BaseView view = weakView.get();
            if (view != null) {
                view.showToastMsg(R.string.common_share_success);
            }
        }

        @Override
        public void onError(Platform platform, int i, int i1, Throwable throwable) {
            Logger.e("share failed code1 is " + i + " and code2 is " + i1 + "\n exception: " + throwable.getMessage());
            BaseView view = weakView.get();
            if (view != null) {
                view.showToastMsg(R.string.common_share_fail);
            }
        }

        @Override
        public void onCancel(Platform platform, int i) {
            BaseView view = weakView.get();
            if (view != null) {
                view.showToastMsg(R.string.common_share_cancel);
            }
        }
    }


}
