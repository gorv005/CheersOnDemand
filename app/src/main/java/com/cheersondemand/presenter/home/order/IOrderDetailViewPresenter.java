package com.cheersondemand.presenter.home.order;

import com.cheersondemand.model.order.orderdetail.OrderListResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IOrderDetailViewPresenter {


    public void getOrderList(String token, String userId);

    void onDestroy();

    interface IOrderDetailView {

        void onSuccessOrderList(OrderListResponse response);

        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
