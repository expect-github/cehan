package com.nj.baijiayun.module_main.adapter.wx;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.NewsBean;
import com.nj.baijiayun.module_public.consts.ConstsNormal;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewSplitLineHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx
 * @describe
 */
@AdapterCreate
public class NewsHolder extends BaseMultipleTypeViewHolder<NewsBean> {
    public NewsHolder(ViewGroup parent) {
        super(parent);
       getConvertView().setOnClickListener(v -> JumpHelper.jumpNewsDetail(getClickModel().getId()));
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_news_v1;
    }

    @Override
    public void bindData(NewsBean model, int position, BaseRecyclerAdapter adapter) {
        ViewSplitLineHelper.setLineVisible(this,position);
        ImageLoader.with(getContext()).load(model.getCover()).rectRoundCorner(ConstsNormal.COVER_ROUND_CORNER).into((ImageView)getView(R.id.iv_cover));
        setText(R.id.tv_title, model.getTitle());
        setText(R.id.tv_content, model.getAbstractX());
        setText(R.id.tv_browse_num, model.getBrowseNumber() +"人浏览");
        setText(R.id.tv_time, getFormatTime(model.getUpdatedAt()));
    }

    public static String getFormatTime(long time) {
        long difference = (System.currentTimeMillis()/1000 - time) / 60;
        if (difference < 1) {
            return "刚刚";
        } else if (difference < 60) {
            return MessageFormat.format("{0}分钟前", difference);
        } else if ((difference /= 60) < 24) {
            return MessageFormat.format("{0}小时前", difference);
        } else if ((difference < 48)) {
            return "昨天";
        } else if ((difference /= 24) < 30) {
            return MessageFormat.format("{0}天前", difference);
        } else if ((difference /= 30) < 12) {
            return MessageFormat.format("{0}月前", difference);
        } else {
            return MessageFormat.format("{0}年前", difference / 12);
        }
    }
}
