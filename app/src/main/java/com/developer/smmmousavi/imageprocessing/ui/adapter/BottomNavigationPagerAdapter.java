package com.developer.smmmousavi.imageprocessing.ui.adapter;

import com.developer.smmmousavi.imageprocessing.ui.fragment.base.BaseDaggerFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BottomNavigationPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseDaggerFragment> mItemList;

    public BottomNavigationPagerAdapter(@NonNull FragmentManager fm, int behavior,
                                        List<BaseDaggerFragment> itemList) {
        super(fm, behavior);
        mItemList = itemList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }
}
