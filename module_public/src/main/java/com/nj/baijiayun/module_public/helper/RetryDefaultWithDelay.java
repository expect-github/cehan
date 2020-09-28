package com.nj.baijiayun.module_public.helper;

import com.nj.baijiayun.logger.log.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author chengang
 * @date 2019-10-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class RetryDefaultWithDelay implements
        Function<Observable<? extends Throwable>, ObservableSource<?>> {

    private final int maxRetries=5;
    private final int retryDelayMillis=2000;
    private int retryCount;

    public RetryDefaultWithDelay() {

    }


    @Override
    public ObservableSource<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {

            Logger.d("retryCount--->"+retryCount+"------"+maxRetries);
            if (++retryCount <= maxRetries) {// 所有的onError都会走到这里，根据需要添加判断控制
                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                return Observable.timer(retryDelayMillis,
                        TimeUnit.MILLISECONDS);
            }
            return  Observable.error(throwable);
        });
    }

}