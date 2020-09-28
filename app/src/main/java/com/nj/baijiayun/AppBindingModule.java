package com.nj.baijiayun;

import com.nj.baijiayun.module_common.demo.DemoActivityBindingModule;
import com.nj.baijiayun.module_course.CourseBindingModule;
import com.nj.baijiayun.module_download.DownloadBindingModule;
import com.nj.baijiayun.module_main.MainBindingModule;
import com.nj.baijiayun.module_public.PublicBindingModule;

import dagger.Module;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module(includes = {
        DemoActivityBindingModule.class,
        PublicBindingModule.class,
        CourseBindingModule.class,
        DownloadBindingModule.class,
        MainBindingModule.class
})
public abstract class AppBindingModule {

}
