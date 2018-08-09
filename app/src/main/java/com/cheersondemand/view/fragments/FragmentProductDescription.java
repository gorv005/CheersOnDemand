package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.coupon.CouponInfo;
import com.cheersondemand.model.coupon.CouponInfoResponse;
import com.cheersondemand.model.coupon.CouponListResponse;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.productdescription.Offers;
import com.cheersondemand.model.productdescription.SimilarProductsResponse;
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.coupon.CouponViewPresenterImpl;
import com.cheersondemand.presenter.coupon.ICouponViewPresenter;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.productDescription.IProductDescViewPresenter;
import com.cheersondemand.presenter.productDescription.ProductDescViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.CustomSpannable;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.util.NonScrollListView;
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
public class FragmentProductDescription extends Fragment implements View.OnClickListener, IProductDescViewPresenter.IProductDescView, IOrderViewPresenterPresenter.IOrderView, ICouponViewPresenter.ICouponView {

    @BindView(R.id.imgProduct)
    ImageView imgProduct;
    @BindView(R.id.lvOffers)
    NonScrollListView lvOffers;
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
    AllProduct product, productDes;
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
    @BindView(R.id.imgBack)
    RelativeLayout imgBack;
    @BindView(R.id.ivCheckout)
    ImageView ivCheckout;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.lvDescCheck)
    LinearLayout lvDescCheck;
    List<AllProduct> allProductList;
    Util util;
    ICouponViewPresenter iCouponViewPresenter;
    List<CouponInfo> couponInfoList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tvTitleText)
    TextView tvTitleText;
    @BindView(R.id.tvReadMore)
    TextView tvReadMore;
    private int productPos;
    private int secPos;
    private boolean isAdd;
    private boolean isProductDes = false, isBuyNow = false;
    boolean isReadMore=false;
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
        iCouponViewPresenter = new CouponViewPresenterImpl(this, getActivity());

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
        ((ActivityContainer) getActivity()).hideToolBar();
        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarDrinks.setLayoutManager(horizontalLayout);
        tvSeeMore.setOnClickListener(this);
        rlLike.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        ivCheckout.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        tvReadMore.setOnClickListener(this);
        setDetail();
        initCollapsingToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        getSimilarProducts();
        getCouponList();
        ((ActivityContainer) getActivity()).hideToolBar();

        //  ActivityContainer.tvTitle.setText(productDes.getName());
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
        tvDesc.setText(product.getDescription());

        //  imageLoader.DisplayImage(product.getImage(), imgProduct);
        Util.setImage(getActivity(), product.getImage(), imgProduct);
        if (product.getIsWishlisted()) {
            imgLike.setImageResource(R.drawable.like);
        } else {
            imgLike.setImageResource(R.drawable.unlike);

        }
        if (product.getIsInCart()) {
            btnAddToCart.setText(getString(R.string.added_to_cart));
        } else {
            btnAddToCart.setText(getString(R.string.add_to_cart));

        }
        tvProductName.setText(product.getName());
        tvProductPrice.setText("$" + product.getPrice());
        tvType.setText(product.getSubCategory().getName());
        tvalcohalVol.setText("-");
        tvRegion.setText(product.getRegion());
        tvQuantity.setText("0.0");

        makeTextViewResizable(tvDesc, 3, "See More", true);

    }
    public  void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                Layout l = tvDesc.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
                    if (lines > 0) {
                        if (l.getEllipsisCount(lines - 1) > 0) {
                            Log.d("", "Text is ellipsized");
                            tvReadMore.setVisibility(View.VISIBLE);
                        } else {
                            if(!isReadMore) {
                                tvReadMore.setVisibility(View.GONE);
                            }

                        }
                    }
                }
            /*    ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() > maxLine) {
                   *//* int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);*//*
                    tvReadMore.setVisibility(View.VISIBLE);
                } else {
                   *//* int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);*//*
                    tvReadMore.setVisibility(View.GONE);

                }*/
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new CustomSpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                    //    makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                      //  makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {
        try {
            if (response.getSuccess()) {
                SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, "" + response.getData().getOrder().getId());
                addToCart();
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getCouponList() {
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iCouponViewPresenter.getListOfCoupons(false, "", Util.id(getActivity()), order_id);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iCouponViewPresenter.getListOfCoupons(true, token, Util.id(getActivity()), order_id);
        }
    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);

                if (isProductDes) {
                    btnAddToCart.setText(getString(R.string.added_to_cart));
                    productDes.setIsInCart(true);
                    isProductDes = false;
                } else if (isBuyNow) {
                    btnAddToCart.setText(getString(R.string.added_to_cart));
                    productDes.setIsInCart(true);
                    isProductDes = false;
                    isBuyNow = false;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds
                            gotoCart();

                        }
                    }, 2000);
                } else {
                    updateCart();
                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
                isProductDes = false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
                updateCart();
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

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

        allProductList.set(productPos, product);
        adapterSimilarProducts.notifyDataSetChanged();
    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);

                product.setCartQunatity(null);
                product.setIsInCart(false);
                allProductList.set(productPos, product);
                adapterSimilarProducts.notifyDataSetChanged();
                if (response.getData() == null) {
                    SharedPreference.getInstance(getActivity()).setBoolean(C.CART_HAS_ITEM, false);
                    SharedPreference.getInstance(getActivity()).setString(C.ORDER_ID, null);
                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {

    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
                if (isProductDes) {
                    isProductDes = false;

                    productDes.setIsWishlisted(true);
                    imgLike.setImageResource(R.drawable.like);
                } else {
                    product.setIsWishlisted(true);
                    adapterSimilarProducts.notifyDataSetChanged();
                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
                isProductDes = false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);
                if (isProductDes) {
                    isProductDes = false;
                    productDes.setIsWishlisted(false);
                    imgLike.setImageResource(R.drawable.unlike);

                } else {
                    product.setIsWishlisted(false);

                    adapterSimilarProducts.notifyDataSetChanged();
                }
            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);
                isProductDes = false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWishListSuccess(WishListDataResponse response) {

    }

    @Override
    public void onSuccessCouponInfo(CouponInfoResponse response) {

    }

    @Override
    public void onSuccessCouponList(CouponListResponse response) {
        try {
            if (response.getSuccess()) {
                couponInfoList = response.getData();

                adapterOffers = new AdapterOffers(getActivity(), couponInfoList);
                if (couponInfoList != null && couponInfoList.size() > 0) {
                    lvOffers.setAdapter(adapterOffers);
                    Util.setListViewHeightBasedOnChildren(lvOffers);

                } else {
                    lvOffers.setVisibility(View.GONE);

                }
            } else {
                lvOffers.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessCartAfterCoupon(UpdateCartResponse response) {

    }

    @Override
    public void getResponseError(String response) {
        isProductDes = false;
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void showProgress() {
        //  util.showDialog(C.MSG,getActivity());

    }

    @Override
    public void hideProgress() {
        // util.hideDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSeeMore:
                Bundle bundle = new Bundle();
                bundle.putString(C.CAT_ID, "" + product.getId());
                bundle.putString(C.SUB_CAT_ID, "");

                bundle.putInt(C.SOURCE, C.FRAGMENT_PRODUCT_DESC);
                ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_PRODUCT_LISTING, bundle);
                break;
            case R.id.rlLike:
                isProductDes = true;
                WishListRequest wishListRequest = new WishListRequest();
                wishListRequest.setProductId(productDes.getId());
                wishListRequest.setUuid(Util.id(getActivity()));
                if (productDes.getIsWishlisted()) {
                    updateWishList(wishListRequest, false);
                } else {
                    updateWishList(wishListRequest, true);

                }
                break;

            case R.id.btnAddToCart:

                if (!productDes.getIsInCart()) {
                    isProductDes = true;
                    product = productDes;

                    if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                        createOrder();
                    } else {
                        addToCart();
                    }
                } else {
                    util.setSnackbarMessage(getActivity(), getString(R.string.product_already_in_cart), rlView, true);

                }
                break;
            case R.id.btnBuyNow:

                if (!productDes.getIsInCart()) {
                    isBuyNow = true;
                    product = productDes;

                    if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                        createOrder();
                    } else {
                        addToCart();
                    }
                } else {
                    gotoCart();

                }

                break;
            case R.id.imgBack:
                getActivity().onBackPressed();
                break;
            case R.id.ivCart:
                gotoCart();
                break;
            case R.id.ivCheckout:
                // getActivity().onBackPressed();
                break;
            case R.id.tvReadMore:
                if(!isReadMore) {
                    tvDesc.setMaxLines(Integer.MAX_VALUE);
                    tvDesc.setEllipsize(null);
                    isReadMore=true;
                    tvReadMore.setText(getString(R.string.read_less));
                    tvReadMore.setVisibility(View.VISIBLE);
                }
                else {
                    tvDesc.setMaxLines(3);
                    tvDesc.setEllipsize(TextUtils.TruncateAt.END);
                    isReadMore=false;
                    tvReadMore.setText(getString(R.string.read_more));
                    tvReadMore.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void initCollapsingToolbar() {

        appbar.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    // collapsing.setTitle("hfhfhfhhhh");
                    tvTitleText.setVisibility(View.VISIBLE);

                    tvTitleText.setText(product.getName());
                    isShow = true;
                } else if (isShow) {
                    tvTitleText.setVisibility(View.GONE);
                    collapsing.setTitle(product.getName());
                    isShow = false;
                }
            }
        });
    }

    void gotoCart() {
        Bundle bundle1 = new Bundle();
        bundle1.putInt(C.SOURCE, C.FRAGMENT_PRODUCT_DESC);
        ((ActivityContainer) getActivity()).fragmnetLoader(C.FRAGMENT_CART, bundle1);
    }
}
