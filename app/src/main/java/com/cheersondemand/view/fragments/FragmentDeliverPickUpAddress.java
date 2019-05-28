package com.cheersondemand.view.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeliverPickUpAddress extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.indicator)
    View indicator;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.llLoginSignup)
    LinearLayout llLoginSignup;
    @BindView(R.id.ivSplash)
    ImageView ivSplash;
    @BindView(R.id.tvCheers)
    TextView tvCheers;
    @BindView(R.id.tvStarted)
    TextView tvStarted;
    @BindView(R.id.fl)
    RelativeLayout fl;
    @BindView(R.id.rl_address_view)
    RelativeLayout rlAddressView;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    private int indicatorWidth;
    TabFragmentAdapter adapter;

    public FragmentDeliverPickUpAddress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deliver_pick_up_address, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TabFragmentAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentDelivery().newInstance(0), getString(R.string.delivery));
        adapter.addFragment(new FragmentDelivery().newInstance(1), getString(R.string.pick_up));
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN)) {
            llLoginSignup.setVisibility(View.GONE);
        }
        tab.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = tab.getWidth() / tab.getTabCount();

                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) indicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                indicator.setLayoutParams(indicatorParams);
                //tab.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //To move the indicator as the user scroll, we will need the scroll offset values
            //positionOffset is a value from [0..1] which represents how far the page has been scrolled
            //see https://developer.android.com/reference/android/support/v4/view/ViewPager.OnPageChangeListener
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) indicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset = (positionOffset + i) * indicatorWidth;
                params.leftMargin = (int) translationOffset;
                indicator.setLayoutParams(params);
                //  setCustomFont(0);
                /*for(int j=0; j < tab.1getChildCount(); j++){
                    View tv = (View) tab.getChildAt(j);

                    if(tv instanceof TextView) {
                        if (i == 0) {
                            ((TextView) tv).setTextColor(Color.WHITE);
                        } else {
                            ((TextView) tv).setTextColor(Color.WHITE);
                        }
                    }
                }*/
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    indicator.setBackgroundResource(R.drawable.gradient_bg_right);
                } else {
                    indicator.setBackgroundResource(R.drawable.gradient_bg);
                }
                // setCustomFont(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                tab.setTabTextColors(Color.parseColor("#c9282f"), Color.WHITE);
                //   setCustomFont(i);
            }
        });
        tab.setTabTextColors(Color.parseColor("#c9282f"), Color.WHITE);
        setCustomFont(0);
    }


    /*    private void setupViewPager(ViewPager viewPager) {
            adapter = new Adapter(getChildFragmentManager());
            adapter.addFragment(new FragmentDelivery().newInstance(1), getString(R.string.delivery));
            adapter.addFragment(new FragmentDelivery().newInstance(2), getString(R.string.pick_up));
            viewPager.setAdapter(adapter);
        }*/
    public void setCustomFont(int pos) {

        ViewGroup vg = (ViewGroup) tab.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "poppins_medium.ttf"));
                    ((TextView) tabViewChild).setTextSize(16);
          /*      if(pos==0) {
                    if (j == 0) {
                        ((TextView) tabViewChild).setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        ((TextView) tabViewChild).setTextColor(Color.parseColor("#ffffff"));

                    }
                }
                else if(pos==1) {
                    if (j == 0) {
                        ((TextView) tabViewChild).setTextColor(Color.parseColor("#ffffff"));

                    } else {
                        ((TextView) tabViewChild).setTextColor(Color.parseColor("#ffffff"));

                    }
                }*/
                }
            }
        }
    }





    @OnClick({R.id.tvLogin, R.id.tvSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                gotoAuthniticationScreen(true);
                break;
            case R.id.tvSignUp:
                gotoAuthniticationScreen(false);

                break;
        }
    }

    class TabFragmentAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public TabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    void gotoAuthniticationScreen(boolean isLogin) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(C.IS_LOGIN_SCREEN, isLogin);
        bundle.putBoolean(C.IS_FROM_HOME, false);
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCTS_HOME);
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_AUTHNITICATION);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(C.BUNDLE, bundle);
        getActivity().startActivity(intent);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
           // getActivity().getSupportFragmentManager().beginTransaction().remove(adapter.getItem(0)).commitAllowingStateLoss();
           // getActivity().getSupportFragmentManager().beginTransaction().remove(adapter.getItem(1)).commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
