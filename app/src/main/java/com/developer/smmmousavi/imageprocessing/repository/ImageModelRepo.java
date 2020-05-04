package com.developer.smmmousavi.imageprocessing.repository;

import android.os.AsyncTask;

import com.developer.smmmousavi.imageprocessing.database.imagemodel.ImageModelDao;
import com.developer.smmmousavi.imageprocessing.database.imagemodel.ImageModelDatabase;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ImageModelRepo {

    private static ImageModelRepo sInstance;

    private ImageModelDao mImageModelDao;


    private ImageModelRepo() {
        mImageModelDao = ImageModelDatabase.getInstance().ImageModelDao();
    }

    public static ImageModelRepo getInstance() {
        if (sInstance == null) {
            sInstance = new ImageModelRepo();
        }
        return sInstance;
    }

    public void insertImageModel(ImageModel imageModel) {
        new InsertImageModelAsyncTask(mImageModelDao).execute(imageModel);
    }

    public void insertImageModelList(List<ImageModel> imageModels) {
        new InsertImageModelListAsyncTask(mImageModelDao).execute(imageModels);
    }

    public void updateImageModel(ImageModel imageModel) {
        new UpdateImageModelAsyncTask(mImageModelDao).execute(imageModel);
    }

    public void deleteImageModel(ImageModel imageModel) {
        new DeleteImageModelAsyncTask(mImageModelDao).execute(imageModel);
    }

    public void deleteAllImageModels() {
        new DeleteAllImageModelAsyncTask(mImageModelDao).execute();
    }

    public LiveData<ImageModel> getImageModelByPath(String path) {
        return mImageModelDao.getImageModelByPath(path);
    }

    public LiveData<List<ImageModel>> getAllImageModels() {
        return mImageModelDao.getAllImageModelsLD();
    }

    /**
     * AsyncTask classes region,  Room doesn't allow to do database works
     * in UI Thread. Only LiveData objects are taken care of.
     * TODO: find a better way of running all methods in one class in background
     * thread.
     * found -> Rxjava
     */
    public static class DeleteAllImageModelAsyncTask extends AsyncTask<Void, Void, Void> {
        private ImageModelDao mImageModelDao;

        public DeleteAllImageModelAsyncTask(ImageModelDao imageModelDao) {
            this.mImageModelDao = imageModelDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mImageModelDao.deleteAllImageModel();
            return null;
        }
    }

    public static class InsertImageModelAsyncTask extends AsyncTask<ImageModel, Void, Void> {
        private ImageModelDao mImageModelDao;

        public InsertImageModelAsyncTask(ImageModelDao noteDao) {
            this.mImageModelDao = noteDao;
        }

        @Override
        protected Void doInBackground(ImageModel... imageModels) {
            try {
                mImageModelDao.insert(imageModels[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class InsertImageModelListAsyncTask extends AsyncTask<List<ImageModel>, Void, Void> {
        private ImageModelDao mImageModelDao;

        public InsertImageModelListAsyncTask(ImageModelDao noteDao) {
            this.mImageModelDao = noteDao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<ImageModel>... candidateImages) {
            List<ImageModel> list = candidateImages[0];
            for (ImageModel image : list)
                mImageModelDao.insert(image);
            return null;
        }
    }

    public static class UpdateImageModelAsyncTask extends AsyncTask<ImageModel, Void, Void> {
        private ImageModelDao mImageModelDao;

        public UpdateImageModelAsyncTask(ImageModelDao noteDao) {
            this.mImageModelDao = noteDao;
        }

        @Override
        protected Void doInBackground(ImageModel... imageModels) {
            mImageModelDao.update(imageModels[0]);
            return null;
        }
    }

    public static class DeleteImageModelAsyncTask extends AsyncTask<ImageModel, Void, Void> {
        private ImageModelDao mImageModelDao;

        public DeleteImageModelAsyncTask(ImageModelDao noteDao) {
            this.mImageModelDao = noteDao;
        }

        @Override
        protected Void doInBackground(ImageModel... imageModels) {
            mImageModelDao.delete(imageModels[0]);
            return null;
        }
    }


}
