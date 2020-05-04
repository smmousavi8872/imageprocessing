package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.morphology;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.ui.activity.drawer.BaseDrawerActivity;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.base.FeatureProcessFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MorphlogyFragment extends FeatureProcessFragment {

    public enum MorphologyType {
        NONE,
        ERROSION,
        DILATION,
        OPENING,
        CLOSING
    }

    public static final String TAG = "com.developer.smmmousavi.imageprocessing.ui.fragment.segment.MorphlogyFragment";

    private BaseDrawerActivity mParentActivity;
    private MorphologyType mMorphologyType;


    public static MorphlogyFragment newInstance() {

        Bundle args = new Bundle();

        MorphlogyFragment fragment = new MorphlogyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MorphlogyFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(BaseApplication.getContext(), R.layout.item_spinner_text, mSpinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnFilterSelect.setAdapter(adapter);
        mSpnFilterSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mBtnApplyProcess.setEnabled(true);
                switch (i) {
                    case 0:
                        mMorphologyType = MorphologyType.NONE;
                        mBtnApplyProcess.setEnabled(false);
                        break;
                    case 1:
                        mMorphologyType = MorphologyType.ERROSION;
                        break;
                    case 2:
                        mMorphologyType = MorphologyType.DILATION;
                        break;
                    case 3:
                        mMorphologyType = MorphologyType.OPENING;
                        break;
                    case 4:
                        mMorphologyType = MorphologyType.CLOSING;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void initSpinnerList() {
        mSpinnerList.add(getString(R.string.select_operation));
        mSpinnerList.add(getString(R.string.erosion));
        mSpinnerList.add(getString(R.string.dilation));
        mSpinnerList.add(getString(R.string.opening));
        mSpinnerList.add(getString(R.string.closing));
    }

    @Override
    protected List<Bitmap> getProcessResult(List<ImageModel> imageModels) {
        List<Bitmap> results = new ArrayList<>();
        switch (mMorphologyType) {
            case NONE:
                break;
            case DILATION:
                results = mImageProcessing.dilateImageModelList(imageModels);
                break;
            case ERROSION:
                results = mImageProcessing.erodeImageModelList(imageModels);
                break;
            case CLOSING:
                results = mImageProcessing.closingOnImageModelList(imageModels);
                break;
            case OPENING:
                results = mImageProcessing.openingOnImageModelList(imageModels);
                break;
        }
        return results;

    }

}
