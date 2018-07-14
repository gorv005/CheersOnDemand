package com.cheersondemand.presenter.home.order;

import android.content.Context;

import com.cheersondemand.intractor.orders.IOrderDetailViewIntractor;
import com.cheersondemand.intractor.orders.OrderDetailViewIntractorImpl;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;


public class OrderDetailViewPresenterImpl implements IOrderDetailViewPresenter, IOrderDetailViewIntractor.OnFinishedListener {

    IOrderDetailView mView;
    Context context;
    IOrderDetailViewIntractor iOrderDetailViewIntractor;

    public OrderDetailViewPresenterImpl(IOrderDetailView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iOrderDetailViewIntractor = new OrderDetailViewIntractorImpl();

    }




    @Override
    public void onSuccessOrderList(OrderListResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccessOrderList(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }

    @Override
    public void getOrderList(String token, String userId) {
        if (mView != null) {
            mView.showProgress();
            iOrderDetailViewIntractor.getOrderList(token,userId,this);
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
