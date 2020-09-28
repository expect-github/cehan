package com.nj.baijiayun.module_public.p_base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.imageloader.config.ScaleMode;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.FileHelper;

import androidx.annotation.NonNull;

/**
 * @author chengang
 * @date 2020-03-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_base
 * @describe
 */
public class ShowQrCodeDialog extends Dialog {
    public ShowQrCodeDialog(@NonNull Context context, String url) {
        super(context, R.style.BasicCommonDialog);
        setContentView(R.layout.public_dialog_show_qrcode);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        View mCloseIv = findViewById(R.id.iv_close);
        mCloseIv.setOnClickListener(v -> dismiss());
        ImageLoader.with(context).scale(ScaleMode.CENTER_CROP).load(url).into(findViewById(R.id.iv_cover));
        findViewById(R.id.iv_cover).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FileHelper.saveImg((Activity) context, url);
                return false;
            }
        });
    }

    public ShowQrCodeDialog hideTopCover() {
        findViewById(R.id.iv_top).setVisibility(View.GONE);
        View viewContent = findViewById(R.id.ll_content);
        View iv = findViewById(R.id.iv_cover);
        RelativeLayout.LayoutParams ivLayoutParams = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        ivLayoutParams.height= DensityUtil.dip2px(265);
        ivLayoutParams.bottomMargin=0;
        ivLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
        iv.setLayoutParams(ivLayoutParams);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewContent.getLayoutParams();
        layoutParams.topMargin = 0;
        viewContent.setLayoutParams(layoutParams);
        return this;
    }
}
