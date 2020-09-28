package com.nj.baijiayun.module_public.manager;

import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.MessageResponse;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * @author chengang
 * @date 2020-03-23
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.manager
 * @describe
 */
public class ShortcutBadgerManager implements IShortcutBadger {
    private int currentCount = 0;
    private boolean isEnable = true;
    private static volatile ShortcutBadgerManager singleton = null;

    private ShortcutBadgerManager() {
    }

    public static ShortcutBadgerManager getInstance() {
        if (singleton == null) {
            synchronized (ShortcutBadgerManager.class) {
                if (singleton == null) {
                    singleton = new ShortcutBadgerManager();
                }
            }
        }
        return singleton;
    }

    public ShortcutBadgerManager setEnable(boolean enable) {
        Logger.d("setEnable"+enable);
        isEnable = enable;
        return this;
    }

    @Override
    public void setNumber(int count) {
        this.currentCount = count;
        currentNumberChangeOrUpdate();
    }

    @Override
    public void add(int count) {
        currentCount += count;
        currentNumberChangeOrUpdate();
    }

    @Override
    public void add() {
        currentCount += 1;
        currentNumberChangeOrUpdate();
    }

    @Override
    public void remove() {
        currentCount -= 1;
        currentNumberChangeOrUpdate();

    }

    @Override
    public void remove(int count) {
        currentCount -= count;
        currentNumberChangeOrUpdate();
    }

    @Override
    public void updateNumberFromDataSource() {
        if (!isEnable) {
            return;
        }
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getMessageUnRead()
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<MessageResponse>() {
                    @Override
                    public void onSuccess(MessageResponse baseResponse) {
                        currentCount = baseResponse.getUnReadCount();
                        currentNumberChangeOrUpdate();
                    }

                    @Override
                    public void onNext(MessageResponse messageResponse) {
                        if (messageResponse.isSuccess()) {
                            onSuccess(messageResponse);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }

    private void currentNumberChangeOrUpdate() {
        if (isEnable) {
            ShortcutBadger.applyCount(BaseApp.getInstance(), currentCount);
        } else {
            ShortcutBadger.applyCount(BaseApp.getInstance(), 0);
        }
    }

    @Override
    public void switchEnable(boolean isEnable) {
        this.isEnable = isEnable;
        currentNumberChangeOrUpdate();
        ConfigManager.getInstance().saveShortcutBadgeStatus(isEnable);
        if (isEnable) {
            updateNumberFromDataSource();
        }
    }

    @Override
    public void tryUpdateNumber() {
        if (currentCount > 0) {
            updateNumberFromDataSource();
        }
    }

}
