package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.explore.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentExplore extends Fragment implements View.OnClickListener {

    int pos;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tabsLayout)
    AppBarLayout tabsLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.content_home)
    RelativeLayout contentHome;
    Unbinder unbinder;
    String tabsText[] = {"Categories", "Deals"};
    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.llLocationName)
    LinearLayout llLocationName;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.rlbar)
    RelativeLayout rlbar;
    ViewPagerAdapter adapter;
    FragmentManager fragmentManager;
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
        llLocationName.setOnClickListener(this);
        ivCart.setOnClickListener(this);

        setupViewPager(viewpager);
        setupTabIcons();

    }

    @Override
    public void onResume() {
        super.onResume();
        setLocationName();
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_STORE_UPDATED)) {
            SharedPreference.getInstance(getActivity()).setBoolean(C.IS_STORE_UPDATED, false);

                if (adapter.getItem(1) != null) {
                    ((FragmentDeals) adapter.getItem(1)).getDeals();
                }

                if (adapter.getItem(0) != null) {
                   // ((FragmentCategorySubCategory) adapter.getItem(0)).getCategories();
                    ((FragmentCategoryList) adapter.getItem(0)).getCategories();

                }

        }
    }

    public  void addToCart(int secPos, int pos, boolean isAdd){

        if (adapter.getItem(1) != null) {
            ((FragmentDeals) adapter.getItem(1)).addToCart(secPos,pos,isAdd);
        }

    }
    public  void wishListUpdate(int secPos, int pos, boolean isAdd){

        if (adapter.getItem(1) != null) {
            ((FragmentDeals) adapter.getItem(1)).wishListUpdate(secPos,pos,isAdd);
        }

    }
    private void setupViewPager(ViewPager viewPager) {
        if(adapter==null) {
            fragmentManager=getActivity().getSupportFragmentManager();
            adapter = new ViewPagerAdapter(fragmentManager);
            /* for(int i=0;i<tabsText.length;i++) {*/
           // adapter.addFrag(new FragmentCategorySubCategory(), "");
          //  adapter.addFrag(new FragmentDeals(), "");
            //  adapter.addFrag(new FragmentSubjectList(), "THREE");
            //}
            viewPager.setAdapter(adapter);
           // tabs.setupWithViewPager(viewpager);

        }
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        try {
            fragmentManager.beginTransaction().remove(adapter.getItem(0)).commitAllowingStateLoss();
            fragmentManager.beginTransaction().remove(adapter.getItem(1)).commitAllowingStateLoss();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            fragmentManager.beginTransaction().remove(adapter.getItem(0)).commitAllowingStateLoss();
            fragmentManager.beginTransaction().remove(adapter.getItem(1)).commitAllowingStateLoss();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void setupTabIcons() {
       /* tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pos = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.blue_dark));
        tabs.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));

        for (int i = 0; i < tabsText.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_item, null);
            TextView tabOne = (TextView) view.findViewById(R.id.tab_title);
            // View vDevider = (View)view.findViewById(R.id.vDevider);

            tabOne.setText(tabsText[i]);
            //  tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
            if (i == 0) {
                tabs.getTabAt(i).setCustomView(tabOne);
            } else {
                tabs.getTabAt(i).setCustomView(view);

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLocationName:
                gotoLocationAndStoreList();
                break;
            case R.id.ivCart:
                gotoCart();
                break;
        }
    }

    void setLocationName() {
        String loc = SharedPreference.getInstance(getActivity()).getString(C.LOCATION_SELECTED);
        if (loc != null) {
            tvLocationName.setText(loc);
        }
    }

    void gotoLocationAndStoreList() {
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_STORE_LOCATION_LIST);
        bundle.putInt(C.FROM, C.FRAGMENT_PRODUCT_LISTING);
        bundle.putBoolean(C.IS_CROSS_SHOW, true);

        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);

    }

    void gotoCart() {
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_CART);
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCT_DESC);
        bundle.putBoolean(C.IS_ADD_BACK, false);
        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);

    }
}
