package com.nj.baijiayun.module_public.helper;

/**
 * @project neixun_android
 * @class name：com.nj.baijiayun.module_public.helper
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019-06-22 09:51
 * @change
 * @time
 * @describe
 */
public abstract class CloneableObservable implements Cloneable {

    public CloneableObservable clone() throws CloneNotSupportedException {
        return (CloneableObservable) super.clone();
    }
}
