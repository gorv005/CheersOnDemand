package com.cheersondemand.presenter;

import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.CategoryRequest;

/**
 * Created by GAURAV on 5/30/2018.
 */

public interface IHomeViewPresenterPresenter {

    public void getCategories(CategoryRequest uuid);
    public void getBrands(String auth,CategoryRequest uuid);


    void onDestroy();

    interface IHomeView {
        public void getResponseSuccess(CategoriesResponse response);
        public void getResponseError(String response);
        void showProgress();

        void hideProgress();
    }

}
