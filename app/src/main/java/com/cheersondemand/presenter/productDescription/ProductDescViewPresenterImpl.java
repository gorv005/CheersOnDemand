package com.cheersondemand.presenter.productDescription;

import android.content.Context;

import com.cheersondemand.intractor.productDescription.IProductDescIntractor;
import com.cheersondemand.intractor.productDescription.ProductDescriptionViewIntractorImpl;
import com.cheersondemand.model.productdescription.SimilarProductsResponse;


public class ProductDescViewPresenterImpl implements IProductDescViewPresenter, IProductDescIntractor.OnLoginFinishedListener {

    IProductDescView mView;
    Context context;
    IProductDescIntractor iProductDescIntractor;

    public ProductDescViewPresenterImpl(IProductDescView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iProductDescIntractor = new ProductDescriptionViewIntractorImpl();

    }


    @Override
    public void onSuccess(SimilarProductsResponse Response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getResponseSuccess(Response);
        }
    }

    @Override
    public void onError(String response) {
        if (mView != null) {
            mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }


    @Override
    public void getSimilarProducts(String productsId, String uuid, String page, String per_page) {
        if (mView != null) {
            mView.showProgress();

            iProductDescIntractor.getSimilarProducts(productsId,uuid,page,per_page, this);
        }
    }

    @Override
    public void getSimilarProducts(String token, String productsId, String uuid, String page, String per_page) {
        if (mView != null) {
            mView.showProgress();

            iProductDescIntractor.getSimilarProducts(token,productsId,uuid,page,per_page, this);
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
