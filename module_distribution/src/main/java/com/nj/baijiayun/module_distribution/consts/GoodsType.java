package com.nj.baijiayun.module_distribution.consts;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.consts
 * @describe
 */
public class GoodsType {
    public static final int TYPE_BOOK = 1;
    public static final int TYPE_COURSE = 0;

    public static boolean isBook(int type) {
        return TYPE_BOOK == type;
    }

    public static boolean isCourse(int type) {
        return TYPE_COURSE == type;
    }

}
