package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.helper.PriceIconHelper;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe 这个类待重构，重构文字效果  xxx5000xxx
 */
public class PriceTextView extends AppCompatTextView {
    /**
     * 需要颜色过滤
     */
    public static final boolean USE_TXT_SIGN = false;
    public static final String UNIT_PRICE = "¥ ";
    public static final String POINT = ".";
    public static final String CN_TEN_THOUSAND = "万";
    public static final float UNIT_AND_POINT_NUMBER_SCALE = (float) (13.0 / 18);
    @Deprecated
    private boolean needColorFilter = true;
    private boolean needBoldPrice;
    private int priceColor;
    private int defaultTextColor;
    private StyleSpan boldSpan;
    private Paint mMidLinePaint;
    private float priceTextSize;
    //显示短价格 xxx万  xxxxx
    private boolean isShowShortPrice;
    private boolean needMidLine = false;
    private int freeTextSize;
    private int freeTextColor;
    private int unitHeight;
    private boolean needShowFreeWhenPriceZero = true;
    private boolean needUnitSmall = false;
    private boolean needAfterPointNumberSmall = false;

    public boolean isUseOnlyStringUnitSign() {
        return USE_TXT_SIGN;
    }

    public PriceTextView(Context context) {
        this(context, null, 0);
    }

    public PriceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PriceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        this.priceTextSize = getTextSize();
        freeTextSize = 16;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PriceTextView);
        needMidLine = typedArray.getBoolean(R.styleable.PriceTextView_need_mid_line, false);
        typedArray.recycle();
    }

    /**
     * 这个方法是设置价格，如果有免费 会显示免费
     * Deprecated 名字写的不知道干嘛
     *
     * @param price
     */
    @Deprecated
    public void setDefaultPrice(String price) {
        setPrice(price);
    }


    /**
     * 设置价格 带有免费的逻辑
     *
     * @param price
     */
    @Deprecated
    public void setPriceWithFree(String price) {
        setPrice(price);
    }

    /**
     * 这个方法是设置价格
     * Deprecated 名字写的不知道干嘛
     *
     * @param price 这个price 是数字
     */
    @Deprecated
    public void setPriceWithFmtTxt(String price) {

        if (!initPriceWithAttrAndCheckContinue(price)) {
            return;
        }
        Logger.d("setPriceWithFmtTxt-->" + price);
        String priceNumberText = getPriceArrayReturnPriceFormatAndPriceNumber(price);
        setDefaultPrice(priceNumberText, priceNumberText);
    }

    public void setPrice(String price) {
        setPriceWithFmtTxt(price);
    }

    public void setPrice(long price) {
        setPrice(String.valueOf(price));
    }

    private boolean initPriceWithAttrAndCheckContinue(String price) {
        if (needMidLine) {
            boolean needMidLinePrice = PriceHelper.parsePriceStr(price) > 0;
            setVisibility(needMidLinePrice ? VISIBLE : GONE);
            if (!needMidLinePrice) {
                return false;
            }
        }
        if (needShowFreeWhenPriceZero && PriceHelper.parsePriceStr(price) <= 0) {
            setText("免费");
            setTextSize(freeTextSize);
            setCompoundDrawables(null, null, null, null);
            return false;
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, priceTextSize);
//        defaultTextColor = defaultTextColor == 0 ? ContextCompat.getColor(getContext(), R.color.public_price) : defaultTextColor;
//        setTextColor(defaultTextColor);
        return true;

    }

    /**
     * @param priceFormatText 完整的显示字符串
     * @param priceNumberText 价格 比如：单买8000  str为 单买8000  keyWord为 8000  所有的实现走这个 这keyWord 是format之后的
     *                        <p>
     *                        单买$8000 $8000
     */
    public void setDefaultPrice(String priceFormatText, String priceNumberText) {

        if (priceFormatText == null) {
            return;
        }
        String priceNumberWithUnitText = UNIT_PRICE + priceNumberText;
        String priceFormatWithUnitText = priceFormatText.replace(priceNumberText, priceNumberWithUnitText);

        if (isUseOnlyStringUnitSign() || ConfigManager.getInstance().getAppConfig().isNoSvg()) {
            setTextViewContent(priceNumberWithUnitText, priceFormatWithUnitText);
            return;
        }
        //获取drawable bold 为预留的
        Drawable vectorDrawable = PriceIconHelper.getVectorDrawable(ConfigManager.getInstance().getAppConfig().getPriceSvg(),
                getPriceColor(),
                getFontHeight(),
                needBoldPrice,
                (!needMidLine && needUnitSmall) ? UNIT_AND_POINT_NUMBER_SCALE : 1);

        setTextViewContent(priceNumberWithUnitText, priceFormatWithUnitText, vectorDrawable);

    }

//    1、价格为：xxxx.yz（其中y不为0，z不为0）时 ，则显示为：xxxx.yz;
//2、价格为：xxxx.yz（其中y不为0，z为0）时 ，则显示为：xxxx.y；
//            3、价格为：xxxx.yz（其中y为0，z不为0）时 ，则显示为：xxxx.yz；
//            4、价格为：xxxx.yz（其中y为0，z为0）时 ，则显示为：xxxx；
//            5、价格整数部分超过五位数时，如价格为abcdef，则显示为ab.cd万，不用四舍五入
//
//    ps：课程详情页显示具体的详细价格 xx6000xx --> 6000,xx6000xx   600-->{600,600}  600000000 {6万，60000}

