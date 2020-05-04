package com.developer.smmmousavi.imageprocessing.ui.activity.drawer.callback;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

public interface ContentFragmentInsertCallback {

    @IdRes
    int getFragmentId();

    Fragment getFragmentObject();

    String getFragmentTag();
}
