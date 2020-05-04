package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.filter.di;

import com.developer.smmmousavi.imageprocessing.processtools.ImageProcessing;
import com.developer.smmmousavi.imageprocessing.repository.ImageModelRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class FilterFragmentModule {
    @Provides
    protected ImageModelRepo provideImageModelRepo() {
        return ImageModelRepo.getInstance();
    }

    @Provides
    protected ImageProcessing provideEdgeDetectorFilter() {
        return new ImageProcessing();
    }
}
