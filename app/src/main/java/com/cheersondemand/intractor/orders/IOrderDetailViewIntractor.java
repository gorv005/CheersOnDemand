package com.cheersondemand.intractor.orders;

import android.content.Context;

import com.cheersondemand.model.order.orderdetail.CancelOrderRequest;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface IOrderDetailViewIntractor {
    interface OnFinishedListener {
        void onSuccessOrderList(OrderListResponse Response);
        void onSuccessCartList(UpdateCartResponse response);
        void onSuccessReorderList(OrderListResponse Response);
        void onSuccessCancelOrder(OrderListResponse Response);


        void onError(String response);
        Context getAPPContext();
    }

    public void getOrderList(String token, String userId, OnFinishedListener listener);
    public void getCartList(String token, String user_id, String order_id, String uuid, OnFinishedListener listener);
    public void reorderOrder(String token, String user_id, String order_id, OnFinishedListener listener);
    public void cancelOrder(String token, String user_id, String order_id, CancelOrderRequest cancelOrderRequest, OnFinishedListener listener);


}
