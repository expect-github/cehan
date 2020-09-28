package com.nj.baijiayun.sdk_player.component;

import android.content.Context;
import android.view.View;

import com.baijiayun.videoplayer.ui.component.ControllerComponent;
import com.baijiayun.videoplayer.ui.listener.OnTouchGestureListener;
import com.nj.baijiayun.sdk_player.R;


/**
 * Created by yongjiaming on 2018/8/7
 */

public class FloatControllerComponent extends ControllerComponent implements OnTouchGestureListener {

    public FloatControllerComponent(Context context) {
        super(context);
    }


    @Override
    protected View onCreateComponentView(Context context) {
        //注意这个布局里面的id跟ControllerComponent 里面的id保持一致
        return View.inflate(context, R.layout.layout_float_controller_component_lib, null);
    }


}
