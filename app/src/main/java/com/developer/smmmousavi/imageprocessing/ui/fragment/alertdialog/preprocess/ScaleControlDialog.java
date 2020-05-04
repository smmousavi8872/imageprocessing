package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.preprocess.callback.ScaleCallback;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.preprocess.PreprocessFragment;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class ScaleControlDialog extends BaseAlertDialogFragment implements ScaleCallback {

    public static final String ARGS_HORIZONTAL_SCALE_SIZE = "ArgHorizontalScaleSize";
    public static final String ARGS_VERTICAL_SCALE_SIZE = "ArgVerticalScaleSize";
    public static final String TAG = "ScaleControlDialogTag";

    private RangeSeekBar mSeekbarHorizontalScaleSize;
    private RangeSeekBar mSeekbarVerticalScaleSize;

    public static ScaleControlDialog newInstance(int horizontalScaleSize, int verticalScaleSize) {

        Bundle args = new Bundle();
        args.putInt(ARGS_HORIZONTAL_SCALE_SIZE, horizontalScaleSize);
        args.putInt(ARGS_VERTICAL_SCALE_SIZE, verticalScaleSize);

        ScaleControlDialog fragment = new ScaleControlDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void findViews(View v) {
        mSeekbarHorizontalScaleSize = v.findViewById(R.id.seekbarHorizontalScaleSize);
        mSeekbarVerticalScaleSize = v.findViewById(R.id.seekbarVerticalScaleSize);
    }

    public void initView() {
        mSeekbarHorizontalScaleSize.setSelectedMinValue(getArguments().getInt(ARGS_HORIZONTAL_SCALE_SIZE));
        mSeekbarVerticalScaleSize.setSelectedMinValue(getArguments().getInt(ARGS_VERTICAL_SCALE_SIZE));
    }

    private boolean controlsChanged() {
        return mSeekbarHorizontalScaleSize.getSelectedMinValue().intValue() > 0 ||
            mSeekbarVerticalScaleSize.getSelectedMinValue().intValue() > 0;
    }

    @Override
    public View getDialogContentView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_scale_controls, null, false);
        findViews(v);
        initView();
        return v;
    }

    @Override
    public String getMessageTitle() {
        return getResources().getString(R.string.dialog_title_scale_controls);
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
        parent.setScaleCallback(this);
        if (controlsChanged())
            parent.setApplyButtonEnabled(true);
        dismiss();

    }

    @Override
    public void onNegetiveButtonClick(View v) {
        dismiss();

    }

    @Override
    public int getHorizontalScaleSize() {
        return mSeekbarHorizontalScaleSize.getSelectedMinValue().intValue();
    }

    @Override
    public int getVeritcalScaleSize() {
        return mSeekbarVerticalScaleSize.getSelectedMinValue().intValue();
    }
}
