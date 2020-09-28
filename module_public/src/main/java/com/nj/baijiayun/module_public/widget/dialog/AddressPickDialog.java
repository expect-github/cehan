package com.nj.baijiayun.module_public.widget.dialog;

import android.content.Context;

import com.nj.baijiayun.basic.utils.StringUtils;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget.mDialog
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/24 上午11:41
 * @change
 * @time
 * @describe
 */
public class AddressPickDialog {

    private String province;
    private String city;
    private String county;
    private final AddressPickTask task;
    private OnAddressPickListener mListener;

    public AddressPickDialog(Context context) {
        task = new AddressPickTask(context);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
//                                showShortToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (mListener != null) {
                    mListener.onAddressPick(province.getAreaName(), province.getAreaId(),
                            city.getAreaName(), city.getAreaId(),
                            county.getAreaName(), county.getAreaId());
                }
            }
        });
    }

    public AddressPickDialog init(String province, String city, String county) {
        this.province = province;
        this.city = city;
        this.county = county;
        return this;
    }

    public AddressPickDialog setOnAddressPickListener(OnAddressPickListener listener) {
        this.mListener = listener;

        return this;
    }

    public interface OnAddressPickListener {
        void onAddressPick(String province, String provinceId, String city, String cityId, String county, String countyId);
    }

    public void show() {
        if (StringUtils.isEmpty(county)) {
            task.execute("北京市", "北京市", "东城区");
        } else {
            task.execute(province, city, county);
        }
    }

}
