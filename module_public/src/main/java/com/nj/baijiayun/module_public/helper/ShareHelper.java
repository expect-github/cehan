package com.nj.baijiayun.module_public.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.config.ShapeTypeConfig;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.mvp.BaseView;
import com.nj.baijiayun.module_common.widget.dialog.CommonShareDialog;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.ShareImgResponse;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.share_login.JPushHelper;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.jiguang.share.android.api.Platform;

/**
 * @author chengang
 * @date 2019-11-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe 这个需要优化一下  分离业务逻辑
 */
public class ShareHelper {


    private CommonShareDialog commonShareDialog;
    private ShareInfo mShareInfo;
    private boolean isCreateImgSuccess;
    private File downloadFile;
    private String saveFileName;
    private Activity activity;
    private boolean needOpenImgCreateShare = false;


    // &invite_uid=xx&from== 2微信 3朋友圈 4QQ

    public void showDialog(Activity activity, BaseView baseView, ShareInfo shareInfo) {
        showDialog(activity, baseView, shareInfo, null);
    }


    public void showDialog(Activity activity, BaseView baseView, ShareInfo shareInfo, String api) {
        if (!TextUtils.isEmpty(api)) {
            needOpenImgCreateShare = true;
        }
        //注意这里，相同activity 不去重建对话框
        if (this.activity != activity) {
            commonShareDialog = null;
            try {
                mShareInfo = (ShareInfo) shareInfo.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            isCreateImgSuccess = false;
            downloadFile = null;
            saveFileName = "";
        }
        this.activity = activity;
        if (commonShareDialog == null) {
            commonShareDialog = new CommonShareDialog(activity,needOpenImgCreateShare) {
                @Override
                public int bindLayout() {
                        return R.layout.public_layout_share_create_img_dialog;
                }
            };
            commonShareDialog.setAutoDismiss(false);
            commonShareDialog.setOnItemClickListener((position, view, item) -> {
                if (item.getType() == ShapeTypeConfig.IMG) {
                    if (!isCreateImgSuccess()) {
                        getShareImgAndCreateImg(item, baseView, api);
                        return;
                    }
                    saveImg(activity);
                    return;
                }
                if (needOpenImgCreateShare) {
                    mShareInfo.setUrl(createShareUrl(mShareInfo.getUrl(), item.getType()));
                }
                JPushHelper.platFormShare(activity, mShareInfo, item, new JPushHelper.ShareListener(baseView) {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        super.onComplete(platform, i, hashMap);
                    }
                });
                commonShareDialog.dismiss();
            });

        }
        commonShareDialog.show();


    }

    //仅仅在 邀请页面有
    private String createShareUrl(String url, ShapeTypeConfig type) {
        if (url == null) {
            return "";
        }
        int uid = 0;
        if (AccountHelper.getInstance().getInfo() != null) {
            uid = AccountHelper.getInstance().getInfo().getId();
        }
        String extra = MessageFormat.format("invite_uid={0}&join_from={1}", String.valueOf(uid), String.valueOf(getFrom(type)));
        if (url.contains("?")) {
            return url + "&" + extra;
        }
        return ConstsH5Url.getUrl(url + "?" + extra);
    }

    private int getFrom(ShapeTypeConfig type) {
        int from = 0;
        if (type == ShapeTypeConfig.WX) {
            from = 2;
        } else if (type == ShapeTypeConfig.WXP) {
            from = 3;
        } else if (type == ShapeTypeConfig.QQ) {
            from = 4;
        } else if (type == ShapeTypeConfig.QQZONE) {
            from = 5;
        }
        return from;
    }

    private void saveImg(Activity activity) {
        FileHelper.saveGlideImage(activity, downloadFile, saveFileName);
    }

    private void getShareImgAndCreateImg(CommonShareDialog.ShareBean item, BaseView baseView, String api) {
        ToastUtil.shortShow(BaseApp.getInstance(), "正在生成分享图片");
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getShareImg(api)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<ShareImgResponse>() {
                    @Override
                    public void onPreRequest() {
                        baseView.showLoadV();
                    }

                    @Override
                    public void onSuccess(ShareImgResponse shareImgResponse) {
                        baseView.closeLoadV();
                        String shareImg = shareImgResponse.getData().getShareImg();
                        if (shareImg != null && shareImg.contains("/")) {
                            saveFileName = shareImg.substring(shareImg.lastIndexOf("/") + 1);
                        } else {
                            saveFileName = "share.png";
                        }
                        mShareInfo.setImage(shareImg);
                        showImg();
                    }

                    private void showImg() {
                        Glide.with(commonShareDialog.getContext())
                                .downloadOnly()
                                .load(mShareInfo.getImage())
                                .into(new CustomTarget<File>() {
                                    @Override
                                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                        isCreateImgSuccess = true;
                                        downloadFile = resource;
                                        if (mShareInfo != null) {
                                            mShareInfo.setLocalImgPath(resource.getPath());
                                        }
                                        item.setName("保存图片");
                                        item.setRes(R.drawable.public_ic_share_save);
                                        commonShareDialog.getAdapter().notifyDataSetChanged();
                                        Glide.with(commonShareDialog.getContext()).load(resource.getPath()).into(new CustomTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                                int ivWidth = getShareImg().getWidth();
                                                int ivHeight = getShareImg().getHeight();
                                                ViewGroup.LayoutParams layoutParams = getShareImg().getLayoutParams();
                                                if (ivWidth < ivHeight) {
                                                    ivHeight = ivWidth * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                                                    layoutParams.height = ivHeight;
                                                } else {
                                                    ivWidth = ivHeight * resource.getIntrinsicWidth() / resource.getIntrinsicHeight();
                                                    layoutParams.width = ivWidth;
                                                }
                                                getShareImg().setLayoutParams(layoutParams);
                                                getShareImg().setImageDrawable(resource);
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                        Logger.d("glideload onLoadCleared");

                                    }

                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                        // Intentionally empty, this can be optionally implemented by subclasses.
                                        Logger.d("glideload onLoadFailed");
                                    }
                                });
                    }

                    @Override
                    public void onFail(Exception e) {
                        baseView.closeLoadV();
                        ToastUtil.shortShow(BaseApp.getInstance(), e.getMessage());
                    }
                });


    }

    public boolean isCreateImgSuccess() {
        return isCreateImgSuccess;
    }

    private Context getContext() {
        return commonShareDialog.getContext();
    }

    private ImageView getShareImg() {
        return commonShareDialog.findViewById(R.id.iv_share_cover);
    }
}
