package com.cheersondemand.intractor.orders;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
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

                        Gson gson=new Gson();
                        OrderListResponse response= gson.fromJson(msg,OrderListResponse.class);
                        listener.onSuccessOrderList(response);

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
}
