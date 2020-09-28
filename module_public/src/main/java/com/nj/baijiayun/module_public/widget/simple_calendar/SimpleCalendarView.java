package com.nj.baijiayun.module_public.widget.simple_calendar;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import com.nj.baijiayun.module_public.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.simple_calendar
 * @describe
 */
public class SimpleCalendarView extends RecyclerView {

    private SimpleCalendarAdapter adapter;
    private int selectIndex;

    public SimpleCalendarView(@NonNull Context context) {
        this(context,null,0);
    }

    public SimpleCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }


    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 7));
        adapter = new SimpleCalendarAdapter(getContext());
        setAdapter(adapter);

        adapter.setOnItemClickListener((holder, position, view, item) -> {

            if (!(item instanceof DayOfMonthBean)) {
                return;
            }
            if (((DayOfMonthBean) item).isBeforeToday()) {
                return;
            }
            if (((DayOfMonthBean) item).isSelected()) {
                return;
            }

            ((DayOfMonthBean) item).setSelected(true);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                if (adapter.getItem(i) instanceof DayOfMonthBean) {
                    if (i != position) {
                        ((DayOfMonthBean) adapter.getItem(i)).setSelected(false);
                    }
                }
            }
            adapter.notifyDataSetChanged();

        });

    }


    public void initData() {
        List<Object> dataArray = new ArrayList<>();
        String[] stringArray = getContext().getResources().getStringArray(R.array.public_simple_calendar_array);
        for (String s : stringArray) {
            dataArray.add(new WeekBean(s));
        }

        List<String> days = WeekDayHelper.getTwoWeekStartThisWeekData("d");

        int todayIndex = WeekDayHelper.getTodayIndex();
        for (int i = 0; i < days.size(); i++) {
            DayOfMonthBean dayOfMonthBean = new DayOfMonthBean();
            dayOfMonthBean.setDay(Integer.parseInt(days.get(i)));
            if (i == todayIndex) {
                dayOfMonthBean.setToday(true);
            } else if (i < todayIndex) {
                dayOfMonthBean.setBeforeToday(true);
            }
            dataArray.add(dayOfMonthBean);
        }
        adapter.addAll(dataArray);


    }
}
