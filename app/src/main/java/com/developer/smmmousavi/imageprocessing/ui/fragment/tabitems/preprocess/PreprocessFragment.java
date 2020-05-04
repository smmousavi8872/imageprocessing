package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.preprocess;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.NoiseReductionDialog;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.ScaleControlDialog;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.callback.ReduceNoiseCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.callback.RotationCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.callback.ScaleCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.base.FeatureProcessFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreprocessFragment extends FeatureProcessFragment {

    public static final String TAG = "PreporcessFragmentTag";
    public static final int REQ_CODE_ROTATION_DIALOG = 1001;
    public static final int REQ_CODE_SCLAE_DIALOG = 1002;
    public static final int REQ_CODE_DRAW_CONTOUR_DIALOG = 1003;

    public enum PreprocessType {
        NONE,
        /*ROTATION,*/
        SCALE,
        REMOVE_BACKGROUND,
        DRAW_CONTOURS,
        REDUCE_NOISE,
        GRAY_SCLAE
    }

    //Preprocess Controls results
    private int mRotationDegree = 0;
    private int mHorizontalScaleSize = 0;
    private int mVerticalScaleSize = 0;
    private boolean mPreprocessReduceNoise = true;

    private PreprocessType mPreprocessType;


    public void setRotationCallback(RotationCallback callback) {
        mRotationDegree = callback.getRotationDegree();
    }

    public void setScaleCallback(ScaleCallback callback) {
        mHorizontalScaleSize = callback.getHorizontalScaleSize();
        mVerticalScaleSize = callback.getVeritcalScaleSize();
    }

    public void setReduceNoiseCallback(ReduceNoiseCallback callback) {
        mPreprocessReduceNoise = callback.getReduceNoise();

    }

    public PreprocessFragment() {
        // Required empty public constructor
    }

    public static PreprocessFragment newInstance() {

        Bundle args = new Bundle();

        PreprocessFragment fragment = new PreprocessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initSpinnerList() {
        mSpinnerList.add(getString(R.string.select_operation));
        mSpinnerList.add(getString(R.string.preprocess_scale));
        mSpinnerList.add(getString(R.string.preprocess_remove_background));
        mSpinnerList.add(getString(R.string.preprocess_draw_contours));
        mSpinnerList.add(getString(R.string.preprocess_reduce_noise));
        mSpinnerList.add(getString(R.string.preprocess_gray_scale));
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
                BaseAlertDialogFragment dialog = null;
                String tag = null;
                int requestCode = 0;
                switch (i) {
                    case 0:
                        mPreprocessType = PreprocessType.NONE;
                        mBtnApplyProcess.setEnabled(false);
                        break;
                  /*  case 1:
                        mPreprocessType = PreprocessType.ROTATION;
                        dialog = RotationControlsDialog.newInstance(mRotationDegree);
                        tag = RotationControlsDialog.TAG;
                        requestCode = REQ_CODE_ROTATION_DIALOG;
                        break;*/
                    case 1:
                        mPreprocessType = PreprocessType.SCALE;
                        dialog = ScaleControlDialog.newInstance(mHorizontalScaleSize, mVerticalScaleSize);
                        tag = ScaleControlDialog.TAG;
                        requestCode = REQ_CODE_SCLAE_DIALOG;
                        break;
                    case 2:
                        mPreprocessType = PreprocessType.REMOVE_BACKGROUND;
                        break;
                    case 3:
                        mPreprocessType = PreprocessType.DRAW_CONTOURS;
                        break;
                    case 4:
                        mPreprocessType = PreprocessType.REDUCE_NOISE;
                        break;
                    case 5:
                        mPreprocessType = PreprocessType.GRAY_SCLAE;
                        break;
                }
                if (dialog != null) {
                    dialog.setCancelable(false);
                    dialog.setTargetFragment(PreprocessFragment.this, requestCode);
                    dialog.show(getFragmentManager(), tag);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected List<Bitmap> getProcessResult(List<ImageModel> imageModels) {
        List<Bitmap> artfactResults = null;

        switch (mPreprocessType) {
            case NONE:
                break;
          /*  case ROTATION:
                if (mRotationDegree > 0)
                    artfactResults = mImageProcessing.applyRotateImageOnImageModelList(imageModels, mRotationDegree);
                break;*/
            case SCALE:
                if (mHorizontalScaleSize > 0 || mVerticalScaleSize > 0) {
                    artfactResults = mImageProcessing.applyImageScaleOnImageModelList(imageModels,
                        mHorizontalScaleSize, mVerticalScaleSize);
                }
                break;
            case REMOVE_BACKGROUND:
                try {
                    artfactResults = mImageProcessing.removeBackgroundOnImageModelList(imageModels);
                } catch (Exception e) {
                    Snackbar.make(getView(), getResources().getString(R.string.error_alert), Snackbar.LENGTH_SHORT);
                    e.printStackTrace();
                }
                break;
            case DRAW_CONTOURS:
                artfactResults = mImageProcessing.applyDrawContourOnImageModelList(imageModels, mPreprocessReduceNoise);
                break;
            case REDUCE_NOISE:
                artfactResults = mImageProcessing.applyReduceNoiseOnImageModelList(imageModels);
                break;
            case GRAY_SCLAE:
                artfactResults = mImageProcessing.applyGrayScaleOnImageModelList(imageModels);
                break;
        }
        return artfactResults;
    }

    @OnClick(R.id.imgControls)
    public void setPreprocessControlsListener(View v) {
        BaseAlertDialogFragment dialog = null;
        String tag = null;
        int requestCode = 0;
        switch (mPreprocessType) {
            /*case ROTATION:
                dialog = RotationControlsDialog.newInstance(mRotationDegree);
                requestCode = REQ_CODE_ROTATION_DIALOG;
                tag = RotationControlsDialog.TAG;
                break;*/
            case SCALE:
                dialog = ScaleControlDialog.newInstance(mHorizontalScaleSize, mVerticalScaleSize);
                requestCode = REQ_CODE_SCLAE_DIALOG;
                tag = ScaleControlDialog.TAG;
                break;
            case DRAW_CONTOURS:
                dialog = NoiseReductionDialog.newInstance(mPreprocessReduceNoise);
                requestCode = REQ_CODE_DRAW_CONTOUR_DIALOG;
                tag = NoiseReductionDialog.TAG;
                break;
        }
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.setTargetFragment(PreprocessFragment.this, requestCode);
            dialog.show(getFragmentManager(), tag);
        }
    }


}
