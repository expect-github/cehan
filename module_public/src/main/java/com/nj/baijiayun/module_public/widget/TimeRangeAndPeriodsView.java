package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.nj.baijiayun.module_public.helper.PublicFormatHelper;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class TimeRangeAndPeriodsView extends androidx.appcompat.widget.AppCompatTextView {
    private long startPlay;
    private long endPlay;
    private int periods;
    private int type;
    private ImageView iv;
    private boolean isShowTimeRange = true;
    private boolean isWhenYearDiffNotShowHour;
    private boolean isWhenYearDiffShowShort;
    private boolean needShowIcon = true;

    public TimeRangeAndPeriodsView(Context context) {
        this(context, null);
    }

    public TimeRangeAndPeriodsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeRangeAndPeriodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeRangeAndPeriodsView setIv(ImageView iv) {
        this.iv = iv;
        return this;
    }

    public TimeRangeAndPeriodsView setWhenYearDiffNotShowHour(boolean whenYearDiffNotShowHour) {
        isWhenYearDiffNotShowHour = whenYearDiffNotShowHour;
        isWhenYearDiffShowShort = whenYearDiffNotShowHour;
        return this;
    }

    public void show() {

        StringBuilder stringBuilder = new StringBuilder();
        //时间范围
        String timeByCourseType = "";
        if (isShowTimeRange) {
            timeByCourseType = PublicFormatHelper.getTimeByTimeRange(startPlay, endPlay, !isWhenYearDiffNotShowHour, isWhenYearDiffShowShort);

        }
        stringBuilder.append(timeByCourseType);
        //图标是否显示
        if(iv!=null) {
            iv.setVisibility((needShowIcon && timeByCourseType.length() > 0) ? VISIBLE : GONE);
        }
        //显示课时
        if (periods > 0) {
            if (timeByCourseType.length() > 0) {
                stringBuilder.append(" | ");
            }
            stringBuilder.append(MessageFormat.format("共{0}课时", periods));

        }
        //整个textView 是否显示
        setVisibility(stringBuilder.toString().length() > 0 ? VISIBLE : GONE);
        setText(getVisibility() == VISIBLE ? stringBuilder.toString() : "");


    }


    public long getStartPlay() {
        return startPlay;
    }

    public TimeRangeAndPeriodsView setStartPlay(long startPlay) {
        this.startPlay = startPlay;
        return this;
    }


    public TimeRangeAndPeriodsView setEndPlay(long endPlay) {
        this.endPlay = endPlay;
        return this;

    }

    public TimeRangeAndPeriodsView setShowTimeRanger(boolean isShowTimeRange) {
        this.isShowTimeRange = isShowTimeRange;
        return this;
    }


    public TimeRangeAndPeriodsView setPeriods(int periods) {
        this.periods = periods;
        return this;

    }

    public int getType() {
        return type;
    }

    public TimeRangeAndPeriodsView setType(int type) {
        this.type = type;
        return this;

    }

    public TimeRangeAndPeriodsView noNeedShowIcon() {
        this.needShowIcon = false;
        return this;
    }
}
