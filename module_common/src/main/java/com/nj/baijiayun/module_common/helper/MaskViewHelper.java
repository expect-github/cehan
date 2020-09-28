package com.nj.baijiayun.module_common.helper;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;

/**
 * @author chengang
 * @date 2020-03-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.helper
 * @describe
 */
public class MaskViewHelper {
    private HashMap<Activity, View> map = new HashMap<>();
    private boolean isOpenMask = false;
    private int maskViewColor = Color.parseColor("#66ff0000");
    private static volatile MaskViewHelper singleton = null;

    private MaskViewHelper() {
    }

    public static MaskViewHelper getInstance() {
        if (singleton == null) {
            synchronized (MaskViewHelper.class) {
                if (singleton == null) {
                    singleton = new MaskViewHelper();
                }
            }
        }
        return singleton;
    }

    public HashMap<Activity, View> getMap() {
        return map;
    }

    public MaskViewHelper setMaskViewColor(int maskViewColor) {
        this.maskViewColor = maskViewColor;
        return this;
    }

    public MaskViewHelper initShowMask(boolean needShow) {
        this.isOpenMask = needShow;
        return this;
    }

    public void showMask(Activity activity, boolean needShow) {
        if (needShow) {
            open(activity);
        } else {
            close(activity);
        }

    }

    public void updateColor(int maskViewColor) {
        this.maskViewColor = maskViewColor;
        for (Activity activity : map.keySet()) {
            map.get(activity).setBackgroundColor(maskViewColor);
        }
    }


    public void open(Activity activity) {
        isOpenMask = true;
        if (map.get(activity) != null) {
            return;
        }
        ViewGroup dec = (ViewGroup) activity.getWindow().getDecorView();
        View inflate = new View(activity);
        inflate.setBackgroundColor(maskViewColor);
        dec.addView(inflate, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dec.bringChildToFront(inflate);
        map.put(activity, inflate);
    }

    public void close(Activity activity) {
        isOpenMask = false;
        if (map.get(activity) != null) {
            ViewGroup dec = (ViewGroup) activity.getWindow().getDecorView();
            dec.removeView(map.get(activity));
            map.remove(activity);
        }

    }


    public void initApplicationLife(Application app) {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ViewGroup dec = (ViewGroup) activity.getWindow().getDecorView();
                dec.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                    @Override
                    public void onChildViewAdded(View parent, View child) {
                        View view = map.get(activity);
                        if (view != null) {
                            FrameLayout dec = (FrameLayout) activity.getWindow().getDecorView();
                            dec.bringChildToFront(view);
                        }
                    }

                    @Override
                    public void onChildViewRemoved(View parent, View child) {

                    }
                });

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (isOpenMask) {
                    open(activity);
                } else {
                    close(activity);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {


            }

            @Override
            public void onActivityPaused(Activity activity) {


            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                map.remove(activity);
            }
        });
    }

}
