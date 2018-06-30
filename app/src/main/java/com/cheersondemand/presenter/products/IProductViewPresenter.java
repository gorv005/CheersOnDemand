package com.cheersondemand.presenter.products;


import com.cheersondemand.model.productList.ProductListResponse;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface IProductViewPresenter {

    public void getProductList(boolean isAuthUser ,String token,String uuid,String page,String per_page);
    public void getProductList(boolean isAuthUser ,String token,String uuid,String page,String per_page,String cat_id,String from,String to, String orderBy,String orderField);


    void onDestroy();

    interface IProductView {
        public void getProductListSuccess(ProductListResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}