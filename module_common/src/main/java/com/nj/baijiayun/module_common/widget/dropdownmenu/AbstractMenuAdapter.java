package com.nj.baijiayun.module_common.widget.dropdownmenu;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget
 * @describe
 */
public abstract class AbstractMenuAdapter implements MenuAdapter {

    private OnFilterDoneListener onFilterDoneListener;


    public OnFilterDoneListener getOnFilterDoneListener() {
        return onFilterDoneListener;
    }

    public AbstractMenuAdapter setOnFilterDoneListener(OnFilterDoneListener onFilterDoneListener) {
        this.onFilterDoneListener = onFilterDoneListener;
        return this;
    }

    public interface OnFilterDoneListener<T> {
        void onFilterDone(T result);
    }
}
