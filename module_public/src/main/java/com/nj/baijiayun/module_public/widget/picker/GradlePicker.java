package com.nj.baijiayun.module_public.widget.picker;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.module_public.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.qqtheme.framework.picker.WheelPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/22 下午4:31
 * @change
 * @time
 * @describe
 */
public class GradlePicker<T> extends WheelPicker {
    private static final int ITEM_WIDTH_UNKNOWN = -99;
    private List<T> items = new ArrayList<>();
    private List<String> itemStrings = new ArrayList<>();
    private WheelView wheelView;
    private OnWheelListener<T> onWheelListener;
    private OnItemPickListener<T> onItemPickListener;
    private int selectedItemIndex = 0;
    private String label = "";
    private int itemWidth = ITEM_WIDTH_UNKNOWN;

    public GradlePicker(Activity activity, T[] items) {
        this(activity, Arrays.asList(items));
    }

    public GradlePicker(Activity activity, List<T> items) {
        super(activity);
        setItems(items);
    }

    /**
     * 添加数据项
     */
    public void addItem(T item) {
        items.add(item);
        itemStrings.add(formatToString(item));
    }

    /**
     * 移除数据项
     */
    public void removeItem(T item) {
        items.remove(item);
        itemStrings.remove(formatToString(item));
    }

    /**
     * 设置数据项
     */
    public void setItems(T[] items) {
        setItems(Arrays.asList(items));
    }

    /**
     * 设置数据项
     */
    public void setItems(List<T> items) {
        if (null == items || items.size() == 0) {
            return;
        }
        this.items = items;
        for (T item : items) {
            itemStrings.add(formatToString(item));
        }
        if (null != wheelView) {
            wheelView.setItems(itemStrings, selectedItemIndex);
        }
    }

    /**
     * 设置显示的单位，如身高为cm、体重为kg
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 设置默认选中的项的索引
     */
    public void setSelectedIndex(int index) {
        if (index >= 0 && index < items.size()) {
            selectedItemIndex = index;
        }
    }

    /**
     * 设置默认选中的项
     */
    public void setSelectedItem(@NonNull T item) {
        setSelectedIndex(itemStrings.indexOf(formatToString(item)));
    }

    /**
     * 设置选项的宽(dp)
     */
    public void setItemWidth(int itemWidth) {
        if (null != wheelView) {
            ViewGroup.LayoutParams params = wheelView.getLayoutParams();
            params.width = ConvertUtils.toPx(activity, itemWidth);
            wheelView.setLayoutParams(params);
        } else {
            this.itemWidth = itemWidth;
        }
    }

    /**
     * 设置滑动过程监听器
     */
    public void setOnWheelListener(OnWheelListener<T> onWheelListener) {
        this.onWheelListener = onWheelListener;
    }

    /**
     * 设置确认选择监听器
     */
    public void setOnItemPickListener(OnItemPickListener<T> listener) {
        this.onItemPickListener = listener;
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        if (items.size() == 0) {
            throw new IllegalArgumentException("Items can't be empty");
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);

        wheelView = createWheelView();
        layout.addView(wheelView);

        if (TextUtils.isEmpty(label)) {
            wheelView.setLayoutParams(new LinearLayout.LayoutParams(screenWidthPixels, WRAP_CONTENT));
        } else {
            wheelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            TextView labelView = createLabelView();
            labelView.setText(label);
            layout.addView(labelView);
        }

        wheelView.setItems(itemStrings, selectedItemIndex);
        wheelView.setDividerConfig(null);
        wheelView.setTextColor(getContext().getResources().getColor(com.nj.baijiayun.module_common.R.color.common_main_text_color_content),
                getContext().getResources().getColor(com.nj.baijiayun.module_common.R.color.common_main_text_color_title));
        wheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                selectedItemIndex = index;
                if (onWheelListener != null) {
                    onWheelListener.onWheeled(selectedItemIndex, items.get(index));
                }
            }
        });
        if (itemWidth != ITEM_WIDTH_UNKNOWN) {
            ViewGroup.LayoutParams params = wheelView.getLayoutParams();
            params.width = ConvertUtils.toPx(activity, itemWidth);
            wheelView.setLayoutParams(params);
        }


        TextView submitButton = getSubmitButton();
        submitButton.setTextColor(getContext().getResources().getColorStateList(R.color.public_pick_selector));
        TextView cancelButton = getCancelButton();
        cancelButton.setTextColor(getContext().getResources().getColorStateList(R.color.public_pick_selector));

        return layout;
    }

    private String formatToString(T item) {
        if (item instanceof Float || item instanceof Double) {
            return new DecimalFormat("0.00").format(item);
        }
        return item.toString();
    }

    @Override
    public void onSubmit() {
        if (onItemPickListener != null) {
            onItemPickListener.onItemPicked(selectedItemIndex, getSelectedItem());
        }
    }

    public T getSelectedItem() {
        return items.get(selectedItemIndex);
    }

    public int getSelectedIndex() {
        return selectedItemIndex;
    }

    public WheelView getWheelView() {
        return wheelView;
    }

    public interface OnItemPickListener<T> {

        void onItemPicked(int index, T item);

    }

    public interface OnWheelListener<T> {

        void onWheeled(int index, T item);

    }

}
