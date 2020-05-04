package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.retrieval.di;

import com.developer.smmmousavi.imageprocessing.processtools.ImageComprator;

import dagger.Module;
import dagger.Provides;

@Module
public class RetrievalFragmentModule {

    @Provides
    ImageComprator provideImageComparator() {
        return new ImageComprator();
    }
}
