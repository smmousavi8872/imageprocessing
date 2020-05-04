package com.developer.smmmousavi.imageprocessing.model;

import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "image_model_table")
public class ImageModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long mId;
    @ColumnInfo(name = "path")
    private String mPath;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "date")
    private long mDate;
    @ColumnInfo(name = "size")
    private long mSize;
    @ColumnInfo(name = "buckedId")
    private String mBuckedId;
    @ColumnInfo(name = "buckedName")
    private String mBuckedName;

    public ImageModel(ImageFile imageFile) {
        setId(imageFile.getId());
        setPath(imageFile.getPath());
        setName(imageFile.getName());
        setDate(imageFile.getDate());
        setSize(imageFile.getSize());
    }

    public ImageModel(ArtefactResultModel artefactResultModel) {
        File file = new File(artefactResultModel.getResultArtefactPath());
        setId(artefactResultModel.getId());
        setPath(artefactResultModel.getResultArtefactPath());
        setName(file.getName());
        setSize(file.length());
    }

    public ImageModel() {

    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getBuckedId() {
        return mBuckedId;
    }

    public void setBuckedId(String buckedId) {
        mBuckedId = buckedId;
    }

    public String getBuckedName() {
        return mBuckedName;
    }

    public void setBuckedName(String buckedName) {
        mBuckedName = buckedName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public long getSize() {
        return mSize;
    }

    public void setSize(long size) {
        mSize = size;
    }
}
