package com.developer.smmmousavi.imageprocessing.ui.fragment.loadimage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.fragment.base.BaseDaggerFragment;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadImageFragment extends BaseDaggerFragment {

    public static final String TAG = "com.developer.smmmousavi.imageprocessing.ui.fragment.loadimage.LoadImageFragment";

    public static LoadImageFragment newInstance() {

        Bundle args = new Bundle();

        LoadImageFragment fragment = new LoadImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoadImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_load_image, container, false);

        return v;
    }

}
