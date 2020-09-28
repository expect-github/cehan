package com.nj.baijiayun.module_common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Project Name: user
 * Package Name: com.prettyyes.user.app.view.pupopwindow
 * Author: SmileChen
 * Created on: 2016/11/23
 * Description: Nothing
 * @author chengang
 */
public abstract class BasePopupWindow extends PopupWindow {


    private float defaultAlpha = 0.7f;
    private View mMenuView;
    public Activity activity;


    public void setDefaultAlpha(float defaultAlpha) {
        this.defaultAlpha = defaultAlpha;
    }

    public abstract void initView(View view);

    public abstract int setLayoutId();

    public void setListener() {
    }


    public View getMenuView() {
        return mMenuView;
    }

    public BasePopupWindow(final Activity context) {
        super(context);
        init(context);

    }

    public void init(Activity context) {
        activity = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (setLayoutId() != 0 && inflater != null) {
            mMenuView = inflater.inflate(setLayoutId(), null);
        }
        View view = createView();
        if (view != null) {
            mMenuView = view;
        }
        setListener();

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);


        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(setAnimation());
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        initView(mMenuView);


        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = mMenuView.getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }

    public int setAnimation() {
        return 0;
    }

    public View createView() {
        return null;
    }

    public void showAtBottom(View view) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = defaultAlpha;
        activity.getWindow().setAttributes(lp);
        //设置layout在PopupWindow中显示的位置
        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    public void showAsDropDown(View anchor) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = defaultAlpha;
        activity.getWindow().setAttributes(lp);
        //设置layout在PopupWindow中显示的位置
        super.showAsDropDown(anchor);
    }

    public void show(int id) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = defaultAlpha;
        activity.getWindow().setAttributes(lp);
        //设置layout在PopupWindow中显示的位置
        this.showAtLocation(activity.findViewById(id), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }


}
