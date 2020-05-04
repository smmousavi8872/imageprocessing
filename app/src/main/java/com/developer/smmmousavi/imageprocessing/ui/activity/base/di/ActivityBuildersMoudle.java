package com.developer.smmmousavi.imageprocessing.ui.activity.base.di;

import com.developer.smmmousavi.imageprocessing.ui.activity.base.BaseDaggerAppCompatActivity;
import com.developer.smmmousavi.imageprocessing.ui.activity.main.MainDrawerActivity;
import com.developer.smmmousavi.imageprocessing.ui.activity.main.di.MainDrawerActivityModule;
import com.developer.smmmousavi.imageprocessing.ui.activity.loadimage.LoadImageActivity;
import com.developer.smmmousavi.imageprocessing.ui.activity.loadimage.di.LoadImageActivityModule;
import com.developer.smmmousavi.imageprocessing.ui.activity.opencv.LiveCameraActivity;
import com.developer.smmmousavi.imageprocessing.ui.activity.opencv.di.OpenCVActivityModule;
import com.developer.smmmousavi.imageprocessing.ui.activity.splash.SplashActivity;
import com.developer.smmmousavi.imageprocessing.ui.activity.splash.di.SplashActivityMoudle;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersMoudle {

    @ContributesAndroidInjector(modules = {BaseDaggerAppCompatActivityModule.class})
    public abstract BaseDaggerAppCompatActivity contributeBaseDagggerAppCompatActivity();

    @ContributesAndroidInjector(modules = {MainDrawerActivityModule.class})
    public abstract MainDrawerActivity contributeHomeDrawerActivity();

    @ContributesAndroidInjector(modules = {OpenCVActivityModule.class})
    public abstract LiveCameraActivity contributeOpenCVActivity();

    @ContributesAndroidInjector(modules = {SplashActivityMoudle.class})
    public abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector(modules = {LoadImageActivityModule.class})
    public abstract LoadImageActivity contributeLoadImageActivity();
}
