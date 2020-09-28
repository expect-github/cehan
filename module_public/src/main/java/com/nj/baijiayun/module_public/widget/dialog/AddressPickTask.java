package com.nj.baijiayun.module_public.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.nj.baijiayun.module_public.widget.picker.BJYAddressPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 获取地址数据并显示地址选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015/12/15
 */
public class AddressPickTask extends AsyncTask<String, Void, ArrayList<Province>> {
    private List<Province> list;
    private WeakReference<Context> activityReference;// 2018/6/1 StaticFieldLeak
    private ProgressDialog dialog;
    private Callback callback;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";
    private boolean hideProvince = false;
    private boolean hideCounty = false;

    public AddressPickTask(Context activity) {
        this.activityReference = new WeakReference<>(activity);
    }

    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        Context activity = activityReference.get();
        if (activity == null) {
            return;
        }
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }

    @Override
    protected ArrayList<Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    if (!"0".equals(params[0])) {
                        selectedProvince = params[0];
                    }
                    break;
                case 2:
                    if (!"0".equals(params[0])) {
                        selectedProvince = params[0];
                        if (!"0".equals(params[1])) {
                            selectedCity = params[1];
                        }
                    }
                    break;
                case 3:
                    if (!"0".equals(params[0])) {
                        selectedProvince = params[0];
                        if (!"0".equals(params[1])) {
                            selectedCity = params[1];
                            if (!"0".equals(params[2])) {
                                selectedCounty = params[2];
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        ArrayList<Province> data = new ArrayList<>();
        try {
            Context activity = activityReference.get();
            if (activity != null) {
                String json = ConvertUtils.toString(activity.getAssets().open("city.json"));
                JSONArray provices = new JSONArray(json);
                for (int i = 0; i < provices.length(); i++) {
                    JSONObject proviceJson = provices.getJSONObject(i);
                    String code = proviceJson.getString("code");
                    String name = proviceJson.getString("name");
                    Province province = new Province(code, name);
                    ArrayList<City> cities = new ArrayList<City>();
                    JSONArray citys = proviceJson.getJSONArray("cityList");
                    for (int j = 0; j < citys.length(); j++) {
                        ArrayList<County> counties = new ArrayList<>();
                        JSONObject cityJson = citys.getJSONObject(j);
                        City city = new City(cityJson.getString("code"), cityJson.getString("name"));
                        JSONArray areaJson = cityJson.getJSONArray("areaList");
                        for (int k = 0; k < areaJson.length(); k++) {
                            JSONObject jsonObject = areaJson.getJSONObject(k);
                            counties.add(new County(jsonObject.getString("code"), jsonObject.getString("name")));
                        }
                        city.setCounties(counties);
                        cities.add(city);
                    }
                    province.setCities(cities);
                    data.add(province);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<Province> result) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (result.size() > 0) {
            Context activity = activityReference.get();
            if (activity == null) {
                return;
            }
            BJYAddressPicker picker = new BJYAddressPicker(activity, result);
            picker.setHideProvince(hideProvince);
            picker.setHideCounty(hideCounty);
            if (hideCounty) {
                picker.setColumnWeight(1 / 3.0f, 2 / 3.0f);//将屏幕分为3份，省级和地级的比例为1:2
            } else {
                picker.setColumnWeight(3 / 9.0f, 3 / 9.0f, 3 / 9.0f);//省级、地级和县级的比例为2:3:3
            }
            picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
            picker.setOnAddressPickListener(callback);
            picker.show();
        } else {
            callback.onAddressInitFailed();
        }
    }

    public interface Callback extends BJYAddressPicker.OnAddressPickListener {

        void onAddressInitFailed();

    }

}
