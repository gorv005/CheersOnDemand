package com.cheersondemand.view;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.view.fragments.FragmentCategoryList;
import com.cheersondemand.view.fragments.FragmentChangePassword;
import com.cheersondemand.view.fragments.FragmentCoupons;
import com.cheersondemand.view.fragments.FragmentNotification;
import com.cheersondemand.view.fragments.FragmentProductDescription;
import com.cheersondemand.view.fragments.FragmentProductsListing;
import com.cheersondemand.view.fragments.FragmentProfile;
import com.cheersondemand.view.fragments.FragmentStoreSelection;
import com.cheersondemand.view.fragments.FragmentUpdateProfile;

import java.util.List;

import butterknife.ButterKnife;

public class ActivityContainer extends AppCompatActivity {
  public static TextView tvTitle,tvClearAll;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvClearAll=(TextView)findViewById(R.id.tvClearAll);

        final Drawable upArrow = getResources().getDrawable(R.drawable.left_arrow_1);
        upArrow.setColorFilter(getResources().getColor(R.color.profile_text_color), PorterDuff.Mode.SRC_ATOP);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        Bundle bundle = getIntent().getBundleExtra(C.BUNDLE);
        int fragmentAction = getIntent().getIntExtra(C.FRAGMENT_ACTION, 100);
        fragmnetLoader(fragmentAction, bundle);
    }


    public void fragmnetLoader(int fragmentType, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        tvClearAll.setVisibility(View.GONE);
        switch (fragmentType) {
            case C.FRAGMENT_UPDATE_PROFILE:
                fragment = new FragmentUpdateProfile();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_PROFILE_HOME:
                fragment = new FragmentProfile();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_NOTIFICATIONS:
                fragment = new FragmentNotification();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_CHANGE_PASSWORD:
                fragment = new FragmentChangePassword();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;

            case C.FRAGMENT_STORE_LIST:
                getSupportActionBar().hide();
                fragment = new FragmentStoreSelection();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_PRODUCT_DESC:
                fragment = new FragmentProductDescription();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_COUPON_LIST:
                fragment = new FragmentCoupons();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_CATEGORIES:
                fragment = new FragmentCategoryList();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_PRODUCT_LISTING:
                fragment = new FragmentProductsListing();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCT_LISTING);
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





    public void addToCart(int secPos,int pos,boolean isAdd){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentProductDescription){
            ((FragmentProductDescription)fragment).addToCart(secPos,pos,isAdd);
    
        }
        if(fragment!=null && fragment instanceof FragmentProductsListing){
            ((FragmentProductsListing)fragment).addToCart(secPos,pos,isAdd);

        }
    }
    public void updateCart(int secPos,int pos,boolean isAdd){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentProductDescription){
            ((FragmentProductDescription)fragment).updateCart(secPos,pos,isAdd);

        }
        else if(fragment!=null && fragment instanceof FragmentProductsListing){
            ((FragmentProductsListing)fragment).updateCart(secPos,pos,isAdd);

        }
    }

 public void applyCoupon(String couponName){

     Fragment fragment=getVisibleFragment();
     if(fragment!=null && fragment instanceof FragmentCoupons){
         ((FragmentCoupons)fragment).applyCoupon(couponName);

     }
 }
    public void wishListUpdate(int secPos,int pos,boolean isAdd){
        Fragment fragment=getVisibleFragment();
         if(fragment!=null && fragment instanceof FragmentProductDescription){
            ((FragmentProductDescription)fragment).wishListUpdate(secPos,pos,isAdd);

        }
        else if(fragment!=null && fragment instanceof FragmentProductsListing){
            ((FragmentProductsListing)fragment).wishListUpdate(secPos,pos,isAdd);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
