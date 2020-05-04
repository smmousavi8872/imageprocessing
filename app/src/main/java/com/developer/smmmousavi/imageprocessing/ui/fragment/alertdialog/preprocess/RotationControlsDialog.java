package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.callback.RotationCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.preprocess.PreprocessFragment;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class RotationControlsDialog extends BaseAlertDialogFragment implements RotationCallback {

    public static final String TAG = "RotationControlsDialogFragmentTag";

    public static final String ARGS_ROTATION_DEGREE = "ArgsRotationDegree";

    private RangeSeekBar mSeekbarRotationDegree;

    public static RotationControlsDialog newInstance(int rotationDegree) {

        Bundle args = new Bundle();
        args.putInt(ARGS_ROTATION_DEGREE, rotationDegree);

        RotationControlsDialog fragment = new RotationControlsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void findViews(View v) {
        mSeekbarRotationDegree = v.findViewById(R.id.seekbarRotationDegree);
    }

    public void initView() {
        mSeekbarRotationDegree.setSelectedMinValue(getArguments().getInt(ARGS_ROTATION_DEGREE));
    }

    private boolean controlsChanged() {
        return mSeekbarRotationDegree.getSelectedMinValue().intValue() > 0;
    }

    @Override
    public View getDialogContentView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_rotation_controls, null, false);
        findViews(v);
        initView();
        return v;
    }

    @Override
    public String getMessageTitle() {
        return getResources().getString(R.string.rotation_controls);
    }

    @Override
    public String getMessageText() {
        return null;
    }

    @Override
    public String getPositiveButtonText() {
        return getResources().getString(R.string.ok);
    }

    @Override
    public String getNegitiveButtonText() {
        return getResources().getString(R.string.discard);
    }

    @Override
    public void onPositiveButtonClick(View v) {
        PreprocessFragment parent = ((PreprocessFragment) getTargetFragment());
        parent.setRotationCallback(this);
        if (controlsChanged())
            parent.setApplyButtonEnabled(true);
        dismiss();
    }

    @Override
    public void onNegetiveButtonClick(View v) {
        dismiss();
    }

    @Override
    public int getRotationDegree() {
        return mSeekbarRotationDegree.getSelectedMinValue().intValue();
    }
}
