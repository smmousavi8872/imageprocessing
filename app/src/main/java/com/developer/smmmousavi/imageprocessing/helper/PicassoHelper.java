package com.developer.smmmousavi.imageprocessing.helper;

import android.widget.ImageView;

import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PicassoHelper {

    /**
     * @HardCodded
     */
    public static void loadImage(String url, ImageView imageView) {
        File file = new File(url);
        Picasso.with(BaseApplication.getContext())
            .load(file)
            .fit()
            .centerCrop()
            .into(imageView);
    }

}
