package com.developer.smmmousavi.imageprocessing.application;


import android.content.Context;

import com.developer.smmmousavi.imageprocessing.BuildConfig;
import com.developer.smmmousavi.imageprocessing.application.di.DaggerApplicationComponent;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraMailSender(mailTo = "smmousavi.developer@gmail.com")
public class BaseApplication extends DaggerApplication {

    private static volatile Context sApplicationContext;

    public static Context getContext() {
        return sApplicationContext;
    }

    //copied form Telegram ApplicationLoader Class
    @Override
    public void onCreate() {
        try {
            sApplicationContext = getApplicationContext();
        } catch (Throwable ignore) {

        }
        super.onCreate();
        if (sApplicationContext == null) {
            sApplicationContext = getApplicationContext();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}
