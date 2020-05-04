package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.guassiandiff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;

public class GaussianDiffControlsDialg extends BaseAlertDialogFragment {

    public static GaussianDiffControlsDialg newInstance() {

        Bundle args = new Bundle();

        GaussianDiffControlsDialg fragment = new GaussianDiffControlsDialg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getDialogContentView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_gaussian_diff_controls, null, false);
    }

    @Override
    public String getMessageTitle() {
        return getResources().getString(R.string.guassian_difference_dialog);
    }

    @Override
    public String getMessageText() {
        return null;
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
        dismiss();

    }

    @Override
    public void onNegetiveButtonClick(View v) {
        dismiss();
    }
}
