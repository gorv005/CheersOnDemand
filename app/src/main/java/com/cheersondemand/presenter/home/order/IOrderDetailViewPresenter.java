package com.cheersondemand.presenter.home.order;

import com.cheersondemand.model.order.orderdetail.CancelOrderRequest;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IOrderDetailViewPresenter {


    public void getOrderList(String token, String userId);
    public void getCartList(String token,String user_id, String order_id, String uuid);
    public void reorderOrder(String token, String user_id, String order_id);
    public void cancelOrder(String token, String user_id, String order_id, CancelOrderRequest cancelOrderRequest);

    void onDestroy();

    interface IOrderDetailView {

        void onSuccessOrderList(OrderListResponse response);
        public void getCartListSuccess(UpdateCartResponse response);
        void onSuccessReorderList(OrderListResponse Response);
        void onSuccessCancelOrder(OrderListResponse Response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
