package com.developer.smmmousavi.imageprocessing.ui.viewholder;

import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.developer.smmmousavi.imageprocessing.helper.PicassoHelper;
import com.developer.smmmousavi.imageprocessing.model.ArtefactResultModel;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtefactResultVH extends RecyclerView.ViewHolder {

    private View mItemView;

    @BindView(R.id.imgOriginalImage)
    AppCompatImageView mImgOriginalImage;
    @BindView(R.id.imgResultArtefact)
    AppCompatImageView mImgResultArtefact;

    public ArtefactResultVH(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemView = itemView;
    }

    public void bind(ArtefactResultModel item) {
        if (item.getOriginalImagePath() != null && !item.getOriginalImagePath().isEmpty()) {
            PicassoHelper.loadImage(item.getOriginalImagePath(), mImgOriginalImage);
            PicassoHelper.loadImage(item.getOriginalImagePath(), mImgResultArtefact);
        } else {
            mImgOriginalImage.setImageDrawable(ContextCompat.getDrawable(BaseApplication.getContext(), R.drawable.icon_place_holder));
        }
    }

}
