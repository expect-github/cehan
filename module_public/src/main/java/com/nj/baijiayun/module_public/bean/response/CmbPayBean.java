package com.nj.baijiayun.module_public.bean.response;


/**
 * @author chengang
 * @date 2019-08-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class CmbPayBean {


    /**
     * version : 1.0
     * charset : UTF-8
     * signType : SHA-256
     * reqData : {"dateTime":"20200814193348","branchNo":"0791","merchantNo":"000050","date":"20200814","orderNo":"P2020081419334835901","amount":"111.00","expireTimeSpan":1800,"payNoticeUrl":"http://ykt.xiaowai.info/api/pay/cmbNotify","payNoticePara":"","clientIP":"","cardType":"A","subMerchantNo":"","subMerchantName":"","subMerchantTPCode":"","subMerchantTPName":"","payModeType":"","agrNo":"","merchantSerialNo":"","userID":"","mobile":"","lon":"","lat":"","riskLevel":"","signNoticeUrl":"http://ykt.xiaowai.info/notify/cmb","signNoticePara":""}
     * sign : ee6ffb73678467f7a872ef5a3835d0d2fe8e9939c61e10218dc3fbbb0c72438e
     */

    private String version;
    private String charset;
    private String signType;
    private ReqDataBean reqData;
    private String sign;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public ReqDataBean getReqData() {
        return reqData;
    }

    public void setReqData(ReqDataBean reqData) {
        this.reqData = reqData;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class ReqDataBean {
        /**
         * dateTime : 20200814193348
         * branchNo : 0791
         * merchantNo : 000050
         * date : 20200814
         * orderNo : P2020081419334835901
         * amount : 111.00
         * expireTimeSpan : 1800
         * payNoticeUrl : http://ykt.xiaowai.info/api/pay/cmbNotify
         * payNoticePara :
         * clientIP :
         * cardType : A
         * subMerchantNo :
         * subMerchantName :
         * subMerchantTPCode :
         * subMerchantTPName :
         * payModeType :
         * agrNo :
         * merchantSerialNo :
         * userID :
         * mobile :
         * lon :
         * lat :
         * riskLevel :
         * signNoticeUrl : http://ykt.xiaowai.info/notify/cmb
         * signNoticePara :
         */

        private String dateTime;
        private String branchNo;
        private String merchantNo;
        private String date;
        private String orderNo;
        private String amount;
        private int expireTimeSpan;
        private String payNoticeUrl;
        private String payNoticePara;
        private String clientIP;
        private String cardType;
        private String subMerchantNo;
        private String subMerchantName;
        private String subMerchantTPCode;
        private String subMerchantTPName;
        private String payModeType;
        private String agrNo;
        private String merchantSerialNo;
        private String userID;
        private String mobile;
        private String lon;
        private String lat;
        private String riskLevel;
        private String signNoticeUrl;
        private String signNoticePara;

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getBranchNo() {
            return branchNo;
        }

        public void setBranchNo(String branchNo) {
            this.branchNo = branchNo;
        }

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getExpireTimeSpan() {
            return expireTimeSpan;
        }

        public void setExpireTimeSpan(int expireTimeSpan) {
            this.expireTimeSpan = expireTimeSpan;
        }

        public String getPayNoticeUrl() {
            return payNoticeUrl;
        }

        public void setPayNoticeUrl(String payNoticeUrl) {
            this.payNoticeUrl = payNoticeUrl;
        }

        public String getPayNoticePara() {
            return payNoticePara;
        }

        public void setPayNoticePara(String payNoticePara) {
            this.payNoticePara = payNoticePara;
        }

        public String getClientIP() {
            return clientIP;
        }

        public void setClientIP(String clientIP) {
            this.clientIP = clientIP;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getSubMerchantNo() {
            return subMerchantNo;
        }

        public void setSubMerchantNo(String subMerchantNo) {
            this.subMerchantNo = subMerchantNo;
        }

        public String getSubMerchantName() {
            return subMerchantName;
        }

        public void setSubMerchantName(String subMerchantName) {
            this.subMerchantName = subMerchantName;
        }

        public String getSubMerchantTPCode() {
            return subMerchantTPCode;
        }

        public void setSubMerchantTPCode(String subMerchantTPCode) {
            this.subMerchantTPCode = subMerchantTPCode;
        }

        public String getSubMerchantTPName() {
            return subMerchantTPName;
        }

        public void setSubMerchantTPName(String subMerchantTPName) {
            this.subMerchantTPName = subMerchantTPName;
        }

        public String getPayModeType() {
            return payModeType;
        }

        public void setPayModeType(String payModeType) {
            this.payModeType = payModeType;
        }

        public String getAgrNo() {
            return agrNo;
        }

        public void setAgrNo(String agrNo) {
            this.agrNo = agrNo;
        }

        public String getMerchantSerialNo() {
            return merchantSerialNo;
        }

        public void setMerchantSerialNo(String merchantSerialNo) {
            this.merchantSerialNo = merchantSerialNo;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }

        public String getSignNoticeUrl() {
            return signNoticeUrl;
        }

        public void setSignNoticeUrl(String signNoticeUrl) {
            this.signNoticeUrl = signNoticeUrl;
        }

        public String getSignNoticePara() {
            return signNoticePara;
        }

        public void setSignNoticePara(String signNoticePara) {
            this.signNoticePara = signNoticePara;
        }
    }
}
