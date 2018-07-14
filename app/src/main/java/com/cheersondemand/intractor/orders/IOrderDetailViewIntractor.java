package com.cheersondemand.intractor.orders;

import android.content.Context;

import com.cheersondemand.model.order.orderdetail.OrderListResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface IOrderDetailViewIntractor {
    interface OnFinishedListener {
        void onSuccessOrderList(OrderListResponse Response);


        void onError(String response);
        Context getAPPContext();
    }

    public void getOrderList(String token, String userId, OnFinishedListener listener);



}
