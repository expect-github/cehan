package com.nj.baijiayun.module_common.helper;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * @author chengang
 * @date 2019-06-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.helper
 * @describe
 */
public class FragmentCreateHelper {

    public static <TYPE extends Fragment> TYPE newInstance(int type, Class<TYPE> clazz) {
        TYPE fragment = null;
        try {
            fragment = clazz.newInstance();
            Bundle args = new Bundle();
            args.putInt("type", type);
            fragment.setArguments(args);
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            Log.e("TAG", e.getMessage());
        }
        return fragment;
    }


    public static <TYPE extends Fragment> TYPE newInstance(Bundle bundle, Class<TYPE> clazz) {
        TYPE fragment = null;
        try {
            fragment = clazz.newInstance();
            fragment.setArguments(bundle);
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            Log.e("TAG", e.getMessage());
        }
        return fragment;
    }


    public static int getType(Bundle bundle) {
        int type = 0;
        if (bundle != null) {
            type = bundle.getInt("type");
        }
        return type;
    }
}

