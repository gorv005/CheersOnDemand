package com.cheersondemand.presenter.home;

import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.authentication.CategoryRequest;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface IHomeViewPresenterPresenter {

    public void getCategories(CategoryRequest uuid);
    public void getCategories(String uuid);


    //LANDING SCREEN
    public void getProductWithCategories(String uuid);
    public void getProductWithCategories(String token,String uuid);


    public void getBrands(String auth,CategoryRequest uuid);


    void onDestroy();

    interface IHomeView {
        public void getProductWithCategoriesSuccess(ProductsWithCategoryResponse response);

        public void getResponseSuccess(CategoriesResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
