package com.cheersondemand.intractor.orders;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.order.orderdetail.CancelOrderRequest;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class OrderDetailViewIntractorImpl implements IOrderDetailViewIntractor {



    @Override
    public void getOrderList(String token, String userId, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getOrderList(new ResponseResolver<OrderListResponse>() {
                @Override
                public void onSuccess(OrderListResponse r, Response response) {
                    listener.onSuccessOrderList(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        OrderListResponse response= gson.fromJson(msg,OrderListResponse.class);
                        listener.onSuccessOrderList(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,userId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getCartList( String token, String user_id, String order_id, String uuid,boolean isFromPayment, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCartList(new ResponseResolver<UpdateCartResponse>() {
                @Override
                public void onSuccess(UpdateCartResponse updateProductQuantityResponse, Response response) {
                    listener.onSuccessCartList(updateProductQuantityResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){
                        try {
                        Gson gson=new Gson();
                        UpdateCartResponse response= gson.fromJson(msg,UpdateCartResponse.class);

                        listener.onSuccessCartList(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,user_id,order_id,uuid,isFromPayment);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void reorderOrder(String token, String user_id, String order_id, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().reorderOrder(new ResponseResolver<OrderListResponse>() {
                @Override
                public void onSuccess(OrderListResponse r, Response response) {
                    listener.onSuccessReorderList(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                        Gson gson = new Gson();
                        OrderListResponse response = gson.fromJson(msg, OrderListResponse.class);
                        listener.onSuccessReorderList(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id,order_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelOrder(String token, String user_id, String order_id, CancelOrderRequest cancelOrderRequest, final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().cancelOrder(new ResponseResolver<OrderListResponse>() {
                @Override
                public void onSuccess(OrderListResponse r, Response response) {
                    listener.onSuccessCancelOrder(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                        Gson gson = new Gson();
                        OrderListResponse response = gson.fromJson(msg, OrderListResponse.class);
                        listener.onSuccessCancelOrder(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, token, user_id,order_id,cancelOrderRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
