package com.cheersondemand.presenter.home;

import android.content.Context;

import com.cheersondemand.intractor.home.HomeViewIntractorImpl;
import com.cheersondemand.intractor.home.IHomeViewIntractor;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.authentication.GenRequest;


public class HomeViewPresenterImpl implements IHomeViewPresenterPresenter, IHomeViewIntractor.OnLoginFinishedListener {

    IHomeView mView;
    Context context;
    IHomeViewIntractor iHomeViewIntractor;

    public HomeViewPresenterImpl(IHomeView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iHomeViewIntractor = new HomeViewIntractorImpl();

    }


    @Override
    public void onSuccess(CategoriesResponse categoriesResponse) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getResponseSuccess(categoriesResponse);
        }
    }

    @Override
    public void onProductWithCategorySuccess(ProductsWithCategoryResponse response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getProductWithCategoriesSuccess(response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }


    @Override
    public void getCategories(GenRequest uuid) {
        if (mView != null) {

            iHomeViewIntractor.getCategories(uuid, this);
        }
    }

    @Override
    public void getCategories(boolean isAuth ,String token,String uuid) {
        if (mView != null) {

            iHomeViewIntractor.getCategories(isAuth,token,uuid, this);
        }
    }

    @Override
    public void getProductWithCategories(String uuid) {
        if (mView != null) {

            iHomeViewIntractor.getProductsWithCategories(uuid, this);
        }
    }

    @Override
    public void getProductWithCategories(String token, String uuid) {
        if (mView != null) {

            iHomeViewIntractor.getProductsWithCategories(token,uuid, this);
        }
    }

    @Override
    public void getBrands(String auth,GenRequest uuid) {
        if (mView != null) {

            iHomeViewIntractor.getBrands(auth,uuid, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
