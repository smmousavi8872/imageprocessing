package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.morphology.di;

import com.developer.smmmousavi.imageprocessing.processtools.ImageProcessing;
import com.developer.smmmousavi.imageprocessing.repository.ImageModelRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class MorphologyFragmentModuel {


    @Provides
    protected ImageModelRepo provideImageModelRepo() {
        return ImageModelRepo.getInstance();
    }

    @Provides
    protected ImageProcessing provideEdgeDetectorFilter() {
        return new ImageProcessing();
    }
}
