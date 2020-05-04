package com.developer.smmmousavi.imageprocessing.ui.viewholder;

import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.helper.PicassoHelper;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NavbarSliderVH extends SliderViewAdapter.ViewHolder {

    private View mItemView;

    @BindView(R.id.imgImageSlideBackground)
    AppCompatImageView mImgSlideBackground;

    public NavbarSliderVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mItemView = itemView;
    }

    public void bind(ImageModel item) {
        PicassoHelper.loadImage(item.getPath(), mImgSlideBackground);
    }


}
