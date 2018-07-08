package com.cheersondemand.presenter.home;

import com.cheersondemand.model.BrandResponse;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.SubCategoryResponse;

import java.util.List;

/**
 * Created by AB on 5/30/2018.
 */

public interface IHomeViewPresenterPresenter {

    public void getCategories(String uuid);
    public void getCategories(boolean isAuth,String token,String uuid);

    public void getSubCategories(boolean isAuth , String token, List<Integer> id, String uuid);

    //LANDING SCREEN
    public void getProductWithCategories(String uuid);
    public void getProductWithCategories(String token,String uuid);


    public void getBrands(boolean isAuth,String auth, String uuid);


    void onDestroy();

    interface IHomeView {
        public void getProductWithCategoriesSuccess(ProductsWithCategoryResponse response);
        public void getBrandResponseSuccess(BrandResponse response);
        public void getResponseSuccessSubCat(SubCategoryResponse response);

        public void getResponseSuccess(CategoriesResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
