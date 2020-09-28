package com.nj.baijiayun.module_public.widget.simple_calendar;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.ViewGroup;

import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.MultipleTypeHolderFactory;

/**
 * @author chengang
 * @date 2019-07-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.simple_calendar
 * @describe
 */
public class SimpleCalendarAdapter extends BaseMultipleTypeRvAdapter {
    public SimpleCalendarAdapter(Context context) {
        super(context);
    }

    @Override
    public MultipleTypeHolderFactory createTypeFactory() {
        return new MultipleTypeHolderFactory() {
            @Override
            public BaseMultipleTypeViewHolder createViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 2) {
                    return new DayHolder(parent);
                }
                if (viewType == 1) {
                    return new WeekHolder(parent);

                }
                return null;
            }

            @Override
            public int getViewType(Object object) {
                if (object instanceof WeekBean) {
                    return 1;
                }
                return 2;
            }
        };
    }

    private class WeekHolder extends BaseMultipleTypeViewHolder<WeekBean> {

        WeekHolder(ViewGroup parent) {
            super(parent);
        }

        @Override
        public int bindLayout() {
            return R.layout.public_item_week;
        }

        @Override
        public void bindData(WeekBean model, int position, BaseRecyclerAdapter adapter) {
            setText(R.id.tv_title, model.getWeek());
        }
    }

    private class DayHolder extends BaseMultipleTypeViewHolder<DayOfMonthBean> {

        DayHolder(ViewGroup parent) {
            super(parent);

        }

        @Override
        public int bindLayout() {
            return R.layout.public_item_day_of_month;
        }

        @Override
        public void bindData(DayOfMonthBean model, int position, BaseRecyclerAdapter adapter) {
            setText(R.id.tv_day, String.valueOf(model.getDay()));
            //设置文本内容
            if (model.isToday()) {
                setVisible(R.id.tv_today,true);
                setText(R.id.tv_today, getContext().getString(R.string.public_today));
            } else {
                setVisible(R.id.tv_today,false);

                setText(R.id.tv_today, "");
            }

            if (model.isBeforeToday()) {
                setBackgroundRes(R.id.tv_day, 0);
                setTextColor(R.id.tv_day, ContextCompat.getColor(getContext(), R.color.public_calendar_un_enable));
            } else if (model.isSelected()) {
                setBackgroundRes(R.id.tv_day, R.drawable.public_bg_calendar_selected);
                setTextColor(R.id.tv_day, ContextCompat.getColor(getContext(), R.color.public_calendar_select_text));
            } else {
                if (model.isToday()) {
                    setBackgroundRes(R.id.tv_day, R.drawable.public_bg_calendar_today);
                    setTextColor(R.id.tv_day, ContextCompat.getColor(getContext(), R.color.public_calendar_today_text));
                } else {
                    setBackgroundRes(R.id.tv_day, 0);
                    setTextColor(R.id.tv_day, ContextCompat.getColor(getContext(), R.color.public_calendar_normal_text));

                }
            }

        }
    }


}
