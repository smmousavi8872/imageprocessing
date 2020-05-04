package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.callback.ReduceNoiseCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.preprocess.PreprocessFragment;

public class NoiseReductionDialog extends BaseAlertDialogFragment implements ReduceNoiseCallback {

    public static final String TAG = "NoiseReductionDialogTag";
    public static final String ARGS_REDUCE_NOISE = "argsReduceNoise";

    private CheckBox mChkReduceNoise;

    public static NoiseReductionDialog newInstance(boolean reduceNoise) {

        Bundle args = new Bundle();
        args.putBoolean(ARGS_REDUCE_NOISE, reduceNoise);

        NoiseReductionDialog fragment = new NoiseReductionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getDialogContentView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_reduce_noise, null, false);
        findViews(v);
        initView();
        return v;
    }

    public void findViews(View v) {
        mChkReduceNoise = v.findViewById(R.id.chkNoisReduction);
    }

    public void initView() {
        mChkReduceNoise.setChecked(getArguments().getBoolean(ARGS_REDUCE_NOISE));
    }

    @Override
    public String getMessageTitle() {
        return getResources().getString(R.string.contoure_controls);
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
        parent.setReduceNoiseCallback(this);
        parent.setApplyButtonEnabled(true);
        dismiss();
    }

    @Override
    public void onNegetiveButtonClick(View v) {
        dismiss();

    }

    @Override
    public boolean getReduceNoise() {
        return mChkReduceNoise.isChecked();
    }
}
