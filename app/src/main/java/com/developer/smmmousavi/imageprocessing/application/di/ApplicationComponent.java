package com.developer.smmmousavi.imageprocessing.application.di;

import android.app.Application;

import com.developer.smmmousavi.imageprocessing.ui.activity.base.di.ActivityBuildersMoudle;
import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.developer.smmmousavi.imageprocessing.ui.fragment.base.di.FragmentBuildersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
    AndroidSupportInjectionModule.class,
    ActivityBuildersMoudle.class,
    FragmentBuildersModule.class,
})

public interface ApplicationComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
