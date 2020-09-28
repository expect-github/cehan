package com.nj.baijiayun.module_distribution.holder;

import com.nj.baijiayun.module_public.bean.IDistrution;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.widget.PriceTextView;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.holder
 * @describe
 */
public class HolderHelper {


    public static void setProfit(PriceTextView priceTextView, IDistrution iDistrution) {

        if (iDistrution.isShowRate()) {
            priceTextView.setText(MessageFormat.format("佣金比例：{0}%", String.valueOf(iDistrution.getCommissionRate())));

        } else {
            String price = PriceHelper.getCommonPrice(iDistrution.getCommission());
            priceTextView.setDefaultPrice("佣金：" + price, price);
        }
        priceTextView.inListShow();

    }


}
