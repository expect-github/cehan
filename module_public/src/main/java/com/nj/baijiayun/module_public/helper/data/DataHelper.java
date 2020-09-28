package com.nj.baijiayun.module_public.helper.data;

import com.nj.baijiayun.module_common.widget.tabs.SingleListModel;
import com.nj.baijiayun.module_public.bean.PublicCourseTypeBean;
import com.nj.baijiayun.module_public.bean.response.PublicCourseClassifyResponse;
import com.nj.baijiayun.module_public.widget.filter_tabs.SingleListTab;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2020-02-28
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.data
 * @describe
 */
public class DataHelper {


    public static void addCategoryData(List<PublicCourseTypeBean> datas, SingleListTab singleListTab) {
        if (datas == null) {
            return;
        }
        List<SingleListModel> result = new ArrayList<>();
        for (PublicCourseTypeBean data : datas) {
            result.add( new SingleListModel(data.getId(),data.getName()));
        }
        singleListTab.addData(result);
    }

}
