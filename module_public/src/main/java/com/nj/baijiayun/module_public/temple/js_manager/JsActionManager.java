package com.nj.baijiayun.module_public.temple.js_manager;

import com.nj.baijiayun.module_public.temple.js_manager.js_action.ClosePageJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.CourseBuySuccessAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.CourseDetailJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.DistributeShareJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.FilePreViewJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.FullScreenShowJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.HideSoftKeyBordJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.HomePageJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.HomeTabVisibleChangeJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.JumpAppPageJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.LibraryJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.LoginJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.MyLearnedJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.PayJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.PaySuccessRouterCompleteCallbackJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.PlayJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.SelectPhotoJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.SetAppStatusBarColorJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.ShareByShareInfoJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.ShareImgJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.ShowShareJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.js_action.WebJsAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengang
 * @date 2019-08-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager
 * @describe
 */
public class JsActionManager {


    //注册需要的JsActionManager
    private static Map<String, Class<? extends IJsAction>> jsActionRegisterClassMap = new HashMap<>();

    static {
        jsActionRegisterClassMap.put("closeAppPage", ClosePageJsAction.class);
        jsActionRegisterClassMap.put("CourseDetail", CourseDetailJsAction.class);
        jsActionRegisterClassMap.put("homePage", HomePageJsAction.class);
        jsActionRegisterClassMap.put("login", LoginJsAction.class);
        jsActionRegisterClassMap.put("pay", PayJsAction.class);
        jsActionRegisterClassMap.put("play", PlayJsAction.class);
        jsActionRegisterClassMap.put("selectPhoto", SelectPhotoJsAction.class);
        jsActionRegisterClassMap.put("setAppStatusBarColor", SetAppStatusBarColorJsAction.class);
        jsActionRegisterClassMap.put("web", WebJsAction.class);
        jsActionRegisterClassMap.put("myLearned", MyLearnedJsAction.class);
        jsActionRegisterClassMap.put("filePreView", FilePreViewJsAction.class);
        jsActionRegisterClassMap.put("homeTabVisibleChange", HomeTabVisibleChangeJsAction.class);
        jsActionRegisterClassMap.put("shareImg", ShareImgJsAction.class);
        jsActionRegisterClassMap.put("LibraryDetail", LibraryJsAction.class);
        jsActionRegisterClassMap.put("videoPlay", VideoPlayJsAction.class);
        jsActionRegisterClassMap.put("hideSoftInput", HideSoftKeyBordJsAction.class);
        jsActionRegisterClassMap.put("fullScreenShow", FullScreenShowJsAction.class);
        jsActionRegisterClassMap.put("shareByInfo", ShareByShareInfoJsAction.class);
        jsActionRegisterClassMap.put("showAppShare", ShowShareJsAction.class);
        jsActionRegisterClassMap.put("courseBuySuccess", CourseBuySuccessAction.class);
        jsActionRegisterClassMap.put("jumpPageByPath", JumpAppPageJsAction.class);
        jsActionRegisterClassMap.put("paySuccessRouterCompleteCallback", PaySuccessRouterCompleteCallbackJsAction.class);
        jsActionRegisterClassMap.put("distributeShare", DistributeShareJsAction.class);
    }



    private Map<String, IJsAction> jsActionMap = new HashMap<>();

    private JsActionManager() {
    }

    public static JsActionManager getInstance() {
        return JsActionManager.InstanceCreate.instance;
    }

    private static class InstanceCreate {
        static JsActionManager instance = new JsActionManager();
    }

    public IJsAction getJsAction(String key) {
        IJsAction iJsAction = jsActionMap.get(key);
        if (iJsAction != null) {
            return iJsAction;
        }

        Class<? extends IJsAction> aClass = jsActionRegisterClassMap.get(key);
        if (aClass == null) {
            return null;
        }
        try {
            IJsAction newAction = aClass.newInstance();
            jsActionMap.put(key, newAction);
            return newAction;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
