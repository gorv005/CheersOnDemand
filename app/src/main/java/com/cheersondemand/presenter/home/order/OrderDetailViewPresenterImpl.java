package com.cheersondemand.presenter.home.order;

import android.content.Context;

import com.cheersondemand.intractor.orders.IOrderDetailViewIntractor;
import com.cheersondemand.intractor.orders.OrderDetailViewIntractorImpl;
import com.cheersondemand.model.order.orderdetail.CancelOrderRequest;
import com.cheersondemand.model.order.orderdetail.OrderListResponse;
import com.cheersondemand.model.order.updatecart.UpdateCartResponse;


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
    public void onSuccessCartList(UpdateCartResponse response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getCartListSuccess(response);
        }
    }

    @Override
    public void onSuccessReorderList(OrderListResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccessReorderList(Response);
        }
    }

    @Override
    public void onSuccessCancelOrder(OrderListResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccessCancelOrder(Response);
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
    public void getCartList(String token, String user_id, String order_id, String uuid) {
        if (mView != null) {
            mView.showProgress();
            iOrderDetailViewIntractor.getCartList(token,user_id,order_id,uuid, this);
        }
    }

    @Override
    public void reorderOrder(String token, String user_id, String order_id) {
        if (mView != null) {
            mView.showProgress();
            iOrderDetailViewIntractor.reorderOrder(token,user_id,order_id, this);
        }
    }

    @Override
    public void cancelOrder(String token, String user_id, String order_id, CancelOrderRequest cancelOrderRequest) {
        if (mView != null) {
            mView.showProgress();
            iOrderDetailViewIntractor.cancelOrder(token,user_id,order_id,cancelOrderRequest, this);
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
