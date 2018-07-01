package com.cheersondemand.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.productList.ProductListResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.presenter.products.IProductViewPresenter;
import com.cheersondemand.presenter.products.ProductViewPresenterImpl;
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
public class FragmentProductsListing extends Fragment implements IProductViewPresenter.IProductView,IOrderViewPresenterPresenter.IOrderView {


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

    AdapterProducts adapterProducts;
    List<AllProduct> allProductList;
    int page = 1;
    int perPage = 10;
    @BindView(R.id.rlView)
    RelativeLayout rlView;
    private GridLayoutManager lLayout;
    String catId;
    long from=0,to=5000;
     String orderBy="desc";
     String orderField="created_at";
     int source;
    private int productPos;
    private int secPos;
    private boolean isAdd;
    AllProduct product;

    public FragmentProductsListing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new Util();
        iProductViewPresenter = new ProductViewPresenterImpl(this, getActivity());
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());

        catId=getArguments().getString(C.CAT_ID);
        source=getArguments().getInt(C.SOURCE);
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
        rvProductsList.setHasFixedSize(true);
        rvProductsList.setLayoutManager(lLayout);
        if(source==C.FRAGMENT_CATEGORIES) {
            getProductsList(page, perPage, catId, "" + from, "" + to, orderBy, orderField);
        }
        else if(source==C.FRAGMENT_PRODUCTS_HOME){
            getAllProducts(page, perPage, "" + from, "" + to, orderBy, orderField);

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
    void getProductsList(int page, int perPage,String catId,String from ,String to,String orderBy,String orderField) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getProductList(false, "", Util.id(getActivity()), "" + page, "" + perPage,catId,from,to,orderBy,orderField);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getProductList(true, token, Util.id(getActivity()), "" + page, "" + perPage,catId,from,to,orderBy,orderField);
        }
    }


    void getAllProducts(int page, int perPage,String from ,String to,String orderBy,String orderField) {

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            iProductViewPresenter.getAllProducts(false, "", Util.id(getActivity()), "" + page, "" + perPage,from,to,orderBy,orderField);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iProductViewPresenter.getAllProducts(true, token, Util.id(getActivity()), "" + page, "" + perPage,from,to,orderBy,orderField);
        }
    }

    @Override
    public void getProductListSuccess(ProductListResponse response) {
        if (response.getSuccess()) {
            allProductList = response.getData();
            adapterProducts = new AdapterProducts(allProductList, getActivity());
            rvProductsList.setAdapter(adapterProducts);
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), rlView, true);

        }
    }



    public void addToCart(int secPos,int pos,boolean isAdd) {
        productPos= pos;
        this.secPos=secPos;
        this.isAdd=isAdd;
        if (allProductList != null && allProductList.size() > 0) {

            product =allProductList.get(pos);
            if (SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID) == null) {
                createOrder();
            } else {
                addToCart();
            }
        }


    }

    void createOrder(){
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id =""+ SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iOrderViewPresenterPresenter.createOrder(id, new GenRequest(Util.id(getActivity())));
        } else {
            String id = ""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.createOrder(token, id, new GenRequest(Util.id(getActivity())));
        }
    }

    void addToCart(){
        AddToCartRequest addToCartRequest=new AddToCartRequest();
        addToCartRequest.setUuid(Util.id(getActivity()));
        addToCartRequest.setProductId(product.getId());
        if(product.getCartQunatity()==null || product.getCartQunatity().equalsIgnoreCase("0")) {
            addToCartRequest.setQuantity(1);
        }
        else {
            addToCartRequest.setQuantity(Integer.parseInt(product.getCartQunatity()) + 1);

        }
        String  order_id=SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id =""+ SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iOrderViewPresenterPresenter.addToCart(id,order_id,addToCartRequest);
        }
        else {
            String id = ""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.addToCart(token,id,order_id,addToCartRequest);

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
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,true );

        }
    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,false );
            updateCart();
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,true );

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
    void updateCart(){
        int q;
        if(product.getCartQunatity()==null || product.getCartQunatity().equalsIgnoreCase("0")) {
            product.setCartQunatity("1");
            product.setIsInCart(true);

        }
        else {
            if(isAdd) {
                q =Integer.parseInt(product.getCartQunatity()) + 1;
            }
            else {
                q= Integer.parseInt(product.getCartQunatity()) - 1;

            }
            product.setCartQunatity(""+q);
            if(q==0){
                product.setIsInCart(false);

            }
            else {
                product.setIsInCart(true);

            }
        }

        allProductList.set(productPos,product);
        adapterProducts.notifyDataSetChanged();
    }
    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,false );

            product.setCartQunatity(null);
            product.setIsInCart(false);
            allProductList.set(productPos,product);
            adapterProducts.notifyDataSetChanged();

        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,true );

        }
    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {

    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,false );
            product.setIsWishlisted(true);
            adapterProducts.notifyDataSetChanged();

        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,true );

        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,false );
            product.setIsWishlisted(false);
            adapterProducts.notifyDataSetChanged();

        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),rlView,true );

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
}
