package com.cheersondemand.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.util.listeners.OnFilterNameSelectionChangeListener;
import com.cheersondemand.view.fragments.FragmentBrandsFilter;
import com.cheersondemand.view.fragments.FragmentCategoryFilter;
import com.cheersondemand.view.fragments.FragmentPriceRangeFilter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFilters extends AppCompatActivity implements OnFilterNameSelectionChangeListener{

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        ButterKnife.bind(this);
    }






    public void fragmnetLoader(String fragmentType, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragmentType) {
            case C.TAG_FRAGMENT_PRICE_RANGE:
                fragment = new FragmentPriceRangeFilter();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.TAG_FRAGMENT_CATEGORY:
                fragment = new FragmentCategoryFilter();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.TAG_FRAGMENT_BRAND:
                fragment = new FragmentBrandsFilter();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;

        }
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();


    }

    @Override
    public void OnSelectionChanged(String versionNameIndex, Bundle bundle) {
        fragmnetLoader(versionNameIndex,bundle);
    }
}
