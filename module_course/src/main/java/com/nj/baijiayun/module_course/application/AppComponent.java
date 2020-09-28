package com.nj.baijiayun.module_course.application;

import android.app.Application;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This is a Dagger component. Refer to {@link } for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * }.
 * //{@link AndroidSupportInjectionModule}
 * // is the module from Dagger.Android that helps with the generation
 * // and location of subcomponents.
 */
//@Singleton
//@Component(modules = {
//        ApplicationModule.class,
//        CourseBindingModule.class,
//        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<CourseApplication> {

//    @Component.Builder
    interface Builder {
//        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
