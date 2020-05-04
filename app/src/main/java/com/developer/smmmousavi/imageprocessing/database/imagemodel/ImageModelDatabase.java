package com.developer.smmmousavi.imageprocessing.database.imagemodel;

import android.os.AsyncTask;

import com.developer.smmmousavi.imageprocessing.application.BaseApplication;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ImageModel.class}, version = 2)
public abstract class ImageModelDatabase extends RoomDatabase {

    private static ImageModelDatabase sInstance;

    public abstract ImageModelDao ImageModelDao();

    public static synchronized ImageModelDatabase getInstance() {
        if (sInstance == null)
            sInstance = Room.databaseBuilder(BaseApplication.getContext(), ImageModelDatabase.class, "image_model_table")
                .fallbackToDestructiveMigration()
                .addCallback(sCallback)
                .build();
        return sInstance;
    }

    // for adding some data only when db is created or every time that db is opened
    // should use callback as following
    private static Callback sCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(sInstance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ImageModelDao mImageModelDao;

        public PopulateDbAsyncTask(ImageModelDatabase db) {
            mImageModelDao = db.ImageModelDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //add files which you want to exist in db by default
            return null;
        }
    }
}
