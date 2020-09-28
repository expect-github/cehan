package com.nj.baijiayun.module_main.helper;

import android.graphics.Rect;
import android.view.View;

import com.nj.baijiayun.basic.utils.DensityUtil;

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
public class HomePageSpaceItemDecoration extends RecyclerView.ItemDecoration {
    int spanCount = 3;
    private boolean includeEdge = true;
    private boolean leftRightIncludeEdge = true;
    private boolean includeFirstEdge = false;
    private int topBottom = DensityUtil.dip2px(15);
    private int leftRight = DensityUtil.dip2px(15);


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        setGridLayoutSpaceItemDecoration(outRect, view, parent, state);
    }

    private void setGridLayoutSpaceItemDecoration(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        assert layoutManager != null;
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanIndex = spanSizeLookup.getSpanSize(position);
        //一行有多个的
        if (spanIndex == 1) {
            //  找到上面
            if (position > 0) {
                int realIndex = -1;
                for (int i = position - 1; i >= 0; i--) {
                    int lastSpanIndex = spanSizeLookup.getSpanSize(i);
                    realIndex++;
                    if (spanIndex != lastSpanIndex) {
                        break;
                    }
                }
                position = realIndex;
            }
            int column = position % spanCount;
            if (leftRightIncludeEdge) {
                outRect.left = leftRight - column * leftRight / spanCount;
                outRect.right = (column + 1) * leftRight / spanCount;
                outRect.bottom = topBottom;
            } else {
                outRect.left = column * leftRight / spanCount;
                outRect.right = leftRight - (column + 1) * leftRight / spanCount;
                if (position >= spanCount) {
                    outRect.top = topBottom;
                }
            }

        } else {
            outRect.left = 0;
            outRect.right = 0;
            //底部边界
            if (parent.getChildLayoutPosition(view) == (layoutManager.getItemCount() - 1)) {
                outRect.bottom = includeEdge ? topBottom : 0;
            } else {
                outRect.bottom = topBottom;
            }
            //第一个
            if (position == 0) {
                outRect.top = includeEdge && includeFirstEdge ? topBottom : 0;
            } else {
                outRect.top = 0;
            }

        }


    }
}
