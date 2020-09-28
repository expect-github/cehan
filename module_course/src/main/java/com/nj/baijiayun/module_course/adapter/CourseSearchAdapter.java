package com.nj.baijiayun.module_course.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

/**
 * @author chengang
 * @date 2019-08-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter
 * @describe
 */
public class CourseSearchAdapter extends BaseRecyclerAdapter<String> {

    private int color;

    public CourseSearchAdapter(Context context) {
        super(context);
        color = ContextCompat.getColor(context, R.color.common_646464);

    }

    private String searchKeyWord;

    public void setSearchKeyWord(String searchKeyWord) {
        this.searchKeyWord = searchKeyWord;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.course_item_course_search;
    }

    @Override
    protected void bindViewAndData(BaseViewHolder holder, String s, int position) {
        holder.setText(R.id.textview, TextSpanHelper.matcherSearchKeyWord(color, s, searchKeyWord));
    }
}
