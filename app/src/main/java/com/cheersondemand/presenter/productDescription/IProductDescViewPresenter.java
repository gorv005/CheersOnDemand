package com.cheersondemand.presenter.productDescription;


import com.cheersondemand.model.productdescription.SimilarProductsResponse;

/**
 * Created by AB on 5/30/2018.
 */

public interface IProductDescViewPresenter {

    public void getSimilarProducts(String productsId, String uuid, String page, String per_page);
    public void getSimilarProducts(String token,String productsId, String uuid, String page, String per_page);


    void onDestroy();

    interface IProductDescView {
        public void getResponseSuccess(SimilarProductsResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
