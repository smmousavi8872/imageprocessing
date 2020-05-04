package com.developer.smmmousavi.imageprocessing.ui.activity.singlefragment;

import android.os.Bundle;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.activity.base.BaseDaggerAppCompatActivity;
import com.developer.smmmousavi.imageprocessing.factory.SignleFragmentFactory;

import androidx.fragment.app.Fragment;


public abstract class SingleFragmentActivity extends BaseDaggerAppCompatActivity implements SignleFragmentFactory {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        /*
         * Factory Method Design Pattern
         * Functionality: Client
         */
        insertFragment();
    }

    private void insertFragment() {
        Fragment foundFragment = mFm.findFragmentById(R.id.flSingleFragmentContainer);

        if (foundFragment == null) {
            foundFragment = createFragment();
            String tag = getTag();
            mFm.beginTransaction()
                .add(R.id.flSingleFragmentContainer, foundFragment, tag)
                .commit();
        }
    }
}
