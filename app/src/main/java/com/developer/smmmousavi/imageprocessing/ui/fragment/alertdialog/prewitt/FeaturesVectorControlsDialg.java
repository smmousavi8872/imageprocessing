package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.prewitt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.base.BaseAlertDialogFragment;

import androidx.appcompat.widget.AppCompatTextView;

public class FeaturesVectorControlsDialg extends BaseAlertDialogFragment {

    public static final String ARGS_RGB_VECTOR = "ArgsRgbVector";
    public static final String ARGS_HSV_VECTOR = "ArgsHsvVector";
    public static final String ARGS_TOP_LEFT_POINT_X = "ArgsTopLeftPointX";
    public static final String ARGS_TOP_LEFT_POINT_Y = "ArgsTopLeftPointY";
    public static final String ARGS_BOTTOM_RIGHT_POINT_X = "ArgsBottomRightPointX";
    public static final String ARGS_BOTOTM_RIGHT_POINT_Y = "ArgsBottomRightPointY";
    public static final String ARGS_RECT_AREA = "ArgRectArea";

    private AppCompatTextView mTxtRgbVector;
    private AppCompatTextView mTxtHsvVector;
    private AppCompatTextView mTxtTopLeftPoint;
    private AppCompatTextView mTxtBottomRightPoint;
    private AppCompatTextView mTxtLargestRectArea;

    public static FeaturesVectorControlsDialg newInstance(double[] rgbVector, double[] hsvVector,
                                                          double topLeftPointX, double topLeftPointY,
                                                          double bottomRightPointX, double bottomRightPointY,
                                                          double rectArea) {
        Bundle args = new Bundle();
        args.putDoubleArray(ARGS_RGB_VECTOR, rgbVector);
        args.putDoubleArray(ARGS_HSV_VECTOR, hsvVector);
        args.putDouble(ARGS_TOP_LEFT_POINT_X, topLeftPointX);
        args.putDouble(ARGS_TOP_LEFT_POINT_Y, topLeftPointY);
        args.putDouble(ARGS_BOTTOM_RIGHT_POINT_X, bottomRightPointX);
        args.putDouble(ARGS_BOTOTM_RIGHT_POINT_Y, bottomRightPointY);
        args.putDouble(ARGS_RECT_AREA, rectArea);
        FeaturesVectorControlsDialg fragment = new FeaturesVectorControlsDialg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getDialogContentView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_features_vector, null, false);

        findViews(v);

        setViews();

        return v;
    }

    private void findViews(View v) {
        mTxtRgbVector = v.findViewById(R.id.txtRgbVector);
        mTxtHsvVector = v.findViewById(R.id.txtHsvVector);
        mTxtTopLeftPoint = v.findViewById(R.id.txtTopLeftPoint);
        mTxtBottomRightPoint = v.findViewById(R.id.txtBottomRightPoint);
        mTxtLargestRectArea = v.findViewById(R.id.txtLargestRectArea);
    }

    @Override
    public String getMessageTitle() {
        return getResources().getString(R.string.features_vector);
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
        return "";
    }

    @Override
    public void onPositiveButtonClick(View v) {
        dismiss();
    }

    @Override
    public void onNegetiveButtonClick(View v) {
    }

    private void setViews() {
        double[] rgbVector = getArguments().getDoubleArray(ARGS_RGB_VECTOR);
        double[] hsvVector = getArguments().getDoubleArray(ARGS_HSV_VECTOR);
        double tlX = getArguments().getDouble(ARGS_TOP_LEFT_POINT_X);
        double tlY = getArguments().getDouble(ARGS_TOP_LEFT_POINT_Y);
        double brX = getArguments().getDouble(ARGS_BOTTOM_RIGHT_POINT_X);
        double brY = getArguments().getDouble(ARGS_BOTOTM_RIGHT_POINT_Y);
        double rectArea = getArguments().getDouble(ARGS_RECT_AREA);
        String rgbVectorValue = String.format("[ %.2f, %.2f, %.2f]", rgbVector[0], rgbVector[1], rgbVector[2]);
        String hsvVectorValue = String.format("[ %.2f, %.2f, %.2f]", hsvVector[0], hsvVector[1], hsvVector[2]);
        String tlPoint = String.format("( %.2f, %.2f)", tlX, tlY);
        String brPoint = String.format("( %.2f, %.2f)", brX, brY);
        mTxtRgbVector.setText(rgbVectorValue);
        mTxtHsvVector.setText(hsvVectorValue);
        mTxtTopLeftPoint.setText(tlPoint);
        mTxtBottomRightPoint.setText(brPoint);
        mTxtLargestRectArea.setText(String.format("%.2f", rectArea));
    }
}
