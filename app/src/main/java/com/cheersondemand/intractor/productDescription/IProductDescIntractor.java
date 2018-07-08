package com.cheersondemand.intractor.productDescription;

import android.content.Context;

import com.cheersondemand.model.productdescription.SimilarProductsResponse;


/**
 * Created by AB on 7/31/2017.
 */

public interface IProductDescIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(SimilarProductsResponse Response);
        void onError(String response);
        Context getAPPContext();
    }
    public void getSimilarProducts(String token,String productsId,String uuid,String page,String per_page, OnLoginFinishedListener listener);

    public void getSimilarProducts(String productsId,String uuid,String page,String per_page, OnLoginFinishedListener listener);


}
