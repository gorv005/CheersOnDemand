package com.cheersondemand.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.Brand;
import com.cheersondemand.model.Categories;
import com.cheersondemand.util.C;
import com.cheersondemand.util.listeners.OnFilterNameSelectionChangeListener;
import com.cheersondemand.view.fragments.FragmentBrandsFilter;
import com.cheersondemand.view.fragments.FragmentCategoryFilter;
import com.cheersondemand.view.fragments.FragmentPriceRangeFilter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFilters extends AppCompatActivity implements View.OnClickListener, OnFilterNameSelectionChangeListener {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Fragment fragment;
    public List<Brand> brandList;
    public List<Categories> categoriesList;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnApplyFilter)
    Button btnApplyFilter;
    Bundle bundleg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        ButterKnife.bind(this);
        bundleg = getIntent().getBundleExtra(C.BUNDLE);
        //int fragmentAction = getIntent().getIntExtra(C.FRAGMENT_ACTION, 100);
        brandList = (List<Brand>) bundleg.getSerializable(C.BRANDS_LIST);
        for(int i=0;i<brandList.size();i++){
            brandList.get(i).setPos(i);
        }

        categoriesList = (List<Categories>) bundleg.getSerializable(C.CATEGORY_LIST);
        for(int i=0;i<categoriesList.size();i++){
            categoriesList.get(i).setPos(i);
        }
        btnApplyFilter.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        fragmnetLoader(C.TAG_FRAGMENT_PRICE_RANGE, bundleg);
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

    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }


    void getCategoryList() {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentCategoryFilter) {
            categoriesList = ((FragmentCategoryFilter) fragment).getCategoriesList();

        }
    }

    public void getBrandsList() {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentBrandsFilter) {
            brandList = ((FragmentBrandsFilter) fragment).getBrandList();

        }
    }

    @Override
    public void OnSelectionChanged(String versionNameIndex, Bundle bundle) {
        getCategoryList();
        getBrandsList();
        fragmnetLoader(versionNameIndex, bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnApplyFilter:
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable(C.BRANDS_LIST,(Serializable)brandList);
                bundle.putSerializable(C.CATEGORY_LIST,(Serializable)categoriesList);
                intent.putExtra(C.BUNDLE,bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
                case R.id.btnCancel:
                    /*Intent intent1 = new Intent();
                    Bundle bundle1=new Bundle();
                    bundle1.putSerializable(C.BRANDS_LIST,(Serializable)(List<Brand>) bundleg.getSerializable(C.BRANDS_LIST));
                    bundle1.putSerializable(C.CATEGORY_LIST,(Serializable)(List<Categories>) bundleg.getSerializable(C.CATEGORY_LIST));
                    intent1.putExtra(C.BUNDLE,bundle1);
                    setResult(RESULT_OK, intent1);*/
                    finish();
                    break;
        }
    }
}
