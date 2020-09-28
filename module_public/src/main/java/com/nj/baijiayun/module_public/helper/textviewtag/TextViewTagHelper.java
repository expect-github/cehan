package com.nj.baijiayun.module_public.helper.textviewtag;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import com.nj.baijiayun.module_public.helper.textviewtag.tags.CouponTag;
import com.nj.baijiayun.module_public.helper.textviewtag.tags.CourseTypeTag;
import com.nj.baijiayun.module_public.helper.textviewtag.tags.SpellTag;
import com.nj.baijiayun.module_public.helper.textviewtag.tags.VipTag;
import com.nj.baijiayun.module_public.widget.IconTextSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2020-03-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class TextViewTagHelper {

    private AbstractTagAttr vipTag;
    private AbstractTagAttr couponTag;
    private AbstractTagAttr courseTypeTag;
    private AbstractTagAttr spellTag;
    private static volatile TextViewTagHelper singleton = null;
    private List<IconTextSpan> result;

    private TextViewTagHelper() {
        result = new ArrayList<>();
    }

    public static TextViewTagHelper getInstance() {
        if (singleton == null) {
            synchronized (TextViewTagHelper.class) {
                if (singleton == null) {
                    singleton = new TextViewTagHelper();
                }
            }
        }
        return singleton;
    }


    public TextViewTagHelper bind() {
        if (result == null) {
            result = new ArrayList<>();
        }
        result.clear();
        return this;
    }

    public TextViewTagHelper setVip(boolean isVip) {
        if (isVip) {
            result.add(getVipTag().getIconTextSpan());
        }
        return this;
    }


    public TextViewTagHelper setCoupon(boolean hasCoupon) {

        if (hasCoupon) {
            result.add(getCouponTag().getIconTextSpan());
        }
        return this;
    }


    public TextViewTagHelper setSpell(boolean isSpell) {
        if (isSpell) {
            result.add(getSpellTag().getIconTextSpan());
        }
        return this;
    }

    public TextViewTagHelper setCourseType(String courseType) {
        if (!TextUtils.isEmpty(courseType)) {
            getCourseTypeTag().getIconTextSpan().setText(courseType);
            result.add(getCourseTypeTag().getIconTextSpan());
        }
        return this;
    }

    public void show(TextView textView, String text) {
        show(textView, text, true);
    }

    public void show(TextView textView, String text, boolean isAddTagHead) {

        IconTextSpan[] iconTextSpans = null;
        if (result.size() > 0) {
            iconTextSpans = new IconTextSpan[result.size()];
            for (int i = 0; i < result.size(); i++) {
                iconTextSpans[i] = result.get(i);
            }
        }
        addTag(textView, text, isAddTagHead, iconTextSpans);
    }


    public void addTag(TextView textView, String txt, boolean isAddTagHead, IconTextSpan... iconTextSpan) {
        if (txt == null) {
            txt = "";
        }
        if (iconTextSpan == null) {
            textView.setText(txt);
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        IconTextSpan[] target = new IconTextSpan[iconTextSpan.length];
        int index = 0;
        if (!isAddTagHead) {
            stringBuilder.append(txt);
        }
        for (IconTextSpan textSpan : iconTextSpan) {
            if (textSpan != null) {
                stringBuilder.append(" ");
                target[index++] = textSpan;
            }
        }
        if (isAddTagHead) {
            stringBuilder.append(txt);
        }


        spannableStringBuilder.clear();
        spannableStringBuilder.append(stringBuilder.toString());
        for (int i = 0; i < target.length; i++) {
            if (target[i] == null) {
                break;
            }
            spannableStringBuilder.setSpan(target[i], isAddTagHead ? i : i + txt.length(), isAddTagHead ? i + 1 : i + txt.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannableStringBuilder);
    }

    private AbstractTagAttr getVipTag() {
        if (vipTag == null) {
            vipTag = new VipTag();
        }
        return vipTag;
    }

    private AbstractTagAttr getCouponTag() {
        if (couponTag == null) {
            couponTag = new CouponTag();
        }
        return couponTag;
    }

    private AbstractTagAttr getSpellTag() {
        if (spellTag == null) {
            spellTag = new SpellTag();
        }
        return spellTag;
    }

    private AbstractTagAttr getCourseTypeTag() {
        if (courseTypeTag == null) {
            courseTypeTag = new CourseTypeTag();
        }

        return courseTypeTag;
    }

}
