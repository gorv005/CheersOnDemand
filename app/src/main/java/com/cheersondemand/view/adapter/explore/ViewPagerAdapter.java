package com.cheersondemand.view.adapter.explore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.cheersondemand.view.fragments.FragmentCategoryList;
import com.cheersondemand.view.fragments.FragmentCategorySubCategory;
import com.cheersondemand.view.fragments.FragmentDeals;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    //private final List<String> mFragmentTitleList = new ArrayList<>();
    //private final FragmentCategorySubCategory lf1 = FragmentCategorySubCategory .newInstance();
    private final FragmentCategoryList lf1 = FragmentCategoryList .newInstance();

    private final FragmentDeals lf2 = FragmentDeals .newInstance();
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      //  return mFragmentList.get(position);
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return lf1;
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return lf2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
   //     mFragmentTitleList.add(title);
    }

    /*@Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position == 0)
            lf1.getCategories(); //Refresh what you need on this fragment
        else if (position == 1)
            lf2.getDeals();
    }*/
/*  @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }*/
}
