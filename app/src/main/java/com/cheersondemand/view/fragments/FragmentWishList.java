package com.cheersondemand.view.fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.model.wishlist.WishListDataResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.adapter.products.AdapterProducts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWishList extends Fragment implements IOrderViewPresenterPresenter.IOrderView {


    @BindView(R.id.rvProductsList)
    RecyclerView rvProductsList;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    Unbinder unbinder;
    AdapterProducts adapterProducts;
    List<AllProduct> allProductList;
    WishListDataResponse productListResponse;
    @BindView(R.id.tvNoProduct)
    TextView tvNoProduct;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    Util util;
    AllProduct product;
    private GridLayoutManager lLayout;
    private int productPos;
    private int secPos;
    private boolean isAdd;

    public FragmentWishList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productListResponse = (WishListDataResponse) getArguments().getSerializable(C.PRODUCT_LIST);
        //  allProductList=productListResponse.getData();
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());


        util = new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lLayout = new GridLayoutManager(getActivity(), 2);
        rvProductsList.setLayoutManager(lLayout);
        rvProductsList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvProductsList.setItemAnimator(new DefaultItemAnimator());
        // showWishlist();
    }


    /* void  showWishlist(){
         if(allProductList!=null && allProductList.size()>0) {
             adapterProducts = new AdapterProducts(allProductList, getActivity());
             rvProductsList.setAdapter(adapterProducts);
         }
     }*/
    @Override
    public void onResume() {
        super.onResume();
        ActivityContainer.tvTitle.setText(R.string.wishList);
        getWishList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void getWishList() {
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            iOrderViewPresenterPresenter.getWishList(false, "", id, Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.getWishList(true, token, id, Util.id(getActivity()));
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

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {
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
        adapterProducts.notifyDataSetChanged();
    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        try {
            if (response.getSuccess()) {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, false);

                product.setCartQunatity(null);
                product.setIsInCart(false);
                allProductList.set(productPos, product);
                adapterProducts.notifyDataSetChanged();
                if(response.getData()==null){
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
                product.setIsWishlisted(true);
                adapterProducts.notifyDataSetChanged();

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

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
                product.setIsWishlisted(false);
                allProductList.remove(productPos);
                if (allProductList.size() == 0) {
                    tvNoProduct.setVisibility(View.VISIBLE);
                }
                adapterProducts.notifyDataSetChanged();

            } else {
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getWishListSuccess(WishListDataResponse response) {
        try {
            if (response.getSuccess()) {
                if (response.getData() != null && response.getData().size() > 0) {
                    allProductList = response.getData();
                    adapterProducts = new AdapterProducts(allProductList, getActivity());
                    rvProductsList.setAdapter(adapterProducts);

                } else {
                    tvNoProduct.setVisibility(View.VISIBLE);

                }
            } else {
                tvNoProduct.setVisibility(View.VISIBLE);
                util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, rlView, true);

    }

    @Override
    public void showProgress() {
        util.showDialog(C.MSG, getActivity());

    }

    @Override
    public void hideProgress() {
        util.hideDialog();

    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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

}
