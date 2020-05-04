package com.developer.smmmousavi.imageprocessing.ui.fragment.shade;


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
public class ProcessingShadeFragment extends BaseDaggerFragment {

    public static final String TAG = "com.developer.smmmousavi.imageprocessing.ui.fragment.shade.ProcessingShadeFragment";

    public ProcessingShadeFragment() {
        // Required empty public constructor
    }

    public static ProcessingShadeFragment newInstance() {

        Bundle args = new Bundle();

        ProcessingShadeFragment fragment = new ProcessingShadeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shade, container, false);

        return v;
    }

}
