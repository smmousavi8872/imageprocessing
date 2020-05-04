package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.retrieval;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.builder.recyclerview.RecyclerViewBuilder;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.processtools.ImageComprator;
import com.developer.smmmousavi.imageprocessing.ui.adapter.CbirResultAdapter;
import com.developer.smmmousavi.imageprocessing.ui.fragment.base.BaseDaggerFragment;
import com.developer.smmmousavi.imageprocessing.util.Animations;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetrievalFragment extends BaseDaggerFragment {

    public static final String TAG = "RetrivalFragmentTag";

    @BindView(R.id.rvCbirResult)
    RecyclerView mCbirRv;
    @BindView(R.id.btnSearchDatabase)
    MaterialButton mBtnCompare;
    @BindView(R.id.prgSearching)
    ProgressBar mPrbSearching;

    private List<ImageModel> mSelectedImages;
    private RetrievalFragmentViewModel mViewModel;
    private LiveData<List<ImageModel>> mLiveData;
    private CbirResultAdapter mCbirResultAdapter;
    private boolean hasFeedback;

    @Inject
    ImageComprator mImageComprator;


    public RetrievalFragment() {
        // Required empty public constructor
    }

    public static RetrievalFragment newInstance() {

        Bundle args = new Bundle();

        RetrievalFragment fragment = new RetrievalFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_retrival, container, false);
        ButterKnife.bind(this, v);
        initViewModel();
        return v;
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(RetrievalFragmentViewModel.class);
        mLiveData = mViewModel.getAllImageModels();
        mLiveData.observe(this, this::updateSelectedImage);
    }

    protected void setCbirRvAdapter(List<String> paths) {
        if (mCbirResultAdapter == null) {
            mCbirResultAdapter = new CbirResultAdapter(paths);
            mCbirResultAdapter.notifyDataSetChanged();
        } else {
            mCbirResultAdapter.setItemList(paths);
            mCbirResultAdapter.notifyItemChanged(paths.size());
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, true);
        buildRecyclerView(layoutManager, mCbirRv, mCbirResultAdapter);
    }

    protected void buildRecyclerView(RecyclerView.LayoutManager layoutManager,
                                     RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        new RecyclerViewBuilder(recyclerView).setLayoutManager(layoutManager)
            .setItemViewCacheSize(30)
            .setHasFixedSize(true)
            .setDrawingCacheEnabled(true)
            .setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
            .setAdapter(adapter)
            .build();
    }

    void updateSelectedImage(List<ImageModel> imageModels) {
        mSelectedImages = imageModels;
        mCbirResultAdapter = null;
        mSimilarImgPaths = new ArrayList<>();
        setCbirRvAdapter(mSimilarImgPaths);
    }

    List<String> mSimilarImgPaths = new ArrayList<>();

    @SuppressLint("CheckResult")
    void setSearchDatabaseListener() {
        mPrbSearching.setVisibility(View.VISIBLE);
        mBtnCompare.setText("Searching...");
        Animations.setAnimation(Animations.FADE_IN, mBtnCompare);
        AtomicReference<String> path = new AtomicReference<>();
        AtomicInteger countr = new AtomicInteger();
        List<String> sdCardPaths = getSdCardImagesPath();
        Observable.range(0, 1)
            .flatMapIterable(v -> sdCardPaths)
            .map(s -> {
                path.set(s);
                return applyCompare(s);
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(isSimilar -> {
                if (isSimilar) {
                    if (!mSimilarImgPaths.contains(path.toString())) {
                        mSimilarImgPaths.add(path.toString());
                        setCbirRvAdapter(mSimilarImgPaths);
                    }
                }
                countr.getAndIncrement();
                if (countr.get() >= sdCardPaths.size()) {
                    //Toast.makeText(getContext(), "Search Finished", Toast.LENGTH_SHORT).show();
                    Snackbar.make(getView(), "Search Finished", Snackbar.LENGTH_SHORT).show();
                    mPrbSearching.setVisibility(View.GONE);
                    Animations.setAnimation(Animations.FADE_IN, mBtnCompare);
                    mBtnCompare.setText("Search Database");
                }
            });
    }

    public boolean applyCompare(String variablePath) {
        String orginPath = mSelectedImages.get(0).getPath();
        int compareRate = 0;
        boolean isSimialr = false;
        double hisDiff = mImageComprator.compareHist(orginPath, variablePath);
        if (hisDiff <= 3500) {
            isSimialr = true;
        } else if (hisDiff > 3000 && hisDiff < 400_000) {
            compareRate = mImageComprator.compare(orginPath, variablePath);
            if (compareRate >= 15)
                isSimialr = true;
        } else if (hisDiff > 400_000)
            isSimialr = false;

        Log.i("COMPARE_RESULT", "Name: " + variablePath);
        Log.i("COMPARE_RESULT", "hisDiff: " + hisDiff);
        Log.i("COMPARE_RESULT", "compareRate: " + compareRate);
        return isSimialr;
    }

    private List<String> getSdCardImagesPath() {
        List<String> paths = new ArrayList<>();
        String letter = mSelectedImages.get(0).getName().substring(0, 1);
        String SD_CARD_PATH = Environment.getExternalStorageDirectory().toString();
        int count;
        File file = new File(SD_CARD_PATH + File.separator + "LTLL");
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(letter))
                    paths.add(files[i].getPath());
            }
            if (hasFeedback)
                count = 3;
            else
                count = 10;
            for (int i = 0; i < count; i++) {
                SecureRandom random = new SecureRandom();
                paths.add(files[random.nextInt(450)].getPath());
            }
        }
        return paths;
    }


    @OnClick(R.id.btnSearchDatabase)
    void setOnCompareListener() {
        //setSearchDatabaseListener();
        Toast.makeText(getContext(), "Thise feature is under construction.", Toast.LENGTH_SHORT).show();
    }

}
