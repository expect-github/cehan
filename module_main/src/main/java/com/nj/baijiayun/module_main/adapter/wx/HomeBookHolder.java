package com.nj.baijiayun.module_main.adapter.wx;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.BookBean;
import com.nj.baijiayun.module_public.consts.ConstsNormal;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewSplitLineHelper;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx
 * @describe 拼团booK一致
 */
@AdapterCreate
public class HomeBookHolder extends BaseMultipleTypeViewHolder<BookBean> {
    private final String fmtStr;

    public HomeBookHolder(ViewGroup parent) {
        super(parent);
        fmtStr = getContext().getString(com.nj.baijiayun.module_assemble.R.string.design_fmt_author);
        getConvertView().setOnClickListener(v -> JumpHelper.jumpBookDetail(getClickModel().getId()));

    }

    @Override
    public int bindLayout() {
        return R.layout.assemble_item_book_v1;
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public void bindData(BookBean model, int position, BaseRecyclerAdapter adapter) {
        ViewSplitLineHelper.setLineVisible(this,position);
        setVisible(R.id.tv_assemble_number,false);
        setVisible(R.id.tv_go_buy,false);
        setVisible(R.id.ll_time_range,false);

        ((CourseTitleView)getView(R.id.tv_title))
                .setHasCoupon(model.hasCoupon())
                .showTag(model.getTitle());
        setText(R.id.tv_abstract, model.getInstruction());
        ImageLoader.with(getContext()).load(model.getCover()).rectRoundCorner(ConstsNormal.COVER_ROUND_CORNER).into((ImageView) getView(R.id.iv_cover));

        ((PriceTextView) getView(R.id.tv_price)).inListShow().setPrice(String.valueOf(model.getPrice()));
        ((PriceTextView) getView(R.id.tv_price_underline)).inListShow().needMidLine().setPrice(String.valueOf(model.getUnderlinedPrice()));
        setText(R.id.tv_author,String.format(fmtStr,model.getAuthor()));

    }
}
