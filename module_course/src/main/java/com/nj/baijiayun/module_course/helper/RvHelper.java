package com.nj.baijiayun.module_course.helper;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2020-02-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.helper
 * @describe
 */
public class RvHelper {

    //这里只需要记录
    public static int getScollyDistance(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        if (position == 0) {
            return -firstVisiableChildView.getTop();
        }
        return Integer.MAX_VALUE;
    }
    public static void forceStopRecyclerViewScroll(RecyclerView mRecyclerView) {
        mRecyclerView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
    }

}
