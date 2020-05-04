package com.developer.smmmousavi.imageprocessing.ui.fragment.base.di;

import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.archive.ArchiveFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.archive.di.ArchiveFragmentModule;
import com.developer.smmmousavi.imageprocessing.ui.fragment.base.BaseDaggerFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.loadimage.LoadImageFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.loadimage.di.LoadImageFragmentModule;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.base.FeatureProcessFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.base.di.FeatureProcessFragmentModule;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.filter.FilterFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.filter.di.FilterFragmentModule;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.morphology.MorphlogyFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.morphology.di.MorphologyFragmentModuel;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.preprocess.PreprocessFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.preprocess.di.PreprocessFragmentModule;
import com.developer.smmmousavi.imageprocessing.ui.fragment.main.MainDrawerFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.main.di.MainDrawerFragmentModule;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.retrieval.RetrievalFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.retrieval.di.RetrievalFragmentModule;
import com.developer.smmmousavi.imageprocessing.ui.fragment.shade.ProcessingShadeFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.splash.SplashFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.splash.di.SplashFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = {BaseDaggerFragmentModule.class})
    public abstract BaseDaggerFragment contributeBaseDaggerFragment();

    @ContributesAndroidInjector(modules = {MainDrawerFragmentModule.class})
    public abstract MainDrawerFragment contributeHomeFragment();

    @ContributesAndroidInjector(modules = {SplashFragmentModule.class})
    public abstract SplashFragment cotntirbuteSplashFragment();

    @ContributesAndroidInjector(modules = {LoadImageFragmentModule.class})
    public abstract LoadImageFragment contributeLoadImageFragment();

    @ContributesAndroidInjector(modules = {ArchiveFragmentModule.class})
    public abstract ArchiveFragment contirbuteArchiveFragment();

    @ContributesAndroidInjector(modules = {FeatureProcessFragmentModule.class})
    public abstract FeatureProcessFragment contirbuteFilterFarmgnet();

    @ContributesAndroidInjector(modules = {PreprocessFragmentModule.class})
    public abstract PreprocessFragment contributePreprocessFragment();

    @ContributesAndroidInjector(modules = {FilterFragmentModule.class})
    public abstract FilterFragment contributeFilterFragment();

    @ContributesAndroidInjector(modules = {MorphologyFragmentModuel.class})
    public abstract MorphlogyFragment contirbuteMorphologyFragment();

    @ContributesAndroidInjector(modules = {RetrievalFragmentModule.class})
    public abstract RetrievalFragment contributeRetrievalFargment();

    @ContributesAndroidInjector
    public abstract ProcessingShadeFragment contributeShadeFragment();
}
