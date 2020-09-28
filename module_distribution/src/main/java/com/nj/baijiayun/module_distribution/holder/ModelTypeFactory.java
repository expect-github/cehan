package com.nj.baijiayun.module_distribution.holder;

import com.nj.baijiayun.annotations.ModelMultiTypeAdapterCreate;
import com.nj.baijiayun.module_distribution.bean.DtbCourseBean;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeModelHolderFactory;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.holder
 * @describe
 */
@ModelMultiTypeAdapterCreate
public class ModelTypeFactory extends BaseMultipleTypeModelHolderFactory<DtbCourseBean> {
    @Override
    public Class<? extends RecyclerView.ViewHolder> getMultipleTypeHolderClass(DtbCourseBean model) {
//        if (TextUtils.isEmpty(model.getCover())) {
//            return DtbCourseHolder.class;
//        }
        return DtbCourseWIthCoverHolder.class;
    }
}
