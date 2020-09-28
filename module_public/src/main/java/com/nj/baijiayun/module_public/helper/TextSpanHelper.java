package com.nj.baijiayun.module_public.helper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe 待修改的类
 */
public class TextSpanHelper {
    /**
     * @param tv
     * @param resId
     * @param sizeDp
     * @param placeholder 占位空格
     */
    public static void insertImgAtHead(TextView tv, int resId, int sizeDp, String placeholder) {
        insertImgAtHead(tv, resId, sizeDp, sizeDp, placeholder);

    }

    public static void insertImgAtHead(TextView tv, int resId, float width, float height, String placeholder) {

        Bitmap bitmap = BitmapFactory.decodeResource(tv.getContext().getResources(), resId);

        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        drawable.setBounds(0, 0, DensityUtil.dip2px(width), DensityUtil.dip2px(height));
        CustomImageSpan imgSpan = new CustomImageSpan(drawable, CustomImageSpan.ALIGN_FONTCENTER);
        SpannableStringBuilder spanImageBuilder = new SpannableStringBuilder("logo");
        spanImageBuilder.setSpan(imgSpan, 0, spanImageBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanImageBuilder.append(placeholder);
        spanImageBuilder.append(tv.getText());
        tv.setText(spanImageBuilder);

    }


//    public static void insertImgAtHead(TextView tv, int resId, int width, int height, String placeholder) {
//        insertImgAtHead(tv, resId, width, height, placeholder);
//
//    }


    public static SpannableString matcherSearchKeyWord(int color, String text, String keyword) {
        String string = text.toLowerCase();
        String key = keyword.toLowerCase();
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(string);
        SpannableString ss = new SpannableString(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }


    public static ForegroundColorSpan getTextColorSpan(int color) {
        return new ForegroundColorSpan(color);
    }

    public static AbsoluteSizeSpan getTextSizeSpan(int dp) {
        return new AbsoluteSizeSpan(dp, true);
    }

    public static AbsoluteSizeSpan getTextSizeSpanPx(int px) {
        return new AbsoluteSizeSpan(px, false);
    }

    public static UnderlineSpan getUnderlineSpan() {
        return new UnderlineSpan();
    }

    public static StyleSpan getBoldSpan() {
        return new StyleSpan(Typeface.BOLD);

    }

    public static SpannableString matcherSearchKeyWord(String text, String keyword, Object... what) {
        return matcherSearchKeyWord(new SpannableString(text), text, keyword, what);
    }

    public static SpannableString matcherSearchKeyWordNoToLowCase(String text, String keyword, Object... what) {
        return matcherSearchKeyWordNoToLowCase(new SpannableString(text), text, keyword, what);
    }


    public static SpannableString matcherSearchKeyWord(SpannableString ss, String text, String keyword, Object... what) {
        String string = text.toLowerCase();
        String key = keyword.toLowerCase();
        return getSpannableString(ss, string, key, what);
    }

    private static SpannableString getSpannableString(SpannableString ss, String string, String key, Object[] what) {
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            for (Object o : what) {
                ss.setSpan(o, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ss;
    }

    public static SpannableString matcherSearchKeyWordNoToLowCase(SpannableString ss, String text, String keyword, Object... what) {

        return getSpannableString(ss, text, keyword, what);
    }


}
