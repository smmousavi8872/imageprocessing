package com.developer.smmmousavi.imageprocessing.ui.activity.main;

import android.content.Context;
import android.content.Intent;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.activity.drawer.BaseDrawerActivity;
import com.developer.smmmousavi.imageprocessing.ui.fragment.main.MainDrawerFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainDrawerActivity extends BaseDrawerActivity {

    public static Intent newIntent(Context orgin) {
        Intent intent = new Intent(orgin, MainDrawerActivity.class);
        return intent;
    }

    @Override
    public int getFragmentId() {
        return R.id.flDrawerContentFragmentContainer;
    }

    @Override
    public Fragment getFragmentObject() {
        return MainDrawerFragment.newInstance();
    }

    @Override
    public String getFragmentTag() {
        return MainDrawerFragment.TAG;
    }

    @Override
    public boolean setToolbarVisiable() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}