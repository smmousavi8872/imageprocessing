package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.filter;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.developer.smmmousavi.imageprocessing.constant.CannyControlsDefaultVal;
import com.developer.smmmousavi.imageprocessing.constant.GuassianControls;
import com.developer.smmmousavi.imageprocessing.constant.SobelControlsDefValue;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.canny.CannyControlsCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.canny.CannyControlsDialog;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.guassiandiff.GaussianDiffControlsDialg;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.prewitt.FeaturesVectorControlsDialg;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.sobel.SobelControlsCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.sobel.SobelControlsDialog;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.base.FeatureProcessFragment;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends FeatureProcessFragment {

    public enum FilterType {
        NONE,
        SOBEL,
        CANNY,
        FEATURE_VECTOR,
        GUASSIAN_DIFF
    }

    //Canny Controls results
    private int mCannyHorizontalThrValue = CannyControlsDefaultVal.DEF_THR1;
    private int mCannyVerticalThrValue = CannyControlsDefaultVal.DEF_THR2;
    private boolean mCannyReduceNoise = true;

    //Soble Controls results
    private int mSobelKernelSize = SobelControlsDefValue.DEF_K_SIZE;
    private int mSobelScaleSize = SobelControlsDefValue.DEF_SCALE;
    private int mSobelDeltaSize = SobelControlsDefValue.DEF_DELTA;
    private boolean mSobelReduceNoise = true;

    //Gaussian Deff Controls results
    private int mGuassianDeffThr = GuassianControls.DEF_THR;
    private int mGuassianDeffMaxVal = GuassianControls.DEF_MAXVAL;
    private boolean mGuassianDiffReduceNoise = true;

    //Features Vector values
    private double[] mRGBArverageValue;
    private double[] mHSVAverageValue;
    private org.opencv.core.Point mTopLeftPoint;
    private org.opencv.core.Point mBottomRigthPoint;
    private double mLargestRectArea;

    private FilterType mFilterType;

    public void setCannyControlsCallback(CannyControlsCallback cannyControlsCallback) {
        mCannyHorizontalThrValue = cannyControlsCallback.getHorizontalThr();
        mCannyVerticalThrValue = cannyControlsCallback.getVerticalThr();
        mCannyReduceNoise = cannyControlsCallback.getNoiseReduction();
        mSetAsSelectedArtefact = cannyControlsCallback.getResultAsSelectedArtefact();
    }


    public void setSobelControlsCallback(SobelControlsCallback sobelControlsCallback) {
        mSobelKernelSize = sobelControlsCallback.getKernelSize();
        mSobelScaleSize = sobelControlsCallback.getScaleSize();
        mSobelDeltaSize = sobelControlsCallback.getDeltaSize();
        mSobelReduceNoise = sobelControlsCallback.noiseReduction();
        mSetAsSelectedArtefact = sobelControlsCallback.setAsSelectedArtefact();
    }

    public static FilterFragment newInstance() {

        Bundle args = new Bundle();

        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initSpinnerList() {
        mSpinnerList.add(getString(R.string.select_operation));
        mSpinnerList.add(getString(R.string.filter_canny));
        mSpinnerList.add(getString(R.string.filter_sobel));
        mSpinnerList.add(getString(R.string.filter_guassian_diff));
        mSpinnerList.add(getString(R.string.features_vector));
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
                        mFilterType = FilterType.NONE;
                        mBtnApplyProcess.setEnabled(false);
                        break;
                    case 1:
                        mFilterType = FilterType.CANNY;
                        break;
                    case 2:
                        mFilterType = FilterType.SOBEL;
                        break;
                    case 3:
                        mFilterType = FilterType.GUASSIAN_DIFF;
                        break;
                    case 4:
                        mFilterType = FilterType.FEATURE_VECTOR;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected List<Bitmap> getProcessResult(List<ImageModel> imageModels) {
        return getFiltersResult(imageModels);
    }

    private List<Bitmap> getFiltersResult(List<ImageModel> imageModels) {
        List<Bitmap> artfactResults = null;
        switch (mFilterType) {
            case NONE:
                break;
            case CANNY:
                artfactResults = (mImageProcessing.applyCannyOnImageModelList(imageModels, mCannyHorizontalThrValue,
                    mCannyVerticalThrValue, mCannyReduceNoise));
                break;
            case SOBEL:
                artfactResults = mImageProcessing.applySobelOnImageModelList(imageModels, mSobelKernelSize,
                    mSobelScaleSize, mSobelDeltaSize, mSobelReduceNoise);
                break;
            case GUASSIAN_DIFF:
                artfactResults = mImageProcessing.applyGaussianDiffOnImageModelList(imageModels, GuassianControls.DEF_THR,
                    GuassianControls.DEF_MAXVAL, true);
                break;
            case FEATURE_VECTOR:
                artfactResults = mImageProcessing.applyImageHsvOnImageModelLsit(imageModels);
                mRGBArverageValue = mImageProcessing.getAverageRgbValue(imageModels.get(0).getPath());
                mHSVAverageValue = mImageProcessing.getAverageHsvValue(imageModels.get(0).getPath());
                mTopLeftPoint = mImageProcessing.getTopLeftPoint(imageModels.get(0).getPath(), true);
                mBottomRigthPoint = mImageProcessing.getBottomRightPoint(imageModels.get(0).getPath(), true);
                mLargestRectArea = mImageProcessing.getLargestRectArea(imageModels.get(0).getPath(), true);
                break;
        }
        return artfactResults;
    }

    @OnClick(R.id.imgControls)
    void setFilterControlsListener(View v) {
        BaseAlertDialogFragment dialog = null;
        String tag = null;
        if (mFilterType == FilterType.NONE)
            Snackbar.make(v, getResources().getString(R.string.no_filter_selected), Snackbar.LENGTH_SHORT).show();
        else {
            try {
                switch (mFilterType) {
                    case CANNY:
                        dialog = CannyControlsDialog.newInstance(mCannyHorizontalThrValue, mCannyVerticalThrValue,
                            mCannyReduceNoise, mSetAsSelectedArtefact);
                        dialog.setTargetFragment(this, TARGET_FRAGMENT_CANNY_REQ_CODE);
                        tag = CannyControlsDialog.TAG;
                        break;
                    case SOBEL:
                        dialog = SobelControlsDialog.newInstance(mSobelKernelSize, mSobelScaleSize, mSobelDeltaSize,
                            mSobelReduceNoise, mSetAsSelectedArtefact);
                        dialog.setTargetFragment(this, TARGET_FRAGMENT_SOBEL_REQ_CODE);
                        tag = SobelControlsDialog.TAG;
                        break;
                    case GUASSIAN_DIFF:
                        dialog = GaussianDiffControlsDialg.newInstance();
                        dialog.setTargetFragment(this, TARGET_FRAGMENT_GAUSSIAN_REQ_CODE);
                        break;
                    case FEATURE_VECTOR:
                        dialog = FeaturesVectorControlsDialg.newInstance(mRGBArverageValue, mHSVAverageValue,
                            mTopLeftPoint.x, mTopLeftPoint.y, mBottomRigthPoint.x, mBottomRigthPoint.y, mLargestRectArea);
                        dialog.setTargetFragment(this, TARGET_FRAGMENT_PREWITT_REQ_CODE);
                        break;
                }
                dialog.setCancelable(false);
                dialog.show(getFragmentManager(), tag);
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(v, "Select an image and click apply, first!", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

}
