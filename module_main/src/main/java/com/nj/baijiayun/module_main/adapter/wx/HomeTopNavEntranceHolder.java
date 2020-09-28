package com.nj.baijiayun.module_main.adapter.wx;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.NavBean;
import com.nj.baijiayun.module_main.helper.HomeTabPageHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-10-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx
 * @describe
 */

@AdapterCreate(group = "entrance")
public class HomeTopNavEntranceHolder extends BaseMultipleTypeViewHolder<NavBean> {


    @SuppressLint("ClickableViewAccessibility")
    public HomeTopNavEntranceHolder(ViewGroup parent) {
        super(parent);
        changeViewSize((RecyclerView) parent);
        normalUi();
        setOnTouchListener(R.id.tab_view, (v, event) -> {


            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                pressedUi();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                normalUi();
                jump(getClickModel());
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                normalUi();

            }
            return true;
        });
    }

    private void changeViewSize(RecyclerView parent) {
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
//        int itemWidth = (DensityUtil.getScreenWidth() - DensityUtil.dip2px(8 * (spanCount - 1) + 15 * (spanCount - 1))) / spanCount;
        int itemWidth = DensityUtil.getScreenWidth() / spanCount;
        ViewGroup.LayoutParams layoutParams = getView(R.id.tab_view).getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth;
        getView(R.id.tab_view).setLayoutParams(layoutParams);
    }

    private void jump(NavBean clickModel) {
        if (ClickUtils.isFastDoubleClick()) {
            return;
        }

        HomeTabPageHelper.checkHomeTabOrJumpNewPageByRouter(clickModel.getRouter());
//        clickModel.tryJump();

    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_entrance_v2;
    }


    @Override
    public void bindData(NavBean model, int position, BaseRecyclerAdapter adapter) {
        setText(R.id.tv_name, model.getName());
        ImageLoader.with(getContext()).load(model.getNavImg()).into((ImageView) getView(R.id.iv_icon));
    }

    private void normalUi() {
//        getView(R.id.tab_view).setBackgroundResource(R.drawable.main_shap_home_top_tab_normal);
//        getView(R.id.tab_view).setBackgroundResource(R.color.bjplayer_color_ad_blue);
//        getView(R.id.tab_view).setBackground(null);
//        ((ImageView) getView(R.id.iv_icon)).setColorFilter(null);
//        ((TextView) getView(R.id.tv_name)).setTextColor(ContextCompat.getColor(getContext(), R.color.public_FF8C8C8C));

    }

    private void pressedUi() {
//        getView(R.id.tab_view).setBackgroundResource(R.drawable.main_shap_home_top_tab_pressed);
//        getView(R.id.tab_view).setBackgroundResource(R.color.bjplayer_color_ad_blue);
//        ((ImageView) getView(R.id.iv_icon)).setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
//        ((TextView) getView(R.id.tv_name)).setTextColor(Color.WHITE);

    }
}
