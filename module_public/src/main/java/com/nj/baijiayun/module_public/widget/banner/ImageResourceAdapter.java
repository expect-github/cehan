package com.nj.baijiayun.module_public.widget.banner;

import android.view.View;
import android.widget.ImageView;

import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_public.R;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

import androidx.annotation.NonNull;


/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:
 * </pre>
 */
public class ImageResourceAdapter<T extends IBannerImage> extends BaseBannerAdapter<T, ImageResourceAdapter.ImageResourceViewHolder> {

    private int roundCornerDp;

    public ImageResourceAdapter() {
        this(0);
    }

    public ImageResourceAdapter(int roundCornerDp) {
        this.roundCornerDp=roundCornerDp;
    }


    @Override
    protected void onBind(ImageResourceViewHolder holder, T data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public ImageResourceViewHolder createViewHolder(View itemView, int viewType) {
        return new ImageResourceViewHolder(itemView,roundCornerDp);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.public_item_banner_image;
    }


  public   static class ImageResourceViewHolder<T extends IBannerImage> extends BaseViewHolder<T> {
        ImageView imageView;
        private int roundCornerDp;

        public ImageResourceViewHolder(@NonNull View itemView) {
            this(itemView, 0);
        }

        @Override
        public void bindData(T data, int position, int pageSize) {
            ImageLoader.with(imageView.getContext()).load(data.getBannerCover()).rectRoundCorner(roundCornerDp).into(imageView);

        }

        public ImageResourceViewHolder(@NonNull View itemView, int roundCornerDp) {
            super(itemView);
            imageView = findView(R.id.banner_image);
            this.roundCornerDp = roundCornerDp;
        }

    }

}
