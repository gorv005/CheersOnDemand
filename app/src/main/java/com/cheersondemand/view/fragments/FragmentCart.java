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
import android.widget.LinearLayout;

import com.cheersondemand.R;
import com.cheersondemand.model.coupon.CouponInfoResponse;
import com.cheersondemand.model.coupon.CouponListResponse;
import com.cheersondemand.model.coupon.CouponRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.order.addtocart.CartProduct;
import com.cheersondemand.model.order.addtocart.Order;
import com.cheersondemand.model.order.addtocart.OrderItem;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.cheersondemand.model.wishlist.WishListRequest;
import com.cheersondemand.model.wishlist.WishListResponse;
import com.cheersondemand.presenter.coupon.CouponViewPresenterImpl;
import com.cheersondemand.presenter.coupon.ICouponViewPresenter;
import com.cheersondemand.presenter.home.order.IOrderViewPresenterPresenter;
import com.cheersondemand.presenter.home.order.OrderViewPresenterImpl;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityHome;
import com.cheersondemand.view.adapter.cart.AdapterCartList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCart extends Fragment implements View.OnClickListener, IOrderViewPresenterPresenter.IOrderView,ICouponViewPresenter.ICouponView {


    @BindView(R.id.btnBrowseProduct)
    Button btnBrowseProduct;
    @BindView(R.id.llNoProductInCount)
    LinearLayout llNoProductInCount;
    Unbinder unbinder;
    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;
    @BindView(R.id.LLView)
    LinearLayout LLView;
    private LinearLayoutManager mLinearLayoutManager;
    IOrderViewPresenterPresenter iOrderViewPresenterPresenter;
    ICouponViewPresenter iCouponViewPresenter;
    AdapterCartList adapterCartList;
    Util util;
    CartProduct cartProduct;
    Order order;
    List<OrderItem> orderItemsList;
    int secPos,productPos;
    boolean isAdd;
    OrderItem orderItem;
    public FragmentCart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iOrderViewPresenterPresenter = new OrderViewPresenterImpl(this, getActivity());
        iCouponViewPresenter=new CouponViewPresenterImpl(this,getActivity());
        util=new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBrowseProduct.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCartList.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCartList();

    }

    void getCartList() {
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iOrderViewPresenterPresenter.getCartList(id, order_id, Util.id(getActivity()));
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token =  C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.getCartList(token, id, order_id, Util.id(getActivity()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBrowseProduct:

                ((ActivityHome) getActivity()).setHome();
                break;
        }
    }


    public void removeCoupon(){

        CouponRequest couponRequest=new CouponRequest();
        couponRequest.setUuid(Util.id(getActivity()));
        String order_id = SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        couponRequest.setCartId( order_id);

        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id = "" + SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();

            iCouponViewPresenter.removeCoupon(false,"",couponRequest);
        } else {
            String id = "" + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();
            String token =  C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iCouponViewPresenter.removeCoupon(true,token,couponRequest);
        }
    }


    public void updateCart(int secPos,int productPos, boolean isAdd){
        this.secPos=secPos;
        this.productPos=productPos;
        this.isAdd=isAdd;
        if (orderItemsList != null &&orderItemsList.size()>0) {

            orderItem = orderItemsList.get(productPos);

            UpdateCartRequest updateCartRequest=new UpdateCartRequest();
            updateCartRequest.setUuid(Util.id(getActivity()));
            updateCartRequest.setProductId(orderItem.getProduct().getId());
            updateCartRequest.setIsIncrease(isAdd);
            if(isAdd) {
                updateCartRequest.setQuantity(orderItem.getQuantity() + 1);
                updateProduct(updateCartRequest);
            }
            else {
                if(orderItem.getQuantity()==1){
                    removeProduct(updateCartRequest);
                }
                else {
                    updateCartRequest.setQuantity(orderItem.getQuantity() - 1);
                    updateProduct(updateCartRequest);
                }
            }

        }
    }


    public void wishListUpdate(int secPos,int pos,boolean isAdd) {
        productPos= pos;
        this.secPos=secPos;
        this.isAdd=isAdd;
        if (orderItemsList != null && orderItemsList.size() > 0) {
            orderItem = orderItemsList.get(productPos);

            WishListRequest wishListRequest=new WishListRequest();
            wishListRequest.setProductId(orderItem.getProduct().getId());
            wishListRequest.setUuid(Util.id(getActivity()));

            updateWishList(wishListRequest,isAdd);
        }


    }


    void updateWishList(WishListRequest wishListRequest,boolean status){
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id =""+ SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            if(status) {
                iOrderViewPresenterPresenter.addToWishList(id, wishListRequest);
            }
            else {
                iOrderViewPresenterPresenter.removeFromWishList(id, wishListRequest);

            }
        } else {
            String id = ""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            if(status) {
                iOrderViewPresenterPresenter.addToWishList(token,id, wishListRequest);
            }
            else {
                iOrderViewPresenterPresenter.removeFromWishList(token,id, wishListRequest);

            }
        }
    }
    public void removeProduct(UpdateCartRequest updateCartRequest){
        String  order_id=SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id =""+ SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            iOrderViewPresenterPresenter.removeItemFromCart(id,order_id, updateCartRequest);
        } else {
            String id = ""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.removeItemFromCart(token, id,order_id, updateCartRequest);
        }
    }
    void updateProduct(UpdateCartRequest updateCartRequest){
        String  order_id=SharedPreference.getInstance(getActivity()).getString(C.ORDER_ID);
        if (SharedPreference.getInstance(getActivity()).getBoolean(C.IS_LOGIN_GUEST)) {
            String id =""+ SharedPreference.getInstance(getActivity()).geGuestUser(C.GUEST_USER).getId();
            iOrderViewPresenterPresenter.updateCart(id,order_id, updateCartRequest);
        } else {
            String id = ""+SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getUser().getId();

            String token = C.bearer + SharedPreference.getInstance(getActivity()).getUser(C.AUTH_USER).getData().getToken().getAccessToken();
            iOrderViewPresenterPresenter.updateCart(token, id,order_id, updateCartRequest);
        }
    }
    @Override
    public void getCreateOrderSuccess(CreateOrderResponse response) {

    }

    @Override
    public void getAddToCartSucess(AddToCartResponse response) {

    }

    @Override
    public void getUpdateCartSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,false );
            cartProduct=response.getData();

            orderItemsList.clear();
            orderItemsList=response.getData().getOrder().getOrderItems();
            adapterCartList.setData(cartProduct,orderItemsList);

            /*for(int i=0;i<response.getData().getOrder().getOrderItems().size();i++) {
                orderItemsList.add(response.getData().getOrder().getOrderItems().get(i));
            }*/
            adapterCartList.notifyDataSetChanged();
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,true );

        }
    }

    @Override
    public void getRemoveItemFromCartSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,false );
            cartProduct=response.getData();
            orderItemsList.clear();
            orderItemsList=response.getData().getOrder().getOrderItems();
            adapterCartList.setData(cartProduct,orderItemsList);

          /*  for(int i=0;i<response.getData().getOrder().getOrderItems().size();i++) {
                orderItemsList.add(response.getData().getOrder().getOrderItems().get(i));
            }*/
            adapterCartList.notifyDataSetChanged();
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,true );

        }
    }

    @Override
    public void getCartListSuccess(UpdateCartResponse response) {
        if (response.getSuccess()) {
            cartProduct=response.getData();
//            adapterCartList.setData(cartProduct);
            orderItemsList=response.getData().getOrder().getOrderItems();
            adapterCartList = new AdapterCartList(cartProduct,orderItemsList, getActivity());
            rvCartList.setAdapter(adapterCartList);
        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(), LLView, true);

        }
    }

    @Override
    public void addTowishListSuccess(WishListResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,false );
            orderItemsList.get(productPos).getProduct().setIsWishlisted(true);
            adapterCartList.notifyDataSetChanged();

        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,true );

        }
    }

    @Override
    public void removeFromWishListSuccess(WishListResponse response) {
        if(response.getSuccess()){
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,false );
            orderItemsList.get(productPos).getProduct().setIsWishlisted(false);

            adapterCartList.notifyDataSetChanged();

        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,true );

        }
    }

    @Override
    public void onSuccessCouponInfo(CouponInfoResponse response) {

    }

    @Override
    public void onSuccessCouponList(CouponListResponse response) {

    }

    @Override
    public void onSuccessCartAfterCoupon(UpdateCartResponse response) {
        if(response.getSuccess()){

           cartProduct=response.getData();
         //   cartProduct.getOrder().setOrderDate("");
            orderItemsList.clear();
            orderItemsList=response.getData().getOrder().getOrderItems();

           /* for (int i=0;i<response.getData().getOrder().getOrderItems().size();i++){
                orderItemsList.add(response.getData().getOrder().getOrderItems().get(i));
                orderItemsList.get(i).setChange("couponremove");
            }*/
            adapterCartList=new AdapterCartList(cartProduct,response.getData().getOrder().getOrderItems(),getActivity());
            rvCartList.setAdapter(adapterCartList);
     //      CartProduct s=cartProduct;
         //   orderItemsList=response.getData().getOrder().getOrderItems();

         //   adapterCartList.setData(cartProduct,orderItemsList);
            adapterCartList.notifyDataSetChanged();

        }
        else {
            util.setSnackbarMessage(getActivity(), response.getMessage(),LLView,true );

        }
    }

    @Override
    public void getResponseError(String response) {
        util.setSnackbarMessage(getActivity(), response, LLView, true);

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
