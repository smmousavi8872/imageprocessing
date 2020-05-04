package com.developer.smmmousavi.imageprocessing.builder.recyclerview;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBuilder implements OnBuildRecyclerView {

    private RecyclerView mRecyclerView;

    public RecyclerViewBuilder(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }


    @Override
    public RecyclerViewBuilder setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
        return this;
    }

    @Override
    public RecyclerViewBuilder setItemViewCacheSize(int cacheSize) {
        mRecyclerView.setItemViewCacheSize(cacheSize);
        return this;
    }

    @Override
    public RecyclerViewBuilder setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
        return this;
    }

    @Override
    public RecyclerViewBuilder setDrawingCacheEnabled(boolean drawingCacheEnabled) {
        mRecyclerView.setDrawingCacheEnabled(drawingCacheEnabled);
        return this;
    }

    @Override
    public RecyclerViewBuilder setDrawingCacheQuality(int quality) {
        mRecyclerView.setDrawingCacheQuality(quality);
        return this;
    }

    @Override
    public RecyclerViewBuilder setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        return this;
    }

    @Override
    public RecyclerView build() {
        return mRecyclerView;
    }
}
