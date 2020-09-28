package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;

import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;

import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class CourseTitleView extends androidx.appcompat.widget.AppCompatTextView {


    public CourseTitleView(Context context) {
        this(context, null, 0);
    }

    public CourseTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextSize(15);
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        setTextColor(ContextCompat.getColor(context, R.color.public_coupon_get_new));
        //IconTextSpan 相关
        setLayerType(LAYER_TYPE_HARDWARE, null);

    }

    @Deprecated
    private void addCoupon() {
        TextSpanHelper.insertImgAtHead(this, R.drawable.public_ic_coupon, 16, " ");
    }

    private void addIcon(int iconRes) {
        TextSpanHelper.insertImgAtHead(this, iconRes, 17.5f, 16f, " ");
    }


    SpannableStringBuilder spannableStringBuilder;

    //传入的不能为空
    @Deprecated
    public void addTag(String txt, IconTextSpan... iconTextSpan) {
        if (iconTextSpan == null) {
            setText(txt);
            return;
        }
        if (spannableStringBuilder == null) {
            spannableStringBuilder = new SpannableStringBuilder();
        }
        StringBuilder stringBuilder = new StringBuilder();
        IconTextSpan[] target = new IconTextSpan[iconTextSpan.length];
        int index = 0;
        for (IconTextSpan textSpan : iconTextSpan) {
            if (textSpan != null) {
                stringBuilder.append(" ");
                target[index++] = textSpan;
            }
        }
        stringBuilder.append(txt);
        spannableStringBuilder.clear();
        spannableStringBuilder.append(stringBuilder.toString());
        for (int i = 0; i < target.length; i++) {
            if (target[i] == null) {
                break;
            }
            spannableStringBuilder.setSpan(target[i], i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannableStringBuilder);
    }

    public void clearText() {
        setText("");
    }

    private boolean isHasCoupon;
    private boolean isHasAssemble;

    @Deprecated
    public void showVip(boolean isHas) {

    }

    @Deprecated
    public void showCoupon(boolean isHas) {

    }

    public CourseTitleView setHasAssemble(boolean hasAssemble) {
        isHasAssemble = hasAssemble;
        return this;
    }

    public CourseTitleView setHasCoupon(boolean hasCoupon) {
        isHasCoupon = hasCoupon;
        return this;
    }

    public void showTag(String text) {
        setText(text);
        if (isHasAssemble) {
            addIcon(R.drawable.public_ic_tag_assemble);
        }
        if (isHasCoupon) {
            addIcon(R.drawable.public_ic_tag_coupon);
        }
    }
}
