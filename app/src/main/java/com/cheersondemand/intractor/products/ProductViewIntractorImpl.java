package com.cheersondemand.intractor.products;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.productList.ProductListResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Response;

/**
 * Created by AB on 7/31/2017.
 */

public class ProductViewIntractorImpl implements IProductsViewIntractor {
    @Override
    public void getProductList(boolean isAuthUser, String token, String uuid, String page, String per_page,final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getProductList(new ResponseResolver<ProductListResponse>() {
                @Override
                public void onSuccess(ProductListResponse r, Response response) {
                    listener.onProductListSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        ProductListResponse response= gson.fromJson(msg,ProductListResponse.class);
                        listener.onProductListSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,uuid,page,per_page);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getProductList(boolean isAuthUser, String token, String uuid, String page, String per_page, String cat_id,
                               String from, String to, String orderBy, String orderField, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getProductList(new ResponseResolver<ProductListResponse>() {
                @Override
                public void onSuccess(ProductListResponse r, Response response) {
                    listener.onProductListSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        ProductListResponse response= gson.fromJson(msg,ProductListResponse.class);
                        listener.onProductListSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,uuid,page,per_page,cat_id,from,to,orderBy,orderField);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAllProducts(boolean isAuthUser, String token, String uuid, String page, String per_page, String from, String to, String orderBy, String orderField, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getAllProducts(new ResponseResolver<ProductListResponse>() {
                @Override
                public void onSuccess(ProductListResponse r, Response response) {
                    listener.onProductListSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        ProductListResponse response= gson.fromJson(msg,ProductListResponse.class);
                        listener.onProductListSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,uuid,page,per_page,from,to,orderBy,orderField);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAllProductsFilter(boolean isAuthUser, String token, List<Integer> category_id,
                                     String uuid, String page, String per_page, String from, String to,
                                     String orderBy, String orderField,final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getAllProductsFilter(new ResponseResolver<ProductListResponse>() {
                @Override
                public void onSuccess(ProductListResponse r, Response response) {
                    listener.onProductListSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        ProductListResponse response= gson.fromJson(msg,ProductListResponse.class);
                        listener.onProductListSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,category_id,uuid,page,per_page,from,to,orderBy,orderField);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getAllProductsFilter(boolean isAuthUser, String token, List<Integer> category_id,
                                     List<Integer> brand_id,List<Integer> sub_cat_id, String uuid, String page, String per_page,
                                     String from, String to, String orderBy, String orderField, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getAllProductsFilter(new ResponseResolver<ProductListResponse>() {
                @Override
                public void onSuccess(ProductListResponse r, Response response) {
                    listener.onProductListSuccess(r);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        ProductListResponse response= gson.fromJson(msg,ProductListResponse.class);
                        listener.onProductListSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuthUser,token,category_id,brand_id,sub_cat_id,uuid,page,per_page,from,to,orderBy,orderField);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
