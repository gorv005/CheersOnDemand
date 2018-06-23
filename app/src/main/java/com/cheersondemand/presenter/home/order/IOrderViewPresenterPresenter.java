package com.cheersondemand.presenter.home.order;

import com.cheersondemand.model.order.addtocart.AddToCartRequest;
import com.cheersondemand.model.order.addtocart.AddToCartResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.cheersondemand.model.order.CreateOrderResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartRequest;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface IOrderViewPresenterPresenter {

    public void createOrder(String user_id,GenRequest uuid);
    public void createOrder(String token,String user_id,GenRequest uuid);

    public void addToCart(String user_id, String order_id, AddToCartRequest addToCartRequest);
    public void addToCart(String token,String user_id, String order_id, AddToCartRequest addToCartRequest);

    public void updateCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest);
    public void updateCart(String token,String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest);

    public void removeItemFromCart(String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest);
    public void removeItemFromCart(String token,String user_id, String order_id, UpdateCartRequest updateProductQuantityRequest);

    public void getCartList(String user_id, String order_id, String uuid);
    public void getCartList(String token,String user_id, String order_id, String uuid);

    void onDestroy();

    interface IOrderView {
        public void getCreateOrderSuccess(CreateOrderResponse response);
        public void getAddToCartSucess(AddToCartResponse response);
        public void getUpdateCartSuccess(UpdateCartResponse response);
        public void getRemoveItemFromCartSuccess(UpdateCartResponse response);
        public void getCartListSuccess(UpdateCartResponse response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }


}
