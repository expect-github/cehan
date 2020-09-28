package com.nj.baijiayun.module_public.helper;

import androidx.lifecycle.LifecycleOwner;

import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-08-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class RefreshListDataHelper {

    public static void registerCourseHasBuyChange(LifecycleOwner lifecycleOwner, BaseRecyclerAdapter adapter) {

        LiveDataBus.get().with(LiveDataBusEvent.COURSE_HAS_BUY_SUCCESS_BY_JOIN_OR_FREE, Integer.class).observe(lifecycleOwner, integer -> {
            if (integer != null && integer > 0) {
                Logger.d("refreshCourseList-->" + integer + "--->" + adapter);
                refreshCourseList(integer, adapter);
            }
        });
        LiveDataBus.get().with(LiveDataBusEvent.COURSE_HAS_BUY_SUCCESS_BY_PAY, Integer.class).observe(lifecycleOwner, integer -> {
            if (integer != null && integer > 0) {
                Logger.d("refreshCourseList COURSE_HAS_BUY_SUCCESS_BY_PAY-->" + integer);

                refreshCourseList(integer, adapter);
            }
        });
    }


    private static void refreshCourseList(int courseId, BaseRecyclerAdapter adapter) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i) instanceof PublicCourseBean) {
                PublicCourseBean bean = (PublicCourseBean) adapter.getItem(i);
                if (courseId == bean.getId()) {
                    ((PublicCourseBean) adapter.getItem(i)).setHasBuy();
                    ((PublicCourseBean) adapter.getItem(i)).setSalesNum(((PublicCourseBean) adapter.getItem(i)).getSalesNum()+1);
                    adapter.notifyItemChanged(i);
                    break;

                }
            }

        }
    }
}
