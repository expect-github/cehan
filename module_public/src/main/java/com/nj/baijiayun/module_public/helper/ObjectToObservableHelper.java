package com.nj.baijiayun.module_public.helper;


import com.nj.baijiayun.logger.log.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 把一个对象转换为可观察的对象，观察粒度到每一个属性
 *
 * @param <T>
 */
public class ObjectToObservableHelper<T extends CloneableObservable> {
    private static Map<Class, ObjectToObservableHelper> objectObservables = new HashMap<>();
    private List<Method> methodList = new ArrayList<>();
    private Map<Method, String> methodPropertyMap = new HashMap<>();
    private T currentValue;
    public static final String PROPERTY_ALL = "property_all";
    private BehaviorSubject<ObservableWrapper<T>> mLoginSubject;
    private Map<Object, CompositeDisposable> mCompositeMap;


    public static <T extends CloneableObservable> ObjectToObservableHelper<T> getInstance(Class<T> tClass) {
        ObjectToObservableHelper object = objectObservables.get(tClass);
        if (object != null) {
            return object;
        }
        throw new IllegalStateException("should init first");
    }

    private ObjectToObservableHelper() {
        mCompositeMap = new HashMap<>();
    }

    public static <T extends CloneableObservable> void init(Class<T> tClass, T defaultValue) {
        ObjectToObservableHelper<T> toObservableHelper = new ObjectToObservableHelper<>();
        toObservableHelper.currentValue = defaultValue;
        toObservableHelper.initCheckMethods(tClass);
        toObservableHelper.mLoginSubject = BehaviorSubject.createDefault(new ObservableWrapper<>(defaultValue, PROPERTY_ALL));
        objectObservables.put(tClass, toObservableHelper);
    }

    /**
     * 获取对象信息所有属性对对应的get方法，用于在对象信息变化时获取变化的属性内容
     *
     * @param clazz
     */
    private void initCheckMethods(Class<T> clazz) {
        for (Method method : clazz.getMethods()) {
            TypeVariable<Method>[] parameters = method.getTypeParameters();
            String name = method.getName();
            Class<?> type = method.getReturnType();
            if (parameters.length == 0 && type != Void.TYPE) {
                if (name.startsWith("get") || name.startsWith("is")) {
                    methodList.add(method);
                    if (name.startsWith("is")) {
                        methodPropertyMap.put(method, name);
                    } else {
                        methodPropertyMap.put(method, getPropertyName(name, "get"));
                    }
                }
            }
        }

    }

    /**
     * get the right property name with the prefix
     *
     * @param name full method name with prefix
     * @param pre  prefix
     * @return property name without prefix
     */
    private static String getPropertyName(String name, String pre) {
        assert (pre != null);
        char first = Character.toLowerCase(name.charAt(pre.length()));
        String substring = name.substring(pre.length() + 1);
        return first + substring;
    }


    /**
     * 监听用户信息所有内容变化
     *
     * @param object   监听的对象
     * @param consumer 监听回调
     */
    public void subscribe(Object object, Consumer<ObservableWrapper<T>> consumer) {
        subscribe(object, consumer, PROPERTY_ALL);
    }

    /**
     * 监听用户信息指定属性变化，可以指定具体的属性名或者{@link #PROPERTY_ALL}来指定所有属性
     *
     * @param object       监听的对象
     * @param consumer     监听回调
     * @param propertyName 需要监听的属性，只有对于属性变化有事件
     */
    public void subscribe(Object object, Consumer<ObservableWrapper<T>> consumer, final String... propertyName) {
        Disposable d = mLoginSubject
                .filter(userInfoChangeWrapper -> {
                    Logger.d("login subject test");
                    List<String> strings = Arrays.asList(propertyName);
                    List<String> changeList = userInfoChangeWrapper.getChangeList();
                    //if the property contains
                    if (strings.contains(PROPERTY_ALL) || changeList.contains(PROPERTY_ALL)) {
                        return true;
                    }
                    return changeList.removeAll(strings);
                })
//                .map((Function<ObservableWrapper<T>, T>) userInfoChangeWrapper -> {
//                    T userLoginBean = userInfoChangeWrapper.getContent();
//                    return userLoginBean == null ? UserLoginBean.getEmpty() : userLoginBean;
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e(throwable);
                    }
                });
        CompositeDisposable compositeDisposable = mCompositeMap.get(object);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            mCompositeMap.put(object, compositeDisposable);
        }
        compositeDisposable.add(d);
    }

    public void unSubscribe(Object object) {
        CompositeDisposable remove = mCompositeMap.remove(object);
        if (remove != null) {
            remove.clear();
        }
    }

    /**
     * 保存信息
     *
     * @return
     */
    public boolean saveContent(T userLoginBean) {
        Logger.d("saveContent" + userLoginBean);
        try {
            List<String> changeList = checkChange(userLoginBean);
            if (changeList.size() > 0) {
                //sUserLoginBean不能和任何其它对象使用同一个对象，否则会导致checkChange结果错误；
                currentValue = userLoginBean == null ? null : (T) userLoginBean.clone();
                notifyUserInfoChanged(userLoginBean, changeList);

            }
            return true;
        } catch (Exception e) {
            Logger.e("saveContent Exectption" + e.getMessage());

            Logger.e(e.getMessage());
            return false;
        }
    }

    /**
     * 通知订阅者用户信息变化
     */
    private void notifyUserInfoChanged(T userLoginBean, List<String> changeList) {
        mLoginSubject.onNext(new ObservableWrapper<>(userLoginBean, changeList));
    }

    /**
     * 检查内容中发生变化的属性
     *
     * @param current 新的实体类对象
     * @return 发生变化的属性集合
     */
    private List<String> checkChange(T current) throws InvocationTargetException, IllegalAccessException {
        List<String> changePropertyList = new ArrayList<>();
        if (current == null || this.currentValue == null) {
            changePropertyList.add(PROPERTY_ALL);
            return changePropertyList;
        }
        for (Method method : methodList) {
            Object currentValue = method.invoke(current);
            Object preValue = method.invoke(this.currentValue);
            if (preValue == null && currentValue == null) {
            } else if (currentValue == null) {
                changePropertyList.add(methodPropertyMap.get(method));
            } else if (!currentValue.equals(preValue)) {
                changePropertyList.add(methodPropertyMap.get(method));
            }
        }
        return changePropertyList;
    }


    /**
     * 清除所有信息
     *
     * @return isSuccess
     */
    public static boolean clearInfo(Class tClass) {
        try {
            ObjectToObservableHelper remove = objectObservables.remove(tClass);
            if (remove != null) {
                remove.notifyUserInfoChanged(null, Collections.singletonList(PROPERTY_ALL));
            }
            return true;
        } catch (Exception e) {
            Logger.e(e.getMessage());
            return false;
        }
    }
}
