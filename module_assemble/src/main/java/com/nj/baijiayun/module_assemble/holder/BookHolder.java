package com.nj.baijiayun.module_assemble.holder;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_assemble.R;
import com.nj.baijiayun.module_assemble.bean.AssembleBean;
import com.nj.baijiayun.module_public.consts.ConstsNormal;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewSplitLineHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.holder
 * @describe
 */
@AdapterCreate
public class BookHolder extends BaseMultipleTypeViewHolder<AssembleBean> {


    private final String fmtStr;

    public BookHolder(ViewGroup parent) {
        super(parent);
       getConvertView().setOnClickListener(v -> JumpHelper.jumpBookDetail(getClickModel().getBookId()));
        fmtStr = getContext().getString(R.string.design_fmt_author);

    }
    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }


    @Override
    public int bindLayout() {
        return R.layout.assemble_item_book_v1;
    }

    @Override
    public void bindData(AssembleBean model, int position, BaseRecyclerAdapter adapter) {
        ViewSplitLineHelper.setLineVisible(this,position);

//        setText(R.id.tv_book_name, model.getBookName());
        setText(R.id.tv_abstract, model.getInstruction());
        ImageLoader.with(getContext()).load(model.getCoverImg()).rectRoundCorner(ConstsNormal.COVER_ROUND_CORNER).into((ImageView) getView(R.id.iv_cover));
        setText(R.id.tv_author,String.format(fmtStr,model.getAuthor()));
        AssembleJoinNumberHelper.setCommonInfo(this,model);

    }
}
