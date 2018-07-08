package com.cheersondemand.view.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.Brand;
import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.Categories;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategory;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.productList.ProductListResponse;
import com.cheersondemand.model.productdescription.SimilarProductsResponse;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.HomeViewPresenterImpl;
import com.cheersondemand.presenter.home.IHomeViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.productDescription.IProductDescViewPresenter;
import com.cheersondemand.presenter.productDescription.ProductDescViewPresenterImpl;
import com.cheersondemand.presenter.products.IProductViewPresenter;
import com.cheersondemand.presenter.products.ProductViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.ActivityFilters;
import com.cheersondemand.view.adapter.products.AdapterProducts;
import com.cheersondemand.view.adapter.products.AdapterSortList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.cheersondemand.util.C.REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProductsListing extends Fragment implements View.OnClickListener, IProductViewPresenter.IProductView, IOrderViewPresenterPresenter.IOrderView, IProductDescViewPresenter.IProductDescView, IHomeViewPresenterPresenter.IHomeView {


    @BindView(R.id.llSort)
    LinearLayout llSort;
    @BindView(R.id.llFilter)
    LinearLayout llFilter;
    @BindView(R.id.rvProductsList)
    RecyclerView rvProductsList;
    Unbinder unbinder;
    Util util;
    IProductViewPresenter iProductViewPresenter;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    IHomeViewPresenterPresenter iHomeViewPresenterPresenter;
    IProductDescViewPresenter iProductDescViewPresenter;
    AdapterProducts adapterProducts;
    List<AllProduct> allProductList;
    int page = 1;
    int perPage = 10;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.rlbar)
    RelativeLayout rlbar;
    @BindView(R.id.rlMainView)
    RelativeLayout rlMainView;
    @BindView(R.id.btnBrowseProduct)
    Button btnBrowseProduct;
    @BindView(R.id.llNoProductInCount)
    LinearLayout llNoProductInCount;
    private GridLayoutManager lLayout;
    String catId, subCatId;
    String from = "0", to = "5000";
    String orderBy = "desc";
    String orderField = "created_at";
    int source;
    private int productPos;
    private int secPos;
    private boolean isAdd, isFilter = false;
    AllProduct product;
    List<Categories> categoriesList;
    List<Brand> brandList;
    List<SubCategory> subCatList;

    int totalPages;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean loading = true;
    int sortPos = -1;
    List<Integer> catIdList;
    List<Integer> brandId;
    List<Integer> sub_cat_id;

    public FragmentProductsListing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();

        iProductViewPresenter = new ProductViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());
        iHomeViewPresenterPresenter = new HomeViewPresenterImpl(this, getActivity());
        iProductDescViewPresenter = new ProductDescViewPresenterImpl(this, getActivity());

        catId = getArguments().getString(C.CAT_ID);
        source = getArguments().getInt(C.SOURCE);
        subCatId = getArguments().getString(C.SUB_CAT_ID);

        catIdList = new ArrayList<>();
        brandId = new ArrayList<>();
        sub_cat_id = new ArrayList<>();
        if (!catId.equals("")) {
            catIdList.add(Integer.parseInt(catId));
        }
        if (!subCatId.equals("")) {
            sub_cat_id.add(Integer.parseInt(subCatId));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_products_listing, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lLayout = new GridLayoutManager(getActivity(), 2);
        //rvProductsList.setHasFixedSize(true);
        llFilter.setOnClickListener(this);
        llSort.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        rvProductsList.setLayoutManager(lLayout);
        btnBrowseProduct.setOnClickListener(this);
        rvProductsList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvProductsList.setItemAnimator(new DefaultItemAnimator());
        getProducts(page);
        rvProductsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = lLayout.getChildCount();
                    totalItemCount = lLayout.getItemCount();
                    pastVisibleItems = lLayout.findFirstVisibleItemPosition();
                    if (loading && page <= totalPages) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            progressbar.setVisibility(View.VISIBLE);
                            getProducts(page++);
                        }
                    }
                }
            }
        });
        getBrands();
        getCategories();
    }


    void getProducts(int page) {
        if (isFilter) {
            getProductsList(page, perPage, catIdList, brandId, sub_cat_id, "" + from, "" + to, orderBy, orderField);
        } else {
            if (source == C.FRAGMENT_CATEGORIES || source == C.FRAGMENT_CATEGORIES_HOME) {
                getProductsList(page, perPage, catIdList, brandId, sub_cat_id, "" + from, "" + to, orderBy, orderField);
            } else if (source == C.FRAGMENT_PRODUCTS_HOME) {
                getAllProducts(page, perPage, "" + from, "" + to, orderBy, orderField);

            } else if (source == C.FRAGMENT_PRODUCT_DESC) {
                getSimilarProducts(catId, page, perPage);
            }
        }
    }

    void getCategories() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iHomeViewPresenterPresenter.getCategories(false, "", Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getCategories(true, token, Util.id(getActivity()));
        }
    }

    void getBrands() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iHomeViewPresenterPresenter.getBrands(false, "", Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iHomeViewPresenterPresenter.getBrands(true, token, Util.id(getActivity()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.product_listing);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    void getProducts(int page, int perPage) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getProductList(false, "", Util.id(getActivity()), "" + page, "" + perPage);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getProductList(true, token, Util.id(getActivity()), "" + page, "" + perPage);
        }
    }

    void getProductsList(int page, int perPage, String catId, String from, String to, String orderBy, String orderField) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getProductList(false, "", Util.id(getActivity()), "" + page, "" + perPage, catId, from, to, orderBy, orderField);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getProductList(true, token, Util.id(getActivity()), "" + page, "" + perPage, catId, from, to, orderBy, orderField);
        }
    }

    void getProductsList(int page, int perPage, List<Integer> catId, String from, String to, String orderBy, String orderField) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getAllProductsFilter(false, "", catId, Util.id(getActivity()), "" + page, "" + perPage, from, to, orderBy, orderField);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getAllProductsFilter(true, token, catId, Util.id(getActivity()), "" + page, "" + perPage, from, to, orderBy, orderField);
        }
    }

    void getProductsList(int page, int perPage, List<Integer> catId, List<Integer> brandId, List<Integer> sub_cat_id, String from, String to, String orderBy, String orderField) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getAllProductsFilter(false, "", catId, brandId, sub_cat_id, Util.id(getActivity()), "" + page, "" + perPage, from, to, orderBy, orderField);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getAllProductsFilter(true, token, catId, brandId, sub_cat_id, Util.id(getActivity()), "" + page, "" + perPage, from, to, orderBy, orderField);
        }
    }

    void getSimilarProducts(String id, int page, int per_page) {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductDescViewPresenter.getSimilarProducts(id, Util.id(getActivity()), "" + page, "" + per_page);
        } else {
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductDescViewPresenter.getSimilarProducts(token, id, Util.id(getActivity()), "" + page, "" + per_page);

        }
    }

    void getAllProducts(int page, int perPage, String from, String to, String orderBy, String orderField) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getAllProducts(false, "", Util.id(getActivity()), "" + page, "" + perPage, from, to, orderBy, orderField);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getAllProducts(true, token, Util.id(getActivity()), "" + page, "" + perPage, from, to, orderBy, orderField);
        }
    }

    @Override
    public void getProductListSuccess(ProductListResponse response) {
        if (response.getSuccess()) {
            progressbar.setVisibility(View.GONE);
            if (allProductList != null && allProductList.size() > 0 && page != 1) {
                allProductList.addAll(response.getData());
                adapterProducts.notifyDataSetChanged();
            } else {
                if(response.getData()!=null && response.getData().size()>0) {
                    llNoProductInCount.setVisibility(View.GONE);

                    if (response.getMeta() != null && response.getMeta().getPagination() != null) {
                        totalPages = response.getMeta().getPagination().getTotalPages();
                    }
                    allProductList = response.getData();
                    adapterProducts = new AdapterProducts(allProductList, getActivity());
                    rvProductsList.setAdapter(adapterProducts);
                }
                else {
                    llNoProductInCount.setVisibility(View.VISIBLE);
                }
            }
            loading = true;
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }


    public void addToCart(int secPos, int pos, boolean isAdd) {
        productPos = pos;
        this.secPos = secPos;
        this.isAdd = isAdd;
        if (allProductList != null && allProductList.size() > 0) {

            product = allProductList.get(pos);
            if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                createOrder();
            } else {
                addToCart();
            }
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

            product = allProductList.get(pos);
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
        if (allProductList != null && allProductList.size() > 0) {

            product = allProductList.get(productPos);

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


    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {
        if (response.getSuccess()) {
            SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, "" + response.getData().getOrder().getId());
            addToCart();
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            updateCart();
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            updateCart();
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

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

        allProductList.set(productPos, product);
        adapterProducts.notifyDataSetChanged();
    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);

            product.setCartQunatity(null);
            product.setIsInCart(false);
            allProductList.set(productPos, product);
            adapterProducts.notifyDataSetChanged();

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {

    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            product.setIsWishlisted(true);
            adapterProducts.notifyDataSetChanged();

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            product.setIsWishlisted(false);
            adapterProducts.notifyDataSetChanged();

        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {

    }

    @Override
    public void getProductWithCategoriesSuccess(ProductsWithCategoryResponse response) {

    }

    @Override
    public void getBrandResponseSuccess(BrandResponse response) {
        if (response.getSuccess()) {
            brandList = response.getData();
        }
    }

    @Override
    public void getResponseSuccessSubCat(SubCategoryResponse response) {

    }

    @Override
    public void getResponseSuccess(CategoriesResponse response) {
        if (response.getSuccess()) {
            categoriesList = response.getData();
        }
    }

    @Override
    public void getResponseSuccess(SimilarProductsResponse response) {
        if (response.getSuccess()) {
            progressbar.setVisibility(View.GONE);
            if (allProductList != null && allProductList.size() > 0) {
                allProductList.addAll(response.getData().getSimilarProducts());
                adapterProducts.notifyDataSetChanged();
            } else {
                if (response.getData() != null && response.getData().getSimilarProducts() != null && response.getData().getSimilarProducts().size() > 0) {
                    llNoProductInCount.setVisibility(View.GONE);

                    if (response.getMeta() != null && response.getMeta().getPagination() != null) {
                        totalPages = response.getMeta().getPagination().getTotalPages();
                    }
                    allProductList = response.getData().getSimilarProducts();
                    adapterProducts = new AdapterProducts(allProductList, getActivity());
                    rvProductsList.setAdapter(adapterProducts);
                }
                else {
                    llNoProductInCount.setVisibility(View.VISIBLE);
                }
            }
            loading = true;
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

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
            case R.id.llFilter:
                sendFilterData();
                break;
            case R.id.llSort:
                getSortDialog();
                break;
            case R.id.imgBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnBrowseProduct:
                getActivity().finish();
                Intent intent=new Intent(getActivity(), ActivityContainer.class);
                intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_CATEGORIES);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode = " + requestCode);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra(C.BUNDLE);
            brandList = (List<Brand>) bundle.getSerializable(C.BRANDS_LIST);
            categoriesList = (List<Categories>) bundle.getSerializable(C.CATEGORY_LIST);
            subCatList = (List<SubCategory>) bundle.getSerializable(C.SUB_CATEGORY_LIST);
            from=bundle.getString(C.MIN_RANGE);
            to=bundle.getString(C.MAX_RANGE);

            catIdList = new ArrayList<>();
            brandId = new ArrayList<>();
            sub_cat_id = new ArrayList<>();

            for (int i = 0; i < categoriesList.size(); i++) {
                if (categoriesList.get(i).isSelected()) {
                    catIdList.add(categoriesList.get(i).getId());
                }
            }
            for (int i = 0; i < brandList.size(); i++) {
                if (brandList.get(i).isSelected()) {
                    brandId.add(brandList.get(i).getId());
                }
            }
            if (subCatList != null) {
                for (int i = 0; i < subCatList.size(); i++) {
                    if (subCatList.get(i).isSelected()) {
                        sub_cat_id.add(subCatList.get(i).getId());
                    }
                }
            }
            if (source == C.FRAGMENT_PRODUCTS_HOME && catIdList.size() == 0) {
                if(catId!=null &&!catId.equals("")) {
                    catIdList.add(Integer.parseInt(catId));
                }
            }

            if (catIdList.size() > 0 || brandId.size() > 0 || sub_cat_id.size() > 0) {
                isFilter = true;
            } else {
                isFilter = false;
            }
            page = 1;
            getProducts(page);

        }
    }

    void sendFilterData() {
        Intent intent = new Intent(getContext(), ActivityFilters.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(C.BRANDS_LIST, (Serializable) brandList);
        bundle.putSerializable(C.CATEGORY_LIST, (Serializable) categoriesList);
        bundle.putSerializable(C.SUB_CATEGORY_LIST, (Serializable) subCatList);
        bundle.putString(C.MIN_RANGE,from);
        bundle.putString(C.MAX_RANGE,to);
        intent.putExtra(C.BUNDLE, bundle);
        startActivityForResult(intent, REQUEST_CODE);
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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void getSortDialog() {

        View view = getLayoutInflater().inflate(R.layout.dialog_sort, null);

        ListView sortList = (ListView) view.findViewById(R.id.lvSortItems);
        ImageView btnDone = (ImageView) view.findViewById(R.id.ivCross);

        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        final AdapterSortList adapterSortList = new AdapterSortList(getActivity(), Util.getSortList(), sortPos);
        mBottomSheetDialog.show();

        sortList.setAdapter(adapterSortList);
        sortList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterSortList.setTick(position + 1);
                sortPos = position + 1;
                mBottomSheetDialog.dismiss();
                if (position == 0) {
                    orderBy = "asc";
                    //  orderField="price";
                } else if (position == 0) {
                    orderBy = "desc";
                    //  orderField="price";
                } else {
                    orderBy = "desc";
                    //  orderField = "created_at";
                }

                getProducts(1);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

    }
}
