package com.nj.baijiayun.module_common.widget.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.basic.widget.attrtab.AbstractTab;
import com.nj.baijiayun.basic.widget.attrtab.TriangleView;
import com.nj.baijiayun.module_common.R;


/**
 * @author chengang
 * @date 2020-02-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication.tab
 * @describe 可重复点击的 不会影响别的，比如上下控制
 */
public class RepeatSelectTab extends AbstractTab {

    private TextView mTv;
    private TriangleView mTlv1;
    private TriangleView mTlv2;
    private boolean isSelectBottom = false;
    private String title;
    private LinearLayout mTabView;

    public RepeatSelectTab setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isSelectTab() {
        return isSelectBottom;
    }

    @Override
    public void select(boolean isSelect) {
        super.select(isSelect);
        isSelectBottom = !isSelectBottom;
        if (isSelect) {
            mTlv2.setColorRes(isSelectBottom ? R.color.colorSelect : R.color.colorTriangleUnSelect);
            mTlv1.setColorRes(isSelectBottom ? R.color.colorTriangleUnSelect : R.color.colorSelect);
        } else {
            mTlv1.setColorRes(R.color.colorTriangleUnSelect);
            mTlv2.setColorRes(R.color.colorTriangleUnSelect);
        }
    }


    @Override
    public View createTabView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.common_layout_tab_repeat, null);
        this.mTabView= (LinearLayout) inflate;
        mTv = inflate.findViewById(R.id.tv);
        mTlv1 = inflate.findViewById(R.id.tlv_1);
        mTlv2 = inflate.findViewById(R.id.tlv_2);
        mTlv1.setColorRes(R.color.colorTriangleUnSelect);
        mTlv2.setColorRes(R.color.colorTriangleUnSelect);
        mTv.setText(title);
        return inflate;
    }

    @Override
    public boolean isRepeat() {
        return true;
    }

    @Override
    public void viewCreate() {

    }

    @Override
    public View getTabView() {
        return mTabView;
    }

    @Override
    public void setGravity(int gravity) {
        mTabView.setGravity(gravity);
    }

}
