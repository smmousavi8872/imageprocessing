package com.developer.smmmousavi.imageprocessing.base;

import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.repository.ImageModelRepo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private ImageModelRepo mImageModelRepo;

    public BaseViewModel() {
        mImageModelRepo = ImageModelRepo.getInstance();
    }

    public void insertImageModelList(List<ImageModel> imageModels) {
        mImageModelRepo.insertImageModelList(imageModels);
    }

    public void insertImageModel(ImageModel imageModel) {
        mImageModelRepo.insertImageModel(imageModel);
    }

    public void deleteIamgeModel(ImageModel imageModel) {
        mImageModelRepo.deleteImageModel(imageModel);
    }

    public void updateImageModel(ImageModel imageModel) {
        mImageModelRepo.updateImageModel(imageModel);
    }

    public void deleteAllImageModel() {
        mImageModelRepo.deleteAllImageModels();
    }

    public LiveData<List<ImageModel>> getAllImageModels() {
        return mImageModelRepo.getAllImageModels();
    }
}
