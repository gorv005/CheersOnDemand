package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.deals.Deals;
import com.cheersondemand.model.deals.DealsResponse;
import com.cheersondemand.model.explore.SpinnerCategoryAdapter;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.StoreProducts;
import com.cheersondemand.util.Util;
import com.cheersondemand.util.itemdecoration.GridSpacingItemDecoration;
import com.cheersondemand.view.adapter.explore.AdapterDeals;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeals extends Fragment implements IHomeViewPresenterPresenter.IHomeView, IOrderViewPresenterPresenter.IOrderView {


    @BindView(R.id.rvDeals)
    RecyclerView rvDeals;
    @BindView(R.id.llView)
    LinearLayout llView;
    Unbinder unbinder;
    Util util;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    AdapterDeals adapterDeals;
    @BindView(R.id.tvCategoryName)
    TextView tvCategoryName;
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    SpinnerCategoryAdapter spinnerCategoryAdapter;
    List<Categories> categoriesList;
    Deals deals;
    int currentPos = -1;
    @BindView(R.id.llCategory)
    LinearLayout llCategory;
    @BindView(R.id.tvNoProduct)
    TextView tvNoProduct;
    private int productPos;
    private int secPos;
    private boolean isAdd;
    AllProduct product;
    List<AllProduct> allProductList;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;

    public FragmentDeals() {
        // Required empty public constructor
    }

    public static FragmentDeals newInstance() {
        FragmentDeals fragmentFirst = new FragmentDeals();
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapterDeals != null) {
            adapterDeals.notifyData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deals, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDeals.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvDeals.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(1, getActivity()), true));
        rvDeals.setItemAnimator(new DefaultItemAnimator());
        init();
        getDeals();
    }

    public void getDeals() {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iHomeViewPresenterPresenter.getDeals(false, "", Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getDeals(true, token, Util.id(getActivity()));
        }
    }


    void init() {

        llCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCategory.performClick();
            }
        });
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentPos != position) {
                    currentPos = position;
                    tvCategoryName.setText(categoriesList.get(position).getName());
                    adapterDeals.getFilter().filter("" + categoriesList.get(position).getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getProductWithCategoriesSuccess(ProductsWithCategoryResponse response) {

    }

    @Override
    public void getBrandResponseSuccess(BrandResponse response) {

    }

    @Override
    public void getResponseSuccessSubCat(SubCategoryResponse response) {

    }


    public void addToCart(int secPos, int pos, boolean isAdd) {
        productPos = pos;
        this.secPos = secPos;
        this.isAdd = isAdd;
        if (allProductList != null && allProductList.size() > 0) {

            product = adapterDeals.getRendererList().get(pos);
            if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                createOrder();
            } else {
                addToCart();
            }
        }


    }

    void createOrder() {
        StoreList store = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
        if (store != null) {
            GenRequest genRequest = new GenRequest();
            genRequest.setUuid(Util.id(getActivity()));
            genRequest.setWarehouseId("" + store.getId());

            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
                String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

                iOrderViewPresenterPresenter.createOrder(id, genRequest);
            } else {
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                iOrderViewPresenterPresenter.createOrder(token, id, genRequest);
            }
        }
    }

    void addToCart() {
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setUuid(Util.id(getActivity()));
        addToCartRequest.setProductId(product.getId());
        if (product.getCartQunatity() == null || product.getCartQunatity().equalsIgnoreCase("0")) {
            addToCartRequest.setQuantity(1);
        } else {
            addToCartRequest.setQuantity(Integer.parseInt(product.getCartQunatity()) + 1);

        }
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iOrderViewPresenterPresenter.addToCart(id, order_id, addToCartRequest);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.addToCart(token, id, order_id, addToCartRequest);

        }
    }

    public void wishListUpdate(int secPos, int pos, boolean isAdd) {
        productPos = pos;
        this.secPos = secPos;
        this.isAdd = isAdd;
        if (allProductList != null && allProductList.size() > 0) {

            product = adapterDeals.getRendererList().get(pos);
            WishListRequest wishListRequest = new WishListRequest();
            wishListRequest.setProductId(product.getId());
            wishListRequest.setUuid(Util.id(getActivity()));

            updateWishList(wishListRequest, isAdd);
        }


    }

    void updateWishList(WishListRequest wishListRequest, boolean status) {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            if (status) {
                iOrderViewPresenterPresenter.addToWishList(id, wishListRequest);
            } else {
                iOrderViewPresenterPresenter.removeFromWishList(id, wishListRequest);

            }
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            if (status) {
                iOrderViewPresenterPresenter.addToWishList(token, id, wishListRequest);
            } else {
                iOrderViewPresenterPresenter.removeFromWishList(token, id, wishListRequest);

            }
        }
    }

    @Override
    public void getDealsResponse(DealsResponse response) {
        try {

            if (response.getSuccess()) {
                deals = response.getData();
                if (deals != null) {
                    categoriesList = deals.getCategories();
                    if (categoriesList != null && categoriesList.size() > 0) {
                        spinnerCategoryAdapter = new SpinnerCategoryAdapter(getActivity(), R.layout.spinner_item_new, categoriesList);
                        spinnerCategory.setAdapter(spinnerCategoryAdapter);
                        tvCategoryName.setText(categoriesList.get(0).getName());
                        currentPos = 0;


                        allProductList = deals.getProducts();
                        if(allProductList!=null && allProductList.size()>0) {
                            adapterDeals = new AdapterDeals(true, allProductList, getActivity());
                            rvDeals.setAdapter(adapterDeals);
                            if (categoriesList != null && categoriesList.size() > 0) {
                                adapterDeals.getFilter().filter("" + categoriesList.get(0).getId());
                            }
                        }
                    }
                    else {
                        tvNoProduct.setVisibility(View.VISIBLE);
                        llCategory.setVisibility(View.GONE);
                        rvDeals.setVisibility(View.GONE);
                    }
                } else {
                    tvNoProduct.setVisibility(View.VISIBLE);
                    llCategory.setVisibility(View.GONE);
                    rvDeals.setVisibility(View.GONE);
                }
            }
            else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, true);
                tvNoProduct.setVisibility(View.VISIBLE);
                llCategory.setVisibility(View.GONE);
                rvDeals.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseSuccess(CategoriesResponse response) {

    }

    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {
        try {
            if (response.getSuccess()) {
                SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, "" + response.getData().getOrder().getId());
                addToCart();
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {
        try {

            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, false);
                updateCart();
/*
            v1.animate().rotationX(90).setDuration(400).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    v1.setVisibility(View.GONE);
                    v2.setRotationX(-90);
                    v2.setVisibility(View.VISIBLE);
                    v2.animate().rotationX(0).setDuration(200).setListener(null);
                    v1=null;
                    v2=null;

                }
            });
*/


            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateCart() {
        int q;
        if (product.getCartQunatity() == null || product.getCartQunatity().equalsIgnoreCase("0")) {
            product.setCartQunatity("1");
            product.setIsInCart(true);

        } else {
            if (isAdd) {
                q = Integer.parseInt(product.getCartQunatity()) + 1;
            } else {
                q = Integer.parseInt(product.getCartQunatity()) - 1;

            }
            product.setCartQunatity("" + q);
            if (q == 0) {
                product.setIsInCart(false);

            } else {
                product.setIsInCart(true);

            }
        }
        StoreProducts.getInstance().addProduct(product);
        // homeCategoriesSectionList.get(secPos).getAllProducts().set(productPos, product);
        // adapterHomeCategoriesSections.notified();
        if (secPos == 0) {
            // allProductList.set(productPos, product);
            adapterDeals.modifyList(productPos, product);
            // adapterDeals.notifyDataSetChanged();

        }
    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {

    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {

    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, false);
                product.setIsWishlisted(true);

                StoreProducts.getInstance().addProduct(product);
                if (secPos == 0) {
                    // allProductList.set(productPos, product);
                    adapterDeals.modifyList(productPos, product);
                    // adapterDeals.notifyDataSetChanged();
                }

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, false);
                product.setIsWishlisted(false);
                StoreProducts.getInstance().addProduct(product);

                //   adapterHomeCategoriesSections.notified();
                if (secPos == 0) {
                    // allProductList.set(productPos, product);
                    adapterDeals.modifyList(productPos, product);
                    // adapterDeals.notifyDataSetChanged();

                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), llView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {

    }

    @Override
    public void getMinimumAmountResponse(WishListResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

}
