package com.nj.baijiayun.module_public.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.widget.dialog.IosLoadingDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.adapter.CommonAdapter;
import com.nj.baijiayun.module_public.adapter.MultiItemTypeAdapter;
import com.nj.baijiayun.module_public.adapter.ViewHolder;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewSplitLineHelper;
import com.nj.baijiayun.module_public.helper.videoplay.VideoPlayHelper;
import com.nj.baijiayun.module_public.helper.videoplay.res.BjyTokensRes;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-07-18
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx_layout
 * @describe
 */
@AdapterCreate
public class PublicCourseHolder extends BaseMultipleTypeViewHolder<PublicCourseBean> {
    public int  num = 0;
    public PublicCourseHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> {
            if (ConstsCouseType.isPublic(getClickModel().getCourseType())) {
                playPublicOpenCourse(getContext(), getClickModel().getPlayId(), getClickModel().getCourseType());
                return;
            }
            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL).withInt("courseId", getClickModel().getId()).navigation();
        });


    }

    public static void playPublicOpenCourse(Context context, int courseId, int courseType) {

        final IosLoadingDialog iosLoadingDialog = BJYDialogHelper.buildLoadingDialog(context).setLoadingTxt("");
        iosLoadingDialog.show();
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getBjyVideoTokenPublicCourse("", String.valueOf(courseId))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain((LifecycleOwner) context))
                .subscribe(new BaseSimpleObserver<BjyTokensRes>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BjyTokensRes baseResponse) {
                        iosLoadingDialog.dismiss();
                        VideoPlayHelper.playVideo(baseResponse.getData(), courseType);

                    }

                    @Override
                    public void onFail(Exception e) {
                        iosLoadingDialog.dismiss();
                        ToastUtil.shortShow(context, e.getMessage());

                    }
                });
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.public_item_course_v5;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindData(PublicCourseBean model, int position, BaseRecyclerAdapter adapter) {
        num = num+1;

        ViewSplitLineHelper.setLineVisible(this, position);
        //课时背景
//        ImageLoader.with(getContext()).load(model.getCover()).rectRoundCorner(ConstsNormal.COVER_ROUND_CORNER).into((ImageView) getView(R.id.iv_cover));
        ((PriceTextView) getView(R.id.tv_price_discount)).inListShow()
                .setNeedBoldPrice(true)
                .setPrice(model.getPrice());
//        ((PriceTextView) getView(R.id.tv_price_unline)).inListShow().needMidLine().setPrice(model.getUnderlinedPrice());
//        setVisible(R.id.iv_sign_up, false);
        setVisible(R.id.tv_price_unline, false);
        String fmt;
//        if (position==0){
//            setVisible(R.id.cl_root_view,false);
//        }else {
//            setVisible(R.id.cl_root_view,true);
//        }
        Log.e("下标差异：",""+num);
//        LinearLayout linearLayout = (LinearLayout) getView(R.id.cl_root);
        if (model.getSalesNum() > 10000) {
            fmt = String.format("%.1f万+", model.getSalesNum() * 1.0 / 10000);
        } else {
            fmt = String.valueOf(model.getSalesNum());
        }


        setText(R.id.public_tv_sign_up_num, fmt + "人已报名");
        setVisibleInVisible(R.id.public_tv_sign_up_num, !ConstsCouseType.isPublic(model.getCourseType()));
        setCourseTitle(model);
        //什么课
//        if (getView(R.id.tv_course_type) != null) {
//            setText(R.id.tv_course_type, ConstsCouseType.getCourseTypeName(model.getCourseType()));
//        }

        //修改时间2020-08-03
        String satTime = DateUtil.getDateToString(model.getStartPlayDate());
        String endTime = DateUtil.getDateToString(model.getEndPlayDate());
        String conTime = "    共" + model.getTotalPeriods() + "个课时";
        setText(R.id.tv_course_time, satTime + "-" + endTime + conTime);
        if (getView(R.id.tv_course_subject) != null) {
            setSubiect(R.id.tv_course_subject, model.getSubject_name());
        }
        if (CheckUtil.isListNotEmpty(model.getTeacherList())) {
            RecyclerView recyclerView = (RecyclerView) getView(R.id.tv_course_recycler);
            LinearLayoutManager ms = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(ms);
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
            CommonAdapter<PublicTeacherBean> commonAdapter =
                    new CommonAdapter<PublicTeacherBean>(getContext(), model.getTeacherList(), R.layout.public_item_course_v5_list) {
                        @Override
                        public void convert(ViewHolder holder, PublicTeacherBean item, int position) {
                            TextView tv_name = holder.getView(R.id.tv_course_teacher);
                            ImageView iv_head = holder.getView(R.id.tv_course_head);
                            if (model.getTeacherList().size() > 1) {
                                tv_name.setVisibility(View.GONE);
                            } else {
                                tv_name.setVisibility(View.VISIBLE);
                                tv_name.setText(item.getName());
                            }
                            Glide.with(getContext()).load(model.getTeacherList().get(position).getTeacherAvatar()).into(iv_head);
                        }
                    };
            recyclerView.setAdapter(commonAdapter);
            commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder mHolder, int index) {
                    JumpHelper.jumpWebViewNoNeedAppTitle(MessageFormat.format("{0}?id={1}&back=1", ConstsH5Url.getTeacher(), String.valueOf(model.getTeacherList().get(index).getTeacherId())));
                }
            });
        }
    }

    protected void setCourseTitle(PublicCourseBean model) {
        ((CourseTitleView) getView(R.id.tv_course_name)).setHasAssemble(model.isJoinSpell())
                .setHasCoupon(model.isHasCoupon())
                .showTag(model.getTitle());
    }

    private void setSubiect(int viewId, String type) {
        TextView tv_subject = (TextView) getView(viewId);
        tv_subject.setText(type);
        if (CheckUtil.isEmpty(type)){
            tv_subject.setVisibility(View.GONE);
        }else {
            tv_subject.setVisibility(View.VISIBLE);
        }
        switch (type) {
            case "语文":
                tv_subject.setBackgroundResource(R.drawable.public_bg_course_type_ke_v1);
                break;
            case "数学":
                tv_subject.setBackgroundResource(R.drawable.public_bg_course_type_ke_v2);
                break;
            case "英语":
                tv_subject.setBackgroundResource(R.drawable.public_bg_course_type_ke_v3);
                break;
            case "家庭教育":
                tv_subject.setBackgroundResource(R.drawable.public_bg_course_type_ke_v5);
                break;
            case "多科":
                tv_subject.setBackgroundResource(R.drawable.public_bg_course_type_ke_v4
                );
                break;
            default:
                tv_subject.setBackgroundResource(R.drawable.public_bg_course_type_ke_v6);
                break;
        }
    }

}
