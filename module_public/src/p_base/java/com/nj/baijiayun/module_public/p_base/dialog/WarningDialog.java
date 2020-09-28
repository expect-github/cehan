package com.nj.baijiayun.module_public.p_base.dialog;

import android.content.Context;

import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;

import androidx.annotation.NonNull;

/**
 * @author chengang
 * @date 2020-03-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_base
 * @describe
 */
public class WarningDialog extends CommonMDDialog {
    private Context context;

    public WarningDialog(@NonNull Context context) {
        super(context, R.style.BasicCommonDialog);
        this.context = context;
    }

    public void setWarningContent(int resId) {
        setContentTxt(context.getString(resId));
        setPositiveTxt(R.string.confirm)
                .setNegativeTxt(R.string.cancel);
        setOnNegativeClickListener(() -> dismiss());
        TextSpanHelper.insertImgAtHead(getContentTxt(), R.drawable.p_set_warning, 14, " ");
    }
}
