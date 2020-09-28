package com.nj.baijiayun.module_main.mvp.presenter;

import com.google.gson.reflect.TypeToken;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_main.api.MainService;
import com.nj.baijiayun.module_main.bean.BookBean;
import com.nj.baijiayun.module_main.bean.NavBean;
import com.nj.baijiayun.module_main.bean.NewsBean;
import com.nj.baijiayun.module_main.bean.PublicOpenCourseBean;
import com.nj.baijiayun.module_main.bean.PublicOpenListWrapperBean;
import com.nj.baijiayun.module_main.bean.res.HomeBannerResponse;
import com.nj.baijiayun.module_main.bean.res.HomePageResponse;
import com.nj.baijiayun.module_main.bean.wx.ChannelInfoBean;
import com.nj.baijiayun.module_main.bean.wx.HomeDataWrapperBean;
import com.nj.baijiayun.module_main.mvp.contract.MainContract;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.IChannel;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.bean.response.MessageResponse;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.manager.ShortcutBadgerManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp
 * @describe
 */
public class MainPresenter extends MainContract.Presenter {

    @Inject
    MainService mainService;

    @Inject
    PublicService publicService;

    private boolean isFirst = false;

    @Inject
    MainPresenter() {
    }


    @Override
    public void getList(boolean isFirst) {
        this.isFirst = isFirst;
        if (isFirst) {
            mView.showLoadView();
        }
        getBanner();
        getNav();
        getHomeRecommod();
    }

    @Override
    public void getBanner() {
        submitRequest(mainService.getBanner(), new BaseObserver<HomeBannerResponse>() {
            @Override
            public void onPreRequest() {


            }

            @Override
            public void onSuccess(HomeBannerResponse homeBannerRes) {
                mView.showContentView();
                mView.setNewBannerData(homeBannerRes.getData());
            }

            @Override
            public void onFail(Exception e) {
                if (isFirst) {
                    mView.showErrorDataView();
                }
                mView.showToastMsg(e.getMessage());

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void getHomeRecommod() {
        submitRequest(mainService.getHomePageIndex(), new BaseObserver<HomePageResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(HomePageResponse homePageResponse) {
                mView.showContentView();
//                parseData(homePageResponse.getData());
                mView.setListData(parseData(homePageResponse.getData()));
            }

            @Override
            public void onFail(Exception e) {
                mView.loadFinish(false);
                mView.showToastMsg(e.getMessage());
//                if (isFirst) {
//                    mView.showErrorDataView();
//                }
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getMessageCount() {

        if (!AccountHelper.getInstance().isLogin()) {
            mView.setShowUnreadCount(0);
            return;
        }
        submitRequest(publicService.getMessageUnRead(), new BaseObserver<MessageResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(MessageResponse messageResponse) {
                mView.setShowUnreadCount(messageResponse.getUnReadCount());
                ShortcutBadgerManager.getInstance().setNumber(messageResponse.getUnReadCount());
            }

            @Override
            public void onFail(Exception e) {
                //  mView.showToastMsg(e.getMessage());

            }

            @Override
            public void onNext(MessageResponse messageResponse) {
                if (messageResponse.isSuccess()) {
                    onSuccess(messageResponse);
                }

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    public void getNav() {
        submitRequest(mainService.getHomePageNav(), new BaseSimpleObserver<BaseResponse<List<NavBean>>>() {
            @Override
            public void onSuccess(BaseResponse<List<NavBean>> listBaseResponse) {
                mView.setNavData(listBaseResponse.getData());
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    /**
     * 注意这里解析的model 不要用String 去接受long 类型的 否则会变成科学计数法，
     */
    private List<Object> parseData(List<HomeDataWrapperBean> data) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            IChannel channelInfo = data.get(i).getChannelInfo();
            result.add(channelInfo);

            //这个横向列表
            if (((ChannelInfoBean) channelInfo).isPublicOpenCourse()) {
                List<PublicOpenCourseBean> maps = GsonHelper
                        .getGsonInstance()
                        .fromJson(GsonHelper
                                        .getGsonInstance()
                                        .toJson(data.get(i).getList()),
                                new TypeToken<List<PublicOpenCourseBean>>() {
                                }.getType());
                result.add(new PublicOpenListWrapperBean(maps));

            }
//            else if (((ChannelInfoBean) channelInfo).isTeacherType()) {
//                List<PublicTeacherBean> maps = GsonHelper
//                        .getGsonInstance()
//                        .fromJson(GsonHelper
//                                        .getGsonInstance()
//                                        .toJson(data.get(i).getList()),
//                                new TypeToken<List<PublicTeacherBean>>() {
//                                }.getType());
//                result.add(new AppointTeacherListWrapperBean(maps));
//
//            }
            else {
                result.addAll(parseJsonData(data.get(i).getList(), getParseType(((ChannelInfoBean) channelInfo)).getType()));
            }
        }

        return result;
    }

    private TypeToken getParseType(ChannelInfoBean channelInfo) {
        if (channelInfo.isCourseType()) {
            return new TypeToken<List<PublicCourseBean>>() {
            };
        } else if (channelInfo.isTeacherType()) {
            return new TypeToken<List<PublicTeacherBean>>() {
            };
        } else if (channelInfo.isNewsType()) {
            return new TypeToken<List<NewsBean>>() {
            };
        } else if (channelInfo.isBookType()) {
            return new TypeToken<List<BookBean>>() {
            };
        } else if (channelInfo.isPublicOpenCourse()) {
            return new TypeToken<List<PublicOpenListWrapperBean>>() {
            };
        }
        return new TypeToken<List<Object>>() {
        };
    }


    private List<Map<String, String>> parseJsonData(List<Object> obj, Type type) {
        return GsonHelper.getGsonInstance().fromJson(GsonHelper.getGsonInstance().toJson(obj), type);
    }

}
