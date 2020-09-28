package com.nj.baijiayun.module_public.fragment;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.ConstsProtocol;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author chengang
 * @date 2019-09-25
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.fragment
 * @describe
 */
public class LoginDelegate {


    private CheckBox mProtocolCb;
    private TextView mProtocolTv;
    private Context context;


    public void initView(View view) {
        this.context = view.getContext();
        mProtocolCb = view.findViewById(R.id.cb_protocol);
        mProtocolTv = view.findViewById(R.id.tv_protocol);
    
    }

    private boolean isAgree() {
        return mProtocolCb.isChecked();
    }

    public boolean checkAgreePass(){

        if(!isAgree())
        {
            ToastUtil.shortShow(context,"你需要同意用户注册协议和隐私保护协议");
            return false;
        }
        return true;
    }


    public void registerListener() {
        SpannableString spannableString = TextSpanHelper.matcherSearchKeyWord(context.getString(R.string.public_login_protocol),
                context.getString(R.string.public_login_protect_protocol), new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getProtocol(ConstsProtocol.PROTECT));
                    }
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(ContextCompat.getColor(context, R.color.common_main_color));
                        ds.setUnderlineText(true);

                    }

                });

        TextSpanHelper.matcherSearchKeyWord(spannableString, context.getString(R.string.public_login_protocol),
                context.getString(R.string.public_login_register_protocol), new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getProtocol(ConstsProtocol.REGISTER));

                    }
                }, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.common_main_color)));
        
        mProtocolTv.setMovementMethod(LinkMovementMethod.getInstance());
        mProtocolTv.setText(spannableString);
        
    }

    /**
     * 释放对象  (registerListener 有可能导致内存泄漏)
     */
    public void release() {
        context =null;
        mProtocolCb=null;
        mProtocolTv=null;

//        new FutureTask<>()
    }
}
