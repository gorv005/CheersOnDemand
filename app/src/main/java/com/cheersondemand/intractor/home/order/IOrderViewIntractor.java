package com.cheersondemand.intractor.home.order;

import android.content.Context;

import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;

/**
 * Created by GAURAV on 7/31/2017.
 */

public interface IOrderViewIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(CreateOrderResponse response);
        void onSuccessAddToCart(AddToCartResponse response);
        void onSuccessUpdateCart(UpdateCartResponse response);
        void onSuccessRemoveCart(UpdateCartResponse response);
        void onSuccessCartList(UpdateCartResponse response);

        void onError(String response);
        Context getAPPContext();
    }
    public void createOrder(String user_id, GenRequest uuid, OnLoginFinishedListener listener);

    public void createOrder(String token,String user_id,GenRequest uuid, OnLoginFinishedListener listener);

    public void addToCart(String user_id, String order_id, AddToCartRequest addToCartRequest, OnLoginFinishedListener listener);

    public void addToCart(String token,String user_id, String order_id, AddToCartRequest addToCartRequest, OnLoginFinishedListener listener);

    public void updateCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, OnLoginFinishedListener listener);

    public void updateCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, OnLoginFinishedListener listener);

    public void removeItemFromCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, OnLoginFinishedListener listener);

    public void removeItemFromCart(String token, String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest, OnLoginFinishedListener listener);

    public void getCartList(String user_id, String order_id, String uuid, OnLoginFinishedListener listener);

    public void getCartList(String token, String user_id, String order_id, String uuid, OnLoginFinishedListener listener);
}
