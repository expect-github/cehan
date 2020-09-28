package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-08-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PayBean {

//    {
//        "responme":"",
//        "payment_number":""
//    }

    @SerializedName("payment_number")
    private String paymentNumber;
    private String pay_type;

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    private String response;

    public String getResponse() {
        return response;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
