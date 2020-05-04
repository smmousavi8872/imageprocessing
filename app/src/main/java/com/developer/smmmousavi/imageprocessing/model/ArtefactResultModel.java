package com.developer.smmmousavi.imageprocessing.model;

public class ArtefactResultModel {

    private int mId;

    private String mOriginalImagePath;

    private String mResultArtefactPath;

    public String getOriginalImagePath() {
        return mOriginalImagePath;
    }

    public ArtefactResultModel(String originalImagePath, String resultImagePath) {
        mOriginalImagePath = originalImagePath;
        mResultArtefactPath = resultImagePath;
    }

    public void setOriginalImagePath(String originalImagePath) {
        mOriginalImagePath = originalImagePath;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getResultArtefactPath() {
        return mResultArtefactPath;
    }

    public void setResultArtefactPath(String resultArtefactPath) {
        mResultArtefactPath = resultArtefactPath;
    }
}
