package com.nj.baijiayun.module_common.helper;

import android.content.Context;

import com.nj.baijiayun.basic.widget.dialog.CommonDialog;
import com.nj.baijiayun.module_common.widget.dialog.CommonBottomDialog;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_common.widget.dialog.CommonShareDialog;
import com.nj.baijiayun.module_common.widget.dialog.IosLoadingDialog;




/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget
 * @describe 通用的Dialog工厂
 * - 什么样的Dialog放在这里？ 只有具有一定的复用性或者通用性
 * - 添加Dialog的时候需要加上注释说明，做好也加上使用范例
 * @anthor houyi QQ:1007362137
 * @time 18/11/21 下午5:17
 * @change
 * @time
 * @describe
 */
public class BJYDialogHelper {
    /**
     * 通用的MD风格提示框，最最通用的提示框，所有没有特殊说明的提示都使用这个，
     * 支持简单的自定义布局，但是没有优化高度
     * example：
     * <pre>
     *  CommonMDDialog commonMDDialog = BJYDialogFactory.buildMDDialog(this)
     *          .setTitleTxt(R.string.common_tip)
     *          .setContentTxt(R.string.community_unjoin_tip)
     *          .setNegativeTxt(R.string.common_cancel)
     *          .setPositiveTxt(R.string.common_confirm);
     *  commonMDDialog.setOnPositiveClickListener(new CommonMDDialog.OnPositiveClickListener() {
     *
     *      public void positiveClick() {
     *          commonMDDialog.dismiss();
     *          mPresenter.join(groupInfo);
     *      }
     *  });
     *  commonMDDialog.show();
     * </pre>
     */
    public static CommonMDDialog buildMDDialog(Context context) {
        return new CommonMDDialog(context);
    }


    /**
     * 底部功能弹出框，目前主要应用在拍照上传
     */
    public static CommonBottomDialog buildCommonBottomDialog(Context context) {
        return new CommonBottomDialog(context);
    }

    /**
     * 通用的分享弹框
     */
    public static CommonShareDialog buildShareDialog(Context context) {
        return new CommonShareDialog(context);
    }

    /**
     * 通用的loading框，目前封装在Base类中，一半不需要单独使用
     */
    public static IosLoadingDialog buildLoadingDialog(Context context) {
        return new IosLoadingDialog(context);
    }

    /**
     * 各类自定义内容的对话框，主要定义的对话框的最大宽高的比例
     */
    public static CommonDialog buildCommonDialog(Context context) {
        return new CommonDialog(context);
    }

}
