package com.cheersondemand.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cheersondemand.R;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.search.ISearchViewPresenter;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.StoreProducts;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.fragments.FragmentCart;
import com.cheersondemand.view.fragments.FragmentCategoryList;
import com.cheersondemand.view.fragments.FragmentCoupons;
import com.cheersondemand.view.fragments.FragmentHome;
import com.cheersondemand.view.fragments.FragmentProductDescription;
import com.cheersondemand.view.fragments.FragmentProfile;
import com.cheersondemand.view.fragments.FragmentSearchProducts;
import com.cheersondemand.view.fragments.FragmentWishList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener, IOrderViewPresenterPresenter.IOrderView {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

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
    String currentPage;
    int action;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    @BindView(R.id.ivDot)
    ImageView ivDot;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;
    @BindView(R.id.ivFav)
    ImageView ivFav;
    @BindView(R.id.rlFav)
    RelativeLayout rlFav;
    @BindView(R.id.ivExplore)
    ImageView ivExplore;
    @BindView(R.id.rlExplore)
    RelativeLayout rlExplore;
    private Fragment fragment;
    boolean doubleBackToExitPressedOnce = false;
    ISearchViewPresenter iSearchViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(R.style.ActivityTheme);
        super.onCreate(savedInstanceState);
     /*  requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        StoreProducts.getInstance().clear();
        try {
            Bundle bundle = getIntent().getBundleExtra(C.BUNDLE);
            action = bundle.getInt(C.FRAGMENT_ACTION);
            if (action == C.FRAGMENT_PROFILE_HOME) {
                setProfile();
            } else if (action == C.FRAGMENT_CART) {
              //  setCart();
                setHome();
                gotoCart();
            } else if (action == C.FRAGMENT_WISHLIST) {
                setWishlist();
            } else if (action == C.FRAGMENT_SEARCH_PRODUCT) {
                setSearchProducts();
            } else {
                setHome();
            }
        } catch (Exception e) {
            e.printStackTrace();
            fragmnetLoader(C.FRAGMENT_PRODUCTS_HOME, null);

        }
        rlCart.setOnClickListener(this);
        rlProfile.setOnClickListener(this);
        rlHome.setOnClickListener(this);
        rlFav.setOnClickListener(this);
        rlSearch.setOnClickListener(this);

        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, this);

    }

    public void fragmnetLoader(int fragmentType, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case C.FRAGMENT_PRODUCTS_HOME:
                currentPage = getString(R.string.home);
                fragment = new FragmentHome();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_PROFILE_HOME:
                currentPage = getString(R.string.profile);

                fragment = new FragmentProfile();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_CART:
                currentPage = getString(R.string.my_cart);

                fragment = new FragmentCart();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case C.FRAGMENT_CATEGORIES:
                currentPage = "Categories";
                fragment = new FragmentCategoryList();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_CATEGORY);
                break;
            case C.FRAGMENT_WISHLIST:
                currentPage = getString(R.string.wishList);
                fragment = new FragmentWishList();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_WISHLIST);
                break;
            case C.FRAGMENT_SEARCH_PRODUCT:
                currentPage = getString(R.string.search_result);
                fragment = new FragmentSearchProducts();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_SEARCH_PRODUCTS);
                break;
            case C.FRAGMENT_COUPON_LIST:
                currentPage = "coupon";
                fragment = new FragmentCoupons();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_COUPONS);
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
                if (!currentPage.equals(getString(R.string.home))) {
                    setHome();
                }

                break;
            case R.id.rlCart:
                if (!currentPage.equals(getString(R.string.my_cart))) {

                    setCart();
                }
                break;
            case R.id.rlProfile:
                if (!currentPage.equals(getString(R.string.profile))) {

                    setProfile();
                }
                break;
            case R.id.rlFav:
                if (!currentPage.equals(getString(R.string.wishList))) {

                    setWishlist();
                }
                break;
            case R.id.rlSearch:
                if (!currentPage.equals(getString(R.string.search_result))) {

                    setSearchProducts();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //   getCartList();
    }

    public void applyCoupon(String couponName) {

        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentCoupons) {
            ((FragmentCoupons) fragment).applyCoupon(couponName);

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

    void getCartList() {
        String order_id = SharedPreference.getInstance(this).getString(C.ORDER_ID);

        if (order_id != null && !order_id.equals("0")) {
            if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
                String id = "" + SharedPreference.getInstance(this).geGuestUser(C.GUEST_USER).getId();

                iOrderViewPresenterPresenter.getCartList(id, order_id, Util.id(this), false);
            } else {
                String id = "" + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getUser().getId();
                String token = C.bearer + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                iOrderViewPresenterPresenter.getCartList(token, id, order_id, Util.id(this), false);
            }
        } else {
            ivDot.setVisibility(View.GONE);
        }
    }

    public void getCartHasItem() {
        GenRequest genRequest = new GenRequest();
        genRequest.setUuid(Util.id(this));

        if (SharedPreference.getInstance(this).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(this).geGuestUser(C.GUEST_USER).getId();

            iOrderViewPresenterPresenter.getCartHasItem(false, "", id, genRequest);
        } else {
            String id = "" + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(this).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.getCartHasItem(true, token, id, genRequest);

        }
    }

    public void removeFromCart(UpdateCartRequest updateCartRequest) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentCart) {
            ((FragmentCart) fragment).removeProduct(updateCartRequest);
        }
    }

    public void removeCoupon() {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentCart) {
            ((FragmentCart) fragment).removeCoupon();
        }
    }

    public void addToCart(int secPos, int pos, boolean isAdd) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentHome) {
            ((FragmentHome) fragment).addToCart(secPos, pos, isAdd);
        } else if (fragment != null && fragment instanceof FragmentProductDescription) {
            ((FragmentProductDescription) fragment).addToCart(secPos, pos, isAdd);

        } else if (fragment != null && fragment instanceof FragmentWishList) {
            ((FragmentWishList) fragment).addToCart(secPos, pos, isAdd);

        }
    }

    public void addToCart(int secPos, int pos, boolean isAdd, View v1, View v2) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentHome) {
            ((FragmentHome) fragment).addToCart(secPos, pos, isAdd, v1, v2);
        } else if (fragment != null && fragment instanceof FragmentProductDescription) {
            ((FragmentProductDescription) fragment).addToCart(secPos, pos, isAdd);

        } else if (fragment != null && fragment instanceof FragmentWishList) {
            ((FragmentWishList) fragment).addToCart(secPos, pos, isAdd);

        }
    }
    public void showAlert(){
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentWishList) {
            ((FragmentWishList) fragment).showAlert();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            StoreProducts.getInstance().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCart(int secPos, int pos, boolean isAdd) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentHome) {
            ((FragmentHome) fragment).updateCart(secPos, pos, isAdd);
        } else if (fragment != null && fragment instanceof FragmentCart) {
            ((FragmentCart) fragment).updateCart(secPos, pos, isAdd);
        } else if (fragment != null && fragment instanceof FragmentProductDescription) {
            ((FragmentProductDescription) fragment).updateCart(secPos, pos, isAdd);

        }
    }

    public void updateCart(int secPos, int pos, boolean isAdd, View v1, View v2) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentHome) {
            ((FragmentHome) fragment).updateCart(secPos, pos, isAdd, v1, v2);
        } else if (fragment != null && fragment instanceof FragmentCart) {
            ((FragmentCart) fragment).updateCart(secPos, pos, isAdd);
        } else if (fragment != null && fragment instanceof FragmentProductDescription) {
            ((FragmentProductDescription) fragment).updateCart(secPos, pos, isAdd);

        }
    }

    public void wishListUpdate(int secPos, int pos, boolean isAdd) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentHome) {
            ((FragmentHome) fragment).wishListUpdate(secPos, pos, isAdd);
        } else if (fragment != null && fragment instanceof FragmentCart) {
            ((FragmentCart) fragment).wishListUpdate(secPos, pos, isAdd);
        } else if (fragment != null && fragment instanceof FragmentProductDescription) {
            ((FragmentProductDescription) fragment).wishListUpdate(secPos, pos, isAdd);

        } else if (fragment != null && fragment instanceof FragmentWishList) {
            ((FragmentWishList) fragment).wishListUpdate(secPos, pos, isAdd);

        }
    }
    void gotoCart() {
        Intent intent = new Intent(this, ActivityContainer.class);
        Bundle bundle=new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_CART);
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCT_DESC);
        bundle.putBoolean(C.IS_ADD_BACK, false);
        intent.putExtra(C.BUNDLE,bundle);
        startActivity(intent);

    }
    public void setHome() {
        currentPage = getString(R.string.home);
        ivFav.setImageResource(R.drawable.heart);
        ivHome.setImageResource(R.drawable.ic_bar_home_enabled);
        ivCart.setImageResource(R.drawable.ic_bar_cart);
        ivSearch.setImageResource(R.drawable.search);
        ivProfile.setImageResource(R.drawable.ic_bar_profile);
        fragmnetLoader(C.FRAGMENT_PRODUCTS_HOME, null);
    }

    public void setCart() {
        setTheme(R.style.ActivityTheme);
        ivSearch.setImageResource(R.drawable.search);

        currentPage = getString(R.string.my_cart);
        ivFav.setImageResource(R.drawable.heart);
        ivCart.setImageResource(R.drawable.cart_enable);
        ivProfile.setImageResource(R.drawable.ic_bar_profile);
        ivHome.setImageResource(R.drawable.home_disable);
        Bundle bundle = new Bundle();
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCTS_HOME);
        fragmnetLoader(C.FRAGMENT_CART, bundle);

    }

    public void setWishlist() {
        setTheme(R.style.ActivityTheme);

        currentPage = getString(R.string.wishList);
        ivFav.setImageResource(R.drawable.heart_hover);
        ivSearch.setImageResource(R.drawable.search);

        ivCart.setImageResource(R.drawable.ic_bar_cart);
        ivProfile.setImageResource(R.drawable.ic_bar_profile);
        ivHome.setImageResource(R.drawable.home_disable);
        Bundle bundle = new Bundle();
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCTS_HOME);
        fragmnetLoader(C.FRAGMENT_WISHLIST, bundle);

    }

    public void setSearchProducts() {
        currentPage = getString(R.string.search_result);
        ivFav.setImageResource(R.drawable.heart);
        ivSearch.setImageResource(R.drawable.search_hover);
        ivCart.setImageResource(R.drawable.ic_bar_cart);
        ivProfile.setImageResource(R.drawable.ic_bar_profile);
        ivHome.setImageResource(R.drawable.home_disable);
        Bundle bundle = new Bundle();
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCTS_HOME);
        fragmnetLoader(C.FRAGMENT_SEARCH_PRODUCT, bundle);

    }

    public void setProfile() {
        currentPage = getString(R.string.profile);
        ivFav.setImageResource(R.drawable.heart);
        ivSearch.setImageResource(R.drawable.search);

        ivProfile.setImageResource(R.drawable.profile_enabled);
        ivCart.setImageResource(R.drawable.ic_bar_cart);
        ivHome.setImageResource(R.drawable.home_disable);
        fragmnetLoader(C.FRAGMENT_PROFILE_HOME, null);
    }


    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {

    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {

    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {
        if (response.getData() != null && response.getData().getOrder() != null && response.getData().getOrder().getOrderItems() != null && response.getData().getOrder().getOrderItems().size() > 0) {
            // SharedPreference.getInstance(this).setBoolean(C.CART_HAS_ITEM, response.getData().getHasCartProduct());
            //  SharedPreference.getInstance(this).setString(C.ORDER_ID, "" + response.getData().getOrderId());
            ivDot.setVisibility(View.VISIBLE);
        } else {
            ivDot.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        try {
            Util.hideKeyboard(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentCoupons) {
            setCart();
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void disableProceedButton() {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentCart) {
            ((FragmentCart) fragment).disableProceedButton();
        }
    }

    public void getProductDesc(String query, String class_name, String class_id) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentSearchProducts) {
            ((FragmentSearchProducts) fragment).getProductDesc(query, class_name, class_id);
        }
    }

    public void showMessage() {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FragmentCart) {
            ((FragmentCart) fragment).showMessage();
        }
    }

    public void setDot(boolean isCartHasProduct) {
        if (isCartHasProduct) {
            ivDot.setVisibility(View.VISIBLE);
        } else {
            ivDot.setVisibility(View.GONE);

        }
    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {

    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {

    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {

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
