package com.cheersondemand.presenter.products;

import android.content.Context;

import com.cheersondemand.intractor.products.IProductsViewIntractor;
import com.cheersondemand.intractor.products.ProductViewIntractorImpl;
import com.cheersondemand.model.productList.ProductListResponse;


public class ProductViewPresenterImpl implements IProductViewPresenter, IProductsViewIntractor.OnLoginFinishedListener {

    IProductView mView;
    Context context;
    IProductsViewIntractor iProductsViewIntractor;

    public ProductViewPresenterImpl(IProductView mView, Context context) {
        this.mView = mView;
        this.context = context;
        iProductsViewIntractor = new ProductViewIntractorImpl();

    }




    @Override
    public void onProductListSuccess(ProductListResponse Response) {
        if (mView != null) {
            //mView.hideProgress();
            mView.getProductListSuccess(Response);
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
    public void getProductList(boolean isAuthUser, String token, String uuid, String page, String per_page) {
        if (mView != null) {

            iProductsViewIntractor.getProductList(isAuthUser,token,uuid,page,per_page, this);
        }
    }

    @Override
    public void getProductList(boolean isAuthUser, String token, String uuid, String page, String per_page, String cat_id, String from, String to, String orderBy, String orderField) {
        if (mView != null) {

            iProductsViewIntractor.getProductList(isAuthUser,token,uuid,page,per_page, cat_id,from,to,orderBy,orderField,this);
        }
    }

    @Override
    public void getAllProducts(boolean isAuthUser, String token, String uuid, String page, String per_page, String from, String to, String orderBy, String orderField) {
        if (mView != null) {

            iProductsViewIntractor.getAllProducts(isAuthUser,token,uuid,page,per_page,from,to,orderBy,orderField,this);
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
