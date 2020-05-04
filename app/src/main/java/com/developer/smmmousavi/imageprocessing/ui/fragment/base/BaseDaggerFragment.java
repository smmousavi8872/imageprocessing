package com.developer.smmmousavi.imageprocessing.ui.fragment.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.fragment.shade.ProcessingShadeFragment;
import com.developer.smmmousavi.imageprocessing.util.Animations;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 */
public class BaseDaggerFragment extends DaggerFragment {


    public BaseDaggerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base_dagger, container, false);
        return v;
    }

    public void replaceFragment(@IdRes int containerId,
                                @NonNull BaseDaggerFragment fragment,
                                String tag,
                                @AnimatorRes @AnimRes int enterAnimId,
                                @AnimatorRes @AnimRes int exitAnimId) {

        getFragmentManager().beginTransaction()
            .setCustomAnimations(enterAnimId, exitAnimId)
            .replace(containerId, fragment, tag)
            .commit();
    }


    public void addFragment(@IdRes int containerId,
                            @NonNull BaseDaggerFragment fragment,
                            String tag,
                            @AnimatorRes @AnimRes int enterAnimId,
                            @AnimatorRes @AnimRes int exitAnimId) {

        getFragmentManager().beginTransaction()
            .setCustomAnimations(enterAnimId, exitAnimId)
            .add(containerId, fragment, tag)
            .commit();
    }

    public void removeFragment(@NonNull BaseDaggerFragment fragment,
                               @AnimatorRes @AnimRes int enterAnimId,
                               @AnimatorRes @AnimRes int exitAnimId) {

        getFragmentManager().beginTransaction()
            .setCustomAnimations(enterAnimId, exitAnimId)
            .remove(fragment)
            .commit();
    }


    public void setProcessingShadeVisibility(boolean isVisible) {
        if (isVisible) {
            addFragment(R.id.flDrawerContentFragmentContainer,
                ProcessingShadeFragment.newInstance(),
                ProcessingShadeFragment.TAG,
                Animations.FADE_IN_FAST,
                Animations.FADE_OUT_FAST);
        } else {
            ProcessingShadeFragment fragment = (ProcessingShadeFragment) getFragmentManager()
                .findFragmentByTag(ProcessingShadeFragment.TAG);
            if (fragment != null)
                removeFragment(fragment,
                    Animations.FADE_IN_FAST,
                    Animations.FADE_OUT_FAST);
        }
    }

}
