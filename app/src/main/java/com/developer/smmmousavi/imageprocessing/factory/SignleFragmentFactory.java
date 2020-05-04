package com.developer.smmmousavi.imageprocessing.factory;

import androidx.fragment.app.Fragment;

public interface SignleFragmentFactory {

   /*
    * Factory Method Design Pattern
    * Functionality: Factory methods which return products
    */

   Fragment createFragment();

   String getTag();
}
