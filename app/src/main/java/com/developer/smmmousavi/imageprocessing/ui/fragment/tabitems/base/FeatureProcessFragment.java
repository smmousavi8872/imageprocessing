package com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.base;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.helper.PicassoHelper;
import com.developer.smmmousavi.imageprocessing.model.ArtefactResultModel;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.processtools.ImageProcessing;
import com.developer.smmmousavi.imageprocessing.repository.ImageModelRepo;
import com.developer.smmmousavi.imageprocessing.ui.fragment.base.BaseDaggerFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.main.MainDrawerFragment;
import com.developer.smmmousavi.imageprocessing.util.Animations;
import com.developer.smmmousavi.imageprocessing.util.CircleTransform;
import com.developer.smmmousavi.imageprocessing.util.ImageUtils;
import com.developer.smmmousavi.imageprocessing.util.StringsUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class FeatureProcessFragment extends BaseDaggerFragment {

    public static final String TAG = "com.developer.smmmousavi.imageprocessing.ui.fragment.filter.FeatureProcessFragment";
    public static final int TARGET_FRAGMENT_PREPROCESS_REQ_CODE = 0;
    public static final int TARGET_FRAGMENT_CANNY_REQ_CODE = 1;
    public static final int TARGET_FRAGMENT_SOBEL_REQ_CODE = 2;
    public static final int TARGET_FRAGMENT_PREWITT_REQ_CODE = 3;
    public static final int TARGET_FRAGMENT_GAUSSIAN_REQ_CODE = 4;

    public enum ProcessMode {
        NONE,
        PREPROCESS,
        FILTER,
        MORPHLOGY
    }

    @BindView(R.id.flFilterFragmentParentView)
    protected FrameLayout mFlParentView;
    @BindView(R.id.spnSelectFilter)
    protected AppCompatSpinner mSpnFilterSelect;
    @BindView(R.id.chipsSelectedImages)
    protected ChipGroup mChipGroup;
    @BindView(R.id.btnApplyFilter)
    protected MaterialButton mBtnApplyProcess;
    @BindView(R.id.crFiltersUserPanel)
    protected MaterialCardView mCrFilterUserPanel;
    @BindView(R.id.imgOriginalImage)
    protected AppCompatImageView mImgOriginalImage;
    @BindView(R.id.imgResultArtefact)
    protected AppCompatImageView mImgResultArtefact;
    @BindView(R.id.llResultLayout)
    protected LinearLayout mResultLayout;
    @BindView(R.id.imgControls)
    protected AppCompatImageView mImgFilterControls;
    @BindView(R.id.imgEmptyPlaceholder)
    protected AppCompatImageView mImgEmpytPlaceholder;


    /*@BindView(R.id.rvArtefactResult)
    RecyclerView mRvArtfactResult;*/

    protected List<String> mSpinnerList = new ArrayList<>();
    protected boolean mSetAsSelectedArtefact = false;
    private FeatureProcessFragmentViewModel mViewModel;
    private List<ImageModel> mImageModels;

    private boolean mFirstScreenTab;
    private boolean mAccessoriesPanelVisible;
    private List<ArtefactResultModel> artefactResultModels;
    MainDrawerFragment mParentFragment;
    //private ArtefactResultAdapter mArtefactResultAdapter;

    @Inject
    public ImageModelRepo mRepo;
    @Inject
    public ImageProcessing mImageProcessing;

    //abstract methods
    protected abstract void setSpinnerAdapter();

    protected abstract void initSpinnerList();

    protected abstract List<Bitmap> getProcessResult(List<ImageModel> imageModels);

    public void setImageModels(List<ImageModel> imageModels) {
        mImageModels = imageModels;
    }

    public FeatureProcessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feature_process, container, false);
        ButterKnife.bind(this, v);

        initVariabels();

        initSpinnerList();

        setChips();

        setSpinnerAdapter();

        return v;
    }

    private void initVariabels() {
        mViewModel = ViewModelProviders.of(this).get(FeatureProcessFragmentViewModel.class);
        mRepo = ImageModelRepo.getInstance();
        mParentFragment = (MainDrawerFragment) getFragmentManager().findFragmentByTag(MainDrawerFragment.TAG);
    }

    private void setChips() {
        mViewModel.getAllImageModels().observe(this, imageModels -> {
            mChipGroup.setSingleSelection(false);
            mChipGroup.removeAllViews();
            for (ImageModel imageModel : imageModels) {
                final Chip chip = new Chip(getActivity(), null, R.style.Widget_MaterialComponents_Chip_Entry);
                chip.setText(getShortName(imageModel.getName()));
                chip.setCloseIconVisible(true);
                mChipGroup.addView(chip);
                chip.setOnCloseIconClickListener(view -> {
                    mChipGroup.removeView(chip);
                    mViewModel.deleteIamgeModel(imageModel);
                });
                Target target = getTargetOfPicasso(chip);
                File imageFile = new File(imageModel.getPath());
                Picasso.with(getActivity())
                    .load(imageFile)
                    .error(R.drawable.icon_no_image)
                    .placeholder(R.drawable.icon_place_holder)
                    .transform(new CircleTransform()).resize(30, 30)
                    .into(target);

                chip.setTag(target);
                // setting target as tag is in-case the images is not loaded the target get garbage collected
                // setTaging the target prevent it from garbage collecting
                // If you want to remove some chips threw another ways like button click etc
            }
            mImageModels = imageModels;
        });
    }

    private String getShortName(String name) {
        if (name.length() > 10)
            return StringsUtil.getShortString(name);
        else
            return name;
    }

    private Target getTargetOfPicasso(final Chip targetChip) {
        return new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                targetChip.setChipIcon(d);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                targetChip.setChipIcon(errorDrawable);

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                targetChip.setChipIcon(placeHolderDrawable);
            }
        };
    }

    private void setArtefactResult(List<ArtefactResultModel> artefactResultModels) {
        PicassoHelper.loadImage(artefactResultModels.get(artefactResultModels.size() - 1).getOriginalImagePath(), mImgOriginalImage);
        PicassoHelper.loadImage(artefactResultModels.get(artefactResultModels.size() - 1).getResultArtefactPath(), mImgResultArtefact);
        //mImgOriginalImage.setImageBitmap(ImageUtils.getBitmap(artefactResultModels.get(0).getOriginalImagePath()));
        //mImgResultArtefact.setImageBitmap(ImageUtils.getBitmap(artefactResultModels.get(0).getResultArtefactPath()));

    }

    private void setViewGroupEnability(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                setViewGroupEnability((ViewGroup) view, enabled);
            }
        }
    }

    protected List<Bitmap> applyProcess(List<ImageModel> imageModels) throws IllegalArgumentException {

        List<Bitmap> artfactResults = null;
        if (imageModels != null)
            artfactResults = getProcessResult(imageModels);
        else {
            Snackbar.make(getView(), R.string.selecte_image_first, Snackbar.LENGTH_SHORT).show();
            throw new IllegalArgumentException();
        }
        return artfactResults;
    }


    private void showUserPanelAccessories(boolean isEnable) {
        if (!isEnable) {
            Animations.setAnimation(R.anim.slide_up, mCrFilterUserPanel);
            setViewGroupEnability(mCrFilterUserPanel, false);
            mParentFragment.setFabVisibility(false);
            mAccessoriesPanelVisible = false;
        } else {
            Animations.setAnimation(R.anim.slide_down, mCrFilterUserPanel);
            setViewGroupEnability(mCrFilterUserPanel, true);
            mParentFragment.setFabVisibility(true);
            mAccessoriesPanelVisible = true;
        }

    }

    private void setDoubleScreenTabListener() {
        mFirstScreenTab = true;
        new Handler().postDelayed(() -> {
            mFirstScreenTab = false;
        }, 200);
    }

    public void setApplyButtonEnabled(boolean enable) {
        mBtnApplyProcess.setEnabled(enable);
    }

    @NotNull
    private List<ImageModel> getImageModelListFromArtefact(List<ArtefactResultModel> artefactResultModels) {
        List<ImageModel> newSelectedList = new ArrayList<>();
        for (ArtefactResultModel resultModel : artefactResultModels)
            newSelectedList.add(new ImageModel(resultModel));
        return newSelectedList;
    }


    @OnClick(R.id.llResultLayout)
    void setOnParentViewListener() {
        if (mFirstScreenTab)
            if (mAccessoriesPanelVisible)
                showUserPanelAccessories(false);
            else
                showUserPanelAccessories(true);
        else
            setDoubleScreenTabListener();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.btnApplyFilter)
    void setApplyFilterListener() {
        if (mImageModels.size() > 0) {
            artefactResultModels = new ArrayList<>();
            mParentFragment.setProcessingShadeVisibility(true);
            Single.fromCallable(() -> applyProcess(mImageModels))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                })
                .subscribe((bitmaps) -> {
                    for (int i = 0; i < mImageModels.size(); i++) {
                        String resultPath = ImageUtils.storeImage(bitmaps.get(i));
                        artefactResultModels.add(new ArtefactResultModel(mImageModels.get(i).getPath(), resultPath));
                    }
                    //setArtefactResultRvAdapter(artefactResultModels);
                    new Handler().postDelayed(() -> {
                        if (mSetAsSelectedArtefact) {
                            updaetArtefactList(artefactResultModels);
                        }
                        setArtefactResult(artefactResultModels);
                        mParentFragment.setProcessingShadeVisibility(false);
                        mResultLayout.setVisibility(View.VISIBLE);
                        mImgEmpytPlaceholder.setVisibility(View.GONE);
                    }, 1000);
                    new Handler().postDelayed(() -> {
                        showUserPanelAccessories(false);
                        showSaveSnackbar(getView(), getResources().getString(R.string.save_sa_selected_artefact));

                    }, 1300);
                });
        } else
            Snackbar.make(getView(), R.string.selecte_image_first, Snackbar.LENGTH_SHORT).show();
    }

    private void updaetArtefactList(List<ArtefactResultModel> artefactResultModels) {
        List<ImageModel> newSelectedList = getImageModelListFromArtefact(artefactResultModels);
        mViewModel.deleteAllImageModel();
        mViewModel.insertImageModelList(newSelectedList);
    }

    public void showSaveSnackbar(View parentLayout, String message) {
        Snackbar snack = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        snack.setAction(getString(R.string.save), v -> {
            updaetArtefactList(artefactResultModels);
            snack.dismiss();
        });
        snack.show();
    }

    /*
        @OnClick(R.id.btnApplyFilter)
        void setApplyFilterListener() {
            List<ArtefactResultModel> artefactResultModels = new ArrayList<>();

            mParentFragment.setProcessingShadeVisibility(true);
            for (ImageModel imageModel : mImageModels) {
                new Handler().postDelayed(() -> {
                    applyProcess(imageModel.getPath())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(bitmap -> {
                            String resultPath = ImageUtils.storeImage(bitmap);
                            artefactResultModels.add(new ArtefactResultModel(imageModel.getPath(), resultPath));
                            //setArtefactResultRvAdapter(artefactResultModels);
                            setArtefactResult(artefactResultModels);
                            mParentFragment.setProcessingShadeVisibility(false);
                            mResultLayout.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(() -> {
                                showUserPanelAccessories(false);

                            }, 300);
                        })
                        .subscribe();
                }, 1000);
            }

        }
    */


}
