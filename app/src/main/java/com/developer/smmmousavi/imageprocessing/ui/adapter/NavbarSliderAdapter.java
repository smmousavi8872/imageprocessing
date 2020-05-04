package com.developer.smmmousavi.imageprocessing.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.ui.viewholder.NavbarSliderVH;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class NavbarSliderAdapter extends SliderViewAdapter<NavbarSliderVH> {

    private List<ImageModel> mImageModels;

    public NavbarSliderAdapter(List<ImageModel> imageModels) {
        mImageModels = imageModels;
    }

    public void setItemList(List<ImageModel> imageModels) {
        mImageModels = imageModels;
    }

    @Override
    public NavbarSliderVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_slider, null);
        return new NavbarSliderVH(inflate);
    }

    @Override
    public void onBindViewHolder(NavbarSliderVH viewHolder, int position) {
        viewHolder.bind(mImageModels.get(position));
    }

    @Override
    public int getCount() {
        return mImageModels.size();
    }

}
