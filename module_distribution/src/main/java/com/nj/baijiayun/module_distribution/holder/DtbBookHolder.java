package com.nj.baijiayun.module_distribution.holder;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_distribution.R;
import com.nj.baijiayun.module_distribution.bean.DtbBookBean;
import com.nj.baijiayun.module_public.consts.ConstsNormal;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.holder
 * @describe
 */
@AdapterCreate
public class DtbBookHolder extends BaseMultipleTypeViewHolder<DtbBookBean>  {

    public DtbBookHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> JumpHelper.jumpBookDetailWithDistribution(getClickModel().getBookId()));


    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.distribution_item_book;
    }

    @Override
    public void bindData(DtbBookBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv_title, model.getName());
        ImageLoader.with(getContext()).load(model.getCoverImg()).rectRoundCorner(ConstsNormal.COVER_ROUND_CORNER).into((ImageView) getView(R.id.iv_cover));
        setText(R.id.tv_desc, model.getAuthor());

        ((PriceTextView) getView(R.id.tv_price)).setPriceWithFree(model.getPrice());
        ((PriceTextView) getView(R.id.tv_price_unline)).setPrice(model.getUnderlinedPrice());

//        setText(R.id.tv_other, model.getAuthor());

        //务必String.valueOf
//        setText(R.id.tv_assemble_number, MessageFormat.format(getContext().getString(R.string.assemble_fmt_assemble_join_number_tag), String.valueOf(model.getUserNum())));

//        AssembleJoinNumberHelper.setCommonInfo(this,model);
        HolderHelper.setProfit(((PriceTextView) getView(R.id.tv_other)),  model);

    }
}
