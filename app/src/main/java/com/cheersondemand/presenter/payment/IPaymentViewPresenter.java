package com.cheersondemand.presenter.payment;

import com.cheersondemand.model.payment.PaymentRequest;
import com.cheersondemand.model.payment.PaymentResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IPaymentViewPresenter {

    public void orderPayment(String token, PaymentRequest paymentRequest);
    public void retryPayment(String token,String userId,String OrderId,PaymentRequest  paymentRequest);


    void onDestroy();

    interface IPaymentView {
        void onPaymentSuccess(PaymentResponse response);

        public void getPaymentError(String response);
        void showProgress();

        void hideProgress();
    }

}
