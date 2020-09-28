package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.nj.baijiayun.module_public.R;

/**
 * @author chengang
 * @date 2019-06-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class TagStatusView extends AppCompatImageView {


    public void statusUnstart() {
        setImageResource(R.drawable.public_ic_status_unstart);
    }

    public void statusIng() {
        setImageResource(R.drawable.public_ic_status_ing);
    }

    public void statusOverDate() {
        setImageResource(R.drawable.public_ic_status_over_date);

    }

    public void statusOther() {
        setImageResource(0);
    }

    public void setStatus(IStatus iStatus) {
        if (iStatus.isUnstart()) {
            statusUnstart();
        } else if (iStatus.isInProgress()) {
            statusIng();
        } else if (iStatus.isOverDate()) {
            statusOverDate();
        } else {
            statusOther();
        }

    }


    public TagStatusView(Context context) {
        super(context);
    }

    public TagStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        statusUnstart();
    }

    public interface IStatus {
        boolean isInProgress();

        boolean isUnstart();

        boolean isOverDate();

        boolean isOther();
    }


}
