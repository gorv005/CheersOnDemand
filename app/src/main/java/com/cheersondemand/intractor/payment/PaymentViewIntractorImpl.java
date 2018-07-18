package com.cheersondemand.intractor.payment;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.payment.PaymentRequest;
import com.cheersondemand.model.payment.PaymentResponse;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class PaymentViewIntractorImpl implements IPaymentViewIntractor {


    @Override
    public void paymentOrder(String token,PaymentRequest paymentRequest,final OnFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().paymentOrder(new ResponseResolver<PaymentResponse>() {
                @Override
                public void onSuccess(PaymentResponse r, Response response) {
                    listener.onPaymentSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        PaymentResponse response= gson.fromJson(msg,PaymentResponse.class);
                        listener.onPaymentSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,paymentRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
