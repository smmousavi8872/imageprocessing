package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.canny;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.constant.CannyControlsDefaultVal;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.base.FeatureProcessFragmentViewModel;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.filter.FilterFragment;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.google.android.material.button.MaterialButton;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.List;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.lifecycle.ViewModelProviders;

public class CannyControlsDialog extends BaseAlertDialogFragment implements CannyControlsCallback {

    public static final String TAG = "CannyControlsDialogTag";
    public static final String ARGS_HOR_THR = "ArgsHorThr";
    public static final String ARGS_VER_THR = "ArgsVerThr";
    public static final String ARGS_REDUCE_NOISE = "ArgsReduceNoise";
    public static final String ARGS_SET_RESULT_AS_SELECTED_ARTEFACT = "ArgsSetResultAsSelectedArtefact";

    private RangeSeekBar mSeekbarHorizontalThr;
    private RangeSeekBar mSeekbarVerticalThr;
    private AppCompatCheckBox mChkNoiseReduction;
    private AppCompatCheckBox mChkSetResultAsSelectedArtefact;

    private FeatureProcessFragmentViewModel mFeatureProcessFragmentViewModel;
    List<ImageModel> mImageModels;
    private MaterialButton mBtnResetToDefaults;

    public static CannyControlsDialog newInstance(int horThr, int verThr, boolean reduceNoise, boolean setResultAsSelectedArtefact) {

        Bundle args = new Bundle();

        args.putInt(ARGS_HOR_THR, horThr);
        args.putInt(ARGS_VER_THR, verThr);
        args.putBoolean(ARGS_REDUCE_NOISE, reduceNoise);
        args.putBoolean(ARGS_SET_RESULT_AS_SELECTED_ARTEFACT, setResultAsSelectedArtefact);
        CannyControlsDialog fragment = new CannyControlsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getDialogContentView() {
        //get context from BaseApplication resulted in wrong inflation
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_canny_controls, null, false);
        mFeatureProcessFragmentViewModel = ViewModelProviders.of(this).get(FeatureProcessFragmentViewModel.class);
        mFeatureProcessFragmentViewModel.getAllImageModels().observe(this, imageModels -> {
            mImageModels = imageModels;
            Log.i("TAG", "ImageModelSize: " + mImageModels.size());
        });
        findViews(v);

        initViews();

        return v;
    }

    private void findViews(View v) {
        mSeekbarHorizontalThr = v.findViewById(R.id.seekbarHorizontalThr);
        mSeekbarVerticalThr = v.findViewById(R.id.seekbarVerticalThr);
        mChkNoiseReduction = v.findViewById(R.id.chkNoisReduction);
        mChkSetResultAsSelectedArtefact = v.findViewById(R.id.chkSetSelectedArtefact);
        mBtnResetToDefaults = v.findViewById(R.id.txtResetToDefault);
    }

    private void initViews() {
        mSeekbarHorizontalThr.setSelectedMinValue(getArguments().getInt(ARGS_HOR_THR));
        mSeekbarVerticalThr.setSelectedMinValue(getArguments().getInt(ARGS_VER_THR));
        mChkNoiseReduction.setChecked(getArguments().getBoolean(ARGS_REDUCE_NOISE));
        mChkSetResultAsSelectedArtefact.setChecked(getArguments().getBoolean(ARGS_SET_RESULT_AS_SELECTED_ARTEFACT));
        mBtnResetToDefaults.setOnClickListener(v -> {
            mSeekbarHorizontalThr.setSelectedMinValue(CannyControlsDefaultVal.DEF_THR1);
            mSeekbarVerticalThr.setSelectedMinValue(CannyControlsDefaultVal.DEF_THR2);
            mChkNoiseReduction.setChecked(true);
            mChkSetResultAsSelectedArtefact.setChecked(false);
        });
    }

    @Override
    public String getMessageTitle() {
        return getResources().getString(R.string.canny_controls);
    }

    @Override
    public String getMessageText() {
        return "";
    }

    @Override
    public String getPositiveButtonText() {
        return getResources().getString(R.string.save);
    }

    @Override
    public String getNegitiveButtonText() {
        return getResources().getString(R.string.discard);
    }

    @Override
    public void onPositiveButtonClick(View v) {
        ((FilterFragment) getTargetFragment()).setCannyControlsCallback(this);
        dismiss();

    }

    @Override
    public void onNegetiveButtonClick(View v) {
        dismiss();
    }


    @Override
    public int getHorizontalThr() {
        return mSeekbarHorizontalThr.getSelectedMinValue().intValue();
    }

    @Override
    public int getVerticalThr() {
        return mSeekbarVerticalThr.getSelectedMinValue().intValue();
    }

    @Override
    public boolean getNoiseReduction() {
        return mChkNoiseReduction.isChecked();
    }

    @Override
    public boolean getResultAsSelectedArtefact() {
        return mChkSetResultAsSelectedArtefact.isChecked();
    }


}
