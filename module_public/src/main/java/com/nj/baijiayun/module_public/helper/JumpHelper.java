package com.nj.baijiayun.module_public.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.helper.config.tasks.PersonServiceTask;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.JsActionManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengang
 * @date 2019-07-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.helper
 * @describe
 */
public class JumpHelper {

    /**
     * 跳转方式 1视频课 2音频课 3图文 4培训任务 5线下培训 6试卷 7外链 8无跳转
     */
    private static final int TYPE_COURSE = 2;
    private static final int TYPE_LINK = 1;
    private static final int TYPE_NEWS = 3;
    private static final int TYPE_BOOK = 4;
    private static final int TYPE_LIBRARY = 5;

    //    跳转类型 0无链接 1链接 2课程 3.资讯 4:图书 5:文库
    public static void jump(JumpWrapBean jumpWrapBean) {
        switch (jumpWrapBean.getType()) {

            case TYPE_LINK:
                jumpWebView(jumpWrapBean.getTypeContent());
                break;
            case TYPE_COURSE:
                try {
                    ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL).withInt("courseId", Integer.parseInt(jumpWrapBean.getTypeContent())).navigation();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                break;

            case TYPE_NEWS:
                JumpHelper.jumpWebViewNoNeedAppTitle("news-detail?id=" + jumpWrapBean.getTypeContent());
                break;
            case TYPE_BOOK:
                JumpHelper.jumpWebViewNoNeedAppTitle("book-detail?id=" + jumpWrapBean.getTypeContent());
                break;
            case TYPE_LIBRARY:
                JumpHelper.jumpLibraryDetail(jumpWrapBean.getId());
                break;
            default:
                break;
        }

    }


    public static void handlerWebAction(Context context, AppWebView webView, JsActionDataBean jsActionDataBean) {
        if (jsActionDataBean == null) {
            return;
        }
        IJsAction jsAction = JsActionManager.getInstance().getJsAction(jsActionDataBean.getName());
        if (jsAction != null) {
            jsAction.handlerData(context, webView, jsActionDataBean);
        }
    }


