package com.developer.smmmousavi.imageprocessing.ui.fragment.main;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.repository.ImageModelRepo;
import com.developer.smmmousavi.imageprocessing.ui.activity.drawer.BaseDrawerActivity;
import com.developer.smmmousavi.imageprocessing.ui.adapter.BottomNavigationPagerAdapter;
import com.developer.smmmousavi.imageprocessing.ui.fragment.base.BaseDaggerFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.archive.ArchiveFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.filter.FilterFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.morphology.MorphlogyFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.preprocess.PreprocessFragment;
import com.developer.smmmousavi.imageprocessing.ui.fragment.tabitems.retrieval.RetrievalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.vincent.filepicker.Constant.REQUEST_CODE_PICK_IMAGE;
import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainDrawerFragment extends BaseDaggerFragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "androidx.fragment.app.Fragment.MainFragmentTAG";

    @BindView(R.id.rlMainFragmentParentView)
    RelativeLayout mParentLayout;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fabSelectImage)
    FloatingActionButton mFabSelectImage;
    @BindView(R.id.bottomNavigationViewPager)
    ViewPager mViewPager;


    private ImageModelRepo mImageModelRepo;
    private int lastSelectedId = R.id.menuPreprocess;
    private MainDrawerFragmentViewModel mViewModel;
    private List<Integer> mSpinnerList;
    private BottomNavigationPagerAdapter mPagerAdapter;


    public MainDrawerFragment() {
        // Required empty public constructor
    }

    public static MainDrawerFragment newInstance() {

        Bundle args = new Bundle();

        MainDrawerFragment fragment = new MainDrawerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_drawer, container, false);
        ButterKnife.bind(this, v);

        initVariables();

        getRequiredPermissions();

        initBottomNavigationView();

        initViewPagerAdapter();

        return v;
    }

    private void getRequiredPermissions() {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                }
            }).check();
    }

    public void setFabVisibility(boolean isVisible) {
        if (isVisible)
            mFabSelectImage.show();
        else
            mFabSelectImage.hide();
    }

    private void initVariables() {
        mImageModelRepo = ImageModelRepo.getInstance();
        mViewModel = ViewModelProviders.of(this).get(MainDrawerFragmentViewModel.class);
    }

    private void initViewPagerAdapter() {
        List<BaseDaggerFragment> fragmentList = new ArrayList<>();
        fragmentList.add(PreprocessFragment.newInstance());
        fragmentList.add(FilterFragment.newInstance());
        fragmentList.add(MorphlogyFragment.newInstance());
        //TODO: the last two of fragments must change to real ones
        fragmentList.add(RetrievalFragment.newInstance());
        fragmentList.add(ArchiveFragment.newInstance());

        mPagerAdapter = new BottomNavigationPagerAdapter(getFragmentManager(),
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBottomNavigationView.setSelectedItemId(R.id.menuPreprocess);
                        break;
                    case 1:
                        mBottomNavigationView.setSelectedItemId(R.id.menuFilters);
                        break;
                    case 2:
                        mBottomNavigationView.setSelectedItemId(R.id.menuMorphology);
                        break;
                    case 3:
                        mBottomNavigationView.setSelectedItemId(R.id.menuRetrival);
                        break;
                    case 4:
                        mBottomNavigationView.setSelectedItemId(R.id.menuArchive);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBottomNavigationView() {
        mBottomNavigationView.setSelectedItemId(R.id.menuPreprocess);
        mViewPager.setCurrentItem(0);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        ((BaseDrawerActivity) getActivity()).setToolbarTitle(getResources().getString(R.string.preprocess));
    }

    private void setNavigationItemAction(@NonNull MenuItem item) {
        BaseDrawerActivity parent = (BaseDrawerActivity) getActivity();
        if (item.getItemId() != lastSelectedId)
            switch (item.getItemId()) {
                case R.id.menuPreprocess:
                    mViewPager.setCurrentItem(0);
                    setFabVisibility(true);
                    parent.setToolbarTitle(getResources().getString(R.string.preprocess));
                    break;
                case R.id.menuFilters:
                    mViewPager.setCurrentItem(1);
                    setFabVisibility(true);
                    parent.setToolbarTitle(getResources().getString(R.string.filters));
                    break;
                case R.id.menuMorphology:
                    mViewPager.setCurrentItem(2);
                    setFabVisibility(true);
                    parent.setToolbarTitle(getResources().getString(R.string.morphology));
                    break;
                case R.id.menuRetrival:
                    mViewPager.setCurrentItem(3);
                    setFabVisibility(true);
                    parent.setToolbarTitle(getResources().getString(R.string.retrival));
                    break;
                case R.id.menuArchive:
                    mViewPager.setCurrentItem(4);
                    setFabVisibility(false);
                    parent.setToolbarTitle(getResources().getString(R.string.archive));
                    break;
            }
        lastSelectedId = item.getItemId();
    }


    @OnClick(R.id.fabSelectImage)
    void setFabClickListener() {
        Intent intent = new Intent(getContext(), ImagePickActivity.class);
        intent.putExtra(IS_NEED_CAMERA, true);
        intent.putExtra(Constant.MAX_NUMBER, 1);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            ArrayList<ImageFile> imageFiles = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            mImageModelRepo.deleteAllImageModels();

            for (ImageFile imageFile : imageFiles) {
                ImageFile compressedImg = getCompressedImageFile(imageFile);
                mViewModel.insertImageModel(new ImageModel(compressedImg));
            }
        }
    }

    private ImageFile getCompressedImageFile(ImageFile imageFile) {
        ImageFile compressedImg = new ImageFile();
        String compressedPath = getCompressedImagePath(imageFile.getPath());

        compressedImg.setPath(compressedPath);
        compressedImg.setOrientation(imageFile.getOrientation());
        compressedImg.setBucketId(imageFile.getBucketId());
        compressedImg.setBucketName(compressedImg.getBucketName());
        compressedImg.setDate(imageFile.getDate());
        compressedImg.setId(imageFile.getId());
        compressedImg.setName(imageFile.getName());
        compressedImg.setSelected(imageFile.isSelected());
        compressedImg.setSize(imageFile.getSize());
        return compressedImg;
    }

    private String getCompressedImagePath(String actualImgPath) {
        File imgFile = new File(actualImgPath);
        //File compressedFile = Compressor.getDefault(getContext()).compressToFile(imgFile);
        File compressedFile = new Compressor.Builder(getContext())
            .setMaxWidth(640)
            .setMaxHeight(480)
            .setQuality(75)
            .setCompressFormat(Bitmap.CompressFormat.WEBP)
            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getAbsolutePath())
            .build()
            .compressToFile(imgFile);
        return compressedFile.getAbsolutePath();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        setNavigationItemAction(menuItem);
        return true;
    }

}
