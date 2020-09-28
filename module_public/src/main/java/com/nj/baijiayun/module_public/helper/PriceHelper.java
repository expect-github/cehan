package com.nj.baijiayun.module_public.helper;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.utils
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/21 上午10:27
 * @change
 * @time
 * @describe
 */
public class PriceHelper {
    private static DecimalFormat format = new DecimalFormat("0.00");
    private static NumberFormat numberFormat = NumberFormat.getPercentInstance();

    /**
     * 格式化金额
     *
     * @param price 以分为单位的金额
     * @return 金额数额以元为单位，精确到小数点两位
     */
    public static String getCommonPrice(int price) {
        if (price < 0) {
            price = 0;
        }
        StringBuilder sb = new StringBuilder();
        if (price < 10) {
            sb.append("0.0");
            sb.append(price);
        } else if (price < 100) {
            sb.append("0.");
            sb.append(price);
        } else {
            sb.append(price / 100);
            sb.append(".");
            int decimalPrice = (int) (price % 100);
            if (decimalPrice < 10) {
                sb.append("0");
            }
            sb.append(decimalPrice);
        }
        return sb.toString();
    }


    public static String getCommonPriceEndOfPointNotZeroNumEnd(int price) {
        if (price < 0) {
            price = 0;
        }
        StringBuilder sb = new StringBuilder();
        if (price < 10) {
            sb.append("0.0");
            sb.append(price);
        } else if (price < 100) {
            sb.append("0.");
            sb.append(price);
        } else {
            sb.append(price / 100);
            long decimalPrice = price % 100;
            if (decimalPrice != 0) {
                sb.append(".");
                if (decimalPrice < 10) {
                    sb.append("0");
                }
                sb.append(decimalPrice);
            }
        }
        return sb.toString();
    }


    public static String getYuanPrice(String price) {
        return price.substring(0, price.length() - 2);
    }

    public static String getYuanPrice(int price) {
        if (price < 0) {
            price = 0;
        }
        return String.valueOf(price / 100);
    }

    public static String getYuanPrice(long price) {
        if (price < 0) {
            price = 0;
        }
        if (price > 100) {
            return String.valueOf((float) price / 100);
        } else {
            return String.valueOf((float) price / 100);
        }
    }

    public static String getCommonPrice(String price) {

        return getCommonPriceEndOfPointNotZeroNumEnd(Integer.parseInt(price));
    }

    public static int parsePriceStr(String price) {
        return Integer.parseInt(price);
    }



}