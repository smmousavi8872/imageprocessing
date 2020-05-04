package com.developer.smmmousavi.imageprocessing.ui.viewholder;

import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.helper.PicassoHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CbirResultVH extends RecyclerView.ViewHolder {
    @BindView(R.id.imgCbirResult)
    AppCompatImageView mImgCbirResult;

    public CbirResultVH(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnLongClickListener(v -> {
            mImgCbirResult.setBackgroundDrawable(itemView.getContext().getResources().getDrawable(R.drawable.bg_selected_item));
            return true;
        });
    }

    public void bind(String item) {
        PicassoHelper.loadImage(item, mImgCbirResult);
    }

}
