package com.nj.baijiayun.module_main.helper;

import android.graphics.Rect;
import android.view.View;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-09-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class BeanSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int headViewCount;
    private BaseRecyclerAdapter adapter;
    private Map<Class, EdgeBean> marginMap = new HashMap<>();
    //待写完

    public Map<Class, EdgeBean> getMarginMap() {
        return marginMap;
    }

    public BeanSpaceItemDecoration addMarginEdge(Class cls, EdgeBean edgeBean) {
        marginMap.put(cls, edgeBean);
        return this;
    }

    public BeanSpaceItemDecoration(BaseRecyclerAdapter baseRecyclerAdapter) {
        this.adapter = baseRecyclerAdapter;
    }

    public BeanSpaceItemDecoration setHeadViewCount(int headViewCount) {
        this.headViewCount = headViewCount;
        return this;
    }

    public BeanSpaceItemDecoration setMargin(int margin) {
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();

            if (spanCount != spanSizeLookup.getSpanSize(pos)) {
                setGridLayoutSpaceItemDecoration(outRect, view, parent, pos, spanCount / spanSizeLookup.getSpanSize(pos));
            } else {
                setLayoutSpaceItemDecoration(outRect, view, pos,parent);
            }
        } else {
            setLayoutSpaceItemDecoration(outRect, view, pos,parent);

        }

    }

    private void setGridLayoutSpaceItemDecoration(Rect outRect, View view, RecyclerView parent, int position, int spanCount) {

        Object currentPosBean = getItem(position);

        Logger.d("currentPosBean" + currentPosBean);
        if (currentPosBean == null) {
            return;
        }
        EdgeBean edgeBean = marginMap.get(currentPosBean.getClass());
        if (edgeBean == null) {
            return;
        }
        //找出
        int realIndex = 0;
        for (int i = position - 1; i >= 0; i--) {
            Object obj = getItem(i);
            if (obj != null && obj.getClass() == currentPosBean.getClass()) {
                realIndex++;
            } else {
                break;
            }
        }
        //当前模型的第几个
        int tmpPos = realIndex;


        //需要找出列 设置左右边距
        int column = tmpPos % spanCount;
        if (edgeBean.needEdge) {
            outRect.left = edgeBean.gridMargin - column * edgeBean.gridMargin / spanCount;
            outRect.right = (column + 1) * edgeBean.gridMargin / spanCount;
        } else {
            outRect.left = column * edgeBean.gridMargin / spanCount;
            outRect.right = edgeBean.gridMargin - (column + 1) * edgeBean.gridMargin / spanCount;
        }

        //超过一行
        if (tmpPos >= spanCount) {
            outRect.top = edgeBean.topMargin;
        } else {
            //不足一行
            outRect.top = edgeBean.preTopMargin;
        }
        //判断下一行不是跟自己相关的
        Object nextRow = getItem(position + spanCount - column);
        if (nextRow == null || nextRow.getClass() != currentPosBean.getClass()) {
            outRect.bottom = edgeBean.nextBottomMargin;
        } else {
            outRect.bottom = edgeBean.bottomMargin;
        }

        edgeBean.handleItem(column, spanCount, view, outRect, parent, position);

        //最后一排显示bottom magin


    }

    public Object getItem(int position) {
        int realDataIndex = position - headViewCount;
        return adapter.getItem(realDataIndex);
    }

    private void setLayoutSpaceItemDecoration(Rect outRect, View view, int pos,RecyclerView parent) {
        Object item = getItem(pos);
        if (item == null) {
            return;
        }
        EdgeBean edgeBean = marginMap.get(item.getClass());
        if (edgeBean == null) {
            return;
        }
        Object pre = getItem(pos - 1);
        Object next = getItem(pos + 1);
        outRect.top = edgeBean.topMargin;
        outRect.bottom = edgeBean.bottomMargin;
        outRect.left = edgeBean.leftMargin;
        outRect.right = edgeBean.rightMargin;

        //前一个跟自己不相同
        if (pre == null || pre.getClass() != item.getClass()) {
            outRect.top = edgeBean.preTopMargin;
            outRect.left = edgeBean.preLeftMargin;
        }
        //后一个跟自己不相同
        if (next == null || next.getClass() != item.getClass()) {
            outRect.bottom = edgeBean.nextBottomMargin;
            outRect.right = edgeBean.nextRightMargin;
        }
        edgeBean.handleItem(0, 0, view, outRect, parent, pos);


    }


    public static class EdgeBean {
        /**
         * 以下注释数字作为间距，--或者|作为显示holder
         * pre,next 指的是跟自己不一样的前一个bean, 或者前一行bean  next就是说的最后一个或下一行跟自己不一样
         * topMargin bottomMargin 一般设置一边就可以了  leftMargin rightMargin 也是，按照一个方向实现就可以了
         * <p>
         * 例子：
         * 0---2---2----1
         * <p>
         * 0:看作preLeftMargin  这个会覆盖你设置的leftMargin
         * 1:看作nextRightMargin
         * 2:看作rightMargin/leftMargin
         * <p>
         * 0
         * |
         * |
         * 2
         * |
         * |
         * 1
         * <p>
         * 0:看作preTopMargin
         * 1:看作nextRightMargin
         * 2:看作TopMargin/bottomMargin
         * <p>
         * 以下参数：这是参数仅仅用于一行多个会显示多行的 left,right相关的margin 不生效，无需要使用
         * gridLayoutManager
         * needEdge 开启左右边距，开启后自动在一行的第一个和最后一个加上左边距和右边距
         * 例如
         * needEdge true：0---0---0
         * needEdge false: ---0---
         * bottomMargin/topMargin preTopMargin/nextBottomMargin 可以作用于行间距
         * gridMargin 指的item之间的左右边距，这个不是使用
         * <p>
         * handleItem方法用来重写特殊间距去求
         */
        int bottomMargin;
        int topMargin;
        int nextBottomMargin;
        int preTopMargin;
        int leftMargin;
        int rightMargin;
        int preLeftMargin;
        int nextRightMargin;
        int gridMargin;
        boolean needEdge = true;

        /**
         * 单行 单列
         *
         * @param topMargin    topMargin
         * @param bottomMargin bottomMargin
         */
        public EdgeBean(int topMargin, int bottomMargin) {
            this.bottomMargin = getMargin(bottomMargin);
            this.topMargin = getMargin(topMargin);
            this.preTopMargin = this.topMargin;
            this.nextBottomMargin = this.bottomMargin;
        }

        public EdgeBean() {
        }

        /**
         * 多行多列表
         *
         * @param gridMargin   gridMargin
         * @param topMargin    topMargin
         * @param bottomMargin bottomMargin
         */
        public EdgeBean(int gridMargin, int topMargin, int bottomMargin) {
            this.gridMargin = getMargin(gridMargin);
            this.bottomMargin = getMargin(bottomMargin);
            this.topMargin = getMargin(topMargin);
            this.preTopMargin = this.topMargin;
            this.nextBottomMargin = this.bottomMargin;
        }

        public void handleItem(int col, int cols, View view, Rect outRect, RecyclerView parent, int pos) {

        }

        public EdgeBean noNeedLeftRightEdge() {
            this.needEdge = false;
            return this;
        }

        public EdgeBean setNextBottomMargin(int nextBottomMargin) {
            this.nextBottomMargin = getMargin(nextBottomMargin);
            return this;
        }

        public EdgeBean setPreTopMargin(int preTopMargin) {
            this.preTopMargin = getMargin(preTopMargin);
            return this;
        }

        public EdgeBean setPreLeftMargin(int preLeftMargin) {
            this.preLeftMargin = getMargin(preLeftMargin);
            return this;
        }

        public EdgeBean setNextRightMargin(int nextRightMargin) {
            this.nextRightMargin = getMargin(nextRightMargin);
            return this;
        }

        public EdgeBean setLeftMargin(int leftMargin) {
            this.leftMargin = getMargin(leftMargin);
            return this;
        }

        public EdgeBean setRightMargin(int rightMargin) {
            this.rightMargin = getMargin(rightMargin);
            return this;
        }

        public EdgeBean setGridMargin(int gridMargin) {
            this.gridMargin = getMargin(gridMargin);
            return this;
        }

        private int getMargin(int margin) {
            return margin > 0 ? DensityUtil.dip2px(margin) : -DensityUtil.dip2px(-margin);
        }
    }


}
