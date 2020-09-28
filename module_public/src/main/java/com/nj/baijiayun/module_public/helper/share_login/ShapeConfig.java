package com.nj.baijiayun.module_public.helper.share_login;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ShapeConfig.QQ,
        ShapeConfig.SINA,
        ShapeConfig.WX,
})
@Retention(RetentionPolicy.SOURCE)
public @interface ShapeConfig {
    int QQ=1;
    int WX=2;
    int SINA=3;
}
