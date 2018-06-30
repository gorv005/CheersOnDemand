package com.cheersondemand.intractor.home;

import android.content.Context;

import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.authentication.GenRequest;

/**
 * Created by GAURAV on 7/31/2017.
 */

public interface IHomeViewIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(CategoriesResponse categoriesResponse);
        void onProductWithCategorySuccess(ProductsWithCategoryResponse response);

        void onError(String response);
        Context getAPPContext();
    }
    public void getCategories(GenRequest uuid, OnLoginFinishedListener listener);

    public void getCategories(boolean isAuth ,String token,String uuid, OnLoginFinishedListener listener);

    public void getBrands(String auth, GenRequest uuid, OnLoginFinishedListener listener);

    //LANDING SCREEN
    public void getProductsWithCategories(String uuid, OnLoginFinishedListener listener);
    public void getProductsWithCategories(String token,String uuid, OnLoginFinishedListener listener);


}
