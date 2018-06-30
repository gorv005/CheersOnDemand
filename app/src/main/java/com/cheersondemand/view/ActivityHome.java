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
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.addtocart.CartHasItemResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.fragments.FragmentCart;
import com.cheersondemand.view.fragments.FragmentHome;
import com.cheersondemand.view.fragments.FragmentProductDescription;
import com.cheersondemand.view.fragments.FragmentProfile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener,IOrderViewPresenterPresenter.ICartHasItem{
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
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
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
        iOrderViewPresenterPresenter=new OrderViewPresenterImpl(this,this);

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


  public   void getCartHasItem(){
        GenRequest genRequest=new GenRequest();
        genRequest.setUuid(Util.id(this));

        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(this).geGuestUser(C.GUEST_USER).getId();

            iOrderViewPresenterPresenter.getCartHasItem(false,"",id,genRequest);
        } else {
            String id = "" + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getUser().getId();
            String token =  C.bearer + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.getCartHasItem(true,token,id,genRequest);

        }
    }

    public void removeFromCart(UpdateCartRequest updateCartRequest){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentCart ){
            ((FragmentCart)fragment).removeProduct(updateCartRequest);
        }
    }
    public void removeCoupon(){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentCart ){
            ((FragmentCart)fragment).removeCoupon();
        }
    }
   public void addToCart(int secPos,int pos,boolean isAdd){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentHome ){
            ((FragmentHome)fragment).addToCart(secPos,pos,isAdd);
        }
        else   if(fragment!=null && fragment instanceof FragmentProductDescription){
            ((FragmentProductDescription)fragment).addToCart(secPos,pos,isAdd);

        }
    }
    public void updateCart(int secPos,int pos,boolean isAdd){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentHome ){
            ((FragmentHome)fragment).updateCart(secPos,pos,isAdd);
        }
        else if(fragment!=null && fragment instanceof FragmentCart ){
            ((FragmentCart)fragment).updateCart(secPos,pos,isAdd);
        }
        else   if(fragment!=null && fragment instanceof FragmentProductDescription){
            ((FragmentProductDescription)fragment).updateCart(secPos,pos,isAdd);

        }
    }

    public void wishListUpdate(int secPos,int pos,boolean isAdd){
        Fragment fragment=getVisibleFragment();
        if(fragment!=null && fragment instanceof FragmentHome ){
            ((FragmentHome)fragment).wishListUpdate(secPos,pos,isAdd);
        }
        else if(fragment!=null && fragment instanceof FragmentCart ){
            ((FragmentCart)fragment).wishListUpdate(secPos,pos,isAdd);
        }
        else   if(fragment!=null && fragment instanceof FragmentProductDescription){
            ((FragmentProductDescription)fragment).wishListUpdate(secPos,pos,isAdd);

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

    @Override
    public void getCartHasItemSuccess(CartHasItemResponse response) {
        if(response.getSuccess()){
            SharedPreference.getInstance(this).setBoolean(C.CART_HAS_ITEM,response.getData().getHasCartProduct());
            SharedPreference.getInstance(this).setString(C.ORDER_ID,""+response.getData().getOrderId());

        }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
