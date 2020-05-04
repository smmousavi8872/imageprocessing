package com.developer.smmmousavi.imageprocessing.ui.activity.drawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.activity.base.BaseDaggerAppCompatActivity;
import com.developer.smmmousavi.imageprocessing.ui.activity.drawer.callback.BackPressCallback;
import com.developer.smmmousavi.imageprocessing.ui.activity.drawer.callback.ContentFragmentInsertCallback;
import com.developer.smmmousavi.imageprocessing.ui.activity.drawer.callback.ToolbarVisibilityCallback;
import com.developer.smmmousavi.imageprocessing.ui.activity.opencv.LiveCameraActivity;
import com.developer.smmmousavi.imageprocessing.ui.adapter.NavbarSliderAdapter;
import com.developer.smmmousavi.imageprocessing.model.ImageModel;
import com.developer.smmmousavi.imageprocessing.util.Animations;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseDrawerActivity extends BaseDaggerAppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, BackPressCallback, ContentFragmentInsertCallback,
    ToolbarVisibilityCallback {

    @BindView(R.id.includeToolbar)
    AppBarLayout mToolabrLayout;
    @BindView(R.id.txtToolbarTitle)
    AppCompatTextView mToolbarTitle;
    @BindView(R.id.dlMainFragmentDrawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navbarView)
    NavigationView mNavigationView;
    @BindView(R.id.imgLiveCamera)
    AppCompatImageView mImgLiveCamera;

    private SliderView mSliderView;
    private BackPressCallback mBackPressCallback;
    private SliderViewAdapter mSliderAdapter;
    private BaseDrawerActivityViewModle mViewModel;
    private LiveData<List<ImageModel>> mLiveData;
    private boolean firstExitTab;

    public void setBackPressCallback(BackPressCallback listener) {
        mBackPressCallback = listener;
    }

    @Override
    protected void onResume() {
        super.onResume();
        uncheckNavbar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        ButterKnife.bind(this);

        initViewModel();

        setStateBarColor();

        insertContentFragment(this);

        initNavView();

        setToolbarVisibility();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(BaseDrawerActivityViewModle.class);
        mLiveData = mViewModel.getAllImageModels();
        mLiveData.observe(this, this::setImageSlider);
    }

    private void setStateBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow()
                .setNavigationBarColor(ContextCompat.getColor(this, R.color.pureWhite));
            //status bar or the time bar at the top
            getWindow()
                .setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    private void setToolbarVisibility() {
        if (!setToolbarVisiable())
            mToolabrLayout.setVisibility(View.GONE);
    }

    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
        Animations.setAnimation(R.anim.fade_in_fast, mToolbarTitle);
    }

    private void initNavView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu()
            .getItem(0)
            .setChecked(true);

        // to set listener on views of header, first should find them
        // here is how to get NavigationView header
        View navHeader = mNavigationView.getHeaderView(0);
        mSliderView = navHeader.findViewById(R.id.imageSlider);
    }

    private void setImageSlider(List<ImageModel> imageModels) {
        mSliderAdapter = new NavbarSliderAdapter(imageModels);
        mSliderView.setSliderAdapter(mSliderAdapter);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        /*
            Set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or
            FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP
        */
        mSliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        mSliderView.setCircularHandlerEnabled(true);
    }

    //TODO: onMenuItmesClicked
    private void setNavigationItemAction(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        }
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void uncheckNavbar() {
        int itemCount = mNavigationView.getMenu().size();
        for (int i = 0; i < itemCount; i++) {
            if (mNavigationView.getMenu().getItem(i).isChecked()) {
                mNavigationView.getMenu().getItem(i).setChecked(false);
            }
        }
    }

    public void insertContentFragment(ContentFragmentInsertCallback contentSet) {
        int containerId = contentSet.getFragmentId();
        Fragment fragmentObject = contentSet.getFragmentObject();
        String fragmentTag = contentSet.getFragmentTag();

        Fragment foundFragment = mFm.findFragmentById(containerId);

        if (foundFragment == null) {
            mFm.beginTransaction()
                .add(containerId, fragmentObject, fragmentTag)
                .commit();
        }
    }

    public void doubleTabeExit() {
        if (!firstExitTab)
            Toast.makeText(this, "To exit, press back another time", Toast.LENGTH_SHORT).show();
        else
            super.onBackPressed();

        firstExitTab = true;
        new Handler().postDelayed(() -> {
            firstExitTab = false;
        }, 2000);
    }

    // @OnClick Region
    @OnClick(R.id.imgToolbarNavbarButton)
    void setNavBarListener() {
        mNavigationView.getMenu()
            .getItem(0)
            .setChecked(true);
        mDrawerLayout.openDrawer(GravityCompat.START);
        setBackPressCallback(this);
    }

    @OnClick(R.id.imgLiveCamera)
    public void setOnLiveCameraListener() {
        Intent intent = LiveCameraActivity.newIntent(this);
        startActivity(intent);
    }

    // @Override Region
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        item.setChecked(true);
        setNavigationItemAction(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mBackPressCallback != null) {
            if (isDrawerOpen()) {
                mBackPressCallback.onBack();
                mBackPressCallback = null;
            } else
                doubleTabeExit();
        } else
            doubleTabeExit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav_bar, menu);
        return true;
    }

    @Override
    public void onBack() {
        closeDrawer();
    }
}

