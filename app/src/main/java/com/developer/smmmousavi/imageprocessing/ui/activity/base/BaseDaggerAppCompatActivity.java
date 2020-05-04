package com.developer.smmmousavi.imageprocessing.ui.activity.base;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import dagger.android.support.DaggerAppCompatActivity;

public class BaseDaggerAppCompatActivity extends DaggerAppCompatActivity {
    protected FragmentManager mFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFm = getSupportFragmentManager();
    }
}
