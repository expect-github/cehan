package com.nj.baijiayun.module_public.holder;

import android.widget.ImageView;

import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.IconTextSpan;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.util.List;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.holder
 * @describe
 */
public class CourseHolderHelper {


    private static IconTextSpan iconVipSpan;
    private static IconTextSpan iconCouponSpan;
    private static IconTextSpan iconCourseType;
    private static IconTextSpan iconCourseSpell;

    public static IconTextSpan getIconVipSpan() {

        if (iconCouponSpan == null) {
            iconVipSpan = new IconTextSpan(BaseApp.getInstance(), "会员");
            iconVipSpan.setBgColorResId(R.color.public_tag_vip_bg)
                    .setTextColorResId(R.color.public_tag_vip_text).setAttrToPaint();
        }
        return iconVipSpan;
    }

    public static IconTextSpan getIconCourseSpell() {

        if (iconCourseSpell == null) {
            iconCourseSpell = new IconTextSpan(BaseApp.getInstance(), "拼");
            iconCourseSpell.setBgColorResId(R.color.public_tag_spell_bg)
                    .setTextColorResId(R.color.public_tag_spell_text).setAttrToPaint();
        }
        return iconCourseSpell;
    }

    public static IconTextSpan getCouponSpan() {
        if (iconCouponSpan == null) {
            iconCouponSpan = new IconTextSpan(BaseApp.getInstance(), "券");
        }
        iconCouponSpan
                .setBgColorResId(R.color.public_tag_coupon_bg)
                .setTextColorResId(R.color.public_tag_coupon_text).setAttrToPaint();
        return iconCouponSpan;

    }

    public static IconTextSpan getCourseSpan() {
        if (iconCourseType == null) {
            iconCourseType = new IconTextSpan(BaseApp.getInstance(), "");
            iconCourseType.setBgColorResId(R.color.public_tag_course_bg)
                    .setTextColorResId(R.color.public_tag_course_text).setAttrToPaint();
        }
        return iconCourseType;

    }

    @Deprecated
    public static void showCourseTitleNoCourseType(PublicCourseBean model, CourseTitleView courseNameTextView) {
        courseNameTextView.addTag(model.getTitle(), getIconSpans(model.isVipCourse(), model.isJoinSpell(), model.isHasCoupon()));
    }

    public static void showCourseTitle(PublicCourseBean model, CourseTitleView courseNameTextView) {
        getCourseSpan().setText(ConstsCouseType.getCourseTypeName(model.getCourseType())).setAttrToPaint();
        courseNameTextView.addTag(model.getTitle(), getIconSpansWithCourseType(model.isVipCourse(), model.isJoinSpell(), model.isHasCoupon()));
    }

    public static IconTextSpan[] getIconSpansWithCourseType(boolean isVip, boolean isJoinSpell, boolean isHasCoupon) {
        return new IconTextSpan[]{getCourseSpan(), isVip ? getIconVipSpan() : null,
                isJoinSpell ? getIconCourseSpell() : null,
                isHasCoupon ? getCouponSpan() : null
        };
    }

    public static IconTextSpan[] getIconSpans(boolean isVip, boolean isJoinSpell, boolean isHasCoupon) {
        return new IconTextSpan[]{isVip ? getIconVipSpan() : null,
                isJoinSpell ? getIconCourseSpell() : null,
                isHasCoupon ? getCouponSpan() : null
        };
    }


    public static void setHeadInfo(BaseViewHolder baseViewHolder, List<PublicTeacherBean> data, int[] tvArrAy, int[] ivArrAy) {
        if (data == null) {
            for (int i = 0; i < tvArrAy.length; i++) {
                showTeacherHead(i, false, baseViewHolder, tvArrAy, ivArrAy);
            }
            return;
        }
        for (int i = 0; i < tvArrAy.length; i++) {
            if (i <= (data.size() - 1)) {
                baseViewHolder.setText(tvArrAy[i], data.get(i).getName());
                ImageLoader.with(baseViewHolder.getConvertView().getContext()).load(data.get(i).getAvatar()).asCircle().into((ImageView) baseViewHolder.getView(ivArrAy[i]));
                showTeacherHead(i, true, baseViewHolder, tvArrAy, ivArrAy);
            } else {
                showTeacherHead(i, false, baseViewHolder, tvArrAy, ivArrAy);
            }
        }

    }

    private static void showTeacherHead(int index, boolean isShow, BaseViewHolder baseViewHolder, int[] tvArrAy, int[] ivArrAy) {
        baseViewHolder.setVisibleInVisible(tvArrAy[index], isShow);
        baseViewHolder.setVisibleInVisible(ivArrAy[index], isShow);
    }

}
