package com.nj.baijiayun.module_public.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.helper.CloneableObservable;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class UserInfoBean extends CloneableObservable {

    //  is_new token


    /**
     * id : 5
     * login_name : ZYWX_mZKfCJ
     * nickname : 183
     * password : 123456
     * email :
     * avatar : https://bjywangxiao.oss-cn-hangzhou.aliyuncs.com/uploads/avatar.jpg
     * remember_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Rlc3R3eC5iYWlqaWF5dW4uY29tL2FwaS9hcHAvbG9naW4iLCJpYXQiOjE1NjM5NDc4MjYsImV4cCI6MTU2NDI1MDIyNiwibmJmIjoxNTYzOTQ3ODI2LCJqdGkiOiJyMTRyR3FBQ3FrSWowQVlEIiwic3ViIjo1LCJwcnYiOiI5ZjFmZTllMGRmZmJlNDQ0MmRjNzgzMTA3NTFmNTkxY2Y0ZDE0MDIwIn0._Iy4HgPDjlXGYBqgGAZ5FDuhiz9MO943MlnhuLxSFRc
     * id_card : 0
     * mobile : 18356214885
     * birthday : 2019-09-27T16:00:00.000Z
     * is_new : 1
     * is_buy : 2
     * wx_openid :
     * qq_openid :
     * integral : 0
     * user_from : PC
     * province_id : 0
     * city_id : 0
     * district_id : 0
     * last_login_ip : 223.104.147.52
     * last_login_time : 1563947826
     * status : 1
     * created_at : 1563265181
     * updated_at : 1563947826
     * deleted_at : 0
     */

    @SerializedName("remember_token")
    private String token;
    @SerializedName("is_new")
    private int isNew;
    private int id;
    private String nickname;
    private String email;
    private String avatar;
    private String mobile;
    private int sex;
    private String birthday;
    @SerializedName("is_vip")
    private int isVip;
    private PublicVipBean vip;
    @SerializedName("auth_name")
    private String authName;
    @SerializedName("auth_num")
    private String authNum;
    /**
     * province_id : 360000
     * city_id : 360100
     * district_id : 360102
     * province_name : 江西省
     * city_name : 南昌市
     * district_name : 东湖区
     * vip : []
     * attr : []
     */

    @SerializedName("province_id")
    private int provinceId;
    @SerializedName("city_id")
    private int cityId;
    @SerializedName("district_id")
    private int districtId;
    @SerializedName("province_name")
    private String provinceName;
    @SerializedName("city_name")
    private String cityName;
    @SerializedName("district_name")
    private String districtName;

    public int getId() {
        return id;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLoginToken() {
        return token;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isNewUser() {
        return isNew == 1;
    }

    public boolean isSetPwd() {
        return false;
    }

    public String getSexStr() {
        if (sex == 1) {
            return "男";
        }
        return "女";
    }

    public boolean isVip() {
        return isVip == 1;
    }

    public long getVipEndAt() {
        if (vip != null) {
            return vip.getVipEndAt();
        }
        return 0;
    }

    public long getVipCreateAt() {
        if (vip != null) {
            return vip.getVipCreatedAt();
        }
        return 0;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public void setVipEndAt(long vipEndAt) {
        if (vip == null) {
            vip = new PublicVipBean();
        }
        vip.setVipEndAt(vipEndAt);
    }

    public void setVipCreateAt(long vipCreateAt) {
        if (vip == null) {
            vip = new PublicVipBean();
        }
        vip.setVipCreatedAt(vipCreateAt);
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthNum() {
        return authNum;
    }

    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public void tryCopyLoginInfo(UserInfoBean userInfoBean) {
        if (TextUtils.isEmpty(userInfoBean.token)) {
            return;
        }
        this.token = userInfoBean.token;
        this.isNew = userInfoBean.isNew;
    }

    public boolean isCertify() {
        return !TextUtils.isEmpty(authName) && !TextUtils.isEmpty(authNum);
    }

}
