package com.cheersondemand.intractor.home;

import android.util.Log;

import com.cheersondemand.frameworks.retrofit.ResponseResolver;
import com.cheersondemand.frameworks.retrofit.RestError;
import com.cheersondemand.frameworks.retrofit.WebServicesWrapper;
import com.cheersondemand.model.CategoriesResponse;
import com.cheersondemand.model.ProductsWithCategoryResponse;
import com.cheersondemand.model.authentication.GenRequest;
import com.google.gson.Gson;

import retrofit2.Response;

/**
 * Created by GAURAV on 7/31/2017.
 */

public class HomeViewIntractorImpl implements IHomeViewIntractor {



    @Override
    public void getCategories(GenRequest uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCategories(new ResponseResolver<CategoriesResponse>() {
                @Override
                public void onSuccess(CategoriesResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        CategoriesResponse response= gson.fromJson(msg,CategoriesResponse.class);
                        listener.onSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getCategories(boolean isAuth,String token,String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getCategories(new ResponseResolver<CategoriesResponse>() {
                @Override
                public void onSuccess(CategoriesResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        CategoriesResponse response= gson.fromJson(msg,CategoriesResponse.class);
                        listener.onSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },isAuth,token,uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getBrands(String auth, GenRequest uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getBrands(new ResponseResolver<CategoriesResponse>() {
                @Override
                public void onSuccess(CategoriesResponse signUpResponse, Response response) {
                    listener.onSuccess(signUpResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        CategoriesResponse response= gson.fromJson(msg,CategoriesResponse.class);
                        listener.onSuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },auth,uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getProductsWithCategories(String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getProductsWithCategories(new ResponseResolver<ProductsWithCategoryResponse>() {
                @Override
                public void onSuccess(ProductsWithCategoryResponse productsWithCategoryResponse, Response response) {
                    listener.onProductWithCategorySuccess(productsWithCategoryResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        ProductsWithCategoryResponse response= gson.fromJson(msg,ProductsWithCategoryResponse.class);
                        listener.onProductWithCategorySuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getProductsWithCategories(String token, String uuid, final OnLoginFinishedListener listener) {
        try {

            WebServicesWrapper.getInstance().getProductsWithCategories(new ResponseResolver<ProductsWithCategoryResponse>() {
                @Override
                public void onSuccess(ProductsWithCategoryResponse productsWithCategoryResponse, Response response) {
                    listener.onProductWithCategorySuccess(productsWithCategoryResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    Log.e("dd","hh");
                    if(error==null ||error.getError()==null){

                        Gson gson=new Gson();
                        ProductsWithCategoryResponse response= gson.fromJson(msg,ProductsWithCategoryResponse.class);
                        listener.onProductWithCategorySuccess(response);

                    }
                    else {
                        listener.onError(error.getError());
                    }
                }
            },token,uuid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
