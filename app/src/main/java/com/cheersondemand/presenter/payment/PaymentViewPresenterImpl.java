package com.cheersondemand.presenter.payment;

import android.content.Context;

import com.cheersondemand.intractor.payment.IPaymentViewIntractor;
import com.cheersondemand.intractor.payment.PaymentViewIntractorImpl;
import com.cheersondemand.model.payment.PaymentRequest;
import com.cheersondemand.model.payment.PaymentResponse;


public class PaymentViewPresenterImpl implements IPaymentViewPresenter, IPaymentViewIntractor.OnFinishedListener {

    IPaymentView mView;
    Context context;
    IPaymentViewIntractor  iPaymentViewIntractor ;

    public PaymentViewPresenterImpl(IPaymentView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iPaymentViewIntractor = new PaymentViewIntractorImpl();

    }

    @Override
    public void onPaymentSuccess(PaymentResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onPaymentSuccess(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getPaymentError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }



    @Override
    public void orderPayment(String token, PaymentRequest paymentRequest) {
        if (mView != null) {
            mView.showProgress();
            iPaymentViewIntractor.paymentOrder(token,paymentRequest, this);
        }
    }

    @Override
    public void retryPayment(String token, String userId, String OrderId, PaymentRequest paymentRequest) {
        if (mView != null) {
            mView.showProgress();
            iPaymentViewIntractor.retryPayment(token,userId,OrderId,paymentRequest, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
