package com.nj.baijiayun.module_common.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019/5/20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name www.baijiayun.module_common.helper
 * @describe
 */
public class DataTestHelper {
    public static List<Object> createList(Class cls,int n)
    {
        List<Object>datas=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            try {
                datas.add(cls.newInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }
}
