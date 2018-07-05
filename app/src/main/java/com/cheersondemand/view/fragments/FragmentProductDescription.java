package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.productdescription.Offers;
import com.cheersondemand.model.productdescription.SimilarProductsResponse;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.productDescription.IProductDescViewPresenter;
import com.cheersondemand.presenter.productDescription.ProductDescViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.AdapterHomeCategories;
import com.cheersondemand.view.adapter.productdescription.AdapterOffers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProductDescription extends Fragment implements View.OnClickListener, IProductDescViewPresenter.IProductDescView, IOrderViewPresenterPresenter.IOrderView {

    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.lvOffers)
    ListView lvOffers;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvQuantity)
    TextView tvQuantity;
    @BindView(R.id.tvalcohal_vol)
    TextView tvalcohalVol;
    @BindView(R.id.tvRegion)
    TextView tvRegion;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvSeeMore)
    TextView tvSeeMore;
    @BindView(R.id.rvSimilarDrinks)
    RecyclerView rvSimilarDrinks;

    Unbinder unbinder;
    ImageLoader imageLoader;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvProductPrice)
    TextView tvProductPrice;

    AdapterOffers adapterOffers;
    LinearLayoutManager horizontalLayout;
    IProductDescViewPresenter iProductDescViewPresenter;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    AdapterHomeCategories adapterSimilarProducts;
    @BindView(R.id.btnAddToCart)
    Button btnAddToCart;
    @BindView(R.id.btnBuyNow)
    Button btnBuyNow;
    AllProduct product,productDes;
    @BindView(R.id.rlImage)
    RelativeLayout rlImage;
    @BindView(R.id.rlSimilar)
    RelativeLayout rlSimilar;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    @BindView(R.id.imgLike)
    ImageView imgLike;
    @BindView(R.id.rlLike)
    RelativeLayout rlLike;
    private int productPos;
    private int secPos;
    private boolean isAdd;
    List<AllProduct> allProductList;
    Util util;
    private boolean isProductDes=false;

    public FragmentProductDescription() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = new ImageLoader(getActivity());
        product = (AllProduct) getArguments().getSerializable(C.PRODUCT);
        productDes = (AllProduct) getArguments().getSerializable(C.PRODUCT);
        iProductDescViewPresenter = new ProductDescViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());
        util = new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarDrinks.setLayoutManager(horizontalLayout);
        tvSeeMore.setOnClickListener(this);
        rlLike.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        setDetail();

    }

    @Override
    public void onResume() {
        super.onResume();
        getSimilarProducts();
        ActivityContainer.tvTitle.setText(productDes.getName());
    }

    void getSimilarProducts() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductDescViewPresenter.getSimilarProducts("" + product.getId(), Util.id(getActivity()), "1", "10");
        } else {
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductDescViewPresenter.getSimilarProducts(token, "" + product.getId(), Util.id(getActivity()), "1", "10");

        }
    }

    void setDetail() {
        //  imageLoader.DisplayImage(product.getImage(), imgProduct);
        Util.setImage(getActivity(), product.getImage(), imgProduct);
        if(product.getIsWishlisted()){
            imgLike.setImageResource(R.drawable.like);
        }
        else {
            imgLike.setImageResource(R.drawable.unlike);

        }
        if(product.getIsInCart()){
            btnAddToCart.setText(getString(R.string.added_to_cart));
        }
        else {
            btnAddToCart.setText(getString(R.string.add_to_cart));

        }
        tvProductName.setText(product.getName());
        tvProductPrice.setText("$" + product.getPrice());
        tvType.setText(product.getSubCategory().getName());
        tvalcohalVol.setText("-");
        tvRegion.setText(product.getRegion());
        tvQuantity.setText("0.0");
        tvDesc.setText(product.getDescription());
        adapterOffers = new AdapterOffers(getActivity(), offersList());
        lvOffers.setAdapter(adapterOffers);
        Util.setListViewHeightBasedOnChildren(lvOffers);
    }

    List<Offers> offersList() {
        List<Offers> offersList = new ArrayList<>();
        offersList.add(new Offers(1, "Flat 60", "Flat 60 off on your purchase"));
        offersList.add(new Offers(2, "FLAT 20", "Flat 20 for each order"));
        return offersList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void getResponseSuccess(SimilarProductsResponse response) {
        if (response.getSuccess()) {
            if (response.getData().getSimilarProducts().size() > 0) {
                allProductList = response.getData().getSimilarProducts();
                if (allProductList.size() > 0) {
                    adapterSimilarProducts = new AdapterHomeCategories(false, allProductList, getActivity());
                    rvSimilarDrinks.setAdapter(adapterSimilarProducts);
                }
            } else {
                rlSimilar.setVisibility(View.GONE);
                rvSimilarDrinks.setVisibility(View.GONE);
            }
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

            if(isProductDes){
                btnAddToCart.setText(getString(R.string.added_to_cart));
                productDes.setIsInCart(true);
                isProductDes=false;
            }
            else {
                updateCart();
            }
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
            isProductDes=false;

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
        adapterSimilarProducts.notifyDataSetChanged();
    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);

            product.setCartQunatity(null);
            product.setIsInCart(false);
            allProductList.set(productPos, product);
            adapterSimilarProducts.notifyDataSetChanged();

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
            if(isProductDes) {
                isProductDes=false;

                productDes.setIsWishlisted(true);
                imgLike.setImageResource(R.drawable.like);
            }
            else {
                product.setIsWishlisted(true);
                adapterSimilarProducts.notifyDataSetChanged();
            }
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
            isProductDes=false;

        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
            if(isProductDes) {
                isProductDes=false;
                productDes.setIsWishlisted(false);
                imgLike.setImageResource(R.drawable.unlike);

            }
            else {
                product.setIsWishlisted(false);

                adapterSimilarProducts.notifyDataSetChanged();
            }
        } else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
            isProductDes=false;

        }
    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {

    }

    @Override
    public void getResponseError(String response) {
        isProductDes=false;
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
            case R.id.tvSeeMore:
                Bundle bundle = new Bundle();
                bundle.putString(C.CAT_ID, "" + product.getId());
                bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCT_DESC);
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PRODUCT_LISTING, bundle);
                break;
            case R.id.rlLike:
                isProductDes=true;
                WishListRequest wishListRequest = new WishListRequest();
                wishListRequest.setProductId(productDes.getId());
                wishListRequest.setUuid(Util.id(getActivity()));
                if(productDes.getIsWishlisted()) {
                    updateWishList(wishListRequest, false);
                }
                else {
                    updateWishList(wishListRequest, true);

                }
                break;

            case R.id.btnAddToCart:

                if(!productDes.getIsInCart()) {
                    isProductDes = true;
                    product = productDes;

                    if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                        createOrder();
                    } else {
                        addToCart();
                    }
                }
                else {
                    util.setSnackbarMessage(getActivity(), getString(R.string.product_already_in_cart), rlView, true);

                }
                break;
            case R.id.btnBuyNow:
                Bundle bundle1=new Bundle();
                bundle1.putInt(C.SOURCE,C.FRAGMENT_PRODUCT_DESC);
                ((ActivityContainer)getActivity()).fragmnetLoader(C.FRAGMENT_CART,bundle1);
                break;
        }
    }
}
