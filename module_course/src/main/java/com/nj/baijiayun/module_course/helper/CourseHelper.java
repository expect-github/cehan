package com.nj.baijiayun.module_course.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.api.ApiErrorHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.wx.ChapterBean;
import com.nj.baijiayun.module_course.bean.wx.HomeWorkBean;
import com.nj.baijiayun.module_course.bean.wx.MyLearnedDetailWrapperBean;
import com.nj.baijiayun.module_course.bean.wx.SectionBean;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.WxCourseDetailActivity;
import com.nj.baijiayun.module_public.bean.ICourseStudy;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.videoplay.ICourseInfo;
import com.nj.baijiayun.module_public.helper.videoplay.IDownloadInfo;
import com.nj.baijiayun.refresh.recycleview.extend.HeaderAndFooterRecyclerViewAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-08-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.helper
 * @describe
 */
public class CourseHelper {

    /**
     * 需要判断库存
     *
     * @param courseType
     * @return
     */
    public static boolean isNeedJudgeStockNum(int courseType) {
        return ConstsCouseType.isSmallGroup(courseType) || ConstsCouseType.isFace(courseType);
    }

    public static boolean isShowProgress(int courseType) {
        return ConstsCouseType.isLive(courseType)
                || ConstsCouseType.isAudio(courseType)
                || ConstsCouseType.isVideo(courseType);
    }

    public static boolean isShowPlayer(int courseType) {
        return ConstsCouseType.isAudio(courseType)
                || ConstsCouseType.isVideo(courseType);
    }

    public static boolean isCanPlay(int courseType) {
        return ConstsCouseType.isLive(courseType)
                || ConstsCouseType.isAudio(courseType)
                || ConstsCouseType.isVideo(courseType);
    }


    public static boolean isMustJumpBjySdk(int courseType) {
        return ConstsCouseType.isLive(courseType);
    }


    public static ICourseInfo getICourseInfo(MyLearnedDetailWrapperBean.Course publicCourseBean) {
        return new ICourseInfo() {
            @Override
            public String getCourseId() {
                return String.valueOf(publicCourseBean.getCourseId());
            }

            @Override
            public String getCourseCover() {
                return null;
            }

            @Override
            public String getCourseName() {
                return publicCourseBean.getTitle();
            }

            @Override
            public int getCourseType() {
                return publicCourseBean.getCourseType();
            }

        };
    }

    public static ICourseInfo getICourseInfo(PublicCourseBean publicCourseBean) {
        return new ICourseInfo() {
            @Override
            public String getCourseId() {
                return String.valueOf(publicCourseBean.getId());
            }

            @Override
            public String getCourseCover() {
                return null;
            }

            @Override
            public String getCourseName() {
                return publicCourseBean.getTitle();
            }

            @Override
            public int getCourseType() {
                return publicCourseBean.getCourseType();
            }

        };
    }

    public static IDownloadInfo getIDownloadInfo(int sectionId, List result) {
        if (result == null || result.size() <= 0) {
            return null;
        }

        boolean isOnlySection = result.get(0) instanceof SectionBean;
        final ChapterBean resultChapterBean = new ChapterBean();
        final SectionBean resultSectionBean = new SectionBean();
        if (isOnlySection) {
            for (int i = 0; i < result.size(); i++) {
                if (((SectionBean) result.get(i)).getId() == sectionId) {
                    resultSectionBean.setId(((SectionBean) result.get(i)).getId());
                    resultSectionBean.setPeriodsTitle(((SectionBean) result.get(i)).getPeriodsTitle());
                    break;
                }
            }
        } else {
            //遍历父亲
            for (int i = 0; i < result.size(); i++) {
                List<SectionBean> child = ((ChapterBean) result.get(i)).getChild();
                if (child == null) {
                    continue;
                }
                for (int j = 0; j < child.size(); j++) {
                    SectionBean queryBean = child.get(j);
                    if (queryBean.getId() == sectionId) {
                        resultSectionBean.setId(queryBean.getId());
                        resultSectionBean.setPeriodsTitle(queryBean.getPeriodsTitle());
                        break;
                    }
                }
            }

        }

        return new IDownloadInfo() {
            @Override
            public String getSectionId() {

                return String.valueOf(resultSectionBean.getId());
            }

            @Override
            public String getSectionNmae() {
                return resultSectionBean.getPeriodsTitle();
            }

            @Override
            public String getChapterId() {
                return String.valueOf(resultChapterBean.getId());
            }

            @Override
            public String getChapterName() {
                return resultChapterBean.getTitle();
            }
        };
    }

    public static boolean needAddOnlySectionTag(List result) {
        return result != null && result.size() > 0 && result.get(0) instanceof SectionBean;
    }

    public static boolean isChapterFirst(List result) {
        return result != null && result.size() > 0 && result.get(0) instanceof ChapterBean;
    }


    public static void setHomeWork(HomeWorkBean homeWorkBean, View view) {
        boolean noNeedWork = true;
        if (noNeedWork) {
            view.setVisibility(View.GONE);
            return;
        }
        ((TextView) view.findViewById(R.id.tv_homework_name)).setText(homeWorkBean.getHomeworkName());
        view.setVisibility(homeWorkBean.needShowHomeWork() ? View.VISIBLE : View.GONE);
        String showTxt = null;
        if (homeWorkBean.isShowLook()) {
            showTxt = view.getContext().getString(R.string.course_look_work);
        } else if (homeWorkBean.isShowSubmit()) {
            showTxt = view.getContext().getString(R.string.course_submit_bt);
        }
        ((TextView) view.findViewById(R.id.tv_submit)).setText(showTxt);
        view.findViewById(R.id.tv_submit).setVisibility(showTxt == null ? View.GONE : View.VISIBLE);
    }

    public static HeaderAndFooterRecyclerViewAdapter createDefaultAdapter() {
        return new HeaderAndFooterRecyclerViewAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }

    /**
     * 为了 后台判断子课程拼团
     *
     * @param context
     * @return
     */
    public static int getSystemCourseId(Context context) {
        int mSystemCourseId = 0;
        if (context instanceof WxCourseDetailActivity) {
            mSystemCourseId = ((WxCourseDetailActivity) context).getPresenter().getLastPageSystemCourseId();
        }
        return mSystemCourseId;
    }

    public static boolean isCanStudy(ICourseStudy courseStudy) {
        return !(courseStudy.courseStatus() == 2 && courseStudy.isGoToStudy() == 0);
    }


    public static void goImgTxtCourseAndCheckLimit(LifecycleOwner lifecycleOwner,
                                                   int courseId, int sectionId, String name, String systemCourseId) {
        checkCourseLimit(lifecycleOwner, courseId, () -> JumpHelper.jumpImgTxtDetail(sectionId, name, systemCourseId));
    }

    private static void checkCourseLimit(LifecycleOwner lifecycleOwner, int courseId, CourseLimitCheckCallBack callBack) {
        NetMgr.getInstance().getDefaultRetrofit().create(CourseService.class)
                .getCourseLimit(courseId)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as(lifecycleOwner))
                .subscribe(response -> {
                    if (response.getData() != null && response.getData().isLimit()) {
                        ToastUtil.show(com.nj.baijiayun.module_public.R.string.public_limit);
                    } else {
                        callBack.pass();
                    }
                }, throwable -> {
                    ToastUtil.show(ApiErrorHelper.handleError(throwable).getMessage());
                });
    }


    public interface CourseLimitCheckCallBack {
        void pass();
    }


}
