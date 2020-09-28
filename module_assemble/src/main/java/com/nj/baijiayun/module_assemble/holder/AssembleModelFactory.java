package com.nj.baijiayun.module_assemble.holder;

import com.nj.baijiayun.annotations.ModelMultiTypeAdapterCreate;
import com.nj.baijiayun.module_assemble.bean.AssembleBean;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeModelHolderFactory;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.holder
 * @describe
 */
@ModelMultiTypeAdapterCreate
public class AssembleModelFactory extends BaseMultipleTypeModelHolderFactory<AssembleBean> {

    @Override
    public Class<? extends BaseMultipleTypeViewHolder> getMultipleTypeHolderClass(AssembleBean model) {
        return model.getBookId() != 0 ? BookHolder.class : CourseCoverHolder.class;
    }
}
