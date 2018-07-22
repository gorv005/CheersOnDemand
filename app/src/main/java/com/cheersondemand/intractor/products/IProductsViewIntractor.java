package com.cheersondemand.intractor.products;

import android.content.Context;

import com.cheersondemand.model.productList.ProductListResponse;

import java.util.List;

/**
 * Created by AB on 7/31/2017.
 */

public interface IProductsViewIntractor {
    interface OnLoginFinishedListener {
        void onProductListSuccess(ProductListResponse Response);

        void onError(String response);
        Context getAPPContext();
    }
    public void getProductList(boolean isAuthUser ,String token,String uuid,String page,String per_page, OnLoginFinishedListener listener);

    public void getProductList(boolean isAuthUser ,String token,String uuid,String page,String per_page,String cat_id,String from,String to, String orderBy,String orderField, OnLoginFinishedListener listener);

    public void getAllProducts(boolean isAuthUser ,String token,String uuid,String page,String per_page,String from,String to, String orderBy,String orderField, OnLoginFinishedListener listener);
    public void getAllSimilarProducts(boolean isAuthUser ,String token,String uuid,String page,String per_page,String from,String to, String orderBy,String orderField,String id, OnLoginFinishedListener listener);

    public void getAllProductsFilter(boolean isAuthUser , String token, List<Integer> category_id, String uuid, String page, String per_page, String from, String to, String orderBy, String orderField, OnLoginFinishedListener listener);

    public void getAllProductsFilter(boolean isAuthUser , String token, List<Integer> category_id, List<Integer> brand_id,List<Integer> sub_cat_id,String uuid, String page, String per_page, String from, String to, String orderBy, String orderField, OnLoginFinishedListener listener);

}
