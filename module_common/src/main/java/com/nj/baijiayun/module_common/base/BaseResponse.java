package com.nj.baijiayun.module_common.base;

/**
 * @author chengang
 * @date 2019-06-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe
 */
public class BaseResponse<T> {

    private static final int CODE1 = 202;
    private static final int CODE_LOGIN_OTHER_DEVICE = 209;

    private String msg;
    private int code;
    private T data;

    public boolean isSuccess() {
        return 200 == code;
    }

    public boolean isNeedLogin() {
        return CODE1 == code || code == CODE_LOGIN_OTHER_DEVICE;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}