package com.cheersondemand.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.util.C;
import com.cheersondemand.view.fragments.FragmentCart;
import com.cheersondemand.view.fragments.FragmentHome;
import com.cheersondemand.view.fragments.FragmentProfile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.rlHome)
    RelativeLayout rlHome;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.rlCart)
    RelativeLayout rlCart;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.rlProfile)
    RelativeLayout rlProfile;
    private Fragment fragment;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        fragmnetLoader(C.FRAGMENT_PRODUCTS_HOME, null);
        rlCart.setOnClickListener(this);
        rlProfile.setOnClickListener(this);
        rlHome.setOnClickListener(this);

    }

    public void fragmnetLoader(int fragmentType, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case C.FRAGMENT_PRODUCTS_HOME:
                fragment = new FragmentHome();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_PROFILE_HOME:
                fragment = new FragmentProfile();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_CART:
                fragment = new FragmentCart();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
        }
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlHome:
               setHome();

                break;
            case R.id.rlCart:
               setCart();

                break;
            case R.id.rlProfile:
               setProfile();

                break;
        }
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


   public void addToCart(int secPos,int pos){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentHome ){
            ((FragmentHome)fragment).addToCart(secPos,pos);
        }
    }
    public void updateCart(int secPos,int pos,boolean isAdd){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentHome ){
            ((FragmentHome)fragment).updateCart(secPos,pos,isAdd);
        }
    }
    public     void setHome(){
            ivHome.setImageResource(R.drawable.ic_bar_home_enabled);
            ivCart.setImageResource(R.drawable.ic_bar_cart);
            ivProfile.setImageResource(R.drawable.ic_bar_profile);
            fragmnetLoader(C.FRAGMENT_PRODUCTS_HOME, null);
        }

        public void setCart(){
            ivCart.setImageResource(R.drawable.cart_enable);
            ivProfile.setImageResource(R.drawable.ic_bar_profile);
            ivHome.setImageResource(R.drawable.home_disable);
            fragmnetLoader(C.FRAGMENT_CART, null);

        }
        public void setProfile(){
            ivProfile.setImageResource(R.drawable.profile_enabled);
            ivCart.setImageResource(R.drawable.ic_bar_cart);
            ivHome.setImageResource(R.drawable.home_disable);
            fragmnetLoader(C.FRAGMENT_PROFILE_HOME, null);
        }

}
