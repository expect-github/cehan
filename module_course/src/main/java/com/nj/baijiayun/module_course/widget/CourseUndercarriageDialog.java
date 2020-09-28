package com.nj.baijiayun.module_course.widget;

import android.app.Dialog;
import android.content.Context;

import com.nj.baijiayun.module_course.R;

import androidx.annotation.NonNull;

/**
 * @author chengang
 * @date 2020-03-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.widget
 * @describe
 */
public class CourseUndercarriageDialog extends Dialog {
    public CourseUndercarriageDialog(@NonNull Context context) {
        super(context, R.style.BasicCommonDialog);
        setContentView(R.layout.course_dialog_course_undercarriage);
        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());
        findViewById(R.id.btn_confirm).setOnClickListener(v -> dismiss());
    }
}