//isShowShortPrice 暂时不显示万
    private String getPriceArrayReturnPriceFormatAndPriceNumber(String price) {
        int priceInt = PriceHelper.parsePriceStr(price);
        String  priceNumberText;
        isShowShortPrice=false;
        //7位 这个按分来 是否显示万
        if (isShowShortPrice) {
            //1万1.00
            String commonPrice = PriceHelper.getCommonPriceEndOfPointNotZeroNumEnd(priceInt > 999999 ? priceInt / 10000 : priceInt);
            priceNumberText = commonPrice + (priceInt > 999999 ? CN_TEN_THOUSAND : "");
//            priceNumberText = priceFormatTxt;

        } else {
            priceNumberText = PriceHelper.getCommonPrice(price);
//            priceNumberText = priceFormatTxt;
        }
        return priceNumberText;
    }


    private void setTextViewContent(String priceNumberText, String priceFormatWithUnitText) {
        setTextViewContent(priceFormatWithUnitText, priceNumberText, null);
    }

    private AbsoluteSizeSpan textSizeSpanPx;

    private void setTextViewContent(String priceNumberWithUnitText, String priceFormatWithUnitText, Drawable unitDrawable) {

        SpannableString result = new SpannableString(priceFormatWithUnitText);
        if (unitDrawable != null) {
            result = TextSpanHelper.matcherSearchKeyWordNoToLowCase(priceFormatWithUnitText
                    , UNIT_PRICE,
                    new ImageSpan(unitDrawable, DynamicDrawableSpan.ALIGN_BASELINE));
        } else {
            //单纯的¥
            if (!needMidLine && needUnitSmall) {
                result = TextSpanHelper.matcherSearchKeyWordNoToLowCase(priceFormatWithUnitText
                        , UNIT_PRICE,
                        TextSpanHelper.getTextSizeSpanPx((int) (getTextSize() * UNIT_AND_POINT_NUMBER_SCALE)));
            }
        }
        //价格
        if (!needMidLine && needAfterPointNumberSmall && priceNumberWithUnitText.contains(POINT)) {
            int beginIndex = priceNumberWithUnitText.indexOf(POINT);
            String pointText = priceNumberWithUnitText.substring(beginIndex);
            result = TextSpanHelper.matcherSearchKeyWord(result, priceFormatWithUnitText, pointText, getSmallSizeSpan());
        }
        //非下划线价格的
//        if (!needMidLine) {
//            int index = priceFormatWithUnitText.indexOf(CN_TEN_THOUSAND);
//            if (index > 0) {
//                result.setSpan(getSmallSizeSpan(), index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
        result = TextSpanHelper.matcherSearchKeyWord(result, priceFormatWithUnitText, priceNumberWithUnitText, getPriceNumberSpanList());
        setText(result);
    }

    private AbsoluteSizeSpan getSmallSizeSpan() {
        if (textSizeSpanPx == null) {
            textSizeSpanPx = TextSpanHelper.getTextSizeSpanPx((int) (getTextSize() * UNIT_AND_POINT_NUMBER_SCALE));
        }
        return textSizeSpanPx;

    }

    @NotNull
    private Object[] getPriceNumberSpanList() {
        if (needBoldPrice && boldSpan == null) {
            boldSpan = TextSpanHelper.getBoldSpan();
        }
        return new Object[]{needBoldPrice ? boldSpan : null,
                priceColor == 0 ? null : TextSpanHelper.getTextColorSpan(priceColor)};
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (needMidLine) {
            if (mMidLinePaint == null) {
                mMidLinePaint = new Paint();
            }
            mMidLinePaint.setColor(getCurrentTextColor());
            mMidLinePaint.setStrokeWidth(DensityUtil.dip2px(1));
            mMidLinePaint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(0, getHeight() >> 1, getWidth(), getHeight() >> 1, mMidLinePaint);
        }
    }

    public PriceTextView setPriceColor(int priceColor) {
        this.priceColor = priceColor;
        return this;
    }

    public PriceTextView setDefaultTextColorWhite() {
        this.defaultTextColor = Color.WHITE;
        return this;
    }

    public int getPriceColor() {
        if (priceColor == 0) {
            return getCurrentTextColor();
        }
        return priceColor;
    }

    //列表中价格使用 转成xx万显示
    public PriceTextView setShowShortPrice(boolean showShortPrice) {
        isShowShortPrice = showShortPrice;
        return this;
    }


    public PriceTextView setPriceUnitHeightPx(int h) {
        this.unitHeight = h;
        return this;
    }

    public PriceTextView setNeedShowFreeWhenPriceZero(boolean needShowFreeWhenPriceZero) {
        this.needShowFreeWhenPriceZero = needShowFreeWhenPriceZero;
        return this;
    }

    public PriceTextView setNeedUnitSamll(boolean needUnitSamll) {
        this.needUnitSmall = needUnitSamll;
        return this;
    }

    public PriceTextView setNeedAfterPointNumberSamll(boolean needAfterPointNumberSmall) {
        this.needAfterPointNumberSmall = needAfterPointNumberSmall;
        return this;
    }


    public PriceTextView needMidLine() {
        needMidLine = true;
        return this;
    }

    /**
     * 这里算出来不算上只适用于数字的
     */
    private float getFontHeight() {
        if (unitHeight != 0) {
            return unitHeight;
        }
        Paint.FontMetrics fm = getPaint().getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return -(fm.descent + fm.ascent);
    }

    @Deprecated
    public PriceTextView needColorFilter() {
        needColorFilter = true;
        return this;
    }

    public PriceTextView needBoldPrice() {
        needBoldPrice = true;
        return this;
    }

    public PriceTextView setNeedBoldPrice(boolean needBoldPrice) {
        this.needBoldPrice = needBoldPrice;
        return this;
    }

    public PriceTextView inListShow() {
        needUnitSmall = true;
        needAfterPointNumberSmall = true;
        isShowShortPrice=true;
        return this;
    }
}
