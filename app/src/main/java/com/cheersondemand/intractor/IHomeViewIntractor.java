package com.cheersondemand.intractor;

import android.content.Context;

import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.CategoryRequest;
import com.cheersondemand.model.ProductsWithCategoryResponse;

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
    public void getCategories(CategoryRequest uuid, OnLoginFinishedListener listener);

    public void getCategories(String uuid, OnLoginFinishedListener listener);

    public void getBrands(String auth, CategoryRequest uuid,OnLoginFinishedListener listener);

    //LANDING SCREEN
    public void getProductsWithCategories(String uuid, OnLoginFinishedListener listener);


}
