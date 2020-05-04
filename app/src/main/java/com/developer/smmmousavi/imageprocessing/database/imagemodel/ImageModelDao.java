package com.developer.smmmousavi.imageprocessing.database.imagemodel;

import com.developer.smmmousavi.imageprocessing.model.ImageModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Observable;

@Dao
public interface ImageModelDao {
    @Insert
    void insert(ImageModel imageModel);

    @Update
    void update(ImageModel imageModel);

    @Delete
    void delete(ImageModel imageModel);

    @Query("DELETE FROM image_model_table")
    void deleteAllImageModel();

    @Query("SELECT * FROM image_model_table ORDER BY id DESC")
    Observable<List<ImageModel>> getAllNotesWithRx();

    @Query("SELECT * FROM image_model_table ORDER BY id DESC")
    LiveData<List<ImageModel>> getAllImageModelsLD();

    @Query("SELECT * FROM image_model_table WHERE path = :path")
    LiveData<ImageModel> getImageModelByPath(String path);
}
