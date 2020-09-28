package com.nj.baijiayun.module_public.manager;

/**
 * @author chengang
 * @date 2020-03-23
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.manager
 * @describe
 */
public interface IShortcutBadger {

    void setNumber(int count);

    void add(int count);

    void add();

    void remove();

    void remove(int count);

    void updateNumberFromDataSource();

    void switchEnable(boolean isEnable);

    void tryUpdateNumber();

}
