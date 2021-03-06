package com.cheersondemand.intractor.home;

import android.content.Context;

import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategoryResponse;
import com.cheersondemand.model.deals.DealsResponse;

import java.util.List;

/**
 * Created by AB on 7/31/2017.
 */

public interface IHomeViewIntractor {
    interface OnLoginFinishedListener {
        void onSuccess(CategoriesResponse categoriesResponse);
        void onSuccessBrand(BrandResponse categoriesResponse);
        void onSuccessSubCat(SubCategoryResponse categoriesResponse);
        public void onSuccessDealsResponse(DealsResponse response);

        void onProductWithCategorySuccess(ProductsWithCategoryResponse response);

        void onError(String response);
        Context getAPPContext();
    }
    public void getCategories(String uuid, OnLoginFinishedListener listener);
    public void getDeals(boolean isAuth,String token,String uuid, OnLoginFinishedListener listener);

    public void getCategories(boolean isAuth ,String token,String uuid, String with_subcategory,OnLoginFinishedListener listener);
    public void getSubCategories(boolean isAuth , String token, List<Integer> id,String uuid, OnLoginFinishedListener listener);

    public void getBrands(boolean isAuth,String auth, String uuid, OnLoginFinishedListener listener);

    //LANDING SCREEN
    public void getProductsWithCategories(String uuid, OnLoginFinishedListener listener);
    public void getProductsWithCategories(String token,String uuid, OnLoginFinishedListener listener);


}
