package com.developer.smmmousavi.imageprocessing.ui.fragment.alertdialog.sobel;

public interface SobelControlsCallback {

    int getKernelSize();

    int getScaleSize();

    int getDeltaSize();

    boolean noiseReduction();

    boolean setAsSelectedArtefact();

}
