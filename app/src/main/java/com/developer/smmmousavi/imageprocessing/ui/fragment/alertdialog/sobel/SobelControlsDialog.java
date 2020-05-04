package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.sobel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.developer.smmmousavi.imageprocessing.constant.SobelControlsDefValue;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.filter.FilterFragment;
import com.google.android.material.button.MaterialButton;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;

public class SobelControlsDialog extends BaseAlertDialogFragment implements SobelControlsCallback {

    public static final String TAG = "SobelControlsDialogTag";

    public static final String ARGS_KERNEL_SIZE = "ArgsKernelSize";
    public static final String ARGS_SCALE_SIZE = "ArgsScaleSize";
    public static final String ARGS_DELTA_SIZE = "ArgsDeltaSize";
    public static final String ARGS_REDUCE_NOISE = "ArgsReduceNoise";
    public static final String ARGS_SET_RESULT_AS_SELECTED_ARTEFACT = "ArgsSetResultAsSelectedArtefact";

    private AppCompatSpinner mSpnSelectKernelSize;
    private RangeSeekBar mSeekBarScaleSize;
    private RangeSeekBar mSeekBarDeltaSize;
    private AppCompatCheckBox mChkReduceNoise;
    private AppCompatCheckBox mChkSetAsSelectedArtefact;
    private MaterialButton mBtnResetToDefault;

    private List<String> mSpinnerList = new ArrayList<>();
    private int mKernelSize;


    public static SobelControlsDialog newInstance(int kernelSize, int scaleSize, int deltaSize,
                                                  boolean reduceNoise, boolean setAsSelectedArtefact) {

        Bundle args = new Bundle();
        args.putInt(ARGS_KERNEL_SIZE, kernelSize);
        args.putInt(ARGS_SCALE_SIZE, scaleSize);
        args.putInt(ARGS_DELTA_SIZE, deltaSize);
        args.putBoolean(ARGS_REDUCE_NOISE, reduceNoise);
        args.putBoolean(ARGS_SET_RESULT_AS_SELECTED_ARTEFACT, setAsSelectedArtefact);

        SobelControlsDialog fragment = new SobelControlsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getDialogContentView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sobel_controls, null, false);
        findViews(v);
        initView();
        initValues();
        return v;
    }

    private void initValues() {
        initSpinnerList();
        setSpinnerAdapter();
        mSpnSelectKernelSize.setSelection(getArguments().getInt(ARGS_KERNEL_SIZE) / 2);
    }

    public void findViews(View v) {
        mSpnSelectKernelSize = v.findViewById(R.id.spnSelectKernelSize);
        mSeekBarScaleSize = v.findViewById(R.id.seekbarScaleSize);
        mSeekBarDeltaSize = v.findViewById(R.id.seekbarDeltaSize);
        mChkReduceNoise = v.findViewById(R.id.chkNoisReduction);
        mChkSetAsSelectedArtefact = v.findViewById(R.id.chkSetSelectedArtefact);
        mBtnResetToDefault = v.findViewById(R.id.btnResetToDefault);

    }

    public void initView() {
        mSeekBarScaleSize.setSelectedMinValue(getArguments().getInt(ARGS_SCALE_SIZE));
        mSeekBarDeltaSize.setSelectedMinValue(getArguments().getInt(ARGS_DELTA_SIZE));
        mChkReduceNoise.setChecked(getArguments().getBoolean(ARGS_REDUCE_NOISE));
        mChkSetAsSelectedArtefact.setChecked(getArguments().getBoolean(ARGS_SET_RESULT_AS_SELECTED_ARTEFACT));
        mBtnResetToDefault.setOnClickListener(v -> {
            mSpnSelectKernelSize.setSelection(SobelControlsDefValue.DEF_K_SIZE / 2);
            mSeekBarScaleSize.setSelectedMinValue(SobelControlsDefValue.DEF_SCALE);
            mSeekBarDeltaSize.setSelectedMinValue(SobelControlsDefValue.DEF_DELTA);
            mChkReduceNoise.setChecked(true);
            mChkSetAsSelectedArtefact.setChecked(false);
        });
    }

    @Override
    public String getMessageTitle() {
        return getResources().getString(R.string.sobel_controls);
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
        ((FilterFragment) getTargetFragment()).setSobelControlsCallback(this);
        dismiss();
    }

    private void initSpinnerList() {
        mSpinnerList.add("1");
        mSpinnerList.add("3");
        mSpinnerList.add("5");
        mSpinnerList.add("7");
        mSpinnerList.add("9");
    }


    //set spinner custome layout
    private void setSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(BaseApplication.getContext(), R.layout.item_spinner_text, mSpinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnSelectKernelSize.setAdapter(adapter);
        mSpnSelectKernelSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        mKernelSize = 1;
                        break;
                    case 1:
                        mKernelSize = 3;
                        break;
                    case 2:
                        mKernelSize = 5;
                        break;
                    case 3:
                        mKernelSize = 7;
                        break;
                    case 4:
                        mKernelSize = 9;
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onNegetiveButtonClick(View v) {
        dismiss();
    }

    @Override
    public int getKernelSize() {
        return mKernelSize;
    }

    @Override
    public int getScaleSize() {
        return mSeekBarScaleSize.getSelectedMinValue().intValue();
    }

    @Override
    public int getDeltaSize() {
        return mSeekBarDeltaSize.getSelectedMinValue().intValue();
    }

    @Override
    public boolean noiseReduction() {
        return mChkReduceNoise.isChecked();
    }

    @Override
    public boolean setAsSelectedArtefact() {
        return mChkSetAsSelectedArtefact.isChecked();
    }
}