    private static Map<String, String> getParams(String params) {

        Map<String, String> result = new HashMap<>();
        if (params == null || params.length() == 0) {
            return result;
        }
        String[] split = params.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            result.put(split1[0], split[1]);
        }
        return result;
    }


    public static void jumpWebView(String url) {
        ARouter.getInstance()
                .build(RouterConstant.PAGE_WEB_VIEW)
                .withString("url", ConstsH5Url.getUrl(url))
                .navigation();
    }

    public static void jumpLogin() {
        ARouter.getInstance()
                .build(RouterConstant.PAGE_LOGIN)
                .navigation();
    }

    public static boolean checkLogin() {
        if (AccountHelper.getInstance().getInfo() == null || AccountHelper.getInstance().getToken() == null) {
            jumpLogin();
            return true;
        }
        return false;

    }

    public static void jumpMyLearned() {
        ARouter.getInstance().build(RouterConstant.PAGE_COURSE_MY_LEARNED_LIST).navigation();
    }

    public static void jumpVip() {
        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getVip());
    }

    public static void jumpTeacherInfo(int teacherId) {
        JumpHelper.jumpWebViewNoNeedAppTitle(MessageFormat.format("{0}?id={1}&back=1", ConstsH5Url.getTeacher(), String.valueOf(teacherId)));

    }

    public static void jumpImgTxtDetail(int sectionId, String name, String systemCourseId) {
        //systemCourseId 默认为0  从系统课进来的子课程需要携带
        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getImgTxtDetail() + MessageFormat.format("?chapter_id={0}&chapter_name={1}&system_course_id", String.valueOf(sectionId), name, systemCourseId));
    }

    public static void jumpBuyCourse(int courseId, int courseType) {
        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getPurchase() + MessageFormat.format("?id={0}&type={1}", String.valueOf(courseId), courseType));

    }

    public static void jumpAssemblePay(int courseId, int courseType, int spellId, int groupId) {
        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getPurchase() + MessageFormat.format("?id={0}&type={1}&spell_id={2}&group_id={3}", String.valueOf(courseId), courseType, String.valueOf(spellId), String.valueOf(groupId)));

    }

    public static void jumpPreViewSingleImgView(String path) {
        ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_IMAGE_PREVIEW).withString("path", path).navigation();
    }

    public static void jumpPreViewMultiImgView(int index, ArrayList<String> paths) {
        ARouter.getInstance()
                .build(RouterConstant.PAGE_PUBLIC_IMAGE_PREVIEW)
                .withInt("index", index)
                .withStringArrayList("paths", paths)
                .navigation();
    }


    public static void jumpPreViewFile(String filePath, String fileName) {
        ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_FILE_PREVIEW)
                .withString("fileUrl", filePath)
                .withString("fileName", fileName)
                .navigation();
    }

    public static void jumpTestPreView() {
        ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_FILE_PREVIEW)
                .withString("fileUrl", "https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/file/2019eBtDoWKKJ21568169050.pptx")
                .navigation();
    }

    public static void jumpWebViewNoNeedAppTitle(String url, boolean... needTitle) {
        boolean needShowAppTitle = true;
        if (needTitle != null && needTitle.length > 0) {
            needShowAppTitle = needTitle[0];
        }
        ARouter.getInstance()
                .build(RouterConstant.PAGE_WEB_VIEW)
                .withString("url", ConstsH5Url.getUrl(url))
                .withBoolean("appbar", needShowAppTitle)
                .navigation();
    }


    public static void jumpCourseDetail(int courseId) {
        ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL).withInt("courseId", courseId).navigation();

    }

    public static void jumpCourseHomeWork(int courseId) {
        jumpWebViewNoNeedAppTitle(ConstsH5Url.getHomework() + "?id=" + courseId);
    }

    public static void jumpSectionHomeWork(int sectionId) {
        jumpWebViewNoNeedAppTitle(ConstsH5Url.getHomework() + "?period_id=" + sectionId);

    }

    public static void jumpBookDetail(int bookId) {
        jumpWebViewNoNeedAppTitle(MessageFormat.format("book-detail?id={0}", String.valueOf(bookId)));
    }

    public static void jumpBookDetailWithDistribution(int bookId) {
        jumpWebViewNoNeedAppTitle(MessageFormat.format("book-detail?id={0}&dis_type=1", String.valueOf(bookId)));
    }

    public static void jumpLibraryDetail(int id) {
        if (JumpHelper.checkLogin()) {
            return;
        }
        ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_LIBRARY).withInt("libraryId", id).navigation();
    }

    public static void jumpOrderDetail(String orderId, int orderType) {
        jumpWebViewNoNeedAppTitle(MessageFormat.format(ConstsH5Url.getOrderDetail() + "?order_id={0}&order_type={1}", orderId, String.valueOf(orderType)));
    }

    public static void jumpNewsDetail(int newsId) {
        jumpWebViewNoNeedAppTitle(MessageFormat.format("news-detail?id={0}", String.valueOf(newsId)));
    }

    public static void jumpPersonService() {
        String personServiceLink = ConfigManager.getInstance().getPersonServiceLink();
        if (TextUtils.isEmpty(personServiceLink)) {
            PersonServiceTask personServiceTask = new PersonServiceTask() {
                @Override
                protected void loadDataSuccess(Object data) {
                    super.loadDataSuccess(data);
                    if (data != null && !TextUtils.isEmpty(String.valueOf(data))) {
                        jumpWebViewNoNeedAppTitle(String.valueOf(data));
                    }
                }
                @Override
                protected void loadDataFail(Throwable throwable) {
                    ToastUtil.show(throwable.getMessage());

                }
            };
            personServiceTask.start();
        } else {
            jumpWebViewNoNeedAppTitle(personServiceLink);
        }

    }


}
