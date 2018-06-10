package com.cheersondemand.presenter;

import android.content.Context;

import com.cheersondemand.intractor.HomeViewIntractorImpl;
import com.cheersondemand.intractor.IHomeViewIntractor;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.CategoryRequest;


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
    public void getCategories(CategoryRequest uuid) {
        if (mView != null) {

            iHomeViewIntractor.getCategories(uuid, this);
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
