package com.nj.baijiayun.module_public.helper;

import androidx.constraintlayout.widget.Group;
import android.view.View;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class ConstrainsClickHelper {

    public static void setOnClickListener(Group group, View view, View.OnClickListener onClickListener) {
        int[] refIds = group.getReferencedIds();
        for (int id : refIds) {
            view.findViewById(id).setOnClickListener(onClickListener);
        }
    }

    public static void setOnClickListener(Group group, View.OnClickListener onClickListener) {
        int[] refIds = group.getReferencedIds();
        for (int id : refIds) {
            ((View) group.getParent()).findViewById(id).setOnClickListener(onClickListener);
        }
    }

    public static void setOnLongClickListener(Group group, View view, View.OnLongClickListener onLongClickListener) {
        int[] refIds = group.getReferencedIds();
        for (int id : refIds) {
            view.findViewById(id).setOnLongClickListener(onLongClickListener);
        }
    }

    public static void setOnLongClickListener(Group group, View.OnLongClickListener onLongClickListener) {
        int[] refIds = group.getReferencedIds();
        for (int id : refIds) {
            group.findViewById(id).setOnLongClickListener(onLongClickListener);
        }
    }
}
