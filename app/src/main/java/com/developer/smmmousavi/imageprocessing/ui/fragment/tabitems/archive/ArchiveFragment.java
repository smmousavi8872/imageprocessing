package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.archive;


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
public class ArchiveFragment extends BaseDaggerFragment {

    public static final String TAG = "com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.archive.ArchiveFragment";

    public static ArchiveFragment newInstance() {

        Bundle args = new Bundle();

        ArchiveFragment fragment = new ArchiveFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ArchiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_arichive, container, false);
        return v;
    }

}
