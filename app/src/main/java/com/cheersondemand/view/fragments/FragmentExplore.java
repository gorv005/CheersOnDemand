package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.view.adapter.explore.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentExplore extends Fragment {


    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tabsLayout)
    AppBarLayout tabsLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.content_home)
    RelativeLayout contentHome;
    Unbinder unbinder;
    String tabsText[]={"Categories","Deals"};

    public FragmentExplore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabs.setupWithViewPager(viewpager);
        setupViewPager(viewpager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
       /* for(int i=0;i<tabsText.length;i++) {*/
            adapter.addFrag(new FragmentCategorySubCategory(),"");
           adapter.addFrag(new FragmentDeals(), "");
          //  adapter.addFrag(new FragmentSubjectList(), "THREE");
        //}
        viewPager.setAdapter(adapter);
    }
    private void setupTabIcons() {
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.blue_dark));
        tabs.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));

        for(int i=0;i<tabsText.length;i++){
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.tab_item, null);
            TextView tabOne = (TextView)view.findViewById(R.id.tab_title);
           // View vDevider = (View)view.findViewById(R.id.vDevider);

            tabOne.setText(tabsText[i]);
            //  tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
            if(i==0) {
                tabs.getTabAt(i).setCustomView(tabOne);
            }
            else {
                tabs.getTabAt(i).setCustomView(view);

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
