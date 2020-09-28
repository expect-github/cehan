package com.nj.baijiayun.module_assemble.holder;

import android.widget.ImageView;

import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_assemble.R;
import com.nj.baijiayun.module_assemble.bean.AssembleBean;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.module_public.widget.TimeRangeAndPeriodsView;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.holder
 * @describe
 */
public class AssembleJoinNumberHelper {
    private static final int[] IV_HEADS = new int[]{R.id.iv_center, R.id.iv_left, R.id.iv_right};

    static void setJoinHeads(BaseViewHolder baseViewHolder, List<String> heads, int joinNumber) {
        if (baseViewHolder.getView(R.id.cl_join_persons) == null) {
            return;
        }
        if (heads == null || heads.size() == 0 || joinNumber < 0) {
            baseViewHolder.setVisible(R.id.cl_join_persons, false);
            baseViewHolder.setVisible(R.id.tv_join_number, false);
            return;
        }
        baseViewHolder.setVisible(R.id.cl_join_persons, true);
        baseViewHolder.setVisible(R.id.tv_join_number, true);
        baseViewHolder.setText(R.id.tv_join_number, MessageFormat.format(baseViewHolder.getConvertView()
                        .getContext()
                        .getString(R.string.assemble_fmt_assemble_join_number),
                String.valueOf(joinNumber)));
        for (int i = 0; i < IV_HEADS.length; i++) {
            //类型不一致呆滞load 不能用? :
            if (i <= (heads.size() - 1)) {
                ImageLoader.with(baseViewHolder.itemView.getContext())
                        .load(heads.get(i))
                        .asCircle().into((ImageView) baseViewHolder.getView(IV_HEADS[i]));
            } else {
                ImageLoader.with(baseViewHolder.itemView.getContext())
                        .load(R.drawable.public_ic_head_default)
                        .asCircle().into((ImageView) baseViewHolder.getView(IV_HEADS[i]));

            }
        }

    }


    static void setCommonInfo(BaseViewHolder baseViewHolder, AssembleBean model) {

        ((TimeRangeAndPeriodsView) baseViewHolder.getView(com.nj.baijiayun.module_public.R.id.tv_date))
                .setIv((ImageView) baseViewHolder.getView(com.nj.baijiayun.module_public.R.id.iv_clock))
                .setPeriods(0)
                .noNeedShowIcon()
                .setWhenYearDiffNotShowHour(true)
                .setStartPlay(model.getStartPlayDate())
                .setEndPlay(model.getEndPlayDate())
                .show();

        ((CourseTitleView)baseViewHolder.getView(R.id.tv_title))
                .setHasCoupon(model.hasCoupon())
                .showTag(model.getTitle());
        ((PriceTextView) baseViewHolder.getView(R.id.tv_price)).inListShow().setPrice(model.getAssemblePrice());
        ((PriceTextView) baseViewHolder.getView(R.id.tv_price_underline)).inListShow()
                .needMidLine()
                .setPrice(model.getPrice());
        baseViewHolder.setText(R.id.tv_assemble_number, MessageFormat.format(baseViewHolder.getConvertView()
                        .getContext()
                        .getString(R.string.assemble_fmt_assemble_join_number_tag),
                String.valueOf(model.getUserNum())));

    }
}
