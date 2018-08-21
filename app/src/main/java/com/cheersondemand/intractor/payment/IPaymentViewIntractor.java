package com.cheersondemand.intractor.payment;

import android.content.Context;

import com.cheersondemand.model.payment.PaymentRequest;
import com.cheersondemand.model.payment.PaymentResponse;

/**
 * Created by AB on 7/31/2017.
 */

public interface IPaymentViewIntractor {
    interface OnFinishedListener {
        void onPaymentSuccess(PaymentResponse response);
        void onError(String response);
        Context getAPPContext();
    }
    public void paymentOrder(String token,PaymentRequest  paymentRequest, OnFinishedListener listener);

    public void retryPayment(String token,String userId,String OrderId,PaymentRequest  paymentRequest, OnFinishedListener listener);

}
