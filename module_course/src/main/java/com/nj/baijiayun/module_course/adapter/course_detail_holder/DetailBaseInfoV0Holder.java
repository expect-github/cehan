package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.holder.DateUtil;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
@Deprecated
public class DetailBaseInfoV0Holder extends BaseMultipleTypeViewHolder<PublicCourseDetailBean> {


    private boolean isCollect;
    private ClickCallBack clickCallBack;

    public DetailBaseInfoV0Holder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.iv_collect, v -> {

            if (JumpHelper.checkLogin()) {
                return;
            }
            isCollect = !isCollect;
            if(clickCallBack!=null) {
                clickCallBack.collect(isCollect);
            }

        });
    }


    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_head;
    }


    public void updateSignUpAndLimitNumber(PublicCourseDetailBean model) {

//        String satTime = DateUtil.getDateToString(model.getStartPlayDate());
//        String endTime = DateUtil.getDateToString(model.getEndPlayDate());
//        String conTime = "    共" + model.getTotalPeriods() + "个课时";
//
//        setText(R.id.tv_periods, MessageFormat.format("共{0}课时 | {1}人已报名",
//                satTime + "-" + endTime + conTime, ""));
        //以前的
        setText(R.id.tv_periods, MessageFormat.format("共{0}课时 | {1}人已报名", model.getTotalPeriods(), model.getSalesNum()));
        setText(R.id.tv_sales_limit, MessageFormat.format("报名上限：{0}人 | 剩余名额：{1}", model.getStoreNum(), model.getStock()));
    }

    @Override
    public void bindData(PublicCourseDetailBean model, int position, BaseRecyclerAdapter adapter) {
        initUiVisible();
        if (ConstsCouseType.isLive(model.getCourseType())) {
            setVisible(R.id.tv_periods, true);
            setVisible(R.id.tv_time_range, true);
        } else if (ConstsCouseType.isSystem(model.getCourseType())) {
            setVisible(R.id.tv_periods, true);
            setVisible(R.id.tv_time_range, false);
        } else if (ConstsCouseType.isFace(model.getCourseType())) {
            //显示库存
            setVisible(R.id.tv_sales_limit, model.isShowStore());
            setVisible(R.id.tv_sign_up_end, true);
            setVisible(R.id.tv_time_range, true);
            setVisible(R.id.tv_address, true);
        } else {
            setVisible(R.id.tv_periods, true);
        }
        this.isCollect = model.isCollect();
        setText(R.id.tv_course_title, model.getTitle());
        ((PriceTextView) getView(R.id.tv_price_discount)).setDefaultPrice(model.getPrice());
        ((PriceTextView) getView(R.id.tv_price_unline)).needMidLine().setPriceWithFmtTxt(model.getUnderlinedPrice());
        //划线价格
        setVisible(R.id.tv_price_unline, PriceHelper.parsePriceStr(model.getUnderlinedPrice()) != 0);
        setImageResource(R.id.iv_collect, model.isCollect() ? R.drawable.public_ic_collected : R.drawable.public_ic_un_collect);
        updateSignUpAndLimitNumber(model);
        //面授课才有的
        setText(R.id.tv_sign_up_end, MessageFormat.format("报名截止时间：{0} ", TimeFormatHelper.getYearMonthDayHourMinSplitByPoint(model.getFaceCourseSignUpEndTime())));
        setText(R.id.tv_time_range, MessageFormat.format("开课时间：{0}", TimeFormatHelper.getYearMonthDayHourMinRangeByPoint(model.getStartPlayDate(), model.getEndPlayDate())));
        setText(R.id.tv_address, MessageFormat.format("开课地点：{0}", model.getAddress()));

    }

    private void initUiVisible() {
        setVisible(R.id.tv_periods, false);
        setVisible(R.id.tv_sales_limit, false);
        setVisible(R.id.tv_sign_up_end, false);
        setVisible(R.id.tv_time_range, false);
        setVisible(R.id.tv_address, false);
    }
    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }
    public interface ClickCallBack{
        void collect(boolean isCollect);
    }




}
