package com.cheersondemand.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
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
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityHome;
import com.cheersondemand.view.ActivitySearchLocation;
import com.cheersondemand.view.ActivitySearchProducts;
import com.cheersondemand.view.adapter.AdapterHomeBrands;
import com.cheersondemand.view.adapter.AdapterHomeCategoriesSections;
import com.cheersondemand.view.adapter.AdapterHomeProductsSections;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment implements IStoreViewPresenter.IStoreView,IHomeViewPresenterPresenter.IHomeView, IOrderViewPresenterPresenter.IOrderView, View.OnClickListener {


    @BindView(R.id.rvBrands)
    RecyclerView rvBrands;
    Unbinder unbinder;
    LinearLayoutManager horizontalLayout, horizontalLayout1;
    AdapterHomeBrands adapterHomeBrands;
    AdapterHomeProductsSections adapterHomeProductsSections;
    AdapterHomeCategoriesSections adapterHomeCategoriesSections;
    Util util;
    @BindView(R.id.shimmerBrands)
    ShimmerFrameLayout shimmerBrands;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    @BindView(R.id.shimmerProducts)
    ShimmerFrameLayout shimmerProducts;
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

    private int productPos;
    private int secPos;
    private boolean isAdd;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allSampleData = new ArrayList<SectionDataModel>();
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());
        iStoreViewPresenter=new StoreViewPresenterImpl(this,getActivity());
        util = new Util();
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

    @Override
    public void onResume() {
        super.onResume();
       setStoreLocation();
       /* CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setUuid(Util.id(getActivity()));*/
        // iHomeViewPresenterPresenter.getCategories(categoryRequest);
        //  iHomeViewPresenterPresenter.getBrands(SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken(),categoryRequest);
        shimmerBrands.startShimmerAnimation();
        //  ((ActivityHome)getActivity()).getCartHasItem();
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iHomeViewPresenterPresenter.getProductWithCategories(Util.id(getActivity()));
        } else {
            String token = "bearer " + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getProductWithCategories(token, Util.id(getActivity()));
        }
    }

    void  setStoreLocation(){
        store = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
        if (store != null) {
            tvStoreName.setText(store.getName());

        }
        selectedLocation = SharedPreference.getInstance(getActivity()).getString(C.LOCATION_SELECTED);
        if (selectedLocation != null ) {
            tvLocationName.setText(selectedLocation);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            ivNotification.setVisibility(View.INVISIBLE);
        }

            ivNotification.setOnClickListener(this);
        llLocationSelect.setOnClickListener(this);
        llStoreSelect.setOnClickListener(this);
      //  etSearchProduct.setOnClickListener(this);
       // rlSearchProduct.setOnClickListener(this);
        /*rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearchProduct();

            }
        });*/
        rlSearchProduct.setClickable(true);
        rlSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearchProduct();
            }
        });
        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvBrands.setLayoutManager(horizontalLayout);


        horizontalLayout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rvProducts.setLayoutManager(horizontalLayout1);
        rvProducts.setHasFixedSize(true);
        setStoreLocation();

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
        if (response.getSuccess()) {
            if(response.getData().getHasCartProduct()){
                ((ActivityHome)getActivity()).setDot(response.getData().getHasCartProduct());
            }
            shimmerBrands.stopShimmerAnimation();
            List<Categories> categories = new ArrayList<>();

            if (response.getData().getCategories() != null && response.getData().getCategories().size() > 0) {
                if (response.getData().getCategories().size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        categories.add(response.getData().getCategories().get(i));
                    }
                } else {
                    categories.addAll(response.getData().getCategories());
                }
            }
            adapterHomeBrands = new AdapterHomeBrands(categories, getActivity());
            rvBrands.setAdapter(adapterHomeBrands);

            homeCategoriesSectionList = new ArrayList<>();
            homeCategoriesSectionList.add(new HomeCategoriesSectionList("ALL Products", response.getData().getAllProducts()));

            adapterHomeCategoriesSections = new AdapterHomeCategoriesSections(getActivity(), homeCategoriesSectionList);
            rvProducts.setAdapter(adapterHomeCategoriesSections);

            SharedPreference.getInstance(getActivity()).setBoolean(C.CART_HAS_ITEM, response.getData().getHasCartProduct());
            SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, response.getData().getUser().getOrderId());


        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
        updateStore();

    }

    @Override
    public void getBrandResponseSuccess(BrandResponse response) {

    }

    @Override
    public void getResponseSuccessSubCat(SubCategoryResponse response) {

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
        if (response.getSuccess()) {
            SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, "" + response.getData().getOrder().getId());
            addToCart();
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
            updateCart();
            ((ActivityHome)getActivity()).setDot(true);

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
            updateCart();
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
    }
    void updateStore(){
        store = SharedPreference.getInstance(getActivity()).getStore(C.SELECTED_STORE);
        if (store != null) {

            UpdateStore updateStore = new UpdateStore();
            updateStore.setWarehouseId(store.getId());
            updateStore.setUuid(Util.id(getActivity()));
            if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {

                iStoreViewPresenter.updateStore("" + SharedPreference.getInstance(getActivity()).
                        geGuestUser(C.GUEST_USER).getId(), updateStore);
            }
            else {
                String token= C.bearer+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
                String id=""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

                iStoreViewPresenter.updateStore(token,id, updateStore);
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

        homeCategoriesSectionList.get(secPos).getAllProducts().set(productPos, product);
        adapterHomeCategoriesSections.notified();
    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);

            product.setCartQunatity(null);
            product.setIsInCart(false);
            homeCategoriesSectionList.get(secPos).getAllProducts().set(productPos, product);
            adapterHomeCategoriesSections.notified();

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
            product.setIsWishlisted(true);
            adapterHomeCategoriesSections.notified();

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, false);
            product.setIsWishlisted(false);
            adapterHomeCategoriesSections.notified();

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlHomeView, true);

        }
    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {

    }

    @Override
    public void getStoreListSuccess(StoreListResponse response) {

    }

    @Override
    public void updateStoreSuccess(UpdateStoreResponse response) {

    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlHomeView, true);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
                gotoStoreList();
                break;

            case R.id.llLocationSelect:
                gotoLocation();
                break;
            case R.id.etSearchProduct:
                gotoSearchProduct();
                break;
            case R.id.rlSearchProduct:
                gotoSearchProduct();
                break;
        }
    }


    public void addToCart(int secPos, int pos, boolean isAdd) {
        productPos = pos;
        this.secPos = secPos;
        this.isAdd = isAdd;
        if (homeCategoriesSectionList != null && homeCategoriesSectionList.size() > 0) {
            HomeCategoriesSectionList section = homeCategoriesSectionList.get(secPos);

            product = section.getAllProducts().get(pos);
            if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                createOrder();
            } else {
                addToCart();
            }
        }


    }


    public void wishListUpdate(int secPos, int pos, boolean isAdd) {
        productPos = pos;
        this.secPos = secPos;
        this.isAdd = isAdd;
        if (homeCategoriesSectionList != null && homeCategoriesSectionList.size() > 0) {
            HomeCategoriesSectionList section = homeCategoriesSectionList.get(secPos);

            product = section.getAllProducts().get(pos);
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

    public void updateCart(int secPos, int productPos, boolean isAdd) {
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
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iOrderViewPresenterPresenter.createOrder(id, new GenRequest(Util.id(getActivity())));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.createOrder(token, id, new GenRequest(Util.id(getActivity())));
        }
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
        Intent intent = new Intent(getActivity(), ActivitySearchProducts.class);
        startActivity(intent);
    }
}
