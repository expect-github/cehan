package com.nj.baijiayun.module_course.adapter.my_course;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.outline_holder.DatumHolder;

/**
 * @author chengang
 * @date 2019-08-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.my_course
 * @describe
 */
@AdapterCreate(group = "learnedDetail")
public class MyLearnedDatumHolder extends DatumHolder {
    public MyLearnedDatumHolder(ViewGroup parent) {
        super(parent);
    }
    @Override
    public int bindLayout() {
        return R.layout.course_item_outline_datum_withbg;

    }
    
}
