package com.developer.smmmousavi.imageprocessing.ui.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.developer.smmmousavi.imageprocessing.ui.activity.main.MainDrawerActivity;
import com.developer.smmmousavi.imageprocessing.ui.activity.singlefragment.SingleFragmentActivity;
import com.developer.smmmousavi.imageprocessing.ui.fragment.splash.SplashFragment;

import androidx.fragment.app.Fragment;

public class SplashActivity extends SingleFragmentActivity {

    public static final int SPLASH_DELAY = 3000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            Intent intent = MainDrawerActivity.newIntent(this);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }

    @Override
    public Fragment createFragment() {
        return SplashFragment.newInstance();
    }

    @Override
    public String getTag() {
        return SplashFragment.TAG;
    }

    @Override
    public void onBackPressed() {
        //preven user from exit meanwhile loading running activity
    }
}
