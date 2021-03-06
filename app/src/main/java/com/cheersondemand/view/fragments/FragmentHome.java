package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.HomeCategoriesSectionList;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SectionDataModel;
import com.cheersondemand.model.SingleItemModel;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.deals.DealsResponse;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.products.Banner;
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.model.store.StoreListResponse;
import com.cheersondemand.model.store.UpdateStore;
import com.cheersondemand.model.store.UpdateStoreResponse;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.store.IStoreViewPresenter;
import com.cheersondemand.presenter.store.StoreViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.StoreProducts;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;
import com.cheersondemand.view.ActivitySearchLocation;
import com.cheersondemand.view.adapter.AdapterHomeBrands;
import com.cheersondemand.view.adapter.AdapterHomeCategories;
import com.cheersondemand.view.adapter.AdapterHomeCategoriesSections;
import com.cheersondemand.view.adapter.AdapterHomeProductOnSale;
import com.cheersondemand.view.adapter.AdapterHomeProductsSections;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IStoreViewPresenter.IStoreView, IHomeViewPresenterPresenter.IHomeView, IOrderViewPresenterPresenter.IOrderView, View.OnClickListener {

    View v1, v2;

    Unbinder unbinder;
    LinearLayoutManager horizontalLayout, horizontalLayout1;
    AdapterHomeBrands adapterHomeBrands;
    AdapterHomeProductsSections adapterHomeProductsSections;
    AdapterHomeCategoriesSections adapterHomeCategoriesSections;
    Util util;

    AdapterHomeCategories adapterHomeCategories;
    AdapterHomeProductOnSale adapterHomeProductOnSale;
    ArrayList<SectionDataModel> allSampleData;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    IStoreViewPresenter iStoreViewPresenter;
    StoreList store;
    String selectedLocation;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;
    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.tvStoreName)
    TextView tvStoreName;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.llLocationSelect)
    LinearLayout llLocationSelect;
    @BindView(R.id.llStoreSelect)
    LinearLayout llStoreSelect;
    ArrayList<HomeCategoriesSectionList> homeCategoriesSectionList;
    @BindView(R.id.rlHomeView)
    RelativeLayout rlHomeView;
    AllProduct product;

    @BindView(R.id.ivNotification)
    ImageView ivNotification;
    @BindView(R.id.etSearchProduct)
    TextView etSearchProduct;
    @BindView(R.id.rlSearchProduct)
    RelativeLayout rlSearchProduct;
    @BindView(R.id.tvNoStoreAvailable)
    TextView tvNoStoreAvailable;
    @BindView(R.id.rlBrands)
    RelativeLayout rlBrands;
    @BindView(R.id.rlProducts)
    RelativeLayout rlProducts;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvBrands)
    RecyclerView rvBrands;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    @BindView(R.id.rvBrandsShimmer)
    ShimmerRecyclerView rvBrandsShimmer;
    @BindView(R.id.rvProductsShimmer)
    ShimmerRecyclerView rvProductsShimmer;
    @BindView(R.id.ivBanner)
    ImageView ivBanner;
    @BindView(R.id.rlBanner)
    LinearLayout rlBanner;
    @BindView(R.id.rlImage)
    RelativeLayout rlImage;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.rvProductsOnSale)
    RecyclerView rvProductsOnSale;
    @BindView(R.id.tvRecommendedProducts)
    TextView tvRecommendedProducts;
    @BindView(R.id.tvProductsOnSale)
    TextView tvProductsOnSale;
    private GridLayoutManager lLayout;
    int width;
    private int productPos;
    private int secPos;
    private boolean isAdd;
    boolean isProgressShown = false, isApiWorking = false;
    Banner banner;
    List<AllProduct> onSaleProductList;
    List<AllProduct> allProductList;
    ImageLoader imageLoader;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allSampleData = new ArrayList<SectionDataModel>();
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());
        iStoreViewPresenter = new StoreViewPresenterImpl(this, getActivity());
        util = new Util();
        imageLoader = new ImageLoader(getActivity());
        getDisplayCoordinate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context darkTheme = new ContextThemeWrapper(getActivity(),
                R.style.AppTheme);
        inflater = (LayoutInflater)
                darkTheme.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //  return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    void getDisplayCoordinate() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        width -= 50;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_QUANTITY_UPDATED)) {
            dialog();
        }
        setStoreLocation();
        if(SharedPreference.getInstance(getActivity()).getBoolean(C.IS_STORE_UPDATED_HOME)){
            SharedPreference.getInstance(getActivity()).setBoolean(C.IS_STORE_UPDATED_HOME, false);
            getProductsAndCategories();
        }
        else {
            if (allProductList != null && allProductList.size() > 0) {
                //allProductList=StoreProducts.getInstance().getListOfProducts();
                //adapterHomeCategoriesSections.modifyList();
                adapterHomeCategories.notifyDataSetChanged();
            }
            if (onSaleProductList != null && onSaleProductList.size() > 0) {
                //allProductList=StoreProducts.getInstance().getListOfProducts();
                //adapterHomeCategoriesSections.modifyList();
                adapterHomeProductOnSale.notifyDataSetChanged();
            }
        }
       /* CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setUuid(Util.id(getActivity()));*/
        // iHomeViewPresenterPresenter.getCategories(categoryRequest);
        //  iHomeViewPresenterPresenter.getBrands(SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken(),categoryRequest);
        //  ((ActivityHome)getActivity()).getCartHasItem();
    }


    void getProductsAndCategories() {
        isProgressShown = false;
        rvBrandsShimmer.showShimmerAdapter();
        rvProductsShimmer.showShimmerAdapter();

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iHomeViewPresenterPresenter.getProductWithCategories(Util.id(getActivity()));
        } else {
            String token = "bearer " + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getProductWithCategories(token, Util.id(getActivity()));
        }
    }

    void setStoreLocation() {
        store = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
        if (store != null) {
            rvBrands.setVisibility(View.VISIBLE);
            rvProducts.setVisibility(View.VISIBLE);
            tvNoStoreAvailable.setVisibility(View.GONE);
            tvStoreName.setText(store.getName());

        } else {
            rvBrands.setVisibility(View.GONE);
            rvProducts.setVisibility(View.GONE);
            tvNoStoreAvailable.setVisibility(View.VISIBLE);
            // tvNoStoreAvailable.setText(SharedPreference.getInstance(getActivity()).getString(C.STORE_MSG));
            tvStoreName.setText("---");

        }
        selectedLocation = SharedPreference.getInstance(getActivity()).getString(C.LOCATION_SELECTED);
        if (selectedLocation != null) {
            tvLocationName.setText(selectedLocation);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            ivNotification.setVisibility(View.INVISIBLE);
        }
        SharedPreference.getInstance(getActivity()).setBoolean(C.IS_FROM_PAYMENT, false);
        ivNotification.setOnClickListener(this);
        llLocationSelect.setOnClickListener(this);
        llStoreSelect.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        ivBanner.setOnClickListener(this);
        //  etSearchProduct.setOnClickListener(this);
        // rlSearchProduct.setOnClickListener(this);
        /*rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearchProduct();

            }
        });*/

        //  rvProducts.setNestedScrollingEnabled(false);
        rlSearchProduct.setClickable(true);
        rlSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearchProduct();
            }
        });
        // horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        lLayout = new GridLayoutManager(getActivity(), 2);

        rvBrands.setLayoutManager(lLayout);
        rvBrands.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(1, getActivity()), true));
        rvBrands.setItemAnimator(new DefaultItemAnimator());
        // horizontalLayout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvProducts.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(1, getActivity()), true));
        rvProducts.setItemAnimator(new DefaultItemAnimator());

        rvProductsOnSale.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvProductsOnSale.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(1, getActivity()), true));
        rvProductsOnSale.setItemAnimator(new DefaultItemAnimator());

        //rvProducts.setLayoutFrozen(true);

        rvProducts.setHasFixedSize(true);
        rvProducts.setNestedScrollingEnabled(true);

        rvProductsOnSale.setHasFixedSize(true);
        rvProductsOnSale.setNestedScrollingEnabled(false);

        rvBrands.setHasFixedSize(true);
        rvBrands.setNestedScrollingEnabled(false);

        swipeRefreshLayout.setDistanceToTriggerSync(20);
        swipeRefreshLayout.setOnRefreshListener(this);
        getProductsAndCategories();

        //setStoreLocation();

  /*      adapterHomeBrands = new AdapterHomeBrands(setHomeBrands(), getActivity());
        rvBrands.setAdapter(adapterHomeBrands);
*/
        //    shimmerBrands.stopShimmerAnimation();


        /*createDummyData();
        adapterHomeProductsSections = new AdapterHomeProductsSections(getActivity(), allSampleData);
        rvProducts.setAdapter(adapterHomeProductsSections);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void createDummyData() {
        for (int i = 1; i <= 2; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(getString(R.string.latest_addition));

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 10; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    List<String> setHomeBrands() {
        List<String> brandsList = new ArrayList<>();

        brandsList.add("Beer");
        brandsList.add("Whisky");
        brandsList.add("Vodka");
        brandsList.add("Rum");
        brandsList.add("Desi daaru");

        return brandsList;
    }

    @Override
    public void getProductWithCategoriesSuccess(ProductsWithCategoryResponse response) {
        try {
            StoreProducts.getInstance().clear();
            isProgressShown = true;

            rvBrandsShimmer.hideShimmerAdapter();
            rvProductsShimmer.hideShimmerAdapter();
            rvBrands.setVisibility(View.VISIBLE);
            rvProducts.setVisibility(View.VISIBLE);

            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);

            }
            if (response.getSuccess()) {
                banner = response.getData().getBanner();
                onSaleProductList = response.getData().getOnSaleProducts();
                if (banner != null) {
                    // rlImage.setLayoutParams(new LinearLayout.LayoutParams(width, 389 * width / 1242));
                    //    Util.setImage(getActivity(), banner.getImage(), ivBanner);
                    imageLoader.DisplayImage(banner.getImage(), ivBanner);
                }
                rvBrands.setVisibility(View.VISIBLE);
                rvProducts.setVisibility(View.VISIBLE);
                tvNoStoreAvailable.setVisibility(View.GONE);

                if (response.getData().getHasCartProduct()) {
                    ((ActivityHome) getActivity()).setDot(response.getData().getHasCartProduct());
                }


                List<Categories> categories = new ArrayList<>();

                if (response.getData() != null && response.getData().getCategories() != null && response.getData().getCategories().size() > 0) {
                    rlBrands.setVisibility(View.VISIBLE);
                    boolean isViewMore = false;
                    if (response.getData().getCategories().size() > 5) {
                        isViewMore = true;
                        for (int i = 0; i < 5; i++) {
                            categories.add(response.getData().getCategories().get(i));
                        }
                    } else {
                        categories.addAll(response.getData().getCategories());
                    }
                    /*if (isViewMore) {
                        rlViewMoreCategory.setVisibility(View.VISIBLE);
                    } else {
                        rlViewMoreCategory.setVisibility(View.GONE);

                    }*/
                    adapterHomeBrands = new AdapterHomeBrands(C.FRAGMENT_PRODUCTS_HOME, isViewMore, categories, getActivity());
                    rvBrands.setAdapter(adapterHomeBrands);
                } else {
                    rlBrands.setVisibility(View.GONE);
                }

                if (response.getData() != null && response.getData().getAllProducts() != null && response.getData().getAllProducts().size() > 0) {
                    rlProducts.setVisibility(View.VISIBLE);
                    tvRecommendedProducts.setVisibility(View.VISIBLE);
                    //  homeCategoriesSectionList = new ArrayList<>();
                    allProductList = response.getData().getAllProducts();
                    StoreProducts.getInstance().saveProducts(allProductList);
                   /* homeCategoriesSectionList.add(new HomeCategoriesSectionList("Recommended products", allProductList));
                    if (onSaleProductList != null && onSaleProductList.size() > 0) {
                        homeCategoriesSectionList.add(new HomeCategoriesSectionList("Products On Sale", onSaleProductList));
                    }
                    adapterHomeCategoriesSections = new AdapterHomeCategoriesSections(getActivity(), homeCategoriesSectionList);
                    rvProducts.setAdapter(adapterHomeCategoriesSections);
*/
                    adapterHomeCategories = new AdapterHomeCategories(true, allProductList, getActivity());
                    rvProducts.setAdapter(adapterHomeCategories);
                    if (onSaleProductList != null && onSaleProductList.size() > 0) {
                        rvProductsOnSale.setVisibility(View.VISIBLE);
                        tvProductsOnSale.setVisibility(View.VISIBLE);

                        adapterHomeProductOnSale = new AdapterHomeProductOnSale(true, onSaleProductList, getActivity());
                        rvProductsOnSale.setAdapter(adapterHomeProductOnSale);
                    }

                    SharedPreference.getInstance(getActivity()).setBoolean(C.CART_HAS_ITEM, response.getData().getHasCartProduct());
                    SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, response.getData().getUser().getOrderId());
                    if (response.getData().getHasCartProduct()) {
                        ((ActivityHome) getActivity()).setDot(true);

                    } else {
                        ((ActivityHome) getActivity()).setDot(false);

                    }
                } else {
                    tvNoStoreAvailable.setVisibility(View.VISIBLE);
                    tvNoStoreAvailable.setText(getString(R.string.no_product_found_));
                    rlProducts.setVisibility(View.GONE);
                }
            } else {
                rvBrands.setVisibility(View.GONE);
                rvProducts.setVisibility(View.GONE);
                tvNoStoreAvailable.setVisibility(View.VISIBLE);
                tvNoStoreAvailable.setText(response.getMessage());
                //   util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

            }
            updateStore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBrandResponseSuccess(BrandResponse response) {

    }

    @Override
    public void getResponseSuccessSubCat(SubCategoryResponse response) {

    }

    @Override
    public void getDealsResponse(DealsResponse response) {

    }

    @Override
    public void getResponseSuccess(CategoriesResponse response) {
        // Log.e("DEBUG", "" + response.getMessage());
       /* if (response.getSuccess()) {
            shimmerBrands.stopShimmerAnimation();
            adapterHomeBrands = new AdapterHomeBrands(response.getData(), getActivity());
            rvBrands.setAdapter(adapterHomeBrands);
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlHomeView,true );

        }*/
    }

    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {
        try {
            isApiWorking = false;
            if (response.getSuccess()) {
                SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, "" + response.getData().getOrder().getId());
                addToCart();
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {
        try {
            isApiWorking = false;
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
                updateCart();
                ((ActivityHome) getActivity()).setDot(true);
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
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {
        try {
            isApiWorking = false;
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
                updateCart();
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateStore() {
        isProgressShown = false;
        store = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
        if (store != null) {

            UpdateStore updateStore = new UpdateStore();
            updateStore.setWarehouseId(store.getId());
            updateStore.setUuid(Util.id(getActivity()));
            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {

                iStoreViewPresenter.updateStore("" + SharedPreference.getInstance(getActivity()).
                        geGuestUser(C.GUEST_USER).getId(), updateStore);
            } else {
                String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                iStoreViewPresenter.updateStore(token, id, updateStore);
            }
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
            allProductList.set(productPos, product);
            adapterHomeCategories.notifyDataSetChanged();
        } else if (secPos == 1) {
            onSaleProductList.set(productPos, product);
            adapterHomeProductOnSale.notifyDataSetChanged();
        }
    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        try {
            isApiWorking = false;
            if (response.getSuccess()) {


                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
                product.setCartQunatity(null);
                product.setIsInCart(false);
                StoreProducts.getInstance().addProduct(product);

                homeCategoriesSectionList.get(secPos).getAllProducts().set(productPos, product);
                adapterHomeCategoriesSections.notified();

                if (response.getData() == null) {
                    SharedPreference.getInstance(getActivity()).setBoolean(C.CART_HAS_ITEM, false);
                    SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, null);
                    ((ActivityHome) getActivity()).setDot(false);
                }
        /*    v1.animate().rotationX(90).setDuration(400).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    v1.setVisibility(View.GONE);
                    v2.setRotationX(-90);
                    v2.setVisibility(View.VISIBLE);
                    v2.animate().rotationX(0).setDuration(200).setListener(null);

                }
            });*/


            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {
        try {
            if (response.getSuccess()) {

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {
        try {
            isApiWorking = false;
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
                product.setIsWishlisted(true);

                StoreProducts.getInstance().addProduct(product);
                if (secPos == 0) {
                    allProductList.set(productPos, product);
                    adapterHomeCategories.notifyDataSetChanged();

                } else if (secPos == 1) {
                    onSaleProductList.set(productPos, product);
                    adapterHomeProductOnSale.notifyDataSetChanged();

                }

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        try {
            isApiWorking = false;
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
                product.setIsWishlisted(false);
                StoreProducts.getInstance().addProduct(product);

                //   adapterHomeCategoriesSections.notified();
                if (secPos == 0) {
                    allProductList.set(productPos, product);
                    adapterHomeCategories.notifyDataSetChanged();

                } else if (secPos == 1) {
                    onSaleProductList.set(productPos, product);
                    adapterHomeProductOnSale.notifyDataSetChanged();

                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

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
    public void getStoreListSuccess(StoreListResponse response) {

    }

    @Override
    public void updateStoreSuccess(UpdateStoreResponse response) {
        isProgressShown = true;
        if (response.getSuccess()) {
            if (response.getData() != null && response.getData().getIsQuantityUpdated()) {
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_QUANTITY_UPDATED, response.getData().getIsQuantityUpdated());


                dialog();
            } else {
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_QUANTITY_UPDATED, false);

            }
        }
    }

    @Override
    public void getResponseError(String response) {
        try {
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
                rvProductsShimmer.hideShimmerAdapter();
                rvBrandsShimmer.hideShimmerAdapter();
            }
            util.setSnackbarMessage(getActivity(), response, rlHomeView, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showProgress() {
        if (isProgressShown) {
            util.showDialog(C.MSG, getActivity());
        }

    }

    @Override
    public void hideProgress() {
        if (isProgressShown) {
            util.hideDialog();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNotification:
                Intent intent = new Intent(getActivity(), ActivityContainer.class);
                Bundle bundle = new Bundle();
                intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_NOTIFICATIONS);
                intent.putExtra(C.BUNDLE, bundle);
                startActivity(intent);
                // gotoSearchProduct();

                break;
            case R.id.llStoreSelect:
                if (SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE) != null) {
                    // gotoStoreList();
                    gotoLocationAndStoreList();
                }
                break;

            case R.id.llLocationSelect:
                //gotoLocation();
                gotoLocationAndStoreList();
                break;
            case R.id.etSearchProduct:
                //gotoSearchProduct();
                gotoLocationAndStoreList();

                break;
            case R.id.rlSearchProduct:
                // gotoSearchProduct();
                gotoLocationAndStoreList();

                break;
            case R.id.ivCart:
                // gotoSearchProduct();
                gotoCart();
                break;
            case R.id.ivBanner:
                gotoProductListingPage();
                break;
        }
    }


    void gotoProductListingPage(){
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle = new Bundle();
        if(banner.getCategoryId()!=null && !banner.getCategoryId().equals("0") && !banner.getCategoryId().equals(""))
        {
            bundle.putString(C.CAT_ID, "" + banner.getCategoryId());

        }
        else {
            bundle.putString(C.CAT_ID, "");

        }
        if(banner.getSubCategoryId()!=null && !banner.getSubCategoryId().equals("0") && !banner.getSubCategoryId().equals(""))
        {
            bundle.putString(C.SUB_CAT_ID, "" + banner.getSubCategoryId());

        }
        else {
            bundle.putString(C.SUB_CAT_ID, "");

        }
        bundle.putBoolean(C.IS_ON_SALE, false);

        bundle.putInt(C.SOURCE, C.FRAGMENT_CATEGORIES_HOME);
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_PRODUCT_LISTING);
        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);
    }
    void showViewMore() {
        Bundle bundle = new Bundle();
        bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCTS_HOME);
        ((ActivityHome) getActivity()).fragmnetLoader(C.FRAGMENT_CATEGORIES, bundle);
    }

    public void addToCart(int secPos, int pos, boolean isAdd, View v1, View v2) {
        this.v1 = v1;
        this.v2 = v2;
        productPos = pos;
        this.secPos = secPos;
        this.isAdd = isAdd;
        if (allProductList != null && allProductList.size() > 0) {
            product = StoreProducts.getInstance().getProduct(allProductList.get(pos).getId());
            if (product == null) {
                product = allProductList.get(pos);
            }
            if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                createOrder();
            } else {
                addToCart();
            }
        }


    }

    public void addToCart(int secPos, int pos, boolean isAdd) {
        productPos = pos;
        this.secPos = secPos;
        this.isAdd = isAdd;
        if (secPos == 0) {
            if (allProductList != null && allProductList.size() > 0) {
                product = StoreProducts.getInstance().getProduct(allProductList.get(pos).getId());
                if (product == null) {
                    product = allProductList.get(pos);
                }

            }
        } else if (secPos == 1) {
            if (onSaleProductList != null && onSaleProductList.size() > 0) {
                product = StoreProducts.getInstance().getProduct(onSaleProductList.get(pos).getId());
                if (product == null) {
                    product = onSaleProductList.get(pos);
                }

            }
        }
        if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
            createOrder();
        } else {
            addToCart();
        }
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

    public void wishListUpdate(int secPos, int pos, boolean isAdd) {
        if (!isApiWorking) {
            isApiWorking = true;
            productPos = pos;
            this.secPos = secPos;
            this.isAdd = isAdd;
            if (secPos == 0) {
                if (allProductList != null && allProductList.size() > 0) {
                    product = StoreProducts.getInstance().getProduct(allProductList.get(pos).getId());
                    if (product == null) {
                        product = allProductList.get(pos);
                    }

                }
            } else if (secPos == 1) {
                if (onSaleProductList != null && onSaleProductList.size() > 0) {
                    product = StoreProducts.getInstance().getProduct(onSaleProductList.get(pos).getId());
                    if (product == null) {
                        product = onSaleProductList.get(pos);
                    }

                }
            }

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

    public void updateCart(int secPos, int productPos, boolean isAdd, View v1, View v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.secPos = secPos;
        this.productPos = productPos;
        this.isAdd = isAdd;
        if (homeCategoriesSectionList != null && homeCategoriesSectionList.size() > 0) {
            HomeCategoriesSectionList section = homeCategoriesSectionList.get(secPos);

            product = section.getAllProducts().get(productPos);

            UpdateCartRequest updateCartRequest = new UpdateCartRequest();
            updateCartRequest.setUuid(Util.id(getActivity()));
            updateCartRequest.setProductId(product.getId());
            updateCartRequest.setIsIncrease(isAdd);
            if (isAdd) {
                updateCartRequest.setQuantity(Integer.parseInt(product.getCartQunatity()) + 1);
                updateProduct(updateCartRequest);
            } else {
                if (Integer.parseInt(product.getCartQunatity()) == 1) {
                    removeProduct(updateCartRequest);
                } else {
                    updateCartRequest.setQuantity(Integer.parseInt(product.getCartQunatity()) - 1);
                    updateProduct(updateCartRequest);
                }
            }

        }
    }

    public void updateCart(int secPos, int productPos, boolean isAdd) {
        if (!isApiWorking) {
            isApiWorking = true;
            this.secPos = secPos;
            this.productPos = productPos;
            this.isAdd = isAdd;
            if (allProductList != null && allProductList.size() > 0) {
                product = StoreProducts.getInstance().getProduct(allProductList.get(productPos).getId());
                if (product == null) {
                    product = allProductList.get(productPos);
                }

                UpdateCartRequest updateCartRequest = new UpdateCartRequest();
                updateCartRequest.setUuid(Util.id(getActivity()));
                updateCartRequest.setProductId(product.getId());
                updateCartRequest.setIsIncrease(isAdd);
                if (isAdd) {
                    updateCartRequest.setQuantity(Integer.parseInt(product.getCartQunatity()) + 1);
                    updateProduct(updateCartRequest);
                } else {
                    if (Integer.parseInt(product.getCartQunatity()) == 1) {
                        removeProduct(updateCartRequest);
                    } else {
                        updateCartRequest.setQuantity(Integer.parseInt(product.getCartQunatity()) - 1);
                        updateProduct(updateCartRequest);
                    }
                }

            }
        }
    }


    public void removeProduct(UpdateCartRequest updateCartRequest) {
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            iOrderViewPresenterPresenter.removeItemFromCart(id, order_id, updateCartRequest);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.removeItemFromCart(token, id, order_id, updateCartRequest);
        }
    }

    void updateProduct(UpdateCartRequest updateCartRequest) {
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            iOrderViewPresenterPresenter.updateCart(id, order_id, updateCartRequest);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.updateCart(token, id, order_id, updateCartRequest);
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

    void createOrder() {

        store = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
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

    void gotoLocationAndStoreList() {
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_STORE_LOCATION_LIST);
        bundle.putInt(C.FROM, C.HOME);
        bundle.putBoolean(C.IS_CROSS_SHOW, true);

        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);

    }

    void gotoStoreList() {
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle = new Bundle();
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_STORE_LIST);
        bundle.putInt(C.FROM, C.HOME);

        intent.putExtra(C.BUNDLE, bundle);
        startActivity(intent);
    }

    void gotoLocation() {
        Intent intent = new Intent(getActivity(), ActivitySearchLocation.class);

        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_STORE_LIST);
        intent.putExtra(C.FROM, C.HOME);
        startActivity(intent);
    }

    void gotoSearchProduct() {
       /* Intent intent = new Intent(getActivity(), ActivitySearchProducts.class);
        startActivity(intent);*/
        Intent intent = new Intent(getActivity(), ActivityContainer.class);
        Bundle bundle = new Bundle();
        bundle.putInt(C.SOURCE, C.FRAGMENT_SEARCH_PRODUCT);
        intent.putExtra(C.BUNDLE, bundle);
        intent.putExtra(C.FRAGMENT_ACTION, C.FRAGMENT_SEARCH_PRODUCT);
        startActivity(intent);
    }

    void dialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog_ok); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(getString(R.string.app_name));
        message.setText(getString(R.string.sorry_quantity_updated));
//add some action to the buttons
        Button yes = (Button) dialog.findViewById(R.id.bmessageDialogOK);
        yes.setText(getString(R.string.ok));
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                SharedPreference.getInstance(getActivity()).setBoolean(C.IS_QUANTITY_UPDATED, false);

            }
        });

        dialog.show();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        getProductsAndCategories();
    }
}
